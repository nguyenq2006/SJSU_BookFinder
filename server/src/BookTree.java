
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
}
