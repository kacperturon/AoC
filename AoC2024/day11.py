
from helpers import runTest, get_file_lines, get_file_str

test = """0 1 10 99 999"""
test2 = """125 17"""
if runTest:
    input = test2
else: 
    input = get_file_str('day11.txt')


def print_non_zeroes(non_zero, counts):
    line = ""
    for stone in non_zero:
        line += str(stone) + ": " + str(counts[stone])+", "
    print(line)
    print()

def count_stones(blinks):
    counts = {}
    for stone in input.split(" "):
        if int(stone) in counts:
            counts[int(stone)] = counts[int(stone)] + 1
        else: counts[int(stone)] = 1
    memo = {}
    non_zero_count = [key for key, value in counts.items() if value >= 1]
    for _ in range(blinks):
        new_counts = {}
        for i in range(len(non_zero_count)):
            stone = non_zero_count[i]
            stone_count = counts[non_zero_count[i]]
            if stone_count == 0:
                continue
            if stone == 0:
                if 1 in new_counts:
                    new_counts[1] = new_counts[1] + stone_count
                else: 
                    if 1 in counts:
                        new_counts[1] = counts[1] + stone_count
                    else:
                        new_counts[1] = stone_count
            elif stone > 9 and len(str(stone)) % 2 == 0:
                if stone not in memo:
                    midpoint = len(str(stone)) // 2 # floor divisor
                    left, right = str(stone)[:midpoint], str(stone)[midpoint:]
                    left, right = int(left), int(right)
                    memo[stone] = (left, right)
                left, right = memo[stone]
                if left in new_counts:
                    new_counts[left] = new_counts[left] + stone_count
                else: 
                    if left in counts:
                        new_counts[left] = counts[left] + stone_count
                    else:
                        new_counts[left] = stone_count
                if right in new_counts:
                    new_counts[right] = new_counts[right] + stone_count
                else:
                    if right in counts:
                        new_counts[right] = counts[right] + stone_count
                    else:
                        new_counts[right] = stone_count
            else:
                if stone not in memo:
                    memo[stone] = stone*2024
                out = memo[stone]
                if out in new_counts:
                    new_counts[out] = new_counts[out] + stone_count
                else:
                    if out in counts:
                        new_counts[out] = counts[out] + stone_count
                    else:
                        new_counts[out] = stone_count
            if stone in new_counts:
                new_counts[stone] = new_counts[stone] - stone_count
            else: 
                new_counts[stone] = counts[stone] - stone_count
        counts = new_counts
        non_zero_count = [key for key, value in new_counts.items() if value >= 1]
        # print_non_zeroes(non_zero_count, new_counts)
        
    sum = 0
    for stone in counts:
        sum += counts[stone]
    return sum

print(count_stones(25))
print(count_stones(75))

