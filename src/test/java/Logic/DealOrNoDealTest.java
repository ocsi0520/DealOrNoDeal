package Logic;

import DAO.BagDao;
import DAO.GameDao;
import Logic.BagService;
import Logic.DealOrNoDeal;
import Logic.GameService;
import Model.Game;
import Provider.EntityManagerProvider;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DealOrNoDealTest {

    DealOrNoDeal dealOrNoDeal;
    GameService gameService;
    BagService bagService;

    @Mock
    private GameDao gameDao;
    @Mock
    private BagDao bagDao;
    /*@BeforeClass
    private void createServices(){

    }
    */
    @Before
    public void setUp() throws Exception {
        bagService=new BagService(bagDao);
        gameService=new GameService(gameDao);
        Game game = new Game();

        doNothing().when(gameDao).createGame(game);

        dealOrNoDeal=new DealOrNoDeal(gameService,bagService,"Test");
    }

    @Test
    public void openBagShouldAlreadyOpenedBagReturnNull() {
        long seed=System.nanoTime();
        int bagIndex=new Random(seed).nextInt(21); //21-edik indexet nem szabad adni, mivel azt az elején nem lehet kinyitni
        int bagNumber=dealOrNoDeal.getBags().get(bagIndex).getBagNumber();

        dealOrNoDeal.openBag(bagNumber);
        assertNull(dealOrNoDeal.openBag(bagNumber));
    }

    @Test
    public void openBagShouldNotYetOpenedBagReturnNotNull() {
        long seed=System.nanoTime();
        int bagIndex=new Random(seed).nextInt(21); //21-edik indexet nem szabad adni, mivel azt az elején nem lehet kinyitni
        int bagNumber=dealOrNoDeal.getBags().get(bagIndex).getBagNumber();

        assertNotNull(dealOrNoDeal.openBag(bagNumber));
    }

    @Test
    public void isOfferNeededShouldBeTrueAtCertainOpens() {
        long seed=System.nanoTime();
        Random random=new Random(seed);
        List<Integer> offersAtOpenedBags=Arrays.asList(5,8,11,14,16,18,20);
        for(int bagIndex=0;bagIndex<21;bagIndex++){
            int bagNumber=dealOrNoDeal.getBags().get(bagIndex).getBagNumber();
            dealOrNoDeal.openBag(bagNumber);
            boolean isOfferRound=offersAtOpenedBags.contains(bagIndex+1);
            assertEquals(isOfferRound,dealOrNoDeal.isOfferNeeded());
        }
    }

    @Test
    public void isOfferAcceptedShouldBeChangedOnlyWhenOfferAccepted() {
        long seed=System.nanoTime();
        Random random=new Random(seed);
        List<Integer> offersAtOpenedBags=Arrays.asList(5,8,11,14,16,18,20);

        for(int testCase=0;testCase<100;testCase++){
            int offerAt = offersAtOpenedBags
                    .get(random
                            .nextInt(offersAtOpenedBags
                                    .size()));

            for(int bagNumber=1;bagNumber <= offerAt; bagNumber++) {
                dealOrNoDeal.openBag(bagNumber);
                assertEquals(false,dealOrNoDeal.isOfferAccepted());
            }
            if(dealOrNoDeal.isOfferNeeded()) {
                dealOrNoDeal.makeOffer();
                dealOrNoDeal.acceptOffer();
                assertEquals(true,dealOrNoDeal.isOfferAccepted());
            }

            dealOrNoDeal=new DealOrNoDeal(this.gameService,this.bagService,"Test");
        }
    }
}