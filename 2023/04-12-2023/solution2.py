import math
output = 0
index = 0
counts = []
for i in range(192):
    counts.append(0)
f = open("input.txt",'r')
for line in f:
    counts[index] += 1
    winningNumStrList = line.split(":")[1].split("|")[0].strip().split(" ")
    winningNumStrList[:] = [x for x in winningNumStrList if x]
    chosenNumStrList = line.split(":")[1].split("|")[1].strip().split(" ")
    chosenNumStrList[:] = [x for x in chosenNumStrList if x]
    matches = sum(i in winningNumStrList for i in chosenNumStrList)
    for i in range(matches):
        counts[index + i + 1] += counts[index]
    output += counts[index]
    index += 1
f.close()
print(output)