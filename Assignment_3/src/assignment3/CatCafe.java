package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CatCafe implements Iterable < Cat > {
	public CatNode root;

	public CatCafe() {}

	public CatCafe(CatNode dNode) {
		this.root = dNode;
	}

	// Constructor that makes a shallow copy of a CatCafe
	// New CatNode objects, but same Cat objects
	public CatCafe(CatCafe cafe) {
		/*
		 * TODO: ADD YOUR CODE HERE
		 */
		CatNode original = cafe.root;

		if (original == null) {
			return; //if current cafe root is null, we are done; base case
		}

		this.root = new CatNode(original.catEmployee);
		CatNode clone = this.root;

		while (original != null) {
			if (original.junior != null && clone.junior == null) {
				clone.junior = new CatNode(original.junior.catEmployee);
				original = original.junior;
				clone.junior.parent = clone;
				clone = clone.junior;
			} else if (original.senior != null && clone.senior == null) {
				clone.senior = new CatNode(original.senior.catEmployee);
				original = original.senior;
				clone.senior.parent = clone;
				clone = clone.senior;
			} else {
				original = original.parent;
				clone = clone.parent;
			}
		}
	}

	// add a cat to the cafe database
	public void hire(Cat c) {
		if (root == null)
			root = new CatNode(c);
		else
			root = root.hire(c);
	}

	// removes a specific cat from the cafe database
	public void retire(Cat c) {
		if (root != null)
			root = root.retire(c);
	}

	// get the oldest hire in the cafe
	public Cat findMostSenior() {
		if (root == null)
			return null;

		return root.findMostSenior();
	}

	// get the newest hire in the cafe
	public Cat findMostJunior() {
		if (root == null)
			return null;

		return root.findMostJunior();
	}

	// returns a list of cats containing the top numOfCatsToHonor cats
	// in the cafe with the thickest fur. Cats are sorted in descending
	// order based on their fur thickness.
	public ArrayList < Cat > buildHallOfFame(int numOfCatsToHonor) { //edge case proof
		/*
		 * TODO: ADD YOUR CODE HERE
		 */
		ArrayList < Cat > allCats = new ArrayList < Cat > ();
		ArrayList < Cat > catsOfHonor = new ArrayList < Cat > ();
		if (numOfCatsToHonor == 0) {
			return catsOfHonor;
		}
		//gets an ArrayList
		for (Cat cat: this) { //works cuz we implemented iterator correctly :)
			allCats.add(cat); //fill copy arraylist to manipulate later
		}
		int n = 0;
		int initialSize = allCats.size();
		while (n < numOfCatsToHonor && n < initialSize) {
			Cat max = allCats.get(0);
			for (Cat allCat: allCats) {
				if (allCat.getFurThickness() > max.getFurThickness()) {
					max = allCat;
				}
			} //finds current max fur thickness
			catsOfHonor.add(max);
			allCats.remove(max);
			n++;
		}
		return catsOfHonor;
	}

	// Returns the expected grooming cost the cafe has to incur in the next numDays days
	public double budgetGroomingExpenses(int numDays) {
		/*
		 * TODO: ADD YOUR CODE HERE
		 */
		double cost = 0.0;
		ArrayList < Cat > allCats = new ArrayList < Cat > ();
		ArrayList < Cat > catsToGroom = new ArrayList < Cat > ();
		for (Cat cat: this) {
			allCats.add(cat); //fill copy arraylist to manipulate later
		}
		for (Cat catty: allCats) {
			if (catty.getDaysToNextGrooming() <= numDays) {
				catsToGroom.add(catty);
			}
		}
		for (Cat kitty: catsToGroom) {
			cost += kitty.getExpectedGroomingCost();
		}
		return cost;
	}

	// returns a list of list of Cats.
	// The cats in the list at index 0 need be groomed in the next week.
	// The cats in the list at index i need to be groomed in i weeks.
	// Cats in each sublist are listed in from most senior to most junior.
	public ArrayList < ArrayList < Cat >> getGroomingSchedule() {
		/*
		 * TODO: ADD YOUR CODE HERE
		 */
		ArrayList < Cat > allCats = new ArrayList < Cat > ();
		for (Cat cat: this) {
			allCats.add(cat); //fill copy arraylist to manipulate later; already sorted by seniority
		}
		if (allCats.size() == 0) { //edge case handling
			return new ArrayList < ArrayList < Cat > >();
		}
		int maxWeek = allCats.get(0).getDaysToNextGrooming()/7; //if /7 = 0; this week; /7 = 1; 1 week from now
		for (Cat catty: allCats) { //already in seniority order
			int newWeek = catty.getDaysToNextGrooming()/7;
			if (newWeek > maxWeek) {
				maxWeek = newWeek;
			}
		}
		ArrayList < ArrayList < Cat >> schedule = new ArrayList < >();//total number of arraylists needed
		for (int i = 0; i <= maxWeek; i++) {
			ArrayList<Cat> row = new ArrayList<Cat>();
			schedule.add(row);
		}
		for (Cat kitty: allCats) {
			schedule.get(kitty.getDaysToNextGrooming()/7).add(kitty);
		}
		return schedule;
	}

	public Iterator < Cat > iterator() {
		return new CatCafeIterator();
	}

	public class CatNode {
		public Cat catEmployee;
		public CatNode junior;
		public CatNode senior;
		public CatNode parent;
		//public CatNode theRightOne;

		public CatNode(Cat c) {
			this.catEmployee = c;
			this.junior = null;
			this.senior = null;
			this.parent = null;
		}

		// add the c to the tree rooted at this and returns the root of the resulting tree
		private CatNode add(Cat c) {
			if (c.getMonthHired() > this.catEmployee.getMonthHired()) {
				if (this.junior == null) {
					this.junior = new CatNode(c);
					this.junior.parent = this;
					return this.junior;
				} else {
					return this.junior.add(c);
				}
			} else if (c.getMonthHired() < this.catEmployee.getMonthHired()) {
				if (this.senior == null) {
					this.senior = new CatNode(c);
					this.senior.parent = this;
					return this.senior;
				} else {
					return this.senior.add(c);
				}
			}
			return null;
		}
		private CatNode rightRot(CatNode A) {
			CatNode P = A.parent;
			CatNode B = A.junior;
			A.junior = B.senior;
			if (B.senior != null) {
				B.senior.parent = A;
			}
			B.senior = A;
			A.parent = B;
			B.parent = P;
			if (P != null) {
				if (P.junior == A) {
					P.junior = B;
				} else {
					P.senior = B;
				}
			}
			return B;
		}
		private CatNode leftRot(CatNode A) {
			CatNode P = A.parent;
			CatNode B = A.senior;
			A.senior = B.junior;
			if (B.junior != null) {
				B.junior.parent = A;
			}
			B.junior = A;
			A.parent = B;
			B.parent = P;
			if (P != null) {
				if (P.senior == A) {
					P.senior = B;
				} else {
					P.junior = B;
				}
			}
			return B;
		}
		public CatNode hire(Cat c) {
			/*
			 * TODO: ADD YOUR CODE HERE
			 */
			CatNode temp = add(c); //the node we just inserted at the bottom
			if (temp == null) {
				return null;
			}
			if (temp.parent == null) {
				return temp;
			}
			while (temp.parent != null && temp.catEmployee.getFurThickness() > temp.parent.catEmployee.getFurThickness()) {
				if (temp.parent.junior == temp) { //if temp is the left child
					temp = rightRot(temp.parent);
				} else if (temp.parent.senior == temp) { //RC
					temp = leftRot(temp.parent);
				}
			}
			while (temp.parent != null) {
				temp = temp.parent;
			}
			return temp;
		}

		// remo//		private CatNode remove(CatNode curr, Cat c) {
		////			if (curr == null) {
		////				return null;
		////			} else if (c.getMonthHired() > curr.catEmployee.getMonthHired()) { //if the one we're looking for is more junior than curr
		////				curr.junior = remove(curr.junior, c);
		////			} else if (c.getMonthHired() < curr.catEmployee.getMonthHired()) {
		////				curr.senior = remove(curr.senior, c);
		////			} else if (curr.junior == null) {
		////				curr = curr.senior;
		////			} else if (curr.senior == null) {
		////				curr = curr.junior;
		////			} else {
		////				curr.catEmployee = curr.junior.findMostSenior();
		////				curr = remove(curr.junior, curr.catEmployee);
		////			}
		////			return curr; //returns the node that we will ultimately replace c with
		////		}ve c from the tree rooted at this and returns the root of the resulting tree
		public CatNode retire(Cat c) {
			/*
			 * TODO: ADD YOUR CODE HERE
			 */
			if (c == null) { //if we're not retiring an actual cat, do nothing
				return root;
			}
			CatNode now = root;
			if (now.catEmployee == c && now.junior == null && now.senior == null) { //if we're removing the root
				//if both children null we just need to get rid of now
				root = null;
				now = null;
				return root;
			}
			while (now != null && now.catEmployee != c) {//finding the correct node to remove
				if (now.catEmployee.getMonthHired() > c.getMonthHired()) {
					now = now.senior;
				}
				else if (now.catEmployee.getMonthHired() < c.getMonthHired()) {
					now = now.junior;
				}
			} //now is on the cat we need to remove
			if (now == null) { // null do nothing idk if its useful but whatever
				return this;
			}
			else if (now.junior == null && now.senior == null) {//if we're removing a node with no children
				//if both children null we just need to get rid of now
				if (now == root) {
					root = null;
				}
				if (now.parent.junior == now) {
					now.parent.junior = null;
				}
				else if (now.parent.senior == now) {
					now.parent.senior = null;
				}
				now.parent = null;
				now = null;

				return this; //changed from "return root" cuz Sana's tester accounts for the node that got returned.
			}
			else if (now.junior == null) { //edge case where the guy to retire only has right children
				CatNode kitty = now.senior; //the one we need to replace now with
				if (kitty.parent == root) {//make senior cat the new root; no rotations needed; kitty.parent same as now now
					root = kitty;
					kitty.parent.senior = null;
					kitty.parent = null;
					now = null;
					return root;
				}
				else {//one we are removing is not the root
					if (now.parent.senior == now) {//if now is a senior child
						now.parent.senior = now.parent.senior.senior;
						kitty.parent= now.parent;
						now.senior = null;
						now.parent = null;
						now = null;
					}
					else if ((now.parent.junior == now)) {
						now.parent.junior = now.parent.junior.senior;
						kitty.parent = now.parent;
						now.senior = null;
						now.parent = null;
						now = null;
					}
					return root;
				}
			}
			else if (now.senior == null) { //edge case where the guy to retire only has right children
				CatNode kitty = now.junior; //the one we need to replace now with
				if (kitty.parent == root) {//make senior cat the new root; no rotations needed; kitty.parent same as now now
					root = kitty;
					kitty.parent.junior = null;
					kitty.parent = null;
					now = null;
					return root;
				}
				else {//one we are removing is not the root
					if (now.parent.senior == now) {//if now is a senior child
						now.parent.senior = now.parent.senior.junior;
						kitty.parent= now.parent;
						now.junior = null;
						now.parent = null;
						now = null;
					}
					else if ((now.parent.junior == now)) {//if now is junior child
						now.parent.junior = now.parent.junior.junior;
						kitty.parent = now.parent;
						now.junior = null;
						now.parent = null;
						now = null;
					}
					return root;
				}
			}
			else {//have all children and might have to do some binary tree rotation
				Cat kitty = now.junior.findMostSenior();
				CatNode swap = root;
				while (swap != null && swap.catEmployee != kitty) {
					if (swap.catEmployee.getMonthHired() > kitty.getMonthHired()) {
						swap = swap.senior;
					} else if (swap.catEmployee.getMonthHired() < kitty.getMonthHired()) {
						swap = swap.junior;
					}
				} //swap now refers to the cat that should take the place of now
				boolean chRoot = false;
				if (now == root) {
					chRoot = true;
				}
				if (swap != null && now != null && now.junior == swap) {//special case where swap is the first node down from the one we're removing
					swap.parent = now.parent;
					swap.junior = now.junior.junior;
					swap.senior = now.senior;
					swap.senior.parent = swap;
					if (chRoot) {
						root = swap;
					}
					now.parent = null;
					now.junior = null;
					now.senior = null;
					now = null;
				}
				else if (swap != null) {//normal case; swap from  afar
					swap.parent.senior = null; //cuz swap is always the senior child
					swap.parent = now.parent;
					swap.junior = now.junior;
					swap.junior.parent = swap;
					swap.senior = now.senior;
					swap.senior.parent = swap;
					if (chRoot) {
						root = swap;
					}
					now.parent = null;
					now.junior = null;
					now.senior = null;
					now = null;
				}
				CatNode curr = swap;//replace c with the correct node and return the new c replacement
				CatNode murr = curr;
				while ((curr.junior != null || curr.senior != null) && (murr != null)) {
					//if both are bigger
					//if  (curr.junior.catEmployee.getFurThickness() > curr.catEmployee.getFurThickness() || curr.senior.catEmployee.getFurThickness() > curr.catEmployee.getFurThickness())
					if (curr.junior != null && curr.senior != null && curr.junior.catEmployee.getFurThickness() > curr.senior.catEmployee.getFurThickness()) {
						//if junior is bigger than senior
						curr = rightRot(curr);
						curr = curr.senior;
					} else if (curr.senior != null && curr.junior != null && curr.senior.catEmployee.getFurThickness() > curr.junior.catEmployee.getFurThickness()) {
						//if senior is bigger than junior
						curr = leftRot(curr);
						curr = curr.junior;
					}
					else if (curr.senior != null && curr.junior == null && curr.senior.catEmployee.getFurThickness() > curr.catEmployee.getFurThickness()) {
						curr = leftRot(curr);
						curr = curr.junior;
					}
					else if (curr.junior != null && curr.senior == null && curr.junior.catEmployee.getFurThickness() > curr.catEmployee.getFurThickness()) {
						curr = rightRot(curr);
						curr = curr.senior;
					}
					else {
						murr = murr.junior;
					}
				}
				while (curr != this && curr.parent != null) {
					curr = curr.parent;
				}
				return curr;
			}
		}

		// find the cat with highest seniority in the tree rooted at this
		public Cat findMostSenior() { //null edge case proof
			/*
			 * TODO: ADD YOUR CODE HERE
			 */
			if (this == null) {
				return null;
			} else if (this.senior == null) {
				return this.catEmployee;
			} else {
				return this.senior.findMostSenior(); // effectively, this.senior is the new
			}
		}

		// find the cat with lowest seniority in the tree rooted at this
		public Cat findMostJunior() { //null edge case proof
			/*
			 * TODO: ADD YOUR CODE HERE.
			 */
			if (this == null) {
				return null;
			} else if (this.junior == null) {
				return this.catEmployee;
			} else {
				return this.junior.findMostJunior();
			}
		}

		// Feel free to modify the toString() method if you'd like to see something else displayed.
		public String toString() {
			String result = this.catEmployee.toString() + "\n";
			if (this.junior != null) {
				result += "junior than " + this.catEmployee.toString() + " :\n";
				result += this.junior.toString();
			}
			if (this.senior != null) {
				result += "senior than " + this.catEmployee.toString() + " :\n";
				result += this.senior.toString();
			}
      /*
			if (this.parent != null) {
				result += "parent of " + this.catEmployee.toString() + " :\n";
				result += this.parent.catEmployee.toString() +"\n";
			}*/
			return result;
		}
	}

	private class CatCafeIterator implements Iterator < Cat > {
		// HERE YOU CAN ADD THE FIELDS YOU NEED
		private ArrayList < Cat > catsInOrder = new ArrayList < Cat > ();
		private int currCat = 0;
		private int listSize = 0;
		private void inOrder(CatNode node) { //works for null edge cases; all good
			if (node == null) {
				return;
			}
			inOrder(node.junior);
			catsInOrder.add(node.catEmployee);
			inOrder(node.senior);
		}
		private CatCafeIterator() { //inOrder is null edge case proof; so even if curr is null, all good
			/*
			 * TODO: ADD YOUR CODE HERE
			 */
			CatNode curr = CatCafe.this.root;
			inOrder(curr);
			listSize = catsInOrder.size(); //size can be 0 if no elements were added
		}

		public Cat next() {
			/*
			 * TODO: ADD YOUR CODE HERE
			 */
			if (currCat >= listSize) { //say list size is 0, currCat >= 0 is true, so exception will be thrown
				throw new NoSuchElementException("No more cats left to iterate through!");
			}
			return catsInOrder.get(currCat++);
		}

		public boolean hasNext() {
			/*
			 * TODO: ADD YOUR CODE HERE
			 */
			if (currCat < listSize) return true; //0 < 0 is false, which is expected when we have 0 element array
			else return false;
		}

	}

	//dispose of these when submitting vv
	private static void iteratorTest() {
		Cat B = new Cat("Buttercup", 45, 53, 5, 85.0);
		Cat C = new Cat("Chessur", 8, 23, 2, 250.0);
		Cat J = new Cat("Jonesy", 0, 21, 12, 30.0);
		Cat JJ = new Cat("JIJI", 156, 17, 1, 30.0);
		CatCafe Cafe = new CatCafe();
		Cafe.hire(B);
		Cafe.hire(C);
		Cafe.hire(J);
		Cafe.hire(JJ);
		CatCafe pafe = new CatCafe(Cafe);
		Iterator itr = pafe.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next().toString());
		}
		try {
			System.out.println(itr.next().toString());
		} catch (NoSuchElementException e) {
			System.out.println("No such element exists. The previous element has a next cat: " + itr.hasNext());
		}
	}
	private static void printTree(CatNode root, int spaceCount) {
		if (root == null)
			return;

		int spacing = spaceCount + 24;

		printTree(root.senior, spacing);

		System.out.println();
		for (int index = 0; index < spacing + root.catEmployee.toString().length() + 1; index++) {
			System.out.print(" ");
		}
		if (root.senior != null) {
			System.out.print("/");
			System.out.println();
			for (int index = 0; index < spacing + root.catEmployee.toString().length(); index++) {
				System.out.print(" ");
			}
			if (root.senior != null) {
				System.out.print("/");
			}
		}

		System.out.println();
		for (int index = 0; index < spacing; index++) {
			System.out.print(" ");
		}
		System.out.println(root.catEmployee);

		for (int index = 0; index < spacing + root.catEmployee.toString().length(); index++) {
			System.out.print(" ");
		}
		if (root.junior != null) {
			System.out.print("\\");
			System.out.println();
			for (int index = 0; index < spacing + root.catEmployee.toString().length() + 1; index++) {
				System.out.print(" ");
			}
			if (root.junior != null) {
				System.out.print("\\");
			}
		}
		printTree(root.junior, spacing);
	}

	public static void main(String[] args) {
//		Cat B = new Cat("Buttercup", 45, 53, 5, 85.0);
//		Cat C = new Cat("Chessur", 8, 23, 2, 250.0);
//		Cat J = new Cat("Jonesy", 0, 21, 12, 30.0);
//		Cat JJ = new Cat("JIJI", 156, 17, 1, 30.0);
//		Cat JTO = new Cat("J. Thomas O'Malley", 21, 10, 9, 20.0);
//		Cat MrB = new Cat("Mr. Bigglesworth", 71, 0, 31, 55.0);
//		Cat MrsN = new Cat("Mrs. Norris", 100, 68, 15, 115.0);
//		Cat T = new Cat("Toulouse", 180, 37, 14, 25.0);
//		Cat BC = new Cat("Blofeld's cat", 6, 72, 18, 120.0);
//		Cat L = new Cat("Lucifer", 10, 44, 20, 50.0);

		Cat A = new Cat("A", 20, 40, 5, 85.0);
		Cat B = new Cat("B", 15, 20, 2, 250.0);
		Cat C = new Cat("C", 18, 10, 12, 30.0);
		Cat D = new Cat("D", 12, 5, 5, 85.0);
		Cat E = new Cat("E", 22, 39, 9, 20.0);
		Cat F = new Cat("F", 25, 48, 9, 20.0);
		Cat G = new Cat("G", 28, 45, 9, 20.0);
		Cat H = new Cat("H", 60, 28, 9, 20.0);
		Cat I = new Cat("I", 50, 50, 9, 20.0);
		Cat J = new Cat("J", 70, 18, 9, 20.0);
		Cat K = new Cat("K", 55, 20, 9, 20.0);
		Cat L = new Cat("L", 59, 10, 9, 20.0);
		Cat M = new Cat("M", 62, 25, 9, 20.0);

		CatCafe catCafe = new CatCafe();
		catCafe.hire(A);
		catCafe.hire(B);
		catCafe.hire(C);
		catCafe.hire(D);
		catCafe.hire(E);
		catCafe.hire(F);
		catCafe.hire(G);
		catCafe.hire(H);
		catCafe.hire(I);
		catCafe.hire(J);
		catCafe.hire(K);
		catCafe.hire(L);
		catCafe.hire(M);
		printTree(catCafe.root, 1);
		System.out.println("\n\n\n\n\n\n\n\n");
		CatCafe.CatNode nodeReturned = catCafe.root.junior.retire(H);
		printTree(catCafe.root, 1);
		System.out.println("\n\n\n\n\n\n\n\n");

	}
	//probably this one too ^^

//	public static void main(String[] args) {
//		Cat B = new Cat("Buttercup", 45, 53, 5, 85.0);
//		Cat C = new Cat("Chessur", 8, 23, 2, 250.0);
//		Cat J = new Cat("Jonesy", 0, 21, 12, 30.0);
//		Cat JJ = new Cat("JIJI", 156, 17, 1, 30.0);
//		Cat JTO = new Cat("J. Thomas O'Malley", 21, 10, 9, 20.0);
//		Cat MrB = new Cat("Mr. Bigglesworth", 71, 0, 31, 55.0);
//		Cat MrsN = new Cat("Mrs. Norris", 100, 68, 15, 115.0);
//		Cat T = new Cat("Toulouse", 180, 37, 14, 25.0);
//
//
//		Cat BC = new Cat("Blofeld's cat", 6, 72, 18, 120.0);
//		Cat L = new Cat("Lucifer", 10, 44, 20, 50.0);
//
//	}
}