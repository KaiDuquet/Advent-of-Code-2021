def get_raw_line_split(day, puzzle_n):
    path = f'data/data{day}_{puzzle_n}.txt'
    with open(path, 'r') as f:
        data = [line.rstrip() for line in f]

    return data


def get_test_data():
    path = f'data/test.txt'
    with open(path, 'r') as f:
        data = [line.rstrip() for line in f]

    return data
