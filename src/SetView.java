import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetView extends ThreePanel {
	private Set<String> sets;
	
	private SetContent content;
	private JTextField addField;
	private JTextField removeField;
	
	private final static int CELL_WIDTH = 100;
	private final static int CELL_HEIGHT = 20;
	
	private final int MAX_SET_SIZE = 20;
	
	@Override
	public void clear() {
		sets.clear();
		addField.setText("");
		removeField.setText("");
		content.repaint();
	}
	
	@Override
	protected void init(JPanel op) {
		op.setLayout(new BoxLayout(op, BoxLayout.Y_AXIS));
		
		JButton add = new JButton("ADD");
		JButton remove = new JButton("REMOVE");
		
		addField = new JTextField();
		removeField = new JTextField();
		addField.setMaximumSize(new Dimension(100, 30));
		removeField.setMaximumSize(new Dimension(100,30));
		
		JPanel group1 = new JPanel();
		JPanel group2 = new JPanel();
		group1.setLayout(new BoxLayout(group1, BoxLayout.X_AXIS));
		group2.setLayout(new BoxLayout(group2, BoxLayout.X_AXIS));
		
		group1.add(add);
		group1.add(addField);
		
		group2.add(remove);
		group2.add(removeField);
		
		group1.setAlignmentX(LEFT_ALIGNMENT);
		group2.setAlignmentX(LEFT_ALIGNMENT);
		
		op.add(group1);
		op.add(group2);
		
		content = new SetContent();
		setContent(content);
		
		add.addActionListener(new AddAction());
		remove.addActionListener(new RemoveAction());
		
		setInfo("Set can store values without any particular order with no repeated values.\nIt is similar to finite set in mathematics.\nSet is mostly use for testing membership.");
	}
	
	private class AddAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent action) {
			if(sets.size() > MAX_SET_SIZE)
			{
				JOptionPane.showMessageDialog(null, "Maximum Set size limit reached");
				return;
			}
			sets.add(addField.getText());
			content.repaint();
		}
		
	}
	
	private class RemoveAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent action) {
			boolean success = sets.remove(removeField.getText());
			if(!success)
				JOptionPane.showMessageDialog(null, "Set does not contain given element");
			else
				content.repaint();
		}
		
	}
	
	private class SetContent extends JPanel {
		public SetContent()
		{
			sets = new HashSet<String>();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(sets.isEmpty())
			{
				g.drawString("No content available", 100, 100);
				return;
			}
			int size = sets.size();
			
			Iterator<String> itr = sets.iterator();
			
			int i =0;
			while(itr.hasNext())
			{
				int x = getWidth() / 2 - CELL_WIDTH /2;
				int y = CELL_HEIGHT * i;
				g.drawString(itr.next(), x + CELL_WIDTH/2 - 10, y + 16);
				g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);
				i++;
			}
		}
	}
}
