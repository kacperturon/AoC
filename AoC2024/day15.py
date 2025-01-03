from helpers import runTest, get_file_lines, get_file_str
import re

test = """##########
#..O..O.O#
#......O.#
#.OO..O.O#
#..O@..O.#
#O#..O...#
#O..O..O.#
#.OO.O.OO#
#....O...#
##########

<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
>^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^"""

test2 = """########
#..O.O.#
##@.O..#
#...O..#
#.#.O..#
#...O..#
#......#
########

<^^>>>vv<v>>v<<"""

test3 = """#######
#...#.#
#.....#
#..OO@#
#..O..#
#.....#
#######

<vv<<^^<<^^"""

if runTest:
    input = test3.splitlines()
else: 
    input = get_file_lines('day15.txt')
    input = [line.strip() for line in input]

H = 0
W = 0

instr_vec = {
    '<': (0, -1),
    '>': (0, 1),
    '^': (-1, 0),
    'v': (1, 0)
}

def print_warehouse(warehouse, robot_x, robot_y, wide=False):
    width = W if wide == False else W*2
    for y in range(H):
        line = ""
        for x in range(width):
            if y == robot_y and x == robot_x:
                line += '@'
            else: 
                line += warehouse[(y,x)]
        print(line)
    print()

warehouse = {}
robot_x = 0
robot_y = 0
parsing_instr = False
instrs = []
for y in range(len(input)):
    line = input[y]
    if not parsing_instr:
        for x in range(len(line.strip())):
            if y+1 > H:
                H = y+1
            if x+1 > W:
                W = x+1
            if line[x] == '@':
                print('robot', y, x)
                robot_x = x
                robot_y = y
                warehouse[(y,x)] = '.'
            else:
                warehouse[(y,x)] = line[x]
    if line == "":
        parsing_instr = True
    if parsing_instr:
        instrs += list(input[y])
    
def sum_box_coords(warehouse):
    sum = 0
    for y in range(H):
        for x in range(W):
            if (y,x) in warehouse and warehouse[(y,x)] == 'O':
                sum += y*100 + x
    return sum

def execute_instructions(warehouse, instructions, robot_y, robot_x):
    for instr in instructions:
        move_vec = instr_vec[instr]
        new_x = robot_x + move_vec[1]
        new_y = robot_y + move_vec[0]
        new_val = warehouse[(new_y, new_x)]
        if new_val == '.':
            robot_x = new_x
            robot_y = new_y
        elif new_val == '#':
            continue
        elif new_val == 'O':
            new_new_y = new_y + move_vec[0]
            new_new_x = new_x + move_vec[1]
            new_new_val = warehouse[(new_new_y, new_new_x)]
            if new_new_val == '#':
                continue
            elif new_new_val == '.':
                warehouse[(new_y, new_x)] = '.'
                warehouse[(new_new_y, new_new_x)] = 'O'
                robot_x = new_x
                robot_y = new_y
            else:
                end_wall = False
                while warehouse[(new_new_y, new_new_x)] == 'O':
                    new_new_y += move_vec[0]
                    new_new_x += move_vec[1]
                    if warehouse[((new_new_y, new_new_x))] == '#':
                        end_wall = True
                        break
                if end_wall:
                    continue
                else:
                    robot_x = new_x
                    robot_y = new_y
                    warehouse[(new_y, new_x)] = '.'
                    warehouse[((new_new_y, new_new_x))] = 'O'
    return warehouse

