from Day4_data import sample_data, real_data


def prepData(line):
    left, right = line.split(',')
    left1, left2 = left.split('-')
    right1, right2 = right.split('-')
    left1, left2, right1, right2 = int(left1), int(
        left2), int(right1), int(right2)
    return left1, left2, right1, right2


def A(data):
    lines = data.splitlines()
    count = 0
    for line in lines:
        left1, left2, right1, right2 = prepData(line)
        if left1 <= right1 and left2 >= right2 or right1 <= left1 and right2 >= left2:
            count += 1

    return count


def B(data):
    lines = data.splitlines()
    count = 0
    for line in lines:
        left1, left2, right1, right2 = prepData(line)
        if left1 <= right1 and left2 >= right2 or left1 <= right1 and right1 <= left2 or right1 <= left1 and right2 >= left2 or right1 <= left1 and left1 <= right2:
            count += 1

    return count


print("A sample:", A(sample_data))
print("A real:", A(real_data))

print("B sample", B(sample_data))
print("B real:", B(real_data))
