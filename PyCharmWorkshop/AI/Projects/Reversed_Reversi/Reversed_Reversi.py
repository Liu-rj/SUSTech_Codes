import time
import numpy as np

COLOR_BLACK = -1
COLOR_WHITE = 1
COLOR_NONE = 0


class AI(object):
    dirs = ((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
    weight_map = np.array([[-500, 75, -10, -5, -5, -10, 75, -500],
                           [75, 35, -1, -1, -1, -1, 35, 75],
                           [-10, -1, -3, -2, -2, -3, -1, -10],
                           [-5, -1, -2, -1, -1, -2, -1, -5],
                           [-5, -1, -2, -1, -1, -2, -1, -5],
                           [-10, -1, -3, -2, -2, -3, -1, -10],
                           [75, 35, -1, -1, -1, -1, 35, 75],
                           [-500, 75, -10, -5, -5, -10, 75, -500]])

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
        # how many steps affects the depth of search
        self.count = 0

    # The input is current chessboard.
    def go(self, chessboard):
        board = np.array(chessboard)
        start = time.time()
        # Clear candidate_list, must do this step
        self.candidate_list.clear()
        # ==================================================================
        self.candidate_list = self._get_valid_pos(board, self.color)
        # ==============Find new pos========================================
        if len(self.candidate_list) > 0:
            self._choose_next(board, start)

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
        new_board = chessboard.copy()
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
        return new_board, count

    def _stable_eval(self, chessboard):
        stable = [0, 0, 0]
        cind1 = [0, 0, 7, 7]
        cind2 = [0, 7, 7, 0]
        inc1 = [0, 1, 0, -1]
        inc2 = [1, 0, -1, 0]
        stop = [0, 0, 0, 0]
        for i in range(4):
            if chessboard[cind1[i]][cind2[i]] == self.color:
                stop[i] = 1
                stable[0] += 1
                for j in range(1, 7):
                    if chessboard[cind1[i] + inc1[i] * j][cind2[i] + inc2[i] * j] != self.color:
                        break
                    else:
                        stop[i] = j + 1
                        stable[1] += 1
        for i in range(4):
            if chessboard[cind1[i]][cind2[i]] == self.color:
                for j in range(1, 7 - stop[i - 1]):
                    if chessboard[cind1[i] - inc1[i - 1] * j][cind2[i] - inc2[i - 1] * j] != self.color:
                        break
                    else:
                        stable[1] += 1
        colfull = np.zeros((8, 8), dtype=int)
        colfull[:, np.sum(abs(chessboard), axis=0) == 8] = True
        rowfull = np.zeros((8, 8), dtype=int)
        rowfull[np.sum(abs(chessboard), axis=1) == 8, :] = True
        diag1full = np.zeros((8, 8), dtype=int)
        for i in range(15):
            diagsum = 0
            if i <= 7:
                sind1 = i
                sind2 = 0
                jrange = i + 1
            else:
                sind1 = 7
                sind2 = i - 7
                jrange = 15 - i
            for j in range(jrange):
                diagsum += abs(chessboard[sind1 - j][sind2 + j])
                if diagsum == jrange:
                    for k in range(jrange):
                        diag1full[sind1 - j][sind2 + j] = True
        diag2full = np.zeros((8, 8), dtype=int)
        for i in range(15):
            diagsum = 0
            if i <= 7:
                sind1 = i
                sind2 = 7
                jrange = i + 1
            else:
                sind1 = 7
                sind2 = 14 - i
                jrange = 15 - i
            for j in range(jrange):
                diagsum += abs(chessboard[sind1 - j][sind2 - j])
                if diagsum == jrange:
                    for k in range(jrange):
                        diag2full[sind1 - j][sind2 - j] = True
        stable[2] = sum(sum(np.logical_and(np.logical_and(np.logical_and(colfull, rowfull), diag1full), diag2full)))
        return -sum(stable)

    def _piece_score(self, chessboard):
        return -np.sum(chessboard) * self.color

    def _map_score(self, chessboard):
        score = np.sum(chessboard * self.weight_map) * self.color
        return score

    def _active_score(self, chessboard):
        return len(self._get_valid_pos(chessboard, self.color)) - len(self._get_valid_pos(chessboard, -self.color))

    def _edge_penalty(self, chessboard):
        score = 0
        xs = [0, 7]
        ys = [0, 7]
        for x in xs:
            count = 0
            for y in range(1, 7):
                if chessboard[x][y] == self.color:
                    count += 1
            if count == 6:
                score -= 100
        for y in ys:
            count = 0
            for x in range(1, 7):
                if chessboard[x][y] == self.color:
                    count += 1
            if count == 6:
                score -= 100
        return score

    def _adjust_wp(self, chessboard):
        if chessboard[0][0] == self.color:
            self.weight_map[0][1] = -200
            self.weight_map[1][0] = -200
            self.weight_map[1][1] = -200
        elif chessboard[0][0] == -self.color:
            self.weight_map[0][1] = 0
            self.weight_map[1][0] = 0
            self.weight_map[1][1] = 0
        if chessboard[0][7] == self.color:
            self.weight_map[0][6] = -200
            self.weight_map[1][7] = -200
            self.weight_map[1][6] = -200
        elif chessboard[0][7] == -self.color:
            self.weight_map[0][6] = 0
            self.weight_map[1][7] = 0
            self.weight_map[1][6] = 0
        if chessboard[7][0] == self.color:
            self.weight_map[7][1] = -200
            self.weight_map[6][0] = -200
            self.weight_map[6][1] = -200
        elif chessboard[7][0] == -self.color:
            self.weight_map[7][1] = 0
            self.weight_map[6][0] = 0
            self.weight_map[6][1] = 0
        if chessboard[7][7] == self.color:
            self.weight_map[7][6] = -200
            self.weight_map[6][7] = -200
            self.weight_map[6][6] = -200
        elif chessboard[7][7] == -self.color:
            self.weight_map[7][6] = 0
            self.weight_map[6][7] = 0
            self.weight_map[6][6] = 0

    def _gard_award(self, chessboard):
        if chessboard[0][1] == self.color and chessboard[1][0] == self.color:
            return 200
        if chessboard[6][0] == self.color and chessboard[7][1] == self.color:
            return 200
        if chessboard[0][6] == self.color and chessboard[1][7] == self.color:
            return 200
        if chessboard[6][7] == self.color and chessboard[7][6] == self.color:
            return 200
        return 0

    def _heuristic_score(self, chessboard):
        score = 0
        score += self._edge_penalty(chessboard)
        score += self._gard_award(chessboard)
        if self.count > 25:
            score += self._map_score(chessboard) / 5
            score += 10 * self._active_score(chessboard)
            score += 5 * self._stable_eval(chessboard)
            score += 15 * self._piece_score(chessboard)
        else:
            score += self._map_score(chessboard)
            score += 15 * self._active_score(chessboard)
            score += 8 * self._stable_eval(chessboard)
            score += 10 * self._piece_score(chessboard)
        return score

    def _minimax_ab(self, chessboard, color, alpha, beta, level, start):
        candidates = self._get_valid_pos(chessboard, color)
        end = time.time()
        if len(candidates) == 0 or level == 0 or end - start > 4.93:
            # if end - start > 4.93:
            #     print('early return!')
            return self._heuristic_score(chessboard), None
        # alpha-beta pre-search
        if level > 3:
            sorted_candidates = []
            for pos in candidates:
                new_board, _ = self._place_piece(pos, chessboard, color)
                score, _ = self._minimax_ab(new_board, -color, alpha, beta, 2, start)
                sorted_candidates.append((score, pos))
            if color == self.color:
                sorted_candidates.sort(key=lambda x: x[0], reverse=True)
            else:
                sorted_candidates.sort(key=lambda x: x[0])
            candidates = []
            for i in range(len(sorted_candidates)):
                if i > 3:
                    break
                else:
                    candidates.append(sorted_candidates[i][1])
        # we maximize the number we lose
        if color == self.color:
            cur_score, move = float('-inf'), None
            for pos in candidates:
                new_board, _ = self._place_piece(pos, chessboard, color)
                score, _ = self._minimax_ab(new_board, -color, alpha, beta, level - 1, start)
                if score > cur_score:
                    cur_score, move = score, pos
                    alpha = max(alpha, cur_score)
                if cur_score >= beta:
                    return cur_score, move
        # opp minimize the number we lose
        else:
            cur_score, move = float('inf'), None
            for pos in candidates:
                new_board, _ = self._place_piece(pos, chessboard, color)
                score, _ = self._minimax_ab(new_board, -color, alpha, beta, level - 1, start)
                if score < cur_score:
                    cur_score, move = score, pos
                    beta = min(beta, cur_score)
                if cur_score <= alpha:
                    return cur_score, move
        return cur_score, move

    def _get_depth(self):
        if len(self.candidate_list) > 9:
            return 4
        else:
            return 6

    def _choose_next(self, chessboard, start):
        self.count += 1
        self._adjust_wp(chessboard)
        _, move = self._minimax_ab(chessboard, self.color, float('-inf'), float('inf'), 6, start)
        self.candidate_list.append(move)


# ini_board = [[0, 0, 0, 0, 0, 0, 0, 0],
#              [0, 0, 0, 0, 0, 0, 0, 0],
#              [0, 0, 0, 0, 0, 0, 0, 0],
#              [0, 0, 0, 1, -1, 0, 0, 0],
#              [0, 0, 0, 1, -1, -1, 0, 0],
#              [0, 0, 0, 1, 1, 1, 0, 0],
#              [0, 0, 0, 0, 0, 0, 0, 0],
#              [0, 0, 0, 0, 0, 0, 0, 0]]
#
# board1 = [[0, 0, 0, 0, 0, 0, 0, 0],
#           [0, 1, -1, 0, 0, 0, 0, 0],
#           [0, 0, 1, 0, 0, 0, 0, 0],
#           [0, 0, -1, 1, -1, 0, 0, 0],
#           [0, 0, 0, -1, 1, -1, 0, 0],
#           [0, 0, 0, 0, 0, 1, 0, 0],
#           [0, 0, 0, 0, 0, -1, 1, 0],
#           [0, 0, 0, 0, 0, 0, 0, 0]]
#
# end_board = [[0, 1, -1, -1, -1, -1, -1, 0], [-1, -1, -1, 1, 1, 1, 1, -1], [-1, 1, -1, 1, 1, 1, 1, 1],
#              [-1, -1, 1, 1, 1, -1, 1, -1], [-1, -1, 1, 1, 1, -1, 1, -1], [-1, -1, -1, -1, 1, 1, 1, -1],
#              [-1, 1, 1, 1, 1, 1, 1, 0], [0, -1, 1, -1, -1, -1, 0, 0]]
#
# if __name__ == '__main__':
#     ai = AI(8, -1, 5)
#     start = time.time()
#     ai.count = 26
#     ai.go(end_board)
#     end = time.time()
#     print(end - start)
#     print(ai.candidate_list)
#     start = time.time()
#     ai.count = 0
#     ai.go(board1)
#     end = time.time()
#     print(end - start)
#     print(ai.candidate_list)
#     start = time.time()
#     ai.go(ini_board)
#     end = time.time()
#     print(end - start)
#     print(ai.candidate_list)
