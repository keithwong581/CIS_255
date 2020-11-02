/**********************
 * Keith Wong
 * CIS 255
 * 
 **********************/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// subclass of JFrame that handles all the typical frame
// tasks, especially being a listner for ActionEvents and KeyEvents


public class PacmanFrame extends JFrame implements KeyListener, ActionListener {
	public static final int DELAY = 400; //miliseconds
	public static final String GAME_FILE = "Pacman-world-2.txt";
	private PacmanComponent pacman; // our drawing component
	
	private JButton fasterButton; // go faster
	private JButton slowerButton; // go slower
	private javax.swing.Timer timer; // the timer object
	
	public PacmanFrame()
	{
		setTitle("Pacman");
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		pacman = new PacmanComponent(GAME_FILE);
		container.add(pacman, BorderLayout.CENTER);
		pacman.addKeyListener(this); // adding the KeyListener
		pacman.setFocusable(true);
		
		JPanel panel = new JPanel();
		container.add(panel,BorderLayout.SOUTH);
		fasterButton = new JButton("Speed up");
		fasterButton.addActionListener(this);
		fasterButton.setFocusable(false); // focusable should be
		panel.add(fasterButton); // false for everything else
		
		slowerButton = new JButton("Slow down");
		slowerButton.addActionListener(this);
		slowerButton.setFocusable(false); // focusable should be
		panel.add(slowerButton); // false for everything else
		
		timer = new javax.swing.Timer(DELAY,this);
		timer.start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pacman.requestFocusInWindow();
		pack();
		setVisible(true);
	}
	
	//actionPerformed is required because were are implementing the ActionListener interface
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(fasterButton)) {
			timer.setDelay(Math.max(50, timer.getDelay()-50));
		}
		else if (e.getSource().equals(slowerButton)) {
			timer.setDelay(timer.getDelay()+50);
		}
		else if (e.getSource().equals(timer)) {
			pacman.tick();
		}
	}
	
	// keyTyped and keyReleased are required because we're implementing
	// the KeyListener interface. Both should be ignored,
	// since we only care about key presses.
	public void keyTyped(KeyEvent e)
	{ }
	
	public void keyReleased(KeyEvent e)
	{ }
	
	// For keyPressed: hand off the key code of the event to the
	// PacmanComponent to deal with
	public void keyPressed(KeyEvent e)
	{
		pacman.keyPress(e.getKeyCode());
	}
	// our usual simple main
	public static void main(String[] args) {
		PacmanFrame frame = new PacmanFrame();
	}
}
