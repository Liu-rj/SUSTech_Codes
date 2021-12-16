import copy
import numpy as np


def xavier(ndarray):
    np.random.seed(1)
    n1 = ndarray.shape[0]
    n2 = ndarray.shape[1]
    x = np.sqrt(6 * 1.0 / (n1 + n2))
    ndarray = np.random.uniform(low=-x, high=x, size=(n1, n2))
    return ndarray


def gaussian(ndarray):
    np.random.seed(1)
    n1 = ndarray.shape[0]
    n2 = ndarray.shape[1]
    ndarray = np.random.normal(loc=0.0, scale=0.1, size=(n1, n2))
    return ndarray


def relu(ndarray):
    return np.maximum(ndarray, 0)


def d_relu(ndarray):
    result = copy.deepcopy(ndarray)
    result[result > 0] = 1
    return result


def softmax(ndarray):
    r = ndarray.shape[0]
    c = ndarray.shape[1]
    max_arr = np.tile(ndarray.max(axis=1).reshape(r, 1), (1, c))
    e_x = np.exp(ndarray - max_arr)
    return e_x / np.tile(e_x.sum(axis=1).reshape(r, 1), (1, c))


def loss_d_z(label, output):
    return (output - label).reshape(1, output.shape[0])


def getLabel(ndarray, BATCHSIZE, num_output):
    label = np.zeros((BATCHSIZE, num_output))
    for i in range(0, BATCHSIZE):
        idx = ndarray[i]
        label[i][idx] = 1
    return label
