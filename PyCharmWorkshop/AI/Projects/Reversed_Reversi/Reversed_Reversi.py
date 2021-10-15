import copy

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
        self.candidate_list = self._get_valid_pos(chessboard, self.color)
        # ==============Find new pos========================================
        if len(self.candidate_list) > 0:
            self._choose_next(chessboard)

    def _get_valid_pos(self, chessboard, color):
        candidates = []
        for i in range(self.chessboard_size):
            for j in range(self.chessboard_size):
                if chessboard[i][j] == color:
                    for dire in self.dirs:
                        x, y = i + dire[0], j + dire[1]
                        while self._on_board((x, y)) and chessboard[x][y] == -color:
                            x, y = x + dire[0], y + dire[1]
                        if x - dire[0] == i and y - dire[1] == j:
                            continue
                        elif self._on_board((x, y)) and chessboard[x][y] == COLOR_NONE and (x, y) not in candidates:
                            candidates.append((x, y))
        return candidates

    def _on_board(self, pos):
        if 0 <= pos[0] < self.chessboard_size and 0 <= pos[1] < self.chessboard_size:
            return True
        else:
            return False

    def _is_edge(self, pos):
        if pos[0] == 0 or pos[0] == self.chessboard_size - 1:
            return True
        elif pos[1] == 0 or pos[1] == self.chessboard_size - 1:
            return True
        else:
            return False

    def _place_piece(self, pos, chessboard, color):
        new_board = copy.deepcopy(chessboard)
        new_board[pos[0]][pos[1]] = color
        count = 0
        for dire in self.dirs:
            dire_count = 0
            x, y = pos[0] + dire[0], pos[1] + dire[1]
            while self._on_board((x, y)) and new_board[x][y] == -color:
                dire_count += 1
                x, y = x + dire[0], y + dire[1]
            if self._on_board((x, y)) and new_board[x][y] == color:
                count += dire_count
                for i in range(dire_count):
                    x, y = x - dire[0], y - dire[1]
                    new_board[x][y] = color
        return count, new_board

    # TODO: 在深度为0的节点处返回当前棋盘，对于最终最高分数相同的节点从当前棋盘继续开始往下搜索几层（做缓存）
    def _minimax_ab(self, chessboard, pos, color, score, level, alpha, beta):
        reverse, new_board = self._place_piece(pos, chessboard, color)
        candidates = self._get_valid_pos(new_board, -color)
        if len(candidates) == 0 or level == 0:
            if color == self.color:
                return score - reverse
            else:
                return score + reverse
        # we maximize the number we lose
        if color == self.color:
            score -= reverse
            cur_score = float('-inf')
            for next_pos in candidates:
                cur_score = max(self._minimax_ab(new_board, next_pos, -color, score, level - 1, alpha, beta), cur_score)
                if cur_score >= beta:
                    break
                alpha = max(alpha, cur_score)
        # opp minimize the number we lose
        else:
            score += reverse
            cur_score = float('inf')
            for next_pos in candidates:
                cur_score = min(self._minimax_ab(new_board, next_pos, -color, score, level - 1, alpha, beta), cur_score)
                if cur_score <= alpha:
                    break
                beta = min(beta, cur_score)
        return cur_score

    def _choose_next(self, chessboard):
        scores = {}
        for pos in self.candidate_list:
            if self.color == COLOR_BLACK:
                score = self._minimax_ab(chessboard, pos, self.color, 0, 5, float('-inf'), float('inf'))
            else:
                score = self._minimax_ab(chessboard, pos, self.color, 0, 5, float('-inf'), float('inf'))
            # print(pos, ':', score)
            if score in scores.keys() and self._is_edge(pos):
                continue
            scores[score] = pos
        # the score of pure reverse by opp should be maximized
        self.candidate_list.append(scores[max(scores.keys())])


# board = [[0, 0, 0, 0, 0, 0, 0, 0],
#          [0, 0, 0, 0, 0, 0, 0, 0],
#          [0, 0, 0, 0, 0, 0, 0, 0],
#          [0, 0, 0, 1, -1, 0, 0, 0],
#          [0, 0, 0, 1, -1, -1, 0, 0],
#          [0, 0, 0, 1, 1, 1, 0, 0],
#          [0, 0, 0, 0, 0, 0, 0, 0],
#          [0, 0, 0, 0, 0, 0, 0, 0]]
#
# if __name__ == '__main__':
#     ai = AI(8, -1, 5)
#     ai.go(board)
#     print(ai.candidate_list)
