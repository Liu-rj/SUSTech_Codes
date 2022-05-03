import numpy as np
import random
import math
import time
import copy
import timeout_decorator

infinity = math.inf

COLOR_BLACK = -1
COLOR_WHITE = 1
COLOR_NONE = 0
random.seed()

chessboardLength = 8
step = 4

para = np.array([[1000, -5, 10, 5, 5, 10, -5, 1000],
                 [-5, -10, 2, 2, 2, 2, -10, -5],
                 [10, 2, 2, 1, 1, 2, 2, 10],
                 [5, 2, 1, 1, 1, 1, 2, 5],
                 [5, 2, 1, 1, 1, 1, 2, 5],
                 [10, 2, 2, 1, 1, 2, 2, 10],
                 [-5, -10, 2, 2, 2, 2, -10, -5],
                 [1000, -5, 10, 5, 5, 10, -5, 1000]])

para1 = np.array(
    [[22, -3, 30, 10, 10, 30, -3, 22],
     [-3, -23, -24, -25, -25, -24, -23, -3],
     [30, -24, 13, -18, -18, 13, -24, 30],
     [10, -25, -18, -29, -29, -18, -25, 10],
     [10, -25, -18, -29, -29, -18, -25, 10],
     [30, -24, 13, -18, -18, 13, -24, 30],
     [-3, -23, -24, -25, -25, -24, -23, -3],
     [22, -3, 30, 10, 10, 30, -3, 22]])

para00 = np.array(
    [[4, -20, 41, 54, 54, 41, -20, 4],
     [-20, 23, 50, 46, 46, 50, 23, -20],
     [41, 50, 36, 8, 8, 36, 50, 41],
     [54, 46, 8, 6, 6, 8, 46, 54],
     [54, 46, 8, 6, 6, 8, 46, 54],
     [41, 50, 36, 8, 8, 36, 50, 41],
     [-20, 23, 50, 46, 46, 50, 23, -20],
     [4, -20, 41, 54, 54, 41, -20, 4]])

para11 = np.array(
    [[-3, -52, 51, 63, 63, 51, -52, -3],
     [-52, 55, 50, 60, 60, 50, 55, -52],
     [51, 50, 50, 44, 44, 50, 50, 51],
     [63, 60, 44, 62, 62, 44, 60, 63],
     [63, 60, 44, 62, 62, 44, 60, 63],
     [51, 50, 50, 44, 44, 50, 50, 51],
     [-52, 55, 50, 60, 60, 50, 55, -52],
     [-3, -52, 51, 63, 63, 51, -52, -3]])


# Judge the range
def judgeBound(coord):
    return 0 <= coord[0] < chessboardLength and 0 <= coord[1] < chessboardLength


# add by tuple
def addUnit(a, b):
    c = (a[0] + b[0], a[1] + b[1])
    return c


