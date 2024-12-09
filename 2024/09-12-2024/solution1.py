
def getInitialLayout(diskMap):
    output = []
    for i in range(len(diskMap)):
        if i % 2 == 0:
            currentID = i // 2
            spaceTaken = int(diskMap[i])
            for i in range(spaceTaken):
                output.append(str(currentID))
        else:
            spaceFree = int(diskMap[i])
            for i in range(spaceFree):
                output.append('.')
    return output

def fillFreeSpace(initalLayout):
    newLayout = initalLayout
    start = 0
    end = len(newLayout) - 1
    while newLayout[end] == '.':
        end -= 1
    while start < end:
        if newLayout[start] == '.':
            temp = newLayout[start]
            newLayout[start] = newLayout[end]
            newLayout[end] = temp
            end -= 1
            while newLayout[end] == '.':
                end -= 1
        start += 1
    return newLayout

def getCheckSum(layout):
    total = 0
    for i in range(len(layout)):
        if layout[i] != '.':
            total += i * int(layout[i])
    return total


example = 'example.txt'
inputFile = 'input.txt'

f = open(inputFile,'r')
initial = ""
for line in f:
    initial = line.strip()

initialLayout = getInitialLayout(initial)
newLayout = fillFreeSpace(initialLayout)
checkSum = getCheckSum(newLayout)
print(checkSum)