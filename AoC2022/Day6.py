from Day6_data import sample_data, sample_data2, sample_data3, sample_data4, real_data


def fn(data, uniqCount):
  charsProcessed = -1
  left, right = 0,0
  count = 0
  indexes = {}
  chars = list(data)
  while left <= len(data) and right < len(data):
    c = chars[right]
    if c in indexes and indexes[c] >= left:
      left = indexes[c]+1
      count = right-left+1
      indexes[c]
    else:
      count += 1
      if count == uniqCount:
        charsProcessed = right+1
        break
    indexes[c] = right
    right += 1
  return charsProcessed

print("A sample 1:", fn(sample_data, 4))
print("A sample 3:", fn(sample_data2, 4))
print("A sample 4:", fn(sample_data3, 4))
print("A real:", fn(real_data, 4))

print("B sample 1", fn(sample_data, 14))
print("B sample 3", fn(sample_data2, 14))
print("B sample 4", fn(sample_data3, 14))
print("B real:", fn(real_data, 14))
