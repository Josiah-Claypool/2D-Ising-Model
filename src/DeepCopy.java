import java.util.ArrayList;
import java.util.Iterator;

public class DeepCopy {

    public static ArrayList<ArrayList<Integer>> deepCopy2D(ArrayList<ArrayList<Integer>> theArray) {
        ArrayList<ArrayList<Integer>> fullClone = new ArrayList<ArrayList<Integer>>();
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
