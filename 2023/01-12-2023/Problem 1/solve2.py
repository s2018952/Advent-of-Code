output = 0
digDict = {
    "one" : "1",
    "two" : "2",
    "three" : "3",
    "four" : "4",
    "five" : "5",
    "six" : "6",
    "seven" : "7",
    "eight" : "8",
    "nine" : "9"
}

f = open("input.txt",'r')
output = 0
for line in f:
    numStr = ""
    for i in range(len(line)):
        if line[i].isdigit():
            numStr += line[i]
        else:
            for j in range(i,len(line)):
                if line[j].isdigit():
                    numStr += line[j]
                    i = j
                    break
                else:
                    breakNow = False
                    for k in range(i,j + 1):
                        if line[k:j + 1] in digDict:
                            numStr += digDict[line[k:j + 1]]
                            i = j
                            breakNow = True
                            break
                    if (breakNow):
                        break
    s = ""
    if len(numStr) >= 1:
        s = numStr[0] + numStr[-1]
    #print(s)
    output += int(s)

f.close()
print(output)