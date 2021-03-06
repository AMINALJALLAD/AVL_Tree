import java.util.ArrayList;

public class AVL {
	//static int numOfNode;
//	ArrayList<Leaf> snapshot;
	Leaf next;
	Leaf remembered;
	int rootHeightPrevious; // This value for printing the tree before updating the height of the previous root as the range of the array is related to this value
	int max;
	boolean debug;
	private class Leaf {
		Integer key;
		Leaf leftChild ;
		Leaf rightChild;
		int height;
		Leaf parent;
		
		Leaf(Integer key_){
			key = key_;
			height = 1;
			parent = null;
			leftChild = null;
			rightChild = null;
			parent = null;
		}
		
		Leaf(Leaf Leaf){
			key = Leaf.key;
		}
		
		Leaf(){
			key = null;
		}
		
		public String toString() {
			return "Key " + key  +", and left_child  " + ((leftChild == null) ? "null": leftChild.key ) + ", and right_child is " + ((rightChild == null ) ? "null": rightChild.key )+
					", heigh " + height ;//+ "parent " + parent;
		}
	}
	Leaf root;
	public AVL() {
		root = null;
		
	}
	
	public boolean hasLeftChild() {
		return root.leftChild != null;
	}
	
	public boolean hasRightChild() {
		return root.rightChild != null;
	}
	
	public ArrayList<String> allkeys() {
		ArrayList<String> keys = new ArrayList<String>();
		inOrderReading();
		return keys;
	}
	/*
	 * A method to look for a node by giving a specific key
	 * and the root node. It will return the node that contain given key.
	 * Otherwise, it will return null
	 */
	public Leaf search(Leaf traverse, Integer key) {
		if(key.equals(traverse.key)) {
			return traverse;
		}else if (key.compareTo(traverse.key) > 0) {
			if(hasRightChild(traverse)) {
				return search(traverse.rightChild, key);
			}else {
				return null;
			}
		}else {   // node < traverse
			if(hasLeftChild(traverse)) {
				return search(traverse.leftChild, key);
			}else {
				return null;
			}
		}
	}
	
	/*
	 * A method to look for a node by giving a reference of required node
	 * and the root node. It will return the node that contain given key.
	 * Otherwise, it will return null
	 */
	public Leaf search(Leaf traverse, Leaf node) {
		if(node.key.equals(traverse.key)) {
			return traverse;
		}else if (node.key.compareTo(traverse.key) > 0) {
			if(hasRightChild(traverse)) {
				return search(traverse.rightChild, node);
			}else {
				return traverse;
			}
		}else {   // node < traverse
			if(hasLeftChild(traverse)) {
				return search(traverse.leftChild, node);
			}else {
				return traverse;
			}
		}
	}
	
	/*
	 * A method to check if the given node has a left child. It will return true 
	 * if left child isn't null. Otherwise, it returns false
	 */
	public boolean hasLeftChild(Leaf vertex) {
		return (vertex.leftChild != null);
	}
	
	/*
	 * A method to check if the given node has a right child. It will return true 
	 * if right child isn't null. Otherwise, it returns false
	 */
	public boolean hasRightChild(Leaf vertex) {
		return (vertex.rightChild != null);
	}
	
	/*
	 * A method to check if the given node has a parent. It will return true 
	 * if parent isn't null. Otherwise, it returns false (this case only for root node)
	 */
	public boolean hasParent(Leaf vertex) {
		return (vertex.parent != null);
	}
	
	/*
	 * A method to check if the parent node has a left child. It will return true 
	 * if left child for the parent node. Otherwise, it returns false
	 */
	public boolean isLeftChild(Leaf parent, Leaf child) {
		if(!hasLeftChild(parent)) { // In case the parent has only right child and left child is null 
			return false;
		}
		return (child.key.equals(parent.leftChild.key));
	}
	
