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
	
	static boolean saveCnt = true;	//泥섏쓬 ���옣�븯�뒗 �뙆�씪�뱾�� �떎�씠�뼹濡쒓렇 �쑉寃�   (true硫� �쑉怨� false硫� �븞�쑙)
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
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter("json�뙆�씪", "json");
	    	chooser.setCurrentDirectory(new File("C:\\Users\\CEO\\Desktop\\MindmapProject\\src"));
		 		//�뙆�씪寃쎈줈 諛붽퓭�꽌 �떎�뻾�빐�빞�맠�뀋�뀋�뀋�뀋
	    	chooser.setFileFilter(filter);
	    	int ret = chooser.showOpenDialog(Window.getCenterPanel());
	    	
	    	if(ret == JFileChooser.APPROVE_OPTION) {
	    		saveFile = chooser.getSelectedFile().toString();
	    		//�뙆�씪 �솗�옣�옄(.json)�쓣 �엯�젰�븯吏� �븡�븯�쓣 寃쎌슦 �솗�옣�옄 異붽�
	    		if(!saveFile.endsWith(".json")) {
	    			saveFile += ".json";
	    		}	
	        	
	        	//�떎�씠�뼹濡쒓렇�뿉�꽌 �뿴湲곕쾭�듉 �닃���쓣�븣 json�뙆�씪 �뙆�떛
	    		Window.getCenterPanel().removeAll();
	        	JSONParser parser = new JSONParser();
	        	
	        	try {
					Object obj = parser.parse(new FileReader(saveFile));
					JSONObject jObject = (JSONObject)obj;
					
					JSONArray jArray = (JSONArray)jObject.get("node");
					Window.getLeftPanel().datas = new Node[jArray.size()];
					
					Node [] nodes = Window.getLeftPanel().datas;
					
					String [] values = ((String)jObject.get("textarea")).split("\n");
					Stack stk = new Stack(values.length);
					int tabcnt, sublen;
					
		   			for(int i = 0; i < values.length; i++) { // 스택을 이용한 트리 구조 형성
						tabcnt = 0; // \t 개수, 레벨을 판정함
						sublen = values[i].length(); // 문자열 길이
						
						for(int j = 0; j < sublen; j++) {
							if(values[i].charAt(0)=='\t') { // 탭이면
								++tabcnt; // 탭개수 올리고
								values[i] = values[i].substring(1); // 현재 탭 문자 버리고 다음 문자 검사하러 넘어감
							}
						
							else { // 탭이 아니면
								nodes[i] = new Node(values[i]); // 그 문자열로 새 노드 생성
								stk.push(nodes[i], tabcnt); // 스택에 넣고
								break;
							}
						}
					}
		   			
					
					for(int i=0; i<nodes.length /*jArray.size()*/; i++) {
						JSONObject jNodeInfo = (JSONObject)jArray.get(i);
						nodes[i].str = (String)jNodeInfo.get("text");
						nodes[i].x = (int)(long)jNodeInfo.get("x");
						nodes[i].y = (int)(long)jNodeInfo.get("y");
						nodes[i].width = (int)(long)jNodeInfo.get("width");
						nodes[i].height = (int)(long)jNodeInfo.get("height");
						
						nodes[i].color = new Color(Integer.parseInt((String)jNodeInfo.get("color"),16));
						nodes[i].setBounds(nodes[i].x,nodes[i].y,nodes[i].width,nodes[i].height);
						
						nodes[i].initializeNode(nodes[i]);
						
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
	    	
	    	if(saveCnt == true) {	//泥섏쓬���옣�븯�뒗 嫄곕씪硫� 臾댁“嫄� �떎�씠�뼹濡쒓렇 �깮�꽦
		      	JFileChooser chooser = new JFileChooser();
		    	FileNameExtensionFilter filter = new FileNameExtensionFilter("json�뙆�씪", "json");
		    	chooser.setCurrentDirectory(new File("C:\\Users\\CEO\\Desktop\\MindmapProject\\src"));	//�떎�씠�뼹濡쒓렇 �뵒�뤃�듃寃쎈줈
		    	chooser.setFileFilter(filter);
		    	int ret = chooser.showSaveDialog(Window.getCenterPanel());
		    	
		    	if(ret == JFileChooser.APPROVE_OPTION) {
		    		
		    		saveFile = chooser.getSelectedFile().toString();
		    		//�뙆�씪 �솗�옣�옄(.json)�쓣 �엯�젰�븯吏� �븡�븯�쓣 寃쎌슦 �솗�옣�옄 異붽�
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
	    	jObject.put("textarea", Window.getLeftPanel().getTextArea().getText());	//textArea�궡�슜 臾몄옄�뿴濡� �떎 ���옣
	    	
	 	   try {
	 		   saveCnt = false;
	 		   FileWriter file = new FileWriter(saveFile);
	 		   file.write(jObject.toJSONString());
	 		   System.out.println(jObject.toJSONString()); 				// �뼱�뼸寃� ���옣�릺�굹 �뀒�뒪�듃�슜
	 		   file.flush();
	 		   file.close();
	 		   JOptionPane.showMessageDialog(Window.getCenterPanel(), "���옣�릺�뿀�뒿�땲�떎^0^");
	 		   
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    static void selectClose() {	//洹몃깷 醫낅즺�븿
	    	System.exit(0);
	    }
}
