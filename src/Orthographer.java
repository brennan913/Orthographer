/**
 * 
 * @author Brennan Xavier McManus bm2530@columbia.edu
 *  
 * Orthographer takes a text file of any size and converts its contents
 * from phonemic transcription in IPA to a system of practical orthography
 * usually written using characters from the Roman alphabet.
 * 
 * Depends on DescendingSorts.class
 * 
 * Contains static methods scan, replace, and write, used for reading in text files, 
 * replacing their contents, and saving the resulting text respectively.
 * 
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Orthographer {
    
    //records number of prints for debugging purposes
    private static int printCount = 0;

    /**
     * scan in document and store in ArrayList
     * close input stream
     * @param File file - text file to be read into arraylist
     * @throws FileNotFoundException e
     */ 
   public static ArrayList<String> scan(File file) {
       
       ArrayList<String> a = new ArrayList<>();
       try{
           Scanner input = new Scanner(file);
           while( input.hasNextLine() ) {
               a.add( input.nextLine() );
           }
           input.close();
        } catch (FileNotFoundException e) {
            System.err.print("File not found!");
        }
           return a; 
   } 

   /**
    * Utility method for replace() to check if two morphemes match
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
     * Utility method for replace() to remove common diacritics
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
    * @param ArrayList<String> a - arraylist lines of text to be replaced 
    * @param String[] toArray - array of morphemes the new document will be written in
    * @param String[] fromArray - array of morphemes the original text was written in 
    */
    public static void replace(ArrayList<String> a, String[] toArray, String[] fromArray) {
        
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
     * @param ArrayList<String> a stored text to print
     */ 
    private static void print(ArrayList<String> a) {
        System.out.println("********Print "+ printCount++ + "********");
        for(int i=0;i<a.size();i++) {
                System.out.println( a.get(i) );
            }
        
    }
    
    //
    //open an output stream and write edited text to file
    //close output stream and end program
    public static void write(ArrayList<String> a, File document) {
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
    
    //main method for testing purposes
    public static final void main(String[] args) {
        /*prompt();
        scan();
        print();
        //fromIPA();
        print();
        toIPA();
        print();
        fromIPA();
        print();
        //write(); */
    }
    
    
    
}