import numpy as np
import math
from scipy import linalg

def getCost(a_tuple, b_tuple, target_tuple):
    # set up 2x2 matrix M = [[a_x b_x] [a_y b_y]] and target vector t = [t_x t_y]
    epsilon = 1e-4
    M = np.array([[a_tuple[0], b_tuple[0]], [a_tuple[1], b_tuple[1]]])
    t = np.array([target_tuple[0],target_tuple[1]])
    if np.linalg.det(M) != 0:
        x = linalg.solve(M,t)
        if all(i <= 100 and i >= 0 and abs(round(i) - i) < epsilon for i in list(x)):
            return 3*x[0] + x[1]
    return 0

example = "example.txt"
inputFile = "input.txt"

a_tuple = None
b_tuple = None
target_tuple = None
a_cost = 3
b_cost = 1
machines = []
f = open(inputFile,'r')
for line in f:
    if not line.strip():
        machines.append((a_tuple,b_tuple,target_tuple))
    if "Button A" in line:
        a_x = int(line.split(':')[1].split(',')[0].strip().split('+')[1])
        a_y = int(line.split(':')[1].split(',')[1].strip().split('+')[1])
        a_tuple = (a_x,a_y)
    if "Button B" in line:
        b_x = int(line.split(':')[1].split(',')[0].strip().split('+')[1])
        b_y = int(line.split(':')[1].split(',')[1].strip().split('+')[1])
        b_tuple = (b_x,b_y)
    if "Prize" in line:
        target_x = int(line.split(':')[1].split(',')[0].strip().split('=')[1])
        target_y = int(line.split(':')[1].split(',')[1].strip().split('=')[1])
        target_tuple = (target_x,target_y)
machines.append((a_tuple,b_tuple,target_tuple))

output = 0
for machine in machines:
    a_tuple, b_tuple, target_tuple = machine
    output += getCost(a_tuple,b_tuple,target_tuple)
print(output)