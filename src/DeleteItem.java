import java.util.*;
import java.io.*;
import java.sql.*;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ChangeEvent;




















import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class DeleteItem {
	
	static String check;
	static ListView CatView;
	static Pane DelPane;
	static ListView SubCatView;
	static ListView ItemView;
	static String qty;
	static Label Qty;
	static Button DelCat = new Button("Delete Category");
	static Button DelSubCat = new Button("Delete Sub-Category");
	static Button DelItem = new Button("Delete Item");
	static Button EditItem = new Button("Edit Quantity");
	static Button OK = new Button("Ok");
	static String CatHold = null;
	static Label EmpField = new Label("No value typed");
	static Connection conn = SQLConnect.dbConnect();
	static int CatID = 0;
	static int SubID = 0;
	static int ItemID = 0;
	static int UserID = 0;
	
	public static void DeleteMenu(String User, int ID) throws IOException{
		UserID = ID;
		DelPane = new Pane();
		Scene DelScene = new Scene(DelPane,500,250);

		Main.MM.setScene(DelScene);
		Main.MM.setTitle("Delete Items");
		FirstStore(User, ID);
		Qty = new Label();
		Label Quantity = new Label("Quantity:");
		
		DelPane.getChildren().addAll(Menu.Home,Menu.addbut,Menu.viewbut,Menu.DeleteBut);
		
		
		DelPane.getChildren().addAll(Qty,Quantity);
		Quantity.setLayoutX(40);
		Quantity.setLayoutY(220);
		
		
	}
	
	public static void FirstStore(String User, int ID) throws IOException{
		CatView = new ListView<String>();
		CatView.setPrefHeight(100);
		CatView.setPrefWidth(100);
		CatView.setLayoutX(40);
		CatView.setLayoutY(70);
		
		Label CatLabel = new Label("Categories");
		DelPane.getChildren().add(CatLabel);
		CatLabel.setLayoutX(40);
		CatLabel.setLayoutY(50);
		ObservableList Observe = FXCollections.observableArrayList();
		
		String query = "select * from Category where ParentID = '"+ID+"'";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Observe.add(rs.getString("Name"));
			}
		} catch (SQLException e1) {
			System.out.println("Didn't execute");
			e1.printStackTrace();
		}
		
//		FileReader in = new FileReader(User+".txt");
//		BufferedReader input = new BufferedReader(in);
//		while((check = input.readLine())!= null){
//			
//			if(check.substring(0,4).equals("CAT:")){
//				if((check.contains("ITEM")) == false && (check.contains("SUB:")) == false){
//					check = check.substring(5,check.length());
//					Observe.add(check);
//					
//				}
//				
//				
//			}
//		}
		CatView.setItems(Observe);
		DelPane.getChildren().add(CatView);
		
		
		  CatView.getSelectionModel().selectedItemProperty().addListener(
		            new ChangeListener<String>() {
						public void changed(ObservableValue<? extends String> ov, 
			                    String old_val, String new_val) {
							String search = "select * from Category where Name = '"+new_val+"'";
							try {
								PreparedStatement pst = conn.prepareStatement(search);
								ResultSet rs = pst.executeQuery();
								while(rs.next()){
									CatID = rs.getInt("ID");
								}
							} catch (SQLException e1) {
								System.out.println("Couldn't find CatID");
								e1.printStackTrace();
							}
								DelCat.setOnAction(e -> PopUp.ConfirmBox(User,new_val,"CAT",CatHold, CatID));
								DelPane.getChildren().removeAll(DelItem,DelCat,DelSubCat,EditItem);
								DelPane.getChildren().add(DelCat);
								DelCat.setLayoutX(40);
								DelCat.setLayoutY(180);
							try {
								CatHold = new_val;
								SecondStore(User,new_val);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
		        });
		 
	}
	
	public static void SecondStore(String User, String CAT) throws IOException{
		SubCatView = new ListView();
		SubCatView.setPrefHeight(100);
		SubCatView.setPrefWidth(130);
		SubCatView.setLayoutX(140);
		SubCatView.setLayoutY(70);
		
		Label SubCatLabel = new Label("Sub-Categories");
		DelPane.getChildren().add(SubCatLabel);
		SubCatLabel.setLayoutX(140);
		SubCatLabel.setLayoutY(50);
		
		ObservableList Observe = FXCollections.observableArrayList();
		
		String query = "select * from SubCategory where ParentID = '"+CatID+"'";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Observe.add(rs.getString("Name"));
			}
			rs.close();
			pst.close();
			
		} catch (SQLException e1) {
			System.out.println("Didnt connect");
			e1.printStackTrace();
		}
		
