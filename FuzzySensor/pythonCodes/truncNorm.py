from scipy import truncnorm
import matplotlib.pyplot as plt

myclip_a = 0
myclip_b = 1
my_mean = 0.5
my_std = 0.3

a, b = (myclip_a - my_mean) / my_std, (myclip_b - my_mean) / my_std
x_range = np.linspace(-1,2,1000)
plt.plot(x_range, truncnorm.pdf(x_range, a, b, loc = my_mean, scale = my_std))
