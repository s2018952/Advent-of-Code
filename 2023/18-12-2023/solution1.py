from shapely import Polygon,Point
example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\18-12-2023\\example.txt"
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\18-12-2023\\input.txt"

instructions = []
lines = open(inputText).read().strip().split('\n')
perimeter = 0
for line in lines:
    instructions.append((line.split(' ')[0],int(line.split(' ')[1]),line.split(' ')[2].split('(')[1].split(')')[0]))
    perimeter += int(line.split(' ')[1])

points = []
points.append((0,0))
directions = {}
directions["U"] = (0,1)
directions["D"] = (0,-1)
directions["R"] = (1,0)
directions["L"] = (-1,0)
for instruction in instructions:
    prev = points[-1]
    next = (prev[0] + (instruction[1])*directions[instruction[0]][0],prev[1] + (instruction[1])*directions[instruction[0]][1])
    points.append(next)
if points[-1] == (0,0):
    points = points[:len(points) - 1]
#for point in points:
#    print(point)
#print(len(points))
#print(perimeter/2 - 1)
poly = Polygon(points)
area = poly.area + (perimeter/2) + 1
print(area)
