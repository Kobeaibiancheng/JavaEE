package yuxiang;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import yuxiang.model.Duck;
import yuxiang.model.DuckShop;
import yuxiang.model.Person;

public class Main {
    public static void main(String[] args) {
        //开始加载文件
        ApplicationContext context = new
                ClassPathXmlApplicationContext("applications.xml");

        //从容器中获取对象
        /*Duck duck1 = (Duck)context.getBean("duck1");
        System.out.println(duck1);
        Duck duck1_1 = (Duck)context.getBean("duck1");
        System.out.println(duck1_1);
        System.out.println(duck1 == duck1_1);//true: duck1默认是scope为单例


        Duck duck2 = (Duck) context.getBean("duck2");
        System.out.println(duck2);
        Duck duck2_2 = (Duck) context.getBean("duck2");
        System.out.println(duck2_2);
        System.out.println(duck2 == duck2_2);//false:  duck2设置scope为prototype,getBean生成新的对象


        DuckShop duckShop = (DuckShop) context.getBean("duckShop");
        System.out.println(duckShop);
        Person person = context.getBean(Person.class);
        System.out.println(person);

        Duck duck3 = (Duck) context.getBean("duck3");
        System.out.println(duck3);

        Duck duck4 = (Duck) context.getBean("duck4");
        System.out.println(duck4);*/


        //通过类型在容器中获取 bean 对象；该类型只有一个对象在容器中，否则会报错
        //context.getBean(Bean.class);
    }
}
