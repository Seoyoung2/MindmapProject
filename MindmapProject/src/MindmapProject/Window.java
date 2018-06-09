package MindmapProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame{
	static LeftPanel TextEditorPane; // 1
	static CenterPanel MindMapPane; // 2a
	static RightPanel AttributePane; // 2b
	
	public Window(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 종료 시 프로그램 종료
		setLayout(new BorderLayout()); // 배치 레이아웃 설정
		setTitle("MindMap Project"); // 제목 설정
		
		TextEditorPane = new LeftPanel();
		MindMapPane = new CenterPanel();
		AttributePane = new RightPanel();
		
		JScrollPane centerScrollPane = new JScrollPane();
		centerScrollPane.setViewportView(MindMapPane);
        JScrollPane rightScrollPane = new JScrollPane();
        rightScrollPane.setViewportView(AttributePane);
        
		JSplitPane subSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TextEditorPane, centerScrollPane);
		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, subSplitPane, rightScrollPane);
		
		subSplitPane.setDividerLocation(360);
		mainSplitPane.setDividerLocation(1240);
			
		setJMenuBar(new MenuBar()); // Frame에 메뉴바 추가
		add(new ToolBar(), BorderLayout.NORTH); // Frame에 툴바 추가
		add(mainSplitPane, BorderLayout.CENTER); // Frame에 패널 추가

		setResizable(false);	// Frame 사이즈 조절못하게..
		setSize(1600, 1000);	// Frame 사이즈 조정
		setVisible(true);
	}
	
	static LeftPanel getLeftPanel() {
		return TextEditorPane;
	}
	
	static CenterPanel getCenterPanel() {
		return MindMapPane;
	}
	
	static RightPanel getRightPanel() {
		return AttributePane;
	}
	
	public static void main(String [] args) {
		new Window();
	}
}