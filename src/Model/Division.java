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
public class Division {
    private int divID;
    private String division;
    private int countryID;
    
    /**
     *
     * @param divID
     * @param division
     * @param countryID
     */
    public Division(int divID, String division, int countryID) 
    {
        this.divID = divID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     *
     * @return Division ID
     */
    public int getDivID() {
        return divID;
    }

    /**
     *
     * @param divID
     */
    public void setDivID(int divID) {
        this.divID = divID;
    }

    /**
     *
     * @return Division
     */
    public String getDivision() {
        return division;
    }

    /**
     *
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     *
     * @return Country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *
     * @param country
     */
    public void setCountry(int country) {
        this.countryID = countryID;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return (getDivision() + " [" + getDivID() + "]");
    }

    
}
