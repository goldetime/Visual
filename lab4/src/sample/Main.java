package sample;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// --module-path /home/golde/Downloads/javafx-sdk-13/lib --add-modules javafx.controls,javafx.fxml
public class Main extends Application {
	Stage stage;
	BorderPane root;
	ObservableList<String> days;
	public Scene editing;
	TableView<Property> table;
	Stage dialog = null;

	private TextField tfPropertyNumber = null;
	private TextField tfAddress = null;
	private TextField tfCity = null;
	private TextField tfZipCode = null;
	private TextField tfYearBuilt = null;
	private TextField tfBedrooms = null;
	private TextField tfStories = null;
	private TextField tfBathrooms = null;
	private TextField tfMarketValue = null;

	private ComboBox<String> cbType = null;
	private ComboBox<String> cbState = null;
	private ComboBox<String> cbCondition = null;
	private ComboBox<String> cbSaleStatus = null;

	@Override
	public void start(Stage primaryStage) throws Exception{
		root = new BorderPane();
		root.setPadding(new Insets(10));
		
		TableView<Property> top = topSec();
		root.setTop(top);

		StackPane m = midd();
		root.setCenter(m);

		StackPane bot = botStackPane();
		root.setBottom(bot);

		stage = primaryStage;
		Scene listing = new Scene(root, 800, 900);
		primaryStage.setTitle("Properties Listing");
		primaryStage.setScene(listing);
		primaryStage.show();
	}

	TableView<Property> topSec() {
		/* Create table */
		table = new TableView<Property>();
		
		// Editable
		table.setEditable(true);

		/* Create columns */
		TableColumn<Property, String> propertyNumberCol = new TableColumn<Property, String>("Property#");
//		propertyNumberCol.setPrefWidth(10);
		propertyNumberCol.setCellFactory(TextFieldTableCell.<Property> forTableColumn());
		propertyNumberCol.setCellValueFactory(new PropertyValueFactory("propertyNumber"));
		// On Cell edit commit (for FullName column)
		propertyNumberCol.setOnEditCommit((TableColumn.CellEditEvent<Property, String> event) -> {
				TablePosition<Property, String> pos = event.getTablePosition();
 				String s = event.getNewValue();
 				int row = pos.getRow();
				Property p = event.getTableView().getItems().get(row);
				p.setPropertyNumber(s);
			});
		
		TableColumn<Property, String> cityCol = new TableColumn<Property, String>("City");
		TableColumn<Property, Integer> storiesCol = new TableColumn<Property, Integer>("Stories");
		TableColumn<Property, Integer> yearCol = new TableColumn<Property, Integer>("Year");
		TableColumn<Property, Integer> bedsCol = new TableColumn<Property, Integer>("Beds");
		TableColumn<Property, Integer> bathsCol = new TableColumn<Property, Integer>("Baths");
		TableColumn<Property, String> conditionCol = new TableColumn<Property, String>("Condition");
		TableColumn<Property, String> saleStatusCol = new TableColumn<Property, String>("Sale status");
		TableColumn<Property, Double> marketValueCol = new TableColumn<Property, Double>("Market Value");
		
		// (Combine two column)
		// aCol.getColums().addAll(bCol, cCol);

		propertyNumberCol.setMinWidth(200);

		/* Defines how to fill data for each cell. */
		// Get value from property of Property.
		cityCol.setCellValueFactory(new PropertyValueFactory("city"));
		storiesCol.setCellValueFactory(new PropertyValueFactory("stories"));
		yearCol.setCellValueFactory(new PropertyValueFactory("yearBuilt"));
		bedsCol.setCellValueFactory(new PropertyValueFactory("bedrooms"));
		bathsCol.setCellValueFactory(new PropertyValueFactory("bathrooms"));
		conditionCol.setCellValueFactory(new PropertyValueFactory("condition"));
		saleStatusCol.setCellValueFactory(new PropertyValueFactory("saleStatus"));
		marketValueCol.setCellValueFactory(new PropertyValueFactory("marketValue"));

		/* Sort column */
		cityCol.setSortType(TableColumn.SortType.ASCENDING);
		// col.setSortable(false);

		/* Display row data */
		ObservableList<Property> list = getList();
		table.setItems(list);

		/* Add columns */
		table.getColumns().addAll(propertyNumberCol, cityCol, storiesCol,
															yearCol, bedsCol, bathsCol,
															conditionCol, saleStatusCol, marketValueCol);
		//table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
															
		return table;
	}

	private ObservableList<Property> getList() {
 
		Property p1 = new Property("1", "UB", 3, 1999, 2, 2);
		Property p2 = new Property("2", "Erdenet", 3, 1998, 2, 2);
		Property p3 = new Property("3", "Darkhan", 3, 1996, 2, 2);

//		ObservableList<Property> list = FXCollections.observableArrayList(p1, p2, p3);
		ObservableList<Property> list = FXCollections.observableArrayList();
		return list;
  }

	public StackPane midd() {
		StackPane s = new StackPane();
		Rectangle r = new Rectangle();
		r.setFill(Color.TRANSPARENT);
		r.setStroke(Color.BLACK);
		r.setWidth(780);
		r.setHeight(400);

		s.getChildren().add(r);
		return s;
	}


	StackPane botStackPane() {
		StackPane p = new StackPane();

		HBox hbox = new HBox();
		hbox.setSpacing(400);
		// t, l, b, r
		// hbox.setPadding(new Insets(0, 20, 0, 20));

		Button c = new Button("New Real Estate Property...");
		c.setOnAction(e -> {
			showNewRealEstatePropertyDialog(stage, table);
//				PropertyEditor pe = new PropertyEditor();
//				try {
//					pe.start(stage);
//				} catch (Exception ex) {
//					ex.printStackTrace();
//				}
			});
		
		Button close = new Button("Close");
		close.setPrefWidth(180);
		close.setOnAction(e -> {
				stage.close();
			});

		hbox.getChildren().addAll(c, close);
		p.getChildren().addAll(hbox);
		return p;
	}

