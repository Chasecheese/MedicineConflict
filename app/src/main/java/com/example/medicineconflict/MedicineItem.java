package com.example.medicineconflict;

public class MedicineItem {

    private int ID;
    private String Name;
    private String Conflict;

    public MedicineItem(String name, String conflict) {
        Name = name;
        Conflict = conflict;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getConflict() {
        return Conflict;
    }

    public void setConflict(String conflict) {
        Conflict = conflict;
    }
}
