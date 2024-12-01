inputText = "input.txt"
exampleText = "example.txt"

col1 = []
col2_counts = {}
f = open(inputText,'r')
for line in f:
    line = ' '.join(line.split()).strip()
    first = int(line.split(' ')[0])
    second = int(line.split(' ')[1])
    col1.append(first)
    if second not in col2_counts:
        col2_counts[second] = 0
    col2_counts[second] += 1
similarity_score = 0
for num in col1:
    if num in col2_counts:
        similarity_score += num * col2_counts[num]
print(similarity_score)