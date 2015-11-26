import java.sql.*;
import java.util.List;
import java.io.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class tbd {

	static Pane AddPane;
	static ListView FirstList = new ListView();
	static ListView SecondList = new ListView();
	static String check = null;
	static boolean FirstTime = true;
	static ObservableList SubList = FXCollections.observableArrayList();
	static ObservableList CatList = FXCollections.observableArrayList();
	static Button Back = new Button("Back");
	static Label AddCat = new Label("Category Name:");
	static TextField CatField = new TextField();
	static Button CatBut = new Button("Add");
	static Label AddSub = new Label("Sub-Category Name:");
	static TextField SubField = new TextField();
	static Button SubBut = new Button("Add");
	static boolean ToSub = false;
	static ListView ThirdList = new ListView();
	static ObservableList SubsList;
	static String Holder = null;
	static Label AddItem = new Label("Item Name:");
	static TextField ItemField = new TextField();
	static Button ItemBut = new Button("Add");
	static Label PickCat = new Label("Pick Category:");
	static Label PickSubCat = new Label("Pick Sub-Category");
	static Label Added = new Label("Added!");
	static Label DupEnt = new Label("Duplicate Entry");
	static Label EmpField = new Label("Empty Field");
	static String SubHolder = null;
	static TextField ItemQtyField = new TextField();
	static Label ItemQty = new Label("Quantity:");
	static Label EmptyQty = new Label("No Value Entered");
	static Connection conn = SQLConnect.dbConnect();
	static int CatID = 0;
	static int SubID = 0;
	static Label NotInt = new Label("Not an integer");
	
	public static void RunAdd(String User, int ID) throws IOException{	
		if(FirstTime == true){
			Setup(User);
		}
		
		FirstList.setItems(null);
		FirstList.setItems(CatList);
		SecondList.setItems(null);
		SecondList.setItems(SubList);
		ThirdList.setItems(null);
		ThirdList.setItems(SubsList);
		
		AddPane =  new Pane();
		
		Added.setLayoutX(160);
		Added.setLayoutY(180);
		DupEnt.setLayoutX(160);
		DupEnt.setLayoutY(180);
		EmpField.setLayoutX(160);
		EmpField.setLayoutY(180);
		EmptyQty.setLayoutX(160);
		EmptyQty.setLayoutY(180);
		
		Scene AddScene = new Scene(AddPane,500,250);
		Main.MM.setScene(AddScene);
		Main.MM.setTitle("Adding Items");
		AddPane.getChildren().addAll(Menu.addbut,Menu.DeleteBut,Menu.viewbut,Menu.Home);
		
		Cats(User,ID);
		
		CatBut.setOnAction(e -> {
			AddPane.getChildren().removeAll(Added,EmpField,DupEnt);
			if(CatField.getText().equals("")){
				AddPane.getChildren().add(EmpField);
				return;
			}
			String holdtext = CatField.getText();
			String search = "select * from Category where Name = '"+holdtext+"'";
			try {
				PreparedStatement psearch = conn.prepareStatement(search);
				ResultSet rsearch = psearch.executeQuery();
				int count = 0;
				while(rsearch.next()){
					count += 1;
				}
				if(count == 0){
					AddPane.getChildren().removeAll(DupEnt,Added);
					String query = "insert into Category(ParentID,Name) values('"+ID+"','"+holdtext+"')";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.executeUpdate();
					pst.close();
					AddPane.getChildren().add(Added);
				}
				if(count >= 1){
					AddPane.getChildren().removeAll(DupEnt,Added);
					AddPane.getChildren().add(DupEnt);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
//			try {
//				FileReader in =  new FileReader(User+".txt");;
//				BufferedReader input = new BufferedReader(in);
//				while((check = input.readLine()) != null){
//					if(check.contains(CatField.getText()) == true){
//						AddPane.getChildren().add(DupEnt);
//						return;
//					}
//				}
//				PrintWriter write = new PrintWriter(new FileWriter(User+".txt", true));
//				write.println("CAT: " + CatField.getText());
//				write.close();
//				
//				FileReader in2 = new FileReader(User+".txt");
//				BufferedReader input2 = new BufferedReader(in2);
//				while((check = input.readLine())!= null){
//					if(check.substring(0,4).equals("CAT:")){
//						if((check.contains("ITEM")) == false && (check.contains("SUB:")) == false){
//							check = check.substring(5,check.length());
//							SubList.add(check);
//						}
//					}
//				}
//				SecondList.setItems(null);
//				SecondList.setItems(SubList);
//				
//				AddPane.getChildren().add(Added);
//				
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
		});
		
		SubBut.setOnAction(e -> {
		
			
			AddPane.getChildren().removeAll(Added,EmpField,DupEnt);
			if(SubField.getText().equals("")){
				AddPane.getChildren().add(EmpField);
				return;
			}
			
			String holdtext = SubField.getText();
			String search = "select * from SubCategory where Name = '"+holdtext+"'";
			PreparedStatement psearch;
			try {
				psearch = conn.prepareStatement(search);
				ResultSet rsearch = psearch.executeQuery();
				int count = 0;
				while(rsearch.next()){
					count+=1;
				}
				if(count >= 1){
					AddPane.getChildren().removeAll(DupEnt,Added);
					AddPane.getChildren().add(DupEnt);
					
				}
				if(count == 0){
					try{
						AddPane.getChildren().removeAll(DupEnt,Added);	
						String query = "insert into SubCategory(ParentID,Name) values('"+CatID+"','"+holdtext+"')";
						PreparedStatement pst = conn.prepareStatement(query);
						pst.executeUpdate();
						pst.close();
						AddPane.getChildren().add(Added);
					}catch(Exception e1){
//						AddPane.getChildren().add(NotInt);
//						NotInt.setLayoutX(160);
//						NotInt.setLayoutY(180);
					}
					
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			
//			try {
//				FileReader in = new FileReader(User+".txt");
//				BufferedReader input = new BufferedReader(in);
//				while((check = input.readLine()) != null){
//					if( check.contains(Holder) == true && (check.contains(SubField.getText()) == true) ){
//						return;
//					}
//				}
//				PrintWriter write = new PrintWriter(new FileWriter(User+".txt",true));
//				write.println("CAT: " + Holder + " SUB: " + SubField.getText());
//				write.close();

//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
			
		});
		
		ItemBut.setOnAction(e -> {
			
			
			
			AddPane.getChildren().removeAll(Added,EmpField,DupEnt,EmptyQty);
			if(ItemField.getText().equals("")){
				AddPane.getChildren().add(EmpField);
				return;
			}
			if(ItemQtyField.getText().equals("")){
				AddPane.getChildren().add(EmptyQty);
				return;
			}
			try {
				int amount = Integer.parseInt(ItemQtyField.getText());
				String texthold = ItemField.getText();
				String search = "select * from Item where Name = '"+texthold+"'";
				String query = "insert into Item(CatID,SubCatID,Name,Quantity) values('"+CatID+"','"+SubID+"','"+ItemField.getText()+"','"+amount+"')";
				PreparedStatement psearch = conn.prepareStatement(search);
				ResultSet rsearch = psearch.executeQuery();
				int count = 0;
				while(rsearch.next()){
					count+=1;
				}
				if(count == 0){
					PreparedStatement pst = conn.prepareStatement(query);
					pst.executeUpdate();
					pst.close();
					AddPane.getChildren().removeAll(Added,DupEnt,NotInt);
					AddPane.getChildren().add(Added);
				}
				if(count >=1){
					AddPane.getChildren().removeAll(Added,DupEnt,NotInt);
					AddPane.getChildren().add(DupEnt);
				}
				
			} catch (Exception e2) {
				AddPane.getChildren().removeAll(Added,DupEnt,NotInt);
				AddPane.getChildren().add(NotInt);
				NotInt.setLayoutX(160);
				NotInt.setLayoutY(180);
			}
			
//			try {
//				FileReader in = new FileReader(User+".txt");
//				BufferedReader input = new BufferedReader(in);
//				while((check = input.readLine()) != null){
//					if( check.contains(Holder) == true && check.contains(SubHolder) == true && check.contains(ItemField.getText()) == true){
//						AddPane.getChildren().add(DupEnt);
//						return;
//					}
//				}
//				PrintWriter write = new PrintWriter(new FileWriter(User+".txt",true));
//				write.println("CAT: " + Holder + " SUB: " + SubHolder + " ITEM: " + ItemField.getText() + " QTY: " + ItemQtyField.getText());
//				write.close();
//				AddPane.getChildren().add(Added);
//				
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
		});
		
	}
	
	public static void Cats(String User, int ID) throws IOException{
		
		
			
			FirstList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
				public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
					try{
						
						if(new_val.equals("Add Category")){
							AddPane.getChildren().removeAll(AddCat,CatField,CatBut,AddSub,SubField,SubBut);
							AddPane.getChildren().addAll(AddCat,CatField,CatBut);
							CatField.setMaxWidth(100);
							CatField.setLayoutX(160);
							CatField.setLayoutY(100);
							AddCat.setLayoutX(160);
							AddCat.setLayoutY(60);
							CatBut.setLayoutX(160);
							CatBut.setLayoutY(140);
						}
						if(new_val.equals("Add Sub-Category")){
							
							
						
							String query = "select * from Category where ParentID = '"+ID+"'";
							try {
								SubList.clear();
								PreparedStatement pst = conn.prepareStatement(query);
								ResultSet rs = pst.executeQuery();
								while(rs.next()){
									SubList.add(rs.getString("Name"));
								}
								rs.close();
								pst.close();
								SecondList.setItems(null);
								SecondList.setItems(SubList);
							} catch (Exception e2) {
								System.out.println("Didnt work");
								e2.printStackTrace();
							}
							AddPane.getChildren().removeAll(AddCat,CatField,CatBut);
							try {
								AddPane.getChildren().removeAll(FirstList,Added);
								ToSub = true;
								
//								try {
//									SubList.clear();
//									FileReader in2 = new FileReader(User+".txt");
//									BufferedReader input2 = new BufferedReader(in2);
//									while((check = input2.readLine())!= null){
//										if(check.substring(0,4).equals("CAT:")){
//											if((check.contains("ITEM")) == false && (check.contains("SUB:")) == false){
//												check = check.substring(5,check.length());
//												SubList.add(check);
//											}
//										}
//									}
//									
//									SecondList.setItems(null);
//									SecondList.setItems(SubList);
//									
//									
//									
//								} catch (Exception e1) {
//									e1.printStackTrace();
//								}
								
								Subs(User);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}
						if(new_val.equals("Add Item")){
							AddPane.getChildren().removeAll(AddCat,CatField,CatBut);
							ToSub = false;
							
							String query = "select * from Category where ParentID = '"+ID+"'";
							try {
								SubList.clear();
								System.out.println(ID);
								PreparedStatement pst = conn.prepareStatement(query);
								ResultSet rs = pst.executeQuery();
								while(rs.next()){
									SubList.add(rs.getString("Name"));
								}
								rs.close();
								pst.close();
								SecondList.setItems(null);
								SecondList.setItems(SubList);
							} catch (Exception e2) {
								System.out.println("Didnt work");
								e2.printStackTrace();
							}
							try{
								AddPane.getChildren().removeAll(FirstList,Added);
								Subs(User);
							}catch(IOException e){
							}
						}
					}catch(Exception e){
						
					}
					
				}
			});
		
		FirstList.setPrefHeight(100);
		FirstList.setPrefWidth(120);
		FirstList.setLayoutX(20);
		FirstList.setLayoutY(60);
		AddPane.getChildren().add(FirstList);
	}
	
	public static void Subs(String User) throws IOException{
		SubsList = FXCollections.observableArrayList();
		AddPane.getChildren().removeAll(SecondList,Back,PickCat,Added);
		
		AddPane.getChildren().addAll(Back,PickCat);
		Back.setLayoutX(20);
		Back.setLayoutY(180);
		SecondList.setPrefHeight(100);
		SecondList.setPrefWidth(120);
		SecondList.setLayoutX(20);
		SecondList.setLayoutY(60);
		PickCat.setLayoutX(20);
		PickCat.setLayoutY(40);
		
		
		AddPane.getChildren().add(SecondList);
		///////
		Back.setOnAction(e -> {
			if(AddPane.getChildren().contains(SecondList)){
				AddPane.getChildren().removeAll(DupEnt,EmpField,SecondList,Back,AddSub,SubField,SubBut,PickCat,PickSubCat);
				FirstList.setItems(null);
				FirstList.setItems(CatList);
				AddPane.getChildren().add(FirstList);
				AddPane.getChildren().remove(PickCat);
				SecondList.getSelectionModel().clearSelection();
				AddPane.getChildren().removeAll(AddSub,SubField,SubBut);
			}
			else if(AddPane.getChildren().contains(ThirdList)){
				System.out.println("Test");
				AddPane.getChildren().removeAll(DupEnt,EmpField,ThirdList,PickCat,PickSubCat,ItemQty,ItemQtyField,AddItem,ItemField,ItemBut);
				SecondList.setItems(null);
				SecondList.setItems(SubList);
				AddPane.getChildren().addAll(SecondList,PickCat);
				AddPane.getChildren().removeAll(PickSubCat,AddItem,ItemField,ItemBut,ItemQty,ItemQtyField);
			}
		});
		/////////////////////////////////
		SecondList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
				try{
					
					if(ToSub == true){
						String search = "select * from Category where Name = '"+new_val+"'";
						PreparedStatement psearch = conn.prepareStatement(search);
						ResultSet rsearch = psearch.executeQuery();
						while(rsearch.next()){
							CatID = rsearch.getInt("ID");
						}
						psearch.close();
						rsearch.close();
						AddPane.getChildren().removeAll(AddCat,CatField,CatBut,AddSub,SubField,SubBut);
						AddPane.getChildren().addAll(AddSub,SubField,SubBut);
						AddSub.setLayoutX(160);
						AddSub.setLayoutY(60);
						SubField.setLayoutX(160);
						SubField.setLayoutY(100);
						SubField.setMaxWidth(100);
						SubBut.setLayoutX(160);
						SubBut.setLayoutY(140);
						Holder = new_val;
					}else{
						System.out.println("Test111");
						String search = "select * from Category where Name = '"+new_val+"'";
						PreparedStatement psearch = conn.prepareStatement(search);
						ResultSet rsearch = psearch.executeQuery();
						while(rsearch.next()){
							CatID = rsearch.getInt("ID");
						}
						psearch.close();
						rsearch.close();
						
						Holder = new_val;
						
						String query = "select * from SubCategory where ParentID = '"+CatID+"'";
						try {
							System.out.println("111");
							PreparedStatement pst = conn.prepareStatement(query);
							ResultSet rs = pst.executeQuery();
							
							while(rs.next() == true){

								SubsList.add(rs.getString("Name"));
							}
							rs.close();
							pst.close();
							ThirdList.setItems(null);
							ThirdList.setItems(SubsList);
						} catch (Exception e2) {
							System.out.println("Didnt work");
							e2.printStackTrace();
						}
						
						AddPane.getChildren().removeAll(Added,SecondList);
						try {
							FinalOne(User,Holder);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}catch(Exception e){
					
				}
				
			}
		});
		
	}
	
	public static void FinalOne(String User, String CAT) throws IOException{
		
		AddPane.getChildren().removeAll(Added,PickCat);
		
		
//		Refresh(User,Holder);
		
		ThirdList.setPrefHeight(100);
		ThirdList.setPrefWidth(120);
		ThirdList.setLayoutX(20);
		ThirdList.setLayoutY(60);
		PickSubCat.setLayoutX(20);
		PickSubCat.setLayoutY(40);
		String query = "select * from SubCategory where ParentID = '"+CatID+"'";
		try {
			SubsList.clear();
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				SubsList.add(rs.getString("Name"));
			}
			rs.close();
			pst.close();
			ThirdList.setItems(null);
			ThirdList.setItems(SubsList);
		} catch (Exception e2) {
			System.out.println("Didnt work");
			e2.printStackTrace();
		}
		AddPane.getChildren().removeAll(ThirdList,PickSubCat);
		AddPane.getChildren().addAll(ThirdList,PickSubCat);
		
		ThirdList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
				
				
				String search = "select * from SubCategory where Name = '"+new_val+"'";
				PreparedStatement psearch;
				try {
					psearch = conn.prepareStatement(search);
					ResultSet rsearch = psearch.executeQuery();
					while(rsearch.next()){
						SubID = rsearch.getInt("ID");
					}
					psearch.close();
					rsearch.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				
				AddPane.getChildren().removeAll(AddItem,ItemField,ItemBut,ItemQty,ItemQtyField);
				AddPane.getChildren().addAll(AddItem,ItemField,ItemBut,ItemQty,ItemQtyField);
				AddItem.setLayoutX(160);
				AddItem.setLayoutY(60);
				ItemField.setLayoutX(160);
				ItemField.setLayoutY(100);
				ItemField.setMaxWidth(100);
				ItemBut.setLayoutX(160);
				ItemBut.setLayoutY(140);
				ItemQty.setLayoutX(300);
				ItemQty.setLayoutY(60);
				ItemQtyField.setLayoutX(300);
				ItemQtyField.setLayoutY(100);
				ItemQtyField.setMaxWidth(100);
				SubHolder = new_val;
				
			}
		});
		
		
	}
	
	public static void Setup(String User) throws IOException{
		
		CatList.add("Add Category");
		CatList.add("Add Sub-Category");
		CatList.add("Add Item");
		FirstList.setItems(CatList);
		
		
		
//		FileReader in = new FileReader(User+".txt");
//		BufferedReader input = new BufferedReader(in);
//		while((check = input.readLine())!= null){
//			if(check.substring(0,4).equals("CAT:")){
//				if((check.contains("ITEM")) == false && (check.contains("SUB:")) == false){
//					check = check.substring(5,check.length());
//					SubList.add(check);
//				}
//			}
//		}
//		SecondList.setItems(SubList);
		
		String query = "select * from Category";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				SubList.add(rs.getString("Name"));
			}
			rs.close();
			pst.close();
			
		} catch (Exception e2) {
			System.out.println("Didnt work");
			e2.printStackTrace();
		}
		
		FirstTime = false;
	}
	
//	public static void Refresh(String User,String CAT) throws IOException{
//		
//		SubsList = FXCollections.observableArrayList();
//		
//		FileReader in = new FileReader(User+".txt");
//		BufferedReader input = new BufferedReader(in);
//		while((check = input.readLine())!= null){
//			
//			if(check.substring(0,4).equals("CAT:")){
//				if((check.contains("ITEM")) == false && (check.contains("SUB:")) == true && (check.contains(CAT) == true)){
//					int id = check.lastIndexOf("SUB:");
//					check = check.substring(id+5,check.length());
//					SubsList.add(check);
//					
//					
//				}
//				
//				
//			}
//		}
//		
//		SecondList.setItems(SubList);
//	}
	
}
