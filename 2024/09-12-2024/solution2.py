
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
    newLayout = [x for x in initalLayout]
    freeSpaceStart = 0
    while newLayout[freeSpaceStart] != '.':
        freeSpaceStart += 1
    freeSpaceEnd = freeSpaceStart + 1
    while newLayout[freeSpaceEnd] == '.':
        freeSpaceEnd += 1
    rightFileStart = len(initalLayout) - 1
    while initalLayout[rightFileStart] == '.':
        rightFileStart -= 1
    rightFileEnd = rightFileStart + 1
    rightFile = initalLayout[rightFileStart]
    while initalLayout[rightFileStart] == rightFile:
        rightFileStart -= 1
    rightFileStart += 1
    while rightFile != '0':
        while freeSpaceEnd - freeSpaceStart < rightFileEnd - rightFileStart and freeSpaceEnd <= rightFileStart:
            # update free space indices
            freeSpaceStart = freeSpaceEnd
            while freeSpaceStart < rightFileStart and newLayout[freeSpaceStart] != '.':
                freeSpaceStart += 1
            freeSpaceEnd = freeSpaceStart + 1
            while freeSpaceEnd <= rightFileStart and newLayout[freeSpaceEnd] == '.':
                freeSpaceEnd += 1
        if freeSpaceEnd - freeSpaceStart >= rightFileEnd - rightFileStart and freeSpaceEnd <= rightFileStart:
            # switch
            for i in range(rightFileEnd - rightFileStart):
                # switch newLayout[freeSpaceStart + i] with newLayout[rightfilestart + i]
                temp = newLayout[freeSpaceStart + i]
                newLayout[freeSpaceStart + i] = newLayout[rightFileStart + i]
                newLayout[rightFileStart + i] = temp
        # reset free space indices
        freeSpaceStart = 0
        while newLayout[freeSpaceStart] != '.':
            freeSpaceStart += 1
        freeSpaceEnd = freeSpaceStart + 1
        while freeSpaceEnd < len(newLayout) and newLayout[freeSpaceEnd] == '.':
            freeSpaceEnd += 1
        #  move to next file
        rightFileStart -= 1
        while initalLayout[rightFileStart] == '.':
            rightFileStart -= 1
        rightFileEnd = rightFileStart + 1
        rightFile = initalLayout[rightFileStart]
        while rightFileStart >= 0 and initalLayout[rightFileStart] == rightFile:
            rightFileStart -= 1
        rightFileStart += 1
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