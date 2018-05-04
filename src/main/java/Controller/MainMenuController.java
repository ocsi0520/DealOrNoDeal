package Controller;

import DAO.BagDao;
import DAO.GameDao;
import Logic.BagService;
import Logic.DealOrNoDeal;
import Logic.GameService;
import Model.Game;
import Provider.EntityManagerProvider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainMenuController {
    private Logger logger=LoggerFactory.getLogger(getClass());
    public void newGameClicked(MouseEvent event) throws IOException {
        TextInputDialog dialog = new TextInputDialog("Player");
        dialog.setTitle("Játékos neve");
        dialog.setHeaderText("Játékos neve");
        dialog.setContentText("Add meg a neved:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            ApplicationContext.getInstance().setDealOrNoDeal(new DealOrNoDeal(new GameService(new GameDao(EntityManagerProvider.provideEntityManager())), new BagService(new BagDao(EntityManagerProvider.provideEntityManager())),result.get()));
            goToGame();
        }
    }

    public void loadGameClicked(MouseEvent event) throws IOException {
        GameService gameService=new GameService(new GameDao(EntityManagerProvider.provideEntityManager()));
        List<Game> choices = gameService.getAllNotFinishedGames();

        ChoiceDialog<Game> dialog = new ChoiceDialog<Game>(null,choices);
        dialog.setTitle("Elérhető játékok");
        dialog.setHeaderText("Elérhető játékok");
        dialog.setContentText("Válassz egy játékot");

        Optional<Game> result = dialog.showAndWait();
        if (result.isPresent()){
            DealOrNoDeal dealOrNoDeal=new DealOrNoDeal(gameService,new BagService(new BagDao(EntityManagerProvider.provideEntityManager())),result.get().getId());
            ApplicationContext.getInstance().setDealOrNoDeal(dealOrNoDeal);

            goToGame();
        }
    }

    private void goToGame() throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Studio.fxml"));

            Scene scene = new Scene(root, 1024, 768);

            Main.stage.setTitle("Stúdió");
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            logger.error("Hiba a main.fxml betöltésénél",e);
            throw new RuntimeException(e);
        }
    }
}
