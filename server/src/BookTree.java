/*BookTree sort the ISBN by the book title
* it follow the same algorithm as AVLTree
*/
public class BookTree {
	//node class for the tree
	private class AVLNode{
		String bookISBN;
		AVLNode left;
		AVLNode right;
		int height;

		//Constructor for the node
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

	private int height(AVLNode t){
		if(t == null) return 0;
		return Math.max(height(t.left), height(t.right)) + 1;
	}
	//BookTree
	private AVLNode root;

	/**
	 * BookTree constructor
	 */
	public BookTree(){
		this.root = null;
	}

	public void insert(Book b){
		root = 	insert(b, root);
	}
	
	public void delete(Book b) {
		root = delete(root, b);
	}

	private AVLNode delete(AVLNode root, Book b) {
		// TODO Auto-generated method stub
		AVLNode current = root;
		if(current == null) {
			return current;
		}
		
		String newTitle = b.getBookTitle();
		DataManager dm = DataManager.sharedInstance();
		Book currentBook = dm.getBook(root.bookISBN);
		String currentTitle = currentBook.getBookTitle();
		int comparator = newTitle.compareTo(currentTitle);
		
		/*if(current.data > data) {
			current.left = delete(current.left, data); 
			else if (current.data < data) {
			current.right = delete(current.right, data);
		} else {
			if(current.left == null && current.right == null) {
				current = null;
			} else if (current.right == null) {
				current = current.left;
			} else if (current.left == null) {
				current = current.right;
			} else {
				Node temp = findMin(current.right);
				current.data = temp.data;
				current.right = delete(current.right, temp.data);
			}
		}*/
		if(comparator > 0) {
			current.left = delete(current.left, b);
		} else if (comparator < 0) {
			current.right = delete(current.right, b);
		} else {
			if(current.left == null && current.right == null) {
				current = null;
			} else if (current.right == null) {
				current = current.left;
			} else if (current.left == null) {
				current = current.right;
			} else { //deleting a node with 2 children
				AVLNode temp = findMin(current.right);
				current.bookISBN = temp.bookISBN; //UNSURE
				current.right = delete(current.right, b); //UNSURE
			}
		}
		
		return balance(current);
			
		
		
	}

	private AVLNode insert(Book b, AVLNode tree){
		if(tree ==null){
			String ISBN = b.getIsbn();
			tree = new AVLNode(ISBN);
		}
		else{
			String newTitle = b.getBookTitle();
			DataManager dm = DataManager.sharedInstance();
			Book currentBook = dm.getBook(tree.bookISBN);
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

	private AVLNode rotateWithLeftChild( AVLNode k2 )
	{
		AVLNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max( height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max( height(k1.left), height(k2)) + 1;
		return k1;
	}

	private AVLNode rotateWithRightChild( AVLNode k1 )
	{
		AVLNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max( height(k1.left) , height(k1.right)) + 1;
		k2.height = Math.max( height(k2.right), height(k1)) + 1;
		return k2;
	}

	private AVLNode rotateLeftThenRight(AVLNode n) {
		n.left = rotateWithRightChild(n.left);
		return rotateWithLeftChild(n);
	}

	private AVLNode rotateRightThenLeft(AVLNode n) {
		n.right = rotateWithLeftChild(n.right);
		return rotateWithRightChild(n);
	}
	
	public String getBook(String bookTitle){
		return get(bookTitle, root);
	}
	
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
