from Day5_data import sample_data, real_data
import re

def parse(input):
  state, instruction = re.split(r'\n\n', input, re.MULTILINE)
  stateLines, instructionLines = state.splitlines(), instruction.splitlines()
  blocks = []
  for line in stateLines:
    block = []
    i = 0
    while i < len(list(line)):
      val = ''
      for _ in range(3):
        c = line[i]
        val += c
        i += 1
      if val == '   ':
        block.append('')
      else:
        block.append(val)   
      i+=1
    blocks.append(block)
  blocks = blocks[:-1]
  instructionsToExecute = []
  for line in instructionLines:
    _, count, _, fromColumn, _, toColumn = re.split(r'\s+', line)
    instructionsToExecute.append(list(map(lambda x: int(x),[count, fromColumn, toColumn])))
  return blocks, instructionsToExecute

def topBlockIndex(state, col, offset=0):
  for ix, row in enumerate(state):
    if row[col] != '':
      if offset > 0:
        return ix+(offset-1)
      else:
        return ix
  return len(state)-1
  
def printState(state):
  for line in state:
    str = ''
    for c in line:
      if c == '':
       str += '   '
      else:
        str += c
    print(str)
  print('----------------------------c')

def topCharsState(state):
  topChars=[]
  for _ in range(len(state[0])):
    topChars.append(None)
  for line in state:
    for id,c in enumerate(line):
      if c != '' and topChars[id] is None:
        topChars[id] = c
  out = ''
  for arr in topChars:
    out += arr[1]
  return out

def fn(data, reverse = False):
  state, instructionsToExecute = parse(data)
  for instruction in instructionsToExecute:
    count, fromCol, toCol = instruction
    for i in range(count):
      offsetRow = 0
      fromRow, toRow = topBlockIndex(state, fromCol-1, count-i if reverse else 0), topBlockIndex(state, toCol-1)
      if toRow-1<0:
        state.insert(0, ['' for _ in range(len(state[0]))])
        offsetRow = 1
      if state[toRow+offsetRow][toCol-1] != '':
        state[toRow-1+offsetRow][toCol-1] = state[fromRow+offsetRow][fromCol-1]
      else:
        state[toRow+offsetRow][toCol-1] = state[fromRow+offsetRow][fromCol-1]
      state[fromRow+offsetRow][fromCol-1] = ''
    printState(state)
  return topCharsState(state)


# print("A sample:", fn(sample_data))
print("A real:", fn(real_data))

# print("B sample", fn(sample_data, True))
# print("B real:", fn(real_data, True))

