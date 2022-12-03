from Day3_data import sample_data, real_data

def A(data):
  lines = data.split('\n')

  total = 0
  for line in lines:
    left, right = list(line[:len(line)//2]), list(line[len(line)//2:])
    leftLetters = set(left)
    rightLetters = set(right)
    uniqLetter = list(leftLetters.intersection(rightLetters))[0]
    if uniqLetter.islower():
      total += ord(uniqLetter)-96
    else:
      total += ord(uniqLetter)-38
  return total

def B(data):
  lines = data.split('\n')
  groups = [lines[i:i+3] for i in range(0, len(lines), 3)]
  total = 0
  for group in groups:
    first, second, third = set(group[0]), set(group[1]), set(group[2])
    uniqLetter = list((first.intersection(second)).intersection(third))[0]
    if uniqLetter.islower():
      total += ord(uniqLetter)-96
    else:
      total += ord(uniqLetter)-38
  return total



print("A sample:",A(sample_data))
print("A real:",A(real_data))

print("B sample",B(sample_data))
print("B real:",B(real_data))