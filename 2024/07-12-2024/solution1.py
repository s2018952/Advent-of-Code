def generateAllOperationOrderings(n,s,i,permutations):
    if i == n:
        permutations.append(s)
        return
    s0 = s + '+'
    generateAllOperationOrderings(n,s0,i + 1, permutations)
    s1 = s + '*'
    generateAllOperationOrderings(n,s1,i + 1,permutations)

def checkValid(equation):
    left, right = equation
    if len(right) == 1 and left == right[0]:
        return true
    permutations = []
    s = ""
    generateAllOperationOrderings(len(right) - 1,s,0,permutations)
    for permutation in permutations:
        total = right[0]
        for i in range(len(permutation)):
            if permutation[i] == '+':
                total += right[i + 1]
            else:
                total *= right[i + 1]
        if total == left:
            return True
    return False

example = "example.txt"
inputFile = "input.txt"

equations = []
f = open(inputFile,'r')
for line in f:
    line = line.strip()
    left = int(line.split(':')[0])
    right = [int(x) for x in line.split(':')[1].strip().split()]
    equations.append((left,right))
output = 0
for equation in equations:
    if checkValid(equation):
        output += equation[0]
print(output)