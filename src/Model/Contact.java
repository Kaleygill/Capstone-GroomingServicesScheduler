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
public class Contact {
    private int id;
    private String name;
    private String email;
    
    /**
     *
     * @param id
     * @param name
     * @param email
     */
    public Contact(int id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     *
     * @return Contact ID
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
     * @return Contact Name
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
     * @return Contact Email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
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
