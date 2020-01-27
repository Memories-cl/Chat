package AppServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.StringHandleInServer;

public class ServerMainApp {
	public static HashMap<String, Socket> map = new HashMap<>();//保存用户名和对应socket接口

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket  server= new ServerSocket(9000);  
		while(true) {
			Socket socket = server.accept();
			System.out.println("已连接");
			new StringHandleInServer(socket, map).start();  
		}
		
	}

}
