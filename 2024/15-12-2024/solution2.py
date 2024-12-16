
def moveRobotBoxesVertically(grid,robotPosition,moveVerticalDir,boxLHSIndices):
    for i in range(len(boxLHSIndices) - 1,-1,-1):
        row,col = boxLHSIndices[i]
        grid[row + moveVerticalDir][col] = '['
        grid[row + moveVerticalDir][col + 1] = ']'
        grid[row][col] = '.'
        grid[row][col + 1] = '.'
    grid[robotPosition[0]][robotPosition[1]] = '.'
    robotPosition[0] += moveVerticalDir
    grid[robotPosition[0]][robotPosition[1]] = '@'
    return robotPosition
def moveRobotBoxesHorizontally(grid,robotPosition,moveHorizontalDir,freeColIndex):
    prev = '.'
    row = robotPosition[0]
    for col in range(robotPosition[1],freeColIndex + moveHorizontalDir,moveHorizontalDir):
        temp = grid[row][col]
        grid[row][col] = prev
        prev = temp
    robotPosition[1] += moveHorizontalDir
    return robotPosition
def computeGridAfterMove(grid,robotPosition,moveDictionary,move):
    # Adjust grid after move and return position of robot after move
    moveVerticalDir, moveHorizontalDir = moveDictionary[move]
    # check case where robot is trying to directly move at wall so then no change in grid and robot position
    if grid[robotPosition[0] + moveVerticalDir][robotPosition[1] + moveHorizontalDir] == '#':
        return robotPosition
    # check case where robot moves into free space
    elif grid[robotPosition[0] + moveVerticalDir][robotPosition[1] + moveHorizontalDir] == '.':
        grid[robotPosition[0]][robotPosition[1]] = '.'
        robotPosition[0] += moveVerticalDir
        robotPosition[1] += moveHorizontalDir
        grid[robotPosition[0]][robotPosition[1]] = '@'
        return robotPosition
    # check case where robot is pushing against box #TODO
    else:
        # do horizontal box moving case first
        if moveVerticalDir == 0:
            # need to identify first non-box in horizontal direction
            row = robotPosition[0]
            col = robotPosition[1] + moveHorizontalDir
            while grid[row][col] != '.' and grid[row][col] != '#':
                col += moveHorizontalDir
            nonBox = (grid[row][col],row,col)
            if nonBox[0] == '#':
                return robotPosition
            else:
                robotPosition = moveRobotBoxesHorizontally(grid,robotPosition,moveHorizontalDir,nonBox[2])
                return robotPosition
        else:
            # need to identify LHS indices of boxes robot trying to push starting from box closest to robot to those furthest away
            boxLHSIndices = []
            prevRowIndices = []
            currRowIndices = []
            if grid[robotPosition[0] + moveVerticalDir][robotPosition[1]] == '[':
                prevRowIndices.append((robotPosition[0] + moveVerticalDir,robotPosition[1]))
            else:
                prevRowIndices.append((robotPosition[0] + moveVerticalDir,robotPosition[1] - 1))
            while len(prevRowIndices) > 0:
                for indices in prevRowIndices:
                    if grid[indices[0] + moveVerticalDir][indices[1]] == '#' or grid[indices[0] + moveVerticalDir][indices[1] + 1] == '#':
                        return robotPosition
                    for j in range(-1,2,1):
                        if grid[indices[0] + moveVerticalDir][indices[1] + j] == '[':
                            newIndices = (indices[0] + moveVerticalDir,indices[1] + j)
                            if newIndices not in currRowIndices:
                                currRowIndices.append(newIndices)
                    boxLHSIndices.append(indices)
                prevRowIndices = currRowIndices
                currRowIndices = []
            robotPosition = moveRobotBoxesVertically(grid,robotPosition,moveVerticalDir,boxLHSIndices)
            return robotPosition


def getBoxGPS(row,col):
    return 100*row + col

def getGPSSum(grid):
    total = 0
    for row in range(len(grid)):
        for col in range(len(grid[0])):
            if grid[row][col] == '[':
                total += getBoxGPS(row,col)
    return total


example1 = "example1.txt"
example2 = "example2.txt"
inputFile = "input.txt"

grid = []
robotPosition = None
moves = ""
isMovement = False
f = open(inputFile,'r')
rowIndex = 0
for line in f:
    if not line.strip():
        isMovement = True
        continue
    if not isMovement:
        lineChars = []
        for j in range(len(line) - 1):
            c = line[j]
            if c == '#':
                lineChars.append('#')
                lineChars.append('#')
            elif c == 'O':
                lineChars.append('[')
                lineChars.append(']')
            elif c == '.':
                lineChars.append('.')
                lineChars.append('.')
            elif c == '@':
                lineChars.append('@')
                lineChars.append('.')
                robotPosition = [rowIndex,len(lineChars) - 2]
        grid.append(lineChars)
        rowIndex += 1
    else:
        moves += line.strip()
moveDictionary = {}
moveDictionary['^'] = (-1,0)
moveDictionary['v'] = (1,0)
moveDictionary['<'] = (0,-1)
moveDictionary['>'] = (0,1)

for move in moves:
    robotPosition = computeGridAfterMove(grid,robotPosition,moveDictionary,move)
output = getGPSSum(grid)
print(output)