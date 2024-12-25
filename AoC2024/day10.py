from helpers import runTest, get_file_lines

test2="""0123
1234
8765
9876"""

test="""89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732"""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('day10.txt')
    input = [line.strip() for line in input]

H = len(input)
W = len(input[0])
grid = {(y, x):[int(input[y][x]), 0] for y in range(H) for x in range(W)}
neighbors = [(0,1), (0,-1), (1,0), (-1,0)]

def print_trail(grid, vals):
    for y in range(H):
        line = ""
        for x in range(W):
            if (y,x) in vals:
                line += str(grid[(y,x)][0])
            else:
                line += '.'
        print(line)
    print()

def valid_trails(grid, y, x):
    visited = set([])
    queue = [(y,x)]
    count = 0
    counted_9s = set([])
    while len(queue) > 0:
        # print_trail(grid, queue)
        node = queue.pop(0)
        node_val = grid[(node[0], node[1])]
        visited.add((node[0], node[1]))
        new_y = node[0]
        new_x = node[1]
        for neighbor in neighbors:
            new_y = node[0] + neighbor[0]
            new_x = node[1] + neighbor[1]
            if (new_y, new_x) in grid and (new_y, new_x) not in visited:
                new_val = grid[(new_y, new_x)]
                if new_val[0] > node_val[0] and new_val[0] - node_val[0] == 1:
                    if new_val[0] == 9 and (new_y, new_x) not in counted_9s:
                        counted_9s.add((new_y, new_x))
                        count += 1
                    else:
                        if (new_y, new_x) not in queue:
                            queue.append((new_y, new_x))
    return count


sum = 0
trialhead_sums = ""
for (y, x) in grid:
    if grid[(y,x)][0] == 0:
        grid[(y,x)][1] = valid_trails(grid, y,x)
        trialhead_sums += str(grid[(y,x)][1]) + " "
        sum += grid[(y,x)][1]

print(sum)