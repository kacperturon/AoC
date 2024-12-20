from helpers import runTest, get_file_str

test = """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9"""

# test = """8 6 4 4 1
# """

if runTest:
    input = test
else: 
    input = get_file_str('day2.txt')

lines = input.splitlines()
print(lines)

def report_safe(nums):
    safe = True
    order = 0
    for i in range(len(nums)-1):
        if not safe:
            break
        if order == 0:
            if nums[i] > nums[i+1]:
                order = -1
            elif nums[i] < nums[i+1]:
                order = 1
            else:
                safe = False
        if nums[i] == nums[i+1]:
            safe = False
        elif order == -1:
            if nums[i] < nums[i+1] or nums[i]-nums[i+1] > 3:
                safe = False
        elif order == 1:
            if nums[i] > nums[i+1] or nums[i+1]-nums[i] > 3:
                safe = False
    return safe
            
safe_reports = 0
for line in lines:
    nums = [int(num) for num in line.split()]
    safe_reports += report_safe(nums)
print(safe_reports)

safe_reports = 0
for line in lines:
    nums = [int(num) for num in line.split()]
    if not report_safe(nums):
        for i in range(len(nums)):
            nums_copy = nums[:i] + nums[i+1:]
            if report_safe(nums[:i] + nums[i+1:]):
                safe_reports+=1
                break
    else:
        safe_reports+=1
print(safe_reports)




