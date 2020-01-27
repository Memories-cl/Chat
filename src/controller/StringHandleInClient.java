package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Platform;

/*
 * ���д���Ҫ���յ���Ϣ
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


public class StringHandleInClient extends Thread{ 
	private Socket socket = null;
	String userName = null; //���������û���
	private Chat ch = null;
	private boolean flag =true;
	public StringHandleInClient(Socket socket,Chat ch) {
		this.socket = socket;
		this.ch = ch;
	}
	
	public void run() {
		WorkClient wc = new WorkClient();  //�ͻ��˲���֧����
		while(true) {
			if(socket != null) {
				System.out.println("socket������");
				break;
				
			}
		}
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("��������ֵ����");
		}//������Ϣ
		while(flag) {
			if(socket==null)
			{
				break;
			}
			String str = "";
			try {
				str = dis.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("�����ַ�����");
				break;
			}
			String st =str.substring(0,1);
			System.out.println(st+"������Ϣ����");
			String rstr = str.substring(1);
			System.out.println("���ǽ��յ�������"+rstr);
			System.out.println("�ͻ����յ���"+str);
			if(st.equals("$")){//ע����Ϣ ��֤������Ϣ  �����1��ע��ɹ� 0Ϊע��ʧ��
	           	wc.registerMassage(rstr);
	           	try {
					socket.close();
					socket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	
	        	flag =false;
			}else if(st.equals("@")) {//������Ϣ 
				wc.chatMassage(rstr, ch);
			}else if(st.equals("*")) {//�����б���Ϣ
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	wc.friendMassage(rstr, ch);
				    }
				});
				
			}else {
				System.out.println("�޷�ʶ����Ϣ");
				break;
			}
		}
		System.out.println("�ѶϿ�����");
	}
		
		
}

















