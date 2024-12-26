
from helpers import runTest, get_file_lines, get_file_str

test = """AAAA
BBCD
BBCC
EEEC
"""
test2 = """RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE"""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('day12.txt')
    input = [line.strip() for line in input]

H = len(input)
W = len(input[0])
grid = {(y,x): input[y][x] for y in range(H) for x in range(W)}
def get_area_perimeter(grid, y, x):
    area = 0
    perimeter = 0
    plot = grid[(y,x)]
    queue = [(y, x)]
    visited = set()
    while len(queue) > 0:
        node = queue.pop(0)
        if (node[0], node[1]) in visited:
            continue
        visited.add((node[0], node[1]))
        if grid[(node[0], node[1])] == plot:
            area += 1
        for neigh in [(0,1), (0,-1), (1,0), (-1,0)]:
            if (node[0]+neigh[0], node[1]+neigh[1]) in grid:
                if  grid[(node[0]+neigh[0], node[1]+neigh[1])] != plot:
                    perimeter += 1
                else:
                    queue.append((node[0]+neigh[0], node[1]+neigh[1]))
            else:
                perimeter += 1
    return area, perimeter, list(visited)


sum = 0
plots_done = {}
for y,x in grid:
    if grid[(y,x)] not in plots_done:
        plots_done[grid[(y,x)]] = []
        area, perimeter, visited = get_area_perimeter(grid, y, x)
        # print(grid[(y,x)], area, perimeter, area*perimeter)
        plots_done[grid[(y,x)]].append(visited)
        sum += area * perimeter
    else: 
        in_plot = False
        for plot in plots_done[grid[(y,x)]]:
            if (y,x) in plot:
                in_plot = True
        if not in_plot:
            area, perimeter, visited = get_area_perimeter(grid, y, x)
            # print(grid[(y,x)], area, perimeter, area*perimeter)
            plots_done[grid[(y,x)]].append(visited)
            sum += area * perimeter
print(sum)