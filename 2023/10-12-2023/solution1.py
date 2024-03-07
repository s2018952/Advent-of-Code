
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\10-12-2023\\input.txt"
example1 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\10-12-2023\\example1.txt"
example2 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\10-12-2023\\example2.txt"
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

max_dist = 0
for key in distances:
    max_dist = max(max_dist,distances[key])
print(max_dist)
