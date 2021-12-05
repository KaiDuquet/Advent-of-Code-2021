def check_board(board):
    for i in range(5):
        if not any(board[i * 5: i * 5 + 5]):
            return True
        if not any([board[(i + j * 5)] for j in range(5)]):
            return True
    return False


def check_all(board_list):
    count = 0
    for board in board_list:
        if check_board(board):
            count += 1

    print(count)


def puzzle1(board_list):
    winning_board = None
    winning_number = 0
    for number in numbers:
        for board in board_list:
            try:
                num_index = board.index(number)
                board[num_index] = ''
                if check_board(board):
                    winning_board = board
                    winning_number = int(number)
                    break
            except ValueError:
                pass
        if winning_board is not None:
            break

    check_all(board_list)

    board_sum = 0
    for num in winning_board:
        if num != '':
            board_sum += int(num)

    for i, num in enumerate(winning_board):
        if i % 5 == 0:
            print()
        print(num if num else '__', end='')
        print(' ', end='')
    print('\n', board_sum, winning_number)
    print(board_sum * winning_number)


def puzzle2(board_list):
    losing_boards = []
    losing_number = 0
    for number in numbers:
        for board in board_list:
            try:
                num_index = board.index(number)
                board[num_index] = ''
                if check_board(board):
                    losing_boards.append(board)
                    losing_number = int(number)
            except ValueError:
                pass

        if len(losing_boards) > 0:
            if len(board_list) == 1:
                break
            board_list = [x for x in board_list if x not in losing_boards]
            losing_boards.clear()

    board_sum = 0
    for num in losing_boards[0]:
        if num != '':
            board_sum += int(num)

    for i, num in enumerate(losing_boards[0]):
        if i % 5 == 0:
            print()
        print(num if num else '__', end='')
        print(' ', end='')
    print('\n', board_sum, losing_number)
    print(board_sum * losing_number)


with open('data4_1.txt', 'r') as f:
    numbers = f.readline().rstrip().split(',')
    f.readline()
    boards = []

    current_board = []
    for line in f:

        if line == '\n':
            boards.append(current_board.copy())
            current_board.clear()
            continue
        current_board.extend([element for element in line.rstrip().split(' ') if element])

    boards.append(current_board.copy())


puzzle1(boards.copy())
puzzle2(boards.copy())
