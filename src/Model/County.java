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
public class County {
    private int id;
    private String name;
    
    /**
     *
     * @param id
     * @param name
     */
    public County(int id, String name) 
    {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return County ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return County Name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return (getName() + " [" + getId() + "]");
    }
}
