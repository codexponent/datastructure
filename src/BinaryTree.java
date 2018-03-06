public class BinaryTree {
	
	public Node root;
	
	public int totalNodes = 0;
	public int maxHeight = 0;
	/**
	 * BinaryTree Constructor
	 */
	public BinaryTree()
	{
		root = null;
	}
	/**
	 * Computes the variables
	 */
	public void compute()
	{
		totalNodes = 0;
		int depth = 1;
		traversal(root, depth);
		
		maxHeight = treeHeight(root);
	}
	/**
	 * Enters the Tree Height
	 * @param n Node n
	 * @return The mathematical calculation of the node
	 */
	public int treeHeight(Node n)
	{
		if(n != null)
		{
			return 1+Math.max(treeHeight(n.left), treeHeight(n.right));
		}
		else
		{
			return -1;
		}
	}
	/**
	 * Clears the screen
	 */
	public void clear()
	{
		root = null;
	}
	/**
	 * Removes the node
	 * @param value the node which is to be removed
	 */
	public void remove(String value)
	{
		root = removeNode(root, value);
	}
	/**
	 * Removes the node with its value
	 * @param node the node which is to be removed
	 * @param value the values which is to be removed
	 * @return the node left after the removal
	 */
	public Node removeNode(Node node, String value)
	{
		if(node == null) return node;
		
		if(value.compareTo(node.data) < 0)
			node.left = removeNode(node.left, value);
		else if(value.compareTo(node.data) > 0)
			node.right = removeNode(node.right, value);
		else
		{
			if(node.left == null)
				return node.right;
			else if(node.right == null)
				return node.left;
			
			node.data = minValue(node.right);
			
			node.right = removeNode(node.right, node.data);
		}
		return node;
	}
	/**
	 * Minimal Value of the string
	 * @param node the node which is to be minified
	 * @return reutrns the min value
	 */
	private String minValue(Node node)
	{
		String min = node.data;
		while(node.left != null)
		{
			min = node.left.data;
			node = node.left;
		}
		return min;
	}
	/**
	 * Checks whether it is empty or not
	 * @return the boolean value
	 */
	public boolean isEmpty()
	{
		return root == null;
	}
	/**
	 * Inserts the data
	 * @param data the data that is to be inserted
	 */
	public void insert(String data)
	{
		root = insertNode(root, data);
	}
	/**
	 * Inserts the Node 
	 * @param node the node that is to be inserted
	 * @param data the data that is to be inserted with
	 * @return the node
	 */
	public Node insertNode(Node node, String data)
	{
		if(node == null)
			return new Node(data);
		if(node.data.compareTo(data) == 0)
			return node;
		if(data.compareTo(node.data) < 0)
			node.left = insertNode(node.left, data);
		else
			node.right = insertNode(node.right, data);
		return node;
	}
	/**
	 * search function
	 * @param n the node in which the operation is going to be held
	 * @param depth the depth of the node
	 */
	public void traversal(Node n, int depth)
	{
		if(n != null)
		{
			traversal(n.left, depth+1);
			n.x = totalNodes++;
			n.y = depth;
			traversal(n.right,depth+1);
		}
	}
	public class Node
	{
		public String data;
		public int x;
		public int y;
		public Node left;
		public Node right;
		/**
		 * Constructor of the node
		 * @param data the data that is to be constructed
		 */
		public Node(String data)
		{
			this.data = data;
			left = right = null;
		}
	}
}