# don't change the class name
class AI(object):

    # chessboard_size, color, time_out passed from agent
    def __init__(self, chessboard_size, color, time_out):
        self.chessboard_size = chessboard_size

        # You are white or black
        self.color = color
        # the max time you should use, your algorithm's run time must not exceed the time limit.
        self.time_out = time_out
        # You need add your decision into your candidate_list. System will get the end of your candidate_list as your
        # decision.
        self.candidate_list = []

    def go(self, chessboard):
        global step
        step = step + 1
        # Clear candidate_list, must do this step
        self.candidate_list.clear()
        # ==================================================================
        # Write your algorithm here
        # Here is the simplest sample:Random decision

        self.candidate_list = list(self.findAvailablePos(chessboard, self.color))
        if step < 51:
            flag = False
            goal = None
            try:
                v, ac = self.alphabeta_search(chessboard, 3)
                if len(self.candidate_list) > 0 and ac is not None:
                    self.candidate_list.append(ac)
                    goal = ac
            except Exception as e:
                v, ac = self.alphabeta_search_3(chessboard, 3)
                if len(self.candidate_list) > 0 and ac is not None:
                    self.candidate_list.append(ac)
                    goal = ac
                    flag = True
            if not flag:
                try:
                    v, ac = self.alphabeta_search_2(chessboard, 4)
                    if len(self.candidate_list) > 0 and ac is not None:
                       self.candidate_list.append(ac)
                except Exception as e:
                    self.candidate_list.append(goal)

        elif step < 56:
            v, ac = self.alphabeta_search_3(chessboard, 4)
            if len(self.candidate_list) > 0 and ac is not None:
                self.candidate_list.append(ac)
        else:
            v, ac = self.alphabeta_search_end(chessboard, 9)
            if len(self.candidate_list) > 0 and ac is not None:
                self.candidate_list.append(ac)
        # ==============Find new pos========================================
        # Make sure that the position of your decision in chess board is empty.
        # If not, the system will return error.
        # Add your decision into candidate_list, Records the chess board
        # You need add all the positions which is valid
        # candidate_list example: [(3,3),(4,4)]
        # You need append your decision at the end of the candidate_list,
        # we will choice the last element of the candidate_list as the position you choose
        # If there is no valid position, you must return an empty list.

    # The input is current chessboard.
    # def actions(self):
    #     """Legal moves are any square not yet taken."""
    #     return self.findAvailablePos()

    def result(self, player, chessboard, action):
        """Place a marker for current player on square."""

        copyChessBoard = copy.deepcopy(chessboard)
        copyChessBoard[action[0]][action[1]] = player
        copyChessBoard, ns, nr = self.changeChessboard(player, copyChessBoard, action)
        return copyChessBoard, ns, nr

    def utility(self, chessboard):
        score = 0
        coe = 0
        coe1 = 0
        coe2 = 0
        numOfGood = 0
        for i in range(chessboardLength):
            for j in range(chessboardLength):
                if chessboard[i][j] == self.color:
                    if step < 40:
                        score += para00[i][j]
                        numOfGood -= 1
                        coe = 4
                        coe1 = 1
                        coe2 = 14

                    else:
                        score += para11[i][j]
                        numOfGood -= 1
                        coe = 15
                        coe1 = 18
                        coe2 = 4
                elif chessboard[i][j] == -self.color:
                    if step < 40:
                        score -= para00[i][j]
                        numOfGood += 1
                        coe = 4
                        coe1 = 1
                        coe2 = 14
                    else:
                        score -= para11[i][j]
                        numOfGood += 1
                        coe = 15
                        coe1 = 18
                        coe2 = 4
        return score + self.calcStablePiece(chessboard, self.color) * 2110 - (len(self.findAvailablePos(chessboard, self.color)) - len(self.findAvailablePos(chessboard, -self.color))) * coe - numOfGood * coe1 + self.calcFreePiece(chessboard, self) * coe2

    def calcFreePiece(self, chessboard, player):
        freePiece = 0
        eight_direction = [(0, -1), (0, 1), (1, 0), (-1, 0), (1, 1), (1, -1), (-1, 1), (-1, -1)]
        numOfRound = 0
        for i in range(chessboardLength):
            for j in range(chessboardLength):
                if chessboard[i][j] == player.color:
                    current = (i, j)
                    for unit in eight_direction:
                        temp = addUnit(current, unit)
                        if judgeBound(temp) and chessboard[temp[0]][temp[1]] == COLOR_NONE:
                            numOfRound += 1
                    if numOfRound > 0:
                        freePiece += 1
                elif chessboard[i][j] == -player.color:
                    current = (i, j)
                    for unit in eight_direction:
                        temp = addUnit(current, unit)
                        if judgeBound(temp) and chessboard[temp[0]][temp[1]] == COLOR_NONE:
                            numOfRound += 1
                    if numOfRound > 0:
                        freePiece -= 1
        return freePiece

    def calcStablePiece(self, chessboard, player):
        stablePiece = 0
        four_corner = [(0, 0), (0, 7), (7, 0), (7, 7)]
        four_direction = [(0, 1), (1, 0), (0, -1), (-1, 0)]
        for corner in four_corner:
            if chessboard[corner[0]][corner[1]] == COLOR_NONE:
                continue
            elif chessboard[corner[0]][corner[1]] == player:
                stablePiece += 1
            elif chessboard[corner[0]][corner[1]] == -player:
                stablePiece -= 1

            for direction in four_direction:
                corner_copy = copy.deepcopy(corner)
                if chessboard[corner_copy[0]][corner_copy[1]] == player:
                    corner_copy = addUnit(corner_copy, direction)
                    while judgeBound(corner_copy):
                        if chessboard[corner_copy[0]][corner_copy[1]] == player:
                            stablePiece += 1
                            corner_copy = addUnit(corner_copy, direction)
                        else:
                            break

                elif chessboard[corner_copy[0]][corner_copy[1]] == -player:
                    corner_copy = addUnit(corner_copy, direction)
                    while judgeBound(corner_copy):
                        if chessboard[corner_copy[0]][corner_copy[1]] == -player:
                            stablePiece -= 1
                            corner_copy = addUnit(corner_copy, direction)
                        else:
                            break
        cnt1 = 0
        cnt2 = 0
        for i in range(8):
            if chessboard[0][i] == player:
                cnt1 += 1
            if cnt1 == 8:
                stablePiece -= 8
            if chessboard[0][i] == -player:
                cnt2 += 1
            if cnt2 == 8:
                stablePiece += 8

        cnt1 = 0
        cnt2 = 0
        for i in range(8):
            if chessboard[i][0] == player:
                cnt1 += 1
            if cnt1 == 8:
                stablePiece -= 8
            if chessboard[i][0] == -player:
                cnt2 += 1
            if cnt2 == 8:
                stablePiece += 8

        cnt1 = 0
        cnt2 = 0
        for i in range(8):
            if chessboard[i][7] == player:
                cnt1 += 1
            if cnt1 == 8:
                stablePiece -= 8
            if chessboard[i][7] == -player:
                cnt2 += 1
            if cnt2 == 8:
                stablePiece += 8

        cnt1 = 0
        cnt2 = 0
        for i in range(8):

            if chessboard[7][i] == player:
                cnt1 += 1
            if cnt1 == 8:
                stablePiece -= 8
            if chessboard[7][i] == -player:
                cnt2 += 1
            if cnt2 == 8:
                stablePiece += 8
        return stablePiece

    #     """A board is a terminal state if it is won or there are no empty squares."""
    #     return board.utility != 0 or len(self.squares) == len(board)

    def findAvailablePos(self, chessboard, player):

        idx = np.where(chessboard == COLOR_NONE)
        idx = list(zip(idx[0], idx[1]))
        unit_direction_set = [(0, -1), (0, 1), (1, 0), (-1, 0), (1, 1), (1, -1), (-1, 1), (-1, -1)]
        available_set = set()
        for coordinate in idx:
            for unit in unit_direction_set:
                current = addUnit(coordinate, unit)
                if not judgeBound(current):
                    continue
                if chessboard[current[0]][current[1]] == player or chessboard[current[0]][current[1]] == COLOR_NONE:
                    continue
                current = addUnit(current, unit)
                while judgeBound(current):
                    if chessboard[current[0]][current[1]] == player:
                        available_set.add(coordinate)
                    elif chessboard[current[0]][current[1]] == COLOR_NONE:
                        break
                    current = addUnit(current, unit)
        return available_set

    @timeout_decorator.timeout(0.3)
    def alphabeta_search(self, state, depth):
        player = self.color

        def max_value(state, alpha, beta, depth, player, numS, numR):

            if self.is_terminal(state) or depth == 0:
                # print(state)
                # print(self.utility(state))
                return self.utility(state) + numS * 0 - numR * 0, None
            v, move = -infinity, None
            max_set = self.findAvailablePos(state, player)
            #############################################
            if len(max_set) == 0:
                return self.utility(state) + numS * 0 - numR * 0, None
            #############################################
            for a in max_set:
                board, ns, nr = self.result(player, state, a)
                # print(player)
                # print(a)
                # print(board)
                v2, mv = min_value(board, alpha, beta, depth - 1, -player, ns, nr)

                # update *v*, *move* and *alpha*
                if v2 > v:
                    v, move = v2, a
                    alpha = max(alpha, v)
                if v >= beta:
                    return v, move
            return v, move

        def min_value(state, alpha, beta, depth, player, numS, numR):

            if self.is_terminal(state) or depth == 0:
                # print(state)
                # print(self.utility(state))
                return self.utility(state) + numS * 0 - numR * 0, None
            v, move = +infinity, None
            min_set = self.findAvailablePos(state, player)
            #############################################
            if len(min_set) == 0:
                return self.utility(state) + numS * 0 - numR * 0, None

            #############################################
            for a in min_set:
                board, ns, nr = self.result(player, state, a)
                # print(player)
                # print(a)
                # print(board)
                v2, mv = max_value(board, alpha, beta, depth - 1, -player, ns, nr)

                # update *v*, *move* and *beta*
                if v2 < v:
                    v, move = v2, a
                    beta = min(beta, v)
                if v <= alpha:
                    return v, move
            return v, move

        return min_value(state, -infinity, +infinity, depth, player, 0, 0)


    @timeout_decorator.timeout(4.4)
    def alphabeta_search_2(self, state, depth):
        player = self.color

        def max_value(state, alpha, beta, depth, player, numS, numR):

            if self.is_terminal(state) or depth == 0:
                # print(state)
                # print(self.utility(state))
                return self.utility(state) + numS * 0 - numR * 0, None
            v, move = -infinity, None
            max_set = self.findAvailablePos(state, player)
            #############################################
            if len(max_set) == 0:
                return self.utility(state) + numS * 0 - numR * 0, None
            #############################################
            for a in max_set:
                board, ns, nr = self.result(player, state, a)
                # print(player)
                # print(a)
                # print(board)
                v2, mv = min_value(board, alpha, beta, depth - 1, -player, ns, nr)

                # update *v*, *move* and *alpha*
                if v2 > v:
                    v, move = v2, a
                    alpha = max(alpha, v)
                if v >= beta:
                    return v, move
            return v, move

        def min_value(state, alpha, beta, depth, player, numS, numR):

            if self.is_terminal(state) or depth == 0:
                # print(state)
                # print(self.utility(state))
                return self.utility(state) + numS * 0 - numR * 0, None
            v, move = +infinity, None
            min_set = self.findAvailablePos(state, player)
            #############################################
            if len(min_set) == 0:
                return self.utility(state) + numS * 0 - numR * 0, None

            #############################################
            for a in min_set:
                board, ns, nr = self.result(player, state, a)
                # print(player)
                # print(a)
                # print(board)
                v2, mv = max_value(board, alpha, beta, depth - 1, -player, ns, nr)

                # update *v*, *move* and *beta*
                if v2 < v:
                    v, move = v2, a
                    beta = min(beta, v)
                if v <= alpha:
                    return v, move
            return v, move

        return min_value(state, -infinity, +infinity, depth, player, 0, 0)

    def alphabeta_search_3(self, state, depth):
        player = self.color

        def max_value(state, alpha, beta, depth, player, numS, numR):

            if self.is_terminal(state) or depth == 0:
                # print(state)
                # print(self.utility(state))
                return self.utility(state) + numS * 0 - numR * 0, None
            v, move = -infinity, None
            max_set = self.findAvailablePos(state, player)
            #############################################
            if len(max_set) == 0:
                return self.utility(state) + numS * 0 - numR * 0, None
            #############################################
            for a in max_set:
                board, ns, nr = self.result(player, state, a)
                # print(player)
                # print(a)
                # print(board)
                v2, mv = min_value(board, alpha, beta, depth - 1, -player, ns, nr)

                # update *v*, *move* and *alpha*
                if v2 > v:
                    v, move = v2, a
                    alpha = max(alpha, v)
                if v >= beta:
                    return v, move
            return v, move

        def min_value(state, alpha, beta, depth, player, numS, numR):

            if self.is_terminal(state) or depth == 0:
                # print(state)
                # print(self.utility(state))
                return self.utility(state) + numS * 0 - numR * 0, None
            v, move = +infinity, None
            min_set = self.findAvailablePos(state, player)
            #############################################
            if len(min_set) == 0:
                return self.utility(state) + numS * 0 - numR * 0, None

            #############################################
            for a in min_set:
                board, ns, nr = self.result(player, state, a)
                # print(player)
                # print(a)
                # print(board)
                v2, mv = max_value(board, alpha, beta, depth - 1, -player, ns, nr)

                # update *v*, *move* and *beta*
                if v2 < v:
                    v, move = v2, a
                    beta = min(beta, v)
                if v <= alpha:
                    return v, move
            return v, move

        return min_value(state, -infinity, +infinity, depth, player, 0, 0)


    def alphabeta_search_end(self, state, depth):
        player = self.color

        def max_value(state, alpha, beta, depth, player, numS, numR):

            if self.is_terminal(state) or depth == 0:
                # print(state)
                # print(self.utility(state))
                if self.is_terminal(state) and self.judgeRes(state) == self.color:
                    return -infinity, None
                if self.is_terminal(state) and self.judgeRes(state) == -self.color:
                    return +infinity, None
                return self.utility(state) + numS * 0 - numR * 0, None

            v, move = -infinity, None
            max_set = self.findAvailablePos(state, player)
            #############################################
            if len(max_set) == 0:
                return +infinity, None
            #############################################
            for a in max_set:
                board, ns, nr = self.result(player, state, a)
                # print(player)
                # print(a)
                # print(board)
                v2, mv = min_value(board, alpha, beta, depth - 1, -player, ns, nr)

                # update *v*, *move* and *alpha*
                if v2 > v:
                    v, move = v2, a
                    alpha = max(alpha, v)
                if v >= beta:
                    return v, move
            return v, move

        def min_value(state, alpha, beta, depth, player, numS, numR):

            if self.is_terminal(state) or depth == 0:
                # print(state)
                # print(self.utility(state))
                if self.is_terminal(state) and self.judgeRes(state) == self.color:
                    return +infinity, None
                if self.is_terminal(state) and self.judgeRes(state) == -self.color:
                    return -infinity, None
                return self.utility(state) + numS * 10 - numR * 10, None
            v, move = +infinity, None
            min_set = self.findAvailablePos(state, player)
            #############################################
            if len(min_set) == 0:
                return -infinity, None

            #############################################
            for a in min_set:
                board, ns, nr = self.result(player, state, a)
                # print(player)
                # print(a)
                # print(board)
                v2, mv = max_value(board, alpha, beta, depth - 1, -player, ns, nr)

                # update *v*, *move* and *beta*
                if v2 < v:
                    v, move = v2, a
                    beta = min(beta, v)
                if v <= alpha:
                    return v, move
            return v, move

        return min_value(state, -infinity, +infinity, depth, player, 0, 0)


    def changeChessboard(self, player, chessboardArray, des):
        numberOfReverseSelf = 0
        numberOfReverseRival = 0
        chessboardArray[des[0]][des[1]] = player
        unit_direction = [(0, -1), (0, 1), (1, 0), (-1, 0), (1, 1), (1, -1), (-1, 1), (-1, -1)]
        for unit in unit_direction:
            cur = addUnit(des, unit)
            while judgeBound(cur):
                if chessboardArray[cur[0]][cur[1]] == COLOR_NONE:
                    break
                elif chessboardArray[cur[0]][cur[1]] == -player:
                    cur = addUnit(cur, unit)
                elif chessboardArray[cur[0]][cur[1]] == player:
                    temp = addUnit(des, unit)
                    while temp != cur:
                        chessboardArray[temp[0]][temp[1]] = player
                        if player == self.color:
                            numberOfReverseSelf += 1
                        else:
                            numberOfReverseRival += 1
                        temp = addUnit(temp, unit)
                    break
        return chessboardArray, 0, 0

    def is_terminal(self, chessboard):
        if len(self.findAvailablePos(chessboard, self.color)) == 0:
            if len(self.findAvailablePos(chessboard, -self.color)) == 0:
                return True
        return False

    def judgeRes(self, chessboard):
        numberOfWhite, numberOfBlack = 0, 0
        for i in range(chessboardLength):
            for j in range(chessboardLength):
                if chessboard[i][j] == -1:
                    numberOfBlack = numberOfBlack + 1
                elif chessboard[i][j] == 1:
                    numberOfWhite = numberOfWhite + 1

        if numberOfWhite > numberOfBlack:
            return -1

        elif numberOfWhite < numberOfBlack:
            return 1

        else:
            return 0