	/*
	 * A method to check if the parent node has a right child. It will return true 
	 * if right child for the parent node. Otherwise, it returns false
	 */
	public boolean isRightChild(Leaf parent, Leaf child) {
		if(!hasRightChild(parent)) { // In case the parent has only right child and left child is null
			return false;
		}
		return (child.key.equals(parent.rightChild.key));
	}
	
	/*
	 * A method to perform a single rotation either to right or to the left
	 * according to the given character 'l' to the left. Otherwise, to the right
	 */
	public void singleRotation(Leaf Z, Leaf Y, Leaf X, char ch) {
		if(debug) {
			System.out.print("It is a single rotation to the ");
		}
		if(ch =='l' ) { // single rotation to left
			if(debug) {
				System.out.println("left");
			}
			if(hasParent(Z)) {
				Leaf parentOfZ = Z.parent; 
				if (isLeftChild(parentOfZ, Z)){ 
					parentOfZ.leftChild = Y;
				}else {
					parentOfZ.rightChild = Y;
				}
				Y.parent = parentOfZ;
			}else { // No parent. It is the root
				root = Y;
				Y.parent = null;
			}
			if(hasRightChild(Y)) {
				Z.leftChild = Y.rightChild;
				Z.leftChild.parent = Z;
			}else {
				Z.leftChild = null;
			}
			Y.rightChild = Z;
			Z.parent = Y;
		}else {  // single Rotation to right
			if(debug) {
				System.out.println("right");
			}
			if(hasParent(Z)) {
				Leaf parentOfZ = Z.parent;
				if (isLeftChild(parentOfZ, Z)){
					parentOfZ.leftChild = Y;
				}else {
					parentOfZ.rightChild = Y;
				}
				Y.parent = parentOfZ;
				
			}else {  // No parent. It is the root
				root = Y;
				Y.parent = null;
			}
			if(hasLeftChild(Y)) {
				Z.rightChild = Y.leftChild;
				Z.rightChild.parent = Z;
			}else {
				Z.rightChild = null;
			}
			Y.leftChild = Z;
			Z.parent = Y;
		}
		if(debug) {
			System.out.println("Z is " + Z.key + ", Y is " + Y.key + ", and X is " + X.key );
		}
		Z.height = Z.height -1; // as Z goes down one level
		rootHeightPrevious = Z.height;
		upDate(X);
		rootHeightPrevious = 0;
	}
	
//	public ArrayList<String> previousCar(String key_){
//		Leaf vertex = searchFound(root, key_);
//		if(vertex == null) {
//			return null;
//		}else {
//			return vertex.owners;
//		}
//	}
	
