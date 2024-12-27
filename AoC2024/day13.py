
from helpers import runTest, get_file_lines, get_file_str
import re

test = """Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279
"""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('day13.txt')
    input = [line.strip() for line in input]

input = list(filter(lambda x: x.strip() != "", input))
grid = {}
pattern_btn = r'X\+(\d+), Y\+(\d+)'
pattern_prize = r'X\=(\d+), Y\=(\d+)'

for i in range(0, len(input), 3):
    prize_line = input[i+2]
    prize_match = re.search(pattern_prize, prize_line)
    prize_x = int(prize_match.group(1))
    prize_y = int(prize_match.group(2))


    a_line = input[i]
    a_match = re.search(pattern_btn, a_line)
    a_x = int(a_match.group(1))
    a_y = int(a_match.group(2))

    b_line = input[i+1]
    b_match = re.search(pattern_btn, b_line)
    b_x = int(b_match.group(1))
    b_y = int(b_match.group(2))

    grid[(prize_y, prize_x)] = [(a_y, a_x), (b_y, b_x)]

# A*94 + B*22 == 8400 (prize[0])
# A*94 == 8400 - B*22
# A == (8400-B*22)/94

# A*34 + B*67 == 5400 (prize[1])
# ((8400-B*22)/94)*34 + B*67 == 5400
# 285600 - B*748 + B*6298 == 507600
# B*5550 == 222000
# B == 40

# A*94 + B*22 == 8400
# A*94 + 880 == 8400
# A == 80

# 80*3 + 40*1 == 280
def find_intersection(prize, a_movement, b_movement):
    a_x_with_b_y = a_movement[1] * b_movement[0]
    x_prize_with_b_y = prize[1] * b_movement[0]
    a_y_with_b_x = a_movement[0] * b_movement[1]
    y_prize_with_b_x = prize[0] * b_movement[1]
    a = (x_prize_with_b_y - y_prize_with_b_x) / (a_x_with_b_y - a_y_with_b_x)
    b = (prize[0] - a_movement[0] * a) / b_movement[0]
    return (a, b)

sum = 0
for prize in grid:
    a_movement = grid[prize][0]
    b_movement = grid[prize][1]
    a, b = find_intersection(prize, a_movement, b_movement)
    if (a*3+b).is_integer():
        sum += int(a*3+b)

print(sum)

offset = 10000000000000
sum = 0
for prize in grid:
    a_movement = grid[prize][0]
    b_movement = grid[prize][1]
    a, b = find_intersection((prize[0]+offset, prize[1]+offset), a_movement, b_movement)
    if a.is_integer() and b.is_integer():
        sum += a*3+b

print(int(sum))