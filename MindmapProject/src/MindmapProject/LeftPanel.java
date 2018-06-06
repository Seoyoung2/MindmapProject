package MindmapProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MindmapProject.Window;

public class LeftPanel extends JPanel{
	JPanel leftPanel;
	static JTextArea textArea; // 입력할 장소
	Stack stk; // 스택 자료구조 사용을 위함
	static Node[] datas;

    public LeftPanel() {
    	setLayout(new BorderLayout());
    	leftPanel = new JPanel();
    	
		setBackground(Color.WHITE);
		
		textArea = new JTextArea(5, 25);
		
        textArea.setEditable(true); // 편집가능하게
        textArea.setTabSize(4); 
        add(textArea, BorderLayout.CENTER);
        
        JButton leftBtn = new JButton("적용");
		add(leftBtn, BorderLayout.SOUTH);
		leftBtn.addActionListener(new LeftButtonListener());
		leftBtn.setBackground(new Color(0xFF6666)); 
    }
    
    static JTextArea getTextArea() {
    	return textArea;
    }
    
    class LeftButtonListener implements ActionListener{
    	int sublen;
    	int tabcnt;
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		JButton b = (JButton)e.getSource();
    		
    		if(b.getText().equals("적용")) { // 적용 버튼이면 // 굳이 if문이 필요한가
    			Window.getCenterPanel().removeAll(); // centerpanel 초기화
    			String[] values = textArea.getText().split("\n"); // 행별로 잘라서
    			stk = new Stack(values.length);
    			
    			datas = new Node[values.length];
   
    			for(int i = 0; i < values.length; i++) { // 스택을 이용한 트리 구조 형성
    				tabcnt = 0; // \t 개수, 레벨을 판정함
    				sublen = values[i].length(); // 문자열 길이
    				for(int j = 0; j < sublen; j++) {
    					if(values[i].charAt(0)=='\t') { // 탭이면
    						++tabcnt; // 탭개수 올리고
    						values[i] = values[i].substring(1); // 현재 탭 문자 버리고 다음 문자 검사하러 넘어감
    					}
    					
    					else { // 탭이 아니면
    						datas[i] = new Node(values[i]); // 그 문자열로 새 노드 생성
    						stk.push(datas[i], tabcnt); // 스택에 넣고
    						Window.getCenterPanel().add(datas[i]); // 가운데 패널에 노드 추가
    						
    						if(datas[i].parentNode != null) {
    						
    							datas[i].x = datas[i].getLocation().x;
    							datas[i].y = datas[i].getLocation().y;
    							datas[i].width = 72;
    							datas[i].height = 30;
    						
    							datas[i].up = new Point(datas[i].x + datas[i].width / 2, datas[i].y); // 선을 연결할 좌표
    							datas[i].down = new Point(datas[i].up.x, datas[i].y + datas[i].height);
    							datas[i].left = new Point(datas[i].x, datas[i].y + datas[i].height / 2);
    							datas[i].right = new Point(datas[i].x + datas[i].width, datas[i].y + datas[i].height / 2);
    							datas[i].center = new Point(datas[i].up.x, datas[i].right.y); 
    						
    						}
    						
    						else { // root면 센터에 배치.
    							datas[i].setBounds(Window.getCenterPanel().getWidth() / 2, Window.getCenterPanel().getHeight() / 2, 72, 30);
    							datas[i].x = datas[i].getLocation().x;
    							datas[i].y = datas[i].getLocation().y;
    							datas[i].width = 72;
    							datas[i].height = 30;
    						}
    						break;
    					}
    				}
    			}    			
    			for(int i = 0; i < datas.length; i++) {
    				if(datas[i].parentNode != null)
    					datas[i].setMyLocation();
    			}

    			Window.getCenterPanel().revalidate();
    			Window.getCenterPanel().repaint();
    		} // 적용 버튼이면 끝
    	}
    }
}