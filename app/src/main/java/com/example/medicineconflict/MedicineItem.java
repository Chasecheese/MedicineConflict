package com.example.medicineconflict;

import java.util.ArrayList;

public class MedicineItem {

    private int ID;
    private String Name;
    private String Conflict;
    private ArrayList<String> coList;

    public MedicineItem(String name, String conflict) {
        Name = name;
        Conflict = conflict;
    }

    public MedicineItem(String name, ArrayList<String> coList) {
        this.Name = name;
        this.coList = coList;
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
