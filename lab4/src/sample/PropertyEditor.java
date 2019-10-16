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
import javafx.stage.Stage;


public class PropertyEditor extends Application {
  Stage stage;
  BorderPane root;
  ObservableList<String> days;

  @Override
  public void start(Stage primaryStage) throws Exception{
    root = new BorderPane();
    root.setPadding(new Insets(10));

    TableView<Property> top = topSec();
    root.setTop(top);

    StackPane s = new StackPane();
    root.setCenter(s);

    StackPane bot = botStackPane();
    root.setBottom(bot);

    stage = primaryStage;
    primaryStage.setTitle("Properties Listing");
    primaryStage.setScene(new Scene(root, 800, 900));
    primaryStage.show();
  }

  TableView<Property> topSec() {
    /* Create table */
    TableView<Property> table = new TableView<Property>();

    // Editable
    table.setEditable(true);

    /* Create columns */
    TableColumn<Property, String> propertyNumberCol = new TableColumn<Property, String>("Property#");
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

    return table;
  }

  private ObservableList<Property> getList() {

    Property p1 = new Property("1", "UB", 3, 1999, 2, 2);
    Property p2 = new Property("2", "Erdenet", 3, 1998, 2, 2);
    Property p3 = new Property("3", "Darkhan", 3, 1996, 2, 2);

    ObservableList<Property> list = FXCollections.observableArrayList(p1, p2, p3);
    return list;
  }

  StackPane botStackPane() {
    StackPane p = new StackPane();

    HBox hbox = new HBox();
    hbox.setSpacing(400);
    // t, l, b, r
    // hbox.setPadding(new Insets(0, 20, 0, 20));

    Button c = new Button("New Real Estate Property...");
    c.setOnAction(e -> {

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

  public static void main(String[] args) {
    launch(args);
  }
}
