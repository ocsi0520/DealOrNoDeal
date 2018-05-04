package Logic;

import DAO.GameDao;
import Model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Játékok kezeléséért felelős osztály
 *
 * A {@link DealOrNoDeal} és a {@link GameDao} osztályok közötti kapcsolatot valósítja meg
 */
public class GameService {

    private Logger logger= LoggerFactory.getLogger(GameService.class);
    /**
     * Játékok adatbázisbeli reprezentációjáért felelős objektum
     */
    GameDao gameDao;
    /**
     * {@link GameService} teljes publikus konstruktora
     * @param gameDao táskák adatbázisbeli reprezentációjáért felelős objektum
     */
    public GameService(GameDao gameDao) {
        this.gameDao=gameDao;
    }

    /**
     * Adatbázisból lekérdezi az összes nem befejezett játékot
     * @return Visszaadja az összes nem befejezett játékot
     */
    public List<Game> getAllNotFinishedGames(){
        return gameDao.findAllNotFinishedGames();
    }

    /**
     * Egy új játékot hoz létre, melyet az adatbázisban is letárol.
     *
     * !!FONTOS!! a játékhoz tartozó táskákat NEM készíti el. Pusztán egy darab játékrekordot hoz létre.
     * @param playerName játékos neve
     * @return Visszaadja az újonnan létrehozott játékot
     */
    public Game createNewGame(String playerName){
        Game game=new Game();
        game.setFinished(false);
        game.setPlayerName(playerName);
        gameDao.createGame(game);
        logger.info("New game (id:{0}) has been created",game.getId());
        return game;
    }

    /**
     * Adatbázisból kinyeri az adott azonosítójű játékot.
     *
     * Amennyiben az adatbázisban nem talált ilyen azonosítójú játékot {@code null}-t ad vissza
     * @param Id kereséshez felhasznált azonosító
     * @return Keresett játék.({@code null}, ha nem található)
     */
    public Game findGameById(Integer Id){
        Game game=gameDao.readGame(Id);
        if(game==null)
            logger.error("Game with {0} id was not found", Id);
        return gameDao.readGame(Id);
    }

    /**
     * Elmenti az adott játék állapotát az adatbázisba
     * @param currentGame Elmentendő játék
     */
    public void saveGame(Game currentGame){
        gameDao.updateGame(currentGame);
    }
}
