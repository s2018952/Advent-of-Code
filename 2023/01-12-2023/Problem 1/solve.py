output = 0
f = open("input.txt",'r')
#count = 0
for line in f:
    #count += 1
    numStr = ""
    for i in range(len(line)):
        if line[i].isdigit():
            numStr += line[i]
    #print(numStr)
    s = ""
    if len(numStr) >= 2:
        s = numStr[0] + numStr[-1]
    elif len(numStr) == 1:
        s = numStr[0] + numStr[-1]
    #print(int(s))
    output += int(s)
f.close()
#print(count)
print(output)