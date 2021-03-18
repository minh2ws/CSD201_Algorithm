# SE1420-CSD201

# Lab 1
# SE140054 - Tran Nhat Minh

## Exercise 1:
	+ Create an Priority Queue based on Doubly LinkedList to store data of user
	+ First insert an user
		+ Check if list empty -> add first
		+ Check for top and last if list not empty than if it higher then top add first or lower than last
		+ If the first is wrong so check each element in Priority Queue if found the user have point lower than user insert so insert in front of user lower
	+ Second update an user
		+ Get the user founded by email from the Priority Queue
		+ Edit it by set point again
		+ After set point take it out of Priority Queue and than add it again to make sure it not wrong in the Priority Queue
	+ Third getTop and removeTop
		+ Get the user of the top (which have next of the header) return point or remove it and set header to the next or this point.
	+ Finally write it to CSV from each of method called in CLI
		+ Write base on format Email, Point
## Exercise 2:
	+ First read all HTML downloaded from URL and than convert it so String
	+ Second read each character
		+ If character macth '<' so start to create an tag and when character macth '>' close this tag
		+ Use StringTokenizer to seperate tag and use trim to prevent enter ('/n')
		+ Check each tag for remove like href ... by using regex "^[a-zA-Z0-9'/''<''>']+$"
	+ Third using stack to check open Tag and close Tag
		+ If is open push it to stack and when it's close Tag pop open Tag from stack for comparing, but convert open Tag to close Tag if each equal put to hashMap for easing increase value
		+ If stack check already exist in hashMap just increase value
	+ Four using contain method in String Libary to check special Tag like '/>'
		+ When tag readed from +Second check it if it contain '/>' so seperate it to normal tag and append '>' behind and put it to hashMap
		+ If stack check already exist in hashMap just increase value
- Main class:
	+ Prepare CLI
	+ Remove some behind CLI make sure not make exception
