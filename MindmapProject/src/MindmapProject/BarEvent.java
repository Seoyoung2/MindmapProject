package MindmapProject;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class BarEvent {
	
	static boolean saveCnt = true;	//ó�� �����ϴ� ���ϵ��� ���̾�α� �߰�   (true�� �߰� false�� �ȶ�)
	static String saveFile = null;
	
	 static void selectNew() {
		 saveCnt = true;
		 LeftPanel.getTextArea().setText("");
		 Window.getCenterPanel().removeAll();
		 Window.getCenterPanel().repaint();
		 Window.getCenterPanel().revalidate();
	    	for(int i=0; i<6; i++) {
	    		Window.getRightPanel().attriTField[i].setText("");
	    	}
	 }
	
	 static void selectOpen() {
			JFileChooser chooser = new JFileChooser();
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter("json����", "json");
	    	chooser.setCurrentDirectory(new File("C:\\Users\\CEO\\Desktop\\MindmapProject\\src"));
	    	chooser.setFileFilter(filter);
	    	int ret = chooser.showOpenDialog(Window.getCenterPanel());
	    	
	    	if(ret == JFileChooser.APPROVE_OPTION) {
	    		saveFile = chooser.getSelectedFile().toString();
	    		//���� Ȯ����(.json)�� �Է����� �ʾ��� ��� Ȯ���� �߰�
	    		if(!saveFile.endsWith(".json")) {
	    			saveFile += ".json";
	    		}	
	        	
	        	//���̾�α׿��� �����ư �������� json���� �Ľ�
	    		Window.getCenterPanel().removeAll();
	        	JSONParser parser = new JSONParser();
	        	try {
					Object obj = parser.parse(new FileReader(saveFile));
					JSONObject jObject = (JSONObject)obj;
					JSONArray jArray = (JSONArray)jObject.get("node");
					
					Window.getLeftPanel().datas = new Node[jArray.size()];
					Node[] nodes = Window.getLeftPanel().datas;
					
					for(int i=0; i<jArray.size(); i++) {
						JSONObject jNodeInfo = (JSONObject)jArray.get(i);
						nodes[i] = new Node((String)jNodeInfo.get("text"));
						nodes[i].x = (int)(long)jNodeInfo.get("x");
						nodes[i].y = (int)(long)jNodeInfo.get("y");
						nodes[i].width = (int)(long)jNodeInfo.get("width");
						nodes[i].height = (int)(long)jNodeInfo.get("height");
						nodes[i].color = new Color(Integer.parseInt((String)jNodeInfo.get("color"),16));
						nodes[i].setBounds(nodes[i].x,nodes[i].y,nodes[i].width,nodes[i].height);
						nodes[i].setBackground(nodes[i].color);
						
						Window.getCenterPanel().add(nodes[i]);
						Window.getCenterPanel().revalidate();
						Window.getCenterPanel().repaint();
	    			
					}
					
					Window.getLeftPanel().getTextArea().setText((String)jObject.get("textarea"));
					saveCnt = false;
	        	
	        	} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	 	
	    }
	 
	    static void selectSave() {
	    	
	    	if(saveCnt == true) {	//ó�������ϴ� �Ŷ�� ������ ���̾�α� ����
		      	JFileChooser chooser = new JFileChooser();
		    	FileNameExtensionFilter filter = new FileNameExtensionFilter("json����", "json");
		    	chooser.setCurrentDirectory(new File("C:\\Users\\CEO\\Desktop\\MindmapProject\\src"));
		    	chooser.setFileFilter(filter);
		    	int ret = chooser.showSaveDialog(Window.getCenterPanel());
		    	
		    	if(ret == JFileChooser.APPROVE_OPTION) {
		    		
		    		saveFile = chooser.getSelectedFile().toString();
		    		//���� Ȯ����(.json)�� �Է����� �ʾ��� ��� Ȯ���� �߰�
		    		if(!saveFile.endsWith(".json")) {
		    			saveFile += ".json";
		    		}	
		    	}
		    	else { return; }
	    	}
	 
	    	JSONObject jObject = new JSONObject();
	    	JSONArray jArray = new JSONArray();
	    	
	    	for(int i=0; i<Window.getLeftPanel().datas.length;i++) {
	    		JSONObject jNodeInfo = new JSONObject();
	    		
		    	jNodeInfo.put("text", Window.getLeftPanel().datas[i].str);
		    	jNodeInfo.put("x", Window.getLeftPanel().datas[i].getX());
		    	jNodeInfo.put("y", Window.getLeftPanel().datas[i].getY());
		    	jNodeInfo.put("width", Window.getLeftPanel().datas[i].getWidth());
		    	jNodeInfo.put("height", Window.getLeftPanel().datas[i].getHeight());
		    	jNodeInfo.put("color", Integer.toHexString(Window.getLeftPanel().datas[i].getBackground().getRGB()).substring(2));
		    	
		    	jArray.add(jNodeInfo);
		    }
	    	jObject.put("node", jArray);
	    	jObject.put("textarea", Window.getLeftPanel().getTextArea().getText());	//textArea���� ���ڿ��� �� ����
	    	
	 	   try {
	 		   saveCnt = false;
	 		   FileWriter file = new FileWriter(saveFile);
	 		   file.write(jObject.toJSONString());
	 		   System.out.println(jObject.toJSONString()); 				// ��� ����ǳ� �׽�Ʈ��
	 		   file.flush();
	 		   file.close();
	 		   JOptionPane.showMessageDialog(Window.getCenterPanel(), "����Ǿ����ϴ�^0^");
	 		   
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    static void selectClose() {	//�׳� ������
	    	System.exit(0);
	    }
}
