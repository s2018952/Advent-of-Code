inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\12-12-2023\\input.txt"
example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\12-12-2023\\example.txt"
springs = []
groups = []
f = open(inputText,'r')
for line in f:
    springs.append(line.split(' ')[0])
    numberChars = line.split(' ')[1].split(',')
    numbers = [int(num) for num in numberChars]
    groups.append(numbers)
f.close()

def getGroup(string):
    nums = []
    consec = 0
    for i in range(len(string)):
        if string[i] == '#':
            consec += 1
        else:
            if consec != 0:
                nums.append(consec)
                consec = 0
    if consec != 0:
        nums.append(consec)
    return nums

def doGroupsMatch(string,group):
    strGroup = getGroup(string)
    return strGroup == group

def generatePossibleStrings(string):
    possibles = [""]
    for c in string:
        if c == '.' or c == '#':
            for i in range(len(possibles)):
                possibles[i] += c
        else:
            temps = []
            for possible in possibles:
                possible1 = possible + '.'
                possible2 = possible + '#'
                temps.append(possible1)
                temps.append(possible2)
            possibles = temps
    return possibles

output = 0
for i in range(len(springs)):
    possibles = generatePossibleStrings(springs[i])
    for possible in possibles:
        if doGroupsMatch(possible,groups[i]):
            output += 1

print(output)