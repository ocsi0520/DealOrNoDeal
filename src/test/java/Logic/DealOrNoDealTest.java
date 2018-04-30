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
        int bagNumber=new Random(seed).nextInt(22);

        dealOrNoDeal.openBag(bagNumber);
        assertNull(dealOrNoDeal.openBag(bagNumber));
    }

    @Test
    public void openBagShouldNotYetOpenedBagReturnNotNull() {
        long seed=System.nanoTime();
        int bagNumber=new Random(seed).nextInt(22)+1;

        System.out.println(bagNumber);
        assertNotNull(dealOrNoDeal.openBag(bagNumber));
    }

    @Test
    public void isOfferNeededShouldBeTrueAtCertainOpens() {
        long seed=System.nanoTime();
        Random random=new Random(seed);
        List<Integer> offersAtOpenedBags=Arrays.asList(5,8,11,14,16,18,20);

        for(int testCase=0;testCase<100;testCase++){
            int offerAt = offersAtOpenedBags
                    .get(random
                            .nextInt(offersAtOpenedBags
                                    .size()));

            for(int bagNumberAndOpenedBags=1;bagNumberAndOpenedBags<= offerAt; bagNumberAndOpenedBags++)
            {
                dealOrNoDeal.openBag(bagNumberAndOpenedBags);
                boolean isOfferRound=offersAtOpenedBags.contains(bagNumberAndOpenedBags);
                assertEquals(isOfferRound,dealOrNoDeal.isOfferNeeded());
            }

            dealOrNoDeal=new DealOrNoDeal(this.gameService,this.bagService,"Test");
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
                dealOrNoDeal.acceptOffer();
                assertEquals(true,dealOrNoDeal.isOfferAccepted());
            }

            dealOrNoDeal=new DealOrNoDeal(this.gameService,this.bagService,"Test");
        }
    }

    @Test
    public void divideBagsToTwoGroupsShouldSizesEqual() {
        // Given
        List<Integer> divideShouldBeDone=Arrays.asList(22-6,22-4,22-2);
        // When
        for(int testCase=0;testCase<3;testCase++){
            for(int bagNumber=1;bagNumber<=divideShouldBeDone.get(testCase);bagNumber++){
                dealOrNoDeal.openBag(bagNumber);
            }

            List<List<String>> twoGroups=dealOrNoDeal.divideBagsToTwoGroups();
            List<String> smallerBagsShowableAmmounts=twoGroups.get(0);
            List<String> largerBagsShowableAmmounts=twoGroups.get(1);
            // Than
            assertEquals(smallerBagsShowableAmmounts.size(),largerBagsShowableAmmounts.size());

            dealOrNoDeal=new DealOrNoDeal(this.gameService,this.bagService,"Test");
        }

    }

    @Test
    public void divideBagsToTwoGroupsShouldBeDistinct() {
        // Given
        List<Integer> divideShouldBeDone=Arrays.asList(22-6,22-4,22-2);
        // When
        for(int testCase=0;testCase<3;testCase++){
            for(int bagNumber=1;bagNumber<=divideShouldBeDone.get(testCase);bagNumber++){
                dealOrNoDeal.openBag(bagNumber);
            }

            List<List<String>> twoGroups=dealOrNoDeal.divideBagsToTwoGroups();
            List<String> smallerBagsShowableAmmounts=twoGroups.get(0);
            List<String> largerBagsShowableAmmounts=twoGroups.get(1);
            // Than
            assertEquals(smallerBagsShowableAmmounts.stream().count(),smallerBagsShowableAmmounts.stream().distinct().count());
            assertEquals(largerBagsShowableAmmounts.stream().count(),largerBagsShowableAmmounts.stream().distinct().count());

            dealOrNoDeal=new DealOrNoDeal(this.gameService,this.bagService,"Test");
        }

    }

    @Test
    public void divideBagsToTwoGroupsShouldBeDisjoint() {
        // Given
        List<Integer> divideShouldBeDone=Arrays.asList(22-6,22-4,22-2);
        // When
        for(int testCase=0;testCase<3;testCase++){
            for(int bagNumber=1;bagNumber<=divideShouldBeDone.get(testCase);bagNumber++){
                dealOrNoDeal.openBag(bagNumber);
            }

            List<List<String>> twoGroups=dealOrNoDeal.divideBagsToTwoGroups();
            List<String> smallerBagsShowableAmmounts=twoGroups.get(0);
            List<String> largerBagsShowableAmmounts=twoGroups.get(1);
            // Than
            assertFalse(smallerBagsShowableAmmounts.stream().anyMatch(smallAmmount -> largerBagsShowableAmmounts.contains(smallAmmount)));

            dealOrNoDeal=new DealOrNoDeal(this.gameService,this.bagService,"Test");
        }

    }
    /*
    @AfterClass
    public void deleteAllNewlyMadeRecords(){

    }*/
}