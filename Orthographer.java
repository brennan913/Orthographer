//TODO
//Make it reversable ( toIPA() ) 
//rename replace, think ( fromIPA() ) 
//take language instructions as a parameter
//come up with name Orthographer, IPA Converter
//come up with conditionals to fix common formatting errors
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
    private static StringBuilder line;
    private static StringBuilder modLog;
    private static StringBuilder newLog;

    private static String[] toArray;
    private static String[] fromArray;
    private static int bound;
   
    //prompt user for filename
    public static void prompt() {
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter file name: ");
        fileName = console.nextLine();
        console.close();
    }
    //scan in document and store in ArrayList
    //close input stream
    //throws FileNotFoundException
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
    * @param StringBuilder line
    * @param Stringbuilder modLog
    * @param int j
    * @param String[] fromArray
    * @param int k
    */
   private static boolean match(StringBuilder line, StringBuilder modLog, int j, String[] fromArray, int k) {
       boolean isMatch = true;
       for( int m=0; m<fromArray[k].length(); m++ ) {
           if( line.charAt(j+m) != fromArray[k].charAt(m) || modLog.charAt(j+m) != '0') {
                isMatch = false;
                break;
           }
       }
       return isMatch;
   }
   // TODO descending QuickSort
   /** 
    *
    */
   // TODO importAlphabet

   /**
    * Replace method to convert each unmodified location to IPA
    */
    public static void replace() {
       
        for(int i=0;i<a.size();i++) {
            
            line = new StringBuilder();
            modLog = new StringBuilder( line.length() );
            
            //mark line as unmodified
            for(int j=0;j<line.length();j++) {
            modlog.setCharAt(j,'0');
            }

            for(int j=0;j<line.length();j++) {
                for(int k=0;k<fromArray.length;k++) {
                    if( match(line, modLog, j,fromArray, k) ) {
                       
                        bound = fromArray[k].length();
                        line.replace( j, bound, toArray[k] );
                        
                        //update modification record 
                        newLog = new StringBuilder();
                        for (int m=0;m<bound;m++)
                            newLog.append('1');
                        modLog.replace( j, bound, newLog.toString() );

                    }
                }

            }

       }
   }
   
    //print the stored text in its current form
    public static void print() {
        printCount++;
        System.out.println("********Print "+ printCount + "********");
        for(int i=0;i<a.size();i++) {
                System.out.println( a.get(i) );
            }
        
    }
    //see how single string gets parsed as indivisual chars
    //used for testing purposes
    public static void charPrint() {
        
        System.out.println("********CharPrint********");
        for(int i=0;i<a.size();i++) {
            System.out.println("Line " + i);
            for(int j=0;j<a.get(i).length();j++){
                System.out.println( "Character " + j + ": "+ a.get(i).charAt(j) );
            }
        }
    }
    
    //open an output stream and write edited text to file
    //close output stream and end program
    public static void write() {
        try{
            PrintWriter outputStream = new PrintWriter(document);
            for(int i=0;i<a.size();i++) {
                outputStream.println( a.get(i) );
            }
            outputStream.flush();
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
        replace();
        print();
        write();
    }
    
    
    
}