	/*
	 * A method to perform a double rotation either to right or to the left
	 * according to the given character 'l' to the left. Otherwise, to the right
	 */
	public void doubleRotation(Leaf Z, Leaf Y, Leaf X, char ch) {
		if(debug) {
			System.out.print("It is a double rotation to the ");
			
		}
		if(ch =='l') { // double rotation to the left
			if(debug) {
				System.out.println("left");
			}
			if(hasParent(Z)) {
				Leaf parentOfZ = Z.parent;
				if (isLeftChild(parentOfZ, Z)){
					parentOfZ.leftChild = X;
				}else {
					parentOfZ.rightChild = X;
				}
				X.parent = parentOfZ;

			}else { // No parent. It is the root
				root = X;
				X.parent = null;
			}
			
			Leaf leftChildOfX = null, rightChildOfX = null;
			if(hasLeftChild(X)) {
				leftChildOfX = X.leftChild;
			}
			if(hasRightChild(X)) {
				rightChildOfX = X.rightChild;
			}
			X.rightChild = Z;
			Z.parent = X; // Same X.rightChild.parent = X;
			X.leftChild = Y;
			Y.parent = X; // Same X.leftChild.parent = X;
			Y.rightChild = leftChildOfX;
			if(hasRightChild(Y)) {
				Y.rightChild.parent = Y; 
			}
			Z.leftChild = rightChildOfX;
			if(hasLeftChild(Z)) {
				Z.leftChild.parent = Z; 
			}
		}else {    // double rotation to the right
			if(debug) {
				System.out.println("right");
			}
			if(hasParent(Z)) {
				Leaf parentOfZ = Z.parent;
				if (isLeftChild(parentOfZ, Z)){
					parentOfZ.leftChild = X;
				}else {
					parentOfZ.rightChild = X;
				}
				X.parent = parentOfZ;
			}else { // No parent. It is the root
				root = X;
				X.parent = null;
			}
			
			Leaf leftChildOfX = null, rightChildOfX = null;
			if(hasLeftChild(X)) {
				leftChildOfX = X.leftChild;
			}
			if(hasRightChild(X)) {
				rightChildOfX = X.rightChild;
			}
			X.rightChild = Y;
			Y.parent = X; // Same X.rightChild.parent = X;
			X.leftChild = Z;
			Z.parent = X; // Same X.leftChild.parent = X;
			Z.rightChild = leftChildOfX;
			if(hasRightChild(Z)) {
				Z.rightChild.parent = Z; // same leftChildOfX.parent = Y
			}
			Y.leftChild = rightChildOfX;
			if(hasLeftChild(Y)) {
				Y.leftChild.parent = Y; // Same rightChildOfX.parent = Z
			}
		}
		if(debug) {
			System.out.println("Z is " + Z.key + ", Y is " + Y.key + ", and X is " + X.key );
			rootHeightPrevious = Z.height;
		}
		Z.height = Z.height -1; // as Z goes down one level
		upDate(Y);
		rootHeightPrevious = 0;
		upDate(Z);
		rootHeightPrevious =0 ;
	}
	
	
	public void remove(Integer key, boolean debugging) {
		System.out.println("-------------Start of deleting----------");
		System.out.println( key + " will be removed from the AVL tree");
		debug = debugging;
		remove(key);
		if(debug) {
			System.out.println(key + " was succesfully removed from the AVL tree");
		}
		debug = false;
		System.out.println("Now tree as the following");
		printTree();
		System.out.println("*************End of deleting************\n");
	}

