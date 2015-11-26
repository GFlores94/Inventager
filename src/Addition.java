import java.util.ArrayList;
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



public class Addition {


	static String check = null;
	
	
	
	static Label SelCat = new Label("Select Category:");
	static Label SubCatLabel;
	static TextField SubCatText = new TextField();
	static Label NullSel = new Label("Duplicate Entry");
	static Label NoSubCatVal = new Label("No Sub-Category Written");
	static boolean done = false;
	static Label ChoSub = new Label("Choose a Sub-Category:");
	static Pane AddPane = new Pane();
	static Button Add2 = new Button("Add");
	static Label ToAdd = new Label("Item Name:");
	static TextField AddField = new TextField();
	static Button Add3 = new Button("Add");
	static Label Added = new Label("Added!");
	static Label Quantity = new Label("Quantity:");
	static TextField QuantityField = new TextField();
	static Label NullVal = new Label("No Sub-Category Selected!");
	static Scene AddItemSce = new Scene(AddPane,500,250);
	static Label Duplicate = new Label("Duplicate Category");
	static ListView AddView;
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
	
	public static void plsword(String User) throws IOException{
	
	Main.MM.setScene(AddItemSce);
	Main.MM.setTitle("Adding Items");
	Label DupSub = new Label("Duplicate Sub-Category");
	secondreqs(User);
	
	AddPane.getChildren().addAll(Menu.Home,Menu.addbut,Menu.viewbut,Menu.DeleteBut);
	

	
	

	
	SubView.setPrefHeight(95);
	SubView.setPrefWidth(120);
	SubView.setLayoutX(20);
	SubView.setLayoutY(60);
	
	AddView.setPrefHeight(95);
	AddView.setPrefWidth(120);
	AddView.setLayoutX(20);
	AddView.setLayoutY(60);
	
	
	
	CatView.setPrefHeight(95);
	CatView.setPrefWidth(120);
	CatView.setLayoutX(20);
	CatView.setLayoutY(60);
	
	
	

	
	
	CatField.setMaxWidth(100);		
	
	TextField SubCatField = new TextField();
	SubCatField.setMaxWidth(100);
	Label PickCat = new Label("Under What Category?");
	
	
	
	
	
	
	
	
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
	

	Add.setOnAction(e -> {
		
		
		try {
			FileReader in2 = new FileReader(User+".txt");
			BufferedReader input2 = new BufferedReader(in2);
			while((check = input2.readLine()) != null){
				int id = check.indexOf(CatField.getText());
				int id2 = check.indexOf("CAT");
				if((id - id2) == 5){
					AddPane.getChildren().removeAll(Added,Duplicate);
					AddPane.getChildren().add(Duplicate);
					Duplicate.setLayoutX(160);
					Duplicate.setLayoutY(160);
					return;
				}
				
			}
			PrintWriter write = new PrintWriter(new FileWriter(User+".txt",true));
			write.println("CAT: "+ CatField.getText());
			write.close();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		AddPane.getChildren().removeAll(Added,Duplicate);
		AddPane.getChildren().add(Added);
		Added.setLayoutX(160);
		Added.setLayoutY(160);
	});

	
	Add2.setOnAction(e -> {
		

		if(SubCatText.getText().equals("")){
			AddPane.getChildren().removeAll(NoSubCatVal,Added,NullSel,DupSub);
			AddPane.getChildren().add(NoSubCatVal);
			NoSubCatVal.setLayoutX(200);
			NoSubCatVal.setLayoutY(160);
		}else{
			AddPane.getChildren().removeAll(NoSubCatVal,Added,NullSel,DupSub);
			try {
				FileReader in2 = new FileReader(User+".txt");
				BufferedReader input2 = new BufferedReader(in2);
				while((check = input2.readLine()) != null){
					boolean checker = check.contains(SubCatText.getText());
					if(checker == true){
						AddPane.getChildren().add(DupSub);
						DupSub.setLayoutX(200);
						DupSub.setLayoutY(160);
						return;
					}
				}
				check = catselect;
				PrintWriter write = new PrintWriter(new FileWriter(User+".txt",true));
				write.println("CAT: "+check+" SUB: "+SubCatText.getText());
				write.close();
				AddPane.getChildren().add(Added);
				Added.setLayoutX(200);
				Added.setLayoutY(160);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	});
	
	
	
	
	}

	public static void secondreqs(String User) throws IOException{
		AddView = new ListView();
		ObservableList AddedItems = FXCollections.observableArrayList();
		AddedItems.add("Add Category");
		AddedItems.add("Add Sub-Category");
		AddedItems.add("Add Item");
		AddView.setItems(AddedItems);
		
		AddView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
							String old_val, String new_val) {
						if(new_val.equals("Add Category")){
							AddPane.getChildren().removeAll(SelCat,SubCatLabel,SubCatText,ChoSub,Added,ToAdd,AddField,Add2,Quantity,QuantityField,WhichCat,Add,ToAdd,AddField,Add3,Quantity,QuantityField);
							AddPane.getChildren().addAll(WhichCat,CatField,Add);
							WhichCat.setLayoutX(160);
							WhichCat.setLayoutY(70);
							CatField.setLayoutX(160);
							CatField.setLayoutY(100);
							Add.setLayoutX(160);
							Add.setLayoutY(130);
						}
						if(new_val.equals("Add Sub-Category")){
							AddPane.getChildren().removeAll(SelCat,SubCatLabel,SubCatText,ChoSub,Added,ToAdd,AddField,Add3,Quantity,CatField,QuantityField,WhichCat,Add,ToAdd,AddField,Add3,Quantity,QuantityField,Add2);
							AddPane.getChildren().remove(AddView);
							AddPane.getChildren().addAll(CatView,Back,SelCat);
							SelCat.setLayoutX(20);
							SelCat.setLayoutY(40);
							Back.setLayoutX(20);
							Back.setLayoutY(160);
							try {
								thirdreq(User);
							} catch (IOException e) {
								e.printStackTrace();
							}
							AddCatPls = true;
						}
						if(new_val.equals("Add Item")){
							AddPane.getChildren().removeAll(SelCat,SubCatLabel,SubCatText,ChoSub,Added,ToAdd,AddField,Add3,Quantity,CatField,QuantityField,WhichCat,Add,ToAdd,AddField,Add3,Quantity,QuantityField);
							AddPane.getChildren().remove(AddView);
							AddPane.getChildren().addAll(CatView,Back);
							Back.setLayoutX(20);
							Back.setLayoutY(160);
							try {
								thirdreq(User);
							} catch (IOException e) {
								e.printStackTrace();
							}
							AddCatPls = false;
						}
					}
				});
		
		AddPane.getChildren().add(AddView);
		
	}
	
