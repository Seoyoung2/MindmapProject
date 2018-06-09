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
        textArea.setFont(new Font("돋움",  Font.PLAIN, 24));
        JScrollPane textScroll = new JScrollPane(textArea);
        add(textScroll, BorderLayout.CENTER);
        
        JButton leftBtn = new JButton("적용");
		add(leftBtn, BorderLayout.SOUTH);
		leftBtn.addActionListener(new LeftButtonListener());
		leftBtn.setBackground(new Color(0xB93232)); 
		leftBtn.setFont(new Font("돋움",  Font.BOLD, 18));
		leftBtn.setPreferredSize(new Dimension(30, 50));
    }
    
    static JTextArea getTextArea() {
    	return textArea;
    }
    
    class LeftButtonListener implements ActionListener{
    	int sublen;
    	int tabcnt;
    	@Override
    	
    	public void actionPerformed(ActionEvent e) {

			if(textArea.getText().equals("")) {		//아무것도 입력안하고 적용버튼 눌렀을때
				return;
			}
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
							
							if(datas[i].parentNode != null)
								datas[i].initializeNode(datas[i]);
							
							else { // root면 센터에 배치.
								datas[i].setBounds(Window.getCenterPanel().getWidth() / 2, Window.getCenterPanel().getHeight() / 2, 72, 30);
								datas[i].initializeNode(datas[i]);
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
		} // 적용 버튼 끝
    }

}