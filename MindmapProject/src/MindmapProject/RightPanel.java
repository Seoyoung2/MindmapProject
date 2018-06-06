package MindmapProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RightPanel extends JPanel{
	JPanel rightPanel;
	JTextField [] attriTField;
	static Node node;
	
	public RightPanel(){
		setLayout(new BorderLayout());

		rightPanel = new JPanel();
		JPanel textPanel = new JPanel(new GridLayout(6, 2, 0, 40));
		
		JLabel attriLabel [] = {new JLabel("TEXT"), new JLabel("X"), new JLabel("Y"), new JLabel("WIDTH"), new JLabel("HEIGHT"), new JLabel("COLOR")};
		attriTField = new JTextField[attriLabel.length];
		
		for(int i = 0 ; i < attriLabel.length; i++) {
			attriLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			textPanel.add(attriLabel[i]);
			
			attriTField[i] = new JTextField("");
			attriTField[i].setHorizontalAlignment(SwingConstants.CENTER);
			textPanel.add(attriTField[i]);
		}
		
		attriTField[0].setEditable(false); // TEXT 영역은 attribute에서 수정 불가능하게.
		// 0 = TEXT, 1 = X, 2 = Y, 3 = WIDTH, 4 = HEIGHT, 5 = COLOR
		
		add(textPanel, BorderLayout.CENTER);
		JButton rightBtn = new JButton("변경");
		rightBtn.setBackground(new Color(0xFF6666));
		rightBtn.addActionListener(new RightButtonListener());
		add(rightBtn, BorderLayout.SOUTH);
	}
	
	class RightButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			if(b.getText().equals("변경")) {
				// 서로 선이 그려질 경우는 어떻게 해야하는가..ㅠ..
				node.setLocation(Integer.parseInt(attriTField[1].getText()),Integer.parseInt(attriTField[2].getText()));
				node.setSize(Integer.parseInt(attriTField[3].getText()), Integer.parseInt(attriTField[4].getText()));
				node.setBackground(new Color(Integer.parseInt(attriTField[5].getText(),16)));
			}
		}
	}
}