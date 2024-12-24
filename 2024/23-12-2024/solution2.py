
def getOtherConnection(first,connectSet):
    for c in connectSet:
        if c != first:
            return c

example = "example.txt"
inputFile = "input.txt"
connections = []
triples = []
computers = set()
f = open(inputFile,'r')
for line in f:
    line = line.strip()
    first = line.split('-')[0]
    second = line.split('-')[1]
    computers.add(first)
    computers.add(second)
    firstConnections = set()
    secondConnections = set()
    for connectSet in connections:
        if first in connectSet:
            other = getOtherConnection(first,connectSet)
            firstConnections.add(other)
            if other in secondConnections:
                tripleSet = set()
                tripleSet.add(first)
                tripleSet.add(second)
                tripleSet.add(other)
                triples.append(tripleSet)
        if second in connectSet:
            other = getOtherConnection(second,connectSet)
            secondConnections.add(other)
            if other in firstConnections:
                tripleSet = set()
                tripleSet.add(first)
                tripleSet.add(second)
                tripleSet.add(other)
                triples.append(tripleSet)
    connection = set()
    connection.add(first)
    connection.add(second)
    connections.append(connection)
bigConnectionSets = []
for triple in triples:
    for c in computers:
        if c in triple:
            continue
        matches = 0
        for conn in connections:
            if c in conn and getOtherConnection(c,conn) in triple:
                matches += 1
        if matches == len(triple):
            triple.add(c)

maxSize = 0
for triple in triples:
    maxSize = max(maxSize,len(triple))
chosen = []
for triple in triples:
    if len(triple) == maxSize:
        for c in triple:
            chosen.append(c)
        break
chosen.sort()
s = ""
for c in chosen:
    s += c
    s += ","
s = s[:len(s) - 1]
print(s)