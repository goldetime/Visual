package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// --module-path /home/golde/Downloads/javafx-sdk-13/lib --add-modules javafx.controls,javafx.fxml
public class Main extends Application {
	Stage s;
	BorderPane root;
	ObservableList<String> days;
	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new BorderPane();
		root.setPadding(new Insets(10));

		StackPane top = topStackPane();
		top.setStyle("-fx-border-color: black");
		
		StackPane mid = midStackPane();
		mid.setStyle("-fx-border-color: black");

		StackPane bot = botStackPane();
		bot.setStyle("-fx-border-color: black");
		
		root.setTop(top);
		BorderPane.setMargin(top, new Insets(10));
		root.setCenter(mid);
		BorderPane.setMargin(mid, new Insets(10));
		root.setBottom(bot);
		BorderPane.setMargin(bot, new Insets(10));

		s = primaryStage;
		primaryStage.setTitle("Employee Payroll");
		primaryStage.setScene(new Scene(root, 700, 400));
		primaryStage.show();
	}

	StackPane topStackPane() {
		StackPane p = new StackPane();

		Label title = new Label();
		title.setText("Employee Identification");
		title.setTranslateX(7);
		title.setTranslateY(-7);

		StackPane.setAlignment(title, Pos.TOP_LEFT);

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		// hbox.setStyle("-fx-border-color: black");
		hbox.setPadding(new Insets(20));

		Label eName = new Label("Employee Name:");
		TextField txtEmployeeName = new TextField();

		Label hSalary = new Label("Hourly Salary:");
		TextField txtHourlySalary = new TextField();
		txtHourlySalary.setPromptText("0.00");

		hbox.getChildren().addAll(eName, txtEmployeeName, hSalary, txtHourlySalary);
		
		p.getChildren().addAll(hbox, title);
		return p;
	}

	StackPane midStackPane() {
		StackPane p = new StackPane();

		Label title = new Label();
		title.setText("Time Value");
		title.setTranslateX(7);
		title.setTranslateY(-7);
		StackPane.setAlignment(title, Pos.TOP_LEFT);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));

		days = FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday",
																						 "Thursday", "Firday", "Saturday", "Sunday");

		// days.stream().forEach(i -> {
		// 		int j = 0;
		// 		grid.add(new Label(i), j++ % 7, j++ / 7);
		// 	});

		int n = 7;
		for (int i = 0; i < n; i++) {
			grid.add(new Label(days.get(i)), i % n + 1, 0);
		}

		Label lblFirstWeek = new Label("First Week:");
		Label lblSecondWeek = new Label("Second Week:");
		grid.add(lblFirstWeek, 0, 1);
		grid.add(lblSecondWeek, 0, 2);

		TextField[] fields = new TextField[14];
		int j = 0;
		for (TextField i : fields) {
			i = new TextField();
			i.setPromptText("0.00");
			i.setAlignment(Pos.CENTER_RIGHT);
			i.setPrefWidth(60);
			i.setMaxWidth(60);
			grid.add(i, j % n + 1, j / n + 1);
			j++;
		}
		
		p.getChildren().addAll(grid, title);
		return p;
	}

	StackPane botStackPane() {
		StackPane p = new StackPane();

		Label title = new Label();
		title.setText("Employee Identification");
		title.setTranslateX(7);
		title.setTranslateY(-7);
		StackPane.setAlignment(title, Pos.TOP_LEFT);

		HBox hbox = new HBox();
		hbox.setSpacing(60);
		// t, l, b, r
		hbox.setPadding(new Insets(0, 20, 0, 20));

		Button c = new Button("Calculate");
		c.setPrefWidth(150);
		c.setPrefHeight(80);
		c.setPadding(new Insets(20));

		days = FXCollections.observableArrayList("Regular:", "Overtime:",
																						 "Time", "Pay Amt");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));

		int n = 2;
		for (int i = 0; i < n; i++) {
			grid.add(new Label(days.get(i)), 0, i % n + 1);
			grid.add(new Label(days.get(i + 2)), i % n + 1, 0);
		}

		TextField[] fields = new TextField[4];
		int j = 0;
		for (TextField i : fields) {
			i = new TextField();
			i.setPromptText("0.00");
			i.setAlignment(Pos.CENTER_RIGHT);
			i.setPrefWidth(60);
			i.setMaxWidth(60);
			grid.add(i, j % n + 1, j / n + 1);
			j++;
		}

		VBox group = new VBox();
		HBox box = new HBox();
		box.setSpacing(25);

		Label lblNetPay = new Label("Net Pay:");
		TextField txtResult = new TextField();
		txtResult.setPromptText("0.00");
		txtResult.setAlignment(Pos.CENTER_RIGHT);
		txtResult.setPrefWidth(60);
		txtResult.setMaxWidth(60);

		box.getChildren().addAll(lblNetPay, txtResult);

		Button close = new Button("Close");
		close.setPrefWidth(180);
		close.setOnAction(e -> {
				s.close();
		});
		// close.setPrefHeight();

		group.setPadding(new Insets(20));
		group.getChildren().addAll(box, close);
		
		hbox.getChildren().addAll(c, grid, group);
		p.getChildren().addAll(hbox, title);
		return p;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
