import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

//read in alphabet pairs in phonetic and practical pairs
//if the alphabet does not exist, prompt user for new alphabet name
//if the alphabet name does not match an existing alphabet
//prompt for each alphabet (IPA then practical), with morphemes separated by spaces
//and create new .txt file in alphabet folder
//if the alphabet already exits, inform the user and do nothing. 
//for toIPA conversions, set toArray to IPA and fromArray to practical
//for toPractical conversions, set toArray to practical and fromArray to IPA
public class ReadAlphabet{
   
    private static File alphabet;
    private static Scanner alphabetReader;
    public static String[] phonetic;
    public static String[] practical;
    private static String fileName;
    
    /**
     * Takes user input to get filename for alphabet document
     */
    public static void prompt() {
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter alphabet name: ");
        fileName = console.nextLine();
        StringBuilder fileBuilder = new StringBuilder(fileName);
        fileBuilder = fileBuilder.append(".txt");
        fileName = fileBuilder.toString();
        console.close();
    }

    
    /**
     * reads alphabet text file and sets phonetic to line 0 and practical to line 1
     * could cosider storing as Lists with the option for method addMorpheme(String IPA, String practical)
     */
    public static void read() {
        try {
            alphabet = new File(fileName);
            alphabetReader = new Scanner(alphabet);
            phonetic = alphabetReader.nextLine().split(" ");
            practical = alphabetReader.nextLine().split(" ");
        } catch(FileNotFoundException e) {
            System.err.print("File not found!");
            System.exit(-1);
        }
    }
    /**
     * Prints the two alphabets in a pair of columns
     */
    public static void print() {
        for(int i=0;i<phonetic.length;i++) {
            System.out.println(phonetic[i] + "\t" + practical[i]);
        }
    }
    /**
     * accessor method for phonetic alphabet
     * @return String[] collection of morphemes written in IPA
     */
    public static String[] getPhonetic() {
        return phonetic;
    }
    /**
     * accessor method for practical alphabet
     * @return String[] collection of morphemes written in practical orthography
     */
    public static String[] getPractical() {
        return practical;
    }
    public static final void main(String[] args) {
        prompt();
        read();
        print();
    }

}