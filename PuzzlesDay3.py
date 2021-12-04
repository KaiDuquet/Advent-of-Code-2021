from utils import *


def puzzle1(numbers):
    num_length = len(numbers[0])
    counts = [0] * num_length
    for num in numbers:
        for i in range(num_length):
            counts[i] += 2 * int(num[i]) - 1

    counts = ['0' if x < 0 else '1' for x in counts]
    gamma = int('0b' + ''.join(counts), 2)
    epsilon = (2 ** num_length - 1) ^ gamma

    print(gamma * epsilon)


def get_most_common_bit(numbers, bit_place):
    most_common_bit = 0
    for num in numbers:
        most_common_bit += 2 * int(num[bit_place]) - 1
    return '0' if most_common_bit < 0 else '1'


def get_filtered_nums(numbers, bit_place, use_most_common):
    if len(numbers) != 1:
        most_common_bit = get_most_common_bit(numbers, bit_place)
        if use_most_common:
            return [num for num in numbers if num[bit_place] == most_common_bit]
        return [num for num in numbers if num[bit_place] != most_common_bit]
    return numbers


def puzzle2(numbers):
    o2_rating = []
    co2_rating = []

    most_common_bit = get_most_common_bit(numbers, 0)
    for num in numbers:
        o2_rating.append(num) if num[0] == most_common_bit else co2_rating.append(num)

    current_bit_place = 0
    while len(o2_rating) != 1 or len(co2_rating) != 1:

        current_bit_place += 1
        o2_rating = get_filtered_nums(o2_rating, current_bit_place, True)
        co2_rating = get_filtered_nums(co2_rating, current_bit_place, False)

    print(int('0b' + o2_rating[0], 2) * int('0b' + co2_rating[0], 2))


data = get_raw_line_split(3, 1)
puzzle1(data)
puzzle2(data)
