import math
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\08-12-2023\\input.txt"
example3 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\08-12-2023\\example3.txt"
starts = []
ends = []
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
        if curr_pos[-1] == 'A':
            starts.append(curr_pos)
        elif curr_pos[-1] == 'Z':
            ends.append(curr_pos)
f.close()
outputs = [0 for start in starts]
founds = [False for start in starts]
currents = [start for start in starts]
while sum(1 for x in founds if x) < len(starts):
    for i in range(len(instruction)):
        if instruction[i] == 'L':
            for j in range(len(currents)):
                if not founds[j]:
                    currents[j] = instruction_map[currents[j]][0]
                    outputs[j] += 1
                    if currents[j] in ends:
                        founds[j] = True
        else:
            for j in range(len(currents)):
                if not founds[j]:
                    currents[j] = instruction_map[currents[j]][1]
                    outputs[j] += 1
                    if currents[j] in ends:
                        founds[j] = True
output = 1
for num in outputs:
      output = (output * num) // math.gcd(output,num)
print(output)