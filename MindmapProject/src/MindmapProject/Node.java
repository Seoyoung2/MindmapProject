package MindmapProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Node extends JLabel{
	Node parentNode;
	String str; // �� �̸�
	int level; // tab ����
	int x, y, width, height; // private�� ���Ŀ�
	Color color;
	
	public Node(String naming){
		super(naming); // �гο��� ���� ����� ����
		this.str = naming; // ��� ������ �� �� �ְ�.
		NodeListener listener = new NodeListener(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
		
		setHorizontalAlignment(SwingConstants.CENTER);
		setBounds(218, 217, 73, 30); // �Ŀ� ����ϱ�.
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
		public void mouseDragged(MouseEvent e) { // ���콺�� ������Ʈ ������ �巡��
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
		public void mouseClicked(MouseEvent e) { // ���콺 ��ư�� Ŭ��
		}

		@Override
		public void mouseEntered(MouseEvent e) { // ���콺�� ������Ʈ ���� �ö��
			setBorder(new BevelBorder(BevelBorder.LOWERED));
            setBackground(Color.WHITE);
		}

		@Override
		public void mouseExited(MouseEvent e) { // ���콺�� ������Ʈ ������ ����
			setBorder(new BevelBorder(BevelBorder.RAISED));
            setBackground(Color.PINK);
		}

		@Override
		public void mousePressed(MouseEvent e) { // ���콺 ��ư�� ������
			Window.getRightPanel().node = node;		//Ŭ���� ����� �Ӽ��� rightPanel���� �ٲܼ� �ְ�

			setBorder(new BevelBorder(BevelBorder.LOWERED));
            color = node.getBackground();
            
			Window.getRightPanel().attriTField[0].setText(node.str);
			Window.getRightPanel().attriTField[1].setText(String.valueOf(node.getX()));
			Window.getRightPanel().attriTField[2].setText(String.valueOf(node.getY()));
			Window.getRightPanel().attriTField[3].setText(String.valueOf(node.getWidth()));
			Window.getRightPanel().attriTField[4].setText(String.valueOf(node.getHeight()));
			Window.getRightPanel().attriTField[5].setText(Integer.toHexString(color.getRGB()).substring(2));

            //System.out.println(parentNode.str);		//Ʈ���߰� �����̾ ������
		}

		@Override
		public void mouseReleased(MouseEvent e) {} // ������ ���콺 ��ư�� ����
		
		@Override
		public void mouseMoved(MouseEvent e) {} // ���콺�� ������Ʈ ������ ������
	}
}