	/*
	 * A method to remove a node by giving a key 
	 */
	public void remove(Integer key) {
		Leaf deleted = search(root, key);			 // find the node with such the key  
		if(deleted == null) { // There is no a such node in the tree
			System.out.println("\nThere is no such value\n\n");
			debug = false;
		}else {
			//System.out.println(deleted);
			if(hasLeftChild(deleted) && hasRightChild(deleted)) { // The deleted node has both right and left children
				if(debug) {
					System.out.println("\n" + key + " has left child " + deleted.leftChild.key + ", and right child " + deleted.rightChild.key);
				}
				next = null; // set the next null
				remembered = null;
				findNext(root, deleted);// The node that should be replaced by the deleted node 
				System.out.println("next is " + next.key);
				Leaf parentOfDeletd = deleted.parent;	
				Leaf parentOfNext = next.parent;	 // for updating purposes
				Leaf rightChildNext = null;
				if(hasLeftChild(deleted)) {
					next.leftChild = deleted.leftChild; // As always left child is null
					next.leftChild.parent = next;
				}
				if(hasParent(deleted)) {
					if(parentOfNext.key.compareTo(deleted.key) == 0) {// That means we'd like to delete the parent of next (substitution node)						
							if (isLeftChild(parentOfDeletd, deleted)){
								parentOfDeletd.leftChild = next;
							}else {
								parentOfDeletd.rightChild = next;
							}
							next.parent = parentOfDeletd;
							
					}else {
						if(isLeftChild(parentOfDeletd, deleted)){
							parentOfDeletd.leftChild = next;
						}else {
							parentOfDeletd.rightChild = next;
						}
						next.parent = parentOfDeletd;
						if(hasRightChild(next)) {
							rightChildNext = next.rightChild;
							parentOfNext.leftChild = rightChildNext;
							rightChildNext.parent = next.parent;
						}else {
							parentOfNext.leftChild = null;
						}
						next.rightChild = deleted.rightChild;
						next.rightChild.parent = next;
					}
					upDate(next);
				}else { // delete the root
					rootHeightPrevious = deleted.height;
					rightChildNext = next.rightChild;
					if((parentOfNext.key.compareTo(deleted.rightChild.key) == 0)) { // the parent of the next is the deleted node
						if(hasRightChild(next)) {
							rightChildNext.parent = parentOfNext;
							rightChildNext.parent.leftChild = rightChildNext;
						}else {
							parentOfNext.leftChild = null;
						}
						next.rightChild = parentOfNext;
						next.rightChild.parent = next;
						parentOfNext.parent = next;
					}else if(next.key.compareTo(root.rightChild.key) == 0   ) { // If the substitution node is the right child of the root  
						
					}else {	
						if(hasRightChild(next)) {
							rightChildNext = next.rightChild;
						}
						next.rightChild = deleted.rightChild;
						next.rightChild.parent = next;
						if(rightChildNext != null) {
							rightChildNext.parent = parentOfNext;
							rightChildNext.parent.leftChild = rightChildNext;
						}else {
							parentOfNext.leftChild = null;
						}
					}
					next.parent = null;
					root = next; // now next is the new root 
					upDate(root);
				}
			}else if (hasRightChild(deleted)) { // The deleted node has only right child
				if(debug) {
					System.out.println(key + " has only right child " + deleted.rightChild.key);
				}
				Leaf parentOfDeleted = deleted.parent;
				Leaf ChildOfDeleted = deleted.rightChild;	 // for updating purposes
				if(hasParent(deleted)) {
					if (isLeftChild(parentOfDeleted, deleted)){
						parentOfDeleted.leftChild = deleted.rightChild;
					}else {
						parentOfDeleted.rightChild = deleted.rightChild;
					}
				}else { // No parent. It is the root
					root = deleted.rightChild;
				}	
				if(hasRightChild(deleted)) {
					deleted.rightChild.parent = parentOfDeleted;
				}
				upDate(ChildOfDeleted);
			}else if (hasLeftChild(deleted)) { // The deleted node has only left Child
				if(debug) {
					System.out.println(key + " has only left child " + deleted.leftChild.key );
				}
				Leaf parentOfDeleted = deleted.parent;
				Leaf ChildOfDeleted = deleted.leftChild;	 // for updating purposes
				if(hasParent(deleted)) {
					if (isLeftChild(parentOfDeleted, deleted)){
						parentOfDeleted.leftChild = deleted.leftChild;
					}else {
						parentOfDeleted.rightChild = deleted.leftChild;
					}
				}else { // No parent. It is the root
					root = deleted.leftChild;
				}	
				if(hasLeftChild(deleted)) {
					deleted.leftChild.parent = parentOfDeleted;
				}
				upDate(ChildOfDeleted);// for updating purposes
			}else {// There is no child at all for deleted nodes
				if(debug) {
					System.out.println(key + " is a leaf. In particular, it doesn't have any child at all");
				}
				Leaf parentOfDeleted = deleted.parent;
				if(hasParent(deleted)) {
					if (isLeftChild(parentOfDeleted, deleted)){
						parentOfDeleted.leftChild = null;
					}else {
						parentOfDeleted.rightChild = null;
					}
				}else { // No parent. It is the root
					root = null;
				}
				upDate(parentOfDeleted);// for updating purposes
			}
		}
		rootHeightPrevious = 0;
	}
	
//	public String previosKey(String key_) {
//		Leaf vertex = search(root, key_);
//		if(vertex.key.equals(key_)) {
//			return parent(vertex).key;
//		}else if(key_.compareTo(vertex.key) > 0) {
//			return vertex.leftChild.key;
//		}else {
//			return vertex.rightChild.key;
//		}
//	}
//	
	public void restructure(Leaf vertex) {
		Leaf Z = vertex, Y, X;
		int heightLeftChild, heightRighttChild;
		if (hasLeftChild(vertex)) {
			heightLeftChild = vertex.leftChild.height;
		}else {
			heightLeftChild = 0;
		}
		if (hasRightChild(vertex)) {
			heightRighttChild = vertex.rightChild.height;
		}else {
			heightRighttChild = 0;
		}
		if(heightLeftChild > heightRighttChild) { // to choose the W that has more height
			Y = vertex.leftChild;
		}else {
			Y = vertex.rightChild;
		}
		heightLeftChild = 0; heightRighttChild = 0; // set the values again to zero
		if (hasLeftChild(Y)) {
			heightLeftChild = Y.leftChild.height;
		}else {
			heightLeftChild = 0;
		}
		if (hasRightChild(Y)) {
			heightRighttChild = Y.rightChild.height;
		}else {
			heightRighttChild = 0;
		}
		if(heightLeftChild > heightRighttChild) { // to choose the X that has more height
			X = Y.leftChild;
		}else {
			X = Y.rightChild;
		}
		if(debug) {
		}
		if(Z.key.compareTo(Y.key) > 0 ) {
			if(Y.key.compareTo(X.key) > 0) { // Single rotation to left
				singleRotation(Z, Y, X , 'l');
			}else {  // double rotation to left
				doubleRotation(Z, Y, X, 'l');
			}
		}else {
			if (Y.key.compareTo(X.key) > 0) { // double rotation to right
				doubleRotation(Z, Y, X, 'r');
			}else { // single rotation to right
				singleRotation(Z, Y, X, 'r');
			}
		}
	}
	
