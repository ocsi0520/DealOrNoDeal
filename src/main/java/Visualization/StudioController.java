package Visualization;

import DAO.BagDao;
import Logic.BagService;
import Logic.DealOrNoDeal;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

//https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
public class StudioController {

    ViewHelper viewHelper;
    DealOrNoDeal dealOrNoDeal;
    public StudioController(){
        /*this.dealOrNoDeal=new DealOrNoDeal(
                new BagService(
                        new BagDao(
                                Provider.EntityManagerProvider.provideEntityManager()
                        )
                )
        );
        this.viewHelper=new ViewHelper(this.dealOrNoDeal.getBags());*/
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
        Text text= (Text) ((StackPane)event.getSource()).getChildren().get(1);
        text.setText("");


    }
}
