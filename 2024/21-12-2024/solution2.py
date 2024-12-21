
def getRowChangeString(rowChange):
    output = ""
    if rowChange < 0:
        rowChange *= -1
        for i in range(rowChange):
            output += "^"
    else:
        for i in range(rowChange):
            output += "v"
    return output

def getColChangeString(colChange):
    output = ""
    if colChange < 0:
        colChange *= -1
        for i in range(colChange):
            output += "<"
    else:
        for i in range(colChange):
            output += ">"
    return output

def getDirectionCodeForNumericCodeFromPrevToCurrent(prev,curr,numeric_map):
    output = ""
    fromPos = numeric_map[prev]
    toPos = numeric_map[curr]
    rowChange = toPos[0] - fromPos[0]
    colChange = toPos[1] - fromPos[1]
    if fromPos[0] == 3 and toPos[1] == 0:
        # rows first then columns
        output += getRowChangeString(rowChange)
        output += getColChangeString(colChange)
    elif toPos[0] == 3 and fromPos[1] == 0:
        # columns first then rows
        output += getColChangeString(colChange)
        output += getRowChangeString(rowChange)
    else:
        if colChange < 0:
            output += getColChangeString(colChange)
            output += getRowChangeString(rowChange)
        else:
            output += getRowChangeString(rowChange)
            output += getColChangeString(colChange)
    output += "A"
    return output

def getDirectionCodeForDirectionCodeFromPrevToCurr(prev,curr,direction_map):
    output = ""
    fromPos = direction_map[prev]
    toPos = direction_map[curr]
    rowChange = toPos[0] - fromPos[0]
    colChange = toPos[1] - fromPos[1]
    if fromPos[0] == 0 and toPos[0] == 1 and toPos[1] == 0:
        # rows then columns
        output += getRowChangeString(rowChange)
        output += getColChangeString(colChange)
    elif toPos[0] == 0 and fromPos[0] == 1 and fromPos[1] == 0:
        # columns then rows
        output += getColChangeString(colChange)
        output += getRowChangeString(rowChange)
    else:
        if colChange < 0:
            output += getColChangeString(colChange)
            output += getRowChangeString(rowChange)
        else:
            output += getRowChangeString(rowChange)
            output += getColChangeString(colChange)
    output += "A"
    return output

def getLayeredDirectionCodeForDirectionCodeLength(code,direction_map,dp,layer):
    prev = 'A'
    output = 0
    for curr in code:
        output += getLength(prev,curr,direction_map,dp,layer)
        prev = curr
    return output
def getLength(prev,curr,direction_map,dp,layer):
    if (prev,curr) in dp:
        if layer in dp[(prev,curr)]:
            return dp[(prev,curr)][layer]
        else:
            latest_layer = None
            for i in range(layer,26):
                if i in dp[(prev,curr)]:
                    latest_layer = i
                    break
            code = getDirectionCodeForDirectionCodeFromPrevToCurr(prev,curr,direction_map)
            for i in range(latest_layer - 1,layer - 1,-1):
                dp[(prev,curr)][i] = 0
                prev1 = 'A'
                for curr1 in code:
                    dp[(prev,curr)][i] += getLength(prev1,curr1,direction_map,dp,i + 1)
                    prev1 = curr1
            return dp[(prev,curr)][layer]
    else:
        dp[(prev,curr)] = {}
        dp[(prev,curr)][25] = 1
        code = getDirectionCodeForDirectionCodeFromPrevToCurr(prev,curr,direction_map)
        for i in range(24,layer - 1,-1):
            dp[(prev,curr)][i] = 0
            prev1 = 'A'
            for curr1 in code:
                dp[(prev,curr)][i] += getLength(prev1,curr1,direction_map,dp,i + 1)
                prev1 = curr1
        return dp[(prev,curr)][layer]

def getCompleteCodeForNumericCodePrevToCurrentLength(prev,curr,numeric_map,direction_map,dp):
    code = getDirectionCodeForNumericCodeFromPrevToCurrent(prev,curr,numeric_map)
    length = getLayeredDirectionCodeForDirectionCodeLength(code,direction_map,dp,0)
    return length

def getCompleteCodeLength(code,numeric_map,direction_map,dp):
    prev = 'A'
    output = 0
    for curr in code:
        output += getCompleteCodeForNumericCodePrevToCurrentLength(prev,curr,numeric_map,direction_map,dp)
        prev = curr
    return output

def getNumericPartCode(code):
    return int(code[:3])

def getComplexity(code,numeric_map,direction_map,dp):
    outputCodeLength = getCompleteCodeLength(code,numeric_map,direction_map,dp)
    return getNumericPartCode(code)*outputCodeLength

example = "example.txt"
inputFile = "input.txt"
codes = []
f = open(inputFile,'r')
for line in f:
    codes.append(line.strip())

numeric_map = {
    '7' : (0,0),
    '8' : (0,1),
    '9' : (0,2),
    '4' : (1,0),
    '5' : (1,1),
    '6' : (1,2),
    '1' : (2,0),
    '2' : (2,1),
    '3' : (2,2),
    '0' : (3,1),
    'A' : (3,2)
}

direction_map = {
    '^' : (0,1),
    'A' : (0,2),
    '<' : (1,0),
    'v' : (1,1),
    '>' : (1,2)
}
dp = {}
total = 0
for code in codes:
    total += getComplexity(code,numeric_map,direction_map,dp)
print(total)