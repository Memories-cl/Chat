package view;

import java.io.IOException;

import controller.Login;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {

	@FXML
	private AnchorPane parent;
	@FXML
	private TextField userNa;

	@FXML
	private PasswordField passa; 
	@FXML
	private Button buttonOK;
	@FXML
	private PasswordField repassa;
   
    @FXML
    public void Back(MouseEvent event) throws IOException {  
    	Login cli = new Login();
    	cli.start(new Stage());
		Stage stage = (Stage) parent.getScene().getWindow(); 
		stage.close();
    }

  

    @FXML
    public void closeRegister(MouseEvent event) {
    	Stage stage = (Stage) parent.getScene().getWindow();
		stage.close();
    }

 
}
