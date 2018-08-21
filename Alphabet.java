import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

//read in alphabet pairs in phonetic and practical pairs
//if the alphabet does not exist, prompt user for new alphabet name
//if the alphabet name does not match an existing alphabet
//prompt for each alphabet (IPA then practical), with morphemes separated by spaces
//and create new .txt file in alphabet folder
//if the alphabet already exits, inform the user and do nothing. 
//for toIPA conversions, set toArray to IPA and fromArray to practical
//for toPractical conversions, set toArray to practical and fromArray to IPA
public class Alphabet{
   
    //static variables
    private static String fileName;
    private static String newLanguageName;
    private static String ipa;
    private static String pcal;



    //instance variables
    private String[] phonetic;
    private String[] practical;
    private String alphabetName;

    
    //Constructors
    /**
     * Default Constructor
     */
    public Alphabet(String languageName) {

        alphabetName = languageName;
        newLanguageName = languageName;
       
        StringBuilder fileBuilder = new StringBuilder("Languages/");
        fileBuilder = fileBuilder.append(languageName);
        fileBuilder = fileBuilder.append(".txt");
        fileName = fileBuilder.toString();
        Scanner alphabetReader;
        // read in alphabets contained in file
        try {

            File alphabet = new File(fileName);
            alphabetReader = new Scanner(alphabet);

            if( alphabetReader.hasNextLine() )
            phonetic = alphabetReader.nextLine().split(" ");
            
            if( alphabetReader.hasNextLine() )
            practical = alphabetReader.nextLine().split(" ");

        } catch(FileNotFoundException e) {
            //TODO new morpheme list creation
            newAlphabet();
            phonetic = ipa.split(" ");
            practical = pcal.split(" ");
        }
        
    }
    
    /**
     * newAlphabet 
     */
    private static void newAlphabet() {

        Scanner console = new Scanner(System.in);

        System.out.println("No morpheme list for \"" + newLanguageName + "\" found, would you like to add a new language?");
        System.out.print("Type \"yes\" to continue or \"no\" to exit: "); 
       
        String answer = console.nextLine();
        answer = answer.toLowerCase();
        answer = answer.trim();
        
        if( !answer.equals("y")  && !answer.equals("yes") && answer.charAt(0) != 'y') 
            System.exit(-1);


        System.out.println("Please enter morpheme lists");

        System.out.println("Enter list of morphemes in IPA below, separated by spaces");
        ipa = console.nextLine();

        //TODO allow for exiting without correctly formatted morpheme lists
        while( ipa.split(" ")[0].equals(ipa) ) {
            System.out.println("Alphabet entered incorrectly! Remember to separate each morpheme with spaces, \nand to include each aspirated consonant as its own morpheme");
            System.out.println("Enter list of morphemes in IPA below, separated by spaces");
            ipa = console.nextLine();    
        }

        System.out.println("enter list of morphemes in practical orthography below, separated by spaces");
        pcal = console.nextLine();

        while( pcal.split(" ")[0].equals(pcal) ) {
            System.out.println("Alphabet entered incorrectly! Remember to separate each morpheme with spaces, \nand to include each aspirated consonant as its own morpheme");
            System.out.println("Enter list of morphemes in IPA below, separated by spaces");
            pcal = console.nextLine();
        }
    
        console.close();

        File newAlphabet = new File(fileName);

        try {
            PrintWriter p = new PrintWriter(newAlphabet);
            p.println(ipa);
            p.println(pcal);
            p.close();
        } catch (FileNotFoundException e2) {
            System.err.print("Could not write file! This should never happen");
            System.exit(-2);
        }
    }
    /**
     * Prints the two alphabets in a pair of columns
     */
    public void print() {
        if(phonetic.length != practical.length ) {
            System.out.println("Alphabets do not match! Could not print");
            return;
        }

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
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter language name: ");
        String lang = input.nextLine();
        Alphabet language = new Alphabet(lang);
        System.out.println(language.alphabetName);
        language.print();
    }

}