
def countWaysToMakeDesign(design,towels,dp):
    if design in dp:
        return dp[design]
    if design == "":
        return 1
    count = 0
    for towel in towels:
        if design[:len(towel)] == towel:
            count += countWaysToMakeDesign(design[len(towel):],towels,dp)
    dp[design] = count
    return count

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
dp = {}
for design in designs:
    count += countWaysToMakeDesign(design,towels,dp)
print(count)