def execute_instructions_2(warehouse, instructions, robot_y, robot_x):
    for i in range(40):
    # for instr in instructions:
        move_vec = instr_vec[instructions[i]]
        print(i+1, 'instr', instructions[i])
        new_x = robot_x + move_vec[1]
        new_y = robot_y + move_vec[0]
        new_val = warehouse[(new_y, new_x)]
        if new_val == '.':
            robot_x = new_x
            robot_y = new_y
        elif new_val == '#':
            continue
        elif new_val == ']' or new_val == '[':
            if (move_vec[1] == 1 or move_vec[1] == -1) and move_vec[0] == 0:
                new_new_y = new_y + move_vec[0] + move_vec[0]
                new_new_x = new_x + move_vec[1] + move_vec[1]
                new_new_val = warehouse[(new_new_y, new_new_x)]
                if new_new_val == '#':
                    continue
                elif new_new_val == '.':
                    warehouse[(new_y, new_x)] = '.'
                    if move_vec[1] == 1:
                        warehouse[(new_y + move_vec[0], new_x + move_vec[1])] = "["
                        warehouse[(new_new_y, new_new_x)] = "]"
                    else:
                        warehouse[(new_y + move_vec[0], new_x + move_vec[1])] = "]"
                        warehouse[(new_new_y, new_new_x)] = "["

                    robot_x = new_x
                    robot_y = new_y
                else:
                    end_wall = False
                    while warehouse[(new_new_y, new_new_x)] == ']' or  warehouse[(new_new_y, new_new_x)] == '[':
                        new_new_y += move_vec[0]
                        new_new_x += move_vec[1]
                        if warehouse[((new_new_y, new_new_x))] == '#':
                            end_wall = True
                            break
                    if end_wall:
                        continue
                    else:
                        robot_x = new_x
                        robot_y = new_y
                        warehouse[(new_y, new_x)] = '.'
                        new_y += move_vec[0]
                        new_x += move_vec[1]
                        if move_vec[1] == 1:
                            right = False
                            while new_x != new_new_x or new_y != new_new_y:
                                warehouse[(new_y, new_x)] = "]" if right else "["
                                new_x += move_vec[1]
                                new_y += move_vec[0]
                                right = False if right else True 
                            warehouse[(new_y, new_x)] = "]" if right else "["
                        else:
                            right = True
                            while new_x != new_new_x or new_y != new_new_y:
                                warehouse[(new_y, new_x)] = "]" if right else "["
                                new_x += move_vec[1]
                                new_y += move_vec[0]
                                right = False if right else True 
                            warehouse[(new_y, new_x)] = "]" if right else "["
            else:
                new_new_y = new_y + move_vec[0] 
                new_new_x = new_x + move_vec[1]
                new_new_val = warehouse[(new_new_y, new_new_x)]
                if new_new_val == '#':
                    continue
                elif new_new_val == '.':
                    if new_val == ']' and warehouse[(new_new_y, new_new_x-1)] == '.':
                        warehouse[(new_new_y, new_new_x-1)] = "["
                        warehouse[(new_new_y, new_new_x)] = "]"
                        warehouse[(new_y, new_x)] = '.'
                        warehouse[(new_y, new_x-1)] = '.'
                    elif new_val == '[' and warehouse[(new_new_y, new_new_x+1)] == '.':
                        warehouse[(new_new_y, new_new_x+1)] = "]"
                        warehouse[(new_new_y, new_new_x)] = "["
                        warehouse[(new_y, new_x)] = '.'
                        warehouse[(new_y, new_x+1)] = '.'
                    robot_x = new_x
                    robot_y = new_y
                else:
                    end_wall = False
                    print('here testing end wall', warehouse[(new_new_y, new_new_x)], warehouse[(new_y, new_x)])
                    while warehouse[(new_new_y, new_new_x)] == ']' or warehouse[(new_new_y, new_new_x)] == '[':
                        new_new_y += move_vec[0]
                        new_new_x += move_vec[1]
                        if warehouse[((new_new_y, new_new_x))] == '#' or warehouse[(new_new_y, new_new_x)] != warehouse[(new_y, new_x)]:
                            end_wall = True
                            break
                    if end_wall:
                        continue
                    else:
                        robot_x = new_x
                        robot_y = new_y
                        if new_val == ']' and warehouse[(new_new_y, new_new_x-1)] == '.':
                            warehouse[(new_y, new_x)] = '.'
                            warehouse[(new_y, new_x-1)] = '.'   
                            new_y += move_vec[0]
                            new_x += move_vec[1]
                            while new_x != new_new_x or new_y != new_new_y:
                                warehouse[(new_y, new_x)] = "]"
                                warehouse[(new_y, new_x-1)] = "["
                                new_x += move_vec[1]
                                new_y += move_vec[0]
                            warehouse[(new_y, new_x)] = "]"
                            warehouse[(new_y, new_x-1)] = "["     

                        elif new_val == '[' and warehouse[(new_new_y, new_new_x+1)] == '.':
                            warehouse[(new_y, new_x)] = '.'
                            warehouse[(new_y, new_x+1)] = '.' 

                            new_y += move_vec[0]
                            new_x += move_vec[1]
                            while new_x != new_new_x or new_y != new_new_y:
                                warehouse[(new_y, new_x)] = "["
                                warehouse[(new_y, new_x+1)] = "]"
                                new_x += move_vec[1]
                                new_y += move_vec[0]
                            warehouse[(new_y, new_x)] = "["
                            warehouse[(new_y, new_x+1)] = "]"
        print_warehouse(warehouse, robot_x, robot_y, True )
    return warehouse, robot_y, robot_x

# print(sum_box_coords(execute_instructions(warehouse, instrs, robot_y, robot_x)))

def warehouse_twice_as_wide(warehouse, robot_y, robot_x):
    new_warehouse = {}
    for y in range(H):
        x_offset = 0
        for x in range(W):
            val = warehouse[(y, x)]
            if val == '#':
                new_warehouse[(y,x+x_offset)] = '#'
                x_offset += 1
                new_warehouse[(y,x+x_offset)] = '#'
            elif val == '.':
                new_warehouse[(y,x+x_offset)] = '.'
                x_offset += 1
                new_warehouse[(y,x+x_offset)] = '.'
            elif val == "O":
                new_warehouse[(y,x+x_offset)] = '['
                x_offset += 1
                new_warehouse[(y,x+x_offset)] = ']'
    return new_warehouse, robot_y, robot_x * 2

# print_warehouse(warehouse, robot_x, robot_y, )
new_warehouse, robot_y, robot_x = warehouse_twice_as_wide(warehouse, robot_y, robot_x)
print_warehouse(new_warehouse, robot_x, robot_y, True)
new_warehouse, robot_y, robot_x = execute_instructions_2(new_warehouse, instrs, robot_y, robot_x)
print_warehouse(new_warehouse, robot_x, robot_y, True)
