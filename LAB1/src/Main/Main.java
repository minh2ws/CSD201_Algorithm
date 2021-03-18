/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import HTMLParser.HTMLValidateAndCount;
import UserManager.FileProcessUser;
import UserManager.PriorityQueue;
import UserManager.User;

/**
 *
 * @author minhv
 */
public class Main {

    /**
     * Check equal character from array String
     *
     * @param array
     * @param value
     * @return number of equal character
     */
    public static int include(String[] array, String value) {
	int result = -1;

	for (int i = 0; i < array.length; i++) {
	    if (array[i].equals(value)) {
		result = i;
	    }
	}
	return result;
    }

    /**
     * Get key of CLI and check it
     *
     * @param args
     * @return finalResult (String) is an String show error
     */
    public static String CLI(String[] args) {
	String finalResult = "";
	if (args.length != 0 && !args[0].equals("-h")) {
	    if (!args[0].equals("1") && !args[0].equals("2")) {
		finalResult += "Name is not correct!";
	    } else if (args[0].equals("1") && include(args, "-r") == -1) {
		finalResult = finalResult + "No user input file !";
	    } else if (args[0].equals("1") && include(args, "-r") != -1 && (include(args, "-r") + 1) >= args.length) {
		finalResult = finalResult + "No input filename !";
	    } else if (args[0].equals("2") && args.length < 3) {
		finalResult = finalResult + "Your CLI format is not correct !";
	    }
	} else {
	    finalResult += "Help:\n";
	    finalResult += "./lab1 1 -r <<user_CSV_file>> -s <<new_user_CSV_file>>: Problem 1, read the user csv file and save the data structure into csv file\n";
	    finalResult += "./lab1 1 -r <<user_CSV_file>> -s <<new_user_CSV_file>> -a <<email>> <<point>>: Problem 1, add a new user into the data structure and save to new csv file\n";
	    finalResult += "./lab1 1 -r <<user_CSV_file>> -s <<new_user_CSV_file>> -d <<email>>: Problem 1, delete a user in the data structure and save to new csv file\n";
	    finalResult += "./lab1 1 -r <<user_CSV_file>> -s <<new_user_CSV_file>> -u <<email>> <<new_point>>: Problem 1, update new point for user in the data structure and save to new csv file\n";
	    finalResult += "./lab1 1 -r <<user_CSV_file>> -s <<new_user_CSV_file>> -dt: Problem 1, delete the top user from the data structure and save to new csv file\n";
	    finalResult += "./lab1 1 -r <<user_CSV_file>> -g <<email>>: Problem 1, get the point of user from the data structure\n";
	    finalResult += "./lab1 1 -r <<user_CSV_file>> -t: Problem 1, get the point of the top user from the data structure\n";
	    finalResult += "./lab1 1 -r <<user_CSV_file>> -t: Problem 1, get the point of the top user from the data structure\n";
	    finalResult += "./lab1 2 <<URL-of-website>> <<output-CSV-file>>: Problem 2, read html info from a URL, save all tag information into the CSV output file\n";
	}
	return finalResult;
    }

    /**
     * Main class
     * @param args
     */
    public static void main(String[] args) {
	PriorityQueue list = new PriorityQueue();
	String msg = CLI(args);
	if (msg.equals("") && args[0].equals("1")) {
	    try {
		try {
		    for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			    case "-r": //read CSV file and add to queue
				String inputFile = args[i + 1].trim();
				FileProcessUser.readData(inputFile, list);
				break;
			    case "-s": //import the queue to new CSV file
				String outputFile = args[i + 1].trim();
				FileProcessUser.writeData(outputFile, list);
				break;
			    case "-a": //insert new Gamer to queue
				int point = Integer.parseInt(args[include(args, "-a") + 2].trim());
				String email = args[include(args, "-a") + 1].trim();
				UserManager.User user = new User(email, point);
				list.insertUser(user);
				FileProcessUser.writeData(args[include(args, "-s") + 1].trim(), list);
				break;
			    case "-d": //delete gamer from queue
				list.deleteUser(args[include(args, "-d") + 1].trim());
				FileProcessUser.writeData(args[include(args, "-s") + 1].trim(), list);
				break;
			    case "-u": //update gamer
				point = Integer.parseInt(args[include(args, "-u") + 2].trim());
				email = args[include(args, "-u") + 1].trim();
				list.updateUser(email, point);
				FileProcessUser.writeData(args[include(args, "-s") + 1].trim(), list);
				break;
			    case "-dt": //delete top gamer
				list.removeTop();
				FileProcessUser.writeData(args[include(args, "-s") + 1].trim(), list);
				break;
			    case "-g": //get point of gamer
				list.searchUser(args[include(args, "-g") + 1].trim());
				break;
			    case "-t": //get point of top gamer
				list.getTop();
				break;
			}
		    }
		} catch (ArrayIndexOutOfBoundsException e) {
		    System.out.println("Can't find argument to excecute!");
		}
	    } catch (Exception e) {
		System.out.println("Can't find the input file");
	    }
	} else if (msg.equals("") && args[0].equals("2")) {
	    try {
		HTMLValidateAndCount html = new HTMLValidateAndCount();
		html.manage(args[2], args[1]);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("Can't find argument to excecute!");
	    }
	} else {
	    System.out.println(msg);
	}
    }
}
