"""
Example:
input:
map:
---------
------x--
-x-------
---@-----
---##----
------x--
--x----x-
-x-------
---------
action:
0 0 3 3 0 3 3 1 1 1 1 1 3 1 1 2 2 2 2 2

output:
7 3

"""

if __name__ == '__main__':
    test_case = 4
    with open(f'test_cases/problem3/{test_case}-map.txt', 'r') as f:
        game_map = [list(line.strip()) for line in f.readlines()]
    with open(f'./test_cases/problem3/{test_case}-actions.txt', 'r') as f:
        actions = [*map(int, f.read().split(' '))]
    head = (0, 0)
    for i in range(len(game_map)):
        for j in range(len(game_map)):
            if game_map[i][j] == '@':
                head = (i, j)
    for act in actions:
        if act == 0:
            x = head[0] - 1
            y = head[1]
            if x < 0 or y < 0 or x >= len(game_map) or y >= len(game_map[0]) or game_map[x][y] == 'x' or game_map[x][y] == '#':
                exit()
            game_map[x][y] = '@'
            game_map[head[0]][head[1]] = '#'
            temp = head
            head = (x, y)
            next_x = temp[0] - 1
            next_y = temp[1]
            # if not (next_x < 0 or next_y < 0 or next_x >= len(game_map) or next_y >= len(game_map[0])) and
