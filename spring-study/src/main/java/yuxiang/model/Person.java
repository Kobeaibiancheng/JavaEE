package yuxiang.model;

public class Person {
    @Override
    public String toString() {
        return "Person{" +
                "duck=" + duck +
                '}';
    }

    public Duck getDuck() {
        return duck;
    }

    public void setDuck(Duck duck) {
        this.duck = duck;
    }

    Duck duck;
}
