package reflection;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        Consumer consumer = new Consumer("log.txt");
        Parent parent = new Parent();
        Child child = new Child(1, "First value");

        consumer.consume(parent);
        consumer.consume(child);

        child.setImportantValue(2);
        child.setStatus("New status");
        consumer.consume(child);

        consumer.consume(new Child(-1, "another Object"));
    }
}
