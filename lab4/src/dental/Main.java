package dental;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// --module-path /home/golde/Downloads/javafx-sdk-13/lib --add-modules javafx.controls,javafx.fxml
// --module-path "C:\Users\ochko\Downloads\openjfx-13_windows-x64_bin-sdk\javafx-sdk-13\lib" --add-modules javafx.controls,javafx.fxml
// scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
public class Main extends Application {

  private String user = "root";
  private String pw = "root";
  private String checkUser, checkPw;

  @Override
  public void start(Stage primaryStage) {

		primaryStage.setTitle("Dental Management System");
        
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,50,50,50));
        
		HBox hb = new HBox();
		hb.setPadding(new Insets(20,20,20,30));
        
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20,20,20,20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);
        
//		Implementing Nodes for GridPane
		Label lblUserName = new Label("Username");
		final TextField txtUserName = new TextField();
		Label lblPassword = new Label("Password");
		final PasswordField pf = new PasswordField();
		Button btnLogin = new Button("Login");
		final Label lblMessage = new Label();
        
//		Adding Nodes to GridPane layout
		gridPane.add(lblUserName, 0, 0);
		gridPane.add(txtUserName, 1, 0);
		gridPane.add(lblPassword, 0, 1);
		gridPane.add(pf, 1, 1);
		gridPane.add(btnLogin, 2, 1);
		gridPane.add(lblMessage, 1, 2);
        
		Text text = new Text("JavaFX 2 Login");
		text.setFont(Font.font ("Verdana", 30));

		hb.getChildren().add(text);
                          
		//Add ID's to Nodes
		bp.setId("bp");
		gridPane.setId("root");
		btnLogin.setId("btnLogin");
		text.setId("text");
                
		//Action for btnLogin
    btnLogin.setOnAction((ActionEvent event) -> {

			DatabaseHelper helper = new DatabaseHelper();
			helper.create();

      checkUser = txtUserName.getText().toString();
      checkPw = pf.getText().toString();
      if(checkUser.equals(user) && checkPw.equals(pw)){
        lblMessage.setText("Congratulations!");
        lblMessage.setTextFill(Color.GREEN);
      }
      else{
        lblMessage.setText("Incorrect user or pw.");
        lblMessage.setTextFill(Color.RED);
      }
      txtUserName.setText("");
      pf.setText("");
    });
       
		bp.setTop(hb);
		bp.setCenter(gridPane);  
        
		Scene scene = new Scene(bp);
		primaryStage.setScene(scene);
//		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {launch(args);}
}