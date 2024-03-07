import sys
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\16-12-2023\\input.txt"
example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\16-12-2023\\example.txt"
grid = []
sys.setrecursionlimit(100000)
f = open(inputText,'r')
for line in f:
    grid.append(line.rstrip('\r\n'))
f.close()
#beams = []
#beams.append([((0,0),(0,1))])
seen = set()


def getNextDirections(row,col,rowDir,colDir):
    c = grid[row][col]
    directions = []
    if c == '.':
        directions.append((rowDir,colDir))
    elif c == '/':
        directions.append((-colDir,-rowDir))
    elif c == '\\':
        directions.append((colDir,rowDir))
    elif c == '|':
        if colDir == 0:
            directions.append((rowDir,colDir))
        else:
            directions.append((-1,0))
            directions.append((1,0))
    elif c == '-':
        if rowDir == 0:
            directions.append((rowDir,colDir))
        else:
            directions.append((0,-1))
            directions.append((0,1))
    return directions

def checkInsideGrid(row,col):
    if row < 0 or col < 0 or row >= len(grid) or col >= len(grid[0]):
        return False
    return True

def ray(row,col,rowDir,colDir):
    if row < 0 or col < 0 or row >= len(grid) or col >= len(grid[0]) or (row,col,rowDir,colDir) in seen:
        return
    seen.add((row,col,rowDir,colDir))
    if grid[row][col] == "/":              ray(row-colDir, col-rowDir, -colDir, -rowDir)
    elif grid[row][col] == "\\":           ray(row+colDir, col+rowDir, colDir, rowDir)
    elif grid[row][col] == "-" and rowDir: ray(row, col+1, 0, 1); ray(row, col-1, 0, -1)
    elif grid[row][col] == "|" and colDir: ray(row+1, col, 1, 0); ray(row-1, col, -1, 0)
    else:                                  ray(row+rowDir, col+colDir, rowDir, colDir)
    #directions = getNextDirections(row,col,rowDir,colDir)
    #for direction in directions:
    #    ray(row + direction[0],col + direction[1],direction[0],direction[1])
#seen.clear()
#ray(0,0,0,1)
#illuminated = set()
#illuminated.clear()
#for tileAndDirection in seen:
#    illuminated.add((tileAndDirection[0],tileAndDirection[1]))
def count_tiles(r, c, dr, dc):
    seen.clear()
    ray(r, c, dr, dc)
    return len({(r,c) for (r, c, _, _) in seen})
print(count_tiles(0,0,0,1))

#print(len(illuminated))
# beamsIndex = 0
# beamsCount = 1
# tilesAndDirections = set()
# tilesAndDirections.add(beams[0][0])
# while(beamsIndex < beamsCount):
#     beam = beams[beamsIndex]
#     beamIndex = 0
#     tileCount = 1
#     while(beamIndex < tileCount):
#         tileAndDirection = beam[beamIndex]
#         nexts = getNextTilesAndDirections(tileAndDirection)
#         for i in range(len(nexts)):
#             print(nexts[i])
#             if i > 0 and checkNextInsideGrid(nexts[i]) and nexts[i] not in tilesAndDirections:
#                 beams.append([nexts[i]])
#                 beamsCount += 1
#             elif i == 0 and checkNextInsideGrid(nexts[i]) and nexts[i] not in tilesAndDirections:
#                 beam.append(nexts[i])
#                 tileCount += 1
#         beamIndex += 1
#     beamsIndex += 1
# seen = set()
# for beam in beams:
#     for tileAndDirection in beam:
#         seen.add(tileAndDirection[0])

#print(len(seen))