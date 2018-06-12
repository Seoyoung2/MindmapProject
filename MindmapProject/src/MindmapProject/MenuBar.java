package MindmapProject;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import MindmapProject.LeftPanel.LeftButtonListener;
import MindmapProject.RightPanel.RightButtonListener;

class MenuBar extends JMenuBar {
	private JMenu[] menuBtn = {new JMenu("New"), new JMenu("Open"), new JMenu("Save"), new JMenu("Save as.."), new JMenu("Close"), new JMenu("����"), new JMenu("����")};
	
    public MenuBar() { 
         setBackground(new Color(0x323C73));
         
         for(int i=0; i<menuBtn.length; i++) {
        	 add(menuBtn[i]);
        	 menuBtn[i].setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 18));
        	 menuBtn[i].setForeground(Color.WHITE);
        	 menuBtn[i].addMenuListener(new MyMenuListener());
         }
         menuBtn[5].addActionListener(Window.getLeftPanel().new LeftButtonListener());
         
         JButton toolApplyBtn = new JButton("����");
         toolApplyBtn.addActionListener(Window.getLeftPanel().new LeftButtonListener());
         add(toolApplyBtn);
		
         JButton toolChangeBtn = new JButton("����");
         toolChangeBtn.addActionListener(Window.getRightPanel().new RightButtonListener());
         add(toolChangeBtn);
    }
    
    //�޴������ʶ� ���ٿ� �ִ� ��ư�����ʶ� �޶� ���� �ۼ�
    class MyMenuListener implements MenuListener{

		@Override
		public void menuCanceled(MenuEvent e) {}

		@Override
		public void menuDeselected(MenuEvent e) {}

		@Override
		public void menuSelected(MenuEvent e) {	// �޴����������� �� �뵵�� �´� �Լ� ȣ��
			JMenu b = (JMenu)e.getSource();
    		
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
//    		else if(b.getText().equals("����")) {
//    			b.addActionListener(Window.getLeftPanel().new LeftButtonListener());
//    		}
//    		else if(b.getText().equals("����")) {
//    			b.addActionListener(Window.getRightPanel().new RightButtonListener());
//    		}
		}
    }
}
