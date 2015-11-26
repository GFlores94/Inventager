import java.util.List;
import java.io.*;
import java.sql.*;

import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PopUp extends IOException{

	static Main item = new Main();
	static String check = null;
	static Connection conn = SQLConnect.dbConnect();
	
	public static void Display(String scene, String message){
		
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Error");
		stage.setMinWidth(250);
		
		Label label = new Label();
		label.setText("Something went wrong");
		
		Button closeButton = new Button();
		closeButton.setText("Close");
		closeButton.setOnAction(e -> stage.close());
		
		StackPane layout = new StackPane();
		layout.getChildren().add(label);
		layout.getChildren().add(closeButton);
		
		Scene scene1 = new Scene(layout,600,400);
		stage.setScene(scene1);
		stage.show();
	}
	
	public static void NewAcc() throws IOException{
		
		Connection conn2 = SQLConnect.ObjectConnect();
		
//		FileReader in = new FileReader("Users.txt");
//		BufferedReader reader = new BufferedReader(in);
		
		Button Create = new Button();
		Create.setText("Done");
		
		//Labels
		Label created = new Label("Account created!");
		Label mismatch = new Label("Passwords do not match");
		Label shortpass = new Label("Password too short, five character minimum.");
		Label duplicate = new Label("Duplicate Usernames!");
		//
		Stage NewAcc = new Stage();
		NewAcc.initModality(Modality.APPLICATION_MODAL);
		NewAcc.setTitle("New Account");
		NewAcc.setMinWidth(250);
		//
		Label label = new Label();
		label.setText("UserName:");
		TextField User = new TextField();
		User.setMaxWidth(100);
		//
		Label pass = new Label();
		pass.setText("Password:");
		PasswordField Pass = new PasswordField();
		Pass.setMaxWidth(100);
		//
		Label repass = new Label();
		repass.setText("Re-Enter Password:");
		PasswordField Repass = new PasswordField();
		Repass.setMaxWidth(100);
		//
		Button closeButton = new Button();
		closeButton.setText("Close");
		closeButton.setOnAction(e -> NewAcc.close());
		
		Pane layout = new Pane();
		layout.getChildren().addAll(label,closeButton,User,pass,Pass,repass,Repass,Create);
		
		//Organizer
		label.setLayoutX(70);
		label.setLayoutY(50);
		User.setLayoutX(130);
		User.setLayoutY(50);
		pass.setLayoutX(70);
		pass.setLayoutY(100);
		Pass.setLayoutX(130);
		Pass.setLayoutY(100);
		repass.setLayoutX(20);
		repass.setLayoutY(150);
		Repass.setLayoutX(130);
		Repass.setLayoutY(150);
		Create.setLayoutX(130);
		Create.setLayoutY(200);
		closeButton.setLayoutX(180);
		closeButton.setLayoutY(200);
		//
		Create.setOnAction(e -> {
			try{
				String query = "select * from Users where Username = ?";
				PreparedStatement pst = conn.prepareStatement(query);
				pst.setString(1, User.getText());
				ResultSet rs = pst.executeQuery();
				int count = 0;
				while(rs.next()){
					count+= 1;
				}
				if(count == 1){
					layout.getChildren().removeAll(duplicate,mismatch,shortpass,created);
					layout.getChildren().add(duplicate);
					duplicate.setLayoutX(130);
					duplicate.setLayoutY(250);
				}
				
				if(count == 0){
					if(Pass.getText().equals(Repass.getText()) && Pass.getText().length() >= 5){
						String user = User.getText();
						String pass2 = Pass.getText();
						String queryadd = "insert into Users(Username,Password) values('"+user+"','"+pass2+"')";
						try {
							PreparedStatement pst2 = conn.prepareStatement(queryadd);
							pst2.executeUpdate();
							pst2.close();
							layout.getChildren().add(created);
							created.setLayoutX(130);
							created.setLayoutY(250);
							
							String objectadd = "create table '"+user+"'(ID integer primary key, Category text, SubCategory text, Item text, Quantity integer)";
							PreparedStatement makeObj = conn2.prepareStatement(objectadd);
							makeObj.executeUpdate();
							makeObj.close();
						} catch (Exception e1) {
							System.out.println("Didnt work");
							e1.printStackTrace();
						}
					}else if(Pass.getText().length() < 5){
						layout.getChildren().removeAll(duplicate,mismatch,shortpass,created);
						layout.getChildren().add(shortpass);
						shortpass.setLayoutX(50);
						shortpass.setLayoutY(250);
					}
				else if(!Pass.getText().equals(Repass.getText())){
					layout.getChildren().removeAll(duplicate,mismatch,shortpass,created);
					layout.getChildren().add(mismatch);
					mismatch.setLayoutX(130);
					mismatch.setLayoutY(250);
				}
					
				}
				rs.close();
				pst.close();
			}catch(Exception ex){
				Label notworking = new Label("Doesn't work");
				
				System.out.println("Did not work");
			}
		});
		
//		Create.setOnAction(e -> {
//			if(Pass.getText().equals(Repass.getText()) && Pass.getText().length() >= 5){
//				layout.getChildren().remove(duplicate);
//				layout.getChildren().remove(created);
//				layout.getChildren().add(created);
//				created.setLayoutX(130);
//				created.setLayoutY(250);
//				layout.getChildren().remove(mismatch);
//				layout.getChildren().remove(shortpass);
//				try {
//					PrintWriter writeuser = new PrintWriter(new FileWriter("Users.txt",true));
//					FileReader FR = new FileReader("Users.txt");
//					BufferedReader reading = new BufferedReader(FR);
//					while((check = reading.readLine()) != null){
//						if(User.getText().equals(check)){
//							layout.getChildren().remove(created);
//							layout.getChildren().add(duplicate);
//							duplicate.setLayoutX(130);
//							duplicate.setLayoutY(250);
//							return;	
//						}
//					}
//					
//					writeuser.println(User.getText());
//					writeuser.close();
//					PrintWriter write = new PrintWriter(new FileWriter(User.getText()+".txt",true));
//					FileReader open = new FileReader(User.getText()+".txt");
//					write.println(Pass.getText());
//					write.close();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					
//					
//				}
//			}else if(!Pass.getText().equals(Repass.getText())){
//				layout.getChildren().remove(duplicate);
//				layout.getChildren().remove(mismatch);
//				layout.getChildren().add(mismatch);
//				mismatch.setLayoutX(130);
//				mismatch.setLayoutY(250);
//				layout.getChildren().remove(shortpass);
//				layout.getChildren().remove(created);
//			}else if(Pass.getText().length() < 5){
//				layout.getChildren().remove(duplicate);
//				layout.getChildren().remove(shortpass);
//				layout.getChildren().add(shortpass);
//				shortpass.setLayoutX(50);
//				shortpass.setLayoutY(250);
//				layout.getChildren().remove(mismatch);
//				layout.getChildren().remove(created);
//			}
//		});
		
		Scene scene1 = new Scene(layout,300,300);
		NewAcc.setScene(scene1);
		NewAcc.show();
	}
	
	public static void Organize(){
		
	}
	
	public static void ConfirmBox(String User, String input,String type, String CAT, int ID){
		Stage Confirm = new Stage();
		Confirm.initModality(Modality.APPLICATION_MODAL);
		Pane ConPane = new Pane();
		Scene ConScene = new Scene(ConPane,400,100);
		Confirm.show();
		Confirm.setScene(ConScene);
		
		Label Sure = new Label("Are you sure you want to delete " + input + "?");
		Button Yes = new Button("Yes");
		Button No = new Button("No");
		Sure.setLayoutX(100);
		Sure.setLayoutY(20);
		Yes.setLayoutX(150);
		Yes.setLayoutY(60);
		No.setLayoutX(200);
		No.setLayoutY(60);
		ConPane.getChildren().addAll(Sure,Yes,No);
		
		No.setOnAction(e -> Confirm.close());
		Yes.setOnAction(e -> {
			if(type.equals("CAT")){
				
				String query = "delete from Category where ID = '"+ID+"'";
				try {
					PreparedStatement pst = conn.prepareStatement(query);
					pst.executeUpdate();
					pst.close();
					Confirm.close();
					DeleteItem.Refresh(User, input, true, false, false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
//				try {
////					FileReader in = new FileReader(User+".txt");
////					BufferedReader inputvar = new BufferedReader(in);
////					PrintWriter tempwrite = new PrintWriter(new FileWriter("TempFile.txt"));
//					while((check = inputvar.readLine()) != null){
//						int id = check.indexOf(input);
//						int id2 = check.indexOf(type);
//						if((id - id2) == 5){
//						}else{
//							tempwrite.println(check);
//						}
//					}
//					tempwrite.close();
//					in = new FileReader("TempFile.txt");
//					inputvar = new BufferedReader(in);
//					PrintWriter tempwrite2 = new PrintWriter(new FileWriter("TempFile.txt",true));
//					PrintWriter og = new PrintWriter(new FileWriter(User+".txt"));
//					while((check = inputvar.readLine()) != null){
//						og.println(check);
//					}
//					tempwrite.close();
//					og.close();
//					Confirm.close();
//					DeleteItem.Refresh(User, input, true, false, false);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
			}
			
			if(type.equals("SUB")){
				
				String query = "delete from SubCategory where ID = '"+ID+"'";
				try {
					PreparedStatement pst = conn.prepareStatement(query);
					pst.executeUpdate();
					pst.close();
					Confirm.close();
					DeleteItem.Refresh(User, CAT, false, true, false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
//				try {
//					FileReader in = new FileReader(User+".txt");
//					BufferedReader inputvar = new BufferedReader(in);
//					PrintWriter tempwrite = new PrintWriter(new FileWriter("TempFile.txt"));
//					while((check = inputvar.readLine()) != null){
//						int id = check.indexOf(input);
//						int id2 = check.indexOf(type);
//						if((id - id2) == 5){
//						}else{
//							tempwrite.println(check);
//						}
//					}
//					tempwrite.close();
//					in = new FileReader("TempFile.txt");
//					inputvar = new BufferedReader(in);
//					PrintWriter tempwrite2 = new PrintWriter(new FileWriter("TempFile.txt",true));
//					PrintWriter og = new PrintWriter(new FileWriter(User+".txt"));
//					while((check = inputvar.readLine()) != null){
//						og.println(check);
//					}
//					tempwrite.close();
//					og.close();
//					Confirm.close();
//					DeleteItem.Refresh(User, CAT, false, true, false);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
			}
			
			if(type.equals("ITEM")){
				
				String query = "delete from Item where ID = '"+ID+"'";
				try {
					PreparedStatement pst = conn.prepareStatement(query);
					pst.executeUpdate();
					pst.close();
					Confirm.close();
					DeleteItem.Refresh(User, CAT, false, false, true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
//				try {
//					FileReader in = new FileReader(User+".txt");
//					BufferedReader inputvar = new BufferedReader(in);
//					PrintWriter tempwrite = new PrintWriter(new FileWriter("TempFile.txt"));
//					while((check = inputvar.readLine()) != null){
//						int id = check.indexOf(input);
//						int id2 = check.indexOf(type);
//						if((id - id2) == 6){
//						}else{
//							tempwrite.println(check);
//						}
//					}
//					tempwrite.close();
//					in = new FileReader("TempFile.txt");
//					inputvar = new BufferedReader(in);
//					PrintWriter tempwrite2 = new PrintWriter(new FileWriter("TempFile.txt",true));
//					PrintWriter og = new PrintWriter(new FileWriter(User+".txt"));
//					while((check = inputvar.readLine()) != null){
//						og.println(check);
//					}
//					tempwrite.close();
//					og.close();
//					Confirm.close();
//					DeleteItem.Refresh(User, CAT, false, false, true);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
			}
		});
		
		
	}
	
}