	public Leaf parent(Leaf vertex) {
		return vertex.parent;
	}
	
	public void upDate(Leaf vertex) {
		if(vertex == null) {
			if(debug) {
				System.out.println("Tree is balance.");
			}
			return;
		}
		int heightLeftChild, heightRighttChild, difference;
		if (hasLeftChild(vertex)) {
			heightLeftChild = vertex.leftChild.height;
		}else {
			heightLeftChild = 0;
		}
		if (hasRightChild(vertex)) {
			heightRighttChild = vertex.rightChild.height;
		}else {
			heightRighttChild = 0;
		}
		difference = heightRighttChild - heightLeftChild;
		difference = Math.abs(difference);
		if (difference > 1) {
			if(debug) {
				System.out.println("The current tree as the following");
				printTree();
				System.out.println("The tree is no longer balance.\nThere will be restructruing process");
			}
			restructure(vertex);
		}else {
			vertex.height = Math.max(heightLeftChild, heightRighttChild) + 1;
			upDate(parent(vertex));
		}
	}
	/*
	 * A method to show the debugging process for any unbalanced position
	 */
	public Leaf addKey(Integer key_ , boolean debuggin) {
//		if(key_.equals()) {
//			
//		}
		if(!isPositive(key_)) {
			return null;
		}
		debug = debuggin;
		System.out.println("-------------Start of adding----------");
		System.out.println("*****Adding " + key_ + " to the tree********");
		Leaf newNode = addKey(key_);
		debug = false;
		System.out.println("\n*****Adding " + key_ + " is done completely*****\n The tree now as the following");
		printTree();
		System.out.println("System.out.println(\"*************End of adding************\n");
		return newNode;
	}
	
	/*
	 * A method to check the input if it is a positive number. It return true if it is positive. Otherwise, it return false
	 */
	public boolean isPositive(Integer key_) {
		if (key_ <= 0) {
			System.out.println(key_ + "can't be added to the tree as the AVL supports only positive integers.");
			return false;
		}
		return true;
	}
	
//	/*
//	 * A method to check the input if it is an integer. It return true if it is positive. Otherwise, it return false
//	 */
//	public boolean isInteger( key_) {
//		Integer
//		if (key_.getClass() == Object.Integer) {
//			System.out.println(key_ + "can't be added to the tree as the AVL supports only positive integers.");
//			return false;
//		}
//		return true;
//	}
	
