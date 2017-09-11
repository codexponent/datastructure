import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StackView extends ThreePanel {

	private Stack<String> stack;
	
	private StackContent content;
	private JTextField input;
	private JLabel result;
	
	private final int MAX_STACK_SIZE = 9;
	
	@Override
	protected void init(JPanel op) {
		op.setLayout(new BoxLayout(op, BoxLayout.Y_AXIS));
		
		JButton push = new JButton("PUSH");
		JButton pop = new JButton("POP");
		JButton peek = new JButton("PEEK");
		
		input = new JTextField();
		input.setMaximumSize(new Dimension(100,30));
		
		JPanel group1 = new JPanel();
		group1.setLayout(new BoxLayout(group1, BoxLayout.X_AXIS));
		group1.add(push);
		group1.add(input);
		
		group1.setAlignmentX(LEFT_ALIGNMENT);
		pop.setAlignmentX(LEFT_ALIGNMENT);
		peek.setAlignmentX(LEFT_ALIGNMENT);
		
		op.add(group1);
		op.add(pop);
		op.add(peek);
		
		op.add(new JLabel("Result:"));
		result = new JLabel();
		op.add(result);
		content = new StackContent();
		setContent(content);
		
		push.addActionListener(new PushAction());
		pop.addActionListener(new PopAction());
		peek.addActionListener(new PeekAction());
		
		setInfo("Stack is an abstract data type. It serves as collection of elements.\nPush add item to collection and pop remove the most recently added item.\nPeek operation access top element without modifying the stack.\n It is also called LIFO (Last in, first out).");
	}
	
	
	public void clear() {
		stack.clear();
		result.setText("");
		content.repaint();
	}
	
	private class PushAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent action) {
			if(stack.size() == MAX_STACK_SIZE)
			{
				JOptionPane.showMessageDialog(null, "The Stack Is OverFlow. ");
			}
			else
			{
				stack.push(input.getText());
				input.setText("");
				input.requestFocus();
				content.repaint();
			}
		}
		
	}
	
	private class PopAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {		
			try {
				result.setText(stack.pop());
				content.repaint();
			}
			catch (EmptyStackException ex1) {
				JOptionPane.showMessageDialog(null, "The Stack Is UnderFlow.");
			}
		}
		
	}
	
	private class PeekAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				result.setText(stack.peek());
				JOptionPane.showMessageDialog(null, result.getText());
			}
			catch (EmptyStackException ex) {
				JOptionPane.showMessageDialog(null, "The Stack Is Empty.");
			}
			input.requestFocus();
		}
	}
	
	private class StackContent extends JPanel
	{
		
		private final static int CELL_WIDTH = 100;
		private final static int CELL_HEIGHT = 20;
		
		public StackContent()
		{
			stack = new Stack<String>();
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(stack.empty())
			{
				g.drawString("No content available", 100, 100);
				return;
			}
			int x = this.getWidth() / 2 - CELL_WIDTH / 2;
			int y = this.getHeight() - 10;
			for (int i = 0; i < stack.size(); i++) {
				y = y - CELL_HEIGHT;
					
				if (i == stack.size() - 1){
					g.drawString("Top -> ", x/2, y+10);
				}
					
				g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);
				g.drawString(stack.get(i), x + CELL_WIDTH / 2 - 10, y + 18);
			}
		}
		
	}
}
