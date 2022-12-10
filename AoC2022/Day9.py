from Day9_data import sample_data, sample_2_data, real_data

def updateKnotLocation(head, knot):
  # x distance = 2
  if abs(knot[0] - head[0]) == 2:
    # move towards head by 1 in x direction
    knot[0] += [1, -1][knot[0] > head[0]]
    # if y distance is more than 1 or more
    if abs(knot[1] - head[1]) >= 1:
        # move towards head by 1 in y direction
        knot[1] += [1, -1][knot[1] > head[1]]
  # y distance 2
  elif abs(knot[1] - head[1]) == 2:
    #  move towards head by 1 in y direction
    knot[1] += [1, -1][knot[1] > head[1]]
    if abs(knot[0] - head[0]) >= 1:
      # move towards head by in in x direction
      knot[0] += [1, -1][knot[0] > head[0]]
  return knot

def fn(data, A=True):
  lines = data.splitlines()
  directions = {
    "R": (0,1),
    "L": (0,-1),
    "U": (1,0),
    "D": (-1,0)
  }
  visitedTail, visitedTail2 = [[0,0]], [[0,0]]
  knots = [[0,0] for _ in range(10)]
  for line in lines:
    direction, count = line.split(' ')
    count = int(count)
    yOffset, xOffset = directions[direction]
    for i in range(count):
      knots[0][0] += yOffset
      knots[0][1] += xOffset
      for k in range(1,10):
        knots[k] = updateKnotLocation(knots[k-1], knots[k])
      if knots[1] not in visitedTail:
          visitedTail.append(knots[1][:])
      if knots[9] not in visitedTail2:
        visitedTail2.append(knots[9][:])
  if A:
    return len(visitedTail)
  else:
    return len(visitedTail2)

print("A sample:", fn(sample_data))
print("A real:", fn(real_data))

print("B sample", fn(sample_data, False))
print("B real:", fn(real_data, False))
      