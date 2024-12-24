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

def get_antennas(grid):
    antennas =  {}
    for y,x in grid:
        val = grid.get((y,x))[0]
        if val != '.':
            if val in antennas:
                antennas[val].append((y,x))
            else:
                antennas[val] = [(y,x)]
    return antennas

def print_grid(grid):
    for y in range(H):
        line = ""
        for x in range(W):
            line += grid.get((y,x))[0]
        print(line)
    print()

def answer(part):
    grid = {(y,x):[input[y][x]] for y in range(H) for x in range(W)}
    antennas = get_antennas(grid)
    antinode_count =  0

    if part == 2:
        for antenna in antennas:
            if len(antennas[antenna]) > 1:
                antinode_count += len(antennas[antenna])

    for y,x in grid:
        val = grid.get((y,x))[0]
        if val != '.' and val != '#':
            for antenna in antennas[val]:
                if antenna[0] != y and antenna[1] != x:
                    diff_y = y-antenna[0]
                    diff_x = x-antenna[1]
                    antinode_y = y + diff_y
                    antinode_x = x + diff_x
                    while antinode_y >= 0 and antinode_y < H and antinode_x >= 0 and antinode_x < W:
                        antinode = grid.get((antinode_y, antinode_x))
                        if '#' not in antinode and part == 1 or '.' in grid.get((antinode_y, antinode_x)) and part == 2:
                            antinode_count += 1
                            if '.' in antinode:
                                grid[(antinode_y, antinode_x)][0] = '#'
                            else:
                                grid[(antinode_y, antinode_x)].append('#')
                        antinode_y += diff_y
                        antinode_x += diff_x 
                        if part == 1:
                            break
    return antinode_count

print(answer(1))
print(answer(2))