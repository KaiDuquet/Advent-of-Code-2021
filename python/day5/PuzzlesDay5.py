def puzzle1_or_2(lines, puzzle_num):
    grid = {}
    for line in lines:
        start = line[0]
        stop = line[1]

        differences = (stop[0] - start[0], stop[1] - start[1])
        non_zero_diff = differences[0] if differences[0] != 0 else differences[1]
        dx = 0 if differences[0] == 0 else (differences[0] // abs(differences[0]))
        dy = 0 if differences[1] == 0 else (differences[1] // abs(differences[1]))

        if dx != 0 and dy != 0 and puzzle_num == 1:
            continue

        for i in range(abs(non_zero_diff) + 1):
            point = (start[0] + i * dx, start[1] + i * dy)
            if grid.get(point) is None:
                grid[point] = 1
            else:
                grid[point] += 1

    danger_count = 0
    for value in grid.values():
        if value >= 2:
            danger_count += 1
    print(danger_count)


with open('data5_1.txt', 'r') as f:
    lines_data = []
    for line_data in f:
        points = line_data.rstrip().split(' -> ')
        p1 = points[0].split(',')
        p2 = points[1].split(',')
        lines_data.append(((int(p1[0]), int(p1[1])), (int(p2[0]), int(p2[1]))))

puzzle1_or_2(lines_data, 1)
puzzle1_or_2(lines_data, 2)
