import java.util.ArrayList;

public class DeepCopy {

    /**
     * Creates a deep copy of a 2D arraylist of integers to avoid accidentally changing arraylist with shallow copies
     *
     * @param theArray The original arraylist that will be deep copied
     * @return A deep copy of the submitted arraylist
     */

    public static ArrayList<ArrayList<Integer>> deepCopy2D(ArrayList<ArrayList<Integer>> theArray) {
        ArrayList<ArrayList<Integer>> fullClone = new ArrayList<>();
        int width = theArray.get(0).size();

        for (int i = 0; i < width; i++) {
            ArrayList<Integer> rowClone = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                rowClone.add(theArray.get(i).get(j));
            }
            fullClone.add(rowClone);
        }
        return fullClone;
    }
}
