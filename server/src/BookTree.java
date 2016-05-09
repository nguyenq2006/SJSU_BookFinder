/**
* BookTree sort the ISBN by the book title
* it follow the same algorithm as AVLTree
*/
public class BookTree {
	/**
	 * Creating an AVL Node class for AVL Book Tree 
	 */
	private class AVLNode{
		String bookISBN;
		AVLNode left;
		AVLNode right;
		int height;

		/**
		 * Constructing an AVL Node 
		 */
		AVLNode(String bookISBN){
			this(bookISBN, null, null);
		}

		AVLNode(String bookISBN, AVLNode left, AVLNode right){
			this.bookISBN = bookISBN;
			this.left = left;
			this.right = right;
			this.height = Math.max(height(left), height(right)) + 1;
		}
	}

	/**
	 * Gets the height of the AVL Book Tree 
	 */
	private int height(AVLNode t){
		if(t == null) return 0;
		return Math.max(height(t.left), height(t.right)) + 1;
	}
	
	/**
	 * Creating the AVL Book Tree  
	 */
	private AVLNode root;

	/**
	 * Constructing the Book AVL Tree
	 */
	public BookTree(){
		this.root = null;
	}
	
	/**
	 * Inserting Book Object into AVL Tree 
	 * @param b The Book to insert on the AVL Tree
	 */
	public void insert(Book b){
		root = 	insert(b, root);
	}
	
	/**
	 * Delete the node based on isbn data of the Book object
	 * @param isbn - the isbn of the Book object associated
	 */
	public void delete(String isbn) {
		root = delete(root, isbn);
	}

	/**
	 * Deleting the AVLNode off of the Book AVL Tree
	 * @param root The node to be deleted off the tree
	 * @param isbn The root associated with the node to be deleted
	 */
	private AVLNode delete(AVLNode root, String isbn) {
		// TODO Auto-generated method stub
		AVLNode current = root;
		if(current == null) {
			return current;
		}
		
		int comparator = isbn.compareTo(root.bookISBN);
		
		if(comparator > 0) {
			current.right = delete(current.right, isbn);
		} else if (comparator < 0) {
			current.left = delete(current.left, isbn);
		} else {
			if(current.left == null && current.right == null) //Case 1: Deleting a leaf node
				current = null;
			else if (current.right == null) { //Case 2: Deleting a node with 1 child
				current = current.left;
			}else if (current.left == null) { //Case 2: Deleting a node with 1 child
				current = current.right;
			} else { //Case 3: deleting a node with 2 children
				AVLNode temp = findMin(current.right);
				current.bookISBN = temp.bookISBN; 
				current.right = delete(current.right, temp.bookISBN); 
			}
		}
		
		return balance(current);
			
		
		
	}

	/**
	 * Inserting the new AVL Node to the Book Tree
	 * @param b The Book object to be inserted
	 * @param tree The root associated to the new node about to be inserted
	 */
	private AVLNode insert(Book b, AVLNode tree){
		if(tree ==null){
			String ISBN = b.getIsbn();
			tree = new AVLNode(ISBN);
		}
		else{
			String newTitle = b.getBookTitle();
			DataManager dm = DataManager.sharedInstance();
			Book currentBook = dm.getBookISBN(tree.bookISBN);
			String currentTitle = currentBook.getBookTitle();
			int comparator = newTitle.compareTo(currentTitle);

			if(comparator < 0)
				tree.left = insert(b, tree.left);
			else if(comparator > 0)
				tree.right = insert(b, tree.right);
		}
		return balance(tree);
	}
	
	/**
    * Find the smallest item in the tree.
    * @return smallest item or null if empty.
    */
	public AVLNode findMin()
	{
		return findMin(root);
	}
    
   /**
    * Internal method to find the smallest item in a subtree.
    * @param t the node that roots the tree.
    * @return node containing the smallest item.
    */
	private AVLNode findMin(AVLNode node) 
	{
       AVLNode current = node;

       /* loop down to find the leftmost leaf */
       while (current.left != null) {
           current = current.left;
       }
       return (current);
   }
	
	/**
	 * Re-balance the tree
	 * @param t - the AVL Node to be re-balanced.
	 */
	private AVLNode balance(AVLNode t )
	{
		if( t == null )
			return t;

		if( height(t.left) - height(t.right) > 1 )
			if( height(t.left.left) >= height(t.left.right))
				t = rotateWithLeftChild( t );
			else
				t = rotateLeftThenRight( t );
		else
			if( height(t.right) - height(t.left) > 1 )
				if( height(t.right.right) >= height(t.right.left) )
					t = rotateWithRightChild( t );
				else
					t = rotateRightThenLeft( t );

		t.height = Math.max( height(t.left), height(t.right)) + 1;
		return t;
	}
	
	/**
	 * Rotate the AVL Node to the left
	 * @param k2 the node to be rotated to the left
	 */
	private AVLNode rotateWithLeftChild( AVLNode k2 )
	{
		AVLNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max( height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max( height(k1.left), height(k2)) + 1;
		return k1;
	}

	/**
	 * Rotate the AVL Node to the right
	 * @param k1 - the node to be rotated to the right
	 */
	private AVLNode rotateWithRightChild( AVLNode k1 )
	{
		AVLNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max( height(k1.left) , height(k1.right)) + 1;
		k2.height = Math.max( height(k2.right), height(k1)) + 1;
		return k2;
	}

	/**
	 * Rotate the AVL Node left then right (2 Rotations)
	 * @param n the node to be rotated twice based on position
	 */
	private AVLNode rotateLeftThenRight(AVLNode n) {
		n.left = rotateWithRightChild(n.left);
		return rotateWithLeftChild(n);
	}

	/**
	 * Rotate the AVL Node right then left (2 Rotations)
	 * @param n the node to be rotated twice based on position
	 */
	private AVLNode rotateRightThenLeft(AVLNode n) {
		n.right = rotateWithLeftChild(n.right);
		return rotateWithRightChild(n);
	}
	
	/**
	 * Get the Book based on Book Title
	 * @param bookTitle the title of the book to retrieve
	 */
	public String getBook(String bookTitle){
		return get(bookTitle, root);
	}
	
	/**
	 * Retrieve the Book based on book title
	 * @param bookTitle The title of the book
	 * @param n the root associated to the new node to retrieve
	 */
	private String get(String bookTitle, AVLNode n){
		DataManager dm = DataManager.sharedInstance();
		Book b = dm.getBookISBN(n.bookISBN);
		String currentTitle = b.getBookTitle();
		String isbn = "";
		int comp = bookTitle.compareTo(currentTitle);
		if(comp == 0)
			isbn = n.bookISBN;
		else if(comp < 0)
			isbn = get(bookTitle, n.left);
		else if(comp > 0)
			isbn = get(bookTitle, n.right);
		return isbn;
		
	}
}
