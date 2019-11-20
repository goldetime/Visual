package dental;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// --module-path /home/golde/Downloads/javafx-sdk-13/lib --add-modules javafx.controls,javafx.fxml
// --module-path "C:\Users\ochko\Downloads\openjfx-13_windows-x64_bin-sdk\javafx-sdk-13\lib" --add-modules javafx.controls,javafx.fxml
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {

    Pane root = new Pane();

    primaryStage.setTitle("Dental");
    primaryStage.setScene(new Scene(root, 450, 300));
    primaryStage.show();
  }

  public static void main(String[] args) {launch(args);}
}