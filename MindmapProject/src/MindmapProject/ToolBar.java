package MindmapProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class ToolBar extends JToolBar{
	public ToolBar() {
		JButton[] toolBtn = {new JButton("New"), new JButton("Open"), new JButton("Save"), new JButton("Save as.."), new JButton("Close")};
		
		setBackground(Color.GRAY);
		setFloatable(false);
		
		for(int i=0; i<5; i++) {
			add(toolBtn[i]);
			toolBtn[i].setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 13));
			toolBtn[i].addActionListener(new ToolBarButtonListener());
		}
    }
	
	
	class ToolBarButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	// �޴����������� �� �뵵�� �´� �Լ� ȣ��
			JButton b = (JButton)e.getSource();
    		
    		if(b.getText().equals("New")) {
    			BarEvent.selectNew();
    		}
    		else if(b.getText().equals("Open")) {
    			BarEvent.selectOpen();
    		}
    		else if(b.getText().equals("Save")) {
    			BarEvent.selectSave();
    		}
    		else if(b.getText().equals("Save as..")) {
    			BarEvent.saveCnt = true;
    			BarEvent.selectSave();
    		}
    		else if(b.getText().equals("Close")) {
    			BarEvent.selectClose();
    		}
		}
	}
    // ���� ����� �̺�Ʈ ó��, �� ���ٿ��� �����ϰ� �޴��ٿ��� �����ϰ� �����ϰ�
    
    // ���� �̺�Ʈ ó��, �� ���ٿ��� �����ϰ� �޴��ٿ��� �����ϰ� �����ϰ�
    
    // ���� �̺�Ʈ ó��, �� ���ٿ��� �����ϰ� �޴��ٿ��� �����ϰ� �����ϰ�
    
    // �ٸ� �̸����� ���� �̺�Ʈ ó��, �� ���ٿ��� �����ϰ� �޴��ٿ��� �����ϰ� �����ϰ�
    
    // �ݱ� �̺�Ʈ ó��, �� ���ٿ��� �����ϰ� �޴��ٿ��� �����ϰ� �����ϰ�
    
    // ���� �̺�Ʈ ó��, �� ���ٿ��� �����ϰ� �޴��ٿ��� �����ϰ� �����ϰ�
    
    // ���� �̺�Ʈ ó��, �� ���ٿ��� �����ϰ� �޴��ٿ��� �����ϰ� �����ϰ�
}
