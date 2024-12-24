
from helpers import runTest, get_file_lines
import itertools

test = """190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20"""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('day7.txt')
    input = [line.strip() for line in input]

def check_equation(test, nums, operator_count):
    if operator_count == 2:
        operators = ['+', '*']
    if operator_count == 3:
        operators = ['+', '*', '|']
    combinations = [''.join(comb) for comb in itertools.product(operators, repeat=len(nums)-1)]
    
    for comb in combinations:
        sum = nums[0]
        for j in range(1, len(nums)):
            if comb[j-1] == "+":
                sum += nums[j]
            elif comb[j-1] == "*":
                sum *= nums[j]
            else:
                sum = int(str(sum)+str(nums[j]))
        if sum == test:
            return True
    return False

calibration_sum = 0

for line in input:
    row = []
    testval, nums = line.split(':')
    testval = int(testval)
    for num in nums.strip().split(' '):
        row.append(int(num))
    if check_equation(testval, row, 2) > 0:
        calibration_sum += testval
print(calibration_sum)

calibration_sum = 0

for line in input:
    row = []
    testval, nums = line.split(':')
    testval = int(testval)
    for num in nums.strip().split(' '):
        row.append(int(num))
    if check_equation(testval, row, 3) > 0:
        calibration_sum += testval
print(calibration_sum)