def getBlinkSize(stone,times,stored):
    if (stone,times) in stored:
        return stored[(stone,times)]
    if times == 0:
        return 1
    if stone == 0:
        size = getBlinkSize(1,times - 1, stored)
    elif len(str(stone)) % 2 == 0:
        left = int(str(stone)[:len(str(stone)) // 2])
        right = int(str(stone)[len(str(stone)) // 2 :])
        size = getBlinkSize(left,times - 1,stored) + getBlinkSize(right, times - 1, stored)
    else:
        size = getBlinkSize(stone*2024, times - 1, stored)
    if (stone,times) not in stored:
        stored[(stone,times)] = size
    return size

example = "example.txt"
inputFile = "input.txt"

f = open(inputFile,'r')
inputLine = []
for line in f:
    inputLine = [int(x) for x in line.strip().split()]

number_iterations = 75

output = 0
stored = {}
for stone in inputLine:
    output += getBlinkSize(stone,number_iterations,stored)
print(output)