	/*
	 * A method to add a node with given 
	 */
	public Leaf addKey(Integer key_) {
		if(!isPositive(key_)) {
			return null;
		}
		Leaf exist;
		if(root == null) {
			exist = null;
		}else {
			exist= search(root, key_); // To check if there is such key in the tree. This search key is different from search Node that looks for memmory address
		}
		if(exist == null) { // There is no such node with the given key
			Leaf newValue = new Leaf(key_);
			if(root == null) {
				root = newValue;
				root.parent = null;
			} else {
				Leaf parentOfAddingValue = search(root, newValue);
				if(key_.compareTo(parentOfAddingValue.key) > 0) {
					parentOfAddingValue.rightChild = newValue;
					newValue.parent = parentOfAddingValue;
					upDate(newValue);
				}else {
					parentOfAddingValue.leftChild = newValue;
					newValue.parent = parentOfAddingValue;
					upDate(newValue);
				}
			}
			return newValue;
		}else {
			
			return exist;
		}
	}
	
	
	public Leaf nextKey(int key) {
		Leaf node = search(root, key);
		remembered = null;
		next = null;
		findNext(root, node);
		return next;
	}
	/*
	 * A method to compare 
	 */
	private void findNext(Leaf traverse, Leaf node) {
		if(hasLeftChild(traverse)) {
			findNext(traverse.leftChild, node);
		}
			//remembered == null just for the first time
		if( (remembered != null ) &&  (remembered.key.compareTo(node.key) == 0)){ // snapshot is an Arraylist 
			remembered = traverse;
			next = traverse;
			return;
		}
		remembered = traverse;
		if(hasRightChild(traverse)) {
			findNext(traverse.rightChild, node);
		}
	}

	public void inOrderReading() {
		System.out.println("The tree in an in-order way");
		inOrderReading2(root);
	}
	
	public void preOrderReading() {
		System.out.println("The tree in a pre-order way");
		preOrderReading2(root);
	}
	
	public void postOrderReading() {
		System.out.println("The tree in a post-order way");
		postOrderReading2(root);
	}
	
	/*
	 * A method to traverse through the tree in an in-order way
	 */
	private void inOrderReading2(Leaf traverse) {
		if(hasLeftChild(traverse)) {
			inOrderReading2(traverse.leftChild);
		}
		System.out.println(traverse.key   + " -->");
		if(hasRightChild(traverse)) {
			inOrderReading2(traverse.rightChild);
		}
	}
	
	/*
	 * A method to traverse through the tree in a pre-order way
	 */
	private void preOrderReading2(Leaf traverse) {
		System.out.println(traverse.key + " -->");
		if(hasLeftChild(traverse)) {
			preOrderReading2(traverse.leftChild);
		}
		if(hasRightChild(traverse)) {
			preOrderReading2(traverse.rightChild);
		}
	}
	
	/*
	 * A method to traverse through the tree in a post-order way
	 */
	private void postOrderReading2(Leaf traverse) {
		if(hasLeftChild(traverse)) {
			postOrderReading2(traverse.leftChild);
		}
		if(hasRightChild(traverse)) {
			postOrderReading2(traverse.rightChild);
		}
		System.out.println(traverse.key + " -->");
	}
	
	/*
	 * A private method that helps print method to put the whole tree as a heap way 
	 * by using array implementation
	 */
	private void printing(Leaf traverse, Leaf[] wholeTree, int ind) {
		int length = traverse.key.toString().length();
		if(length > max) {
			max = length;
		}
		//System.out.println(" Deug printing2 " + traverse);
		wholeTree[ind] =traverse;
		ind *= 2;
		if(hasLeftChild(traverse)) {
			printing(traverse.leftChild, wholeTree, ind + 1);
		}
		if(hasRightChild(traverse)) {
			printing(traverse.rightChild, wholeTree, ind+2);
		}
	}

