package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
 * ���д���Ҫ���͵���Ϣ
 * 
 * 
 * �ͻ����������໥������Ϣʱǰ׺���壺
 * 
 * "#"  ��¼��Ϣ
 * "$"  ע����Ϣ
 * "&"  �˳���Ϣ
 * "@"  ������Ϣ
 * "*"  �����������Ǻ����б� �������"*"�ָ�������
 * 
*/
public class StringHandleOutClient {
	

	
	public void hand(String str,Socket socket) {
		
		try {
		
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			System.out.println("��������"+str);
			out.writeUTF(str); 
			System.out.println(str+"������");
			out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("����ʧ��");
			e.printStackTrace();
		} 
	}
}




















