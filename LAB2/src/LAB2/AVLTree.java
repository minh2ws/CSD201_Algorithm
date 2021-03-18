/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LAB2;

import java.io.PrintWriter;

/**
 *
 * @author minh2ws
 */
public class AVLTree {

    private class Node {

	private int height; //store value of height to check is balance or not
	private User user;  //store value of user
	private Node left;  //store pointer of left child
	private Node right; //store pointer of right child

	/**
	 * Constructor of Node
	 *
	 * @param user
	 */
	public Node(User user) {
	    height = 1;		//set default height is 1
	    this.user = user;	//set user 
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
	    return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
	    this.height = height;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
	    return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
	    this.user = user;
	}

	/**
	 * @return the left
	 */
	public Node getLeft() {
	    return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(Node left) {
	    this.left = left;
	}

	/**
	 * @return the right
	 */
	public Node getRight() {
	    return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(Node right) {
	    this.right = right;
	}
    }

    //create root
    private Node root;

    /**
     * Constructor default set root is null
     */
    public AVLTree() {
	root = null;
    }

    /**
     * @return the root
     */
    public Node getRoot() {
	return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Node root) {
	this.root = root;
    }

    /**
     * Return height of this node, if node null return -1
     *
     * @param node
     * @return height
     */
    public int getHeightOfNode(Node node) {
	//prevent node null make error
	if (node == null) {
	    return 0;
	}
	//return height
	return node.getHeight();
    }

    /**
     * get max height of 2 node
     *
     * @param heightA
     * @param heightB
     * @return max height
     */
    private int maxHeight(int heightA, int heightB) {
	//comapre 2 height
	if (heightA > heightB) {
	    return heightA;
	} else {
	    return heightB;
	}
    }

    /**
     * Update height of node parameter
     *
     * @param node
     */
    private void updateHeight(Node node) {
	//set height of node parameter
	//using max height to get highest height and then plus + 1
	node.setHeight(1 + maxHeight(getHeightOfNode(node.getLeft()), getHeightOfNode(node.getRight())));
    }

    /**
     * Right rotate node and return new root has been rotated
     *
     * @param node
     * @return new root has been rotated
     */
    private Node rightRotate(Node node) {
	//get node Left of node parameter
	Node nodeLeft = node.getLeft();
	//get node right from node left above
	Node nodeRightOfLeft = nodeLeft.getRight();

	//perform rotation
	//set right of node left to node parameter
	nodeLeft.setRight(node);
	//set left of node parameter is node right getted from node left
	node.setLeft(nodeRightOfLeft);

	//update height
	//update height of node parameter after rotate
	updateHeight(node);
	//update height of node left getted from node porameter after rotate
	updateHeight(nodeLeft);

	//return new root;
	return nodeLeft;
    }

    /**
     * Left rotate node and return new root has been rotated
     * 
     * @param node
     * @return 
     */
    private Node leftRotate(Node node) {
	//get node right of node parameter
	Node nodeRight = node.getRight();
	//get node left from node right above
	Node nodeLeftOfRight = nodeRight.getLeft();

	//perform rotation
	//set left of node right to node parameter
	nodeRight.setLeft(node);
	//set right of node parameter is node left getted from node right
	node.setRight(nodeLeftOfRight);

	//update height
	//update height of node parameter after rotate
	updateHeight(node);
	//update height of node right getted from node porameter after rotate
	updateHeight(nodeRight);

	//return new root
	return nodeRight;
    }

    /**
     * Minus height of node for check balance of tree
     *
     * @param node
     * @return number after minus height of node left and height of node right
     */
    private int checkBalance(Node node) {
	//if node parameter is null return 0
	if (node == null) {
	    return 0;
	}
	//return minus height of node left and right
	return getHeightOfNode(node.getLeft()) - getHeightOfNode(node.getRight());
    }

    /**
     * Insert a node
     *
     * @param node
     * @param user
     * @return node from balance method after inserted
     */
    private Node insert(Node node, User user) {
	int point = 0;	//create to save point from node parameter
	//get point from user of node parameter
	if (node != null) {
	    point = node.getUser().getPoint();
	}

	//perform comparing to insert
	if (node == null) { //this tree is empty so inserted to the root
	    return new Node(user);
	} else if (user.getPoint() < point) {  //compare to insert to node left
	    node.setLeft(insert(node.getLeft(), user));	    //using recursive to call function again
	} else if (user.getPoint() > point) {  //compare to insert to node right
	    node.setRight(insert(node.getRight(), user));   //using recursive to call function again
	} else {    //prevent error when duplicate email
	    throw new RuntimeException();
	}

	//call function rebalance to make AVLTree
	return rebalance(node);
    }

    /**
     * Balance tree after inserted
     *
     * @param node
     * @return node after inserted
     */
    private Node rebalance(Node node) {
	//first update height after inserted
	updateHeight(node);

	//check balance of tree inserted
	int balance = checkBalance(node);

	//perform rotation
	if (balance > 1) {  //the height of node left is more than right
	    //check is where make unbalance
	    /* Example: 
	    if(true)	  30		    else    30
			 /			    /
			20			  20
		       /			   \
		      10			   25
	     */
	    if (checkBalance(node.getLeft()) >= 0) {
		return rightRotate(node);
	    } else {
		node.setLeft(leftRotate(node.getLeft()));
		return rightRotate(node);
	    }
	} else if (balance < -1) {   //the height of node right is more than left
	    //check is where make unbalance
	    /* Example: 
	    if(true)	  30		    else    30
			    \			     \
			    40			     40
			     \			     /
			     50			    35
	     */
	    if (checkBalance(node.getRight()) <= 0) {
		return leftRotate(node);
	    } else {
		node.setRight(rightRotate(node.getRight()));
		return leftRotate(node);
	    }
	}

	//return the node unchange pointer
	return node;
    }

    /**
     * reBalanceV2 check base on point of user of each node
     *
     * @param node
     * @param user
     * @return node
     */
    private Node rebalance(Node node, User user) {
	//first update height after inserted
	updateHeight(node);

	//check balance of tree inserted
	int balance = checkBalance(node);

	//prevent null pointer
	int pointRight = 0;
	if (node.getRight() != null) {
	    pointRight = node.getRight().getUser().getPoint();
	}

	//prevent null pointer
	int pointLeft = 0;
	if (node.getLeft() != null) {
	    pointLeft = node.getLeft().getUser().getPoint();
	}

	//right rotation
	if (balance > 1 && user.getPoint() < pointLeft) {
	    return rightRotate(node);
	}

	//left rotation
	if (balance < -1 && user.getPoint() > pointRight) {
	    return leftRotate(node);
	}

	//left right rotation
	if (balance > 1 && user.getPoint() > pointLeft) {
	    node.setLeft(leftRotate(node.getLeft()));
	    return rightRotate(node);
	}

	//right left rotation
	if (balance < -1 && user.getPoint() < pointRight) {
	    node.setRight(rightRotate(node.getRight()));
	    return leftRotate(node);
	}

	//return the node unchange pointer
	return node;
    }

    /**
     * Function use for inserted. Using recursion technique to insert an user
     * Start form the root and call again
     *
     * @param user
     */
    public void insert(User user) {
	System.out.println("\n[INSERT] " + "(" + user.toString() + ")");
	setRoot(insert(getRoot(), user));
	inOrder(getRoot());
	System.out.println("");
	preOrder(getRoot());
    }

    /**
     * Traverse tree in preOder (NLR)
     *
     * @param node
     * @param writer
     */
    public void preOrder(Node node, PrintWriter writer) {
	if (node != null) {
	    //write to csv file
	    writer.println(node.getUser());
	    //left
	    preOrder(node.getLeft(), writer);
	    //right
	    preOrder(node.getRight(), writer);
	}
    }

    /**
     * Traverse tree in inOrder (LNR)
     *
     * @param node
     * @param writer
     */
    public void inOrder(Node node, PrintWriter writer) {
	if (node != null) {
	    //left
	    inOrder(node.getLeft(), writer);
	    //write to csv file
	    writer.println(node.getUser());
	    //right
	    inOrder(node.getRight(), writer);
	}
    }

    /**
     * Traverse tree in posOrder (LRN)
     *
     * @param node
     * @param writer
     */
    public void posOrder(Node node, PrintWriter writer) {
	if (node != null) {
	    //left
	    posOrder(node.getLeft(), writer);
	    //right
	    posOrder(node.getRight(), writer);
	    //write to csv file
	    writer.println(node.getUser());
	}
    }

    /**
     * Traverse tree in preOder (NLR) use for debug mode
     *
     * @param node
     */
    public void preOrder(Node node) {
	if (node != null) {
	    //node - mean visit so print it
	    System.out.print("(" + node.getUser() + ") ");
	    //left
	    preOrder(node.getLeft());
	    //right
	    preOrder(node.getRight());
	}
    }

    /**
     * Traverse tree in inOrder (LNR) use for debug mode
     *
     * @param node
     */
    public void inOrder(Node node) {
	if (node != null) {
	    //left
	    inOrder(node.getLeft());
	    //node - mean visit so print it
	    System.out.print("(" + node.getUser() + ") ");
	    //right
	    inOrder(node.getRight());
	}
    }

    /**
     * Traverse tree in posOrder (LRN) use for debug mode
     *
     * @param node
     */
    public void posOrder(Node node) {
	if (node != null) {
	    //left
	    posOrder(node.getLeft());
	    //right
	    posOrder(node.getRight());
	    //node - mean visit so print it
	    System.out.print("(" + node.getUser() + ") ");
	}
    }

    /**
     * Find a node by user inputed
     *
     * @param point
     * @return node founded
     */
    private Node findNode(int point) {
//	int point = user.getPoint();	//get point from user inpputed to compare

	Node result = getRoot();	//get root for start searching

	//searching
	while (result != null) {	//find still != null
	    if (point == result.getUser().getPoint()) {
		//match point then return result
		return result;
	    } else if (point < result.getUser().getPoint()) {	//point lower in left
		result = result.getLeft();
	    } else {						//point higher in right
		result = result.getRight();
	    }
	}
	//not founded
	return null;
    }

    /**
     * Find the parent of node founded
     *
     * @param node
     * @return father of noded founded
     */
    private Node findNodeParent(Node node) {
	//prevent user node founded so it won't have node father
	if (node == null) {
	    return null;
	}
	//get user of node founded
	User userChild = node.getUser();

	//get point
	int point = userChild.getPoint();

	//create node father
	Node father = null;
	//get root for searching
	Node result = getRoot();

	//searching
	while (result != null && result != node) {
	    //assigned the father
	    father = result;
	    //start for comparing like find node
	    //point is match but it just duplicate point and email not match
	    if (point < result.getUser().getPoint()) {	//point lower in left
		result = result.getLeft();
	    } else {						//point higher in right
		result = result.getRight();
	    }
	}
	//return father founded or it is root it will return null by assigned father = null above
	return father;
    }

    /**
     * Testing for searching node
     *
     * @param point
     * @return
     */
    public User findNodeUser(int point) {
	Node node = findNode(point);
	if (node != null) {
	    return node.getUser();
	}
	return null;
    }

    /**
     * Testing for searching father node
     *
     * @param point
     * @return
     */
    public User findNodeUserParent(int point) {
	Node node = findNode(point);
	Node nodeFather = findNodeParent(node);
	if (nodeFather != null) {
	    return nodeFather.getUser();
	}
	return null;
    }

    /**
     * Dekete by merging left Not used now
     *
     * @param point
     */
    public void deleteByMergingLeft(int point) {
	//node founded
	Node nodeFinded = findNode(point);
	//create node to reserve value
	Node nodeReserve = nodeFinded;
	//node parent
	Node nodeParent = findNodeParent(nodeFinded);
	//node temp to store the rightmost node of left child node
	Node tmp = null;
	//node father of tmp for rebalance
	Node tmpFatherRebalance;

	boolean stopBalance = false;

	//start delete (if node finded is null or not equal with user inputted
	//out of start delete)
	if (nodeFinded != null && nodeFinded.getUser().getPoint() == point) {
	    //check if node have only one left child
	    if (nodeReserve.getRight() == null) {
		//tick left child to parent node
		//move node to node left for tick previous
		nodeReserve = nodeReserve.getLeft();
		//check if node have only one right child
	    } else if (nodeReserve.getLeft() == null) {
		//tick right child to parent node
		//move node to node right for tick previous
		nodeReserve = nodeReserve.getRight();
		//node have 2 child
	    } else {
		//find rightmost
		tmp = nodeReserve.getLeft();
		while (tmp.getRight() != null) {
		    tmp = tmp.getRight();
		}
		//tick right node to right temp node
		//because they have higher value
		tmp.setRight(nodeReserve.getRight());
		//keep node.left for change in previous(nooe reserve become left
		//child of node delete)
		//node finded still equal node to be deleted
		nodeReserve = nodeReserve.getLeft();
	    }

	    //this case is node to be deleted is root
	    if (nodeFinded == getRoot()) {
		//so set root is the tree result
		setRoot(nodeReserve);
		//this case is node to be deleted is child left of parent
	    } else if (nodeParent.getLeft() == nodeFinded) {
		nodeParent.setLeft(nodeReserve);
		//this case is node to be deleted is child right of parent
	    } else {
		nodeParent.setRight(nodeReserve);
	    }

	    //node have 2 child
	    if (nodeReserve != null && tmp != null) {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(tmp);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(tmp);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (tmp == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (tmp == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    tmp = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
		//the orthe case like no child
	    } else if (nodeReserve == null && tmp == null) {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(nodeParent);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(nodeParent);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (nodeParent == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (nodeParent == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    nodeParent = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
		//the other case like 1 child
	    } else {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(nodeReserve);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(nodeReserve);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (tmp == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (tmp == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    nodeReserve = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
	    }
	} else if (getRoot() != null) {
	    System.out.println("User is not in the tree");
	} else {
	    System.out.println("The tree is empty");
	}
    }

    /**
     * Delete by copy right
     *
     * @param point
     */
    public void deleteByCopyingRight(int point) {
	//node founded
	Node nodeFinded = findNode(point);
	//node parent
	Node nodeParent = findNodeParent(nodeFinded);
	//create node to reserve value
	Node nodeReserve = nodeFinded;
	//node temp to store the rightmost node of left child node
	Node previous = null;
	//node father of tmp for rebalance
	Node tmpFatherRebalance;

	boolean stopBalance = false;

	if (nodeFinded != null && nodeFinded.getUser().getPoint() == point) {
	    //check if node have only one left child
	    if (nodeReserve.getRight() == null) {
		//tick left child to parent node
		//move node to node left for tick previous
		nodeReserve = nodeReserve.getLeft();
		//check if node have only one right child
	    } else if (nodeReserve.getLeft() == null) {
		//tick right child to parent node
		//move node to node right for tick previous
		nodeReserve = nodeReserve.getRight();
		//node have 2 child
	    } else {
		previous = nodeReserve;
		Node tmp = nodeReserve.getLeft();
		while (tmp.getRight() != null) {
		    previous = tmp;
		    tmp = tmp.getRight();
		}

		//copy user of rightmost set to node to be deleted
		nodeReserve.setUser(tmp.getUser());
		//node left not have rightmost
		if (previous == nodeReserve) {
		    previous.setLeft(tmp.getLeft());
		    //node right have left child
		} else {
		    //node right have left child. previous have left child
		    //set temp.left (have higher value) to right
		    //previous (have lower value)
		    previous.setRight(tmp.getLeft());
		}
	    }

	    //if node is deleted
	    if (nodeFinded == getRoot()) {
		//so set root is the tree result
		setRoot(nodeReserve);
		//this case is node to be deleted is child left of parent
	    } else if (nodeParent.getLeft() == nodeFinded) {
		nodeParent.setLeft(nodeReserve);
		//this case is node to be deleted is child right of parent
	    } else {
		nodeParent.setRight(nodeReserve);
	    }

	    //node have 2 child
	    if (nodeReserve != null && previous != null) {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(previous);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(previous);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (previous == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (previous == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    previous = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
		//the orthe case like no child
	    } else if (nodeReserve == null && previous == null) {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(nodeParent);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(nodeParent);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (nodeParent == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (nodeParent == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    nodeParent = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
		//the other case like 1 child
	    } else {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(nodeReserve);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(nodeReserve);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (nodeReserve == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (nodeReserve == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    nodeReserve = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
	    }
	} else if (getRoot() != null) { //user not in the tree
	    System.out.println("User is not in the tree");
	} else {
	    System.out.println("The tree is empty");
	}
    }

    /**
     * Delete by copy left
     *
     * @param point
     */
    public void deleteByCopyingLeft(int point) {
	//node founded
	Node nodeFinded = findNode(point);
	//node parent
	Node nodeParent = findNodeParent(nodeFinded);
	//create node to reserve value
	Node nodeReserve = nodeFinded;
	//node temp to store the rightmost node of right child node
	Node previous = null;
	//node father of tmp for rebalance
	Node tmpFatherRebalance;

	boolean stopBalance = false;

	if (nodeFinded != null && nodeFinded.getUser().getPoint() == point) {
	    //check if node have only one left child
	    if (nodeReserve.getRight() == null) {
		//tick left child to parent node
		//move node to node left for tick previous
		nodeReserve = nodeReserve.getLeft();
		//check if node have only one right child
	    } else if (nodeReserve.getLeft() == null) {
		//tick right child to parent node
		//move node to node right for tick previous
		nodeReserve = nodeReserve.getRight();
		//node have 2 child
	    } else {
		previous = nodeReserve;
		Node tmp = nodeReserve.getRight();
		while (tmp.getLeft() != null) {
		    previous = tmp;
		    tmp = tmp.getLeft();
		}

		//copy user of rightmost set to node to be deleted
		nodeReserve.setUser(tmp.getUser());
		//node right not have leftmost
		if (previous == nodeReserve) {
		    previous.setRight(tmp.getRight());
		    //node left have right child
		} else {
		    //node left have right child. previous have right child
		    //set temp.right (have lower value) to left
		    //previous (have higher  value)
		    previous.setLeft(tmp.getRight());
		}
	    }

	    //if node is deleted
	    if (nodeFinded == getRoot()) {
		//so set root is the tree result
		setRoot(nodeReserve);
		//this case is node to be deleted is child left of parent
	    } else if (nodeParent.getLeft() == nodeFinded) {
		nodeParent.setLeft(nodeReserve);
		//this case is node to be deleted is child right of parent
	    } else {
		nodeParent.setRight(nodeReserve);
	    }

	    //node have 2 child
	    if (nodeReserve != null && previous != null) {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(previous);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(previous);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (previous == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (previous == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    previous = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
		//the orthe case like no child
	    } else if (nodeReserve == null && previous == null) {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(nodeParent);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(nodeParent);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (nodeParent == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (nodeParent == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    nodeParent = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
		//the other case like 1 child
	    } else {
		do {
		    //so get up to next rebalance till get to root
		    tmpFatherRebalance = findNodeParent(nodeReserve);
		    //rebalance this tree or update height if it is balanced
		    Node nodeResult = rebalance(nodeReserve);

		    //set root again because this time rotate at root
		    //if not you will start wrong because root in the left or right
		    //of node should be root
		    if (tmpFatherRebalance == null) {
			setRoot(nodeResult);
			//pause rotate when reach to root
			stopBalance = true;
			//set father to node have been rotate
		    } else if (nodeReserve == tmpFatherRebalance.getRight()) {
			tmpFatherRebalance.setRight(nodeResult);
		    } else if (nodeReserve == tmpFatherRebalance.getLeft()) {
			tmpFatherRebalance.setLeft(nodeResult);
		    }

		    //move this node to their parent to next rebalance
		    nodeReserve = tmpFatherRebalance;
		    //go till to the root to rebalance each other nood
		} while (!stopBalance);
	    }
	} else if (getRoot() != null) { //user not in the tree
	    System.out.println("User is not in the tree");
	} else {
	    System.out.println("The tree is empty");
	}
    }
}
