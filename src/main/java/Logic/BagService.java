package Logic;

import DAO.BagDao;
import Model.Bag;
import Model.Game;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.*;

/**
 * Táskák kezeléséért felelős osztály.
 *
 * A {@link DealOrNoDeal} és a {@link BagDao} osztályok közötti kapcsolatot valósítja meg
 */
public class BagService {

    /**
     * {@link BagService} osztály naplózását végző objektum.
     */
    private Logger logger= LoggerFactory.getLogger(BagService.class);
    /**
     * Táskák adatbázisbeli reprezentációjáért felelős objektum.
     */
    private BagDao bagDao;

    /**
     * {@link BagService} teljes publikus konstruktora.
     * @param bagDao táskák adatbázisbeli reprezentációjáért felelős objektum
     */
    public BagService(BagDao bagDao) {
        this.bagDao = bagDao;
    }

    /**
     * Legenerálja a játékhoz használt táskákat.
     * @param gameId Mely játékhoz kell legenerálni a táskákat
     * @return 22 db véletlenszerűen legenerált táska
     */
    private List<Bag> generateBags(Game gameId){ //https://hu.wikipedia.org/wiki/Áll_az_alku#A_2017-es_formátum_pénzfája
        List<Integer> bagNumbers= Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22);
        long seed = System.nanoTime();
        Random random=new Random(seed);
        Collections.shuffle(bagNumbers, random);
        List<Bag> bagList=new ArrayList<Bag>();
        List<Long> values= Arrays.asList(
                1000L,
                //Tárgynyeremény 1
                20000L,
                30000L,
                40000L,
                50000L,
                75000L,
                100000L,
                //Tárgynyeremény 2
                //Malaca van!
                600000L,
                800000L,
                1000000L,
                //Joker
                3000000L,
                4000000L,
                5000000L,
                8000000L,
                10000000L,
                15000000L,
                25000000L,
                50000000L);

        int bagIndex;
        for(bagIndex=0;bagIndex<values.size();bagIndex++){
            Long currentValue=values.get(bagIndex);
            bagList.add(new Bag(false,bagNumbers.get(bagIndex),currentValue,
                    NumberFormat.getInstance(new Locale("hu_HU")).format(currentValue)
                    ,gameId));
        }

        //eddigi csak összeget tartalmazó táskákat összepakoltuk
        Pair<Bag,Bag> p=generateTwoItems(bagNumbers.get(bagIndex++),bagNumbers.get(bagIndex++),gameId);
        bagList.add(p.getKey()); bagList.add(p.getValue());

        Long jokerValue= (random.nextInt(22)+1)*100000L;
        bagList.add(new Bag(false,bagNumbers.get(bagIndex++),jokerValue,"Joker",gameId));

        bagList.add(new Bag(false,bagNumbers.get(bagIndex),500000L,"Malaca van!",gameId)); //elvileg ennek kell a 22.-nek lennie, azaz 21-es index

        Collections.shuffle(bagList);
        logger.info("Bags were generated");
        return bagList;
    }

    /**
     * Visszaad 2 táskát, amelyben tárgynyeremények vannak.
     * @param firstBagNumber  Az első táskához társított táskaszám
     * @param secondBagNumber  A második táskához társított táskaszám
     * @param gameId Azon játék, amelyhez a 2 tárgynyereményt tartalmazó táskát legeneráljuk
     * @return Visszaad 2 táskát, amelyben tárgynyeremények vannak
     */
    private Pair<Bag, Bag> generateTwoItems(int firstBagNumber, int secondBagNumber,Game gameId){
        List<Pair<String,Long>> smallerItemValuesPair= new ArrayList<>();
        smallerItemValuesPair.add(new Pair<>("Slag",10000L));
        smallerItemValuesPair.add(new Pair<>("Gyalogló bot",10000L));
        smallerItemValuesPair.add(new Pair<>("óra",10000L));
        smallerItemValuesPair.add(new Pair<>("Haletető",10000L));
        smallerItemValuesPair.add(new Pair<>("Lámpa",10000L));
        smallerItemValuesPair.add(new Pair<>("Dobszett",10000L));

        List<Pair<String,Long>> largerItemValuesPair= new ArrayList<>();
        largerItemValuesPair.add(new Pair<>("Szörfdeszka",300000L));
        largerItemValuesPair.add(new Pair<>("Belföldi Wellnes",300000L));
        largerItemValuesPair.add(new Pair<>("Hőlégballonozás",300000L));
        largerItemValuesPair.add(new Pair<>("Szusi tanfolyam",300000L));

        long seed = System.nanoTime();

        Pair<String,Long> smallerItemValue= smallerItemValuesPair.get(new Random(seed).nextInt(smallerItemValuesPair.size()));
        Pair<String,Long> largerItemValue = largerItemValuesPair.get(new Random(seed).nextInt(largerItemValuesPair.size()));

        return new Pair<Bag,Bag>(new Bag(false,firstBagNumber,smallerItemValue.getValue(),smallerItemValue.getKey(),gameId),
                new Bag(false,secondBagNumber,largerItemValue.getValue(),largerItemValue.getKey(),gameId));
    }

    /**
     * Táska kinyitását megvalósító metódus.
     * @param bag kinyitandó táska
     */
    public void openBag(Bag bag){
        logger.debug("id: {0} bagnumber: {1} has been opned",bag.getId(),bag.getBagNumber());
        bag.setOpen(true);
        bagDao.updateBag(bag);
    }


    /**
     * Játék kezdésekor a táskák betöltéséért felelős függvény.
     * @param gameId Betölteni kívánt játék azonosítója
     * @param isNewGame újonnan lett-e létrehozva a játék (azaz még az adatbázisban nem volt legenerálva hozzá a 22 táska)
     * @return Visszaadja a játék indításakkor az összes táskát
     */
    public List<Bag> getBagsAtStart(Game gameId, boolean isNewGame){
        List<Bag> bags;
        if(isNewGame){
            bags=generateBags(gameId);
            for(int bagIndex=0;bagIndex<bags.size();bagIndex++)
                bagDao.createBag(bags.get(bagIndex));
            logger.info("Bags were inserted into database");
        }
        else {
            //__QUESTION__
            bags=bagDao.findAllBags(gameId);
            if(bags==null || bags.size()==0)
                logger.error("Loaded game has no bag");
        }

        return bags;
    }
}
