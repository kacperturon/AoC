

from helpers import runTest, get_file_lines
from functools import reduce

test = """p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3"""

if runTest:
    input = test.splitlines()
    W = 11
    H = 7
else: 
    input = get_file_lines('day14.txt')
    input = [line.strip() for line in input]
    W = 101 
    H = 103

grid = {}
for line in input:
    p, v = [val[2:].split(',') for val in line.split(" ")]
    p1, p2 = int(p[0]), int(p[1])
    v1, v2 = int(v[0]), int(v[1])
    if (p1,p2) in grid:
        grid[(p1,p2)].append((v1,v2))
    else:
        grid[(p1,p2)] = [(v1,v2)]

def print_grid(grid):
    for y in range(H):
        line = ""
        for x in range(W):
            # print(x,y)
            if (x,y) not in grid:
                line += '.'
            elif len(grid[(x,y)]) == 0:
                line += '.'
            else:
                # print((x,y), grid[(x,y)])
                line += str(len(grid[(x,y)]))
        print(line)
    print()

def print_grid_vals(grid):
    for y in range(H):
        for x in range(W):
            if (x,y) in grid and len(grid[(x,y)]) != 0:
                print((x,y), grid[(x,y)])

def count_per_quadrant(grid):
    quadrant_vals = []
    for i in range(4):
        val = 0
        offset_x = 0
        offset_y = 0
        if i == 1:
            offset_x += W//2 + 1
        if i == 2:
            offset_y += H//2 + 1
        if i == 3:
            offset_x += W//2 + 1
            offset_y += H//2 + 1
        for y in range(H//2):
            for x in range(W//2):
                if (x+offset_x, y+offset_y) in grid:
                    val += len(grid[(x+offset_x,y+offset_y)])
        quadrant_vals.append(val)
    return quadrant_vals

# print_grid(grid)

for second in range(100):
    new_grid = {}
    for pos in grid:
        vecs = grid[pos]
        for vec in vecs:
            new_x = (pos[0]+vec[0])%W
            new_y = (pos[1]+vec[1])%H
            if (new_x, new_y) in new_grid:
                new_grid[(new_x, new_y)] += [(vec[0], vec[1])]
            else:
                new_grid[(new_x, new_y)] = [(vec[0], vec[1])]
    grid = new_grid
    # print_grid(grid)

# print_grid(grid)

quadrant_counts = count_per_quadrant(grid)
quadrant_sum = reduce(lambda x, y: x*y, quadrant_counts)
print(quadrant_sum)