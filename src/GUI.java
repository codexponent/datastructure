import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI extends JPanel {
	JTabbedPane tab;
	public GUI()
	{
		super(new GridLayout(1,1));
		tab = new JTabbedPane();
			
		tab.addTab("Stack", new StackView());
		tab.addTab("Queue", new QueueView());
		tab.addTab("List", new ListView());
		tab.addTab("Set", new SetView());
		tab.addTab("Tree", new TreeView());
		
		add(tab);
		
		setPreferredSize(new Dimension(800,600));
	}
	
	public void clearContent()
	{
		ThreePanel currentTab = (ThreePanel)tab.getComponentAt(tab.getSelectedIndex());
		currentTab.clear();
	}
	
	public void screenShot() {
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		this.paint(img.getGraphics());
		try {
			JFileChooser jFile = new JFileChooser();
			jFile.showSaveDialog(null);
			Path pth = jFile.getSelectedFile().toPath();
			ImageIO.write(img, "png", new File(pth.toString() + ".png"));
			JOptionPane.showMessageDialog(null, "Image saved to " + pth.toString() + ".png");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Image not Saved!!");
		}
	}
	
	private static JMenuBar createMenuBar(GUI gui, JFrame f)
	{
		JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
		
		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		
		JMenuItem clear = new JMenuItem("Clear");
		edit.add(clear);
		
		JMenuItem save = new JMenuItem("Save");
		file.add(save);
		
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.screenShot();
			}
			
		});
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
			
		});
		
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.clearContent();
			}
			
		});
		
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Data structure");
			}
			
		});
		return menuBar;
	}
	
	private static void createAndShow()
	{
		JFrame frame = new JFrame("Data structure");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI gui = new GUI();
		frame.setContentPane(gui);
		frame.setJMenuBar(createMenuBar(gui, frame));
		
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				createAndShow();
			}
		});
	}
}
