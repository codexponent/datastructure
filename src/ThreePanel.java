import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ThreePanel extends JPanel {
	
	private JPanel operationPanel;
	private JPanel dataPanel;
	private JPanel infoPanel;
	
	private JTextArea infoText;
	
	public ThreePanel()
	{
		setLayout(new BorderLayout());
		
		operationPanel = new JPanel();
		dataPanel = new JPanel(new BorderLayout());
		infoPanel = new JPanel();
		infoText = new JTextArea();
		infoPanel.add(infoText);
		
		operationPanel.setBorder(BorderFactory.createTitledBorder("Operation"));
		dataPanel.setBorder(BorderFactory.createTitledBorder("Data Structure Contents"));
		infoPanel.setBorder(BorderFactory.createTitledBorder("Information..."));
		
		operationPanel.setPreferredSize(new Dimension(200,0));
		infoPanel.setPreferredSize(new Dimension(0, 100));
		
		add(operationPanel, BorderLayout.WEST);
		add(dataPanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.SOUTH);
		
		init(operationPanel);
	}
	
	protected void init(JPanel operation)	// method to be overridden by subclass
	{
		
	}
	
	public void setInfo(String text)
	{
		infoText.setText(text);
	}
	
	
	public void setContent(JPanel view)
	{
		if(dataPanel.getComponentCount() == 1) return;
		dataPanel.add(view);
	}
	
	public void clear()	// method to be overridden by subclass
	{
		
	}
		
}
