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
public class Alphabet{
   
    //instance variables
    private String[] phonetic;
    private String[] practical;
    private String alphabetName;

    
    /**
     * Constructor
     */
    public Alphabet() {
        
        // prompt user for alphabet name
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter alphabet name: ");
        
        String fileName = console.nextLine();
        console.close();
        alphabetName = fileName;
       
        StringBuilder fileBuilder = new StringBuilder(fileName);
        fileBuilder = fileBuilder.append(".txt");
        fileName = fileBuilder.toString();
        
        
        // read in alphabets contained in file
        try {

            File alphabet = new File(fileName);
            Scanner alphabetReader = new Scanner(alphabet);

            phonetic = alphabetReader.nextLine().split(" ");
            practical = alphabetReader.nextLine().split(" ");

            alphabetReader.close();

        } catch(FileNotFoundException e) {

            System.err.print("File not found!");
            System.exit(-1);

        }
        
    }

    /**
     * Prints the two alphabets in a pair of columns
     */
    public void print() {
        for(int i=0;i<phonetic.length;i++) {
            System.out.println(phonetic[i] + "\t" + practical[i]);
        }
    }
    /**
     * accessor method for phonetic alphabet
     * @return String[] collection of morphemes written in IPA
     */
    public String[] getPhonetic() {
        return phonetic;
    }
    /**
     * accessor method for practical alphabet
     * @return String[] collection of morphemes written in practical orthography
     */
    public String[] getPractical() {
        return practical;
    }

    /**
     * accessor method for language name
     * @return String name of language
     */
    public String getAlphabetName() {
        return alphabetName;
    }


    public static final void main(String[] args) {
        Alphabet language = new Alphabet();
        System.out.println(language.alphabetName);
        language.print();
    }

}