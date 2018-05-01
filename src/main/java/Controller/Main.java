package Controller;

import Provider.EntityManagerProvider;
import javafx.application.Application;
;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    private Logger logger= LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) { launch(args); }

    public static Stage stage;

    @Override
    public void init() {
        EntityManagerProvider.provideEntityManager();
        logger.debug("EntityManager has been initialized");
    }

    @Override
    public void stop(){
        EntityManagerProvider.closeConnection();
        logger.debug("EntityManager has closed connection");
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
