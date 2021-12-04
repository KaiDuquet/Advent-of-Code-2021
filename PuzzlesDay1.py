from utils import *


def puzzle1(signals):
    count = 0
    for i in range(1, len(signals)):
        if signals[i] > signals[i - 1]:
            count += 1

    print(count)


def puzzle2(signals):
    count = 0
    prev_sum = signals[0] + signals[1] + signals[2]
    for i in range(3, len(signals)):
        cur_sum = signals[i] + signals[i - 1] + signals[i - 2]
        if cur_sum > prev_sum:
            count += 1
        prev_sum = cur_sum

    print(count)


data = [int(x) for x in get_raw_line_split(1, 1)]
puzzle1(data)
puzzle2(data)
