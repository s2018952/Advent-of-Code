from sympy.solvers.solveset import nonlinsolve
from sympy import Symbol

positions = []
velocities = []
f = open('input.txt','r')
for line in f:
    line = line.strip()
    pos = line.split(' @ ')[0].split(', ')
    posTuple = (int(pos[0]),int(pos[1]),int(pos[2]))
    vels = line.split(' @ ')[1].split(', ')
    velTuple = (int(vels[0]),int(vels[1]),int(vels[2]))
    if len(positions) == 3:
        break
    elif len(positions) == 0:
        positions.append(posTuple)
        velocities.append(velTuple)
    else:
        shouldContinue = False
        for vel in velocities:
            factor = velTuple[0] / vel[0]
            if factor*vel[1] == velTuple[1] and factor*vel[2] == velTuple[2]:
                shouldContinue = True
        if shouldContinue:
            continue
        positions.append(posTuple)
        velocities.append(velTuple)
f.close()

equations = []
x = Symbol('x')
y = Symbol('y')
z = Symbol('z')
u = Symbol('u')
v = Symbol('v')
w = Symbol('w')
symbols = [x,y,z,u,v,w]

for i in range(len(positions)):
    x1,y1,z1 = positions[i]
    vx1,vy1,vz1 = velocities[i]
    equations.append(((x1 - x)/(u-vx1)) - ((y1 - y)/(v-vy1)))
    equations.append(((x1 - x)/(u-vx1)) - ((z1 - z)/(w-vz1)))
    equations.append(((y1 - y)/(v-vy1)) - ((z1 - z)/(w-vz1)))
#print(equations)
answers = nonlinsolve(equations,symbols)
for answer in answers:
    print(answer[0] + answer[1] + answer[2])
