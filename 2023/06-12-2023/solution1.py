import math
output = 1
inputText = "input.txt"
example = "example.txt"
records = {}
f = open(inputText,'r')
lineNum = 0
timesStrs = []
distStrs = []
for line in f:
    if lineNum == 0:
        timesStrs = line.split(":")[1].strip().split(" ")
        timesStrs = [int(x) for x in timesStrs if x]
        lineNum += 1
    else:
        distStrs = line.split(":")[1].strip().split(" ")
        distStrs = [int(x) for x in distStrs if x]
f.close()
for i in range(len(timesStrs)):
    records[timesStrs[i]] = distStrs[i]

for time in records.keys():
    dist = records[time]
    ways = 0
    for i in range(time + 1):
        if ((time - i) * i) > dist:
            ways += 1
    output *= ways
    # determinant = time * time - 4 * dist
    # if (determinant < 0):
    #     output *= 0
    # else:
    #     lowestZeroPointTime = (time - math.sqrt(determinant))/2
    #     largestZeroPointTime = (time + math.sqrt(determinant))/2
    #     if time <= lowestZeroPointTime:
    #         output *= 0
    #     elif time >= largestZeroPointTime and lowestZeroPointTime >= 0:
    #         output *= (math.floor(largestZeroPointTime) - math.ceil(lowestZeroPointTime))
    #     elif time >= largestZeroPointTime and lowestZeroPointTime < 0:
    #         output *= math.floor(largestZeroPointTime)
    #     else:
    #         if lowestZeroPointTime >= 0:
    #             output *= (time - math.ceil(lowestZeroPointTime))
    #         else:
    #             output *= time
print(output)
