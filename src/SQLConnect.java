import java.sql.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SQLConnect {

	static Label ConnectFail = new Label("Connection Failed");
	
	Connection conn = null;
	Connection conn2 = null;
	
	public static Connection dbConnect(){
		try{
			Class.forName("org.sqlite.JDBC");
			Connection Conn = DriverManager.getConnection("jdbc:sqlite:C:\\Inventory\\Accounts.db");
			return Conn;
		}catch(Exception e){
			Stage PopUp = new Stage();
			Pane Err = new Pane();
			Scene ErrScene = new Scene(Err,200,100);
			Err.getChildren().add(ConnectFail);
			ConnectFail.setLayoutX(100);
			ConnectFail.setLayoutY(20);
			PopUp.setScene(ErrScene);
			PopUp.show();
			return null;
		}
	}
	

	
}
