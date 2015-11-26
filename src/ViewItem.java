import java.util.*;
import java.sql.*;
import java.io.*;

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
import javafx.scene.layout.*;

public class ViewItem {

	static Connection conn = SQLConnect.dbConnect();
	static String check;
	static Pane StoreCat;
	static Label Holder;
	static ScrollPane CatScroll;
	static ListView<String> CatView;
	static ListView<String> SubCatView;
	static Pane ViewPane;
	static ListView<String> ItemView;
	static String qty = null;
	static Label Qty;
	static int CatID = 0;
	static int SubID = 0;
	
	public static void ViewMenu(String User, int ID) throws IOException{
		
		
		ViewPane = new Pane();
		Scene ViewMenu = new Scene(ViewPane,500,250);
		Main.MM.setScene(ViewMenu);
		Main.MM.setTitle("The View");
		
		ViewPane.getChildren().addAll(Menu.Home,Menu.addbut,Menu.viewbut,Menu.DeleteBut);
	
		
		FirstStore(User,ID);

		Label CurQty = new Label("Current Quantity:");

		
		CurQty.setLayoutX(40);
		CurQty.setLayoutY(190);

		
		ViewPane.getChildren().addAll(CatView,CurQty);
		Qty = new Label();
		ViewPane.getChildren().add(Qty);
		
		
	
		
		
		
		
	}
		
	public static void FirstStore(String User,int ID) throws IOException{
		CatView = new ListView<String>();
		CatView.setPrefHeight(100);
		CatView.setPrefWidth(100);
		CatView.setLayoutX(40);
		CatView.setLayoutY(70);
		
		Label CatLabel = new Label("Categories");
		ViewPane.getChildren().add(CatLabel);
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
			rs.close();
			pst.close();
			CatView.setItems(Observe);
			
		} catch (SQLException e1) {
			System.out.println("Didnt connect");
			e1.printStackTrace();
		}
		
		
		
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
							
							try {
								SecondStore(User,new_val,ID);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
		        });
		 
	}
	
	public static void SecondStore(String User, String CAT, int ID) throws IOException{
		SubCatView = new ListView();
		SubCatView.setPrefHeight(100);
		SubCatView.setPrefWidth(100);
		SubCatView.setLayoutX(140);
		SubCatView.setLayoutY(70);
		
		Label SubCatLabel = new Label("Sub-Categories");
		ViewPane.getChildren().add(SubCatLabel);
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
			SubCatView.setItems(Observe);
			
		} catch (SQLException e1) {
			System.out.println("Didnt connect");
			e1.printStackTrace();
		}

		
		if(Observe.isEmpty() == true){
			ViewPane.getChildren().add(SubCatView);
			ThirdStore(User,CAT,ID);
		}else{
			ViewPane.getChildren().add(SubCatView);
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
							
							
							
							try {
								ThirdStore(User,new_val,ID);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
		        });
		}
		
			
		
		
		
	}
	
	public static void ThirdStore(String User, String CAT, int ID) throws IOException{
		ItemView = new ListView();
		ItemView.setPrefHeight(100);
		ItemView.setPrefWidth(100);
		ItemView.setLayoutX(240);
		ItemView.setLayoutY(70);
		
		Label ItemLabel = new Label("Items");
		ViewPane.getChildren().add(ItemLabel);
		
		ItemLabel.setLayoutX(240);
		ItemLabel.setLayoutY(50);
		
		ObservableList Observe = FXCollections.observableArrayList();
		
		ObservableList IHoldU = FXCollections.observableArrayList();
		
		String query = "select * from Item where CatID = '"+CatID+"' and SubCatID = '"+SubID+"' ";
		try {
			IHoldU.clear();
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Observe.add(rs.getString("Name"));
				IHoldU.add(rs.getInt("Quantity"));
				
			}
			rs.close();
			pst.close();
			ItemView.setItems(Observe);
			
		} catch (SQLException e1) {
			System.out.println("Didnt connect");
			e1.printStackTrace();
		}
		
		
		
		
		
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
						
						
						Qty.setLayoutX(140);
						Qty.setLayoutY(190);
						Qty.setText(Integer.toString(qtyID));
						
											
					}
	        });
		
		ViewPane.getChildren().add(ItemView);
	}
	
	
}
	
	
	

