import pandas as pd
import matplotlib.pyplot as plt

plt.rcParams['figure.dpi'] = 300

df = pd.read_csv ('ising.csv', 'r', names=['Both'])


df[['Magnetization', 'Energy']] = df['Both'].str.split(',', n=1, expand=True)
df.drop(['Both'], axis=1, inplace=True)

df['Magnetization'] = round(df['Magnetization'].astype(float), 3)
df['Energy'] = df['Energy'].astype(float)


mag_list = df['Magnetization'].values
energy_list = df['Energy'].values
xvalues = range(1, len(mag_list) + 1)

fig, (ax1, ax2) = plt.subplots(2, 1)
fig.suptitle('Magnetization and Energy Over Time')

ax1.plot(xvalues, mag_list)
ax1.set_ylabel("Magnetization")

ax2.plot(xvalues, energy_list)
ax2.set_ylabel("Energy")
ax2.set_xlabel("Iterations")


