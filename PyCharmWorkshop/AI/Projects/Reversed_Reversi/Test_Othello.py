from AI_Project1_4 import AI as AI_ljr
from AI_xc import AI as AI_xc
from Reversed_Reversi import AI
import time
import numpy as np

COLOR_BLACK = -1
COLOR_WHITE = 1
COLOR_NONE = 0

ini_board = [[0, 0, 0, 0, 0, 0, 0, 0],
             [0, 0, 0, 0, 0, 0, 0, 0],
             [0, 0, 0, 0, 0, 0, 0, 0],
             [0, 0, 0, 1, -1, 0, 0, 0],
             [0, 0, 0, -1, 1, 0, 0, 0],
             [0, 0, 0, 0, 0, 0, 0, 0],
             [0, 0, 0, 0, 0, 0, 0, 0],
             [0, 0, 0, 0, 0, 0, 0, 0]]


def is_end(ai, chessboard):
    candidates_black = ai._get_valid_pos(chessboard, COLOR_BLACK)
    candidates_white = ai._get_valid_pos(chessboard, COLOR_WHITE)
    if len(candidates_black) == 0 and len(candidates_white) == 0:
        return True
    return


if __name__ == '__main__':
    ai1 = AI(8, -1, 5)
    ai2 = AI_xc(8, 1, 5)
    chessboard = np.array(ini_board)
    ais = [ai1, ai2]
    count = 0
    for i in range(len(ais)):
        if ais[i].color == COLOR_BLACK:
            count = i
            ai = ais[i]
    while not is_end(ai1, chessboard):
        ai = ais[count % 2]
        print(ai.color, ':')
        start = time.time()
        ai.go(chessboard)
        end = time.time()
        duration = end - start
        print(duration)
        print(ai.candidate_list)
        if duration > 5:
            print('timeout')
        if len(ai.candidate_list) == 0:
            count += 1
            continue
        move = ai.candidate_list[-1]
        chessboard, _ = ai1._place_piece(move, chessboard, ai.color)
        print(chessboard)
        count += 1
    result = np.sum(chessboard)
    print(result)
