
from helpers import runTest, get_file_lines, get_file_str

test = """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#..."""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('day6.txt')
    input = [line.strip() for line in input]

H, W = len(input), len(input[0])
grid = {}
for y in range(H):
    for x in range(W):
        grid[(y,x)] = input[y][x]

guard_y = None
guard_x = None

for y,x in grid:
    if grid.get((y,x)) == "^":
        guard_x = x
        guard_y = y
        grid[(y,x)] = '.'
visited = set([(guard_y, guard_x)])

move = [(-1,0), (0,1), (1,0), (0,-1)]
dir = 0

orig_y = guard_y
orig_x = guard_x
new_y = guard_y
new_x = guard_x
while guard_x + move[dir][1] >= 0 and guard_x + move[dir][1] < W and guard_y + move[dir][0] >= 0 and guard_y + move[dir][0] < H:
    new_y = new_y + move[dir][0]
    new_x = new_x + move[dir][1]
    loc = grid.get((new_y, new_x))
    if loc == '#':
        dir = (dir+1)%len(move)
        new_x = guard_x
        new_y = guard_y
    else:
        guard_y = new_y
        guard_x = new_x
        visited.add((guard_y, guard_x))
print(len(visited))

def grid_print(grid, guard_y, guard_x, dir):
    for y in range(H):
        line = ""
        for x in range(W):
            if guard_y == y and guard_x == x:
                match dir:
                    case 0:
                        line += '^'
                    case 1:
                        line += '>'
                    case 2:
                        line += 'V'
                    case 3:
                        line += '<'
            else:
                line += grid.get((y,x))
        print(line)
    print()


unique_loops = 0
for y,x in grid:
    dir = 0
    guard_y = orig_y
    guard_x = orig_x
    new_y = guard_y
    new_x = guard_x
    visited = set([(guard_y, guard_x, dir)])
    if y == orig_y and x == orig_x or grid.get((y,x)) == '#':
        continue
    else:
        grid[(y,x)] = '#'
    creates_loop = False

    # grid_print(grid, guard_y, guard_x, dir)
    while guard_x + move[dir][1] >= 0 and guard_x + move[dir][1] < W and guard_y + move[dir][0] >= 0 and guard_y + move[dir][0] < H:
        new_y = new_y + move[dir][0]
        new_x = new_x + move[dir][1]
        loc = grid.get((new_y, new_x))
        if loc == '#':
            dir = (dir+1)%len(move)
            new_x = guard_x
            new_y = guard_y
        else:
            if (new_y, new_x, dir) in visited:
                creates_loop = True
                break
            guard_y = new_y
            guard_x = new_x
            # grid_print(grid, guard_y,guard_x, dir)
            visited.add((guard_y, guard_x, dir))
    
    grid[(y,x)] = '.'
    if creates_loop:
        unique_loops += 1
print(unique_loops)