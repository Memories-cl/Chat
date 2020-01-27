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
	private Database data = new Database();//连接数据库 
	private DataInputStream dis ;
	private Socket socket = null;
	private boolean flag = true; 
	private StringHandleOutServer so = new StringHandleOutServer();  //发送消息 
	private HashMap<String, Socket> map;
	public StringHandleInServer(Socket socket,HashMap<String, Socket> map) throws IOException {
		this.socket = socket;
		dis = new DataInputStream(socket.getInputStream());
		this.map = map;
	}
	
	
	private String getMessage() throws IOException {
		String str = dis.readUTF();
		System.out.println("接收到来自客户端的消息："+str);
		return str;
	}
	public void handle(String str) throws IOException, SQLException {
		System.out.println(str.substring(0,1));
		String st =str.substring(0,1);
		String rstr = str;
		if(st.equals("@")) {//聊天消息
			for(Socket os : map.values()) {
				so.outMassage(rstr, os);
			}
		}else if(st.equals("$")){//注册消息 验证返回消息  如果是1则注册成功 0为注册失败
           
		
	    	data.connect(); 
//	    	分离出用户名和密码
			int num = str.indexOf('#');
			String userNa = str.substring(1,num);//该方法从beginIndex位置起,从当前字符串中取出剩余的字符作为一个新的字符串返回.
			String password = str.substring(num+1).trim();
			System.out.println(str);
			System.out.println(userNa+"  "+password);
           		if(data.exist("usermassage", userNa))
        		{
        			
            		so.outMassage("$0", socket);
            		System.out.println("用户注册失败");
            		socket.close();
        			flag = false;
        		}
        		else
        		{
        			data.insert("usermassage", userNa,password);
        			so.outMassage("$1", socket);
        			System.out.println("用户注册成功");
        			socket.close();
        			flag = false;
        		}
 
		}else if(st.equals("&")) {//退出消息 
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
		}else if(st.equals("#")) {//登录验证返回消息 
	    	data.connect();
//	    	分离出用户名和密码
	    	System.out.println(str+"这是");
			int num = str.substring(1).indexOf('#');
			String userNa = str.substring(1,num+1);//该方法从beginIndex位置起,从当前字符串中取出剩余的字符作为一个新的字符串返回.
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
	    			System.out.println("用户登录失败");
	    			socket.close();
	    			flag =false;
	    		}
	    	}
			
    		else
    		{
    			so.outMassage("#0", socket);
    			System.out.println("用户登录失败");
    			socket.close();
    			flag =false;
    		}
		}else {
			System.out.println("无法识别消息");
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
				System.out.println("服务端接收错误");
				flag =false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("数据库查询错误");
				flag =false;
			}
		}
	}
}
