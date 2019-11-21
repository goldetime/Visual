package dental;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

	private DatabaseHelper helper;
	public Stage stage;

  @Override
  public void start(Stage primaryStage) {

		stage = primaryStage;
		primaryStage.setTitle("Dental Management System");

		helper = new DatabaseHelper();
		helper.create();

		primaryStage.setScene(loginScene());
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public Scene loginScene() {

		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,50,50,50));

		HBox hb = new HBox();
		hb.setPadding(new Insets(20,20,20,30));

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20,20,20,20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

//		Implementing Nodes for GridPane
		Label lblUserName = new Label("Нэр:");
		final TextField txtUserName = new TextField();

		Label lblPassword = new Label("Нууц үг:");
		final PasswordField pf = new PasswordField();

		final Label lblMessage = new Label();

		Button btnLogin = new Button("Нэвтрэх");
		btnLogin.setOnAction((ActionEvent event) -> {
			helper = new DatabaseHelper();

			Admin r = helper.login(txtUserName.getText());
			if (r != null) {
				if (r.getFname().equals(txtUserName.getText()) && r.getPassword().equals(pf.getText())) {
					lblMessage.setText("Congratulations!");
					lblMessage.setTextFill(Color.GREEN);
				} else if (r.getFname().equals(txtUserName.getText()) && !r.getPassword().equals(pf.getText())) {
					lblMessage.setText("Нууц үг буруу!");
					lblMessage.setTextFill(Color.RED);
				}
			}  else {
				if (txtUserName.getText().equals("")
						|| pf.getText().equals("")) {
					lblMessage.setText("Хоосон талбар бөглөнө үү!");
					lblMessage.setTextFill(Color.RED);
				} else {
					lblMessage.setText("Бүртгэлгүй хэрэглэгч байна!");
					lblMessage.setTextFill(Color.RED);
				}
			}
			txtUserName.setText("");
			pf.setText("");
		});

		Button btnSignup = new Button("Бүртгүүлэх");
		btnSignup.setOnAction((ActionEvent event) -> {
			stage.setScene(signUpScene());
		});

		gridPane.add(lblUserName, 0, 0);
		gridPane.add(txtUserName, 1, 0);
		gridPane.add(lblPassword, 0, 1);
		gridPane.add(pf, 1, 1);
		gridPane.add(btnLogin, 0, 3);
		gridPane.add(btnSignup, 1, 3);
		gridPane.add(lblMessage, 1, 2);

		Text text = new Text("Тавтай морил");
		text.setFont(Font.font ("Verdana", 30));

		hb.getChildren().add(text);

		//Add ID's to Nodes
		bp.setId("bp");
		gridPane.setId("root");
		btnLogin.setId("btnLogin");
		text.setId("text");

		bp.setTop(hb);
		bp.setCenter(gridPane);

		return new Scene(bp, 500, 300);
	}

	public Scene signUpScene() {

		Text text = new Text("Бүртгэл");
		text.setFont(Font.font ("Verdana", 30));

		HBox hb = new HBox();
		hb.setPadding(new Insets(20,20,20,30));
		hb.getChildren().add(text);

		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,50,50,50));

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20,20,20,20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		Label lblUserName = new Label("Нэр:");
		final TextField txtUserName = new TextField();

		Label lblPassword = new Label("Нууц үг:");
		final PasswordField pf = new PasswordField();

		Label lblPassword1 = new Label("Нууц үг давт:");
		final PasswordField pf1 = new PasswordField();

		final Label lblMessage = new Label();

		Button btnSign = new Button("Бүртгүүлэх");
		btnSign.setOnAction((ActionEvent event) -> {
			helper = new DatabaseHelper();

			boolean t = helper.check(txtUserName.getText());
			if (t) {
				if (txtUserName.getText().equals("")
						|| pf.getText().equals("")
				|| pf1.getText().equals(""))  {
					lblMessage.setText("Хоосон талбар бөглөнө үү!");
					lblMessage.setTextFill(Color.RED);
				} else if (pf.getText().equals(pf1.getText())) {
					helper.sign(txtUserName.getText(), pf.getText());
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Мэдэгдэл");
					alert.setHeaderText(txtUserName.getText() + " нэртэй хэрэглэгч");
					alert.setContentText("Амжилттай бүртгэгдлээ!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							stage.setScene(loginScene());
						}
					});
				} else {
					lblMessage.setText("Нууц үг дахин шалга!");
					lblMessage.setTextFill(Color.RED);
				}
			} else {
				lblMessage.setText("Бүртгэлтэй хэрэглэгч байна!");
				lblMessage.setTextFill(Color.RED);
			}
			txtUserName.setText("");
			pf.setText("");
			pf1.setText("");
		});

		Button btnCancel = new Button("Цуцлах");
		btnCancel.setOnAction((ActionEvent e) -> {
			stage.setScene(loginScene());
		});

		gridPane.add(lblUserName, 0, 0);
		gridPane.add(txtUserName, 1, 0);
		gridPane.add(lblPassword, 0, 1);
		gridPane.add(lblPassword1, 0, 2);
		gridPane.add(pf, 1, 1);
		gridPane.add(pf1, 1, 2);
		gridPane.add(lblMessage, 1, 3);
		gridPane.add(btnSign, 0, 4);
		gridPane.add(btnCancel, 1, 4);

		bp.setTop(hb);
		bp.setCenter(gridPane);

		return new Scene(bp, 500, 300);
	}

	public static void main(String[] args) {launch(args);}
}