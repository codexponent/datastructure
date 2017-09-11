import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class ListView extends ThreePanel {
	private ArrayList<String> list;
	private ListContent content;
	
	private final int MAX_ARRAY_SIZE = 20;
	
	private final static int CELL_WIDTH = 100;
	private final static int CELL_HEIGHT = 20;
	
	private JTextField addInput;
	private JSpinner removeSpin;
	private JLabel result;
	private JSpinner insertSpin;
	
	@Override
	public void clear() {
		list.clear();
		addInput.setText("");
		result.setText("");
		removeSpin.setValue(0);
		insertSpin.setValue(0);
		content.repaint();
	}
	
	@Override
	protected void init(JPanel op) {
		op.setLayout(new BoxLayout(op, BoxLayout.Y_AXIS));
		
		JButton add = new JButton("ADD");
		JButton remove = new JButton("REMOVE");
		JButton insert = new JButton("INSERT");
		
		addInput = new JTextField();
		addInput.setMaximumSize(new Dimension(100,30));
		removeSpin = new JSpinner(new SpinnerNumberModel(0,0, MAX_ARRAY_SIZE-1, 1));
		removeSpin.setMaximumSize(new Dimension(100, 30));
		
		insertSpin = new JSpinner(new SpinnerNumberModel(0, 0, MAX_ARRAY_SIZE-1,1));
		insertSpin.setMaximumSize(new Dimension(100,30));
		
		JPanel group1 = new JPanel();
		group1.setLayout(new BoxLayout(group1, BoxLayout.X_AXIS));
		group1.add(add);
		group1.add(addInput);
		
		group1.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel group2 = new JPanel();
		group2.setLayout(new BoxLayout(group2, BoxLayout.X_AXIS));
		group2.add(remove);
		group2.add(removeSpin);
		
		group2.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel group3 = new JPanel();
		group3.setLayout(new BoxLayout(group3, BoxLayout.X_AXIS));
		group3.add(insert);
		group3.add(insertSpin);
		
		group3.setAlignmentX(LEFT_ALIGNMENT);
		
		op.add(group1);
		op.add(group2);
		op.add(group3);
		
		op.add(new JLabel("Result:"));
		result = new JLabel();
		op.add(result);
		
		content = new ListContent();
		setContent(content);
		
		add.addActionListener(new AddAction());
		remove.addActionListener(new RemoveAction());
		insert.addActionListener(new InsertAction());
		
		setInfo("List is an abstract data type. It contains finite sequence of elements which can repeat.\nElement can be added or removed at an index.");
	}
	
	private class AddAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent action) {
			if(list.size() > MAX_ARRAY_SIZE)
			{
				JOptionPane.showMessageDialog(null, "Maximum list items reached");
				return;
			}
			list.add(addInput.getText());
			content.repaint();
		}
		
	}
	
	private class RemoveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent action) {
			try
			{
				int index = (int)removeSpin.getValue();
				list.remove(index);	
				content.repaint();
			}
			catch(IndexOutOfBoundsException e)
			{
				JOptionPane.showMessageDialog(null, "Index is out of range");
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Enter valid number");	
			}
		}
		
	}
	
	private class InsertAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent action) {
			try
			{
				int index = (int)insertSpin.getValue();
				list.add(index, addInput.getText());
				content.repaint();
			}
			catch(IndexOutOfBoundsException e)
			{
				JOptionPane.showMessageDialog(null, "Index is out of range");
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Enter valid number");	
			}
		}
		
	}
	
	private class ListContent extends JPanel {
		public ListContent() {
			list = new ArrayList<String>();
		}
	
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(list.isEmpty())
			{
				g.drawString("No content available", 100, 100);
				return;
			}
			
			int size = list.size();
			
			for(int i = 0; i < size; i++)
			{
				int x = getWidth() / 2 - CELL_WIDTH /2;
				int y = CELL_HEIGHT * i;
				g.drawString("index: "+i, x - (int)(CELL_WIDTH * 0.6), y+16);
				g.drawString(list.get(i), x + CELL_WIDTH/2 - 10, y + 16);
				g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);
			}
		}
	}
}
