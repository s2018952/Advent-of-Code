def applyBlink(inputLine):
    output = []
    for num in inputLine:
        if num == 0:
            output.append(1)
        elif len(str(num)) % 2 == 0:
            output.append(int(str(num)[:len(str(num)) // 2]))
            output.append(int(str(num)[len(str(num)) // 2 :]))
        else:
            output.append(num * 2024)
    return output

example = "example.txt"
inputFile = "input.txt"

f = open(inputFile,'r')
inputLine = []
for line in f:
    inputLine = [int(x) for x in line.strip().split()]

number_iterations = 25

output = inputLine
for i in range(number_iterations):
    output = applyBlink(output)
print(len(output))