package serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final String path = "human.out";

        ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(path));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));

        List<Human> humans1 = new ArrayList<>(Arrays.asList(
                new Human("XS", 1),
                new Human("S", 4),
                new Human("M", 14),
                new Human("L", 20),
                new Human("XL", 40),
                new Human("XXL", 70)
        ));

        System.out.println("Before serialization : ");
        for (Human h : humans1) {
            System.out.println(h);
        }

        ous.writeObject(humans1);
        ous.close();

        List<Human> humans2 = (List<Human>) ois.readObject();
        ois.close();

        System.out.println("\n\nAfter serialization : ");
        for (Human h : humans2) {
            System.out.println(h);
        }

    }
}
