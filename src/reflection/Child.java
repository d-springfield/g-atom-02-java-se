package reflection;

@Track
public class Child extends Parent {

    @TrackField
    private int importantValue;

    @TrackField(priority = TrackField.Priority.HIGH)
    private String status;

    private int usualValue;

    public Child(int importantValue, String status) {
        this.importantValue = importantValue;
        this.status = status;
    }

    public int getImportantValue() {
        return importantValue;
    }

    public void setImportantValue(int importantValue) {
        this.importantValue = importantValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
