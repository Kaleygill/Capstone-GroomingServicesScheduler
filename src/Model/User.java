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
public class User {
    private int uID;
    private String username;
    private String password;
    
    /**
     *
     * @param uID
     * @param username
     * @param password
     */
    public User(int uID, String username, String password)
    {
        this.uID = uID;
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @return User ID
     */
    public int getuID() {
        return uID;
    }

    /**
     *
     * @param id
     */
    public void setuID(int id) {
        this.uID = uID;
    }

    /**
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return (getUsername() + " [" + getuID() + "]");
    }
}
