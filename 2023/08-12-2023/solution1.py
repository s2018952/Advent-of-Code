output = 0
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\08-12-2023\\input.txt"
example1 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\08-12-2023\\example1.txt"
example2 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\08-12-2023\\example2.txt"
start = "AAA"
end = "ZZZ"
instruction = ""
instruction_map = {}
f = open(inputText,'r')
for line in f:
    if (line.strip() == ""):
        continue
    elif line.find("=") == -1:
        instruction = line.strip()
    else:
        curr_pos = line.split("=")[0].strip()
        left = line.split("=")[1].strip().replace('(','').replace(')','').split(", ")[0]
        right = line.split("=")[1].strip().replace('(','').replace(')','').split(", ")[1]
        instruction_map[curr_pos] = (left,right)
f.close()
#print(instruction)
current = start
while current != end:
    for i in range(len(instruction)):
        if instruction[i] == 'L':
            current = instruction_map[current][0]
        else:
            current = instruction_map[current][1]
        output += 1
        #print(instruction[i])
        #print(current)
        if current == end:
            break
print(output)