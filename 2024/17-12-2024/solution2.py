'''
Input instructions are 2,4,1,5,7,5,1,6,4,3,5,5,0,3,3,0
Input B is 0
Input C is 0
Program is
while A not 0:
    2,4 sets B to A % 8
    1,5 sets B to B XOR 5
    7,5 sets C to A // 2**B
    1,6 sets B to B XOR 6
    4,3 sets B to B XOR C
    5,5 outputs B % 8
    0,3 sets A to A // 2**3
'''
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

def get_best_a_input(instructions, index, current):
    for i in range(8):
        computer = Computer(current * 8 + i, 0, 0, instructions)
        output = computer.execute()
        if output == instructions[index:]:
            if index == 0:
                return current * 8 + i
            ret = get_best_a_input(instructions, index - 1, current * 8 + i)
            if ret is not None:
                return ret
    return None

    

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

print(get_best_a_input(instructions, len(instructions) - 1, 0))