import numpy as np
import time

COLOR_BLACK = -1
COLOR_WHITE = 1
COLOR_NONE = 0


class AI(object):
    dirs = ((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))

    # chessboard_size, color, time_out passed from agent
    def __init__(self, chessboard_size, color, time_out):
        self.chessboard_size = chessboard_size
        # You are white or black
        self.color = color
        # the max time you should use, your algorithm's run time must not exceed the time limit.
        self.time_out = time_out
        # You need add your decision into your candidate_list.
        # System will get the end of your candidate_list as your decision.
        self.candidate_list = []

    # The input is current chessboard.
    def go(self, chessboard):
        # Clear candidate_list, must do this step
        self.candidate_list.clear()
        # ==================================================================
        self._get_valid_pos(chessboard)
        # ==============Find new pos========================================

    def _get_valid_pos(self, chessboard):
        for i in range(self.chessboard_size):
            for j in range(self.chessboard_size):
                if chessboard[i][j] == self.color:
                    for dir in self.dirs:
                        x, y = i + dir[0], j + dir[1]
                        while self._on_board((x, y)) and chessboard[x][y] == -self.color:
                            x, y = x + dir[0], y + dir[1]
                        if x - dir[0] == i and y - dir[1] == j:
                            continue
                        elif self._on_board((x, y)) and chessboard[x][y] == COLOR_NONE and (x, y) not in self.candidate_list:
                            self.candidate_list.append((x, y))

    def _on_board(self, pos):
        if 0 < pos[0] < self.chessboard_size and 0 < pos[1] < self.chessboard_size:
            return True
        else:
            return False

# import unittest
# ini_board = np.zeros([8, 8], dtype=np.int8)
# board1 = np.zeros([8, 8], dtype=np.int8)
#
# ini_board[3][3], ini_board[4][4], ini_board[3][4], ini_board[4][3] = 1, 1, -1, -1
# board1[-1][0], board1[0][-1], board1[6][6], board1[0][0], board1[5][5] = 1, 1, -1, -1, 1
#
# test1_ans = [(2, 4), (3, 5), (4, 2), (5, 3)]
# test2_ans = [(7, 7)]
#
#
# class MyTestCase(unittest.TestCase):
#     def test1(self):
#         ai = AI(8, 1, 5)
#         ai.go(ini_board)
#         self.assertEqual(test1_ans, ai.candidate_list)
#
#     def test2(self):
#         ai = AI(8, 1, 5)
#         ai.go(board1)
#         self.assertEqual(test2_ans, ai.candidate_list)
#
#
# if __name__ == '__main__':
#     unittest.main()