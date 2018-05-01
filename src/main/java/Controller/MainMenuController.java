package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    public void newGameClicked(MouseEvent event) throws IOException {
        /*GameService g = new GameService(new GameDao(EntityManagerProvider.provideEntityManager()));
        BagService b = new BagService(new BagDao(EntityManagerProvider.provideEntityManager()));
        b.getBagsAtStart(g.createNewGame("Viktor"),true);*/


        Parent root = FXMLLoader.load(getClass().getResource("/Studio.fxml"));

        Scene scene = new Scene(root, 1024, 768);

        Main.stage.setTitle("sup?");
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    public void loadGameClicked(MouseEvent event){

    }
}
