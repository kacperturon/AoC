
from Day8_data import sample_data, real_data

def isVisible(data, val, i,j, offsetI, offsetJ):
  if i < 0 or j < 0 or i >= len(data) or j>= len(data[0]):
    return True
  neighbor = int(data[i][j])
  if neighbor >= val: 
    return False
  return isVisible(data,val, i+offsetI,j+offsetJ, offsetI, offsetJ)

def countVisible(data, val, i,j, offsetI, offsetJ):
  if i < 0 or j < 0 or i >= len(data) or j>= len(data[0]):
    return 0
  neighbor = int(data[i][j])
  if neighbor >= val: 
    return 1
  return countVisible(data,val, i+offsetI,j+offsetJ, offsetI, offsetJ) +1


def A(data):
  lines = data.splitlines()
  neighborsX = [-1,0,1,0]
  neighborsY = [0,-1,0,1]
  exterior, interior = 0,0
  for i, line in enumerate(lines):
    for j, c in enumerate(list(line)):
      if i == 0 or i == len(lines)-1 or j == 0 or j == len(line)-1:
        exterior += 1
      else:
        currNum = int(lines[i][j])
        for k in range(len(neighborsX)):
          if isVisible(lines, currNum, i+neighborsX[k],j+neighborsY[k],neighborsX[k], neighborsY[k]):
            interior += 1
            break
  return exterior + interior
  
def B(data):
  lines = data.splitlines()
  neighborsX = [-1,0,1,0]
  neighborsY = [0,-1,0,1]
  exterior, interior = 0,0
  bestScore = 0
  for i, line in enumerate(lines):
    for j, c in enumerate(list(line)):
      if i == 0 or i == len(lines)-1 or j == 0 or j == len(line)-1:
        exterior += 1
      else:
        score = 1
        currNum = int(lines[i][j])
        for k in range(len(neighborsX)):
          score *= countVisible(lines, currNum, i+neighborsX[k],j+neighborsY[k],neighborsX[k], neighborsY[k])
          # print('i',i,'j',j, countVisible(lines, currNum, i+neighborsX[k],j+neighborsY[k],neighborsX[k], neighborsY[k]))
        if score > bestScore:
          bestScore = score
  return bestScore

print("A sample:", A(sample_data))
print("A real:", A(real_data))

print("B sample", B(sample_data))
print("B real:", B(real_data))