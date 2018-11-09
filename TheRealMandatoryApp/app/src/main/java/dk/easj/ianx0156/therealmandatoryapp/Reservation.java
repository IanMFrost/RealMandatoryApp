package dk.easj.ianx0156.therealmandatoryapp;

public class Reservation {

    private int id;
    private int roomId;
    private String userId;
    private String fromTimeString;
    private String toTimeString;
    private String purpose;

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromString() {
        return fromTimeString;
    }

    public void setFromString(String fromString) {
        this.fromTimeString = fromString;
    }

    public String getToString() {
        return toTimeString;
    }

    public void setToString(String toString) {
        this.toTimeString = toString;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Room: " + getRoomId() + " User: " + getUserId() +  " || " + "From: " + fromTimeString + " To: " + toTimeString;

    }
}
