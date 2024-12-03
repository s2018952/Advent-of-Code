

example = "example.txt"
inputFile = "input.txt"
f = open(inputFile,'r')
total = 0
text = ""
for line in f:
    text += line
line = text
for i in range(0,len(line) - 7,1):
    if line[i:i + 4] == "mul(":
        commaFound = False
        bracketFound = False
        continueFlag = False
        j = i + 4
        first = ""
        for k in range(j,len(line) - 3,1):
            if k >= j + 4 and not commaFound:
                continueFlag = True
                break
            if not line[k].isdigit() and line[k] != ',':
                continueFlag = True
                break
            if line[k] == ',':
                commaFound = True
                if k == j:
                    continueFlag = True
                    break
                first = line[j:k]
                j = k + 1
                break
        if continueFlag:
            continue
        second = ""
        if j >= len(line) - 1:
            continue
        for k in range(j,len(line) - 1,1):
            if k >= j + 4 and not bracketFound:
                continueFlag = True
                break
            if not line[k].isdigit() and line[k] != ')':
                continueFlag = True
                break
            if line[k] == ')':
                bracketFound = True
                if k == j:
                    continueFlag = True
                    break
                second = line[j:k]
                j = k + 1
                break
        if continueFlag:
            continue
        first = int(first)
        second = int(second)
        total += first * second

print(total)

