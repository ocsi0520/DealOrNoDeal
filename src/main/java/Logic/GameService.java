package Logic;

import DAO.GameDao;
import Model.Game;

import java.util.List;

/**
 * Játékok kezeléséért felelős osztály
 *
 * A {@link DealOrNoDeal} és a {@link GameDao} osztályok közötti kapcsolatot valósítja meg
 */
public class GameService {

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
        return game;
    }

    /**
     * Adatbázisból kinyeri az adott azonosítójű játékot.
     *
     * Amennyiben az adatbázisban nem talált ilyen azonosítójú játékot {@code null}-t ad vissza
     * @param Id kereséshez felhasznált azonosító
     * @return Keresett játék ({@code null}, ha nem található)
     */
    public Game findGameById(Integer Id){
        return gameDao.readGame(Id);
    }
}
