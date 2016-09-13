package notes.beans;

import java.util.TreeMap;

public class Data {

    private TreeMap<String, Integer> map;

    public Data() {
        map = ParsingTools.creerMap();
    }

    public TreeMap<String, Integer> getMap() {
        return map;
    }

    public void setMap( TreeMap<String, Integer> map ) {
        this.map = map;
    }

 

}
