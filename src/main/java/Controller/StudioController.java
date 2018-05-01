package Controller;

import Logic.DealOrNoDeal;
import Model.Bag;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import javax.swing.text.View;

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

        //Text text= (Text) ((StackPane)event.getSource()).getChildren().get(1);
        //text.setText("aaaaaaa");
    }
    public void proba(MouseEvent event){
        /*
        //Button clickedButton=(Button)action.getSource();
        System.out.println("eddig jó" + event.getClickCount());
        ImageView clickedImage= ((ImageView) event.getSource());
        int Id=Integer.parseInt(clickedImage.getId());

        System.out.println("Eddig:" + this.dealOrNoDeal.getBags().get(Id).isOpen());
        this.dealOrNoDeal.openBag(this.dealOrNoDeal.getBags().get(Id).getBagNumber());
        clickedImage.setDisable(true);
        System.out.println("Mostmár: " + this.dealOrNoDeal.getBags().get(Id).isOpen());
        */

        StackPane selectedControl=((StackPane)event.getSource());
        update(selectedControl);

        //Text text= (Text) ((StackPane)event.getSource()).getChildren().get(1);
        //text.setText("");
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
            text.setText("updated!");

            //kép
            ImageView imageView= (ImageView) bagNode.lookup("ImageView");
            //imageView.setImage(new Image("@malette_small.png"));
        }
        else {
            Text text = (Text) bagNode.lookup("Text");
            text.setText("updated twice biatch!");

            //kép
            ImageView imageView= (ImageView) bagNode.lookup("ImageView");
            //imageView.setImage(new Image("@try_background.jpg"));
        }
    }
}
