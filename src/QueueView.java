import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QueueView extends ThreePanel {
	private Queue<String> queue;
	
	private QueueContent content;
	private JTextField input;
	private JLabel result;
	
	private final int MAX_QUEUE_SIZE = 20;
	
	@Override
	/**
	 * Clears the screen
	 */
	public void clear() {
		queue.clear();
		input.setText("");
		result.setText("");
		content.repaint();
	}
	@Override
	/**
	 * Runs when the class starts
	 */
	protected void init(JPanel op) {
		op.setLayout(new BoxLayout(op, BoxLayout.Y_AXIS));
		
		JButton enqueue = new JButton("ENQUEUE");
		JButton dequeue = new JButton("DEQUEUE");
		JButton peek = new JButton("PEEK");
		
		input = new JTextField();
		input.setMaximumSize(new Dimension(100,30));
		
		JPanel group1 = new JPanel();
		group1.setLayout(new BoxLayout(group1, BoxLayout.X_AXIS));
		group1.add(enqueue);
		group1.add(input);
		
		group1.setAlignmentX(LEFT_ALIGNMENT);
		dequeue.setAlignmentX(LEFT_ALIGNMENT);
		peek.setAlignmentX(LEFT_ALIGNMENT);
		
		op.add(group1);
		op.add(dequeue);
		op.add(peek);
		
		op.add(new JLabel("Result:"));
		result = new JLabel();
		op.add(result);
		
		content = new QueueContent();
		setContent(content);
		enqueue.addActionListener(new EnqueueAction());
		dequeue.addActionListener(new DequeueAction());
		peek.addActionListener(new PeekAction());
		
		setInfo("Queses are designed to operate in FIFO (first in, first out).\n Elements are inserted into one end of the collection and exracted from the other.\n Enqueue insert the element whereas dequeue extract from the other.\nPeek operation can access the first element without modifying the queue.");
	}
	
	private class EnqueueAction implements ActionListener
	{

		/**
		 * Default Action Listener for Enqueue Action
		 */
		public void actionPerformed(ActionEvent action) {
			if(queue.size() > MAX_QUEUE_SIZE)
			{
				JOptionPane.showMessageDialog(null, "Maximum queue capacity reached");	
			}
			else
			{
			queue.add(input.getText());
			content.increasePosition();
			content.repaint();
			}
		}
		
	}
	
	private class DequeueAction implements ActionListener
	{

		/**
		 * Default Action Listener for Dequeue Action
		 */
		public void actionPerformed(ActionEvent action) {
			String data = queue.poll();
			if(data == null)
			{
				JOptionPane.showMessageDialog(null, "The Queue Is Empty.");
			}
			else
			{
				result.setText(data);
				content.decreasePosition();
				content.repaint();
			}
		}
		
	}
	
	private class PeekAction implements ActionListener
	{

		/**
		 * Default Action Listener for Peek Action
		 */
		public void actionPerformed(ActionEvent action) {
			String data = queue.peek();
			if(data == null)
			{
				JOptionPane.showMessageDialog(null, "The Queue Is Empty.");
			}
			else
			{
				result.setText(data);
				JOptionPane.showMessageDialog(null, result.getText());
				content.repaint();
			}
		}
		
	}
	
	private class QueueContent extends JPanel
	{
		private final static int CELL_WIDTH = 100;
		private final static int CELL_HEIGHT = 20;
		
		private int firstPosition = 0;
		/**
		 * QueueContent Constructor
		 */
		public QueueContent() {
			queue = new LinkedList<String>();
		}
		/**
		 * Increases the position
		 */
		public void increasePosition()
		{
			firstPosition++;
			if(firstPosition > 10)
			{
				firstPosition = 10;
			}
		}
		/**
		 * Desceases the position
		 */
		public void decreasePosition()
		{
			if(queue.size() < 10 && firstPosition > 0)
			{
				firstPosition--;
			}
		}
		/**
		 * Default paint component of Java
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(queue.isEmpty())
			{
				g.drawString("No content available", 100, 100);
				return;
			}
			
			int size = queue.size();
			Iterator<String> itr = queue.iterator();
			
			int count = 1;
			while(itr.hasNext())
			{
				String data = itr.next();
				int firstLocation = getHeight() - (firstPosition+1)*CELL_HEIGHT;
				
				int x = getWidth()/2 - CELL_WIDTH/2;
				int y = firstLocation + CELL_HEIGHT * count ;
				
				y = y % getHeight();	// wrap around code
				
				if (count == 1){
					g.drawString("First -> ", x/2, y+10);
				}

				g.drawString(data, x+CELL_WIDTH/2, y+18);
				g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);
				
				if(count == queue.size())
				{
					g.drawString("Last -> ", x/2, y+10);	
				}
				count++;
			}
		}
	}
}
