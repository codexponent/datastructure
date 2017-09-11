import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TreeView extends ThreePanel {
	private BinaryTree binaryTree;
	private TreeContent content;
	
	private JTextField insertField;
	private JTextField removeField;
	private JLabel result;
	
	@Override
	public void clear() {
		binaryTree.clear();
		insertField.setText("");
		removeField.setText("");
		content.repaint();
	}
	@Override
	protected void init(JPanel op) {
		op.setLayout(new BoxLayout(op, BoxLayout.Y_AXIS));
		
		JButton insert = new JButton("INSERT");
		JButton remove = new JButton("REMOVE");
		JButton preO = new JButton("PRE ORDER");
		JButton postO = new JButton("POST ORDER");
		JButton inO = new JButton("IN ORDER");
		JButton showTree = new JButton("SHOW TREE");
		JButton treeH = new JButton("Tree Height");
		
		insertField = new JTextField();
		insertField.setMaximumSize(new Dimension(100,30));
		
		removeField = new JTextField();
		removeField.setMaximumSize(new Dimension(100,30));
		
		JPanel group1 = new JPanel();
		group1.setLayout(new BoxLayout(group1, BoxLayout.X_AXIS));
		group1.add(insert);
		group1.add(insertField);
		
		JPanel group2 = new JPanel();
		group2.setLayout(new BoxLayout(group2, BoxLayout.X_AXIS));
		group2.add(remove);
		group2.add(removeField);
		
		op.add(group1);
		op.add(group2);
		op.add(preO);
		op.add(postO);
		op.add(inO);
		op.add(showTree);
		op.add(treeH);
		op.add(new JLabel("result:"));
		result = new JLabel();
		op.add(result);
		
		group1.setAlignmentX(LEFT_ALIGNMENT);
		group2.setAlignmentX(LEFT_ALIGNMENT);
		preO.setAlignmentX(LEFT_ALIGNMENT);
		postO.setAlignmentX(LEFT_ALIGNMENT);
		inO.setAlignmentX(LEFT_ALIGNMENT);
		showTree.setAlignmentX(LEFT_ALIGNMENT);
		treeH.setAlignmentX(LEFT_ALIGNMENT);
		
		content = new TreeContent();
		setContent(content);
		
		preO.addActionListener(new PreOrderAction());
		postO.addActionListener(new PostOrderAction());
		inO.addActionListener(new InOrderAction());
		showTree.addActionListener(new ShowTreeAction());
		treeH.addActionListener(new TreeHAction());
		insert.addActionListener(new InsertAction());
		remove.addActionListener(new RemoveAction());
		setInfo("Tree are non linear data structure. Topmost node is called root of the tree. Node which are under\n other node are called its children. Node which does not have child are called leaf node.\n Insert on average case take O(log n) time complexity.\nDelete: O(log n). Traversal requires O(n) time since it must visit every node.");
	}
	
	private class InsertAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			binaryTree.insert(insertField.getText());
			content.repaint();
		}
		
	}
	
	private class PreOrderAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			content.setMode(TreeContent.PRE_ORDER);
			content.repaint();
		}
		
	}
	
	private class PostOrderAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			content.setMode(TreeContent.POST_ORDER);
			content.repaint();
		}
		
	}
	
	private class InOrderAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			content.setMode(TreeContent.IN_ORDER);
			content.repaint();
		}
		
	}
	
	private class ShowTreeAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			content.setMode(TreeContent.TREE_DRAW);
			content.repaint();
		}
		
	}
	
	private class TreeHAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			result.setText("height = " +binaryTree.treeHeight(binaryTree.root));
		}
		
	}
	
	private class RemoveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			binaryTree.remove(removeField.getText());
			content.repaint();
		}
		
	}
	
	private class TreeContent extends JPanel {
		private final static int CELL_WIDTH = 100;
		private final static int CELL_HEIGHT = 20;
		
		private int step = 0;
		private int mode = TREE_DRAW;
		public final static int IN_ORDER = 1;
		public final static int PRE_ORDER = 2;
		public final static int POST_ORDER = 3;
		public final static int TREE_DRAW = 4;
		public TreeContent() {
			binaryTree = new BinaryTree();
			binaryTree.insert("F");
			binaryTree.insert("B");
			binaryTree.insert("C");
			binaryTree.insert("D");
			binaryTree.insert("M");
			binaryTree.insert("J");
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(binaryTree.isEmpty())
			{
				g.drawString("No content available", 100, 100);
				return;
			}
			
			BinaryTree.Node root = binaryTree.root;
			
			step = 0;
			
			switch(mode)
			{
			case IN_ORDER:
				inOrder(root,g);
				break;
			case PRE_ORDER:
				preOrder(root,g);
				break;
			case POST_ORDER:
				postOrder(root,g);
				break;
			case TREE_DRAW:
				binaryTree.compute();
				drawTree(root, g);
				break;
			}
		}
		
		public void setMode(int mode)
		{
			this.mode = mode;
		}
		
		private void preOrder(BinaryTree.Node node, Graphics g)
		{	
			if(node != null)
			{
				int x = getWidth()/2 - CELL_WIDTH/2;
				int y = CELL_HEIGHT*step;
				step++;
				g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);
				g.drawString(node.data, x+CELL_WIDTH/2, y+16);
				
				preOrder(node.left, g);
				preOrder(node.right, g);
			}
		}
		
		private void inOrder(BinaryTree.Node node, Graphics g)
		{
			if(node != null)
			{
				inOrder(node.left,g);
				
				int x = getWidth()/2 - CELL_WIDTH/2;
				int y = CELL_HEIGHT*step;
				step++;
				g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);
				g.drawString(node.data, x+CELL_WIDTH/2, y+16);
				
				inOrder(node.right,g);
			}
		}
		
		private void postOrder(BinaryTree.Node node, Graphics g)
		{	
			if(node != null)
			{
				postOrder(node.left, g);
				postOrder(node.right, g);
				
				int x = getWidth()/2 - CELL_WIDTH/2;
				int y = CELL_HEIGHT*step;
				step++;
				g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);
				g.drawString(node.data, x+CELL_WIDTH/2, y+16);
			}
		}
		
		private void drawTree(BinaryTree.Node node, Graphics g)
		{
			int dx, dy, dx2, dy2;
			int scaleX = 50;
			int scaleY = 50;
			
			if(node != null) {
				drawTree(node.left, g);
				dx = node.x * scaleX;
				dy = node.y * scaleY;
				g.drawString(node.data, dx, dy);
				if(node.left!=null) {
					dx2 = node.left.x * scaleX;
					dy2 = node.left.y * scaleY;
					g.drawLine(dx, dy, dx2, dy2);
				}
				if(node.right != null) {
					dx2 = node.right.x * scaleX;
					dy2 = node.right.y * scaleY;
					g.drawLine(dx, dy, dx2, dy2);
				}
				drawTree(node.right,g);
			}
		}
	}
}
