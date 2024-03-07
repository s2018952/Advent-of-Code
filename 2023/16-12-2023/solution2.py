import sys
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\16-12-2023\\input.txt"
example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\16-12-2023\\example.txt"
grid = []
sys.setrecursionlimit(100000)
f = open(inputText,'r')
for line in f:
    grid.append(line.rstrip('\r\n'))
f.close()
seen = set()

def ray(row,col,rowDir,colDir):
    if row < 0 or col < 0 or row >= len(grid) or col >= len(grid[0]) or (row,col,rowDir,colDir) in seen:
        return
    seen.add((row,col,rowDir,colDir))
    if grid[row][col] == "/":              ray(row-colDir, col-rowDir, -colDir, -rowDir)
    elif grid[row][col] == "\\":           ray(row+colDir, col+rowDir, colDir, rowDir)
    elif grid[row][col] == "-" and rowDir: ray(row, col+1, 0, 1); ray(row, col-1, 0, -1)
    elif grid[row][col] == "|" and colDir: ray(row+1, col, 1, 0); ray(row-1, col, -1, 0)
    else:                                  ray(row+rowDir, col+colDir, rowDir, colDir)

def count_tiles(r, c, dr, dc):
    seen.clear()
    ray(r, c, dr, dc)
    return len({(r,c) for (r, c, _, _) in seen})

maxNum = -1
# Loop through top and bottom edges
for j in range(len(grid[0])):
    maxNum = max(maxNum,count_tiles(0,j,1,0))
    maxNum = max(maxNum,count_tiles(len(grid) - 1,j,-1,0))
# Loop through left and right edges
for i in range(len(grid)):
    maxNum = max(maxNum,count_tiles(i,0,0,1))
    maxNum = max(maxNum,count_tiles(i,len(grid[0]) - 1,0,-1))
print(maxNum)