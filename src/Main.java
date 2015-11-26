import java.io.*;
import java.sql.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	static PopUp item = new PopUp();
	static Button button = new Button(); //Making button
	static Label label1 = new Label("UserName"); //Static text
	static Label label2 = new Label("Password");
	static TextField text = new TextField(); //Text box for user input
	static String check = null;
	static Button button2 = new Button();
	static PasswordField password = new PasswordField();
	static boolean usertrue = false;
	static Label incpass = new Label("Incorrect Password");
	static public Stage MM;
	static Label IncInp = new Label("Incorrect username or password");
	
	public static void main(String[] args)throws IOException{
		launch(args);
		File tempfile = new File("TempFile.txt");
	}

	
	public void start(Stage PrimaryStage) throws FileNotFoundException{
		Connection conn = SQLConnect.dbConnect();
		
		
		MM = PrimaryStage;
		MM.setTitle("Launch");
		
//		FileReader file = new FileReader("Users.txt");
//		BufferedReader checker = new BufferedReader(file);
		
		Pane layout1 = new Pane();
		
		button.setText("Login"); //Setting text to button
		button.setOnAction(e -> {
			
			String query = "select * from Users where Username = ? and Password = ?";
			try {
				PreparedStatement pst = conn.prepareStatement(query);
				pst.setString(1, text.getText());
				pst.setString(2,password.getText());
				ResultSet rs = pst.executeQuery();
				int count = 0;
				int id = 0;
				
				while(rs.next()){
					count+=1;
					id = Integer.parseInt(rs.getString("ID"));
				}
				if(count == 0){
					layout1.getChildren().remove(IncInp);
					layout1.getChildren().add(IncInp);
					IncInp.setLayoutX(60);
					IncInp.setLayoutY(170);
				}
				if(count == 1){
					Menu.MainMenu(text.getText(),id);
					
					MM.setScene(Menu.Menu);
				}
				if(count > 1){
					System.out.println("Duplicate user and password");
				}
				rs.close();
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			
			
//			try {
//				while((check = checker.readLine()) != null){
//					if(check.equals(text.getText())){
//						usertrue = true;
//					}
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
			if(usertrue == true){
//				try {
////					FileReader passfile = new FileReader(text.getText()+".txt");
////					BufferedReader passcheck = new BufferedReader(passfile);
////					while((check = passcheck.readLine()) != null){
////						if(check.equals(password.getText())){
//////							Menu.MainMenu(text.getText());
////							
////							MM.setScene(Menu.Menu);
////							return;
////						}else{
////							layout1.getChildren().add(incpass);
////							incpass.setLayoutX(100);
////							incpass.setLayoutY(170);
////						}
////					}
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
			}
		                         });
		
		
		button2.setText("Create Account");
		button2.setOnAction(e -> {
			try {
				PopUp.NewAcc();
			} catch (Exception e1) {
				
			}	
		}
		);
		
		Organizer();
		
		
		
		layout1.getChildren().addAll(button,label1,button2,text,label2,password);
		
		Scene scene = new Scene(layout1,300,200); //Making the actual scene
		MM.setScene(scene); //Adding scene to stage
		MM.show(); //Setting stage to show
		
		
	}
	
	public static void Organizer(){
		text.setMaxWidth(100); //Setting width of text box
		password.setMaxWidth(100);
		text.setLayoutX(100);
		text.setLayoutY(30);
		label1.setLayoutX(100);
		label1.setLayoutY(10);
		label2.setLayoutX(100);
		label2.setLayoutY(60);
		password.setLayoutX(100);
		password.setLayoutY(80);
		button.setLayoutX(100);
		button.setLayoutY(110);
		button2.setLayoutX(100);
		button2.setLayoutY(140);
	}

	
}