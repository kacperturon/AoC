from helpers import runTest, get_file_str
from queue import PriorityQueue 

test = """3   4
4   3
2   5
1   3
3   9
3   3"""

if runTest:
    input = test
else: 
    input = get_file_str('./day1.txt')

def intArrays(input):
    lines = input.splitlines()
    leftArr = []
    rightArr = []
    for line in lines:
        [left, right] = line.split()
        leftArr.append(int(left))
        rightArr.append(int(right))
    return leftArr, rightArr

def ascendingPriorityQueueFromInts(arr):
    prio = PriorityQueue();
    for num in arr:
        prio.put(num)
    return prio

def countDict(arr):
    countIdx = {}
    for num in arr:
        if num in countIdx:
            countIdx[num] = countIdx[num] + 1
        else:
            countIdx[num] = 1
    return countIdx

[leftArr, rightArr] = intArrays(input)


def A():
    leftPrio = ascendingPriorityQueueFromInts(leftArr)
    rightPrio = ascendingPriorityQueueFromInts(rightArr)
    sum = 0
    while leftPrio.empty() == False:
        sum += abs(leftPrio.get() - rightPrio.get())
    return sum

def B():
    rightCounts = countDict(rightArr)
    sum = 0
    for num in leftArr:
        if num in rightCounts:
            sum += num * rightCounts[num]
    return sum


print("A", A())


print("B", B())
