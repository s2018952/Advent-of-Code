positions = []
velocities = []
minPos = 200000000000000
maxPos = 400000000000000
#minPos = 7
#maxPos = 27
f = open('input.txt','r')
for line in f:
    line = line.strip()
    pos = line.split(' @ ')[0].split(', ')
    positions.append((int(pos[0]),int(pos[1]),int(pos[2])))
    vels = line.split(' @ ')[1].split(', ')
    velocities.append((int(vels[0]),int(vels[1]),int(vels[2])))
f.close()

def getIntersection(pos1,vel1,pos2,vel2):
    x1,y1,z1 = pos1
    x2,y2,z2 = pos2
    vx1,vy1,vz1 = vel1
    vx2,vy2,vz2 = vel2
    m1 = vy1 / vx1
    m2 = vy2 / vx2
    if m1 == m2:
        return (0,0)
    x = (m1*x1 - y1 + y2 - m2*x2) / (m1 - m2)
    y = m1*(x - x1) + y1
    t1 = (x - x1) / vx1
    t2 = (x - x2) / vx2
    if t1 < 0 or t2 < 0:
        return (0,0)
    return (x,y)

count = 0
for i in range(len(positions)):
    for j in range(len(positions)):
        if i != j:
            x,y = getIntersection(positions[i],velocities[i],positions[j],velocities[j])
            if x >= minPos and x <= maxPos and y >= minPos and y <= maxPos:
                count += 1

print(count / 2)
