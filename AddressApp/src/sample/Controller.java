package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.Person;

import java.io.IOException;

public class Controller {

  Model m = new Model();

  @FXML
  private TableView<Person> personTable;

  @FXML
  private TableColumn<Person, String> firstNameColumn;

  @FXML
  private TableColumn<Person, String> lastNameColumn;

  @FXML
  private Label firstNameLabel;

  @FXML
  private Label lastNameLabel;

  @FXML
  private Label streetLabel;

  @FXML
  private Label cityLabel;

  @FXML
  private Label postalCodeLabel;

  @FXML
  private Label birthdayLabel;

  private void showPersonDetails(Person person) {
    if (person != null) {
      // Fill the labels with info from the person object.
      firstNameLabel.setText(person.getFirstName());
      lastNameLabel.setText(person.getLastName());
      streetLabel.setText(person.getStreet());
      postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
      cityLabel.setText(person.getCity());
      birthdayLabel.setText(DateUtil.format(person.getBirthday()));
    } else {
      // Person is null, remove all the text.
      firstNameLabel.setText("");
      lastNameLabel.setText("");
      streetLabel.setText("");
      postalCodeLabel.setText("");
      cityLabel.setText("");
      birthdayLabel.setText("");
    }
  }

  @FXML
  void handleDeletePerson(ActionEvent event) {
    int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      personTable.getItems().remove(selectedIndex);
    } else {
      // Nothing selected.
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("No Selection");
      alert.setHeaderText("No Person Selected");
      alert.setContentText("Please select a person in the table.");
      alert.showAndWait();
    }
  }

  public boolean showPersonEditDialog(Person person) {
    try {
      // Load the fxml file and create a new stage for the popup dialog.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Controller.class.getResource("edit.fxml"));
      AnchorPane page = (AnchorPane) loader.load();

      // Create the dialog Stage.
      Stage dialogStage = new Stage();
      dialogStage.setTitle("Edit Person");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set the person into the controller.
      PersonEditDialogController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setPerson(person);

      // Show the dialog and wait until the user closes it
      dialogStage.showAndWait();

      return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
      return false;
    }
  }

  @FXML
  private void handleNewPerson() {
    Person tempPerson = new Person();
    boolean okClicked = showPersonEditDialog(tempPerson);
    if (okClicked) {
//            m.getPersonData().add(tempPerson);
    }
  }

  @FXML
  private void handleEditPerson() {
    Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
    if (selectedPerson != null) {
      boolean okClicked = showPersonEditDialog(selectedPerson);
      if (okClicked) {
        showPersonDetails(selectedPerson);
      }
    } else {
      // Nothing selected.
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("No Selection");
      alert.setHeaderText("No Person Selected");
      alert.setContentText("Please select a person in the table.");
      alert.showAndWait();
    }
  }

  @FXML
  private void initialize() {
    firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

    ObservableList<Person> list = m.getPersonData();
    personTable.setItems(list);

    personTable.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          showPersonDetails(newValue);
        });
  }
}