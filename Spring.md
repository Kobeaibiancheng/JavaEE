# Spring

## 快速入门

1.导入Spring的坐标，在pom.xml中
2.创建Bean,一个类实现一个接口，重新接口中的方法
3.创建applications.xml,在里面指定bean的id
4.通过ApplicationContext对象来getBean(Bean的id)

```java


public interface UserDao {
    public void print();
}
public class UserDaoImpl implements UserDao {

    public UserDaoImpl() {
        System.out.println("UserDaoImpl创建");
    }
    @Override
    public void print() {
        System.out.println("printing...");
    }
}
<bean id="userDao" class="com.spring.impl.UserDaoImpl" ></bean>
    
    
ApplicationContext app = new ClassPathXmlApplicationContext("applications.xml");
UserDao userDao = (UserDao) app.getBean("userDao");
userDao.print();
```

## 配置文件

**bean标签基本配置**

基本属性：

id：Bean实例在Spring容器中的唯一标识

class：Bean的全限定名称

**bean标签范围配置**

scope：值对象的作用范围

```Java
singleton//默认的，单例

prototype//多例的，scope = "prototype"

request//WEB项目中，Spring创建一个Bean的对象，将对象存入到request域中

session//WEB项目中，Spring创建一个Bean的对象，将对象存入到session域中

global session//WEB项目中，应用在Portlet环境，如果没有Porlet环境那么globalSession相当于session
    
<bean id="userDao" class="com.spring.impl.UserDaoImpl" scope="prototype"></bean>
```

1.当scope的取值为singleton时

Bean的实例化个数：1个

Bean的实例化时机：当Spring核心文件被加载时，实例化配置的Bean实例

Bean的生命周期：

- 对象创建：当应用加载，创建容器时，对象就被创建了
- 对象运行：只要容器在，对象一直活着
- 对象销毁：当应用卸载，销魂容器时，对象就被销毁了

2.当scope的取值为prototype时

Bean的实例化个数：多个

Bean的实例化时机：当调用getBean()方法时实例化Bean

- 对象创建：当使用对象时，创建新的对象实例
- 对象运行：只要对象在使用中，就一直存活
- 对象销毁：当对象长时间不用时，被Java的垃圾回收器回收了

### bean生命周期配置

```java
public class UserDaoImpl implements UserDao {

    public UserDaoImpl() {
        System.out.println("UserDaoImpl创建");
    }

    @Override
    public void print() {
        System.out.println("printing...");
    }


    public void init(){
        System.out.println("初始化。。。");
    }


    public void destory(){
        System.out.println("销毁。。。");
    }
}

public class SpringTest {
    @Test
    //测试scope属性
    public void test1(){
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applications.xml");
        UserDao userDao1 = (UserDao) app.getBean("userDao");
        System.out.println(userDao1);
        app.close();
    }
}
//执行结果
//UserDaoImpl创建
//初始化。。。
//com.spring.impl.UserDaoImpl@631330c
//销毁。。。



//要想让对象创建完毕，并且执行初始化方法init()，需要配置init-method = "",当对象使用完毕，执行销毁方法destory()
<bean id="userDao" class="com.spring.impl.UserDaoImpl" init-method="init" destroy-method="destory"></bean>
```

### **Bean实例化的三种方式**

- 无参构造方法实例化
- 工厂静态方法实例化
- 工厂实例方法实例化

```java
/**
 * 工厂静态方法实例化
 * 对象工厂,通过静态工厂获得对象
 */
public class StaticFactory {
    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }
}

<bean id="userDao" class="com.spring.factory.StaticFactory" factory-method="getUserDao"></bean>
```

```java
/**
 * 工厂实例方法实例化
 */
public class DynamicFactory {
    public  UserDao getUserDao() {
        return new UserDaoImpl();
    }
}

//先获取工厂对象
<bean id="factory" class="com.spring.factory.DynamicFactory"></bean>
//要想获得userDao这个对象，找到factory这个工厂对象的getUserDao方法
<bean id="userDao" factory-bean="factory" factory-method="getUserDao"></bean>
```

### **依赖注入**

- set方法注入对象
- 有参构造方法注入

```java
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        userDao.save();
    }
}



<bean id="userDao" class="com.spring.impl.UserDaoImpl"></bean>

//set方法注入，name指的是，ref指的是bean容器中对象的id
<bean id="userService" class="com.spring.impl.UserServiceImpl">
    <property name="userDao" ref="userDao"></property>//ref所指的userDao引用是bean容器中id为userDao这个引用
</bean>

//构造方法注入，name指的是
<bean id="userService" class="com.spring.impl.UserServiceImpl">
    <constructor-arg name="userDao" ref="userDao"></constructor-arg>
</bean>
```

### bean 依赖注入的数据类型

- 普通数据类型注入
- 引用数据类型注入
- 集合数据类型注入

```java
class User {
    private String name;
    private String address;

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

public class UserDaoImpl implements UserDao {
    //基本数据类型
    private String username;
    private int age;
    //集合数据类型
    private List<String> list;
    private Map<String, User> userMap;
    private Properties properties;

    public void setList(List<String> list) {
        this.list = list;
    }
    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAge(int age) {
        this.age = age;
    }
}

<!--注入基本数据类型-->
<bean id="userDao" class="com.spring.impl.UserDaoImpl">
    <property name="username" value="张三"/>
    <property name="age" value="18">
    </property>
</bean>

<!--注入集合数据类型-->
<bean id="userDao" class="com.spring.impl.UserDaoImpl">
    <!--注入List数据类型-->
    <property name="list">
        <list>
            <value>aaa</value>
            <value>bbb</value>
            <value>ccc</value>
        </list>
    </property>

    <!--注入Map数据类型-->
    <property name="userMap">
        <map>
            <entry key="u1" value-ref="user1"></entry>
            <entry key="u2" value-ref="user2"></entry>
        </map>
    </property>

    <!--注入Properties数据类型-->
    <property name="properties">
        <props>
            <prop key="p1">ppp1</prop>
            <prop key="p2">ppp2</prop>
            <prop key="p3">ppp3</prop>
        </props>
    </property>
</bean>

<bean id="user1" class="com.spring.domain.User">
    <property name="name" value="tom"></property>
    <property name="address" value="xian"></property>
</bean>

<bean id="user2" class="com.spring.domain.User">
    <property name="name" value="lucy"></property>
    <property name="address" value="beijing"></property>
</bean>
```