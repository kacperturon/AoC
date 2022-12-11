import math
from Day11_data import sample_data, real_data

def parseMonkey(monkey):
  lines = monkey.splitlines()
  items = lines[1].strip().split(' ')[2:]
  items = list(map(lambda i: int(i[:-1]) if i[-1] == ',' else int(i), items))
  operation = lines[2].strip().split(' ')[1:]
  operation = list(map(lambda i: int(i) if i.isnumeric() else i , operation))
  testDivisibleBy = int(lines[3].strip().split(' ')[-1])
  testTrueThrowTo = int(lines[4].strip().split(' ')[-1])
  testFalseThrowTo = int(lines[5].strip().split(' ')[-1])
  return {
    'items': items,
    'operation': operation,
    'testDivisibleBy': testDivisibleBy,
    'testTrueThrowTo': testTrueThrowTo,
    'testFalseThrowTo': testFalseThrowTo,
    'inspected': 0
  }

def executeOperation(item, operation):
  newItem = 0
  if operation[4] == 'old':
    val = item
  else:
    val = operation[4]
  if operation[3] == '*':
    newItem = item * val
  elif operation[3] == '+':
    newItem = item + val
  return newItem

def fn(data, A=True):
  monkeysStr = data.split('\n\n')
  monkeys = []
  for monkeyStr in monkeysStr:
    monkeys.append(parseMonkey(monkeyStr))
  lcm = math.lcm(*[monkey['testDivisibleBy'] for monkey in monkeys])
  for _ in range(20 if A == True else 10000):
    for monkey in monkeys:
      deleted = 0
      for i in range(len(monkey['items'])):
        monkey['inspected'] += 1
        monkey['items'][i-deleted] = executeOperation(monkey['items'][i-deleted], monkey['operation'])
        if A:
          monkey['items'][i-deleted] = monkey['items'][i-deleted] // 3
        else:
          monkey['items'][i-deleted] = monkey['items'][i-deleted] % lcm

        if monkey['items'][i-deleted] % monkey['testDivisibleBy'] == 0:
          monkeys[monkey['testTrueThrowTo']]['items'].append(monkey['items'][i-deleted])
        else:
          monkeys[monkey['testFalseThrowTo']]['items'].append(monkey['items'][i-deleted])
        del monkey['items'][i-deleted]
        deleted +=1
  inspected = []
  for monkey in monkeys:
    inspected.append(monkey['inspected'])
  top2 = sorted(inspected)[-2:]
  return top2[0] * top2[1]

print("A sample:", fn(sample_data))
print("A real:", fn(real_data))

print("B sample", fn(sample_data, False))
print("B real:", fn(real_data, False))

