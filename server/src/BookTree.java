/*BookTree sort the ISBN by the book title
 * it follow the same algorithm as AVLTree
 */
public class BookTree {
	//node class for the tree
	private class AVLNode{
		String bookISBN;
		AVLNode left;
		AVLNode right;
		
		//Constructor for the node
		AVLNode(String bookISBN){
			this(bookISBN, null, null);
		}
		
		AVLNode(String bookISBN, AVLNode left, AVLNode right){
			this.bookISBN = bookISBN;
			this.left = left;
			this.right = right;
		}
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
		if(root == null){
			String ISBN = b.getIsbn();
			root = new AVLNode(ISBN);
		}
		else
			root = 	insert(b, root);
	}
	
	private static AVLNode insert(Book b, AVLNode tree){
		String newTitle = b.getBookTitle();
		DataManager dm = DataManager.getIntance();
		Book currentBook = dm.getBook(tree.bookISBN);
		String currentTitle = currentBook.getBookTitle();
		int comparator = newTitle.compareTo(currentTitle);
		
		if(comparator < 0)
			tree.left = insert(b, tree.left);
		else if(comparator > 0)
			tree.right = insert(b, tree.right);
		
		return tree;
	}
}