	public static void thirdreq(String User) throws IOException{
		
		ObservableList AddedSub = FXCollections.observableArrayList();
		ObservableList AddedCat = FXCollections.observableArrayList();
		FileReader in = new FileReader(User+".txt");
		BufferedReader input = new BufferedReader(in);
		while((check = input.readLine())!= null){		
			if(check.substring(0,4).equals("CAT:")){
				if((check.contains("ITEM")) == false && (check.contains("SUB:")) == false){
					check = check.substring(5,check.length());
					AddedCat.add(check);
				}
			}
		}
		CatView.setItems(AddedCat);
		
		CatView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
							String old_val, String new_val) {
						catselect = new_val;
						if(AddCatPls == true){
							AddPane.getChildren().removeAll(WhichCat,CatField,Add,Added,SubCatLabel,SubCatText,Add2,SelCat,ChoSub,ToAdd,AddField,Add3,Quantity,QuantityField,ToAdd,AddField,Add3,Quantity,QuantityField);
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
							try {
								AddedSub.clear();
								FileReader in = new FileReader(User+".txt");
								BufferedReader input = new BufferedReader(in);
								while((check = input.readLine())!= null){
									
									if(check.substring(0,4).equals("CAT:")){
										if((check.contains("ITEM")) == false && (check.contains("SUB:")) == true && (check.contains(new_val) == true)){
											int id = check.lastIndexOf("SUB:");
											check = check.substring(id+5,check.length());
											AddedSub.add(check);		
										}
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							PickedCat = new_val;
							try {
								fourthreq(User);
							} catch (IOException e) {
								e.printStackTrace();
							}
							SubView.setItems(AddedSub);
							AddPane.getChildren().remove(CatView);
							AddPane.getChildren().add(SubView);
						}
						
					}
				});
	}

	public static void fourthreq(String User) throws IOException{
		
		

		
		
		SubView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> ov, 
							String old_val, String new_val) {
						System.out.println("hello");
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
	}
	
}

	


