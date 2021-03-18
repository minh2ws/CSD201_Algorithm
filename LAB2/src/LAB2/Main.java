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
public class Main {

    /**
     * Help command
     */
    public static String help = "Help: \n"
	    + "java -jar LAB2.jar -r <<user_CSV_file>> -lnr <<new_user_CSV_file>>: Read the user csv file and save the tree strucutre with LNR order into csv file\n"
	    + "java -jar LAB2.jar -r <<user_CSV_file>> -nlr <<new_user_CSV_file>>: Read the user csv file and save the tree strucutre with NLR order into csv file\n"
	    + "java -jar LAB2.jar -r <<user_CSV_file>> -lrn <<new_user_CSV_file>>: Read the user csv file and save the tree strucutre with LRN order into csv file\n"
	    + "java -jar LAB2.jar -r <<user_CSV_file>> -delete <<point>> -mode <<delete_mode>> -<<order>> <<new_user_CSV_file>>: Read the user csv file, delete the users with email and save the tree strucutre with <<order>> into csv file, <<order>> is one in [lnr, nlr, lrn], <<delete_mode>> is one in [copy-right, copy-left]\n"
	    + "java -jar LAB2.jar -r <<user_CSV_file>> -update <<another_user_CSV_file>> -mode <<delete_mode>> -<<order>> <<new_user_CSV_file>>: Read the user csv file, update the user point from another user CSV file and save the tree strucutre with <<order>> into csv file, <<order>> is one in [lnr, nlr, lrn], <<delete_mode>> is one in [copy-right, copy-left]";

    /**
     * Manipulate CLI to run the program
     *
     * @param args
     */
    public static void getCLI(String[] args) {
	AVLTreeManagement tree = new AVLTreeManagement();
	String point = null;
	boolean deleteOrUpdate = true;
	String modeDelete;
	String fileName = null;
	try {
	    for (int i = 0; i < args.length; i++) {
		switch (args[i]) {
		    case "-r":
			tree.insert(args[i + 1].trim());
			break;
		    case "-lnr":
			tree.write(args[i + 1].trim(), "lnr");
			break;
		    case "-nlr":
			tree.write(args[i + 1].trim(), "nlr");
			break;
		    case "-lrn":
			tree.write(args[i + 1].trim(), "lrn");
			break;
		    case "-delete":
			deleteOrUpdate = true;
			point = args[i + 1].trim();
			break;
		    case "-update":
			fileName = args[i + 1].trim();
			deleteOrUpdate = false;
			break;
		    case "-mode":
			modeDelete = args[i + 1].trim();
			if (deleteOrUpdate) {
			    tree.delete(point, modeDelete);
			} else {
			    tree.update(fileName, modeDelete);
			}
			break;
		}
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.out.println("CLI inpputed wrong");
	}
    }

    /**
     * Main function
     * @param args 
     */
    public static void main(String[] args) {
	try {
	    if (args[0].equals("-h")) {
		System.out.println(help);
	    } else {
		getCLI(args);
	    }
	} catch (Exception e) {
	    System.out.println("Error CLI");
	}
    }
}
