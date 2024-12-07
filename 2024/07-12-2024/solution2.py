def generateAllOperationOrderings(n,s,i,permutations):
    if i == n:
        permutations.append(s)
        return
    s0 = s + '+'
    generateAllOperationOrderings(n,s0,i + 1, permutations)
    s1 = s + '*'
    generateAllOperationOrderings(n,s1,i + 1,permutations)
    s2 = s + '|'
    generateAllOperationOrderings(n,s2,i+1,permutations)

def checkValid(equation):
    left, right = equation
    if len(right) == 1 and int(left) == int(right[0]):
        return true
    permutations = []
    s = ""
    generateAllOperationOrderings(len(right) - 1,s,0,permutations)
    for permutation in permutations:
        total = int(right[0])
        for i in range(len(permutation)):
            if permutation[i] == '+':
                total += int(right[i + 1])
            elif permutation[i] == '|':
                total = int(str(total) + right[i + 1])
            else:
                total *= int(right[i + 1])
        if total == int(left):
            return True
    return False

example = "example.txt"
inputFile = "input.txt"

equations = []
f = open(inputFile,'r')
for line in f:
    line = line.strip()
    left = line.split(':')[0]
    right = line.split(':')[1].strip().split()
    equations.append((left,right))
output = 0
for equation in equations:
    if checkValid(equation):
        output += int(equation[0])
print(output)