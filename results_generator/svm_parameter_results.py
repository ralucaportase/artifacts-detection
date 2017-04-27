print(__doc__)

import numpy as np
import matplotlib.pyplot as plt
from matplotlib.colors import Normalize

class MidpointNormalize(Normalize):

    def __init__(self, vmin=None, vmax=None, midpoint=None, clip=False):
        self.midpoint = midpoint
        Normalize.__init__(self, vmin, vmax, clip)

    def __call__(self, value, clip=None):
        x, y = [self.vmin, self.midpoint, self.vmax], [0, 0.5, 1]
        return np.ma.masked_array(np.interp(value, x, y))

C_range = [0.001, 0.01, 0.1, 0.5, 1, 10, 100]
gamma_range = np.logspace(-7, -2, 6)
precisionDataset = [91.78, 87.79, 84.85, 82.01, 81.68, 79.93, 80.56, 
					87.21, 83.11, 78.61, 78.59, 79.10, 80.73, 77.96,
					50.20, 61.81, 73.41, 78.33, 79.69, 79.19, 76.08,
					10.17, 30.30, 53.06, 64.73, 66.79, 62.81, 54.03,
					10.12, 10.12, 13.53, 24.00, 28.59, 28.37, 28.31,
					10.12, 10.12, 10.12, 10.12, 11.07, 11.36, 11.30]
recallDataset = [50.46, 68.76, 80.57, 85.71, 87.05, 92.49, 94.55,
				72.87, 81.91, 87.67, 92.02, 93.01, 94.76, 95.99,
				84.41, 89.82, 92.81, 93.63, 93.93, 94.65, 92.49,
				100, 96.92, 93.53, 92.81, 92.19, 91.47, 90.85,
				100, 100, 100, 98.66, 97.64, 97.23, 97.23,
				100, 100, 100, 100, 99.90, 99.90, 99.87]

precision = np.array(precisionDataset).reshape(len(gamma_range), len(C_range))
recall = np.array(recallDataset).reshape(len(gamma_range), len(C_range))

plt.figure(figsize=(7, 5))
plt.imshow(precision, interpolation='nearest', cmap=plt.cm.GnBu,
           clim=[10,93])
		   # norm=MidpointNormalize(vmin=0.0, midpoint=0.85)
plt.ylabel('gamma')
plt.xlabel('C')
plt.colorbar()
plt.yticks(np.arange(len(gamma_range)), gamma_range, rotation=45)
plt.xticks(np.arange(len(C_range)), C_range)
plt.title('Validation precision')

plt.figure(figsize=(7, 5))
plt.imshow(recall, interpolation='nearest', cmap=plt.cm.GnBu, clim=[50,100])
plt.ylabel('gamma')
plt.xlabel('C')
plt.colorbar()
plt.yticks(np.arange(len(gamma_range)), gamma_range, rotation=45)
plt.xticks(np.arange(len(C_range)), C_range)
plt.title('Validation sensitivity')
plt.show()