package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Platform;

/*
 * 集中处理要接收的信息
 * 
 * 客户端与服务端相互发送消息时前缀意义：
 * 
 * "#"  登录消息
 * "$"  注册消息
 * "&"  退出消息
 * "@"  聊天消息
 * "*"  代表传过来的是好友列表 后面均以"*"分隔好友名
 * 
*/


public class StringHandleInClient extends Thread{ 
	private Socket socket = null;
	String userName = null; //用来保存用户名
	private Chat ch = null;
	private boolean flag =true;
	public StringHandleInClient(Socket socket,Chat ch) {
		this.socket = socket;
		this.ch = ch;
	}
	
	public void run() {
		WorkClient wc = new WorkClient();  //客户端操作支持类
		while(true) {
			if(socket != null) {
				System.out.println("socket已连接");
				break;
				
			}
		}
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("输入流赋值出错");
		}//接收消息
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
				System.out.println("接收字符出错");
				break;
			}
			String st =str.substring(0,1);
			System.out.println(st+"这是消息类型");
			String rstr = str.substring(1);
			System.out.println("这是接收到的数据"+rstr);
			System.out.println("客户端收到："+str);
			if(st.equals("$")){//注册消息 验证返回消息  如果是1则注册成功 0为注册失败
	           	wc.registerMassage(rstr);
	           	try {
					socket.close();
					socket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	
	        	flag =false;
			}else if(st.equals("@")) {//聊天消息 
				wc.chatMassage(rstr, ch);
			}else if(st.equals("*")) {//好友列表消息
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	wc.friendMassage(rstr, ch);
				    }
				});
				
			}else {
				System.out.println("无法识别消息");
				break;
			}
		}
		System.out.println("已断开连接");
	}
		
		
}

















