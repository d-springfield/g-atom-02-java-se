package reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TrackField {
    public enum Priority {
        HIGH(0),
        MEDIUM(1),
        LOW(2);

        private int priority;

        Priority(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    Priority priority() default Priority.MEDIUM;
}
