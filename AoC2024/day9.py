from helpers import runTest, get_file_str

test = """2333133121414131402"""

if runTest:
    input = [int(num) for num in list(test)]
else: 
    input = get_file_str('day9.txt')
    input = [int(num) for num in list(input.strip())]

converted = []
idx = 0
for i in range(len(input)):
    for j in range(input[i]):
        if i%2 == 0:
            converted.append(idx)
        else: 
            converted.append('.')
    if i%2 == 0:
        idx += 1

def fragmentation_1(arr):
    converted = arr[:]
    left = 0
    right = len(converted)-1
    found_empty = False
    found_swap = False
    while left < right:
        if converted[left] == '.' or found_empty:
            found_empty = True
        else:
            left += 1
        if converted[right] != '.' or found_swap:
            found_swap = True
        else:
            right -= 1
        
        if found_empty and found_swap:
            converted[left] = converted[right]
            converted[right] = '.'
            found_empty = False
            found_swap = False
            left = left + 1
            right = right - 1
    return converted

def calc_check_sum(arr):
    check_sum = 0
    for idx, num in enumerate(arr):
        if num == '.':
            continue
        check_sum += idx * num
    return check_sum

# part 1
arr = fragmentation_1(converted)
print(calc_check_sum(arr))

def available_slot(arr, idx_start_swap, size_swap):
    found_empty = False
    idx_start_empty = -1
    size_empty = 0
    for idx in range(len(arr)):
        if arr[idx] == '.':
            if not found_empty:
                idx_start_empty = idx
                found_empty = True
            size_empty +=1
        else:
            if found_empty and size_empty >= size_swap:
                break
            elif found_empty:
                found_empty = False
                size_empty = 0
    if idx_start_swap != -1 and size_empty >= size_swap and idx_start_empty < idx_start_swap:
        for i in range(size_swap):
            arr[idx_start_empty+i] = arr[idx_start_swap+i]
            arr[idx_start_swap+i] = '.' 
        return True
    return False

def fragmentation_2(arr):
    converted = arr[:]
    done = 0
    updated = True
    while updated:
        found_swap = False
        idx_start_swap = -1
        size_swap = 0
        updated = False
        for idx in range(len(converted[::-1])):
            curr_idx = len(converted)-1-idx
            if converted[curr_idx] != '.':
                if found_swap:
                    if converted[curr_idx] != converted[idx_start_swap]:
                        if (done == 0 or done > converted[idx_start_swap]) and available_slot(converted, idx_start_swap, size_swap):
                            done = arr[idx_start_swap]
                            updated = True
                            break
                        else:
                            size_swap = 1
                            found_swap = True
                            idx_start_swap = curr_idx
                    else:
                        idx_start_swap = curr_idx
                        size_swap += 1
                else:
                    found_swap = True
                    idx_start_swap = curr_idx
                    size_swap += 1
    return converted


# part 2
arr = fragmentation_2(converted)
print(calc_check_sum(arr))