/**
 * 
 * @author Brennan Xavier McManus bm2530@columbia.edu
 *  
 * Orthographer takes a text file of any size and converts its contents
 * from phonemic transcription in IPA to practical orthography written
 * using exclusively characters from the Roman Alphabet.
 * 
 */
//TODO come up with conditionals to fix common formatting errors
//TODO  write new files instead of overwriting
//TODO  GUI stuff

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Orthographer {
    
    private static ArrayList<String> a = new ArrayList<>();
    private static String fileName = null;
    private static File document = null;
    private static int printCount = 0;
    private static Alphabet language;
   
    /**
     * prompts user for name of text file to be converted and name of language used for conversion
     * creates Alphabet object, which accesses arrays of morpheme pairs if the file exits, or prompts 
     * the user to create them if the file is not found. 
     */
    public static void prompt() {
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter file name: ");
        fileName = console.nextLine();
        System.out.print("Please enter alphabet name: ");
        String alphabetName = console.nextLine();
        language = new Alphabet(alphabetName);
        console.close();
    }

    /**
     * scan in document and store in ArrayList
     * close input stream
     * @throws FileNotFoundException e
     */ 
   public static void scan() {
       try{ 
           document = new File(fileName);
           Scanner input = new Scanner(document);
           while( input.hasNextLine() ) {
               a.add( input.nextLine() );
           }
           input.close();
       } catch(FileNotFoundException e){
           System.err.println("file not found!");
           System.exit(-1);
       }
   } 

   /**
    * Utility method to check if two morphemes match
    * @param StringBuilder line - current line being read
    * @param Stringbuilder modLog - record of modification for current line 
    * @param int j - current location on line
    * @param String[] fromArray - array of morphemes to check for a match at this location
    * @param int k - location of current morpheme being checked
    * @return boolean - true if morphemes match, false if morphemes differ
    */
    private static boolean match(StringBuilder line, StringBuilder modLog, int j, String[] fromArray, int k) {
        
        boolean isMatch = true;
        
        //morphemes longer than the remaining characters in the given line do not match
        if( j+fromArray[k].length() >= line.length() ) {
            return false;
        }

        for( int m=0; m<fromArray[k].length(); m++ ) {
           
            int index = j+m;
            if( line.charAt(index) != fromArray[k].charAt(m) || modLog.charAt(index) != '0') {
                isMatch = false;
                break;
            }
        }    
       return isMatch;
   }

   /**
    * toIPa calls replace(phonetic, practical)
    * converts text from phonetic IPA characters to system that uses the roman alphabet  
    */
    public static void toIPA() {
        replace( language.getPhonetic(), language.getPractical() );
    }

    /**
     * fromIPA calls replace(practical, phonetic)
     * converts text from roman alphabet to phonetic IPA characters
     */
    public static void fromIPA() {
        replace( language.getPractical(), language.getPhonetic() );
    }

    /**
     * Utility method to remove common diacritics
     * @param StringBuilder s the line of text to clean
     */
    private static void clean(StringBuilder s) {
        for(int i=0;i<s.length();i++) {
            if(s.charAt(i) == '͡')
                s = s.deleteCharAt(i);
            if(s.charAt(i) == '-')
                s = s.replace(i, i+1, " ");
        }
    }
   /**
    * Replace method to convert each unmodified morpheme between practical orthography and IPA
    * @param String[] toArray - array of morphemes the new document will be written in
    * @param String[] fromArray - array of morphemes the original text was written in 
    */
    private static void replace(String[] toArray, String[] fromArray) {
        
        DescendingSorts.descendingQuickSort(toArray, fromArray);

        StringBuilder line;
        StringBuilder modLog;
        int bound;
        StringBuilder newLog;
        //read each line
        for(int i=0;i<a.size();i++) {
            
            line = new StringBuilder( a.get(i) );
            clean(line);
            modLog = new StringBuilder( line.toString() );

            
            //initialize each line as unmodified
            for(int j=0;j<line.length();j++) {
                modLog.setCharAt(j,'0');
            }

            for(int j=0;j<line.length();j++) {
                for(int k=0;k<fromArray.length;k++) {
                    if( match(line, modLog, j, fromArray, k) ) {
                        
                        bound = fromArray[k].length();
                        line.replace( j, j+bound, toArray[k] );
                        
                        //update modification record 
                        newLog = new StringBuilder(toArray[k].length());
                        for (int m=0;m<toArray[k].length();m++)
                            newLog.append('1');
                        modLog.replace( j, j+bound, newLog.toString() );

                    }
                }

            }
            a.set(i, line.toString() );
       }
   }
   
    /**
     * Print
     * print the stored text in its current form line by line
     * increments printCount
     */ 
    public static void print() {
        System.out.println("********Print "+ printCount++ + "********");
        for(int i=0;i<a.size();i++) {
                System.out.println( a.get(i) );
            }
        
    }
    
    //
    //open an output stream and write edited text to file
    //close output stream and end program
    public static void write() {
        try{
            PrintWriter outputStream = new PrintWriter(document);
            for(int i=0;i<a.size();i++) {
                outputStream.println( a.get(i) );
            }
            outputStream.flush();
            outputStream.close();
        } catch(FileNotFoundException e) {
            System.err.println("file not found!");
            System.exit(-1);
        }
    }
    
    //main method
    public static final void main(String[] args) {
        prompt();
        scan();
        print();
        //fromIPA();
        print();
        toIPA();
        print();
        fromIPA();
        print();
        //write();
    }
    
    
    
}