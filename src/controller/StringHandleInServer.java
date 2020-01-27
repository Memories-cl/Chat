package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Database;

public class StringHandleInServer extends Thread{
	private Database data = new Database();//�������ݿ� 
	private DataInputStream dis ;
	private Socket socket = null;
	private boolean flag = true; 
	private StringHandleOutServer so = new StringHandleOutServer();  //������Ϣ 
	private HashMap<String, Socket> map;
	public StringHandleInServer(Socket socket,HashMap<String, Socket> map) throws IOException {
		this.socket = socket;
		dis = new DataInputStream(socket.getInputStream());
		this.map = map;
	}
	
	
	private String getMessage() throws IOException {
		String str = dis.readUTF();
		System.out.println("���յ����Կͻ��˵���Ϣ��"+str);
		return str;
	}
	public void handle(String str) throws IOException, SQLException {
		System.out.println(str.substring(0,1));
		String st =str.substring(0,1);
		String rstr = str;
		if(st.equals("@")) {//������Ϣ
			for(Socket os : map.values()) {
				so.outMassage(rstr, os);
			}
		}else if(st.equals("$")){//ע����Ϣ ��֤������Ϣ  �����1��ע��ɹ� 0Ϊע��ʧ��
           
		
	    	data.connect(); 
//	    	������û���������
			int num = str.indexOf('#');
			String userNa = str.substring(1,num);//�÷�����beginIndexλ����,�ӵ�ǰ�ַ�����ȡ��ʣ����ַ���Ϊһ���µ��ַ�������.
			String password = str.substring(num+1).trim();
			System.out.println(str);
			System.out.println(userNa+"  "+password);
           		if(data.exist("usermassage", userNa))
        		{
        			
            		so.outMassage("$0", socket);
            		System.out.println("�û�ע��ʧ��");
            		socket.close();
        			flag = false;
        		}
        		else
        		{
        			data.insert("usermassage", userNa,password);
        			so.outMassage("$1", socket);
        			System.out.println("�û�ע��ɹ�");
        			socket.close();
        			flag = false;
        		}
 
		}else if(st.equals("&")) {//�˳���Ϣ 
			socket.close();
			map.remove(rstr.substring(1));
			String friendList = "";
			for(String os : map.keySet()) {
				friendList = friendList+ "*" +os;
			}
			for(Socket os : map.values()) {
				so.outMassage(friendList, os);
			}
			
			flag = false;
		}else if(st.equals("#")) {//��¼��֤������Ϣ 
	    	data.connect();
//	    	������û���������
	    	System.out.println(str+"����");
			int num = str.substring(1).indexOf('#');
			String userNa = str.substring(1,num+1);//�÷�����beginIndexλ����,�ӵ�ǰ�ַ�����ȡ��ʣ����ַ���Ϊһ���µ��ַ�������.
			String password = str.substring(num+2).trim();
			System.out.println(userNa+"  "+password);
	    	if(data.exist("usermassage", userNa)) {
	    		ResultSet rs = data.execResult("select UserName,password from usermassage where UserName = ? ",userNa);
	    		rs.next();
	    		System.out.println(rs.getString(1)+"----"+rs.getString(2));
	    		if(userNa.equals(rs.getString(1))&&password.equals(rs.getString(2))) {
	    			map.put(userNa,socket);
	    			so.outMassage("#1", socket);
	    			String friendList = "";
	    			for(String os : map.keySet()) {
	    				friendList = friendList+ "*" +os;
	    			}
	    			for(Socket os : map.values()) {
	    				so.outMassage(friendList, os);
	    			} 
	    		}
	    		else {
	    			so.outMassage("#0", socket);
	    			System.out.println("�û���¼ʧ��");
	    			socket.close();
	    			flag =false;
	    		}
	    	}
			
    		else
    		{
    			so.outMassage("#0", socket);
    			System.out.println("�û���¼ʧ��");
    			socket.close();
    			flag =false;
    		}
		}else {
			System.out.println("�޷�ʶ����Ϣ");
			flag =false;
		}
	}
	public void run() {
		while(flag) {
			try {
				String sst = this.getMessage();
				if(sst.length()<=0)
					continue;
				handle(sst);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("����˽��մ���");
				flag =false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("���ݿ��ѯ����");
				flag =false;
			}
		}
	}
}
