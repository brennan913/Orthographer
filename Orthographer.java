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
    private static StringBuilder 
   
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
           if( line.charAt(j+m) != fromArray[k].charAt(m) || modLog.charAt(j+m) != '0')
                isMatch = false;
       }
       return isMatch;
   }

   /**
    * Replace method to convert each unmodified location to IPA
    */
   public static void replace() {
       for(int i=0;i<a.size();i++) {
            StringBuilder line = new StringBuilder
       }
   }
    //iterate through stored text and run find and replace
    /*
     * proof of concept of find and replace 
    public static void replace() {
        for(int i=0;i<a.size();i++) {
            //System.out.println("Line: "+ i);            
            StringBuilder line = new StringBuilder( a.get(i) );

            for(int j=0;j<line.length();j++) { 
                //System.out.println( "charAt("+ j + "): " + line.charAt(j) );
                //replace "ŋ" with "ng"
                if(line.charAt(j) == 'ŋ') {
                    line.replace(j,j+1,"ng");
        System.out.println("replaced \"ŋ\" with \"ng\"");

                }
                //replace "ɑ" with "a"
                if(line.charAt(j) == 'ɑ') {
                    line.replace(j,j+1,"a");
        System.out.println("replaced \"ɑ\" with \"a\"");

                }            
                //replace "æ" with "aa"
                if(line.charAt(j) == 'æ') {
                    line.replace(j,j+1,"aa");
        System.out.println("replaced \"æ\" with \"aa\"");
                    
                }                        
                //replace "tʃ" with "ch"
                if(line.charAt(j) == 't' 
                   && line.charAt(j+1) == 'ʃ') {
                    
                    line.replace(j,j+2,"ch");
        System.out.println("replaced \"tʃ\" with \"ch\"");
                    
                }   
                //replace "t͡ʃ" with "ch"
                //TODO combine with "t͡s" replacement
                if( line.charAt(j) == 't'
                   && line.charAt(j+1) == '͡'
                   && line.charAt(j+2) == 'ʃ'){
        System.out.println("replaced \"t͡ʃ\" with \"ch\"");
                    
                    line.replace(j,j+3,"ch");
                }
                //replace "ʃ" with "sh"
                if(line.charAt(j) == 'ʃ') {
                    line.replace(j,j+1,"sh");
        System.out.println("replaced \"ʃ\" with \"sh\"");
                    
                }                        
                //replace "-" with " "
                if(line.charAt(j) == '-') {
                    line.replace(j,j+1," ");
        System.out.println("replaced \"-\" with \" \"");
                    
                }                        
                //replace "ɖ" with "dr"
                if(line.charAt(j) == 'ɖ') {
                    line.replace(j,j+1,"dr");
        System.out.println("replaced \"ɖ\" with \"dr\"");
                    
                }                        
                //replace "ʈ" with "tr"
                if(line.charAt(j) == 'ʈ') {
                    line.replace(j,j+1,"tr");
                }                        
                //replace "dz" and "tz" with "ts"
                if( (line.charAt(j) == 'd' 
                   || line.charAt(j) == 't') 
                   && line.charAt(j+1) == 'z') {
                    
                    line.replace(j,j+2,"ts");
                    }               
                // replace "t͡s" with "ts"
                if( line.charAt(j) == 't'
                  && line.charAt(j+1) == '͡'
                  && line.charAt(j+2) == 's') {
                    
                    line.replace(j,j+3,"ts");
                }
                //replace "j" with "y"
                if(line.charAt(j) == 'j') {
                    line.replace(j,j+1,"y");
                    }                        
                //replace "dʒ" with "j"
                if(line.charAt(j) == 'd'
                   && line.charAt(j+1) == 'ʒ') {
                   
                    line.replace(j,j+2,"j");
                    }             
                //replace "d͡ʒ" with "j"
                if(line.charAt(j) == 'd' 
                   && line.charAt(j+1) == '͡' 
                   && line.charAt(j+2) == 'ʒ') {
                    
                    line.replace(j,j+3,"j");
                } 
                //replace ʰ with h (this probably won't work)
                if(line.charAt(j) == 'ʰ') {
                    line.replace(j,j+1,"h");
                    }                        
            }
            
            a.set( i,line.toString() );
            
        }
    }
     */
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