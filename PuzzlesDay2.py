def puzzle1():
    with open('data/data2_1.txt', 'r') as f:
        commands = f.readlines()

    vertical = 0
    horizontal = 0
    for cmd in commands:
        parts = cmd.split(' ')
        if parts[0] == 'forward':
            horizontal += int(parts[1])
        elif parts[0] == 'up':
            vertical -= int(parts[1])
        elif parts[0] == 'down':
            vertical += int(parts[1])

    print(vertical * horizontal)


def puzzle2():
    with open('data/data2_1.txt', 'r') as f:
        commands = f.readlines()

    vertical = 0
    horizontal = 0
    aim = 0
    for cmd in commands:
        parts = cmd.split(' ')
        val = int(parts[1])
        if parts[0] == 'forward':
            horizontal += val
            vertical += aim * val
        elif parts[0] == 'up':
            aim -= val
        elif parts[0] == 'down':
            aim += val

    print(vertical * horizontal)


puzzle1()
puzzle2()
