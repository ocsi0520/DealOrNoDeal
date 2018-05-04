package Logic;

import DAO.BagDao;
import Model.Bag;
import Model.Game;

import Provider.EntityManagerProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BagTest {
    private final static int BAGS_EXPECTED_SIZE = 22;

    private List<Bag> bags;
    private BagService bagService;
    private Game game;

    @Mock
    private BagDao MockBagDao;

    @Before
    public void setUp() throws Exception {
        this.game=new Game();
        this.bagService=new BagService(MockBagDao);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generateBagsShouldGenerateValuesInRange() {
        // Given
        //Bag b=new Bag();
        //doNothing().when(MockBagDao).createBag(b);

        // When
        List<Bag> bags=bagService.getBagsAtStart(game,true);
        // Then
        assertTrue(
                bags.stream()
                        .map(Bag::getAmmount)
                        .allMatch(value -> value >= 1000L && value <= 50_000_000L));
    }

    @Test
    public void generateBagsBagNumbersShouldBeDistinct(){
        // Given
        //Bag b=new Bag();
        //doNothing().when(MockBagDao).createBag(b);
        // When
        List<Bag> bags=bagService.getBagsAtStart(game,true);

        // Then
        assertEquals(
                bags.stream().map(bag->bag.getBagNumber()).count(),
                bags.stream().map(bag->bag.getBagNumber()).distinct().count()
        );

    }

    @Test
    public void generateBagsShowableAmmountsShouldBeDistinct(){
        // Given
        //Bag b=new Bag();
        //doNothing().when(MockBagDao).createBag(b);
        // When
        List<Bag> bags=bagService.getBagsAtStart(game,true);

        // Then
        assertEquals(
                bags.stream().map(bag->bag.getShowableAmmount()).count(),
                bags.stream().map(bag->bag.getShowableAmmount()).distinct().count()
        );
    }

    @Test
    public void generateBagsShouldSizeBeTwentyTwo(){ //Effective java book
        // Given
        //Bag b=new Bag();
        //doNothing().when(MockBagDao).createBag(b);
        // When
        List<Bag> bags=bagService.getBagsAtStart(game,true);

        // Then
        assertEquals(BAGS_EXPECTED_SIZE ,bags.size());
    }

    @Test
    public void generateBagsShouldGenerateNonStringRewards() {
        // Given
        //Bag b=new Bag();
        //doNothing().when(MockBagDao).createBag(b);
        // When
        List<Bag> bags=bagService.getBagsAtStart(game,true);

        //Then

        //A nem tárgynyereményt tartalmazó táskák száma 18 + 1 (Joker)
        //Joker,2 tárgynyeremény, Malaca van!
        List<Bag> bagsWithNumber=
        bags.stream().filter(bag->
        ! Long.toString(bag.getAmmount()).equals(bag.getShowableAmmount().replaceAll(",","")))
                .collect(Collectors.toList());
        assertEquals(4,bagsWithNumber.size());
    }

    /*@Test
    public void openBag(){
        for(int bagIndex=0;bagIndex<BAGS_EXPECTED_SIZE ;bagIndex++) {
            Bag bag=bags.get(bagIndex);
            assertEquals(false,bag.isOpen());
            bag.open();
            assertEquals(true,bag.isOpen());
        }
    }*/
}