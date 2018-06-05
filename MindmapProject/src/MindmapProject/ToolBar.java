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
		public void actionPerformed(ActionEvent e) {	// 메뉴선택했을때 각 용도에 맞는 함수 호출
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
    // 새로 만들기 이벤트 처리, 단 툴바에서 실행하건 메뉴바에서 실행하건 동일하게
    
    // 열기 이벤트 처리, 단 툴바에서 실행하건 메뉴바에서 실행하건 동일하게
    
    // 저장 이벤트 처리, 단 툴바에서 실행하건 메뉴바에서 실행하건 동일하게
    
    // 다른 이름으로 저장 이벤트 처리, 단 툴바에서 실행하건 메뉴바에서 실행하건 동일하게
    
    // 닫기 이벤트 처리, 단 툴바에서 실행하건 메뉴바에서 실행하건 동일하게
    
    // 적용 이벤트 처리, 단 툴바에서 실행하건 메뉴바에서 실행하건 동일하게
    
    // 변경 이벤트 처리, 단 툴바에서 실행하건 메뉴바에서 실행하건 동일하게
}
