/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LAB2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 *
 * @author minh2ws
 */
public class AVLTreeManagement {

    /**
     * Create class variable for using in all function
     */
    AVLTree tree;
    HashMap<String, User> list;

    /**
     * Constructor default
     */
    public AVLTreeManagement() {
	tree = new AVLTree();
	list = new HashMap<>();
    }

    /**
     * Insert a node to tree by reading from csv file
     *
     * @param fileName
     */
    public void insert(String fileName) {
	File file = null;
	FileReader read = null;
	BufferedReader buffer = null;

	try {
	    file = new File(fileName);
	    read = new FileReader(file);
	    buffer = new BufferedReader(read);
	    while (buffer.ready()) {
		String s = buffer.readLine();
		String[] arr = s.split(", ");
		if (arr.length == 2 && !arr[0].equalsIgnoreCase("email")) {
		    String email = arr[0].trim();
		    int point = Integer.parseInt(arr[1].trim());
		    User user = new User(email, point);
		    tree.insert(user);
		    list.put(email, user);
		}
	    }
	} catch (Exception e) {
	    System.out.println("Read file error");
	} finally {
	    try {
		if (read != null) {
		    read.close();
		}
		if (buffer != null) {
		    buffer.close();
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Delete user by getting mode to delete and point of user
     *
     * @param pointInputted
     * @param typeDelete
     */
    public void delete(String pointInputted, String typeDelete) {
	int point = Integer.parseInt(pointInputted);
	User user = tree.findNodeUser(point);
	String email = user.getEmail();
	switch (typeDelete) {
	    case "copy-right":
		System.out.println("\n[DELETE COPYING RIGHTMOST] " + "(" + list.get(email) + ")");
		tree.deleteByCopyingRight(point);
		tree.inOrder(tree.getRoot());
		System.out.println("");
		tree.preOrder(tree.getRoot());
		break;
	    case "copy-left":
		System.out.println("\n[DELETE COPYING LEFTMOST] " + "(" + list.get(email) + ")");
		tree.deleteByCopyingLeft(point);
		tree.inOrder(tree.getRoot());
		System.out.println("");
		tree.preOrder(tree.getRoot());
		break;
	}
    }

    /**
     * Update user by getting mode for delete and location to write result
     *
     * @param fileName
     * @param typeDelete
     */
    public void update(String fileName, String typeDelete) {
	File file = null;
	FileReader read = null;
	BufferedReader buffer = null;

	try {
	    file = new File(fileName);
	    read = new FileReader(file);
	    buffer = new BufferedReader(read);

	    while (buffer.ready()) {
		String s = buffer.readLine();
		String[] arr = s.split(", ");
		if (arr.length == 2 && !arr[0].equalsIgnoreCase("email")) {
		    String email = arr[0].trim();
		    int point = Integer.parseInt(arr[1].trim());
		    User user = new User(email, point);
		    int oldPoint = list.get(email).getPoint();
		    //notify update
		    System.out.println("\n\n[UPDATE] " + "(" + list.get(email) + ")");
		    //delete old user
		    delete(("" + oldPoint), typeDelete);
		    //remove old user from hashmap
		    list.remove(email);
		    //insert again
		    tree.insert(user);
		    //put new user after update
		    list.put(email, user);
		}
	    }
	} catch (Exception e) {
	    System.out.println("Update error");
	} finally {
	    try {
		if (read != null) {
		    read.close();
		}
		if (buffer != null) {
		    buffer.close();
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Write tree to csv file base on type traverse
     *
     * @param fileName
     * @param typePrint
     */
    public void write(String fileName, String typePrint) {
	File file = null;
	PrintWriter write = null;

	try {
	    file = new File(fileName);
	    write = new PrintWriter(file);
	    write.println("Email, Point");
	    switch (typePrint) {
		case "nlr":
		    tree.preOrder(tree.getRoot(), write);
		    break;
		case "lnr":
		    tree.inOrder(tree.getRoot(), write);
		    break;
		case "lrn":
		    tree.posOrder(tree.getRoot(), write);
		    break;
	    }

	} catch (Exception e) {
	    System.out.println("Write file error");
	} finally {
	    try {
		if (write != null) {
		    write.close();
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
}
