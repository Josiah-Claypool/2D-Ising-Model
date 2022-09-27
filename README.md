# 2D-Ising-Model

Below the Curie temperature, $T_C$, ferromagnetic materials will spontaneously magnetize without the presence of a magnetic field as the magnetic moments of the atoms align naturally over time. This program is a 2D Ising Model that uses the Metropolis–Hastings Monte Carlo algorithm to simulate the thermodynamics of the system.

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
If the energy of the new configuration is less than the old, the new configuration is favorable and is accepted. 

**5.**  If the energy of the new configuration is greater, a random number between 0 and 1 is generated. This random number is then compared to $e^{-\Delta E/k_B T_C}$ . If the random number is less the new configuration is accepted. Otherwise, the system is reverted to before the flip.

**6.**  The JFrame is updated in real time as an animation.

**7.**  Returns to step **3** until either the amount of desired iterations is reached or the system reaches uniform spin.

The total energy and average magnetization of the system is stored and exported as a csv file (***ising.csv***). Run the ***ising.py*** file to generate plots of these quantities if desired. 

For more reading regarding the physics see the wikipedia articles on [Ising model](https://en.wikipedia.org/wiki/Ising_model), [ferromagnetism](https://en.wikipedia.org/wiki/Ferromagnetism#Curie_temperature), and [Monte Carlo methods](https://en.wikipedia.org/wiki/Monte_Carlo_method).

-------

[Here is a video of a run with a 20x20 grid.](https://www.youtube.com/watch?v=y1jBtIeOM-M)

Below is the corresponding python plot for the energy and magnetization. The system completes with a uniform spin up configuration as can be seen by the magnetization of 1.

![](https://i.imgur.com/4aA8aWs.png)



Here a run finishes with uniform spin down.

![](https://i.imgur.com/m55lS9N.png)



A run with a temperature above $T_C$, where spontaneous magnetization doesn't occur.

![](https://i.imgur.com/HyWYe2h.png)
