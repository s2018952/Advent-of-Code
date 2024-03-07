from shapely import Polygon,Point
example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\18-12-2023\\example.txt"
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\18-12-2023\\input.txt"

instructions = []
lines = open(inputText).read().strip().split('\n')
perimeter = 0
for line in lines:
    hexInst = line.split(' ')[2].split('(')[1].split(')')[0]
    direc = hexInst[-1]
    dist = int(hexInst[1:6],16)
    instructions.append((direc,dist))
    perimeter += dist

points = []
points.append((0,0))
directions = {}
directions["3"] = (0,1)
directions["1"] = (0,-1)
directions["0"] = (1,0)
directions["2"] = (-1,0)
for instruction in instructions:
    prev = points[-1]
    next = (prev[0] + (instruction[1])*directions[instruction[0]][0],prev[1] + (instruction[1])*directions[instruction[0]][1])
    points.append(next)
if points[-1] == (0,0):
    points = points[:len(points) - 1]
poly = Polygon(points)
area = poly.area + (perimeter/2) + 1
print(area)