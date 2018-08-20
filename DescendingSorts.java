public class DescendingSorts {
   
    /** 
     * @param String[] s1 
     * @param String[] s2
     * sorts two String arrays, s1.length = s2.length, ordering both arrays in parallel
     * such that s1 is sorted in descending order by String.length() and each item in s2 
     * is located at the same index as the new index of its original partner 
     */
    public static void selectionSort(String[] toArray, String[] fromArray) {
        
        if(toArray.length != fromArray.length) {
            System.out.println("Alphabets do not match! Could not sort");
            return;
        }

        String temp = null;
        int locLongest;

        for(int i=0;i<fromArray.length;i++) {
            
            locLongest = i;

            for(int j=i+1;j<fromArray.length;j++){
                if( fromArray[j].length() > fromArray[locLongest].length() )
                    locLongest = j;
            }

            temp = fromArray[i];
            fromArray[i] = fromArray[locLongest];
            fromArray[locLongest] = temp;

            temp = toArray[i];
            toArray[i] = toArray[locLongest];
            toArray[locLongest] = temp;

        }
    }
    /**
     * Main method tests sorting algorithms using ReadAlphabet static methods
     */
    public static final void main(String[] args) {
    Alphabet language = new Alphabet();
    language.print();
    String[] toArray = language.getPractical();
    String[] fromArray = language.getPhonetic();
    selectionSort(toArray, fromArray);
    System.out.println();
    language.print();
    
    }
}