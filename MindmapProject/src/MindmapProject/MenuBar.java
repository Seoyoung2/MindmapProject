package MindmapProject;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

class MenuBar extends JMenuBar {
	private JMenu[] menuBtn = {new JMenu("New"), new JMenu("Open"), new JMenu("Save"), new JMenu("Save as.."), new JMenu("Close")};
	
    public MenuBar() { 
         setBackground(Color.LIGHT_GRAY);
         
         for(int i=0; i<5; i++) {
        	 add(menuBtn[i]);
        	 menuBtn[i].setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 13));
        	 menuBtn[i].addMenuListener(new MyMenuListener());
         }
    }
    
    //메뉴리스너랑 툴바에 있는 버튼리스너랑 달라서 따로 작성
    class MyMenuListener implements MenuListener{

		@Override
		public void menuCanceled(MenuEvent e) {}

		@Override
		public void menuDeselected(MenuEvent e) {}

		@Override
		public void menuSelected(MenuEvent e) {	// 메뉴선택했을때 각 용도에 맞는 함수 호출
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
		}
    }
}
