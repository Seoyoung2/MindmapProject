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
	int childCnt; // 자식의 수
	int childLocation; // 실제 마인드맵 상에서 이미 배치되어있는 자식의 수
	String str; // 내 이름
	int level; // tab 개수
	int x, y, width, height; // private는 이후에

	Point up, down, left, right, center;

	Color color;
	
	public Node(String naming){
		super(naming); // 패널에서 보일 노드의 제목
		this.str = naming; // 노드 제목을 알 수 있게.
		this.childCnt = 0; // 처음엔 자식이고 뭐고 없음
		this.childLocation = 0;
		
		NodeListener listener = new NodeListener(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
		
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setBackground(Color.RED);
		setOpaque(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics g1 = Window.getCenterPanel().getGraphics();
		
		if(this.parentNode != null) {
		int select = selectLinkNode(this);
		
			if(select == 0)
				g1.drawLine(this.up.x, this.up.y, this.parentNode.down.x, this.parentNode.down.y);
			else if(select == 1)
				g1.drawLine(this.up.x, this.up.y, this.parentNode.left.x, this.parentNode.left.y);
			else if(select == 2)
				g1.drawLine(this.up.x, this.up.y, this.parentNode.right.x, this.parentNode.right.y);
			else if(select == 3)
				g1.drawLine(this.down.x, this.down.y, this.parentNode.up.x, this.parentNode.up.y);
			else if(select == 4)
				g1.drawLine(this.down.x, this.down.y, this.parentNode.left.x, this.parentNode.left.y);
			else if(select == 5)
				g1.drawLine(this.down.x, this.down.y, this.parentNode.right.x, this.parentNode.left.y);
			else if(select == 6)
				g1.drawLine(this.left.x, this.left.y, this.parentNode.up.x, this.parentNode.up.y);
			else if(select == 7)
				g1.drawLine(this.left.x, this.left.y, this.parentNode.down.x, this.parentNode.down.y);
			else if(select == 8)
				g1.drawLine(this.left.x, this.left.y, this.parentNode.right.x, this.parentNode.right.y);
			else if(select == 9)
				g1.drawLine(this.right.x, this.right.y, this.parentNode.up.x, this.parentNode.up.y);
			else if(select == 10)
				g1.drawLine(this.right.x, this.right.y, this.parentNode.down.x, this.parentNode.down.y);
			else if(select == 11)
				g1.drawLine(this.right.x, this.right.y, this.parentNode.left.x, this.parentNode.left.y);
		}
		
		super.paintComponent(g);
		repaint();
	}
	
	int selectLinkNode(Node node) {
		double [] dis = new double[12];
			
		dis[0] = getDistance(node.up, node.parentNode.down);
		dis[1] = getDistance(node.up, node.parentNode.left);
		dis[2] = getDistance(node.up, node.parentNode.right);
		dis[3] = getDistance(node.down, node.parentNode.up);
		dis[4] = getDistance(node.down, node.parentNode.left);
		dis[5] = getDistance(node.down, node.parentNode.right);
		dis[6] = getDistance(node.left, node.parentNode.up);
		dis[7] = getDistance(node.left, node.parentNode.down);
		dis[8] = getDistance(node.left, node.parentNode.right);
		dis[9] = getDistance(node.right, node.parentNode.up);
		dis[10] = getDistance(node.right, node.parentNode.down);
		dis[11] = getDistance(node.right, node.parentNode.left);
		
		int ret = 0;
		double minimum = dis[0];
		
		for(int i = 0; i < dis.length; i++)
			if(minimum > dis[i]) {
				minimum = dis[i];
				ret = i;
			}
		
		return ret;
	}
	
	double getDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(Math.abs(p2.x - p1.x), 2) + Math.pow(Math.abs(p2.y - p1.y), 2)); 
	}
	
	void setMyLocation() {
		int distance = 200 / this.level;
		double radius;
		
		if(this.level == 1)
			radius = 360 / parentNode.childCnt * (parentNode.childLocation + 1);
		else
			radius = 360 / (parentNode.childCnt + 1) * (parentNode.childLocation + 1);

		this.x = parentNode.x + (int)(distance * Math.cos(Math.toRadians(radius)));
		this.y = parentNode.y + (int)(distance * Math.sin(Math.toRadians(radius)));
		
		setBounds(this.x, this.y, this.width, this.height);
		this.initializeNode(this);
		parentNode.childLocation++;
	}
	
    void initializeNode(Node node) {
    	node.x = node.getLocation().x;
		node.y = node.getLocation().y;
		
		if(node.width == 0 || node.width == 72)
			node.width = 72;
		
		if(node.height == 0 || node.height == 30)
			node.height = 30;

		node.up = new Point(node.x + node.width / 2, node.y); // 선을 연결할 좌표
		node.down = new Point(node.up.x, node.y + node.height);
		node.left = new Point(node.x, node.y + node.height / 2);
		node.right = new Point(node.x + node.width, node.y + node.height / 2);
		node.center = new Point(node.up.x, node.right.y); 
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
			initializeNode(node);
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