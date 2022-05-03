import copy

import numpy as np
import time
import argparse
import threading
import random
from abc import ABC, abstractmethod

# SPEC = {}
# COST = []
# FREE = set()
# BEST = None


def arg_parse():
    parser = argparse.ArgumentParser()
    parser.add_argument('file', type=str, help="CARP instance file")
    parser.add_argument('-t', type=int, help="termination")
    parser.add_argument('-s', type=int, help="random seed")
    opts = parser.parse_args()
    return opts


def load_data(path):
    global COST, FREE, SPEC
    with open(path, 'r') as file:
        content = file.readlines()
    for num in range(1, len(content) - 1):
        if num < 9:
            seg = content[num].split(' : ')
            seg[0] = seg[0].strip()
            if seg[0] == 'CAPACIDAD':
                seg[0] = 'CAPACITY'
                SPEC[seg[0]] = int(seg[1])
            elif seg[0] in ['VERTICES', 'ARISTAS_REQ', 'ARISTAS_NOREQ']:
                SPEC[seg[0]] = int(seg[1])
        elif num == 9:
            dim = SPEC['VERTICES'] + 1
            COST = np.full((dim, dim), fill_value=np.inf)
            np.fill_diagonal(COST, 0)
        elif 9 < num <= 9 + SPEC['ARISTAS_REQ']:
            seg = content[num][:-1].split()
            x, y, cost, demand = int(seg[1].rstrip(',')), int(seg[2].rstrip(')')), int(seg[4]), int(seg[6])
            COST[x][y] = cost
            COST[y][x] = cost
            if demand > 0:
                FREE.add((x, y, cost, demand))
                FREE.add((y, x, cost, demand))
        elif 10 + SPEC['ARISTAS_REQ'] < num <= 10 + SPEC['ARISTAS_REQ'] + SPEC['ARISTAS_NOREQ']:
            seg = content[num][:-1].split()
            x, y, cost, demand = int(seg[1].rstrip(',')), int(seg[2].rstrip(')')), int(seg[4]), 0
            COST[x][y] = cost
            COST[y][x] = cost
            if demand > 0:
                FREE.add((x, y, cost, demand))
                FREE.add((y, x, cost, demand))
    line = content[-1]
    seg = line.split(' : ')
    SPEC['DEPOT'] = int(seg[1].strip())


def floyd():
    global SPEC, COST
    dim = SPEC['VERTICES'] + 1
    for k in range(dim):
        for i in range(dim):
            for j in range(dim):
                if COST[i][j] > COST[i][k] + COST[k][j]:
                    COST[i][j] = COST[i][k] + COST[k][j]


def better(edge, target, tail, load, rule):
    global SPEC, COST
    if rule == 1:
        r_new = COST[edge[1]][SPEC['DEPOT']]
        r_old = COST[target[1]][SPEC['DEPOT']]
        if r_new > r_old:
            return True
    elif rule == 2:
        r_new = COST[edge[1]][SPEC['DEPOT']]
        r_old = COST[target[1]][SPEC['DEPOT']]
        if r_new < r_old:
            return True
    elif rule == 3:
        r_new = edge[2] / (COST[tail][edge[0]] + edge[3] + COST[edge[1]][SPEC['DEPOT']])
        r_old = target[2] / (COST[tail][target[0]] + target[3] + COST[target[1]][SPEC['DEPOT']])
        if r_new > r_old:
            return True
    elif rule == 4:
        r_new = edge[2] / (COST[tail][edge[0]] + edge[3] + COST[edge[1]][SPEC['DEPOT']])
        r_old = target[2] / (COST[tail][target[0]] + target[3] + COST[target[1]][SPEC['DEPOT']])
        if r_new < r_old:
            return True
    elif rule == 5:
        r_new = COST[edge[1]][SPEC['DEPOT']]
        r_old = COST[target[1]][SPEC['DEPOT']]
        if load < SPEC['CAPACITY'] / 2:
            if r_new > r_old:
                return True
        else:
            if r_new < r_old:
                return True
    return False


