inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\14-12-2023\\input.txt"
example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\14-12-2023\\example.txt"
grid = []
f = open(inputText,'r')
for line in f:
    grid.append(line.rstrip('\r\n'))
f.close()

def getLoad(rows):
    load = 0
    for i in range(len(rows)):
        for j in range(len(rows[0])):
            if rows[i][j] == 'O':
                load += len(rows) - i
    return load

def westShiftedRow(row):
    roundRockCount = 0
    previousCubeRock = -1
    for i in range(len(row)):
        if row[i] == '#':
            for j in range(roundRockCount):
                row[previousCubeRock + 1 + j] = 'O'
            for j in range(previousCubeRock + 1 + roundRockCount,i):
                row[j] = '.'
            previousCubeRock = i
            roundRockCount = 0
        elif row[i] == 'O':
            roundRockCount += 1
    if roundRockCount > 0:
        for j in range(roundRockCount):
            row[previousCubeRock + 1 + j] = 'O'
        for j in range(previousCubeRock + 1 + roundRockCount,len(row)):
            row[j] = '.'

def getColumns(lines):
    columns = []
    for j in range(len(lines[0])):
        s = []
        for i in range(len(lines)):
            s += lines[i][j]
        columns.append(s)
    return columns

def flipRow(row):
    for i in range(len(row)):
        if i > len(row) - 1 - i:
            break
        temp = row[i]
        row[i] = row[len(row) - 1 - i]
        row[len(row) - 1 - i] = temp

def north(grid1):
    columns = getColumns(grid1)
    for column in columns:
        westShiftedRow(column)
    grid1 = getColumns(columns)
    return grid1

def west(grid1):
    for row in grid1:
        westShiftedRow(row)
    return grid1

def south(grid1):
    columns = getColumns(grid1)
    for column in columns:
        flipRow(column)
        westShiftedRow(column)
        flipRow(column)
    grid1 = getColumns(columns)
    return grid1

def east(grid1):
    for row in grid1:
        flipRow(row)
        westShiftedRow(row)
        flipRow(row)
    return grid1

def loop(grid1):
    grid1 = north(grid1)
    grid1 = west(grid1)
    grid1 = south(grid1)
    grid1 = east(grid1)
    return grid1

gridCopy = grid
previouses = {}
previouses[0] = gridCopy
bigLoopNumber = 1000000000
loopStartKey = -1
loopNextKey = -1
for i in range(1000000000):
    gridCopy = loop(gridCopy)
    if gridCopy not in previouses.values():
        previouses[i + 1] = gridCopy
    else:
        for key in previouses.keys():
            if previouses[key] == gridCopy:
                loopStartKey = key
                loopNextKey = i + 1
                break
        break
cycleLength = loopNextKey - loopStartKey
correspondingKey = ((bigLoopNumber - loopStartKey) % cycleLength) + loopStartKey
#print(loopStartKey)
#print(loopNextKey)
#print(correspondingKey)
print(getLoad(previouses[correspondingKey]))
        

#print(getLoad(gridCopy))