	private void showNewRealEstatePropertyDialog(Stage parent, final TableView<Property> table) {
		dialog = new Stage();
		dialog.setTitle("Property Editor");
		dialog.initOwner(parent);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UTILITY);
		// dialog.setX(parent.getX() + parent.getWidth());
		// dialog.setY(y);

		BorderPane root = new BorderPane();
		StackPane t = top();
		root.setTop(t);

		StackPane m = mid();
		root.setCenter(m);

		StackPane b = bot();
		root.setBottom(b);

		dialog.setScene(new Scene(root));
		dialog.show();
	}

	private StackPane top() {
		StackPane t = new StackPane();
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));

		grid.add(new Label("Property Type:"), 0, 0);
		ObservableList<String> typeList =
				FXCollections.observableArrayList("Condominium", "Townhouse", "Single Family", "Unknown");
		cbType = new ComboBox<>(typeList);
		cbType.setMaxWidth(Double.MAX_VALUE);
		grid.add(cbType, 1, 0);

		grid.add(new Label("Property#:"), 2, 0);
		tfPropertyNumber = new TextField();
		grid.add(tfPropertyNumber, 3, 0);

		grid.add(new Label("Address:"), 0, 1);
		tfAddress = new TextField();
		grid.add(tfAddress, 1, 1);

		grid.add(new Label("City:"), 0, 2);
		tfCity = new TextField();
		grid.add(tfCity, 1, 2);

		grid.add(new Label("ZIP Code:"), 0, 3);
		tfZipCode = new TextField();
		grid.add(tfZipCode, 1, 3);

		grid.add(new Label("Year Built:"), 0, 4);
		tfYearBuilt = new TextField();
		grid.add(tfYearBuilt, 1, 4);

		grid.add(new Label("Bedrooms:"), 0, 5);
		tfBedrooms = new TextField();
		tfBedrooms.setPromptText("0");
		tfBedrooms.setAlignment(Pos.CENTER_RIGHT);
		grid.add(tfBedrooms, 1, 5);

		grid.add(new Label("Market Value:"), 0, 6);
		tfMarketValue = new TextField();
		tfMarketValue.setPromptText("0.00");
		tfMarketValue.setAlignment(Pos.CENTER_RIGHT);
		grid.add(tfMarketValue, 1, 6);

		grid.add(new Label("State:"), 2, 2);
		ObservableList<String> stateList =
				FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID",
						"IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
						"NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
						"SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
		cbState = new ComboBox<>(stateList);
		cbState.setMaxWidth(Double.MAX_VALUE);
		grid.add(cbState, 3, 2);

		grid.add(new Label("Stories:"), 2, 3);
		tfStories = new TextField();
		tfStories.setPromptText("1");
		tfStories.setAlignment(Pos.CENTER_RIGHT);
		grid.add(tfStories, 3, 3);

		grid.add(new Label("Condition:"), 2, 4);
		ObservableList<String> conditionList = FXCollections.observableArrayList("Excellent", "Good Shape", "Needs Fixing");
		cbCondition = new ComboBox<>(conditionList);
		cbCondition.setMaxWidth(Double.MAX_VALUE);
		grid.add(cbCondition, 3, 4);

		grid.add(new Label("Bathrooms:"), 2, 5);
		tfBathrooms = new TextField();
		tfBathrooms.setPromptText("0.00");
		tfBathrooms.setAlignment(Pos.CENTER_RIGHT);
		grid.add(tfBathrooms, 3, 5);

		grid.add(new Label("Sale Status:"), 2, 6);
		ObservableList<String> saleStatusList = FXCollections.observableArrayList("Unspecified", "Available", "Sold");
		cbSaleStatus = new ComboBox<>(saleStatusList);
		cbSaleStatus.setMaxWidth(Double.MAX_VALUE);
		grid.add(cbSaleStatus, 3, 6);

		GridPane.setColumnSpan(tfAddress, 3);
		t.getChildren().addAll(grid);

		return t;
	}

	private StackPane mid() {
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

	private StackPane bot() {
		StackPane s = new StackPane();
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		hbox.setSpacing(20);
		Button ok = new Button("OK");
		ok.setPrefWidth(70);
		ok.setOnAction(e -> {
//			int nextIndex = table.getSelectionModel().getSelectedIndex() + 1;
			Property tmp = new Property();
			tmp.setPropertyNumber(tfPropertyNumber.getText());
			tmp.setCity(tfCity.getText());
			tmp.setStories(Integer.valueOf(tfStories.getText()));
			tmp.setYearBuilt(Integer.valueOf(tfYearBuilt.getText()));
			tmp.setBedrooms(Integer.valueOf(tfBedrooms.getText()));
			tmp.setBathrooms(Integer.valueOf(tfBathrooms.getText()));
			tmp.setCondition(cbCondition.getValue());
			tmp.setSaleStatus(cbSaleStatus.getValue());
			tmp.setMarketValue(Double.valueOf(tfMarketValue.getText()));
			table.getItems().add(tmp);
//			table.getSelectionModel().select(nextIdx);
			dialog.close();
		});

		Button cancel = new Button("Cancel");
		cancel.setPrefWidth(70);
		cancel.setCancelButton(true);
		cancel.setOnAction(e -> {
			dialog.close(); // finish();
		});

		hbox.getChildren().addAll(ok, cancel);
		s.getChildren().add(hbox);
		return s;
	}

	public static void main(String[] args) {
		launch(args);
	}
}