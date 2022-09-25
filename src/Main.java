import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException {
        InitialIsingArray isingSystem = new InitialIsingArray(7, "random", 0.3, 1);
        double mag = isingSystem.magnetization(isingSystem.getIsingArray());
        double energy = isingSystem.totalEnergy(isingSystem.getIsingArray());
        ArrayList<Double> magArray = new ArrayList<>(); // storing the magnetization values
        ArrayList<Double> energyArray = new ArrayList<>(); // storing the magnetization values
        energyArray.add(energy);
        magArray.add(mag);

        JFrame isingJFrame = isingSystem.initialJFrame(isingSystem.getIsingArray());

        int numOfIterations = 10000;
        for (int i = 1; i < numOfIterations; i++) {
            isingSystem.flipEvent(isingSystem.getIsingArray());
            isingJFrame = isingSystem.updateJFrame(isingJFrame);
            mag = isingSystem.magnetization(isingSystem.getIsingArray());
            energy = isingSystem.totalEnergy(isingSystem.getIsingArray());
            energyArray.add(energy);
            magArray.add(mag);
            System.out.println(i);
            // end simulation if system becomes uniform
            if (mag == -1.0 || mag == 1.0 ) {
                System.out.println("break");
                break;
            }
        }
        // exports the energy and magnetization to a csv, run ising.py for plots
        PrintWriter writer = new PrintWriter("ising.csv");
        ArrayList<String> row = new ArrayList<>();
        for (int i = 0; i < magArray.size(); i++) {
            row.add(String.valueOf(magArray.get(i)));
            row.add(String.valueOf(energyArray.get(i)));
            writer.println(String.join(",", row));
            row.clear();

        }
        writer.close();

    }
}
