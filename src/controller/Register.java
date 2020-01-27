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
         primaryStage.initStyle(StageStyle.TRANSPARENT);//三个组键都没有(最小化 最大化 关闭)
         primaryStage.setResizable(false);
         // Show the scene containing the root layout.
         Scene scene = new Scene(rootLayout);
         primaryStage.setScene(scene);
         primaryStage.show();
        
//         注册事件绑定
         scene.lookup("#buttonOK").setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					String userN = ((TextField)scene.lookup("#userNa")).getText();
			    	String pass = ((PasswordField)scene.lookup("#passa")).getText();
			    	String repass = ((PasswordField)scene.lookup("#repassa")).getText();
			    	if(userN.length()==0||pass.length()==0) //如果用户没有填完整  
			    	{
			    		String info="请将信息填完整";
			    		Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("确定",ButtonData.YES));
			      		alert.setHeaderText(null); 
			      		alert.setTitle("提示"); 
			      		alert.show();
			    	}
			    	else {
			    		if(!pass.equals(repass))//如果密码不一样 
			        	{
			            	String info="密码不相同";
			            	Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("确定",ButtonData.YES));
			              	alert.setHeaderText(null);  
			              	alert.setTitle("提示"); 
			              	alert.show();
			        	}
			        	else {
							try {
								socket = new Socket("localhost",9000);
							} catch (IOException e) {
								e.printStackTrace();
								System.out.println("注册时连接失败");
							}  //连接服务器 
			            	//这里用户名和密码发送给服务器
			               	StringHandleOutClient sho = new StringHandleOutClient();
			               	sho.hand("$"+userN+"#"+pass,socket);//发送方法
			               	DataInputStream dis = null;
			        		try {
			        			dis = new DataInputStream(socket.getInputStream());
			        		} catch (IOException e1) {
			        			e1.printStackTrace();
			        			System.out.println("输入流赋值出错");
			        		}
			        		String str = "";
			        		try {
			        			str = dis.readUTF();
			        		} catch (IOException e) {
			        			e.printStackTrace();
			        			System.out.println("接收字符出错");
			        		}
			        		String rstr = str.substring(1);               	
			               	if(rstr.equals("1")) {
			        			String info="注册成功，返回登录吧";
			        	       	Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("确定",ButtonData.YES));
			        	       	alert.setHeaderText(null); 
			        	       	alert.setTitle("提示");   
			        	       	alert.show(); 
			               	}
			               	else {
			               		String info="用户名已存在，换一个吧";
			        	       	Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("确定",ButtonData.YES));
			        	       	alert.setHeaderText(null); 
			        	       	alert.setTitle("提示");   
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
         
         
         
         //界面拖动
         scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
             @Override 
             public void handle(MouseEvent m) {
               //计算          
             	primaryStage.setX(x_stage + m.getScreenX() - x1); 
             	primaryStage.setY(y_stage + m.getScreenY() - y1);
               }                                                
              });
         scene.setOnDragEntered(null);
         scene.setOnMousePressed(new EventHandler<MouseEvent>() {
               public void handle(MouseEvent m) {
               //按下鼠标后，记录当前鼠标的坐标         
               x1 =m.getScreenX();
               y1 =m.getScreenY();
               x_stage = primaryStage.getX();
                   y_stage = primaryStage.getY();
                }                
         });    

	 }
	 //获取socket
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
