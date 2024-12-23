
from helpers import runTest, get_file_lines, get_file_str

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


operators = ['+', '*', '||']
# input = ["292: 11 6 16 20"]
# input = ["190: 10 19"]
# input = ["292: 11 6 16 20"]

# testval_nums = {16159: [6, 5, 8, 839, 218]}
#  3267: [81, 40, 27]
#  3 nums
#  + + 
#  + *
#  * +
#  * *
# (3-1)^2

def get_set_bit(val, idx):
    # Shift the bit at index to the least significant bit and check if it's 1
    return (val & (1 << idx)) != 0

# 3749
# 11 + 6 * 16 + 20.
# + + +
# * + +
# + * +
# + + *
# * * +
# + * *
# * + *
# * * *
def check_equation(test, nums):
    combinations = (len(nums)-1)**len(operators)-1
    if len(nums) == 2:
        combinations = 3
    # print('comb', combinations)
    
    for i in range(combinations):
        sum = nums[0]
        # print('start', sum, bin(i))
        for j in range(1, len(nums)):
            # print('h', nums[j], j-1, get_set_bit(i, j-1), sum)
            if get_set_bit(i, j-1):
                sum *= nums[j]
            else:
                sum += nums[j]
        # print(sum)
        if sum == test:
            # print('pass', sum)
            return True
    return False


testval_nums = {}
calibration_sum = 0

for line in input:
    row = []
    testval, nums = line.split(':')
    testval = int(testval)
    for num in nums.strip().split(' '):
        row.append(int(num))
    if check_equation(testval, row) > 0:
        calibration_sum += testval
    # testval_nums[int(testval)] = row

# calibration_sum = 0
# for test, nums in testval_nums.items():
#     if check_equation(test, nums) > 0:
#         calibration_sum += test

print(calibration_sum)

# 
# 6+5+8*839+218
# 
# 85648246569 - too low
# 85648246694
# 4364915411363 - correct answer
# 4364915411363