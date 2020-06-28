package yuxiang.model;

public class DuckFactory {
    public static Duck create() {
        Duck duck = new Duck();
        duck.setName("Gigi");
        duck.setAge(15);
        return duck;
    }
    public Duck create2() {
        Duck duck = new Duck();
        duck.setName("girlfriend");
        duck.setAge(20);
        return duck;
    }
}
