package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Register extends Application {
	double x1;
	double y1;
	double x_stage;
	double y_stage;
	
	private Socket socket = null;
	private Stage primaryStage;
    private AnchorPane rootLayout;
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

	}
	 public void initRootLayout() throws IOException {
		// Load root layout from fxml file.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Login.class.getResource("../view/Register.fxml"));
         rootLayout = (AnchorPane) loader.load();
         primaryStage.initStyle(StageStyle.TRANSPARENT);//���������û��(��С�� ��� �ر�)
         primaryStage.setResizable(false);
         // Show the scene containing the root layout.
         Scene scene = new Scene(rootLayout);
         primaryStage.setScene(scene);
         primaryStage.show();
        
//         ע���¼���
         scene.lookup("#buttonOK").setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					String userN = ((TextField)scene.lookup("#userNa")).getText();
			    	String pass = ((PasswordField)scene.lookup("#passa")).getText();
			    	String repass = ((PasswordField)scene.lookup("#repassa")).getText();
			    	if(userN.length()==0||pass.length()==0) //����û�û��������  
			    	{
			    		String info="�뽫��Ϣ������";
			    		Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("ȷ��",ButtonData.YES));
			      		alert.setHeaderText(null); 
			      		alert.setTitle("��ʾ"); 
			      		alert.show();
			    	}
			    	else {
			    		if(!pass.equals(repass))//������벻һ�� 
			        	{
			            	String info="���벻��ͬ";
			            	Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("ȷ��",ButtonData.YES));
			              	alert.setHeaderText(null);  
			              	alert.setTitle("��ʾ"); 
			              	alert.show();
			        	}
			        	else {
							try {
								socket = new Socket("localhost",9000);
							} catch (IOException e) {
								e.printStackTrace();
								System.out.println("ע��ʱ����ʧ��");
							}  //���ӷ����� 
			            	//�����û��������뷢�͸�������
			               	StringHandleOutClient sho = new StringHandleOutClient();
			               	sho.hand("$"+userN+"#"+pass,socket);//���ͷ���
			               	DataInputStream dis = null;
			        		try {
			        			dis = new DataInputStream(socket.getInputStream());
			        		} catch (IOException e1) {
			        			e1.printStackTrace();
			        			System.out.println("��������ֵ����");
			        		}
			        		String str = "";
			        		try {
			        			str = dis.readUTF();
			        		} catch (IOException e) {
			        			e.printStackTrace();
			        			System.out.println("�����ַ�����");
			        		}
			        		String rstr = str.substring(1);               	
			               	if(rstr.equals("1")) {
			        			String info="ע��ɹ������ص�¼��";
			        	       	Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("ȷ��",ButtonData.YES));
			        	       	alert.setHeaderText(null); 
			        	       	alert.setTitle("��ʾ");   
			        	       	alert.show(); 
			               	}
			               	else {
			               		String info="�û����Ѵ��ڣ���һ����";
			        	       	Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("ȷ��",ButtonData.YES));
			        	       	alert.setHeaderText(null); 
			        	       	alert.setTitle("��ʾ");   
			        	       	alert.show(); 
			        	       	try {
									socket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			        	}
			    	}	
			    	}
				}
 		});
         
         
         
         //�����϶�
         scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
             @Override 
             public void handle(MouseEvent m) {
               //����          
             	primaryStage.setX(x_stage + m.getScreenX() - x1); 
             	primaryStage.setY(y_stage + m.getScreenY() - y1);
               }                                                
              });
         scene.setOnDragEntered(null);
         scene.setOnMousePressed(new EventHandler<MouseEvent>() {
               public void handle(MouseEvent m) {
               //�������󣬼�¼��ǰ��������         
               x1 =m.getScreenX();
               y1 =m.getScreenY();
               x_stage = primaryStage.getX();
                   y_stage = primaryStage.getY();
                }                
         });    

	 }
	 //��ȡsocket
	 public Socket getSocket() {
		 return socket;
	 }
	 
	 public Stage getPrimaryStage() {
	        return primaryStage;
	    }
	public static void main(String[] args) {
		launch(args);
	}
}
