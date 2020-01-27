package controller;

import java.io.DataInputStream;

/*
 * 
 * 这个类是登录界面
 */

import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {

	double x1;
	double y1;
	double x_stage;
	double y_stage;
	private Stage primaryStage;
    private AnchorPane rootLayout;
    private Socket socket = null;
    private String userName = "";
    private Chat ch = null;
    
    
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage; 
        this.primaryStage.setTitle("Chat"); 
 
        initRootLayout();
 
	}
	 public void initRootLayout() throws IOException {
		  // Load root layout from fxml file.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Login.class.getResource("../view/personOverview.fxml")); 
         rootLayout = (AnchorPane) loader.load();
         primaryStage.initStyle(StageStyle.UNDECORATED);//背景透明 
         primaryStage.setResizable(false);
         // Show the scene containing the root layout.
         Scene scene = new Scene(rootLayout);
         primaryStage.setScene(scene);
         primaryStage.show();
         
//         绑定登录事件
         
         
         scene.lookup("#buttonLogin").setOnMouseClicked(new EventHandler<MouseEvent>() { 

				@Override
				public void handle(MouseEvent event) {
					userName = ((TextField) scene.lookup("#userName")).getText();
					String password = ((TextField) scene.lookup("#password")).getText();   
					try {
						socket = new Socket("localhost",9000);
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("连接出错");
					}
					StringHandleOutClient sho = new StringHandleOutClient();//发送消息的方法
					sho.hand("#"+userName+"#"+password,socket);//发送方法
					DataInputStream dos = null;
					try {
						
						dos = new DataInputStream(socket.getInputStream());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					String str = "";
					try {
						System.out.println("等待数据中");
						str = dos.readUTF();
						System.out.println("收到登录验证结果："+str);
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("接收错误");
					}
					String st =str.substring(0,1);
					System.out.println(st+"这是消息类型");
					String rstr = str.substring(1);
					if(st.equals("#")&&rstr.equals("1")) {//登录验证返回消息
						ch = new Chat(socket,userName);
						ch.start(new Stage());
						new StringHandleInClient(socket, ch).start();
						primaryStage.close();
					}
					else {
						String info="用户名或者密码错误";
				       	Alert alert = new Alert(AlertType.INFORMATION, info, new ButtonType("确定",ButtonData.YES));
				       	alert.setHeaderText(null); 
				       	alert.setTitle("提示");   
				       	alert.show(); 
				    	try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
 			
 		});
         
         
//         界面拖动
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
	 //socket获取
	 public Socket getSocket() {
		 return socket;
	 }
	 //获取用户名
	 public String getUserName() {
		 return userName;
	 }
	 
	 public Stage getPrimaryStage() {
	        return primaryStage;
	    }
	 public static void main(String[] args) {
		launch(args);
	}
}
