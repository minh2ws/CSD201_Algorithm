/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LAB2;

/**
 *
 * @author minh2ws
 */
public class User {
    private String email;
    private int point;

    /**
     * Constructor of User
     * @param email
     * @param point 
     */
    public User(String email, int point) {
	this.email = email;
	this.point = point;
    }

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the point
     */
    public int getPoint() {
	return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(int point) {
	this.point = point;
    }

    /**
     * print the information of user (Used for write to csv file)
     * @return String - email + point
     */
    @Override
    public String toString() {
	return email + ", " + point;
    }
}
