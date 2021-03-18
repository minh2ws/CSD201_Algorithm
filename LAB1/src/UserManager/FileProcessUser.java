/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author minhv
 */
public class FileProcessUser {

    /**
     * Read file from fileName inputted by user and put it inside folder
     * project. Then add it to queue
     *
     * @param fileName
     * @param list
     */
    public static void readData(String fileName, PriorityQueue list) {
	File file = null;
	FileReader fileRead = null;
	BufferedReader read = null;
	try {
	    file = new File(fileName);
	    //create file reader
	    fileRead = new FileReader(file);
	    //create buffer reader
	    read = new BufferedReader(fileRead);
	    while (read.ready()) {
		//read one line in file
		String s = read.readLine(); //abcv203@gmail.com, 250
		//split it into email and point
		String[] arr = s.split(", "); //arr[0] = "abcv203@gmail.com", arr[1] = "250"
		//skip line Email and Point in file
		if (!arr[0].equalsIgnoreCase("Email") || !arr[1].equalsIgnoreCase("Point")) {
		    //create new object and insert email and point from array readed
		    User userData = new UserManager.User(arr[0], Integer.parseInt(arr[1]));
		    //add to list
		    list.insertUser(userData);
		}
	    }
	} catch (Exception e) {
	    System.out.println("File not founded");
	} finally {
	    try {
		if (fileRead != null) {
		    fileRead.close();
		}
		if (read != null) {
		    read.close();
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}
    }
    
    /**
     * Write to csv file
     * @param fileName
     * @param list 
     */
    public static void writeData(String fileName, PriorityQueue list) {
	list.writeUserToFile(fileName);
    }
}