def path_scanning():
    global FREE, COST, SPEC
    routes = []
    quality = 0
    free = FREE.copy()
    while len(free) > 0:
        route = []
        load = 0
        cost = 0
        tail = SPEC['DEPOT']
        while len(free) > 0:
            distance = np.inf
            for edge in free:
                if load + edge[3] > SPEC['CAPACITY']:
                    continue
                if COST[tail][edge[0]] < distance:
                    distance = COST[tail][edge[0]]
                    target = edge
                elif COST[tail][edge[0]] == distance:
                    n = random.randint(1, 5)
                    if better(edge, target, tail, load, n):
                        target = edge
            if distance == np.inf:
                break
            route.append(target)
            free.remove(target)
            free.remove((target[1], target[0], target[2], target[3]))
            load += target[3]
            cost += distance + target[2]
            tail = target[1]
        cost += COST[tail][SPEC['DEPOT']]
        routes.append([route, load])
        quality += cost
    return routes, quality


def flip(target):
    routes = target[0]
    # for r_l in routes:
    #     if not r_l[0]:
    #         routes.remove(r_l)
    x = random.randint(0, len(routes) - 1)  # which route
    route = routes[x][0]
    y = random.randint(0, len(route) - 1)  # which edge in that route
    edge = route[y]
    # start flip
    route[y] = (edge[1], edge[0], route[y][2], route[y][3])
    # temporary insert two assistant edge and adjust the index
    route.insert(0, (SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    route.append((SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    y += 1
    # re-compute total cost
    next_head = route[y + 1][0]
    target[1] -= COST[edge[1]][next_head]
    target[1] += COST[edge[0]][next_head]
    pre_tail = route[y - 1][1]
    target[1] -= COST[pre_tail][edge[0]]
    target[1] += COST[pre_tail][edge[1]]
    # delete the temporarily inserted edges
    route.pop(0)
    route.pop()


def single_insertion(target):
    routes = target[0]
    x = random.randint(0, len(routes) - 1)  # which route
    x_in_routes = x
    route = routes[x][0]
    y = random.randint(0, len(route) - 1)  # which edge in that route
    edge, demand = route[y], route[y][3]
    possible = [routes[x]]
    for r_l in routes:
        if r_l == routes[x]:
            continue
        if r_l[1] + demand <= SPEC['CAPACITY']:
            possible.append(r_l)
    x_new = random.randint(0, len(possible) - 1)  # new route
    route_new = possible[x_new][0]
    y_new = random.randint(0, len(route_new))  # new position in that new route
    # start single insertion
    route.insert(0, (SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    route.append((SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    y += 1
    pre_tail, next_head = route[y - 1][1], route[y + 1][0]
    target[1] -= COST[pre_tail][edge[0]]
    target[1] -= COST[edge[1]][next_head]
    target[1] += COST[pre_tail][next_head]
    route.pop(y)
    route.pop(0)
    route.pop()
    y -= 1
    if route == route_new and y < y_new:
        y_new -= 1
    route_new.insert(0, (SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    route_new.append((SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    y_new += 1
    route_new.insert(y_new, edge)
    pre_tail, next_head = route_new[y_new - 1][1], route_new[y_new + 1][0]
    target[1] -= COST[pre_tail][next_head]
    target[1] += COST[pre_tail][edge[0]]
    target[1] += COST[edge[1]][next_head]
    route_new.pop(0)
    route_new.pop()
    # re-compute route load
    routes[x][1] -= demand
    possible[x_new][1] += demand
    # detect if old route is empty
    if not route:
        routes.pop(x_in_routes)


def double_insertion(target):
    routes = target[0]
    # the first route
    possible = []
    for r_l in routes:
        if len(r_l[0]) >= 2:
            possible.append(r_l)
    if len(possible) == 0:
        return
    x = random.randint(0, len(possible) - 1)  # which route
    x_in_routes = routes.index(possible[x])
    route = possible[x][0]
    y = random.randint(0, len(route) - 2)  # which consecutive edges in that route
    edge1, edge2, demand = route[y], route[y + 1], route[y][3] + route[y + 1][3]
    # recompute route load
    possible[x][1] -= demand
    # the second route
    possible = []
    for r_l in routes:
        if r_l[1] + demand <= SPEC['CAPACITY']:
            possible.append(r_l)
    x_new = random.randint(0, len(possible) - 1)  # new route
    route_new = possible[x_new][0]
    y_new = random.randint(0, len(route_new))  # new position in that new route
    # re-compute route load
    possible[x_new][1] += demand
    # start the double insertion and recompute cost
    route.insert(0, (SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    route.append((SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    y += 1
    pre_tail, next_head = route[y - 1][1], route[y + 2][0]
    target[1] -= COST[pre_tail][edge1[0]]
    target[1] -= COST[edge2[1]][next_head]
    target[1] += COST[pre_tail][next_head]
    route.pop(y)
    route.pop(y)
    route.pop(0)
    route.pop()
    y -= 1
    if route == route_new:
        if y + 1 == y_new:
            y_new -= 1
        elif y + 1 < y_new:
            y_new -= 2
    route_new.insert(y_new, edge1)
    route_new.insert(y_new + 1, edge2)
    route_new.insert(0, (SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    route_new.append((SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    y_new += 1
    pre_tail, next_head = route_new[y_new - 1][1], route_new[y_new + 2][0]
    target[1] -= COST[pre_tail][next_head]
    target[1] += COST[pre_tail][edge1[0]]
    target[1] += COST[edge2[1]][next_head]
    route_new.pop(0)
    route_new.pop()
    # detect if old route is empty
    if not route:
        routes.pop(x_in_routes)


def swap(target):
    routes = target[0]
    # generate the first edge
    x1 = random.randint(0, len(routes) - 1)  # which route
    route1, load1 = routes[x1][0], routes[x1][1]
    y1 = random.randint(0, len(route1) - 1)  # which edge in that route
    edge1, demand1 = route1[y1], route1[y1][3]
    # generate the possible second edge list
    possible = []
    for i in range(len(routes)):
        if i == x1:
            continue
        load2 = routes[i][1]
        for j in range(len(routes[i][0])):
            edge = routes[i][0][j]
            demand2 = edge[3]
            if load1 - demand1 + demand2 <= SPEC['CAPACITY'] and load2 - demand2 + demand1 <= SPEC['CAPACITY']:
                possible.append((i, j))
    if len(possible) == 0:
        return
    # generate the second edge
    pos = random.choice(possible)
    x2, y2 = pos[0], pos[1]  # second route and second edge
    route2, load2 = routes[x2][0], routes[x2][1]
    edge2, demand2 = route2[y2], route2[y2][3]
    # start the swap
    route1.pop(y1)
    route2.pop(y2)
    route1.insert(y1, edge2)
    route2.insert(y2, edge1)
    # temporary insert two couples of assistant edges (4 edges total) and adjust the index
    route1.insert(0, (SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    route1.append((SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    route2.insert(0, (SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    route2.append((SPEC['DEPOT'], SPEC['DEPOT'], 0, 0))
    y1, y2 = y1 + 1, y2 + 1
    # re-compute total cost
    pre_tail, next_head = route1[y1 - 1][1], route1[y1 + 1][0]
    target[1] -= COST[pre_tail][edge1[0]]
    target[1] -= COST[edge1[1]][next_head]
    target[1] += COST[pre_tail][edge2[0]]
    target[1] += COST[edge2[1]][next_head]
    pre_tail, next_head = route2[y2 - 1][1], route2[y2 + 1][0]
    target[1] -= COST[pre_tail][edge2[0]]
    target[1] -= COST[edge2[1]][next_head]
    target[1] += COST[pre_tail][edge1[0]]
    target[1] += COST[edge1[1]][next_head]
    # re-compute route load
    routes[x1][1] = routes[x1][1] - demand1 + demand2
    routes[x2][1] = routes[x2][1] - demand2 + demand1
    # delete the temporarily inserted edges
    route1.pop(0)
    route1.pop()
    route2.pop(0)
    route2.pop()


def two_opt_single(target):
    pass


class GAProblem(ABC):
    @abstractmethod
    def init_population(self, pop_size): pass

    @abstractmethod
    def fitness(self, sample): pass

    @abstractmethod
    def reproduce(self, population, pop_size): pass

    @abstractmethod
    def replacement(self, old, new): pass

    @abstractmethod
    def mutate(self, sample): pass

    @abstractmethod
    def select(self, population): pass

    @abstractmethod
    def crossover(self, p1, p2): pass


class CARP(GAProblem):
    def __init__(self, mutation):
        self.mutation = mutation

    def init_population(self, pop_size):
        population = []
        for _ in range(pop_size):
            r_c = path_scanning()
            population.append([r_c[0], r_c[1]])
        return population

    def fitness(self, sample):
        return -sample[1]

    def mutate(self, sample):
        p = random.random()
        if p > self.mutation:
            return
        n = random.randint(1, 4)
        if n == 1:
            flip(sample)
        elif n == 2:
            single_insertion(sample)
        elif n == 3:
            double_insertion(sample)
        elif n == 4:
            flip(sample)

    def select(self, population):
        tournament = random.choices(population, k=3)
        tournament.sort(key=lambda x: x[1], reverse=False)
        return tournament[0], tournament[1]

    def crossover(self, p1, p2):
        global SPEC
        offspring = [[[route_load[0].copy(), route_load[1]] for route_load in p1[0]], p1[1]]
        routes = offspring[0]
        possible = []
        for r_l in routes:
            if len(r_l[0]) >= 2:
                possible.append(r_l)
        if len(possible) == 0:
            return offspring
        x = random.randint(0, len(possible) - 1)  # which route
        x_in_routes = routes.index(possible[x])
        route, load = possible[x][0], possible[x][1]
        y = random.randint(0, len(route) - 1)  # two sub routes in that route
        subr11, subr12 = route[:y], route[y:]
        demand12 = sum([edge[3] for edge in subr12])
        # generate the possible second sub route list
        routes_new = p2[0]
        possible = []
        for i in range(len(routes_new)):
            if len(routes_new[i][0]) >= 2:
                n = random.randint(1, len(routes[i][0]) - 1)
                subr21, subr22 = routes_new[i][0][:n], routes_new[i][0][n:]
                demand22 = sum([edge[3] for edge in subr22])
                if subr22 != [] and load - demand12 + demand22 <= SPEC['CAPACITY']:
                    possible.append(subr22)
        if len(possible) == 0:
            return offspring
        # remove the route cost and load
        routes[x_in_routes][1] = 0
        if len(route) == 1:
            offspring[1] -= COST[SPEC['DEPOT']][route[0][0]]
            offspring[1] -= route[0][2]
            offspring[1] -= COST[route[0][1]][SPEC['DEPOT']]
        else:
            for i in range(len(route)):
                if i == 0:
                    offspring[1] -= COST[SPEC['DEPOT']][route[i][0]]
                    offspring[1] -= route[i][2]
                elif i == len(route) - 1:
                    offspring[1] -= COST[route[i - 1][1]][route[i][0]]
                    offspring[1] -= route[i][2]
                    offspring[1] -= COST[route[i][1]][SPEC['DEPOT']]
                else:
                    offspring[1] -= COST[route[i - 1][1]][route[i][0]]
                    offspring[1] -= route[i][2]
        pos = random.randint(0, len(possible) - 1)  # two sub routes in that route
        route[y:] = possible[pos]
        subr22 = possible[pos].copy()
        missing = []
        for edge in subr12:
            if edge in subr22:
                subr22.remove(edge)
            else:
                missing.append(edge)
        duplicate = subr22
        for edge in duplicate:
            route.remove(edge)
        # add the route cost and load
        if len(route) == 1:
            offspring[1] += COST[SPEC['DEPOT']][route[0][0]]
            offspring[1] += route[0][2]
            offspring[1] += COST[route[0][1]][SPEC['DEPOT']]
            routes[x_in_routes][1] += route[0][3]
        else:
            for i in range(len(route)):
                routes[x_in_routes][1] += route[i][3]
                if i == 0:
                    offspring[1] += COST[SPEC['DEPOT']][route[i][0]]
                    offspring[1] += route[i][2]
                elif i == len(route) - 1:
                    offspring[1] += COST[route[i - 1][1]][route[i][0]]
                    offspring[1] += route[i][2]
                    offspring[1] += COST[route[i][1]][SPEC['DEPOT']]
                else:
                    offspring[1] += COST[route[i - 1][1]][route[i][0]]
                    offspring[1] += route[i][2]
        # deal with the missing edge here
        for edge in missing:
            record = []
            for i in range(len(routes)):
                r_l = routes[i]
                r = r_l[0]
                if not r:
                    record.append((i, 0,
                                   COST[SPEC['DEPOT']][edge[0]] + edge[2] + COST[edge[1]][SPEC['DEPOT']],
                                   SPEC['CAPACITY'] - edge[3]))
                elif r_l[1] + edge[3] <= SPEC['CAPACITY']:
                    for pos in range(len(r) + 1):
                        if pos == 0:
                            record.append((i, pos,
                                           COST[SPEC['DEPOT']][edge[0]] + edge[2] + COST[edge[1]][r[pos][0]],
                                           SPEC['CAPACITY'] - r_l[1] - edge[3]))
                        elif pos == len(r):
                            record.append((i, pos,
                                           COST[r[pos - 1][1]][edge[0]] + edge[2] + COST[edge[1]][SPEC['DEPOT']],
                                           SPEC['CAPACITY'] - r_l[1] - edge[3]))
                        else:
                            record.append((i, pos,
                                           COST[r[pos - 1][1]][edge[0]] + edge[2] + COST[edge[1]][r[pos][0]],
                                           SPEC['CAPACITY'] - r_l[1] - edge[3]))
            if record:
                record.sort(key=lambda ele: (ele[2], -ele[3]))
                info = record[0]
                routes[info[0]][0].insert(info[1], edge)
                routes[info[0]][1] += edge[3]
                offspring[1] += info[2]
            else:
                routes.append([[edge], edge[3]])
        if not routes[x_in_routes][0]:
            routes.pop(x_in_routes)
        return offspring

    def reproduce(self, population, pop_size):
        global START_TIME, TIMEOUT
        offsprings = []
        while len(offsprings) != pop_size:
            now = time.time()
            if now - START_TIME > TIMEOUT - 5:
                break
            p1, p2 = self.select(population)
            n = random.random()
            if n > 1:
                n = random.random()
                if n > 0.8:
                    offspring = self.crossover(p1, p2)
                else:
                    offspring = self.crossover(p2, p1)
            else:
                offspring = [[[route_load[0].copy(), route_load[1]] for route_load in p1[0]], p1[1]]
            self.mutate(offspring)
            offsprings.append(offspring)
        return offsprings

    def replacement(self, old, new):
        return new


def genetic_algorithm(problem: GAProblem, pop_size, log_interval=100):
    global START_TIME, TIMEOUT
    population = problem.init_population(4000)
    best = max(population, key=problem.fitness)

    gen = 0
    while True:
        gen += 1
        now = time.time()
        if now - START_TIME > TIMEOUT - 5:
            break
        next_gen = problem.reproduce(population, pop_size)
        population = problem.replacement(population, next_gen)
        if population:
            current_best = max(population, key=problem.fitness)
            if problem.fitness(current_best) > problem.fitness(best):
                best = current_best

        if gen % log_interval == 0:
            pass
            # print(f"Generation: {gen},\tBest Fitness={-problem.fitness(best)}")

    return best


def gen_output(answer):
    routes = answer[0]
    quality = int(answer[1])
    out = 's '
    for route in routes:
        out += '0,'
        for edge in route[0]:
            out += '(' + str(edge[0]) + ',' + str(edge[1]) + '),'
        out += '0,'
    out = out.rstrip(',')
    out += '\nq ' + str(quality)
    return out


if __name__ == '__main__':
    global START_TIME, TIMEOUT, COST, FREE, SPEC, BEST
    answer_list = []
    # START_TIME = time.time()
    # args = arg_parse()
    # TIMEOUT = args.t
    # random.seed(args.s)
    # load_data(args.file)
    for i in range(1, 24):
        file = './benchmark/gdb/gdb{}.dat'.format(i)
        SPEC = {}
        COST = []
        FREE = set()
        BEST = None
        START_TIME = time.time()
        TIMEOUT = 60
        random.seed(1)
        load_data(file)
        floyd()
        max_population = 1000
        mutation_rate = 0.9
        carp = CARP(mutation_rate)
        solution = genetic_algorithm(carp, max_population)
        answer_list.append(int(solution[1]))
        output = gen_output(solution)
        print(output)
    temp = ['A', 'B', 'C']
    for i in range(1, 5):
        for ele in temp:
            file = './benchmark/egl/egl-e{}-{}.dat'.format(i, ele)
            SPEC = {}
            COST = []
            FREE = set()
            BEST = None
            START_TIME = time.time()
            TIMEOUT = 60
            random.seed(1)
            load_data(file)
            floyd()
            max_population = 1000
            mutation_rate = 0.9
            carp = CARP(mutation_rate)
            solution = genetic_algorithm(carp, max_population)
            answer_list.append(int(solution[1]))
            output = gen_output(solution)
            print(output)
    temp = ['A', 'B', 'C']
    for i in range(1, 5):
        for ele in temp:
            file = './benchmark/egl/egl-s{}-{}.dat'.format(i, ele)
            SPEC = {}
            COST = []
            FREE = set()
            BEST = None
            START_TIME = time.time()
            TIMEOUT = 60
            random.seed(1)
            load_data(file)
            floyd()
            max_population = 1000
            mutation_rate = 0.9
            carp = CARP(mutation_rate)
            solution = genetic_algorithm(carp, max_population)
            answer_list.append(int(solution[1]))
            output = gen_output(solution)
            print(output)
    for an in answer_list:
        print(an, end=' ')
