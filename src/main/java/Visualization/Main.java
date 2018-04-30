package Visualization;

import Provider.EntityManagerProvider;
import javafx.application.Application;
;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    public static Stage stage;

    @Override
    public void init() {
        EntityManagerProvider.provideEntityManager();
    }

    @Override
    public void stop(){
        EntityManagerProvider.closeConnection();
    }

    public void start(Stage stage) throws Exception {
        Main.stage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));

        Scene scene = new Scene(root, 1024, 768);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
