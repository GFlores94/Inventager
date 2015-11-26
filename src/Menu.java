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

public class Menu{

	static public Scene Menu;
	static String check = null;
	static Pane layout = new Pane();
	static Button viewbut = new Button();
	static Button addbut = new Button();
	static ComboBox Categories = new ComboBox();
	static Label SelCat = new Label("Select Category:");
	static Label SubCatLabel;
	static TextField SubCatText = new TextField();
	static Label NullSel = new Label("Duplicate Entry");
	static Label NoSubCatVal = new Label("No Sub-Category Written");
	static boolean ADD = false;
	static Label ChoSub = new Label("Choose a Sub-Category:");
	static Pane AddPane = new Pane();
	static ComboBox Sub_Categories = new ComboBox();
	static Label ToAdd = new Label("Item Name:");
	static TextField AddField = new TextField();
	static Button Add3 = new Button("Add");
	static Label Added = new Label("Added!");
	static Label Quantity = new Label("Quantity:");
	static TextField QuantityField = new TextField();
	static Label NullVal = new Label("No Sub-Category Selected!");
	static Scene AddItemSce = new Scene(AddPane,500,250);
	static Button Home = new Button("Home");
	static Button DeleteBut = new Button();
	static Label Duplicate = new Label("Duplicate Category");
	static ListView AddView = new ListView();
	static ListView CatView = new ListView();
	static ListView SubView = new ListView();
	static boolean AddCatPls = false;
	static String catselect = null;
	static String PickedCat = null;
	static String PickedSub = null;
	static Label WhichSubCat = new Label("New Sub-Category");
	static Label WhichCat = new Label("New Category:");
	static Button Add = new Button("Add");
	static TextField CatField = new TextField();
	static Button Back = new Button("Back");
	static Button Harro = new Button("Click here");
	
