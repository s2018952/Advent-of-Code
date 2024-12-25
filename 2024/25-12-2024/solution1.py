
def getLockSpaceList(lock):
    output = []
    for j in range(len(lock[0])):
        for i in range(len(lock)):
            if lock[i][j] == '.':
                output.append(i - 1)
                break
    return output
def getKeySpaceList(key):
    output = []
    for j in range(len(key[0])):
        for i in range(len(key) - 1,-1,-1):
            if key[i][j] == '.':
                output.append(len(key) - i - 2)
                break
    return output
example = "example.txt"
inputFile = "input.txt"
f = open(inputFile,'r')
locks = []
keys = []
isKey = False
isLock = False
for line in f:
    if not line.strip():
        isKey = False
        isLock = False
        continue
    line = line.strip()
    if isKey:
        keys[len(keys) - 1].append(line)
        continue
    elif isLock:
        locks[len(locks) - 1].append(line)
        continue
    else:
        if "#####" in line:
            isLock = True
            newList = []
            newList.append(line)
            locks.append(newList)
            continue
        elif "....." in line:
            isKey = True
            newList = []
            newList.append(line)
            keys.append(newList)
            continue
keySpaces = [getKeySpaceList(key) for key in keys]
lockSpaces = [getLockSpaceList(lock) for lock in locks]
count = 0
for kSpace in keySpaces:
    for lSpace in lockSpaces:
        if all(x <= 5 for x in [kSpace[i] + lSpace[i] for i in range(len(kSpace))]):
            count += 1
print(count)