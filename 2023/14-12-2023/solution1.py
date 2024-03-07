inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\14-12-2023\\input.txt"
example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\14-12-2023\\example.txt"
rows = []
f = open(inputText,'r')
for line in f:
    rows.append(line.rstrip('\r\n'))
f.close()
def getLoadForWestTilt(row):
    load = 0
    roundRockCount = 0
    nextLoadToAdd = len(row)
    for i in range(len(row)):
        if row[i] == '#':
            for j in range(roundRockCount):
                load += nextLoadToAdd
                nextLoadToAdd -= 1
            roundRockCount = 0
            nextLoadToAdd = len(row) - i - 1
        elif row[i] == 'O':
            roundRockCount += 1
    if roundRockCount > 0:
        for j in range(roundRockCount):
            load += nextLoadToAdd
            nextLoadToAdd -= 1
    return load

def getColumns(lines):
    columns = []
    for j in range(len(lines[0])):
        s = []
        for i in range(len(lines)):
            s += lines[i][j]
        columns.append(s)
    return columns

output = 0
columns = getColumns(rows)
for column in columns:
    output += getLoadForWestTilt(column)
print(output)