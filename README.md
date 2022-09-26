# 2D-Ising-Model

Below the Curie temperature $T_C$ ferromagnetic materials will spontaneously magnetize without the presence of a magnetic field as the magnetic moments of the atoms align naturally over time. This program is a 2D Ising Model that uses a Monte Carlo method to simulate the thermodynamics of the system.

The energy of the configuration is:

$$H(\sigma) =  -J\sum_{i j}^{N} \sigma_i \sigma_{j} -\mu \sum_{i}h_{j}\sigma_{j}$$. 

Where $\sigma$ is the spin and $\sigma_i$ and $\sigma_j$ are nearest neighbors. This model assumes the external magnetic field is 0. The energy then reduces to:

$$H(\sigma) =  -J\sum_{i j}^{N} \sigma_i \sigma_{j}$$

The magnetization is the average of the spin values:


$$M = \frac{1}{N}\sum_i \sigma_i$$



This program follows these steps to simulate the gradual transition to magnetization:

**1.** A 2D square arraylist with random values of 1 and -1 is created according to desired size. These values represent the spin up and spin down. 

**2.** To visually represent this grid, a JFrame is displayed with panels of blue and orange corresponding to 1 and -1, respectively. 

**3.**  A value in the arraylist is chosen at random and has its sign flipped. This represents the flip in spin.

**4.**  The energy of the old and new configuration is calculated and compared. $\Delta E = E_2 - E_1$ 
If the energy of the new configuration is less or equal to the old, the new configuration is favorable and is accepted. 

**5.**  If the energy of the new configuration is greater, a random number between 0 and 1 is generated. This random number is then compared to $e^{-\Delta E/k_B T_C}$ . If the random number is less the new configuration is accepted. Otherwise, the system is reverted to before the flip.

**6.**  The JFrame is updated in real time as an animation.

**7.**  Returns to step **3** until either the amount of desired iterations is reached or the system reaches uniform spin.

The total energy and average magnetization of the system is stored and exported as a csv file (***ising.csv***). Run the ***ising.py*** file to generate plots of these quantities if desired. 

-------

[Here is a video for a 50x50 grid.](https://www.youtube.com/watch?v=5gmFp_87Nh8) Due to the large size of the array, this video doesn't run long enough for the system to reach uniformity.



Below is a plot for a 21x21 run. The system completes with a uniform spin down configuration as can be seen by the -1 magnetization. 

![](https://i.imgur.com/m55lS9N.png)

Here a 20x20 run finishes with uniform spin up.

![](https://i.imgur.com/ysLkGq5.png)

