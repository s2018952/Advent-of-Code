
def isDesignPossible(design, towels):
    if design == "":
        return True
    check = False
    for towel in towels:
        if design[:len(towel)] == towel:
            check = check or isDesignPossible(design[len(towel):],towels)
    return check

example = "example.txt"
inputFile = "input.txt"
towels = []
designs = []
designLine = False
f = open(inputFile,'r')
for line in f:
    if not line.strip():
        designLine = True
        continue
    elif not designLine:
        towels = line.strip().split(", ")
    else:
        designs.append(line.strip())

count = 0
for design in designs:
    if isDesignPossible(design,towels):
        count += 1
print(count)