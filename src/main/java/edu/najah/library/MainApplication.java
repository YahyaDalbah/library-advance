package edu.najah.library;

import edu.najah.library.utils.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("AllbooksPage.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library7");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        boolean status = HibernateUtil.getInstance().isConnected();
        System.out.println("Database Connected: " + status);
        launch();
    }
}
