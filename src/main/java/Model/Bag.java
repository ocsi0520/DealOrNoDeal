package Model;


//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

//import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * Egy táska reprezentálásáért felelős osztály.
 *
 * A könnyebb adatbázisbeli tárolás érdekében az osztály nem tartalmaz viselkedést (azaz ún. pehelysúlyú osztály).
 * Az adatbázisbeli tárolást végző osztály:{@link DAO.BagDao} és az ehhez tartozó service: {@link Logic.BagService}
 */
@Entity
public class Bag {

    /**
     * Táska azonosítója.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;

    /**
     * Táska állapota.
     */
    @Column(name="IS_OPEN")
    private boolean open;

    /**
     * Táska sorszáma.
     */
    @Column(name="BAG_NUMBER")
    private int bagNumber;

    /**
     * Táskában lévő nyeremény értéke számmal.
     *
     * Pl: 'Slag' nyeremény esetén 2000
     */
    @Column(name="AMMOUNT")
    private long ammount;

    /**
     * Táskában lévő nyeremény szöveggel.
     *
     * Pl: 'Slag' nyeremény esetén 'Slag', 1000 esetén '1000'
     */
    @Column(name="SHOWABLE_AMMOUNT")
    private String showableAmmount;

    /**
     * Azon {@link Game}, amelyhez a táska tartozik.
     */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity=Game.class)
    @JoinColumn(name = "GAME_ID", nullable = false)
    private Game gameId;

    /*public Bag(boolean open, int bagNumber, long ammount, String showableAmmount) {
        this.open = open;
        this.bagNumber = bagNumber;
        this.ammount = ammount;
        this.showableAmmount = showableAmmount;
    }*/

    /**
     * Táska publikus teljes konstruktora.
     * @param open Nyitott-e a táska
     * @param bagNumber Táska sorszáma
     * @param ammount Táskában lévő nyeremény összeggel kifejezve (pl 1000)
     * @param showableAmmount Táskában lévő nyeremény szöveggel kifejezve (pl 'Slag')
     * @param gameId Azon {@link Game}, amelyhez a táska tartozik.
     */
    public Bag(boolean open, int bagNumber, long ammount, String showableAmmount, Game gameId) {
        this.open = open;
        this.bagNumber = bagNumber;
        this.ammount = ammount;
        this.showableAmmount = showableAmmount;
        this.gameId = gameId;
    }

    /**
     * Táska publikus üres konstruktora.
     */
    public Bag() {
    }

    /**
     * Visszaadja a táska azonosítóját.
     * @return Visszaadja a táska azonosítóját
     */
    public int getId() {
        return id;
    }

    /**
     * Beállítja a táska azonosítóját.
     * @param id Beállítja a táska azonosítóját
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Lekérdezi a táska állapotát.
     * @return Visszaadja a táska állapotát
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Beállítja a táska állapotát.
     * @param open Beállítandó állapot
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Visszaadja a táska sorszámát.
     * @return Visszaadja a táska sorszámát
     */
    public int getBagNumber() {
        return bagNumber;
    }

    /**
     * Beállítja a táska sorszámát.
     * @param bagNumber beállítandó sorszám
     */
    public void setBagNumber(int bagNumber) {
        this.bagNumber = bagNumber;
    }

    /**
     * Visszaadja a táskában lévő nyereményt számmal kifejezve.
     * @return Visszaadja a táskában lévő nyereményt számmal kifejezve
     */
    public long getAmmount() {
        return ammount;
    }

    /**
     * Beállítja a táska értékét számmal kifejezve.
     * @param ammount beállítandó érték
     */
    public void setAmmount(long ammount) {
        this.ammount = ammount;
    }

    /**
     * Visszaadja a táskában lévő nyereményt szöveggel kifejezve.
     * @return Visszaadja a táskában lévő nyereményt szöveggel kifejezve
     */
    public String getShowableAmmount() {
        return showableAmmount;
    }

    /**
     * Beállítja a táska értékét szöveggel kifejezve.
     * @param showableAmmount beállítandó szöveg
     */
    public void setShowableAmmount(String showableAmmount) {
        this.showableAmmount = showableAmmount;
    }

    /**
     * Visszaadja azt a {@link Game}-t, amelyhez a táska tartozik.
     * @return Visszaadja azt a {@link Game}-t, amelyhez a táska tartozik
     */
    public Game getGameId() {
        return gameId;
    }

    /**
     * Meghatározza azt, hogy a táska melyik {@link Game}-hez tartozik.
     * @param gameId Játék, amelyhez a táska tartozik
     */
    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    /**
     * Táska szöveggé alakítása hibakeresés elősegítéséhez.
     * @return Visszaadja a táska részleteit szövegesen
     */
    @Override
    public String toString() {
        //return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE);
        return String.valueOf(bagNumber);
    }
}
