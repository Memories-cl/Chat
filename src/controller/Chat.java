package controller;

import java.io.IOException;
import java.net.Socket;

import AppClient.NowTime;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Chat extends Application {
	
	double x1;
	double y1;
	double x_stage;
	double y_stage;
	String userNam = "";
	Socket socket = new Socket();
	//private Stage primaryStage;
	private Stage primaryStage;
    private AnchorPane rootLayout;
    
    public Chat(Socket socket,String userName) {
    	this.socket = socket;
    	this.userNam = userName;
    }
    
    
    public Chat() {
    	;
    }
    public Socket getChatSocket() { 
    	return socket;
    }
    
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");  

        initRootLayout();

	}
	 public void initRootLayout() {
	        try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Login.class.getResource("../view/Chat.fxml"));
	            rootLayout = (AnchorPane) loader.load();
	            primaryStage.initStyle(StageStyle.TRANSPARENT);//���������û��(��С�� ��� �ر�)
	            primaryStage.setResizable(false);
	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            primaryStage.setScene(scene);
	            scene.getStylesheets().add( getClass().getResource("Chat.css") .toExternalForm()); 
	            primaryStage.show(); 
	            
	            //���� ���Ͱ�ť
	    		Button buttonSend = (Button)scene.lookup("#buttonSend"); 
	    		TextField inputFrame  = (TextField)scene.lookup("#inputFrame");
	    		buttonSend.setOnMouseClicked(new EventHandler<MouseEvent>() {  
	                public void handle(MouseEvent m) {
	            		StringHandleOutClient sho = new StringHandleOutClient();//������Ϣ�ķ���
	    				//�����û��������뷢�͸�������
	                   	sho.hand("@"+ userNam+" " +new NowTime().getTime() +"\n"+inputFrame.getText()+"\n",socket);//���ͷ���
	                   	inputFrame.setText("");
	                }                 
	        	});   
	    		
	    		scene.lookup("#buttonClose").setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						StringHandleOutClient sho = new StringHandleOutClient();//������Ϣ�ķ���
						sho.hand("&"+userNam,socket);//���ͷ���
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("socket�ѹر�");
						}
						primaryStage.close();
					}
	    			
	    		});
//	    		�����϶�
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
	            
	        } catch (IOException e) {
	            e.printStackTrace(); 
	        }
	    }

	 public Stage getPrimaryStage() {
	        return primaryStage;
	    }
	public static void main(String[] args) {
		launch(args);
	}
}
