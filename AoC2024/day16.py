from helpers import runTest, get_file_lines
 
test = """###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#.....#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############"""

test2 = """#################
#...#...#...#..E#
#.#.#.#.#.#.#.#.#
#.#.#.#...#...#.#
#.#.#.#.###.#.#.#
#...#.#.#.....#.#
#.#.#.#.#.#####.#
#.#...#.#.#.....#
#.#.#####.#.###.#
#.#.#.......#...#
#.#.###.#####.###
#.#.#...#.....#.#
#.#.#.#####.###.#
#.#.#.........#.#
#.#.#.#########.#
#S#.............#
#################"""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('day16.txt')
    input = [line.strip() for line in input]


def print_path(path):
    count = 0
    for y in range(H):
        line = ""
        for x in range(W):
            if (y, x) in path:
                line += 'X'
                count += 1
            else:
                line += '.'
        print(line)
    print()

H = len(input)
W = len(input[0])
grid = {}
start_tile_x = 0
start_tile_y = 0
end_tile_x = 0
end_tile_y = 0
for y in range(len(input)):
    line = input[y]
    for x in range(len(line)):
        if line[x] == 'S':
            start_tile_x = x
            start_tile_y = y
            grid[(y,x)] = '.'
        elif line[x] == 'E':
            end_tile_x = x
            end_tile_y = y
            grid[(y,x)] = '.'
        else:
            grid[(y,x)] = line[x]

# 168592 - too high
# part 1: 160624 part2: 692
def possible_route_scores_iter(y, x, dir_y, dir_x, end_y, end_x, grid):
    all_routes = []
    all_scores = []
    queue = [(y,x, 0, dir_y, dir_x, [(y,x)], [(y,x)])]
    # visited = []
    while queue:
        y, x, scr, dir_y, dir_x, path, visited = queue.pop(0)
        if y == end_y and x == end_x:
            all_routes.append(path)
            if scr not in all_scores:
                all_scores.append(scr)
            continue
        for dy, dx in [(0,1), (0,-1), (1,0), (-1,0)]:
            new_y = y + dy
            new_x = x + dx
            if (new_y, new_x) in grid and grid[(new_y, new_x)] != '#' and not (new_y, new_x) in visited:
                if dy == dir_y and dx == dir_x:
                    queue.append((new_y, new_x, scr+1, dy, dx, path+[(new_y, new_x)], path+[(new_y, new_x)]))
                else:
                    queue.append((new_y, new_x, scr+1001, dy, dx, path+[(new_y, new_x)], path+[(new_y, new_x)]))
    return all_scores


print(min(possible_route_scores_iter(start_tile_y, start_tile_x, 0, 1, end_tile_y, end_tile_x, grid)))
