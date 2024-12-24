from helpers import runTest, get_file_lines

test = """............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............"""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('day8.txt')
    input = [line.strip() for line in input]

H = len(input)
W = len(input[0])
grid = {(y,x):[input[y][x]] for y in range(H) for x in range(W)}
antennas =  {}
for y,x in grid:
    val = grid.get((y,x))[0]
    if val != '.':
        if val in antennas:
            antennas[val].append((y,x))
        else:
            antennas[val] = [(y,x)]


def print_grid(grid):
    for y in range(H):
        line = ""
        for x in range(W):
            line += grid.get((y,x))[0]
        print(line)
    print()

antinode_count = 0
for y,x in grid:
    val = grid.get((y,x))[0]
    if val != '.' and val != '#':
        for antenna in antennas[val]:
            if antenna[0] != y and antenna[1] != x:
                diff_y = y-antenna[0]
                diff_x = x-antenna[1]
                antinode_y = y + diff_y
                antinode_x = x + diff_x


                if antinode_y >= 0 and antinode_y < H and antinode_x >= 0 and antinode_x < W and (antinode_y, antinode_x) not in antennas[val]:
                    antinode = grid.get((antinode_y, antinode_x))
                    if val not in antinode and '#' not in antinode:
                        antinode_count += 1
                        if '.' in antinode:
                            grid[(antinode_y, antinode_x)][0] = '#'
                        else:
                            grid[(antinode_y, antinode_x)].append('#')

print(antinode_count)
