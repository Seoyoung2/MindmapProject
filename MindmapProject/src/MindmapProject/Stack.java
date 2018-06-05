package MindmapProject;

public class Stack {
	Node [] arr;
	int cnt; // top의 개념, arr안의 수는 cnt + 1임.
	
	Stack(int len) {
		cnt = -1;
		arr = new Node[len];
	}
/*	
	void destruction() {
		while(cnt != -1) {
			arr[cnt].parentNode = null;
			--cnt;
		}
	}
*/
	Node top() {
//		System.out.println("top func");
		return arr[cnt];
	}
	
	void push(Node data, int tabcnt) {
	//	System.out.println("push func");
		data.level = tabcnt;
		
		while(isEmpty() == false) {
			if(top().level == data.level - 1) { // 부모노드이면
				data.parentNode = top();
				break;
			}			
			else
				pop();			
		}
		
		if(isEmpty()) // 스택이 비어있는 경우
			data.parentNode = null;

		arr[++cnt] = data;
	}
	
	Node pop() {
		if(isEmpty())
			return null;
		else
			return arr[cnt--];
	}
	
	boolean isEmpty() {
		if(cnt == -1) {
			return true;	
		}
		else
			return false;
	}
}
