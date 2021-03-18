# SE1420-CSD201

## Lab 2
## SE140054 - Tran Nhat Minh

### Build an AVL tree from BST Tree to store email and point of User

#### AVL Tree have 4 main function:
	+ Insert
	+ Delete
	+ Update
	+ Print tree (Pre Order (NLR), In Order (LNR), Post Order (LRN))

##### AVL Tree have four case to rotation (left, right, left right, right left) after insert a node if it isn't balance
	+ Create function to rotate
		+ Base on the height of node left minus height of node right by function checkBalance()
		+ If result of minus is higher than 1 or lower than -1 so the tree isn't balance
		+ Go to node left if higher than 1 and call checkBalance() function again
		+ If result higher than 0 is the left right case else right case only
		+ Also do it the same as result is lower than 1
		
##### AVL Tree delete have delete by copy (left, right)
	+ Create function delete by copy right
		+ Copy right
		+ Find node by point need to delete and it's parent
		+ Go left of node finded and find rightmost for copy
		+ After copy the data of user of rightmost node and set to node to be deleted
		+ Go balance from the node have copied data to make sure tree don't become unbalance
	+ Create function delete by copy left
		+ Copy left
		+ Find node by point need to delete and it's parent
		+ Go right of node finded and find leftmost for copy
		+ After copy the data of user of leftmost node and set to node to be deleted
		+ Go balance from the node have copied data to make sure tree don't become unbalance

##### AVL Tree update user
	+ Create function update and get mode for delete is copy right or copy left
		+ Using HashMap for find the user from csv file easier
		+ Get point to find node need to update
		+ Remove user have been update from tree
		+ Insert again to make sure it in the right position
