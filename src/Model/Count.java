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
public class Count {
    private String type;
    private int count; 
    private String month; 
    public int getCount;
    
    public Count(String type, int count) {
        this.type = type; 
        this.count = count; 
        month = ""; 
    }
    
    public Count(int count, String month) {
        type = ""; 
        this.count = count; 
        month = ""; 
    }
    
    public void setType(String type) {
        this.type = type; 
    }
    
    public String getType() {
        return type; 
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count; 
    }
    
    public String getMonth() {
        return month; 
    }
    
    public void setMonth(String month) {
        this.month = month; 
    }
}
