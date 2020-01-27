package controller;
/**
 * 
 * 这个类是客户端操作支持类
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
    	}
	}
	
	
	public void chatMassage(String str ,Chat ch) {
		((TextArea)ch.getPrimaryStage().getScene().lookup("#chatFrame")).appendText(str);
	}
		
	public void friendMassage(String str,Chat ch) {
		VBox memberList = (VBox)ch.getPrimaryStage().getScene().lookup("#memberList");
		Label memberFrame = (Label)ch.getPrimaryStage().getScene().lookup("#memberFrame");
		String str1 = str;
		int num = str1.indexOf('*');//该方法用于查找当前字符串中某一个特定字符ch出现的位置.该方法从头向后查找,如果在字符串中找到字符ch,则返回字符ch在字符串中第一次出现的位置;如果在整个字符串中没有找到字符ch,则返回-1
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
			str1 = str1.substring(num+1);//该方法从beginIndex位置起,从当前字符串中取出剩余的字符作为一个新的字符串返回.
			num = str1.indexOf('*');
		}
		
		 
	}
	
	
}








