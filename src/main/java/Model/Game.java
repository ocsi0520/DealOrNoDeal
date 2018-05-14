package Model;

//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * Egy játék reprezentálásáért felelős osztály
 *
 * A könnyebb adatbázisbeli tárolás érdekében az osztály nem tartalmaz viselkedést (azaz ún. pehelysúlyú osztály).
 * Az adatbázisbeli tárolást végző osztály:{@link DAO.GameDao} és az ehhez tartozó service: {@link Logic.GameService}
 */
@Entity
public class Game {
    /**
     * Játék azonosítója.
     */
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    /**
     * Játékhoz tartozó {@link Bag}-ek listája.
     */
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="gameId")
    private List<Bag> bags;

    /**
     * Játék állapota.
     */
    @Column(name="FINISHED", nullable = false)
    private boolean finished;

    /**
     * Játékos neve.
     */
    @Column(name="PLAYER_NAME", nullable = false)
    private String playerName;

    /**
     * Játékos végső nyereménye.
     */
    @Column(name="PRIZE",nullable = true)
    private String prize;

    /**
     * Játék publikus teljes konstruktora.
     * @param bags Játékhoz tartozó táskák listája
     * @param finished Játék állapota
     * @param playerName Játékos neve
     * @param prize Játékos végső nyereménye
     */
    public Game(List<Bag> bags, boolean finished, String playerName, String prize) {
        this.bags = bags;
        this.finished = finished;
        this.playerName = playerName;
        this.prize = prize;
    }

    /**
     * Játék publikus üres konstruktora.
     */
    public Game() {
    }

    /**
     * Visszaadja a játék azonosítóját.
     * @return Visszaadja a játék azonosítóját
     */
    public int getId() {
        return id;
    }

    /**
     * Beállítja a játék azonosítóját.
     * @param id Beállítandó azonosító
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Visszaadja a játékhoz tartozó {@link Bag}-ek listáját.
     * @return Visszaadja a játékhoz tartozó {@link Bag}-ek listáját
     */
    public List<Bag> getBags() {
        return bags;
    }

    /**
     * Beállítja a játékhoz tartozó {@link Bag}-ek listáját.
     * @param bags Játékhoz tartozó táskák listája
     */
    public void setBags(List<Bag> bags) {
        this.bags = bags;
    }

    /**
     * Lekérdezi a játék állapotát.
     * @return Visszaadja a játék állapotát
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Beállítja a játék állapotát.
     * @param finished Beállítandó állapot
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Visszaadja a játékos nevét.
     * @return Visszaadja a játékos nevét
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Beállítja a játékos nevét.
     * @param playerName Játékos neve
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Visszaadja a játékos által elért végső nyereményt.
     * @return Visszaadja a játékos által elért végső nyereményt
     */
    public String getPrize() {
        return prize;
    }

    /**
     * Beállítja a végső nyereményt.
     * @param prize nyeremény
     */
    public void setPrize(String prize) {
        this.prize = prize;
    }

    /**
     * Játék szöveggé alakítása hibakeresés elősegítéséhez.
     * @return Visszaadja a játék részleteit szövegesen
     */
    @Override
    public String toString() {
        return playerName;
    }
}
