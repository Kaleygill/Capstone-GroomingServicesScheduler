/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class Horse extends Pets{
    String haveHorseShoe;

    public Horse(int petID, String petName, String breed, String notes, int cusID, String color, String weight, String haveHorseShoe) {
        super(petID, petName, breed, notes, cusID, color, weight);
        this.haveHorseShoe = haveHorseShoe;
    }

    public String getHaveHorseShoe() {
        return haveHorseShoe;
    }

    public void setHaveHorseShoe(String haveHorseShoe) {
        this.haveHorseShoe = haveHorseShoe;
    }
    
    public static ObservableList<String> getHorseShoe() {
        ObservableList<String> horse = FXCollections.observableArrayList();

        horse.add("Yes");
        horse.add("No");
        horse.add("Unknown");
        
        
        return horse;
    }
    
    @Override 
    public String getHasShoes() {
        return haveHorseShoe;
    }
           
}
