# InnoDB逻辑存储结构

![image-20250328201012237](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250328201012237.png)

**表空间**（ibd文件），一个mysql实例可以对应多个表空间，用于存储记录，索引数据等

**段**，分为数据段，索引段，回滚段，InnoDB是索引组织表，数据段就是B+树的叶子节点，索引段即为B+树的非叶子节点。段用来管理多个区（Extent）

**区**，表空间的单元结构，每个区的大小为1M。默认情况下，InnoDB存储引擎页大小为16k，即一个区中一共有64个连续的页。

**页**，是InnoDB存储引擎磁盘管理的最小单元，每个页的大小默认为16KB，为了保证页的连续性，InnoDB存储引擎每次从磁盘申请4-5个区。

**行**，InnoDB存储引擎数据是按行进行存放的。

# 架构

![image-20250328202117490](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250328202117490.png)

## 内存架构

**Buffer Pool**：缓冲池，位于内存中的一块区域，在执行增删改查时，先判断数据是否在缓冲池中，若在，则先操作缓冲池中的数据，若不在，则从磁盘中将数据加载到缓冲池中，然后再以一定的频率将数据刷新到磁盘中，从而减少磁盘IO，加快处理速度。

缓冲池以Page页为单位，底层采用链表管理Page，根据页状态，可以分为三类

1.free page：空闲页

2.clean page：被使用的页，但数据没有被修改

3.dirty page：被使用的页，且数据被修改过，页中数据和磁盘中的数据不一致

**Change Buffer**：更改缓冲区（针对非唯一二级索引页），在执行DML语句时，如果数据Page没有在Buffer Pool中，不会直接读取磁盘，而是将数据变更存在更改缓冲区（Change Buffer）中，在未来数据被读取时，再将数据合并恢复到Buffer Pool中，最后将合并的数据刷新到磁盘中。

Change Buffer存在的意义是什么?

于聚集索引不同，二级索引通常非唯一，并且以相对随机的顺序插入二级索引，同样，删除和更新可能会影响索引树中不相邻的二级索引页，如果每一次都要操作磁盘，会造成大量的IO，有了Change Buffer之后，我们可以在缓冲池中进行合并处理，减少磁盘IO。

Adaptive Hash Index：自适应hash索引。

**Log Buffer**：日志缓冲区，用来保存要写入到磁盘中的log日志数据（redo log，undo log），默认大小为16MB，日志缓冲区的日志会定期刷新到磁盘中。如果需要更新，插入或者删除多行的事务，增加日志缓冲区的大小可以节省磁盘IO

参数

```SQL
innodb_log_buffer_size:缓冲区大小
innodb_flush_log_at_trx_commit:日志刷新到磁盘时机;
0：每秒将日志写入并刷新到磁盘一次
1：日志在每次事务提交时写入并刷新到磁盘
2：日志在每次事务提交后写入，并每秒刷新到磁盘一次
```

## 磁盘架构

















## 后台线程

![image-20250330214110462](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250330214110462.png)

# 事务原理

![image-20250330214356825](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250330214356825.png)

## redo log

重做日志，记录的是事务提交时数据页的物理修改，用来实现事务的持久性。

由两部分组成：

**重做日志缓冲区（redo log buffer）**：位于内存中

**重做日志文件（redo log file）**：位于磁盘中



![image-20250330214811738](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250330214811738.png)

## undo log

**回滚日志，用来记录数据被修改前的信息，两个作用：提供回滚和MVCC**

它与redo log记录物理日志不一样，它是逻辑日志，当在执行一条delete时，undo log会记录一条执行delete之前的记录，或者在执行一条update时，会记录update之前的记录。

undo log销毁：undo log在事务执行时产生，事务提交时，并不会立即删除undo log，因为这些日志可能还用于MVCC。

undo log存储：采用段的方式进行管理和记录，存储在rollback segment回滚段中，内部包含1024个undo log segment。

![image-20250330215027757](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250330215027757.png)

# MVCC（多版本并发控制）

MVCC基本概念：

**当前读：**

读取的是记录的最新版本，读取时还要保证其他并发事务不能修改当前记录，会对读取的记录进行加锁

```SQL
select...lock in share mode(共享锁),select...for update,update,insert,delete(排他锁)都是一种当前读
```

快照读：

简单的select（不加锁）就是快照读，快照读，读取的是记录数据的可见版本，有可能是历史数据，不加锁，是非阻塞读。

Read Committed（RC）:每次select都生成一个快照读。

Repeatable Read（RR）：开启事务后第一个select 语句才是快照读的地方。

Serializable：快照读会退化为当前读。

MVCC：

多版本并发控制，指维护一个数据的多个版本，使得读写操作没有冲突，快照读为MySQL实现MVCC提供了一个非阻塞读功能。

**MVCC的具体实现，需要依赖数据库记录中的三个隐藏字段，undo log日志，readView。**

![image-20250330215423303](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250330215423303.png)

## 记录中的隐藏字段

事务id：最近修改事务id，记录插入这条记录或最后一个修改该记录的事务id。

回滚指针：指向这条记录的上一个版本，用来配合undo log，指向上一个版本。

隐藏主键：如果该表没有主键，将会自动生成该隐藏字段。

![image-20250331144913393](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250331144913393.png)

![image-20250331145223494](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250331145223494.png)

## undo log版本链

不同事务或相同事务对同一条记录进行修改，会导致该记录的undo log生成一条记录版本链表，链表的头部是最新的旧纪录，链表尾部是最早的旧纪录。



![image-20250331145714557](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250331145714557.png)

## readview

ReadView是快照读SQL执行时MVCC提取数据的依据，记录并维护当前系统活跃的事务（未提交的）id。

四个核心字段：

```mysql
m_ids//当前活跃的事务id集合
min_trx_id//最小活跃事务id
max_trx_id//当前最大事务id+1(因为事务id是自增的)
creator_trx_id//ReadView创建的事务id
```

![image-20250331214104630](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250331214104630.png)

![image-20250331214257873](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250331214257873.png)

## RC（read committed）隔离级别下的MVCC原理

该事务隔离级别下，readview在每一个快照读时生成readview

![image-20250331214814463](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250331214814463.png)

## RR隔离级别下的MVCC

该事务隔离级别下，仅在事务第一次执行快照读时生成readview，后续复用该readview。

























![image-20250331215243570](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250331215243570.png)







# 总结

![image-20250331215531745](C:\Users\Tibet\AppData\Roaming\Typora\typora-user-images\image-20250331215531745.png)
