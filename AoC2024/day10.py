from helpers import runTest, get_file_lines

test="""0123
1234
8765
9876"""

test2="""89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732"""

test3 = """7777707
1143217
1159927
1165437
1179947
1187657
1191117"""

if runTest:
    input = test3.splitlines()
else: 
    input = get_file_lines('day10.txt')
    input = [line.strip() for line in input]

H = len(input)
W = len(input[0])
grid = {(y, x):int(input[y][x]) for y in range(H) for x in range(W)}
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
    queue = [(y,x)]
    visited = set()
    count = 0
    while len(queue) > 0:
        node_y, node_x = queue.pop(0)
        node = grid[(node_y, node_x)]
        if (node_y, node_x) in visited:
            continue
        visited.add((node_y, node_x))
        if node == 9:
            count += 1
        
        for dx, dy in [(0,1), (0,-1), (1,0), (-1,0)]:
            if (node_y+dy, node_x+dx) in grid:
                node_next = grid[(node_y+dy),(node_x+dx)]
                if node_next - node == 1:
                    queue.append(((node_y+dy), (node_x+dx)))
    return count

sum = 0
for y,x in grid:
    if grid[(y,x)] == 0:
        sum +=  valid_trails(grid, y, x)
print(sum)

def find_trails_2(grid, y, x, trail):
    if grid[(y,x)] == 9:
        return [trail]

    trails = []
    for dy, dx in [(0,1), (0,-1), (1,0), (-1,0)]:
        if ((y+dy), (x+dx)) in grid:
            if grid[(y+dy, x+dx)] - grid[(y,x)] == 1:
                trails += find_trails_2(grid, y+dy, x+dx, trail + [(y+dy, x+dx)])
    return trails

sum = 0
for y,x in grid:
    if grid[(y,x)] == 0:
        sum += len(find_trails_2(grid, y, x, []))

print(sum)