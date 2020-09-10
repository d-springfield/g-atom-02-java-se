package serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Human implements Serializable {
    private String name;
    private int age;
    private transient Activity activity;

    private static final long serialVersionUID = 6266090340429295295L;

    public Human(String name, int age) {
        if (age < 0) {
            throw new IllegalArgumentException("negative age: " + age);
        }

        this.name = name;
        this.age = age;
        this.activity = defineActivity(age);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.activity = defineActivity(this.age);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public String toString() {
        return String.format("Name : %-3s, age : %-2d, activity : %s", name, age, activity);
    }

    private Activity defineActivity(int age){
        if (age < 3) {
            return Activity.STAY_AT_HOME;

        } else if (age < 7) {
            return Activity.KINDERGARTEN;

        } else if (age < 18) {
            return Activity.SCHOOL;

        } else if (age < 24) {
            return Activity.UNIVERSITY;

        } else if (age < 65) {
            return Activity.WORK;

        } else {
            return Activity.RETIREMENT;
        }
    }
}
