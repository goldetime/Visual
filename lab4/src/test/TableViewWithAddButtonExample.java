package test;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.util.Callback;

public class TableViewWithAddButtonExample extends Application {
	Stage dialog = null;
  public static void main(String[] args) { launch(args); }
  @Override public void start(final Stage stage) {
    stage.setTitle("People");
    stage.getIcons().add(new Image("http://icons.iconarchive.com/icons/icons-land/vista-people/72/Historical-Viking-Female-icon.png"));  // icon license: Linkware (Backlink to http://www.icons-land.com required)

    // create a table.
    final TableView<Person> table = new TableView<>(
      FXCollections.observableArrayList(
        new Person("Jacob", "Smith"),
        new Person("Isabella", "Johnson"),
        new Person("Ethan", "Williams"),
        new Person("Emma", "Jones"),
        new Person("Michael", "Brown")
				)
			);

    // define the table columns.
    TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
    firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
    TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
    lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
    TableColumn<Person, Boolean> actionCol = new TableColumn<>("Action");
    actionCol.setSortable(false);

    // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
    actionCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, Boolean>, ObservableValue<Boolean>>() {
				@Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Person, Boolean> features) {
					return new SimpleBooleanProperty(features.getValue() != null);
				}
			});

    // create a cell value factory with an add button for each row in the table.
    actionCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {
				@Override public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> personBooleanTableColumn) {
					return new AddPersonCell(stage, table);
				}
			});

    table.getColumns().setAll(firstNameCol, lastNameCol, actionCol);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    stage.setScene(new Scene(table));
    stage.show();
  }

  /** A table cell containing a button for adding a new person. */
  private class AddPersonCell extends TableCell<Person, Boolean> {
    // a button for adding a new person.
    final Button addButton       = new Button("Add");
    // pads and centers the add button in the cell.
    final StackPane paddedButton = new StackPane();
    // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
    final DoubleProperty buttonY = new SimpleDoubleProperty();

    /**
     * AddPersonCell constructor
     * @param stage the stage in which the table is placed.
     * @param table the table to which a new person can be added.
     */
    AddPersonCell(final Stage stage, final TableView table) {
      paddedButton.setPadding(new Insets(3));
      paddedButton.getChildren().add(addButton);
      addButton.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent mouseEvent) {
						buttonY.set(mouseEvent.getScreenY());
					}
				});
      addButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent actionEvent) {
						//showAddPersonDialog(stage, table, buttonY.get());
						showNewRealEstatePropertyDialog(stage, table);
						table.getSelectionModel().select(getTableRow().getIndex());
					}
				});
    }

    /** places an add button in the row only if the row is not empty. */
    @Override protected void updateItem(Boolean item, boolean empty) {
      super.updateItem(item, empty);
      if (!empty) {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(paddedButton);
      } else {
        setGraphic(null);
      }
    }
  }

  /**
   * shows a dialog which displays a UI for adding a person to a table.
   * @param parent a parent stage to which this dialog will be modal and placed next to.
   * @param table the table to which a person is to be added.
   * @param y the y position of the top left corner of the dialog.
   */
  private void showAddPersonDialog(Stage parent, final TableView<Person> table, double y) {
    // initialize the dialog.
    final Stage dialog = new Stage();
    dialog.setTitle("New Person");
    dialog.initOwner(parent);
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initStyle(StageStyle.UTILITY);
    dialog.setX(parent.getX() + parent.getWidth());
    dialog.setY(y);

    // create a grid for the data entry.
    GridPane grid = new GridPane();
    final TextField firstNameField = new TextField();
    final TextField lastNameField = new TextField();
    grid.addRow(0, new Label("First Name"), firstNameField);
    grid.addRow(1, new Label("Last Name"), lastNameField);
    grid.setHgap(10);
    grid.setVgap(10);
    GridPane.setHgrow(firstNameField, Priority.ALWAYS);
    GridPane.setHgrow(lastNameField, Priority.ALWAYS);

    // create action buttons for the dialog.
    Button ok = new Button("OK");
    ok.setDefaultButton(true);
    Button cancel = new Button("Cancel");
    cancel.setCancelButton(true);

    // only enable the ok button when there has been some text entered.
    ok.disableProperty().bind(firstNameField.textProperty().isEqualTo("").or(lastNameField.textProperty().isEqualTo("")));

    // add action handlers for the dialog buttons.
    ok.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent actionEvent) {
					int nextIndex = table.getSelectionModel().getSelectedIndex() + 1;
					table.getItems().add(nextIndex, new Person(firstNameField.getText(), lastNameField.getText()));
					table.getSelectionModel().select(nextIndex);
					dialog.close();
				}
			});
		cancel.setOnAction(e -> {
			dialog.close();
		});

    // layout the dialog.
    //HBox buttons = HBoxBuilder.create().spacing(10).children(ok, cancel).alignment(Pos.CENTER_RIGHT).build();
		HBox buttons = new HBox();
		buttons.getChildren().addAll(ok, cancel);
		buttons.setSpacing(10);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		
    VBox layout = new VBox(10);
    layout.getChildren().addAll(grid, buttons);
    layout.setPadding(new Insets(5));
    dialog.setScene(new Scene(layout));
    dialog.show();
  }

  private void showNewRealEstatePropertyDialog(Stage parent, final TableView<Person> table) {
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
//			table.getItems().add(nextIndex, new Person(firstNameField.getText(), lastNameField.getText()));
//			table.getSelectionModel().select(nextIndex);
//			dialog.close();
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
}