//		FileReader in = new FileReader(User+".txt");
//		BufferedReader input = new BufferedReader(in);
//		while((check = input.readLine())!= null){
//			try{
//				
//				if(check.substring(0,4).equals("CAT:")){
//					if((check.contains("ITEM")) == false && (check.contains("SUB:")) == true && (check.contains(CAT) == true)){
//						int id = check.lastIndexOf("SUB:");
//						check = check.substring(id+5,check.length());
//						Observe.add(check);
//						
//					}
//					
//					
//				}
//			}catch(Exception e){
//				
//			}
//		}
		SubCatView.setItems(Observe);
		
		if(Observe.isEmpty() == true){
			DelPane.getChildren().add(SubCatView);
		
			ThirdStore(User,CAT);
		}else{
			DelPane.getChildren().add(SubCatView);
			SubCatView.getSelectionModel().selectedItemProperty().addListener(
		            new ChangeListener<String>() {
						public void changed(ObservableValue<? extends String> ov, 
			                    String old_val, String new_val) {
							
							String search = "select * from SubCategory where Name = '"+new_val+"'";
							try {
								PreparedStatement pst = conn.prepareStatement(search);
								ResultSet rs = pst.executeQuery();
								while(rs.next()){
									SubID = rs.getInt("ID");
								}
								pst.close();
								rs.close();
							} catch (SQLException e1) {
								System.out.println("Couldn't find SubID");
								e1.printStackTrace();
							}
							
							DelSubCat.setOnAction(e -> PopUp.ConfirmBox(User,new_val,"SUB",CatHold, SubID));
							
							DelSubCat.setLayoutX(140);
							DelSubCat.setLayoutY(180);
							DelPane.getChildren().removeAll(DelItem,DelCat,DelSubCat,EditItem);
							DelPane.getChildren().add(DelSubCat);
							try {
								ThirdStore(User,new_val);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
		        });
		}
		
			
		
		
		
	}
	
	public static void ThirdStore(String User, String CAT) throws IOException{
		ItemView = new ListView();
		ItemView.setPrefHeight(100);
		ItemView.setPrefWidth(150);
		ItemView.setLayoutX(270);
		ItemView.setLayoutY(70);
		
		Label ItemLabel = new Label("Items");
		DelPane.getChildren().add(ItemLabel);
		
		ItemLabel.setLayoutX(270);
		ItemLabel.setLayoutY(50);
		
		ObservableList Observe = FXCollections.observableArrayList();
		
		String query = "select * from Item where CatID = '"+CatID+"' and SubCatID = '"+SubID+"' ";
		try {
			
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Observe.add(rs.getString("Name"));
				
			}
			rs.close();
			pst.close();
			ItemView.setItems(Observe);
			
		} catch (SQLException e1) {
			System.out.println("Didnt connect");
			e1.printStackTrace();
		}
		
		ObservableList IHoldU = FXCollections.observableArrayList();
		
//		FileReader in = new FileReader(User+".txt");
//		BufferedReader input = new BufferedReader(in);
//		
//		while((check = input.readLine())!= null){
//			try{
//				
//				if(check.substring(0,4).equals("CAT:")){
//					if( (check.contains("ITEM") == true) && (check.contains(CAT) == true) && (check.contains("SUB") == true)    ){
//						int id = check.lastIndexOf("ITEM:");
//						int id2 = check.lastIndexOf("QTY");
//						qty = check.substring(id2+5,check.length());
//						
//						check = check.substring(id+6,id2-1);
//						qty = qty + " " + check;
//						IHoldU.add(qty);
//						Observe.add(check);
//					}
//					
//					if((check.contains("ITEM")) == true && (check.contains("SUB:")) == false && (check.contains(CAT) == true)){
//						int id = check.lastIndexOf("ITEM:");
//						int id2 = check.lastIndexOf("QTY");
//						qty = check.substring(id2+5,check.length());
//						check = check.substring(id+6,id2-1);
//						qty = qty + " " + check;
//						IHoldU.add(qty);
//						Observe.add(check);
//						
//					}
//					
//					
//				}
//			}catch(Exception e){
//				
//			}
//		}
		
		ItemView.setItems(Observe);
		
		
		ItemView.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
		                    String old_val, String new_val) {
						
						int qtyID = 0;
						String search = "select * from Item where Name = '"+new_val+"'";
						try {
							PreparedStatement pst = conn.prepareStatement(search);
							ResultSet rs = pst.executeQuery();
							while(rs.next()){
								qtyID = rs.getInt("Quantity");
							}
							rs.close();
							pst.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						
						Qty.setLayoutX(100);
						Qty.setLayoutY(220);
						Qty.setText(Integer.toString(qtyID));
						
						String searchagain = "select * from Item where Name = '"+new_val+"'";
						try {
							PreparedStatement pst = conn.prepareStatement(searchagain);
							ResultSet rs = pst.executeQuery();
							while(rs.next()){
								ItemID = rs.getInt("ID");
							}
							pst.close();
							rs.close();
						} catch (SQLException e1) {
							System.out.println("Couldn't find SubID");
							e1.printStackTrace();
						}
						
						DelItem.setOnAction(e -> PopUp.ConfirmBox(User, new_val, "ITEM",CatHold, ItemID));
						
						DelItem.setLayoutX(270);
						DelItem.setLayoutY(180);
						DelPane.getChildren().removeAll(DelItem,DelCat,DelSubCat,EditItem);
						DelPane.getChildren().add(DelItem);
						DelPane.getChildren().add(EditItem);
						EditItem.setLayoutX(270);
						EditItem.setLayoutY(210);
						EditItem.setOnAction(e -> {
							EditMenu(User,new_val);
						});
//						try{
//							for(int i = 0; i < IHoldU.size(); i++){
//								if(IHoldU.get(i).toString().contains(new_val) == true){
//									int id = IHoldU.get(i).toString().indexOf(" ");
//									Qty.setText(IHoldU.get(i).toString().substring(0, id));
//									Qty.setLayoutX(100);
//									Qty.setLayoutY(220);
//									break;
//								}
//							}						
//							
//						}catch(Exception e){
//							
//						}
					}
	        });
		
		DelPane.getChildren().add(ItemView);
	}

	public static void EditMenu(String User,String item){
		Stage EditMenu = new Stage();
		EditMenu.initModality(Modality.APPLICATION_MODAL);
		Pane EditPane = new Pane();
		Scene Edit = new Scene(EditPane,400,200);
		Label Info = new Label("Item: " + item);
		Label Amt = new Label("Enter Amount:");
		Button AddQ = new Button("Add");
		Button DelQ = new Button("Delete");
		Button Help = new Button("?");
		Label Done = new Label("Done!");
		Label NotInt = new Label("Not a number!");
		
		TextField NewQty = new TextField();
		NewQty.setMaxWidth(100);
		EditPane.getChildren().addAll(Info,AddQ,DelQ,NewQty,Amt,Help);
		
//		int ee = qty.indexOf(" ");
//		qty = qty.substring(0,ee);
		
		NotInt.setLayoutX(10);
		NotInt.setLayoutY(110);
		Done.setLayoutX(10);
		Done.setLayoutY(110);
		Info.setLayoutX(10);
		Info.setLayoutY(10);
		NewQty.setLayoutX(10);
		NewQty.setLayoutY(50);
		AddQ.setLayoutX(10);
		AddQ.setLayoutY(80);
		DelQ.setLayoutX(60);
		DelQ.setLayoutY(80);
		Amt.setLayoutX(10);
		Amt.setLayoutY(30);
		Help.setLayoutX(120);
		Help.setLayoutY(50);
		EmpField.setLayoutX(10);
		EmpField.setLayoutY(110);
		String help = "Help";
		
		AddQ.setOnAction(e -> {
			if(NewQty.getText().equals("")){
				EditPane.getChildren().removeAll(EmpField,Done,NotInt);
				EditPane.getChildren().add(EmpField);
			}else{
				try{
					int amount =  Integer.parseInt(NewQty.getText());
					String update = "update Item set Quantity = Quantity + '"+amount+"' where ID = '"+ItemID+"'";
					PreparedStatement pst = conn.prepareStatement(update);
					pst.executeUpdate();
					pst.close();
					
					EditPane.getChildren().removeAll(EmpField,Done,NotInt);
//					int i = Integer.parseInt(NewQty.getText());
//					int j = Integer.parseInt(qty);
//					int sum = i + j;
//					qty = Integer.toString(sum);
//					try {
//						FileReader in = new FileReader(User+".txt");
//						BufferedReader inputvar = new BufferedReader(in);
//						PrintWriter tempwrite = new PrintWriter(new FileWriter("TempFile.txt"));
//						while((check = inputvar.readLine()) != null){
//							int id = check.indexOf(item);
//							int id2 = check.indexOf("QTY");
//							if(check.contains(item) == true){
//								check = check.substring(0, id2) + "QTY: " + sum;
//								tempwrite.println(check);
//							}else{
//								tempwrite.println(check);
//							}
//						}
//						tempwrite.close();
//						in = new FileReader("TempFile.txt");
//						inputvar = new BufferedReader(in);
//						PrintWriter tempwrite2 = new PrintWriter(new FileWriter("TempFile.txt",true));
//						PrintWriter og = new PrintWriter(new FileWriter(User+".txt"));
//						while((check = inputvar.readLine()) != null){
//							og.println(check);
//						}
//						tempwrite.close();
//						og.close();
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
					EditPane.getChildren().add(Done);
				}catch(Exception es){
					EditPane.getChildren().removeAll(NotInt,Done,EmpField);
					EditPane.getChildren().add(NotInt);
				}
				
			}
		});
		
		DelQ.setOnAction(e -> {
			if(NewQty.getText().equals("")){
				EditPane.getChildren().removeAll(EmpField,Done,NotInt);
				EditPane.getChildren().add(EmpField);
			}else{
				try{
					EditPane.getChildren().removeAll(EmpField,Done,NotInt);
					int amount =  Integer.parseInt(NewQty.getText());
					String update = "update Item set Quantity = Quantity - '"+amount+"' where ID = '"+ItemID+"'";
					PreparedStatement pst = conn.prepareStatement(update);
					pst.executeUpdate();
					pst.close();
					
//					int i = Integer.parseInt(NewQty.getText());
//					int j = Integer.parseInt(qty);
//					int sum = j - i;
//					
					
//					try {
//						FileReader in = new FileReader(User+".txt");
//						BufferedReader inputvar = new BufferedReader(in);
//						PrintWriter tempwrite = new PrintWriter(new FileWriter("TempFile.txt"));
//						while((check = inputvar.readLine()) != null){
//							int id = check.indexOf(item);
//							int id2 = check.indexOf("QTY");
//							if(check.contains(item) == true){
//								check = check.substring(0, id2) + "QTY: " + sum;
//								tempwrite.println(check);
//							}else{
//								tempwrite.println(check);
//							}
//						}
//						tempwrite.close();
//						in = new FileReader("TempFile.txt");
//						inputvar = new BufferedReader(in);
//						PrintWriter tempwrite2 = new PrintWriter(new FileWriter("TempFile.txt",true));
//						PrintWriter og = new PrintWriter(new FileWriter(User+".txt"));
//						while((check = inputvar.readLine()) != null){
//							og.println(check);
//						}
//						tempwrite.close();
//						og.close();
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
					EditPane.getChildren().add(Done);
				}catch(Exception es){
					EditPane.getChildren().removeAll(NotInt,Done,EmpField);
					EditPane.getChildren().add(NotInt);
				}
				
			}
		});
		
		Help.setTooltip(new Tooltip("Help"));
		Help.setOnAction(e ->{
			Helper();			
		});
		
		
		
		Button Clear = new Button("Clear");
		EditMenu.show();
		EditMenu.setScene(Edit);
	}
	
	public static void Helper(){
		Stage HelpStage = new Stage();
		Pane HelpPane = new Pane();
		Scene HelpScene = new Scene(HelpPane,450,50);
		HelpStage.initModality(Modality.APPLICATION_MODAL);
		
		OK.setLayoutX(200);
		OK.setLayoutY(20);
		
		Label HelpInfo = new Label("Value entered in empty box will be added/deleted from the item's current quantity");
		HelpInfo.setLayoutX(8);
		HelpPane.getChildren().addAll(HelpInfo,OK);
		HelpStage.show();
		HelpStage.setScene(HelpScene);
		OK.setOnAction(e -> {
			HelpStage.hide();
			HelpStage.close();
		});
		
		
		
	}
	
	public static void Refresh(String User,String CAT, boolean cat, boolean sub, boolean item) throws IOException{

		if(cat == true){
			
			ObservableList Observe = FXCollections.observableArrayList();
			
			String query = "select * from Category where ParentID = '"+UserID+"'";
			try {
				PreparedStatement pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					Observe.add(rs.getString("Name"));
				}
			} catch (SQLException e1) {
				System.out.println("Didn't execute");
				e1.printStackTrace();
			}
			
//			FileReader in = new FileReader(User+".txt");
//			BufferedReader input = new BufferedReader(in);
//			while((check = input.readLine())!= null){
//				
//				if(check.substring(0,4).equals("CAT:")){
//					if((check.contains("ITEM")) == false && (check.contains("SUB:")) == false){
//						check = check.substring(5,check.length());
//						Observe.add(check);
//						
//					}
//					
//					
//				}
//			}
			CatView.setItems(null);
			CatView.setItems(Observe);
			
		}
		if(sub == true){
			
			ObservableList Observe2 = FXCollections.observableArrayList();
			
			String query = "select * from SubCategory where ParentID = '"+CatID+"'";
			try {
				PreparedStatement pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					Observe2.add(rs.getString("Name"));
				}
				rs.close();
				pst.close();
				
			} catch (SQLException e1) {
				System.out.println("Didnt connect");
				e1.printStackTrace();
			}
			
