package material;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;

// --module-path /home/golde/Downloads/javafx-sdk-13/lib --add-modules javafx.controls,javafx.fxml
public class Main extends Application {

	private static final int WIDTH = 1400;
  private static final int HEIGHT = 800;
 
  @Override
  public void start(Stage primaryStage) {
    Sphere sphere = new Sphere(100);

    PhongMaterial material1 = new PhongMaterial();
    material1.setBumpMap(new Image("file:map.jpg"));
    sphere.setMaterial(material1);
 
    Group group = new Group();
    group.getChildren().add(sphere);

    Camera camera = new PerspectiveCamera();
    Scene scene = new Scene(group, WIDTH, HEIGHT);
    scene.setFill(Color.SILVER);
    scene.setCamera(camera);
 
    sphere.translateXProperty().set(WIDTH / 2);
    sphere.translateYProperty().set(HEIGHT / 2);

    primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
				switch (event.getCode()) {
        case W:
          sphere.translateZProperty().set(sphere.getTranslateZ() + 100);
          break;
        case S:
          sphere.translateZProperty().set(sphere.getTranslateZ() - 100);
          break;
				}
			});
 
		primaryStage.setTitle("World");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
 
  public static void main(String[] args) {
    launch(args);
  }
}