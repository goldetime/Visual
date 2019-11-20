package sample;

import com.sun.prism.Image;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ImageInput;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class PropertyEditor extends Application {
	
	Stage stg;
	ObservableList<String> val;
	BorderPane root;
	
  @Override
  public void start(Stage stage) throws Exception {
		root = new BorderPane();
		root.setPadding(new Insets(10));

		StackPane t = top();
		root.setTop(t);

		StackPane m = mid();
		root.setCenter(m);

		StackPane b = bot();
		root.setBottom(b);
		
		stg = stage;
		Scene s = new Scene(root, 800, 900);
		stage.setTitle("Property Editor");
		stage.setScene(s);
		stage.show();
  }



	public StackPane top() {
		StackPane t = new StackPane();
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));

		// 1 0 2 0 30 40 50 60
		grid.add(new Label("Property Type:"), 0, 0);
		ObservableList<String> typeList =
			FXCollections.observableArrayList("Condominium", "Townhouse", "Single Family", "Unknown");
		ComboBox<String> c1 = new ComboBox<>(typeList);
		c1.setMaxWidth(Double.MAX_VALUE);
		grid.add(c1, 1, 0);
		
		grid.add(new Label("Property#:"), 2, 0);
		TextField t1 = new TextField();
		grid.add(t1, 3, 0);
		
		grid.add(new Label("Address:"), 0, 1);
		TextField t2 = new TextField();
		grid.add(t2, 1, 1);
		
		grid.add(new Label("City:"), 0, 2);
		TextField t3 = new TextField();
		grid.add(t3, 1, 2);
		
		grid.add(new Label("ZIP Code:"), 0, 3);
		TextField t4 = new TextField();
		grid.add(t4, 1, 3);
		
		grid.add(new Label("Year Built:"), 0, 4);
		TextField t5 = new TextField();
		grid.add(t5, 1, 4);
		
		grid.add(new Label("Bedrooms:"), 0, 5);
		TextField t6 = new TextField();
		t6.setPromptText("0");
		t6.setAlignment(Pos.CENTER_RIGHT);
		grid.add(t6, 1, 5);

		grid.add(new Label("Market Value:"), 0, 6);
		TextField t8 = new TextField();
		t8.setPromptText("0.00");
		t8.setAlignment(Pos.CENTER_RIGHT);
		grid.add(t8, 1, 6);

		grid.add(new Label("State:"), 2, 2);
		// 
		ObservableList<String> stateList =
			FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID",
																				"IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
																				"NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
																				"SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
		ComboBox<String> c2 = new ComboBox<>(stateList);
		c2.setMaxWidth(Double.MAX_VALUE);
		grid.add(c2, 3, 2);
		
		grid.add(new Label("Stories:"), 2, 3);
		TextField t7 = new TextField();
		t7.setPromptText("1");
		t7.setAlignment(Pos.CENTER_RIGHT);
		grid.add(t7, 3, 3);

		grid.add(new Label("Condition:"), 2, 4);
		ObservableList<String> conditionList = FXCollections.observableArrayList("Excellent", "Good Shape", "Needs Fixing");
		ComboBox<String> c3 = new ComboBox<>(conditionList);
		c3.setMaxWidth(Double.MAX_VALUE);
		grid.add(c3, 3, 4);

		grid.add(new Label("Bathrooms:"), 2, 5);
		TextField t9 = new TextField();
		t9.setPromptText("0.00");
		t9.setAlignment(Pos.CENTER_RIGHT);
		grid.add(t9, 3, 5);

		grid.add(new Label("Sale Status:"), 2, 6);
		ObservableList<String> saleStatusList = FXCollections.observableArrayList("Unspecified", "Available", "Sold");
		ComboBox<String> c4 = new ComboBox<>(saleStatusList);
		c4.setMaxWidth(Double.MAX_VALUE);
		grid.add(c4, 3, 6);

		GridPane.setColumnSpan(t2, 3);

		t.getChildren().addAll(grid);
		
		return t;
	}

	public StackPane mid() {
		StackPane s = new StackPane();
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(5);

		Button pic = new Button("Picture Insert");
		pic.setPrefWidth(85);
		pic.setAlignment(Pos.TOP_LEFT);

		Rectangle r = new Rectangle();
		r.setFill(Color.TRANSPARENT);
		r.setStroke(Color.BLACK);
		r.setWidth(680);
		r.setHeight(500);

		grid.add(pic, 0, 0);
		grid.add(r, 1, 0);

		s.getChildren().add(grid);
		return s;
	}

	public StackPane bot() {
		StackPane s = new StackPane();
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		hbox.setSpacing(20);
		Button ok = new Button("OK");
		ok.setPrefWidth(70);
		ok.setOnAction(e -> {
				
			});
		
		Button cancel = new Button("Cancel");
		cancel.setPrefWidth(70);
		cancel.setCancelButton(true);
		cancel.setOnAction(e -> {
			// stg.close();
				//finish();
			});
		
		hbox.getChildren().addAll(ok, cancel);
		s.getChildren().add(hbox);
		return s;
	}
  
	// public static void main(String[] args) {launch(args);}
}
