import numpy as np
import random
import time

COLOR_BLACK = -1
COLOR_WHITE = 1
COLOR_NONE = 0
random.seed(0)

# DIR = ((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
# DIR_o = ((0,1),(1,0),(0,-1),(-1,0))


class AI(object):
    def __init__(self, chessboard_size, color, time_out):
        self.chessboard_size = chessboard_size
        self.color = color
        self.time_out = time_out
        self.candidate_list = []
        self.count=0
        self.time=0
        self.weights=[[ 10000, -1000, 40, -40, -40, 40, -1000, 10000],
             [ -1000,-600,-7,  -7,  -7,-7,-600, -1000],
             [  40, -7,  3,  2,  2,  3, -7, 40],
             [  -40,   -7,  2,1,1,  -2,   -7, -40],
             [  -40,   -7,  2,1,1,  -2,   -7, -40],
             [  40, -7,  3,  2,  2,  3, -7, 40],
             [ -1000,-600,-7,  -7,  -7,7,-600, -1000],
             [ 10000, -1000, 40, -40, -40, 40, -1000, 10000]]
        self.factor=np.ones(8)


    def get_corner(self,board):
        cnt=0
        if (board[0][0]==COLOR_NONE and (board[1][0]==self.color or board[0][1]==self.color or board[1][1]==self.color)):
            cnt+=1
        if (board[0][7]==COLOR_NONE and (board[0][6]==self.color or board[1][7]==self.color or board[1][6]==self.color)):
            cnt+=1
        if (board[7][0]==COLOR_NONE and (board[7][1]==self.color or board[6][0]==self.color or board[6][1]==self.color)):
            cnt+=1
        if (board[7][7]==COLOR_NONE and (board[7][6]==self.color or board[6][7]==self.color or board[6][6]==self.color)):
            cnt+=1
        return cnt


    def evaluation(self,preMove,candidates, board):
        weight = self.get_weightMap(board)
        mobility = len(candidates)
        if mobility==0:
            mobility=-70
        if mobility==1:
            if (candidates[0][0]==0 and candidates[0][1]==0):
                mobility=70
            elif (candidates[0][0]==0 and candidates[0][1]==7):
                mobility=70
            elif (candidates[0][0]==7 and candidates[0][1]==0):
                mobility=70
            elif (candidates[0][0]==7 and candidates[0][1]==7):
                mobility=70
            else:
                mobility=10
        else:
            all_corner_flag=True
            for (i,j) in candidates:
                if not ((i==0 and j==0) or (i==0 and j==7) or (i==7 and j==0) or (i==7 and j==7)):
                    all_corner_flag=False
                    break
            if all_corner_flag:
                mobility=80
        diff = self.Difference(board)
        corner=self.get_corner(board)
        #smooth_score = self.get_smooth(preMove[0], preMove[1], board, np.zeros((8, 8), int))
        # positive = 1
        # if smooth_score % 2 == 0:
        #     positive = -1
        if self.count <= 7:
            if (corner<2):
                total_score =- weight - 10 * mobility - 20 * diff
            else:
                total_score=-weight-5*mobility-20*diff
        elif self.count <= 14:
            if (corner<2):
                total_score=-weight-10*mobility-20*diff
            else:
                total_score = -weight -5*mobility- 20 * diff
        # elif self.count<= 18:
        #     total_score = -weight - 50 * mobility - 50 * diff
        elif self.count<= 28:
            stable = self.get_stable(board)
            if (corner<2):
                total_score=-weight-20*stable-10*mobility-10*diff
            else:
                total_score = -weight-20*stable-5*mobility -40*diff
        elif self.count<= 30:
            total_score = -diff
        else:
            total_score = -diff
        return total_score


    def mobility(self,moves, board):
        oppo_moves, oppo_board = self.find_valid_position(board, -self.color)
        return (len(moves) - len(oppo_moves))

    def get_weightMap(self,board):
        weight_map_score = 0
        newBoard = self.weights.copy()
        if (newBoard[0][1] == self.color):
            newBoard[1][0], newBoard[1][1] = -100,-100
        if (newBoard[1][0] == self.color):
            newBoard[0][1],newBoard[1][1]=-100,-100
        if (newBoard[1][1]==self.color):
            newBoard[0][1],newBoard[1][0]=-100,-100
        if (newBoard[0][6]==self.color):
            newBoard[1][7],newBoard[1][6]=-100,-100
        if (newBoard[1][7]==self.color):
            newBoard[0][6],newBoard[1][6]=-100,-100
        if (newBoard[1][6]==self.color):
            newBoard[0][6],newBoard[1][7]=-100,-100
        if (newBoard[6][6]==self.color):
            newBoard[6][7],newBoard[7][6]=-100,-100
        if (newBoard[6][7]==self.color):
            newBoard[7][6],newBoard[6][6]=-100,-100
        if (newBoard[7][6]==self.color):
            newBoard[6][7],newBoard[6][6]=-100,-100
        if (newBoard[6][0]==self.color):
            newBoard[6][1],newBoard[7][1]=-100,-100
        if (newBoard[6][1]==self.color):
            newBoard[6][0],newBoard[7][1]=-100,-100
        if (newBoard[7][1]==self.color):
            newBoard[6][0],newBoard[6][1]=-100,-100
        if (newBoard[0][0]==self.color):
            newBoard[0][1],newBoard[1][0],newBoard[1][1]=500,500,500
        if (newBoard[0][7]==self.color):
            newBoard[0][6],newBoard[1][7],newBoard[1][6]=500,500,500
        if (newBoard[7][0]==self.color):
            newBoard[7][1],newBoard[6][0],newBoard[6][1]=500,500,500
        if (newBoard[7][7]==self.color):
            newBoard[6][7],newBoard[6][6],newBoard[7][6]=500,500,500
        for i in range(len(board)):
            for j in range(len(board[i])):
                weight_map_score += board[i][j] * newBoard[i][j]
        weight_map_score *= self.color
        return weight_map_score

    def check_stable(self,stable_board,color,position):
        i=position[0]
        j=position[1]
        if color==0:
            return False
        if stable_board[i][j]!=0:
            return False
        if (i==0 or i==7):
            if (stable_board[i][j-1]==color):
                stable_board[i][j]=color
            elif (stable_board[i][j+1]==color):
                stable_board[i][j]=color
            else:
                return False
        elif (j==0 or j==7):
            if (stable_board[i-1][j]==color):
                stable_board[i][j]=color
            elif (stable_board[i+1][j]==color):
                stable_board[i][j]=color
            else:
                return False
        else:
            if (stable_board[i-1][j]==color or stable_board[i+1][j]==color) and (stable_board[i][j-1]==color or stable_board[i][j+1]==color) and (stable_board[i+1][j+1]==color or stable_board[i-1][j-1]==color) and (stable_board[i+1][j-1]==color or stable_board[i-1][j+1]==color):
                stable_board[i][j]=color
            else:
                return False
        return True

    def get_stable(self,board):
        stable_num = 0
        stable = np.zeros((8, 8), int)
        queue=[]


        if (board[0][0]!=0):
            stable[0][0]=board[0][0]
            queue.append((0,0))
        if (board[0][7]!=0):
            stable[0][7]=board[0][7]
            queue.append((0,7))
        if (board[7][0]!=0):
            stable[7][0]=board[7][0]
            queue.append((7,0))
        if (board[7][7]!=0):
            stable[7][7]=board[7][7]
            queue.append((7,7))

        exist_board=((board!=0)*1.0)
        i_res=np.dot(exist_board,self.factor)
        j_res=np.dot(self.factor,exist_board)
        for i in range(8):
            if (i_res[i]==8):
                for j in range(8):
                    if (j_res[j]==8):
                        flag=True
                        i_copy=i
                        j_copy=j
                        dir_1=8-abs(i+j-7)
                        for ii in range(dir_1):
                            i_copy-=1
                            j_copy+=1
                            if (i_copy<0) or (j_copy>7):
                                i_copy+=dir_1
                                j_copy-=dir_1
                            if (board[i_copy][j_copy]==0):
                                flag=False
                                break
                        if flag:
                            dir_2=8-abs(i-j)
                            i_copy=i
                            j_copy=j
                            for ii in range(dir_2):
                                i_copy-=1
                                j_copy-=1
                                if (i_copy<0) or (j_copy<0):
                                    i_copy+=dir_2
                                    j_copy+=dir_2
                                if (board[i_copy][j_copy]==0):
                                    flag=False
                                    break
                        if flag:
                            stable[i][j]=1
                            queue.append((i,j))

        while (len(queue)>0):
            list_pop=queue.pop(0)
            i=list_pop[0]
            j=list_pop[1]
            if (i==0):
                if (j==0):
                    if (self.check_stable(stable,board[0][1],(0,1))):
                        queue.append((0,1))
                    if (self.check_stable(stable,board[1][0],(1,0))):
                        queue.append((1,0))
                    if (self.check_stable(stable,board[1][1],(1,1))):
                        queue.append((1,1))
                elif (j==7):
                    if (self.check_stable(stable,board[0][6],(0,6))):
                        queue.append((0,6))
                    if (self.check_stable(stable,board[1][7],(1,7))):
                        queue.append((1,7))
                    if (self.check_stable(stable,board[1][6],(1,6))):
                        queue.append((1,6))
                else:
                    if (self.check_stable(stable,board[0][j-1],(0,j-1))):
                        queue.append((0,j-1))
                    if (self.check_stable(stable,board[0][j+1],(0,j+1))):
                        queue.append((0,j+1))
                    if (self.check_stable(stable,board[1][j-1],(1,j-1))):
                        queue.append((1,j-1))
                    if (self.check_stable(stable,board[1][j],(1,j))):
                        queue.append((1,j))
                    if (self.check_stable(stable,board[1][j+1],(1,j+1))):
                        queue.append((1,j+1))
            elif (i==7):
                if (j==0):
                    if (self.check_stable(stable,board[7][1],(7,1))):
                        queue.append((7,1))
                    if (self.check_stable(stable,board[6][0],(6,0))):
                        queue.append((6,0))
                    if (self.check_stable(stable,board[6][1],(6,1))):
                        queue.append((6,1))
                elif (j==7):
                    if (self.check_stable(stable,board[7][6],(7,6))):
                        queue.append((7,6))
                    if (self.check_stable(stable,board[6][7],(6,7))):
                        queue.append((6,7))
                    if (self.check_stable(stable,board[6][6],(6,6))):
                        queue.append((6,6))
                else:
                    if (self.check_stable(stable,board[7][j-1],(7,j-1))):
                        queue.append((7,j-1))
                    if (self.check_stable(stable,board[7][j+1],(7,j+1))):
                        queue.append((7,j+1))
                    if (self.check_stable(stable,board[6][j-1],(6,j-1))):
                        queue.append((6,j-1))
                    if (self.check_stable(stable,board[6][j],(6,j))):
                        queue.append((6,j))
                    if (self.check_stable(stable,board[6][j+1],(6,j+1))):
                        queue.append((6,j+1))
            else:
                if (j==0):
                    if (self.check_stable(stable,board[i-1][0],(i-1,0))):
                        queue.append((i-1,0))
                    if (self.check_stable(stable,board[i+1][0],(i+1,0))):
                        queue.append((i+1,0))
                    if (self.check_stable(stable,board[i-1][1],(i-1,1))):
                        queue.append((i-1,1))
                    if (self.check_stable(stable,board[i][1],(i,1))):
                        queue.append((i,1))
                    if (self.check_stable(stable,board[i+1][1],(i+1,1))):
                        queue.append((i+1,1))
                elif (j==7):
                    if (self.check_stable(stable,board[i-1][7],(i-1,7))):
                        queue.append((i-1,7))
                    if (self.check_stable(stable,board[i+1][7],(i+1,7))):
                        queue.append((i+1,7))
                    if (self.check_stable(stable,board[i-1][6],(i-1,6))):
                        queue.append((i-1,6))
                    if (self.check_stable(stable,board[i][6],(i,6))):
                        queue.append((i,6))
                    if (self.check_stable(stable,board[i+1][6],(i+1,6))):
                        queue.append((i+1,6))
                else:
                    if (self.check_stable(stable,board[i-1][j-1],(i-1,j-1))):
                        queue.append((i-1,j-1))
                    if (self.check_stable(stable,board[i-1][j],(i-1,j))):
                        queue.append((i-1,j))
                    if (self.check_stable(stable,board[i-1][j+1],(i-1,j+1))):
                        queue.append((i-1,j+1))
                    if (self.check_stable(stable,board[i][j-1],(i,j-1))):
                        queue.append((i,j-1))
                    if (self.check_stable(stable,board[i][j+1],(i,j+1))):
                        queue.append((i,j+1))
                    if (self.check_stable(stable,board[i+1][j-1],(i+1,j-1))):
                        queue.append((i+1,j-1))
                    if (self.check_stable(stable,board[i+1][j],(i+1,j))):
                        queue.append((i+1,j))
                    if (self.check_stable(stable,board[i+1][j+1],(i+1,j+1))):
                        queue.append((i+1,j+1))

        # if (self.count>25):
        #     print(self.count)
        #     print(board)
        #     print(stable)

        for i in range(8):
            for j in range(8):
                if stable[i][j] == self.color:
                    stable_num += 1
                elif stable[i][j]==-self.color:
                    stable_num-=1
        return stable_num

    def Difference(self,board):
        return self.color*np.sum(board)

    def find_valid_position(self, chessboard, color_name):
        result = []
        bounds = []
        boards=[]
        idx = np.where(chessboard == COLOR_NONE)
        idx = list(zip(idx[0], idx[1]))
        for (i, j) in idx:
            flag = False
            boundary = []
            if (i != 7):
                index = i + 1
                for ii in range(i + 1, 8):
                    if (chessboard[ii][j] != -color_name):
                        index = ii
                        break
                if (index > i + 1 and chessboard[index][j] == color_name):
                    flag = True
                    boundary.append((index, j))
                if (j != 7):
                    index1 = 1
                    for ii in range(1, 8):
                        if (i + ii < 8 and j + ii < 8 and chessboard[i + ii][j + ii] != -color_name):
                            index1 = ii
                            break
                    if (index1 > 1 and chessboard[i + index1][j + index1] == color_name):
                        flag = True
                        boundary.append((i + index1, j + index1))
                if (j != 0):
                    index1 = 1
                    for ii in range(1, 8):
                        if (i + ii < 8 and j - ii >= 0 and chessboard[i + ii][j - ii] != -color_name):
                            index1 = ii
                            break
                    if (index1 > 1 and chessboard[i + index1][j - index1] == color_name):
                        flag = True
                        boundary.append((i + index1, j - index1))
            if (i != 0):
                index = i - 1
                for ii in range(i - 1, -1, -1):
                    if (chessboard[ii][j] != -color_name):
                        index = ii
                        break
                if (index < i - 1 and chessboard[index][j] == color_name):
                    flag = True
                    boundary.append((index, j))
                if (j != 7):
                    index1 = 1
                    for ii in range(1, 8):
                        if (i - ii >= 0 and j + ii < 8 and chessboard[i - ii][j + ii] != -color_name):
                            index1 = ii
                            break
                    if (index1 > 1 and chessboard[i - index1][j + index1] == color_name):
                        flag = True
                        boundary.append((i - index1, j + index1))
                if (j != 0):
                    index1 = 1
                    for ii in range(1, 8):
                        if (i - ii >= 0 and j - ii >= 0 and chessboard[i - ii][j - ii] != -color_name):
                            index1 = ii
                            break
                    if (index1 > 1 and chessboard[i - index1][j - index1] == color_name):
                        flag = True
                        boundary.append((i - index1, j - index1))
            if (j != 7):
                index = j + 1
                for ii in range(j + 1, 8):
                    if (chessboard[i][ii] != -color_name):
                        index = ii
                        break
                if (index > j + 1 and chessboard[i][index] == color_name):
                    flag = True
                    boundary.append((i, index))
            if (j != 0):
                index = j - 1
                for ii in range(j - 1, -1, -1):
                    if (chessboard[i][ii] != -color_name):
                        index = ii
                        break
                if (index < j - 1 and chessboard[i][index] == color_name):
                    flag = True
                    boundary.append((i, index))
            if (flag):
                result.append((i, j))
                bounds.append(boundary)
        for id,(i,j) in enumerate(result):
            board=np.array(chessboard).copy()
            self.transform(board,(i,j),bounds[id],color_name)
            boards.append(board)
        return result, boards

    def transform(self, chessboard, position, bounds, color_name):
        for boundary in bounds:
            inc_i = 0
            inc_j = 0
            if (position[0] < boundary[0]):
                inc_i = 1
            elif (position[0] == boundary[0]):
                inc_i = 0
            else:
                inc_i = -1
            if (position[1] < boundary[1]):
                inc_j = 1
            elif (position[1] == boundary[1]):
                inc_j = 0
            else:
                inc_j = -1
            if (inc_i == 0):
                for jj in range(position[1], boundary[1], inc_j):
                    chessboard[position[0]][jj] = color_name
            elif (inc_j == 0):
                for ii in range(position[0], boundary[0], inc_i):
                    chessboard[ii][position[1]] = color_name
            else:
                diff = abs(boundary[0] - position[0])
                for ii in range(diff):
                    chessboard[position[0] + inc_i * ii][position[1] + inc_j * ii] = color_name

    def minmax(self,preMove,board, color, depth, alpha, beta):
        candidates, candidate_boards = self.find_valid_position(board, color)
        if depth == 0:
            return self.evaluation(preMove,candidates, board), list()
        if len(candidates) == 0:
            now_time = time.time()
            if (now_time - self.time > 4.8):
                return -999999,list()
            score, move=self.minmax((-1,-1),board,-color,depth-1,alpha,beta)
            return score,list()

        if self.color == color:
            bsf_score = -999999
            bsf_move = list()
            for i in range(len(candidates)):
                now_time=time.time()
                if (now_time-self.time>4.8):
                    break
                score, move= self.minmax(candidates[i],candidate_boards[i], -color, depth - 1, alpha, beta)
                if bsf_score < score:
                    bsf_score = score
                    bsf_move = candidates[i]
                alpha = max(alpha, bsf_score)
                if beta <= alpha:
                    break
            return bsf_score, bsf_move
        else:
            bsf_score = 999999
            bsf_move = list()
            for i in range(len(candidates)):
                now_time = time.time()
                if (now_time-self.time>4.8):
                    break
                score, move = self.minmax(candidates[i],candidate_boards[i], -color, depth - 1, alpha, beta)
                if bsf_score > score:
                    bsf_score = score
                    bsf_move = candidates[i]
                beta = min(beta, bsf_score)
                if beta <= alpha:
                    break
            return bsf_score, bsf_move


    def go(self, chessboard):
        self.time=time.time()
        self.count=self.count+1
        self.candidate_list.clear()
        self.candidate_list,candidate_board= self.find_valid_position(chessboard,self.color)
        np.random.shuffle(self.candidate_list)
        # print(self.candidate_list)
        board = np.array(chessboard)
        search_depth = 6
        if (len(self.candidate_list)>7):
            search_depth=2
        elif (len(self.candidate_list)>4):
            search_depth=4
        alpha = -999999
        beta = 999999
        bsf_score, bsf_move = self.minmax((-1,-1),board.copy(), self.color, search_depth, alpha, beta)
        if len(bsf_move) != 0:
            self.candidate_list.append(bsf_move)


if __name__=='__main__':
    ai_1 = AI((8, 8), 1, 5)
    ai_2 = AI((8, 8), -1, 5)
    chess_board = np.array([[0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0],
                            [0, 0, 0, 1, -1, 0, 0, 0], [0, 0, 0, -1, 1, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0],
                            [0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0]])
    while True:
        print(chess_board)
        ai_2.go(chess_board)
        flag = True
        try:
            pair = ai_2.candidate_list[-1]
        except:
            # print("Nothing to go")
            aaa = 0
        else:
            flag = False
            a1, a2 = ai_2.find_valid_position(chess_board, ai_2.color)
            for id1, (i, j) in enumerate(a1):
                if (i == pair[0]) and (j == pair[1]):
                    chess_board=np.array(a2[id1])
        ai_1.go(chess_board)
        try:
            pair = ai_1.candidate_list[-1]
        except:
            # print("Nothing to go")
            aaa = 0
        else:
            flag = False
            a1, a2 = ai_1.find_valid_position(chess_board, ai_1.color)
            for id1, (i, j) in enumerate(a1):
                if (i == pair[0]) and (j == pair[1]):
                    chess_board=np.array(a2[id1])
        if (flag):
            break
    s = chess_board.sum()
    print(s)