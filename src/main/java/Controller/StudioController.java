package Controller;

import Logic.DealOrNoDeal;
import Model.Bag;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import javax.swing.text.View;
import java.util.Optional;

//https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
public class StudioController {

    ViewHelper viewHelper;
    DealOrNoDeal dealOrNoDeal;

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
        dealOrNoDeal.openBag(bagNumber);

        update(selectedControl);

        if(dealOrNoDeal.isOfferNeeded()){
            long offer = dealOrNoDeal.makeOffer();
            if(!dealOrNoDeal.isOfferAccepted()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Banktól új ajánlat jött");
                alert.setHeaderText("A bank ajánlata " + offer + " ft.");
                alert.setContentText("Elfogadod az ajánlatot?");

                ButtonType buttonTypeYes = new ButtonType("Igen");
                ButtonType buttonTypeNo = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes) {
                    dealOrNoDeal.acceptOffer();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Banktól új ajánlat jött");
                alert.setHeaderText("A bank ajánlata " + offer + " ft lett volna.");
                //alert.setContentText("I have a great message for you!");
                alert.showAndWait();
            }
        }

        //Text text= (Text) ((StackPane)event.getSource()).getChildren().get(1);
        //text.setText("aaaaaaa");
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
            imageView.setImage(new Image(getClass().getResource("/bag_picture2.png").toString()));
            //imageView.setImage(new Image("@try_background.jpg"));
        }
    }
}
