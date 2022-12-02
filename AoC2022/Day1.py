from Day1_data import sample_data, real_data

def getElvesCals(data):
  lines = data.splitlines()
  elvesCals = [0]

  for line in lines:
    if line == '':
      elvesCals.append(0)
    else:
      elvesCals[len(elvesCals)-1]+=int(line)
  return elvesCals
def A(data):
  elvesCals = getElvesCals(data)
  return max(elvesCals)

def B(data):
  elvesCals = getElvesCals(data)
  elvesCals = sorted(elvesCals, reverse=True)
  return elvesCals[0] + elvesCals[1] + elvesCals[2]


print("A sample:",A(sample_data))
print("A real:",A(real_data))

print("B sample",B(sample_data))
print("B real:",B(real_data))