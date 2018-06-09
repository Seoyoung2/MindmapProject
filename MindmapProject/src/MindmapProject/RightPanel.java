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
			attriLabel[i].setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 18));
			textPanel.add(attriLabel[i]);
			
			attriTField[i] = new JTextField("");
			attriTField[i].setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 18));
			attriTField[i].setHorizontalAlignment(SwingConstants.CENTER);
			textPanel.add(attriTField[i]);
		}
		
		attriTField[0].setEditable(false); // TEXT 영역은 attribute에서 수정 불가능하게.
		// 0 = TEXT, 1 = X, 2 = Y, 3 = WIDTH, 4 = HEIGHT, 5 = COLOR
		
		add(textPanel, BorderLayout.CENTER);
		JButton rightBtn = new JButton("변경");
		rightBtn.setBackground(new Color(0xB93232));
		rightBtn.setPreferredSize(new Dimension(30, 50));
		rightBtn.setFont(new Font("돋움",  Font.BOLD, 18));
		rightBtn.addActionListener(new RightButtonListener());
		add(rightBtn, BorderLayout.SOUTH);
	}
	
	class RightButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				node.setLocation(Integer.parseInt(attriTField[1].getText()),Integer.parseInt(attriTField[2].getText()));
				node.setSize(Integer.parseInt(attriTField[3].getText()), Integer.parseInt(attriTField[4].getText()));
				node.setBackground(new Color(Integer.parseInt(attriTField[5].getText(),16)));
			}catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "잘못된 입력형식입니다!");		// 숫자칸에 문자입력하거나 16진수 색상형식이 잘못됬을때
				return;
			}
		}
	}
}