package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
 * 集中处理要发送的信息
 * 
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
public class StringHandleOutClient {
	

	
	public void hand(String str,Socket socket) {
		
		try {
		
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			System.out.println("即将发送"+str);
			out.writeUTF(str); 
			System.out.println(str+"被发送");
			out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("发送失败");
			e.printStackTrace();
		} 
	}
}




















