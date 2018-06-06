package MindmapProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class Node extends JLabel{
	Node parentNode;
	int childCnt; // �ڽ��� ��
	int childLocation; // ���� ���ε�� �󿡼� �̹� ��ġ�Ǿ��ִ� �ڽ��� ��
	String str; // �� �̸�
	int level; // tab ����
	int x, y, width, height; // private�� ���Ŀ�

	Point up, down, left, right, center;

	Color color;
	
	public Node(String naming){
		super(naming); // �гο��� ���� ����� ����
		this.str = naming; // ��� ������ �� �� �ְ�.
		this.childCnt = 0; // ó���� �ڽ��̰� ���� ����
		this.childLocation = 0;
		
		NodeListener listener = new NodeListener(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
		
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setBackground(Color.RED);
		setOpaque(true);
	}
	
	void setMyLocation() {
		int distance = 200 / this.level;
		double radius;
	//	Graphics g = Window.getCenterPanel().getGraphics();
		radius = 360 / (parentNode.childCnt) * parentNode.childLocation;
		
		this.x = parentNode.x + (int)(distance * Math.cos(Math.toRadians(radius)));
		this.y = parentNode.y + (int)(distance * Math.sin(Math.toRadians(radius)));
		
		setBounds(this.x, this.y, this.width, this.height);
		parentNode.childLocation++;
	//	paintComponent(Window.getCenterPanel().getGraphics());
	}
/*	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		
		int val = getMinimumDistance();
			
		if(val == 0)
			g.drawLine(this.up.x, this.up.y, parentNode.down.x, parentNode.down.y);
		else if(val == 1)
			g.drawLine(this.up.x, this.up.y, parentNode.left.x, parentNode.left.y);
		else if(val == 2)
			g.drawLine(this.up.x, this.up.y, parentNode.right.x, parentNode.right.y);
		else if(val == 3)
			g.drawLine(this.down.x, this.down.y, parentNode.up.x, parentNode.up.y);
		else if(val == 4)
			g.drawLine(this.down.x, this.down.y, parentNode.left.x, parentNode.left.y);
		else if(val == 5)
			g.drawLine(this.down.x, this.down.y, parentNode.right.x, parentNode.right.y);
		else if(val == 6)
			g.drawLine(this.left.x, this.left.y, parentNode.up.x, parentNode.up.y);
		else if(val == 7)
			g.drawLine(this.left.x, this.left.y, parentNode.down.x, parentNode.down.y);
		else if(val == 8)
			g.drawLine(this.left.x, this.left.y, parentNode.right.x, parentNode.right.y);
		else if(val == 9)
			g.drawLine(this.right.x, this.right.y, parentNode.up.x, parentNode.up.y);
		else if(val == 10)
			g.drawLine(this.right.x, this.right.y, parentNode.down.x, parentNode.down.y);
		else if(val == 11)
			g.drawLine(this.right.x, this.right.y, parentNode.left.x, parentNode.left.y);
			
	}

	int getMinimumDistance() {
		double [] ret = new double[12];
		double minimum;
		int returnValue = 0;
		
		ret[0] = this.up.distance(parentNode.down);
		ret[1] = this.up.distance(parentNode.left);
		ret[2] = this.up.distance(parentNode.right);
		ret[3] = this.down.distance(parentNode.up);
		ret[4] = this.down.distance(parentNode.left);
		ret[5] = this.down.distance(parentNode.right);
		ret[6] = this.left.distance(parentNode.up);
		ret[7] = this.left.distance(parentNode.down);
		ret[8] = this.left.distance(parentNode.right);
		ret[9] = this.right.distance(parentNode.up);
		ret[10] = this.right.distance(parentNode.down);
		ret[11] = this.right.distance(parentNode.left);
		
		minimum = ret[0];
		
		for(int i=0; i<12; i++)
			if(minimum > ret[i]) {
				minimum = ret[i];
				returnValue = i;
			}
		
		return returnValue;
	}
*/	
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