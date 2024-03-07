example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\22-12-2023\\example.txt"
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\22-12-2023\\input.txt"

bricks = []
xmin = 100000
ymin = 100000
xmax = 0
ymax = 0
f = open(inputText,'r')
for line in f:
    line = line.strip()
    first = [int(x) for x in line.split('~')[0].split(',')]
    second = [int(x) for x in line.split('~')[1].split(',')]
    bricks.append(((first[0],first[1],first[2]),(second[0],second[1],second[2])))
    xmin = min(first[0],second[0],xmin)
    xmax = max(first[0],second[0],xmax)
    ymin = min(first[1],second[1],ymin)
    ymax = max(first[1],second[1],ymax)
    assert(first[2] <= second[2])
f.close()
bricks.sort(key=lambda a: a[0][2])
positions_to_top = {}
for x in range(xmin,xmax + 1,1):
    for y in range(ymin,ymax + 1,1):
        positions_to_top[(x,y)] = (0,-1)

supports = {}
for i in range(len(bricks)):
    (x0,y0,z0),(x1,y1,z1) = bricks[i]
    supports[i] = set()
    if x0 != x1:
        maxHeight = 0
        for x in range(min(x0,x1),max(x0,x1) + 1,1):
            maxHeight = max(maxHeight,positions_to_top[(x,y0)][0])
        for x in range(min(x0,x1),max(x0,x1) + 1,1):
            if positions_to_top[(x,y0)][0] == maxHeight and positions_to_top[(x,y0)][1] != -1:
                supports[i].add(positions_to_top[(x,y0)][1])
            positions_to_top[(x,y0)] = (maxHeight + 1,i)
    elif y0 != y1:
        maxHeight = 0
        for y in range(min(y0,y1),max(y0,y1) + 1,1):
            maxHeight = max(maxHeight,positions_to_top[(x0,y)][0])
        for y in range(min(y0,y1),max(y0,y1) + 1,1):
            if positions_to_top[(x0,y)][0] == maxHeight and positions_to_top[(x0,y)][1] != -1:
                supports[i].add(positions_to_top[(x0,y)][1])
            positions_to_top[(x0,y)] = (maxHeight + 1,i)
    else:
        maxHeight = positions_to_top[(x0,y0)][0]
        if positions_to_top[(x0,y0)][1] != -1:
            supports[i].add(positions_to_top[(x0,y0)][1])
        positions_to_top[(x0,y0)] = (maxHeight + abs(z1 - z0) + 1,i)

unsafes = set()
for index in range(len(bricks)):
    #print(supports[index])
    if len(supports[index]) == 1:
        for support in supports[index]:
            unsafes.add(support)

total = 0
for unsafe in unsafes:
    falling = set()
    falling.add(unsafe)
    changed = True
    count = len(falling)
    while (changed):
        for index in supports.keys():
            if all([x in falling for x in supports[index]]) and len(supports[index]) > 0:
                falling.add(index)
        if count == len(falling):
            changed = False
        else:
            count = len(falling)
    #print(unsafe)
    #print(falling)
    total += (len(falling) - 1)
print("Part 1:")
print(len(bricks) - len(unsafes))
print("Part 2:")
print(total)