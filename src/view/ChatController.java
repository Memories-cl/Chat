package view;

import java.io.OutputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public  class ChatController {
	@FXML
	private AnchorPane Chatparent;
	@FXML
	private TextArea chatFrame;
	@FXML
	private VBox memberList;

	@FXML
	private Button buttonSend;

	@FXML
	private TextField inputFrame;

	@FXML
	private Button buttonclear;

	@FXML
	private Label memberFrame;
	
   
    @FXML
    private Label buttonClose;


    @FXML
    void close(ActionEvent event) {
    	Stage stage = (Stage) Chatparent.getScene().getWindow();
		stage.close();
    }
    @FXML
    public void clear(MouseEvent event) {
    	inputFrame.setText("");
    }
    
    @FXML
    public void minChat(MouseEvent event) {
    	;
    }
}

