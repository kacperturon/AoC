from Day2_data import sample_data, real_data

#  A - X - 1 - rock
#  B - Y - 2 - paper
#  C - Z - 3 - scissors
#  0 - lose ; 3 - draw ; 6 - win
def A(data):
  lines = data.splitlines()
  score = 0
  for line in lines:
    left, right = line.split(' ')
    if left == 'A' and right == 'X' or left == 'B' and right == 'Y' or left == 'C' and right == 'Z':
      score += 3
    elif left == 'A' and right == 'Y' or left == 'B' and right == 'Z' or left == 'C' and right == 'X':
      score += 6
    if right == 'X':
      score += 1
    if right == 'Y':
      score += 2
    if right == 'Z':
      score += 3
  return score

#  X - lose ; Y - draw ; Z - win
def B(data):
  lines = data.splitlines()
  total = 0
  for line in lines:
    left, outcome = line.split(' ')
    score = 0
    # lose
    if outcome == 'X':
      if left == 'A':
        right = 'Z'
      if left == 'B':
        right = 'X'
      if left == 'C':
        right = 'Y'
    # draw
    if outcome == 'Y':
      if left == 'A':
        right = 'X'
      if left == 'B':
        right = 'Y'
      if left == 'C':
        right = 'Z'
      score += 3
    # win
    if outcome == 'Z':
      if left == 'A':
        right = 'Y'
      if left == 'B':
        right = 'Z'
      if left == 'C':
        right = 'X'
      score += 6
    if right == 'X':
      score += 1
    if right == 'Y':
      score += 2
    if right == 'Z':
      score += 3
    total += score
  return total

print("A sample:",A(sample_data))
print("A real:",A(real_data))

print("B sample",B(sample_data))
print("B real:",B(real_data))
