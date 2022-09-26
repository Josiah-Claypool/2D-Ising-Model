import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.Random;

public class InitialIsingArray {

    ArrayList<ArrayList<Integer>> isingArray;
    double temperature;
    int[] spinIndices = new int[2];
    int width;
    double boltzmann;
    int numOfIterations;


    /**
     *   Initializes the 2d square Ising Array. Values of the array are either 1 or -1, corresponding to
     *   spin up or spin down.
     *
     * @param width   the dimension of the square 2d arraylist
     * @param type   "random" for the proper configuration, "positive" and "negative" create a uniform spin distribution
     * @param temperature   temperature of the system. magnetization occurs below the critical temp of 3.
     *                      above 3 magnetization doesn't occur. the model breaks down at the critical temperature.
     *                      a good temperature to use is 0.3
     * @param boltzmann   the boltzmann constant. here we set it to 1
     */
    public InitialIsingArray(int width, String type, double temperature, double boltzmann, int numOfIterations) {
        this.temperature = temperature;
        this.width = width;
        this.boltzmann = boltzmann;
        this.numOfIterations = numOfIterations;
        ArrayList<ArrayList<Integer>> holdingArray = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < this.width; i++) {
            ArrayList<Integer> rowArray = new ArrayList<>();
            for (int j = 0; j < this.width; j++) {
                switch (type) {
                    case "random" -> {
                        int randomNumber = random.nextBoolean() ? 1 : -1;
                        rowArray.add(randomNumber);
                    }
                    case "positive" -> rowArray.add(1);
                    case "negative" -> rowArray.add(-1);
                }
            }
            holdingArray.add(rowArray);
        }
        this.isingArray = holdingArray;
    }

    public ArrayList<ArrayList<Integer>> getIsingArray() {
        return isingArray;
    }

    public int[] getSpinIndices() {
        return spinIndices;
    }

    public int getNumOfIterations() {
        return numOfIterations;
    }

    /**
     *   Picks a random index in the 2d array and flips the value by multiplying by -1. Updates the spinIndices with
     *   the row and column indices of the flipped value.
     *
     * @param currentArray   The current Ising array that will be flipped
     * @return   The array configuration after one value has been flipped
     */

    public ArrayList<ArrayList<Integer>>  flip(ArrayList<ArrayList<Integer>> currentArray) {
        Random random = new Random();
        int randomRow = random.nextInt(this.width);
        int randomColumn = random.nextInt(this.width);
        ArrayList<ArrayList<Integer>> flippingArray = DeepCopy.deepCopy2D(currentArray);
        flippingArray.get(randomRow).set(randomColumn, -1 * flippingArray.get(randomRow).get(randomColumn));
        this.spinIndices[0] = randomRow;
        this.spinIndices[1] = randomColumn;
        return flippingArray;
    }

    /**
     *   Creates a JFrame with JPanels in a gridlayout. Value in the currentArray that are 1 are displayed as a blue
     *   color and an orange color for -1.
     * @param currentArray   The initial Ising array that will be displayed
     * @return   The JFrame that is created. This will be used later in the updateJFrame method
     */

    public JFrame initialJFrame(ArrayList<ArrayList<Integer>> currentArray) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new GridLayout(this.width, this.width));
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.width; j++) {
                JPanel panel = new JPanel();
                if (currentArray.get(i).get(j) == 1) {
                    panel.setBackground((new Color(100, 100, 200)));
                } else {
                    panel.setBackground(new Color(240, 150, 75));
                }
                frame.add(panel);
            }
        }
        frame.setVisible(true);
        return frame;
    }

    /**
     *   Updates the JFrame's JPanel with the color corresponding to the spin that is flipped
     *
     * @param frame   The JFrame that will be updated
     * @return   The updated JFrame
     */

    public JFrame updateJFrame(JFrame frame) {
        int spinPanel = (this.spinIndices[0] * this.width) + this.spinIndices[1]; // gets location of JPanel
        Component comp = frame.getContentPane().getComponent(spinPanel);  // the JPanel
        int currentValue = this.isingArray.get(spinIndices[0]).get(spinIndices[1]);
        if (currentValue == 1) {
            comp.setBackground((new Color(100, 100, 200)));  // blue
        } else {
            comp.setBackground(new Color(240, 150, 75));  // orange
        }
        frame.repaint();
        frame.setVisible(true);
        return frame;
    }

    /**
     * The magnetization is the mean of the values of the array. A uniform distribution will have a magnetization
     * of 1 or -1. The most disordered configuration, that of a checkerboard, will have a magnetization of 0.
     *
     * @param currentArray   The current Ising configuration
     * @return   The magnetization of the system
     */

    public double magnetization(ArrayList<ArrayList<Integer>> currentArray) {
        double mag = 0;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.width; j++) {
                mag += currentArray.get(i).get(j);
            }
        }
        mag /= (this.width * this.width);
        return mag;
    }

    /**
     *
     * Calculates and returns the energy from the designated value and its nearest neighbors. The nearest neighbors are
     *  summed and then multiplied by negative the value of the designated. This will be used to determine if the new
     *  configuration will have lower energy after the flip.
     *
     * @param currentArray   The current Ising array
     * @param flippedIndices   Array that has the indices of the flipped value
     * @return   The energy from the current value and its nearest neighbors
     */

    public double calcEnergy(ArrayList<ArrayList<Integer>> currentArray, int[] flippedIndices) {
        int x = flippedIndices[0];
        int y = flippedIndices[1];
        double energy = 0;
        energy += currentArray.get((x + 1) % this.width).get(y) + currentArray.get(x).get((y + 1) % this.width);
        if (x == 0) {
            energy += currentArray.get(this.width - 1).get(y);
        } else {
            energy += currentArray.get(x - 1).get(y);
        }
        if (y == 0) {
            energy += currentArray.get(x).get(this.width - 1);
        } else {
            energy += currentArray.get(x).get(y - 1);
        }
        energy *= -1 * currentArray.get(x).get(y);
        return energy;
    }


    /**
     * Calculates the difference in energy between the two energies
     *
     * @param energyInitial  The energy of the system before the flip
     * @param energyFinal   The energy of the system after the flip
     * @return   The difference between the energies of the two systems
     */
    public double calcEnergyDiff(double energyInitial, double energyFinal) {
        double energyDiff = energyFinal - energyInitial;
        return energyDiff;
    }

    /**
     * The primary method of the class that goes through the process. The current configuration is passed into the
     * flip() method, which returns another arraylist with a flipped value. The energy of the system before and after
     * the flip is calculated with calcEnergy(). The difference between them is returned from calcEnergyDiff(). If the
     * energy of the new system is less or equal after the spin the new configuration is accepted and passed onto
     * this.isingArray. If the value of the system is greater after the flip, a random double between 0 and 1 is
     * compared to Math.exp(-energyDifference/(this.boltzmann * this.temperature)). If the random double is less than
     * this value, the new flipped configuration is accepted. Otherwise, the old configuration is retained - the flipped
     * configuration is rejected.
     *
     * @param currentConfig   The current Ising arraylist
     */
    public void flipEvent(ArrayList<ArrayList<Integer>> currentConfig) {
        Random random = new Random();
        ArrayList<ArrayList<Integer>> flippedConfig = flip(currentConfig);
        double preFlipEnergy = calcEnergy(currentConfig, this.spinIndices); // energy before flip
        double postFlipEnergy = calcEnergy(flippedConfig, this.spinIndices); // energy after flip
        double energyDifference = calcEnergyDiff(preFlipEnergy, postFlipEnergy); // difference in energy
        if (energyDifference <= 0) {
            this.isingArray = flippedConfig; // accept the flip if energy is less after flip
        } else if (energyDifference > 0) {
            double probability = Math.exp(-energyDifference/(this.boltzmann * this.temperature));
            double randomValue = random.nextDouble();
            if (randomValue < probability) {
                this.isingArray = flippedConfig; // accept the flip if the random number is less than P
            }
        }
    }

    /**
     * Calculates the entire energy of the system. This is done by going through each value in the arraylist and adding
     * the values of its nearest neighbors and multiplying by the current value. This is then added to the energy sum
     * and repeated for each element of the arraylist.
     * @return   The total energy of the entire system
     */
    public double totalEnergy(ArrayList<ArrayList<Integer>> currentArray) {
        double energySum = 0;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.width; y++) {
                double energy = 0;
                // modulo is used below for the periodic boundary condition
                energy += currentArray.get((x + 1) % this.width).get(y) + currentArray.get(x).get((y + 1) % this.width);
                if (x == 0) {
                    energy += currentArray.get(this.width - 1).get(y); // periodic boundary condition
                } else {
                    energy += currentArray.get(x - 1).get(y);
                }
                if (y == 0) {
                    energy += currentArray.get(x).get(this.width - 1); // periodic boundary condition
                } else {
                    energy += currentArray.get(x).get(y - 1);
                }
                energy *= currentArray.get(x).get(y); // multiply sum of neighbors by current
                energySum += energy;
            }
        }
        energySum /= -2; // half the energy because of double counting
        return energySum;
    }
}
