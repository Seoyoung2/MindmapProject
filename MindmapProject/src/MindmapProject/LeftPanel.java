package MindmapProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MindmapProject.Window;

public class LeftPanel extends JPanel{
	JPanel leftPanel;
	static JTextArea textArea; // �Է��� ���
	Stack stk; // ���� �ڷᱸ�� ����� ����
	static Node[] datas;

    public LeftPanel() {
    	setLayout(new BorderLayout());
    	leftPanel = new JPanel();
    	
		setBackground(Color.WHITE);
		
		textArea = new JTextArea(5, 25);
		
        textArea.setEditable(true); // ���������ϰ�
        textArea.setTabSize(4); 
        add(textArea, BorderLayout.CENTER);
        
        JButton leftBtn = new JButton("����");
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
    		
    		if(b.getText().equals("����")) { // ���� ��ư�̸� // ���� if���� �ʿ��Ѱ�
    			Window.getCenterPanel().removeAll(); // centerpanel �ʱ�ȭ
    			String[] values = textArea.getText().split("\n"); // �ະ�� �߶�
    			stk = new Stack(values.length);
    			
    			datas = new Node[values.length];
   
    			for(int i = 0; i < values.length; i++) { // ������ �̿��� Ʈ�� ���� ����
    				tabcnt = 0; // \t ����, ������ ������
    				sublen = values[i].length(); // ���ڿ� ����
    				for(int j = 0; j < sublen; j++) {
    					if(values[i].charAt(0)=='\t') { // ���̸�
    						++tabcnt; // �ǰ��� �ø���
    						values[i] = values[i].substring(1); // ���� �� ���� ������ ���� ���� �˻��Ϸ� �Ѿ
    					}
    					
    					else { // ���� �ƴϸ�
    						datas[i] = new Node(values[i]); // �� ���ڿ��� �� ��� ����
    						stk.push(datas[i], tabcnt); // ���ÿ� �ְ�
    						Window.getCenterPanel().add(datas[i]); // ��� �гο� ��� �߰�
    						
    						if(datas[i].parentNode != null) {
    						
    							datas[i].x = datas[i].getLocation().x;
    							datas[i].y = datas[i].getLocation().y;
    							datas[i].width = 72;
    							datas[i].height = 30;
    						
    							datas[i].up = new Point(datas[i].x + datas[i].width / 2, datas[i].y); // ���� ������ ��ǥ
    							datas[i].down = new Point(datas[i].up.x, datas[i].y + datas[i].height);
    							datas[i].left = new Point(datas[i].x, datas[i].y + datas[i].height / 2);
    							datas[i].right = new Point(datas[i].x + datas[i].width, datas[i].y + datas[i].height / 2);
    							datas[i].center = new Point(datas[i].up.x, datas[i].right.y); 
    						
    						}
    						
    						else { // root�� ���Ϳ� ��ġ.
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
    		} // ���� ��ư�̸� ��
    	}
    }
}