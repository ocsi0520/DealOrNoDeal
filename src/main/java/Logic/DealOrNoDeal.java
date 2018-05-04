package Logic;

import Model.Bag;
import Model.Game;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Az 'Áll az alku' játék logikai részét megvalósító osztály
 */
public class DealOrNoDeal {

    private BagService bagService;
    private GameService gameService;
    private Game game;
    private String playerName;
    private Logger logger=LoggerFactory.getLogger(DealOrNoDeal.class);

    /**
     * DealOrNoDeal osztály privát inicializáló konstruktora.
     * @param gameService Játékhoz használt {@link GameService}
     * @param bagService Játékhoz használt {@link BagService}
     */
    public DealOrNoDeal(GameService gameService, BagService bagService){
        this.gameService=gameService;
        this.bagService=bagService;
        this.game=gameService.createNewGame("Test");
    }

    /**
     * DealOrNoDeal osztály konstruktora, mely egy új játékot hoz létre
     * @param gameService Játékhoz használt {@link GameService}
     * @param bagService Játékhoz használt {@link BagService}
     * @param playerName Játékos neve
     */
    public DealOrNoDeal(GameService gameService,BagService bagService , String playerName){
        this(gameService, bagService);
        this.game = this.gameService.createNewGame(playerName);
        bags = this.bagService.getBagsAtStart(game,true);
    }

    /**
     * DealOrNoDeal osztály konstruktora, mely egy új játékot hoz létre
     * @param gameService Játékhoz használt {@link GameService}
     * @param bagService Játékhoz használt {@link BagService}
     * @param gameId Betöltendő játék azonosítója
     */
    public DealOrNoDeal(GameService gameService,BagService bagService , Integer gameId) {
        this(gameService, bagService);
        this.game = this.gameService.findGameById(gameId);
        bags = this.bagService.getBagsAtStart(game, false);
        playerName = this.game.getPlayerName();
        openedBags= (int) bags.stream().filter(bag->bag.isOpen()).count();
        offerAccepted = game.getPrize() != null;
    }


    /**
     * Játék során kinyitott táskák száma
     */
    private int openedBags=0;
    /**
     * Meghatározza, hogy a játékos fogadott-e már el ajánlatot
     */
    private boolean offerAccepted=false;
    /**
     * Meghatározza, hogy hanyadik táskanyitás után ad ajánlatot a bank
     *
     * pl ha az 5. táskát nyitottuk ki, akkor az {@code Arrays.asList(5,8,11,14,16,18,20)} tömb alapján ajánlatot kell tennünk
     */
    private List<Integer> offersAtOpenedBags=Arrays.asList(5,8,11,14,16,18,20);

    /**
     * Játékban lévő táskák tömbje
     */
    private List<Bag> bags;

    /**
     * Eddigi ajánlatok listája
     */
    private List<Long> offers = new ArrayList<>();

    /**
     * Kinyit egy táskát a megadott számmal
     * @param bagNumber Kinyitandó táska száma
     * @return Visszaadja a kinyitott táskát. Ha a táska már nyitott volt, akkor {@code null}-t ad vissza
     */
    public Bag openBag(int bagNumber){
        Bag bag = bags.stream().filter(x->x.getBagNumber() == bagNumber).findFirst().get();
        if(bags.indexOf(bag)==21 && openedBags<21)
            return null;
        if(!bag.isOpen()) {
            openedBags++;
            bagService.openBag(bag);
            logger.info("id: " + bag.getId() + " number:" + bagNumber + " bag has been opened");
            if(isFinished()) { //Ha az összes táskát kinyitottuk
                game.setFinished(true);
                if(!isOfferAccepted()) // és nem fogadott el eddig egy ajánlatot se
                    setGamePrize(bag.getShowableAmmount());
                gameService.saveGame(game);
            }
            return bag;
        }
        logger.warn("id: " + bag.getId() + " number:" + bagNumber + " bag had been already opened");
        return null;
    }

    public boolean isFinished(){
        return openedBags==22;
    }

    /**
     * Lekérdezi a játék során elért nyereményt
     * @return Nyeremény szövegesen
     */
    public String getGamePrize(){
        return game.getPrize();
    }

    /**
     * Játéknak beállítja a nyereményét
     * @param prize beállítandó nyeremény
     */
    private void setGamePrize(String prize){
        game.setPrize(prize);
        gameService.saveGame(game);
        logger.info("Prize has been written: {}",prize);
    }


    public List<Bag> getBags(){
        return bags;
    }

    /**
     * Játék jelenlegi állása alapján készít egy ajánlatot és hozzáteszi az eddigi ajánlatok listájához {@link #offers}
     * @return Ajánlat, melyet a bank tett
     */
    public long makeOffer(){
        logger.info("Offer has been given");
        long offer= (long) (bags.stream().filter(bag->!bag.isOpen()).mapToDouble(x->x.getAmmount()).average().getAsDouble() *
                (offersAtOpenedBags.indexOf(openedBags)+1)
                / 10.0);
        offers.add(offer);
        return offer;
        //banker's offer = average value * turn number / 10
    }

    /**
     * Megadja, hogy a játék jelenlegi állása alapján ajánlatot kell-e tennie a banknak
     * @return Visszaadja, hogy kell-e ajánlatot tenni
     */
    public boolean isOfferNeeded(){
        return offersAtOpenedBags.contains(openedBags);
    }

    /**
     * Megadja, hogy a játékos fogadott-e már el ajánlatot
     * @return Visszaadja, hogy a játékos fogadott-e már el ajánlatot
     */
    public boolean isOfferAccepted(){
        return offerAccepted;
    }

    /**
     * Jelenlegi ajánlatot elfogadja
     */
    public void acceptOffer(){
        offerAccepted=true;
        setGamePrize(offers.get(offers.size()-1).toString());
    }

    /**
     * Megadja az eddigi ajánlatok listáját
     * @return Visszaadja az eddigi ajánlatok listáját
     */
    public List<Long> getPreviousOffers() {
        return offers;
    }
}
