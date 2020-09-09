package reflection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Consumer {
    private final File f;

    public Consumer(String path) throws IOException {
        f = new File(path);
        if (!f.exists()) {
            f.createNewFile();
        }
    }

    public void consume(Parent parent) throws IOException {
        Class cl = parent.getClass();
        if (cl.isAnnotationPresent(Track.class)) {
            StackTraceElement[] stackFrames = Thread.currentThread().getStackTrace();
            StackTraceElement callingFrame = stackFrames[stackFrames.length - 1];

            StringBuilder sb = new StringBuilder();
            String format = "%s from %s#%s (%s) ";
            sb.append(
                    String.format(format, cl.getName(), callingFrame.getClassName(),
                            callingFrame.getMethodName(), callingFrame.getLineNumber())
            );

            format = "%s=\"%s\"; ";
            ArrayList<Field> trackedFields = getSortedTrackedFields(cl);
            for (Field f : trackedFields) {
                try {
                    f.setAccessible(true);
                    sb.append(String.format(format, f.getName(), f.get(parent)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            sb.append("\n");

            try (FileWriter fw = new FileWriter(f, true)) {
                fw.write(sb.toString());
            }
        }

    }

    private ArrayList<Field> getSortedTrackedFields(Class cl) {
        ArrayList<Field> trackFields = new ArrayList<>();
        for (Field f : cl.getDeclaredFields()) {
            if (f.isAnnotationPresent(TrackField.class)) {
                trackFields.add(f);
            }
        }

        trackFields.sort((o1, o2) -> {
            int priority1 = o1.getAnnotation(TrackField.class).priority().getPriority();
            int priority2 = o2.getAnnotation(TrackField.class).priority().getPriority();
            return Integer.compare(priority1, priority2);
        });

        return trackFields;
    }
}
