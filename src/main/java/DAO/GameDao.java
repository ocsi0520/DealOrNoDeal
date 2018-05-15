package DAO;

import Model.Game;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * {@link Game} objektumok adatbázisbeli tárolásáért felelős osztály.
 */
public class GameDao {

    /**
     * Perzisztenciaegységet kezelő objektum.
     */
    private EntityManager entityManager;

    /**
     * {@link GameDao} teljes publikus konstruktora.
     * @param entityManager perzisztenciaegységet kezelő objektum
     */
    public GameDao(EntityManager entityManager) {
        this.entityManager=entityManager;
    }

    /**
     * Adatbázisba létrehoz egy játékot.
     * @param game Adatbázisban létrehozandó játék
     */
    public void createGame(Game game){
        entityManager.getTransaction().begin();
        entityManager.persist(game);
        entityManager.getTransaction().commit();
    }

    /**
     * Kiolvas egy játékot adatbázisból.
     * @param Id Kiolvasandó játék azonosítója
     * @return Kiolvasott játék, ha adott {@code Id}-val nem talált játékot, akkor {@code null}-t ad vissza
     */
    public Game readGame(Integer Id){
        return entityManager.find(Game.class,Id);
    }

    /**
     * Egy játék tartalmát frissíti adatbázisban.
     * @param game Frissítendő játék
     */
    public void updateGame(Game game){
        entityManager.getTransaction().begin();
        entityManager.merge(game); //megtalálja magától
        entityManager.getTransaction().commit();
    }

    /**
     * Kitöröl egy játékot az adatbázisból.
     * @param game Törlendő játék
     */
    public void deleteGame(Game game){
        entityManager.getTransaction().begin();
        entityManager.remove(game);
        entityManager.getTransaction().commit();
    }

    /**
     * Kiolvassa az összes olyan játékot, amelyek nem lettek befejezve.
     * @return Visszaadja az összes nem befejezett játékot
     */
    public List<Game> findAllNotFinishedGames(){
        TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Model.Game g WHERE FINISHED = FALSE", Game.class);
        return query.getResultList();
    }
}
