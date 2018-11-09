package dk.easj.ianx0156.therealmandatoryapp;

public class Rooms {

    private int id;
    private String name;
    private String description;
    private int capacity;
    private String remarks;
    private int buildingId;

    //Get propperties
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getbuildingId() {
        return buildingId;
    }

    //Set propperties
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        return "Room:" + name + "   capacity of: " + capacity ;
    }
}
