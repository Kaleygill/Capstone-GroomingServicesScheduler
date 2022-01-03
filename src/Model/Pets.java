/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Koala
 */
public class Pets {
    private int petID; 
    private String petName; 
    private String breed;
    private String color; 
    private String weight; 
    private int cusID; 
    private String notes;

    public Pets(int petID, String petName, String breed,  String notes,int cusID,String color, String weight) {
        this.petID = petID;
        this.petName = petName;
        this.breed = breed;
        this.color = color;
        this.weight = weight;
        this.cusID = cusID;
        this.notes = notes;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getCusID() {
        return cusID;
    }

    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getHasShoes() {
        return null;
    }
    
    
}
