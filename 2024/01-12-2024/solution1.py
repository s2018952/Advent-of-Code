inputText = "input.txt"
exampleText = "example.txt"

col1 = []
col2 = []
f = open(inputText,'r')
for line in f:
    line = ' '.join(line.split()).strip()
    first = int(line.split(' ')[0])
    second = int(line.split(' ')[1])
    col1.append(first)
    col2.append(second)
col1.sort()
col2.sort()
difference_sum = 0
for x,y in zip(col1,col2):
    difference_sum += abs(x - y)
print(difference_sum)