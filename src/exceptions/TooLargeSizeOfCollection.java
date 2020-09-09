package exceptions;

public class TooLargeSizeOfCollection extends RuntimeException {
    public TooLargeSizeOfCollection() {
        super();
    }

    public TooLargeSizeOfCollection(String message) {
        super(message);
    }

    public TooLargeSizeOfCollection(String message, Throwable cause) {
        super(message, cause);
    }

    public TooLargeSizeOfCollection(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Max collection size reached. No elements can be added";
    }

    @Override
    public String getLocalizedMessage() {
        return "Достигнут максимальный размер коллекции. Дальнейшее добавление элементов невозможно";
    }

    @Override
    public String toString() {
        printStackTrace();
        String s = getClass().getName();
        String message = getMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

    @Override
    public void printStackTrace() {
        printStackTrace(System.out);
    }
}
