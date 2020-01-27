package view;
import java.io.DataInputStream;
/*
 *登录界面的controller 
 * 
 * 
 */
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import controller.Chat;
import controller.Register;
import controller.StringHandleOutClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PersonOverviewController extends Thread{ 
   
    @FXML
    private Label buttonClose;
	@FXML
	private AnchorPane loginparent;//这是登录界面的根布局的id
    @FXML
    private PasswordField password;//密码框

    @FXML
    private TextField userName;//用户名
    
    @FXML
    private Button buttonLogin;
    
    private Socket socket;
    private String usera;
    private String passa;
    
    
    //                      登录界面控制
    
    
    @FXML
	public void closeLogin(MouseEvent event) {
    	Stage stage = (Stage) loginparent.getScene().getWindow();
		stage.close();
    }
   

    public void register(MouseEvent event) throws IOException {
    	Register rs = new Register(); 
    	rs.start(new Stage());
		Stage stage = (Stage) loginparent.getScene().getWindow(); 
		stage.close();
   }
}
