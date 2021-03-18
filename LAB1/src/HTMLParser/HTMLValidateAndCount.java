/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HTMLParser;

import java.util.StringTokenizer;

/**
 *
 * @author minhv
 */
public class HTMLValidateAndCount {

    private FileProcessHTML file = new FileProcessHTML();
    //Create an stack
    private Stack stack = new Stack();

    /**
     * Check is open tag or not
     *
     * @param tag
     * @return true or false
     */
    private boolean isOpenTag(String tag) {
	//if it contain "<" and don't contain "</"
	return tag.contains("<") && !tag.contains("</");
    }

    /**
     * Check is close tag or not
     *
     * @param tag
     * @return true or false
     */
    private boolean isCloseTag(String tag) {
	//if it contain "</"
	return tag.contains("</");
    }

    /**
     * Convert an open tag to close tag
     *
     * @param tag
     * @return tag
     */
    public String converToCloseTag(String tag) {
	tag = tag.replace("<", "</");	//replace "<" to "</"
	return tag;
    }

    /**
     * If it (in status close tag after converted) is contain in HTML return
     * false, else return true. Khang explains for me how to identify alone tag
     * like <br>
     * Thanks for his helping
     *
     * @param tag
     * @param htmlString
     * @return true or false
     */
    public boolean isAloneTag(String tag, String htmlString) {
	tag = converToCloseTag(tag);	//convert to close tag
	return !htmlString.contains(tag);	//compare tag in the HTMl
	//htmlString.contains(tag) is true when it have close tag -> not alone tag
	//else false -> it is alone tag
    }

    /**
     * Convert special tag like to normal tag
     *
     * @param tag
     * @return
     */
    public String converSpecialToNormalTag(String tag) {
	tag = tag.replace("/>", ">");
	return tag;
    }

    /**
     * Compare two tag one is open and one is close Convert open to close for
     * comparing
     *
     * @param tag
     * @param tagInStack
     * @return true or false
     */
    public boolean compareTag(String tag, String tagInStack) {
	//convert open tag to close tag
	tagInStack = converToCloseTag(tagInStack);
	//return an compare if equal return true else false
	return tag.equals(tagInStack);
    }

    /**
     * Spit each character and merge it to a tag. Using regex expression to
     * verify each character. If it a tag put it to checkTag method to verify it
     * open or close or alone tag
     *
     * @param htmlString
     */
    public void splitTag(String htmlString) {
	String tag = "";
	boolean isStillValid = true;
	//htmlString.length() use to get length of string also include space
	for (int i = 0; i < htmlString.length(); i++) {
	    //check is "<" or not
	    if (htmlString.charAt(i) == '<') {
		tag += '<';
		//continue to read this String and isStillValid is the break point of loop
		for (int j = i + 1; j < htmlString.length() && isStillValid; j++) {
		    if (htmlString.charAt(j) != '>' && htmlString.charAt(j) != '<') {
			//merge each character to a tag
			tag += htmlString.charAt(j);
			//it's not a tag, reset tag 
//			if (tag.equals("< ")) {
//			    tag = "";
//			    i = j;
//			    isStillValid = false;
//			}
		    } else if (htmlString.charAt(j) == '<') {
			tag = "";
			isStillValid = false;
			j-=2;
			i=j;
		    } else if (htmlString.charAt(j) == '>') {
			//make tag close
			tag += '>';
			//check is special tag without close tag like </html>
			if (tag.contains("/>")) {
			    //create temp String to store tag
			    String tempTag = "";
			    StringTokenizer stk = new StringTokenizer(tag, " ");
			    tempTag = stk.nextToken();
			    tempTag = tempTag.trim();
			    //make temp tag close
			    if (!tempTag.contains("/>")) {
				tempTag += "/>";
			    } else if (!tempTag.contains(">")) {
				tempTag += ">";
			    }
			    //result tag from <!DOCTYPE html> or comment tag
			    if (!tempTag.equals("</>") && regexCheckTag(tempTag)) {
				//it's time for check tag
				checkTag(tempTag, htmlString);
			    }
			    //stop the loop
			    isStillValid = false;
			} else {
//			        System.out.println("out: " + tag);
			    //create temp String to store tag
			    String tempTag = "";
			    StringTokenizer stk = new StringTokenizer(tag, " ");
			    tempTag = stk.nextToken();
			    tempTag = tempTag.trim();
			    if (!tempTag.contains(">")) {
				tempTag += ">";
			    }
//			    System.out.println("Is Valid: " + regexCheckTag(tempTag) + " tag: " + tempTag);
			    //make temp tag close
			    //result tag from <!DOCTYPE html> or comment tag
			    if (!tempTag.equals("<>") && regexCheckTag(tempTag)) {
				//it's time for check tag
				checkTag(tempTag, htmlString);
			    }
			    //stop the loop
			    isStillValid = false;
			}
			//reset String tag to none
			tag = "";
			//decrease j-- unless it will cause an error when it
			//execute in else it also run j++ and then it will skip tag
			j--;
			//let start i from j position
			i = j;
		    }
		}
	    } else {
		//reset isStillValid to valid for run the loop
		isStillValid = true;
	    }
	}
    }

    /**
     * Check an tag is valid or not and than add to list in File to write to csv
     * File
     *
     * @param tag
     * @param htmlString
     */
    public void checkTag(String tag, String htmlString) {
	String tagCompare = "";
	String tagTemp = "";
	boolean isValid;
	//check open tag or close tag
	if (isOpenTag(tag)) {
	    //if it not alone tag put it to stack
	    if (!isAloneTag(tag, htmlString)) {
		stack.push(tag);
//		System.out.println("Push: " + tag);
	    } else if (tag.contains("/>")) {
		tagTemp = converSpecialToNormalTag(tag);
		file.updateValue(tagTemp);
	    }
	} else if (isCloseTag(tag)) {
	    //close tag then tag it from stack and compare it
	    tagCompare = stack.top();
	    //compare tag return true if it matches
	    isValid = compareTag(tag, tagCompare);
//	    System.out.println("Pop: " + tag + " isValid: " + isValid);
	    if (isValid) {
		//increase count
		file.updateValue(stack.pop());
	    }
	}
    }

    /**
     * Check each character base on regex expression start from a to z, A to Z,
     * 0 to 9 and include '/' also + for check not empty (space), if match
     * return true else false
     *
     * @param character
     * @return true or false
     */
    public static boolean regexCheckTag(String character) {
	return character.matches("^[a-zA-Z0-9'/''<''>']+$");
    }

    /**
     * Function to run all or function to split and count HTML tag from and url
     * and write it to csv file
     *
     * @param csvFile
     * @param urlString
     */
    public void manage(String csvFile, String urlString) {
	try {
	    String htmlString = FileProcessHTML.downloadWebsite(urlString);
	    splitTag(htmlString);
	    FileProcessHTML.writeData(csvFile);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
