package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class StringHandleOutServer {

	public void outMassage(String str, Socket socket) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("�������������������"+str);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(str);  
		out.flush();
	} 

	;
}
