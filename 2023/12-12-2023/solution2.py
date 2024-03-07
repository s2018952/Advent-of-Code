import functools
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\12-12-2023\\input.txt"
example = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\12-12-2023\\example.txt"
springs = []
groups = []
f = open(inputText,'r')
for line in f:
    single = line.split(' ')[0]
    s = ""
    for i in range(4):
        s += single + '?'
    s += single
    springs.append(s)
    numberChars = line.split(' ')[1].split(',')
    nums = [int(num) for num in numberChars]
    numbers = []
    for i in range(5):
        for num in nums:
            numbers.append(num)
    groups.append(tuple(numbers))
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

def isGroupConsistent(group_to_check,group):
    if len(group_to_check) > len(group):
        return False
    else:
        for i in range(len(group_to_check)):
            if i < len(group_to_check) - 1 and group_to_check[i] != group[i]:
                return False
            elif i == len(group_to_check) - 1 and group_to_check[i] > group[i]:
                return False
    return True

def generatePossibleStrings(strings,ch,group):
    possibles = []
    for string in strings:
        if ch == '.' or ch == '#':
            s = string + ch
            g = getGroup(s)
            if isGroupConsistent(g,group):
                possibles.append(s)
        else:
            s1 = string + '.'
            s2 = string + '#'
            g1 = getGroup(s1)
            g2 = getGroup(s2)
            if isGroupConsistent(g1,group):
                possibles.append(s1)
            if isGroupConsistent(g2,group):
                possibles.append(s2)
    if len(strings) == 0 and ch != '?':
        s = "" + ch
        if isGroupConsistent(getGroup(s),group):
            possibles.append(s)
    elif len(strings) == 0 and ch == '?':
        s1 = "."
        s2 = "#"
        if isGroupConsistent(getGroup(s1),group):
            possibles.append(s1)
        if isGroupConsistent(getGroup(s2),group):
            possibles.append(s2)
    return possibles

@functools.lru_cache(maxsize=None)
def getCounts(string,size,group):
    if len(group) == 0:
        if all(c in '.?' for c in string):
            return 1
        return 0
    firstRunSize = group[0]
    rest = group[1:]
    count = 0
    for i in range(size - firstRunSize - sum(rest) - len(rest) + 1):
        before = '.' * i + '#' * firstRunSize + '.'
        if all(c1 == c2 or c1 == '?' for c1,c2 in zip(string,before)):
            count += getCounts(string[len(before):],size - firstRunSize - i - 1,rest)
    return count

output = 0
for i in range(len(springs)):
    output += getCounts(springs[i],len(springs[i]),groups[i])
    print("Line: " + str(i + 1))
    #possibles = generatePossibleStrings(springs[i])
    #for possible in possibles:
    #    if doGroupsMatch(possible,groups[i]):
    #        output += 1

print(output)