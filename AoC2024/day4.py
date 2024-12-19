from helpers import runTest, get_file_lines, get_file_str

test= """MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX"""

if runTest:
    input = test.splitlines()
else: 
    input = get_file_lines('./day4.txt')

H, W = len(input), len(input[0])-1
grid = {(y,x):input[y][x] for y in range(H) for x in range(W)}
count = 0
for y,x in grid:
    for dx, dy in [(0,1), (0,-1), (1,0), (-1,0), (1,1), (-1,-1), (-1,1), (1,-1)]:
        candidate = ""
        for i in range(4):
            if grid.get((y+i*dx,x+i*dy)):
                candidate += grid.get((y+i*dx,x+i*dy))
        if candidate == "XMAS":
            count += 1

print('count', count)
count = 0
for y,x in grid:
    if grid.get((y,x)) == "A":
        LT = grid.get((y+1, x+1))
        RB = grid.get((y-1, x-1))
        if LT == "M" and RB == "S" or LT == "S" and RB == "M":
            RT = grid.get((y-1, x+1))
            LB = grid.get((y+1, x-1))
            if RT == "M" and LB == "S" or RT == "S" and LB == "M":
                count+=1
# 00 01 02
# 10 11 12
# 20 21 22

print('count', count)
