package MindmapProject;

public class Stack {
	Node [] arr;
	int cnt; // top�� ����, arr���� ���� cnt + 1��.
	
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
			if(top().level == data.level - 1) { // �θ����̸�
				data.parentNode = top();
				break;
			}			
			else
				pop();			
		}
		
		if(isEmpty()) // ������ ����ִ� ���
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
