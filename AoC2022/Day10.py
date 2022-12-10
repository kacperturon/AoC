from Day10_data import sample_data, real_data

def A(data):
  lines = data.splitlines()
  cycleValues = [20, 60, 100, 140, 180, 220]
  x, cycle, output = 1, 0, 0
  for line in lines:
    if line == 'noop':
      cycle += 1
      if cycle in cycleValues:
        output += (cycle * x)
    else: 
      instruction, value = line.split(' ')
      value = int(value)
      cycle +=1 
      if cycle in cycleValues:
        output+= (cycle * x)
      cycle +=1
      if cycle in cycleValues:
        output+= (cycle * x)
      x += value
  return output

def draw(spriteIndex, cycle):
  if cycle%40 == spriteIndex or cycle%40 == spriteIndex +1 or cycle%40 == spriteIndex+2:
    return '#'
  return '.'

def printCRT(crt):
  for line in crt:
    print(line)

def B(data):
  lines = data.splitlines()
  crt = []
  crtLine = ''
  spriteIndex, cycle, output = 1, 0, 0
  for line in lines:
    if line == 'noop':
      cycle += 1
      crtLine += draw(spriteIndex, cycle)

      if len(crtLine) == 40:
        crt.append(crtLine)
        crtLine = ''
      #  draw
    else: 
      instruction, value = line.split(' ')
      value = int(value)
      cycle +=1 
      # draw
      crtLine += draw(spriteIndex, cycle)

      if len(crtLine) == 40:
        crt.append(crtLine)
        crtLine = ''
      cycle +=1
      #  draw
      crtLine += draw(spriteIndex, cycle)

      if len(crtLine) == 40:
        crt.append(crtLine)
        crtLine = ''
      spriteIndex += value
  printCRT(crt)
  return crt

print("A sample:", A(sample_data))
print("A real:", A(real_data))

print("B sample", B(sample_data))
print("B real:", B(real_data))

