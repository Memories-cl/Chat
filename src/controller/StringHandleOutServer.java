package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class StringHandleOutServer {

	public void outMassage(String str, Socket socket) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("进入服务器发送区发送"+str);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(str);  
		out.flush();
	} 

	;
}