	public static void MainMenu(String User, int ID) throws IOException{
		Label Welcome = new Label("Welcome " + User);
		
		

		
		
		DeleteBut.setText("Delete Item");
		viewbut.setText("View Items");
		addbut.setText("Add Items");
		addbut.setOnAction(e -> {
			try {
				tbd.RunAdd(User,ID);
			} catch (Exception e1) {
				e1.printStackTrace();
			}	
		});
		Home.setLayoutX(60);
		Home.setLayoutY(10);
		addbut.setLayoutX(140);
		addbut.setLayoutY(10);
		viewbut.setLayoutX(240);
		viewbut.setLayoutY(10);
		DeleteBut.setLayoutX(340);
		DeleteBut.setLayoutY(10);
		layout.getChildren().addAll(viewbut,addbut,Welcome,DeleteBut,Home);
		Welcome.setLayoutX(200);
		Welcome.setLayoutY(50);
		Menu = new Scene(layout,500,250);
		
		viewbut.setOnAction(e -> {
			try {
				ViewItem.ViewMenu(User,ID);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		DeleteBut.setOnAction(e -> {
			try {
				DeleteItem.DeleteMenu(User, ID);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		Home.setOnAction(e -> {
			Main.MM.setScene(Menu);
			layout.getChildren().clear();
			layout.getChildren().addAll(viewbut,addbut,Welcome,DeleteBut,Home);
		});
	}
	
	
	public static void AddItem(String User) throws IOException{
		Main.MM.setScene(AddItemSce);
		Main.MM.setTitle("Adding Items");
		Label DupSub = new Label("Duplicate Sub-Category");
		Button Add2 = new Button();
		Add2.setText("Add");
		AddPane.getChildren().clear();
		AddView.getItems().clear();
		AddPane.getChildren().addAll(Home,addbut,viewbut,DeleteBut);
		
		ObservableList AddedItems = FXCollections.observableArrayList();
		ObservableList AddedCat = FXCollections.observableArrayList();
		ObservableList AddedSub = FXCollections.observableArrayList();
		

		

		
		SubView.setPrefHeight(95);
		SubView.setPrefWidth(120);
		SubView.setLayoutX(20);
		SubView.setLayoutY(60);
		
		AddView.setPrefHeight(95);
		AddView.setPrefWidth(120);
		AddView.setLayoutX(20);
		AddView.setLayoutY(60);
		AddedItems.add("Add Category");
		AddedItems.add("Add Sub-Category");
		AddedItems.add("Add Item");
		AddView.setItems(AddedItems);
		
		CatView.setItems(AddedCat);
		CatView.setPrefHeight(95);
		CatView.setPrefWidth(120);
		CatView.setLayoutX(20);
		CatView.setLayoutY(60);
		
		AddPane.getChildren().add(AddView);
		

		
		
		CatField.setMaxWidth(100);		
		
		TextField SubCatField = new TextField();
		SubCatField.setMaxWidth(100);
		Label PickCat = new Label("Under What Category?");
		
		
		
		CatView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
							String old_val, String new_val) {
						catselect = new_val;
						if(AddCatPls == true){
							AddPane.getChildren().removeAll(WhichCat,CatField,Add,Added,SubCatLabel,DupSub,SubCatText,Add2,Categories,SelCat,ChoSub,Sub_Categories,ToAdd,AddField,Add3,Quantity,QuantityField,ToAdd,AddField,Add3,Quantity,QuantityField);
							SubCatLabel = new Label("Sub-Category Name:");
							AddPane.getChildren().addAll(SubCatText,SubCatLabel,Add2);
							SubCatLabel.setLayoutX(200);
							SubCatLabel.setLayoutY(70);
							SubCatText.setMaxWidth(100);
							SubCatText.setLayoutX(200);
							SubCatText.setLayoutY(100);
							Add2.setLayoutX(200);
							Add2.setLayoutY(130);
						}else{

							PickedCat = new_val;
							
							SubView.setItems(AddedSub);
							AddPane.getChildren().remove(CatView);
							AddPane.getChildren().add(SubView);
						}
						
					}
				});
		
		SubView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
							String old_val, String new_val) {
						
						PickedSub = new_val;
						AddPane.getChildren().removeAll(ToAdd,AddField,Add3,Added,Quantity,QuantityField,NullVal);
						AddPane.getChildren().addAll(ToAdd,AddField,Add3,Quantity,QuantityField);
						ToAdd.setLayoutX(200);
						ToAdd.setLayoutY(100);
						AddField.setMaxWidth(100);
						AddField.setLayoutX(200);
						AddField.setLayoutY(120);
						Add3.setLayoutX(200);
						Add3.setLayoutY(150);
						Quantity.setLayoutX(330);
						Quantity.setLayoutY(100);
						QuantityField.setMaxWidth(100);
						QuantityField.setLayoutX(330);
						QuantityField.setLayoutY(120);
						
						Label NoName = new Label("Item Field Empty!");
						Label NoQty = new Label("No Quantity Specified!");
						
						Add3.setOnAction(e -> {
							if(AddField.getText().equals("")){
								AddPane.getChildren().removeAll(NoName,NullVal,NoQty);
								AddPane.getChildren().add(NoName);
								NoName.setLayoutX(320);
								NoName.setLayoutY(200);
								return;
							}

							if(QuantityField.getText().equals("")){
								AddPane.getChildren().removeAll(NoName,NullVal,NoQty);
								AddPane.getChildren().add(NoQty);
								NoQty.setLayoutX(320);
								NoQty.setLayoutY(200);
								return;
							}
							try {
								PrintWriter write = new PrintWriter(new FileWriter(User+".txt", true));
								write.println("CAT: " + PickedCat + " SUB: " + PickedSub + " ITEM: " + AddField.getText() + " QTY: " + QuantityField.getText());
								write.close();
								AddPane.getChildren().add(Added);
								Added.setLayoutX(320);
								Added.setLayoutY(210);
								AddPane.getChildren().removeAll(NullVal,NoName,NoQty);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						});
						
					}
				});
		
		Back.setOnAction(e -> {
			if(AddPane.getChildren().contains(CatView)){
				AddPane.getChildren().removeAll(CatView,Back,SubCatText,SubCatLabel,Add2,SelCat,ToAdd,AddField,Add3,Quantity,QuantityField);
				AddPane.getChildren().add(AddView);
			}
			if(AddPane.getChildren().contains(SubView)){
				AddPane.getChildren().removeAll(SubView,ToAdd,AddField,Add3,Quantity,QuantityField);
				AddPane.getChildren().add(CatView);
			}
		});
		

		
		Add2.setOnAction(e -> {
			

			if(SubCatText.getText().equals("")){
				AddPane.getChildren().removeAll(NoSubCatVal,Added,NullSel,DupSub);
				AddPane.getChildren().add(NoSubCatVal);
				NoSubCatVal.setLayoutX(200);
				NoSubCatVal.setLayoutY(160);
			}else{
				AddPane.getChildren().removeAll(NoSubCatVal,Added,NullSel,DupSub);

			}
			
		});
		
		

		
		
	}
	
	
	public static void ItemAddition(String User, boolean boo) throws IOException{
		AddField.setMaxWidth(100);
		if(boo == true){
			AddPane.getChildren().removeAll(ToAdd,AddField,Add3,Added,Quantity,QuantityField,NullVal);
			AddPane.getChildren().addAll(ToAdd,AddField,Add3,Quantity,QuantityField);
			ToAdd.setLayoutX(20);
			ToAdd.setLayoutY(150);
			
			AddField.setLayoutX(20);
			AddField.setLayoutY(170);
			Add3.setLayoutX(20);
			Add3.setLayoutY(200);
			Quantity.setLayoutX(160);
			Quantity.setLayoutY(150);
			QuantityField.setMaxWidth(100);
			QuantityField.setLayoutX(160);
			QuantityField.setLayoutY(170);
			
			Label NoName = new Label("Item Field Empty!");
			Label NoQty = new Label("No Quantity Specified!");
			
			Add3.setOnAction(e -> {
				if(AddField.getText().equals("")){
					AddPane.getChildren().removeAll(NoName,NullVal,NoQty);
					AddPane.getChildren().add(NoName);
					NoName.setLayoutX(160);
					NoName.setLayoutY(200);
					return;
				}
				if(Sub_Categories.getValue() == null){
					AddPane.getChildren().removeAll(NoName,NullVal,NoQty);
					AddPane.getChildren().add(NullVal);
					NullVal.setLayoutX(160);
					NullVal.setLayoutY(200);
					return;
				}
				if(QuantityField.getText().equals("")){
					AddPane.getChildren().removeAll(NoName,NullVal,NoQty);
					AddPane.getChildren().add(NoQty);
					NoQty.setLayoutX(160);
					NoQty.setLayoutY(200);
					return;
				}
				try {
					PrintWriter write = new PrintWriter(new FileWriter(User+".txt", true));
					write.println("CAT: " + Categories.getValue().toString() + " SUB: " + Sub_Categories.getValue().toString() + " ITEM: " + AddField.getText() + " QTY: " + QuantityField.getText());
					write.close();
					AddPane.getChildren().add(Added);
					Added.setLayoutX(70);
					Added.setLayoutY(210);
					AddPane.getChildren().removeAll(NullVal,NoName,NoQty);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		}else{
			AddPane.getChildren().removeAll(ToAdd,AddField,Add3,Added,Quantity,QuantityField,NullVal);
			AddPane.getChildren().addAll(ToAdd,AddField,Add3,Quantity,QuantityField);
			ToAdd.setLayoutX(20);
			ToAdd.setLayoutY(120);
			AddField.setLayoutX(20);
			AddField.setLayoutY(140);
			Add3.setLayoutX(20);
			Add3.setLayoutY(170);
			Quantity.setLayoutX(160);
			Quantity.setLayoutY(120);
			QuantityField.setMaxWidth(100);
			QuantityField.setLayoutX(160);
			QuantityField.setLayoutY(140);
			Add3.setOnAction(e -> {
				try {
					PrintWriter write = new PrintWriter(new FileWriter(User+".txt", true));
					write.println("CAT: " + Categories.getValue().toString() + " ITEM: " + AddField.getText() + " QTY: " + QuantityField.getText());
					write.close();
					AddPane.getChildren().add(Added);
					Added.setLayoutX(70);
					Added.setLayoutY(130);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		}
	}

	
}
