package DAO;

import Model.Bag;
import Model.Game;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * {@link Bag} objektumok adatbázisbeli tárolásáért felelős osztály
 */
public class BagDao {

    /**
     * Perzisztenciaegységet kezelő objektum
     */
    private EntityManager entityManager;

    /**
     * {@link BagDao} teljes publikus konstruktora
     * @param entityManager perzisztenciaegységet kezelő objektum
     */
    public BagDao(EntityManager entityManager) {
        this.entityManager=entityManager;
    }

    /**
     * Adatbázisba létrehoz egy táskát
     * @param bag Adatbázisban létrehozandó táska
     */
    public void createBag(Bag bag){
        entityManager.getTransaction().begin();
        entityManager.persist(bag);
        entityManager.getTransaction().commit();
    }

    /**
     * Kiolvas egy táskát adatbázisból
     * @param Id Kiolvasandó táska azonosítója
     * @return Kiolvasott táska, ha adott {@code Id}-val nem talált táskát, akkor {@code null}-t ad vissza
     */
    public Bag readBag(Integer Id){
        return entityManager.find(Bag.class,Id);
    }

    /**
     * Egy táska tartalmát frissíti adatbázisban
     * @param bag Frissítendő táska
     */
    public void updateBag(Bag bag){
        entityManager.getTransaction().begin();
        entityManager.merge(bag); //megtalálja magától
        entityManager.getTransaction().commit();
    }

    /**
     * Kitöröl egy táskát az adatbázisból
     * @param bag Törlendő táska
     */
    public void deleteBag(Bag bag){
        entityManager.getTransaction().begin();
        entityManager.remove(bag);
        entityManager.getTransaction().commit();
    }

    /**
     * Kiolvassa egy játékhoz tartozó összes táskát az adatbázisból
     * @param gameId Játék azonosítója, amelyhez tartozó táskákat szeretnénk kiolvasni
     * @return Visszaadja a játékhoz tartozó táskák listáját
     */
    //__QUESTION__
    public List<Bag> findAllBags(Game gameId){
        TypedQuery<Bag> query = entityManager.createQuery("SELECT b FROM Model.Bag b WHERE GAME_ID = " + gameId.getId(), Bag.class);
        return query.getResultList();
    }
}
