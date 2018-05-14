package Controller;

import Logic.DealOrNoDeal;
import Model.Bag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.View;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

//https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
public class StudioController {

    ViewHelper viewHelper;
    DealOrNoDeal dealOrNoDeal;
    private Logger logger=LoggerFactory.getLogger(getClass());

    @FXML
    private AnchorPane anchorPane;

    public StudioController(){
        dealOrNoDeal=ApplicationContext.getInstance().getDealOrNoDeal();
        viewHelper = new ViewHelper(dealOrNoDeal.getBags());
    }

    @FXML
    public void initialize() {
         for(int i = 0; i < dealOrNoDeal.getBags().size(); ++i)
             update(i);
    }

    public void imageClicked(MouseEvent event){
        StackPane selectedControl=((StackPane)event.getSource());

        int bagIndex = Integer.parseInt(selectedControl.getId());
        int bagNumber = dealOrNoDeal.getBags().get(bagIndex).getBagNumber();

        if(dealOrNoDeal.openBag(bagNumber)==null)
            return;

        update(selectedControl);

        if(dealOrNoDeal.isOfferNeeded()){ //Ha kell adni ajánlatot
            long offer = dealOrNoDeal.makeOffer();
            String formattedOffer = NumberFormat.getInstance(new Locale("hu_HU")).format(offer);
            if(!dealOrNoDeal.isOfferAccepted()) { //és még nem fogadott el egyet sem, akkor elfogadhatja
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Banktól új ajánlat jött");
                alert.setHeaderText("A bank ajánlata " + formattedOffer + " ft.");
                alert.setContentText("Elfogadod az ajánlatot?");

                ButtonType buttonTypeYes = new ButtonType("Igen");
                ButtonType buttonTypeNo = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes) { //Ha igent nyomott
                    dealOrNoDeal.acceptOffer();
                }
            }
            else{ //Ha már fogadott el előtte ajánlatot, akkor csak szemléltetésül írjuk ki
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Banktól új ajánlat jött");
                alert.setHeaderText("A bank ajánlata " + formattedOffer + " ft lett volna.");
                //alert.setContentText("I have a great message for you!");
                alert.showAndWait();
            }
        }
        else if(dealOrNoDeal.isFinished()){ //Ha ez volt az utolsó táska
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gratulálunk");
            alert.setHeaderText("A nyereményed " + dealOrNoDeal.getGamePrize() + " ft.");
            //alert.setContentText("I have a great message for you!");
            alert.showAndWait();
            goToMain();
        }

        //Text text= (Text) ((StackPane)event.getSource()).getChildren().get(1);
        //text.setText("aaaaaaa");
    }

    private void goToMain(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
            Scene scene = new Scene(root, 1024, 768);
            Main.stage.setTitle("Főmenü");
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            logger.error("Hiba a main.fxml betöltésénél",e);
            throw new RuntimeException(e);
        }
    }

    private void update(int bagIdx) {
        // Find bag node
        StackPane bagNode = (StackPane) anchorPane.lookup("#" + Integer.toString(bagIdx));

        // Update bag node
        update(bagNode);
    }

    private void update(StackPane bagNode) {
        int index=Integer.parseInt(bagNode.getId());
        Bag bag=dealOrNoDeal.getBags().get(index);
        if(!bag.isOpen()){
            //szöveg
            Text text = (Text) bagNode.lookup("Text");
            text.setText(String.valueOf(bag.getBagNumber()));
            //text.setText(String.valueOf(bag.getShowableAmmount()));

            //kép
            ImageView imageView= (ImageView) bagNode.lookup("ImageView");
            imageView.setImage(new Image(getClass().getResource("/malette_small.png").toString()));
            //imageView.setImage(new Image("@malette_small.png"));
        }
        else {
            //szöveg
            Text text = (Text) bagNode.lookup("Text");
            text.setText(bag.getShowableAmmount());

            //kép
            ImageView imageView= (ImageView) bagNode.lookup("ImageView");
            imageView.setImage(new Image(getClass().getResource("/opened_bag_picture.png").toString()));
            //imageView.setImage(new Image("@try_background.jpg"));
        }
    }
}
