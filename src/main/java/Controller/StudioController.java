package Controller;

import Logic.DealOrNoDeal;
import Model.Bag;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import javax.swing.text.View;

//https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
public class StudioController {

    ViewHelper viewHelper;
    DealOrNoDeal dealOrNoDeal;
    public StudioController(){
        dealOrNoDeal=ApplicationContext.getInstance().getDealOrNoDeal();
        viewHelper = new ViewHelper(dealOrNoDeal.getBags());
    }

    @FXML
    public void initialize() {

    }

    public void imageClicked(MouseEvent event){
        StackPane selectedControl=((StackPane)event.getSource());
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

    private void update(int bagIdx, Bag bagData) {
        // Find bag node
        StackPane bagNode = null;
        // ...

        // Update bag node
        update(bagNode);
    }

    private void update(StackPane bagNode) {
        /*int index=Integer.parseInt(bagNode.getId());
        Bag bag=dealOrNoDeal.getBags().get(index);
        if(bag.isOpen()){
            //szöveg
            //kép
            ImageView imageView= (ImageView) bagNode.getChildren().get(0);
            //imageView.set
        }
*/
    }
}
