from helpers import runTest, get_file_str
import re


test = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
test2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

if runTest:
    input = test
else: 
    input = get_file_str('./day3.txt')

def get_muls(str):
    # Regular expression to match "mul(number,number)" with round brackets only
    pattern = r'mul\(\d+,\d+\)'
    # Use re.findall to find all matching parts
    matches = re.findall(pattern, str)
    matches = [match[4:-1].split(',') for match in matches]
    matches = [(int(match[0]), int(match[1])) for match in matches]
    return matches

sum = 0
for left, right in get_muls(input):
    sum += left * right

sum = 0

input = "".join(input.splitlines())
pattern_beginning =  r"^(.*?)(?=do\(\)|don't\(\))"
pattern_dos =  r"do\(\)(.*?)(?=don't\(\))"

match = re.search(pattern_beginning, input)
# # # Print the matched part
if match:
    for left, right in get_muls(match.group(1)):
        sum += left * right

matches = re.findall(pattern_dos, input)
for match in matches:
    print(match)
    print()
    for left, right in get_muls(match):
        sum += left*right
print(sum)

