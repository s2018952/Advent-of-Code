import math
class Computer:
    def __init__(self,A,B,C,instructions):
        self.A = A
        self.B = B
        self.C = C
        self.instructions = instructions
        self.instruction_pointer = 0
        self.output = []
    def comboValue(self,operand):
        if operand < 4 and operand >= 0:
            return operand
        if operand == 4:
            return self.A
        if operand == 5:
            return self.B
        if operand == 6:
            return self.C
        else:
            print("Error. Invalid program")
            return
    def adv(self,operand):
        result = self.A // math.pow(2,self.comboValue(operand))
        self.A = int(result)
        return
    def bxl(self,operand):
        result = self.B ^ operand
        self.B = result
        return
    def bst(self,operand):
        result = self.comboValue(operand) % 8
        self.B = result
        return
    def jnz(self,operand):
        if self.A == 0:
            return
        else:
            self.instruction_pointer = operand - 2
            return
    def bxc(self,operand):
        result = self.B ^ self.C 
        self.B = result
        return
    def out(self,operand):
        result = self.comboValue(operand) % 8
        self.output.append(result)
        return
    def bdv(self,operand):
        result = self.A // math.pow(2,self.comboValue(operand))
        self.B = int(result)
        return
    def cdv(self,operand):
        result = self.A // math.pow(2,self.comboValue(operand))
        self.C = int(result)
        return
    
    def execute(self):
        while self.instruction_pointer < len(self.instructions) - 1:
            instruction = self.instructions[self.instruction_pointer]
            operand = self.instructions[self.instruction_pointer + 1]
            if instruction == 0:
                self.adv(operand)
            elif instruction == 1:
                self.bxl(operand)
            elif instruction == 2:
                self.bst(operand)
            elif instruction == 3:
                self.jnz(operand)
            elif instruction == 4:
                self.bxc(operand)
            elif instruction == 5:
                self.out(operand)
            elif instruction == 6:
                self.bdv(operand)
            elif instruction == 7:
                self.cdv(operand)
            else:
                print("Error")
            self.instruction_pointer += 2
        return self.output

    

example = "example.txt"
inputFile = "input.txt"
A = None
B = None
C = None
instructions = None
f = open(inputFile,'r')
for line in f:
    if "Register A" in line:
        A = int(line.split(':')[1].strip())
    elif "Register B" in line:
        B = int(line.split(':')[1].strip())
    elif "Register C" in line:
        C = int(line.split(':')[1].strip())
    elif "Program" in line:
        instructions = [int(c) for c in line.split(':')[1].strip().split(',')]
    else:
        continue
computer = Computer(A,B,C,instructions)
output = computer.execute()
output = [int(x) for x in output]
s = ""
for num in output:
    if len(s) > 0:
        s += ","
    s += str(num)
print(s)