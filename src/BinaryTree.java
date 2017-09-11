public class BinaryTree {
	
	public Node root;
	
	public int totalNodes = 0;
	public int maxHeight = 0;
	
	public BinaryTree()
	{
		root = null;
	}
	
	public void compute()
	{
		totalNodes = 0;
		int depth = 1;
		traversal(root, depth);
		
		maxHeight = treeHeight(root);
	}
	
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
	public void clear()
	{
		root = null;
	}
	
	public void remove(String value)
	{
		root = removeNode(root, value);
	}
	
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
	
	public boolean isEmpty()
	{
		return root == null;
	}
	
	public void insert(String data)
	{
		root = insertNode(root, data);
	}
	
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
		
		public Node(String data)
		{
			this.data = data;
			left = right = null;
		}
	}
}
