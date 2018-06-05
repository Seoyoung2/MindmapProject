package MindmapProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame{
	static LeftPanel TextEditorPane; // 1
	static CenterPanel MindMapPane; // 2a
	static RightPanel AttributePane; // 2b
	
	public Window(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ������ ���� �� ���α׷� ����
		setLayout(new BorderLayout()); // ��ġ ���̾ƿ� ����
		setTitle("MindMap Project"); // ���� ����
		
		TextEditorPane = new LeftPanel();
		MindMapPane = new CenterPanel();
		AttributePane = new RightPanel();
		
		JSplitPane subSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TextEditorPane, MindMapPane);
		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, subSplitPane, AttributePane);
		
		subSplitPane.setDividerLocation(320);
		mainSplitPane.setDividerLocation(960);
			
		setJMenuBar(new MenuBar()); // Frame�� �޴��� �߰�
		add(new ToolBar(), BorderLayout.NORTH); // Frame�� ���� �߰�
		add(mainSplitPane, BorderLayout.CENTER); // Frame�� �г� �߰�

		setSize(1280, 720);	// Frame ������ ����
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