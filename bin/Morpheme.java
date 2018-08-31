import javafx.beans.property.SimpleStringProperty;

public class Morpheme {

    private final SimpleStringProperty ipa;
    private final SimpleStringProperty practical;

    //constructor
    public Morpheme(String i, String p){
    ipa = new SimpleStringProperty(i);
    practical = new SimpleStringProperty(p);
    }

    //accessor methods
    public String getIpa() {
        return ipa.get();
    }

    public String getPractical() {
        return practical.get();
    }

    public String toString() {
        String s = ipa.get() + "\t" + practical.get();
        return s;
    }
    
}