package leo.app;

import java.io.IOException;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	static Stage primaryStage;
    private BorderPane rootLayout;
    private String title="Morphing";
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle(title);
        initRootLayout();
        //show();
	}

	public static void main(String[] args) {
		System.out.println(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_java2413");
		launch(args);
	}
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Shows the person overview inside the root layout.
     */
    public void show() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/show.fxml"));
            AnchorPane show = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(show);
           // rootLayout.setAlignment(show, Pos.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns the main stage.
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}
