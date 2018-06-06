package MindmapProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Node extends JLabel{
	Node parentNode;
	String str; // 내 이름
	int level; // tab 개수
	int x, y, width, height; // private는 이후에
	Color color;
	
	public Node(String naming){
		super(naming); // 패널에서 보일 노드의 제목
		this.str = naming; // 노드 제목을 알 수 있게.
		NodeListener listener = new NodeListener(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
		
		setHorizontalAlignment(SwingConstants.CENTER);
		setBounds(218, 217, 73, 30); // 후에 계산하기.
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setBackground(Color.RED);
		setOpaque(true);
	}
	
	class NodeListener implements MouseListener, MouseMotionListener{
		Node node;
		NodeListener(Node me){
			this.node = me;
		}
		@Override
		public void mouseDragged(MouseEvent e) { // 마우스를 컴포넌트 위에서 드래그
			Point endP = e.getPoint();
			JLabel la = (JLabel)e.getSource();
			Point p = la.getLocation();
			
			Window.getRightPanel().attriTField[0].setText(node.str);
			Window.getRightPanel().attriTField[1].setText(String.valueOf(node.getX()));
			Window.getRightPanel().attriTField[2].setText(String.valueOf(node.getY()));
			Window.getRightPanel().attriTField[3].setText(String.valueOf(node.getWidth()));
			Window.getRightPanel().attriTField[4].setText(String.valueOf(node.getHeight()));
			//Color
			
			la.setLocation(p.x+endP.x - (int)la.getWidth()/2, p.y+ endP.y - (int)la.getHeight()/2);
			la.getParent().repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) { // 마우스 버튼이 클릭
		}

		@Override
		public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 위에 올라옴
			setBorder(new BevelBorder(BevelBorder.LOWERED));
            setBackground(Color.WHITE);
		}

		@Override
		public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 밖으로 나감
			setBorder(new BevelBorder(BevelBorder.RAISED));
            setBackground(Color.PINK);
		}

		@Override
		public void mousePressed(MouseEvent e) { // 마우스 버튼이 눌러짐
			Window.getRightPanel().node = node;		//클릭한 노드의 속성을 rightPanel에서 바꿀수 있게

			setBorder(new BevelBorder(BevelBorder.LOWERED));
            color = node.getBackground();
            
			Window.getRightPanel().attriTField[0].setText(node.str);
			Window.getRightPanel().attriTField[1].setText(String.valueOf(node.getX()));
			Window.getRightPanel().attriTField[2].setText(String.valueOf(node.getY()));
			Window.getRightPanel().attriTField[3].setText(String.valueOf(node.getWidth()));
			Window.getRightPanel().attriTField[4].setText(String.valueOf(node.getHeight()));
			Window.getRightPanel().attriTField[5].setText(Integer.toHexString(color.getRGB()).substring(2));

            //System.out.println(parentNode.str);		//트리추가 아직이어서 에러뜸
		}

		@Override
		public void mouseReleased(MouseEvent e) {} // 눌러진 마우스 버튼이 떼짐
		
		@Override
		public void mouseMoved(MouseEvent e) {} // 마우스가 컴포넌트 위에서 움직임
	}
}