//			FileReader in2 = new FileReader(User+".txt");
//			BufferedReader input2 = new BufferedReader(in2);
//			while((check = input2.readLine())!= null){
//				
//				if(check.substring(0,4).equals("CAT:")){
//					if((check.contains("ITEM")) == false && (check.contains("SUB:")) == true && (check.contains(CAT) == true)){
//						int id = check.lastIndexOf("SUB:");
//						check = check.substring(id+5,check.length());
//						Observe2.add(check);
//						
//					}
//					
//					
//				}
//			}
			SubCatView.setItems(null);
			SubCatView.setItems(Observe2);
			
		}
		
		if(item == true){
			
			ObservableList IHoldU = FXCollections.observableArrayList();
			ObservableList Observe3 = FXCollections.observableArrayList();
			
			String query = "select * from Item where CatID = '"+CatID+"' and SubCatID = '"+SubID+"' ";
			try {
				
				PreparedStatement pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					Observe3.add(rs.getString("Name"));
					
				}
				rs.close();
				pst.close();
				
			} catch (SQLException e1) {
				System.out.println("Didnt connect");
				e1.printStackTrace();
			}
//			FileReader in3 = new FileReader(User+".txt");
//			BufferedReader input3 = new BufferedReader(in3);
//			
//			while((check = input3.readLine())!= null){
//				
//				if(check.substring(0,4).equals("CAT:")){
//					if( (check.contains("ITEM") == true) && (check.contains(CAT) == true) && (check.contains("SUB") == true)    ){
//						int id = check.lastIndexOf("ITEM:");
//						int id2 = check.lastIndexOf("QTY");
//						qty = check.substring(id2+5,check.length());
//						
//						check = check.substring(id+6,id2-1);
//						qty = qty + " " + check;
//						
//						IHoldU.add(check);
//					}
//					
//					if((check.contains("ITEM")) == true && (check.contains("SUB:")) == false && (check.contains(CAT) == true)){
//						int id = check.lastIndexOf("ITEM:");
//						int id2 = check.lastIndexOf("QTY");
//						qty = check.substring(id2+5,check.length());
//						check = check.substring(id+6,id2-1);
//						qty = qty + " " + check;
//						
//						IHoldU.add(check);
//						
//					}
//					
//					
//				}
//			}
			ItemView.setItems(null);
			ItemView.setItems(Observe3);
			
		}
		
	}
	
}
