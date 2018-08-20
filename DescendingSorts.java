public class DescendingSorts {
   
    /** 
     * @param String[] s1 
     * @param String[] s2
     * sorts two String arrays, s1.length = s2.length, ordering both arrays in parallel
     * such that s1 is sorted in descending order by String.length() and each item in s2 
     * is located at the same index as the new index of its original partner 
     */
    public static void descendingSelectionSort(String[] toArray, String[] fromArray) {
        
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
     * Descending Quicksort
     * @param String[] s1 array that determines ordering by String.length()
     * @param String[] s2 array that is sorted in parallel
     */
    public static void descendingQuickSort(String[] s1, String[] s2) {
        if(s1.length != s2.length ) {
            System.out.println("Alphabets do not match! Could not sort");
            return;
        }

         descendingQuickSort(s1, s2, 0, s1.length-1);
    }

    /**
     * Utility method to swaps references to Strings in paired arrays
     * @param String[] s1 to be swapped, deterimines ordering by String.length()
     * @param String[] s2 array to be swapped in parallel
     * @param int index of first String pair to be swapped 
     * @param int index of second String pair to be swapped
     */
    private static void swap(String[] s1, String[] s2, int x, int y) {
        String temp;
        temp = s1[x];
        s1[x] = s1[y];
        s1[y] = temp;

        temp = s2[x];
        s2[x] = s2[y];
        s2[y] = temp;
    }
    /**
     * Utility method to place pivot in each recusion of descendingQuickSort
     * @param String[] s1
     * @param String[] s2
     * @param int left
     * @param int right
     */
    private static String medianOf3(String[] s1, String[] s2, int left, int right) {
        int center = (left+right)/2;
        if( s1[left].length() < s1[right].length() )
            swap(s1, s2, left, right);
        if( s1[left].length() < s1[center].length() )
            swap(s1, s2, left, center);
        if( s1[center].length() < s1[right].length() )
            swap(s1, s2, center, right);
    
    swap(s1, s2, center, right-1);

    return s1[right-1];
    }
    private static final int MINIMUM_SIZE = 3;
    /**
     * Recursive helper method for quicksort
     */
    private static void descendingQuickSort(String[] s1, String[] s2, int left, int right) {
        
        //base case
        if(right-left <= MINIMUM_SIZE) {
            descendingInsertionSort(s1, s2, left, right);
            return;
        }

        String pivot = medianOf3(s1, s2, left, right);
        
        int i = left;
        int j = right-1;
        for(;;) {
            while( s1[++i].length() > pivot.length() ) { }
            while( s1[--j].length() < pivot.length() ) { }

            if(i < j)
                swap(s1, s2, i, j);
            else
                break;

            swap(s1, s2, i, right-1);

            descendingQuickSort(s1, s2, left, i-1);
            descendingQuickSort(s1, s2, i, right);

        }
    }
    /**
     *  Descending Insertion Sort
     * @param String[] s1 array to be ordered by String.length()
     * @param String[] s2 array to be sorted in parallel
     */
    public static void descendingInsertionSort(String[] s1, String[] s2){
        descendingInsertionSort(s1, s2, 0, s1.length-1);
    }
    
    /**
     * Descending Insertion Sort - used to handle base case for descendingQuickSort()
     * @param String[] s1 array to be ordered by String.length()
     * @param String[] s2 array to be sorted in parallel
     * @param int left for left bound of sort
     * @param int right for left bound of sort
     * 
     */
    private static void descendingInsertionSort(String[] s1, String[] s2, int left, int right) {
        if(s1.length != s2.length ) {
            System.out.println("Alphabets do not match! Could not sort");
            return;
        }
        String temp1;
        String temp2;
        
        for(int i=left+1;i<=right;i++){
            temp1 = s1[i];
            temp2 = s2[i];
            int j;
            for( j=i; j > left && temp1.length() > s1[j-1].length() ; j--) {
                s1[j] = s1[j-1];
                s2[j] = s2[j-1];
            }
                s1[j] = temp1;
                s2[j] = temp2;
            
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
    //descendingSelectionSort(toArray, fromArray);
    //descendingQuickSort(fromArray, toArray);
    descendingInsertionSort(fromArray, toArray);
    System.out.println("***************Sorted Alphabets***************");
    language.print();
    
    }
}