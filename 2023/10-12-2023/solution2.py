from shapely.geometry import Point, Polygon
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\10-12-2023\\input.txt"
example3 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\10-12-2023\\example3.txt"
example4 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\10-12-2023\\example4.txt"
example5 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\10-12-2023\\example5.txt"
def getNextNeighbours(currentRow,currentColumn):
    returnList = []
    if lines[currentRow][currentColumn] == '|':
        returnList.append((currentRow - 1, currentColumn))
        returnList.append((currentRow + 1, currentColumn))
    elif lines[currentRow][currentColumn] == '-':
        returnList.append((currentRow, currentColumn - 1))
        returnList.append((currentRow, currentColumn + 1))
    elif lines[currentRow][currentColumn] == 'L':
        returnList.append((currentRow - 1, currentColumn))
        returnList.append((currentRow, currentColumn + 1))
    elif lines[currentRow][currentColumn] == 'J':
        returnList.append((currentRow - 1, currentColumn))
        returnList.append((currentRow, currentColumn - 1))
    elif lines[currentRow][currentColumn] == '7':
        returnList.append((currentRow + 1, currentColumn))
        returnList.append((currentRow, currentColumn - 1))
    elif lines[currentRow][currentColumn] == 'F':
        returnList.append((currentRow + 1, currentColumn))
        returnList.append((currentRow, currentColumn + 1))
    elif lines[currentRow][currentColumn] == 'S':
        possibles = []
        for i in range(max(currentRow - 1,0),min(currentRow + 2, len(lines))):
            for j in range(max(currentColumn - 1,0),min(currentColumn + 2, len(lines[0]))):
                if i == currentRow and j == currentColumn:
                    continue
                possibles.append((i,j))
        for possible in possibles:
            possibleNeighbours = getNextNeighbours(possible[0],possible[1])
            for neighbour in possibleNeighbours:
                if getSymbol(neighbour) == 'S':
                    returnList.append(possible)
                    break
    return returnList
def getSymbol(index):
    return lines[index[0]][index[1]]
lines = []
distances = {}
startIndex = (0,0)
f = open(inputText,'r')
lineIndex = 0
for line in f:
    lines.append(line)
    for i in range(len(line)):
        if line[i] == 'S':
            startIndex = (lineIndex,i)
    lineIndex += 1
f.close()
end = False
currents = [startIndex]
distances[startIndex] = 0
while not end:
    if len(currents) == 1:
        currents = getNextNeighbours(startIndex[0],startIndex[1])
        distances[currents[0]] = 1
        distances[currents[1]] = 1
    else:
        for i in range(2):
            nexts = getNextNeighbours(currents[i][0],currents[i][1])
            nextCount = 0
            for next in nexts:
                if next not in distances:
                    distances[next] = distances[currents[i]] + 1
                    nextCount += 1
                    currents[i] = next
            if nextCount == 0:
                end = True

loop = []
loop.append(startIndex)
while len(loop) < len(distances.keys()):
    nexts = getNextNeighbours(loop[-1][0],loop[-1][1])
    for next in nexts:
        if next not in loop:
            loop.append(next)
vertices = []
startNeighbours = getNextNeighbours(startIndex[0],startIndex[1])
if startNeighbours[0][0] != startNeighbours[1][0] and startNeighbours[0][1] != startNeighbours[1][1]:
    vertices.append(startIndex)
for tile in loop:
    if tile not in vertices and getSymbol(tile) != '-' and getSymbol(tile) != '|':
        vertices.append(tile)
poly = Polygon(vertices)
count = 0
for i in range(len(lines)):
    for j in range(len(lines[0])):
        point = Point(i,j)
        if poly.contains(point):
            count += 1
print(count)

