package sprint1_0.product;

import java.awt.*;
import javax.swing.*;

public class GUI extends JFrame{
	JFrame frame = new JFrame("SOS Game");
	JPanel topPanel = new JPanel(new GridLayout(1,5));
	JPanel leftPanel = new JPanel(new GridLayout(4,1));
	JPanel rightPanel = new JPanel(new GridLayout(5,1));
	
	private ButtonGroup gSelect = new ButtonGroup();
	private JRadioButton sGame = new JRadioButton("Simple Game");
	private JRadioButton gGame = new JRadioButton("General Game");
	private JTextField squares = new JTextField(2);
	
	private ButtonGroup bPlayerSelect = new ButtonGroup();
	private JRadioButton human = new JRadioButton("Human");
	private JRadioButton computer = new JRadioButton("Comptuer");
	JCheckBox record = new JCheckBox("Record");
	
	private ButtonGroup rPlayerSelect = new ButtonGroup();
	private JRadioButton human2 = new JRadioButton("Human");
	private JRadioButton computer2 = new JRadioButton("Comptuer");
	private JButton replay = new JButton("Replay");
	private JButton nGame = new JButton("New Game");
	
	public GUI() {
		gSelect.add(sGame);
		gSelect.add(gGame);	
		
		topPanel.add(new JLabel("SOS"));
		topPanel.add(sGame);
		topPanel.add(gGame);
		topPanel.add(new JLabel("Board Size"));
		topPanel.add(squares);
		
		bPlayerSelect.add(human);
		bPlayerSelect.add(computer);
		
		leftPanel.add(new JLabel("Blue Player"));
		leftPanel.add(human);
		leftPanel.add(computer);
		leftPanel.add(record);
		
		rPlayerSelect.add(human2);
		rPlayerSelect.add(computer2);
		
		rightPanel.add(new JLabel("Red Player"));
		rightPanel.add(human2);
		rightPanel.add(computer2);
		rightPanel.add(replay);
		rightPanel.add(nGame);
		
		JPanel panelGameFrame = new JPanel(new BorderLayout());
		panelGameFrame.add(topPanel, BorderLayout.NORTH);
		panelGameFrame.add(leftPanel, BorderLayout.WEST);
		panelGameFrame.add(rightPanel, BorderLayout.EAST);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panelGameFrame);
		frame.setSize(550,300);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});
	}
}
