
def getOtherConnection(first,connectSet):
    for c in connectSet:
        if c != first:
            return c

example = "example.txt"
inputFile = "input.txt"
connections = []
triples = []
f = open(inputFile,'r')
for line in f:
    line = line.strip()
    first = line.split('-')[0]
    second = line.split('-')[1]
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

count = 0
for tripleSet in triples:
    for c in tripleSet:
        if c[0] == 't':
            count += 1
            break
print(count)