package midterm;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
  Stage stage;
  SplitPane root;
  TableView<Country> table;
  int j = 1;

  @Override
  public void start(Stage primaryStage) throws Exception {
    VBox main = new VBox();

    HBox header = new HBox();
    Label t = new Label("Countries");

    header.setPadding(new Insets(20));
    header.getChildren().add(t);

    root = new SplitPane();
    root.setPadding(new Insets(10));

    root.setOrientation(Orientation.HORIZONTAL);

    TreeView l = left();
    TableView<Country> top = right();
    //root.setDividerPosition(1, 500);
    root.getItems().addAll(l, top);

    main.getChildren().addAll(header, root);

    stage = primaryStage;
    Scene scene = new Scene(main, 1000, 500);
    primaryStage.setTitle("Countries Statistic");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  TreeView left() {
    Image image;
    Node WORLD = null;
    try {
      image = new Image("file:worldwide.png");
      WORLD = new ImageView(image);
    } catch (Exception e) {
      System.out.println("no image");
    }
    midterm.TreeViewHelper helper = new midterm.TreeViewHelper();
    ArrayList<TreeItem> products = helper.getProducts();

    products.stream().forEach(i -> {
      try {
        String s = "file:" + String.valueOf(j) + ".png";
        j++;
        Node t = new ImageView(s);
        i.setGraphic(t);
      } catch (Exception ex) {
        System.out.println("no image");
      }
    });

    TreeItem rootItem = new TreeItem("World");
    //rootItem.setExpanded(true);
    rootItem.setGraphic(WORLD);
    rootItem.getChildren().addAll(products);

    TreeView treeView = new TreeView();
    treeView.setRoot(rootItem);


    treeView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem<String>>) c -> {
      //con = FXCollections.observableArrayList();
      if (c.next()) {
        try {
          ObservableList<Country>  con = helper.getCountries(c.getAddedSubList().get(0).getValue());
          table.setItems(con);
        } catch (Exception ex) {
          System.out.println("No country");
        }
        System.out.println("selected: " + c.getAddedSubList().get(0).getValue());
      }
    });
    return treeView;
  }

  TableView<Country> right() {
    /* Create table */
    table = new TableView<Country>();
    // Editable
    table.setEditable(true);

    /* Create columns */
    TableColumn<Country, String> nameCol = new TableColumn<Country, String>("Name");
    TableColumn<Country, Double> areaCol = new TableColumn<Country, Double>("Area (sq km)");
    TableColumn<Country, Integer> populationCol = new TableColumn<Country, Integer>("Population");
    TableColumn<Country, String> capitalCol = new TableColumn<Country, String>("Capital");
    TableColumn<Country, String> codeCol = new TableColumn<Country, String>("Code");

    nameCol.setCellValueFactory(new PropertyValueFactory("name"));
    areaCol.setCellValueFactory(new PropertyValueFactory("area"));
    areaCol.setMinWidth(200);
    populationCol.setCellValueFactory(new PropertyValueFactory("population"));
    populationCol.setMinWidth(200);
    capitalCol.setCellValueFactory(new PropertyValueFactory("capital"));
    codeCol.setCellValueFactory(new PropertyValueFactory("code"));;

    /* Display row data */
    ObservableList<Country> list = getList();

    /* Add columns */
    table.getColumns().addAll(nameCol, areaCol, populationCol, capitalCol, codeCol);
    //table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    return table;
  }

  // test add
  private ObservableList<Country> getList() {
    Country p1 = new Country();
    Country p2 = new Country();
    Country p3 = new Country();

		ObservableList<Country> list = FXCollections.observableArrayList(p1, p2, p3);
//    ObservableList<Country> list = FXCollections.observableArrayList();
    return list;
  }

  public static void main(String[] args) {
    launch(args);
  }
}