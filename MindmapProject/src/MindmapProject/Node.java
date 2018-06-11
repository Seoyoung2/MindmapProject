package MindmapProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class Node extends JLabel{
	Node parentNode;
	int childCnt; // 자식의 수
	int childLocation; // 실제 마인드맵 상에서 이미 배치되어있는 자식의 수
	String str; // 내 이름
	int level; // tab 개수
	int x, y, width, height; // private는 이후에

	Point up, down, left, right, center;

	Color color, color2;
	
	public Node(String naming){
		super(naming); // 패널에서 보일 노드의 제목
		this.str = naming; // 노드 제목을 알 수 있게.
		this.childCnt = 0; // 처음엔 자식이고 뭐고 없음
		this.childLocation = 0;
		
		NodeListener listener = new NodeListener(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
		setBorder(new NodeDot());	//크기조절하는 점출력하는 보더클래스
		setHorizontalAlignment(SwingConstants.CENTER);
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
		else
			node.width = node.getWidth();
		
		if(node.height == 0 || node.height == 30)
			node.height = 30;
		else
			node.height = node.getHeight();
		
		setBounds(this.x, this.y, this.width, this.height);
		node.up = new Point(node.x + node.width / 2, node.y); // 선을 연결할 좌표
		node.down = new Point(node.up.x, node.y + node.height);
		node.left = new Point(node.x, node.y + node.height / 2);
		node.right = new Point(node.x + node.width, node.y + node.height / 2);
		node.center = new Point(node.up.x, node.right.y); 
    }

	class NodeListener implements MouseListener, MouseMotionListener{
		Node node;
		private int cursor;
        private Point start = null;
        
		NodeListener(Node me){
			this.node = me;
		}
		
		@Override
		public void mouseDragged(MouseEvent e) { // 마우스를 컴포넌트 위에서 드래그
					
			if (start != null) {
				int x = getX();
				int y = getY();
				int width = getWidth();
				int height = getHeight();
				
                int dx = e.getX() - start.x;
                int dy = e.getY() - start.y;

                switch (cursor) {
	                    case Cursor.N_RESIZE_CURSOR:
	                        if (!(height - dy < 30)) {
	                        	setBounds(x, y + dy, width, height - dy);
	                        	node.y += dy;
	                        	node.height -= dy;
	                            getParent().revalidate();
	                        }
	                        break;
	
	                    case Cursor.S_RESIZE_CURSOR:
	                        if (!(height + dy < 30)) {
	                            setBounds(x, y, width, height + dy);
	                            node.height += dy;
	                            start = e.getPoint();
	                            getParent().revalidate();
	                        }
	                        break;
	
	                    case Cursor.W_RESIZE_CURSOR:
	                        if (!(node.width - dx < 50)) {
	                            setBounds(x + dx, y, width - dx, height);
	                            node.x += dx;
	                            node.width -= dx;
	                            getParent().revalidate();
	                        }
	                        break;
	
	                    case Cursor.E_RESIZE_CURSOR:
	                        if (!(width + dx < 50)) {
	                            setBounds(x, y, width + dx, height);
	                            node.width += dx;
	                            start = e.getPoint();
	                            getParent().revalidate();
	                        }
	                        break;
	
	                    case Cursor.NW_RESIZE_CURSOR:
	                        if (!(width - dx < 50) && !(height - dy < 30)) {
	                            setBounds(x + dx, y + dy, width - dx, height - dy);
	                            node.x += dx;
	                            node.y += dy;
	                            node.width -= dx;
	                            node.height -= dy;
	                            getParent().revalidate();
	                        }
	                        break;
	
	                    case Cursor.NE_RESIZE_CURSOR:
	                        if (!(width + dx < 50) && !(height - dy < 30)) {
	                            setBounds(x, y + dy, width + dx, height - dy);
	                            node.y += dy;
	                            node.width += dx;
	                            node.height -= dy;
	                            start = new Point(e.getX(), start.y);
	                            getParent().revalidate();
	                        }
	                        break;
	
	                    case Cursor.SW_RESIZE_CURSOR:
	                        if (!(width - dx < 50) && !(height + dy < 30)) {
	                            setBounds(x + dx, y, width - dx, height + dy);
	                            node.x += dx;
	                            node.width -= dx;
	                            node.height += dy;
	                            start = new Point(start.x, e.getY());
	                            getParent().revalidate();
	                        }
	                        break;
	
	                    case Cursor.SE_RESIZE_CURSOR:
	                        if (!(width + dx < 50) && !(height + dy < 30)) {
	                            setBounds(x, y, width + dx, height + dy);
	                            node.width += dx;
	                            node.height += dy;
	                            start = e.getPoint();
	                            getParent().revalidate();
	                        }
	                        break;
	
	                    case Cursor.MOVE_CURSOR:
		                        Rectangle bounds = getBounds();
		                        bounds.translate(dx, dy);
		                        node.x = dx;
		                        node.y = dy;
		                        setBounds(bounds);
		                        getParent().revalidate();
                	}
                
                setCursor(Cursor.getPredefinedCursor(cursor));
                initializeNode(node);
	            }
			
			Window.getRightPanel().attriTField[0].setText(node.str);
			Window.getRightPanel().attriTField[1].setText(String.valueOf(node.getX()));
			Window.getRightPanel().attriTField[2].setText(String.valueOf(node.getY()));
			Window.getRightPanel().attriTField[3].setText(String.valueOf(node.getWidth()));
			Window.getRightPanel().attriTField[4].setText(String.valueOf(node.getHeight()));
			//Color 변경안되니까 나타날 필요없음
		}

		@Override
		public void mouseClicked(MouseEvent e) { // 마우스 버튼이 클릭
		}

		@Override
		public void mouseEntered(MouseEvent e) { // 마우스가 컴포넌트 위에 올라옴
			requestFocus();
			node.color2 = node.getBackground();
			node.setBackground(new Color((color2.getRed() + 128) % 255, (color2.getGreen() + 128) % 256, (color2.getBlue() + 128) % 256));
		}

		@Override
		public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트 밖으로 나감
			setCursor(Cursor.getDefaultCursor());
			setBackground(node.color2);
		}

		@Override
		public void mousePressed(MouseEvent e) { // 마우스 버튼이 눌러짐
			Window.getRightPanel().node = node;		//클릭한 노드의 속성을 rightPanel에서 바꿀수 있게
            
			Window.getRightPanel().attriTField[0].setText(node.str);
			Window.getRightPanel().attriTField[1].setText(String.valueOf(node.getX()));
			Window.getRightPanel().attriTField[2].setText(String.valueOf(node.getY()));
			Window.getRightPanel().attriTField[3].setText(String.valueOf(node.getWidth()));
			Window.getRightPanel().attriTField[4].setText(String.valueOf(node.getHeight()));
			Window.getRightPanel().attriTField[5].setText(Integer.toHexString(node.color2.getRGB()).substring(2));

			NodeDot dot = (NodeDot)getBorder();
            cursor = dot.getCursor(e);
            start = e.getPoint();
            requestFocus();
            repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			start = null;
		} // 눌러진 마우스 버튼이 떼짐

		@Override
		public void mouseMoved(MouseEvent e) { // 마우스가 컴포넌트 위에서 움직임
			if (hasFocus()) {					// 커서모양 바꾸기
				NodeDot dot = (NodeDot)getBorder();
				setCursor(Cursor.getPredefinedCursor(dot.getCursor(e)));
	        }
		} 
	}
	
	class NodeDot implements Border{
		/*		0		1		2
		 * 		3				4
		 * 		5		6		7	*/
		int locations[] = {
		        SwingConstants.NORTH_WEST, SwingConstants.NORTH, SwingConstants.NORTH_EAST,
		        SwingConstants.WEST, SwingConstants.EAST,
		        SwingConstants.SOUTH_WEST, SwingConstants.SOUTH, SwingConstants.SOUTH_EAST
		    };
		int cursors[] = {
		        Cursor.NW_RESIZE_CURSOR, Cursor.N_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
		        Cursor.W_RESIZE_CURSOR, Cursor.E_RESIZE_CURSOR, 
		        Cursor.SW_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
		    };
		
		@Override
		public Insets getBorderInsets(Component c) {	//Border클래스 메소드들 오버라이딩
			return new Insets(8,8,8,8);
		}

		@Override
		public boolean isBorderOpaque() {
			return false;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        if (c.hasFocus()) {
	            for (int i = 0; i < 8; i++) {	// 노드의 8개 점 출력
	                Rectangle rect = getRectangle(x, y, width, height, locations[i]);
	                g.setColor(Color.WHITE);
	                g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
	                g.setColor(Color.BLACK);
	                g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
	            }
	        }
		}
		 private Rectangle getRectangle(int x, int y, int w, int h, int location) {	//점8개 각각의 위치지정
		        switch (location) {
		            case SwingConstants.NORTH:
		                return new Rectangle(x + w / 2 - 4, y, 8, 8);
		            case SwingConstants.SOUTH:
		                return new Rectangle(x + w / 2 - 4, y + h - 8, 8, 8);
		            case SwingConstants.WEST:
		                return new Rectangle(x, y + h / 2 - 4, 8, 8);
		            case SwingConstants.EAST:
		                return new Rectangle(x + w - 8, y + h / 2 - 4, 8, 8);
		            case SwingConstants.NORTH_WEST:
		                return new Rectangle(x, y, 8, 8);
		            case SwingConstants.NORTH_EAST:
		                return new Rectangle(x + w - 8, y, 8, 8);
		            case SwingConstants.SOUTH_WEST:
		                return new Rectangle(x, y + h - 8, 8, 8);
		            case SwingConstants.SOUTH_EAST:
		                return new Rectangle(x + w - 8, y + h - 8, 8, 8);
		        }
		        return null;
		    }

		    public int getCursor(MouseEvent e) {
		        Component c = e.getComponent();
		        int w = c.getWidth();
		        int h = c.getHeight();

		        for (int i = 0; i < 8; i++) {	//점 위치에 맞는 커서모양
		            Rectangle rect = getRectangle(0, 0, w, h, locations[i]);
		            if (rect.contains(e.getPoint())) {
		                return cursors[i];
		            }
		        }
		        return Cursor.MOVE_CURSOR;
		    }
	}
}