package controller;
/**
 * 
 * ������ǿͻ��˲���֧����
 * 
 * @author Administrator
 *
 */

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class WorkClient extends Application {
	
	private  StringHandleOutClient sho = new StringHandleOutClient();
	
	@Override
	public void start(Stage primaryStage) {
		
	}

	public void registerMassage(String str) {
		if(str.equals("1")) {
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
    	}
	}
	
	
	public void chatMassage(String str ,Chat ch) {
		((TextArea)ch.getPrimaryStage().getScene().lookup("#chatFrame")).appendText(str);
	}
		
	public void friendMassage(String str,Chat ch) {
		VBox memberList = (VBox)ch.getPrimaryStage().getScene().lookup("#memberList");
		Label memberFrame = (Label)ch.getPrimaryStage().getScene().lookup("#memberFrame");
		String str1 = str;
		int num = str1.indexOf('*');//�÷������ڲ��ҵ�ǰ�ַ�����ĳһ���ض��ַ�ch���ֵ�λ��.�÷�����ͷ������,������ַ������ҵ��ַ�ch,�򷵻��ַ�ch���ַ����е�һ�γ��ֵ�λ��;����������ַ�����û���ҵ��ַ�ch,�򷵻�-1
		memberList.getChildren().clear();
		while(str1.length()>0) {

			if(num == -1) { 
				Label friend  = new Label(str1); 
				friend.setTextFill(Color.AQUA);
				Font SongTi = Font.font("SongTi", 16);
				friend.setFont(SongTi);
			
				memberList.getChildren().add(friend);
				System.out.println(str1);
				break;
			}
			String str2 = str1.substring(0, num);
			Label friend  = new Label(str2); 
			friend.setTextFill(Color.AQUA);
			Font SongTi = Font.font("SongTi", 16);
			friend.setFont(SongTi);
			System.out.println(str2);
			memberList.getChildren().add(friend);
			System.out.println(str1);
			str1 = str1.substring(num+1);//�÷�����beginIndexλ����,�ӵ�ǰ�ַ�����ȡ��ʣ����ַ���Ϊһ���µ��ַ�������.
			num = str1.indexOf('*');
		}
		
		 
	}
	
	
}