	/*
	 *  This method check weather the specific index (i) is corresponding
	 *   to new level e.g 0, 2, 6, 14, 30 ... true is the i is equal 
	 *   to any value of this group in order to break the row to new row
	 */
	public boolean isNewRow(int i) {
		boolean itIs = false;
		int breakLine = 0;
		if(debug) {
			int rootHeight = 0;
			if(rootHeightPrevious != 0) {
				breakLine = rootHeightPrevious;
			}else {
				breakLine = root.height + 1; // as the unbalanced tree will have more line, so it needs more row as well
			}
		}else {
			breakLine = root.height;
		}
		for(int ind=1; ind <= breakLine; ind++) {
			if(i == ((int)Math.pow(2, ind) -2 )) {
				itIs = true;
				break;
			}
		}
		return itIs;
	}
	
	/*
	 * This method is to make a tap space. ch ='n' means the key is null. The purpose is to fill space for each empty node
	 * or ch anything stands to shift either at the beginning or between the nodes
	 */
	
	public void shift(int tap, char ch) {
		int num = 0;
		if(ch == 'n') {
			num = ( ( (int) Math.pow(2, tap) -1) * max)  ; // where max refers to the maximum space was allocated to key
		}else {
			num = (((int) Math.pow(2, tap) -1) * (max+2))  ;
		}
		for(int k = num ; k >0; k--) {
			System.out.print(" ");
		}
		
	}
	
	
	public  void printTree() {
		Leaf[] WholeTree;
		if(root == null) {
			System.out.println("There is no nodes at all in this tree to print\n");
			return;
		}
		if(debug) {
			int rootHeight = 0;
			if(rootHeightPrevious != 0) {
				rootHeight = rootHeightPrevious;
			}else {
				rootHeight = root.height;
			}
			WholeTree = new Leaf[(int)Math.pow(2, rootHeight + 1) -1]; // In order to avoid exception as the tree is unbalanced
		}else {
			WholeTree = new Leaf[(int)Math.pow(2, root.height) -1]; // create an array with the length equals the maximum number of nodes according to the root height of the tree
		}
		
		int ind =0;
		max = 0; // This is the maximum length of the whole keys in the tree
		printing(root, WholeTree, ind);
		int tap=0;
		int counter = 0; // we can delete
		boolean newLevel = true;
		int extraLevel = 0;
		if(debug) {
			extraLevel +=1; // give one more level so that we could see the unbalanced tree
		}
		int rootHeight = 0;
		if(rootHeightPrevious != 0) {
			rootHeight = rootHeightPrevious; // This is only when we print the tree as the root height was not updating yet
		}else {
			rootHeight = root.height;
		}
		for(int i=0; i < WholeTree.length ;i++) {
			if(newLevel) {
				tap = rootHeight - 1 - counter++ + extraLevel; // this value give the exponential value 
				shift(tap, 'd');
				newLevel =false;
			}
			if(WholeTree[i] == null) { // tap space if key is null
				System.out.print("|");
				shift(1, 'n');
				System.out.print("|");
			}else {
				System.out.printf("|%-" + max + "s|", WholeTree[i].key.toString());		
			}
			if(isNewRow(i)) { // To break the line 
				newLevel = true;
				System.out.println();
			}else { // give space to the next value
				shift(tap + 1, 'd');
			}
		}
		System.out.println();
	}
	
	
	public static void main(String[] a) {
		AVL amin = new AVL();
		amin.printTree();
		amin.addKey(14, true);
		amin.inOrderReading();
		amin.addKey(17, true);
		amin.inOrderReading();
		amin.addKey(11, true);
		amin.inOrderReading();
		amin.addKey(7, true);
		amin.inOrderReading();
		amin.addKey(53, true);
		amin.inOrderReading();
		amin.addKey(4, true);
		amin.inOrderReading();
		amin.addKey(13, true);
		amin.addKey(12, true);
		amin.addKey(8, true);
		amin.remove(53, true);
	}
}
