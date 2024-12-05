
def isUpdateValid(update, rules):
    for rule in rules:
        before = rule[0]
        after = rule[1]
        beforeIndex = -1
        afterIndex = -1
        for i in range(len(update)):
            if update[i] == before:
                beforeIndex = i
            if update[i] == after:
                afterIndex = i
            if beforeIndex >= 0 and afterIndex >= 0 and afterIndex < beforeIndex:
                return False
    return True

def getMiddleNumber(update):
    return update[len(update) // 2]

example = "example.txt"
inputFile = "input.txt"

f = open(inputFile,'r')
rules = []
updates = []
for line in f:
    if line.__contains__('|'):
        line = line.strip()
        rules.append([int(num) for num in line.split('|')])
    if line.__contains__(','):
        line = line.strip()
        updates.append([int(num) for num in line.split(',')])

total = 0
for update in updates:
    if isUpdateValid(update,rules):
        total += getMiddleNumber(update)
print(total)