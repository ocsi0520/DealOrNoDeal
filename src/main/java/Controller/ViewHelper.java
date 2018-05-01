package Controller;

import Model.Bag;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Megjelenítéshez szükséges segédosztály
 */
public class ViewHelper {
    /**
     * Játékban lévő nyeremények értékei számmal kifejezve (tartalmazza a tárgyak pénzértékeit is)
     */
    List<Long> values;

    /**
     * ViewHelper teljes konstruktora
     * @param list Játékban használt táskák listája
     */
    public ViewHelper(List<Bag> list){
        this.values=list.stream().map(Bag::getAmmount).sorted().collect(Collectors.toList());
    }

    /**
     * Szín meghatározását végző metódus
     * @param bag Azon táska, melynek színét meg akarjuk határozni
     * @return Visszaadja az adott táska kikalkulált színét
     */
    public Color ColorCalculator(Bag bag){
            long ammount = bag.getAmmount();
            int indexOfValue=values.indexOf(ammount);
            double blue = (21.0-indexOfValue)/21.0;
            double red = indexOfValue/21.0;
            double green = 0.3;
            Color c = new Color(red, 1, blue, .99);
            return c;
    }
}
