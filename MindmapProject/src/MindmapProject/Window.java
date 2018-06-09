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
		
		JScrollPane centerScrollPane = new JScrollPane();
		centerScrollPane.setViewportView(MindMapPane);
        JScrollPane rightScrollPane = new JScrollPane();
        rightScrollPane.setViewportView(AttributePane);
        
		JSplitPane subSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TextEditorPane, centerScrollPane);
		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, subSplitPane, rightScrollPane);
		
		subSplitPane.setDividerLocation(360);
		mainSplitPane.setDividerLocation(1240);
			
		setJMenuBar(new MenuBar()); // Frame�� �޴��� �߰�
		add(new ToolBar(), BorderLayout.NORTH); // Frame�� ���� �߰�
		add(mainSplitPane, BorderLayout.CENTER); // Frame�� �г� �߰�

		setResizable(false);	// Frame ������ �������ϰ�..
		setSize(1600, 1000);	// Frame ������ ����
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