
def computeGridAfterMove(grid,robotPosition,moveDictionary,move):
    # Adjust grid after move and return position of robot after move
    moveVerticalDir, moveHorizontalDir = moveDictionary[move]
    # check case where robot is trying to directly move at wall so then no change in grid and robot position
    if grid[robotPosition[0] + moveVerticalDir][robotPosition[1] + moveHorizontalDir] == '#':
        return robotPosition
    # check case where robot moves into free space
    if grid[robotPosition[0] + moveVerticalDir][robotPosition[1] + moveHorizontalDir] == '.':
        grid[robotPosition[0]][robotPosition[1]] = '.'
        robotPosition[0] += moveVerticalDir
        robotPosition[1] += moveHorizontalDir
        grid[robotPosition[0]][robotPosition[1]] = '@'
        return robotPosition
    # check case where robot is pushing against box
    if grid[robotPosition[0] + moveVerticalDir][robotPosition[1] + moveHorizontalDir] == 'O':
        # identify nature and indices of next non-box in this direction
        row = robotPosition[0] + moveVerticalDir
        col = robotPosition[1] + moveHorizontalDir
        while grid[row][col] == 'O':
            row += moveVerticalDir
            col += moveHorizontalDir
        nonBox = (grid[row][col],row,col)
        # case where next non-box is wall so no possible movement
        if nonBox[0] == '#':
            return robotPosition
        # case where boxes can be moved to free space
        else:
            # turn free space at nonbox into a box and put robot in first box position and turn robot's old position to free space
            grid[nonBox[1]][nonBox[2]] = 'O'
            grid[robotPosition[0]][robotPosition[1]] = '.'
            robotPosition[0] += moveVerticalDir
            robotPosition[1] += moveHorizontalDir
            grid[robotPosition[0]][robotPosition[1]] = '@'
            return robotPosition

def getBoxGPS(row,col):
    return 100*row + col

def getGPSSum(grid):
    total = 0
    for row in range(len(grid)):
        for col in range(len(grid[0])):
            if grid[row][col] == 'O':
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
            lineChars.append(c)
            if c == '@':
                robotPosition = [rowIndex,j]
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