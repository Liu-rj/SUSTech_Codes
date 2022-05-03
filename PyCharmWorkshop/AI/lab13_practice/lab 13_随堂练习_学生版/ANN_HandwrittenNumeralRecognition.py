import os
import numpy as np
import pickle

from utils import xavier, gaussian, relu, d_relu, softmax, getLabel, loss_d_z
import matplotlib

print(matplotlib.get_backend())
# matplotlib.use('Agg')
import matplotlib.pyplot as plt


def Parameter_initialization(num_input, num_layer1, num_layer2, num_output):
    Parameter = {}
    # Your code starts here
    # Please Initialize all parameters used in ANN-Hidden Layers with Xavier
    w1 = xavier(np.zeros((num_input, num_layer1)))
    b1 = xavier(np.zeros((1, num_layer1)))
    w2 = xavier(np.zeros((num_layer1, num_layer2)))
    b2 = xavier(np.zeros((1, num_layer2)))
    w3 = xavier(np.zeros((num_layer2, num_output)))
    b3 = xavier(np.zeros((1, num_output)))
    # Your code ends here
    Parameter['w1'] = w1
    Parameter['b1'] = b1
    Parameter['w2'] = w2
    Parameter['b2'] = b2
    Parameter['w3'] = w3
    Parameter['b3'] = b3
    return Parameter


def Hidden_Layer(x, w, b, batch_size):
    # Your code starts here
    a = relu(np.dot(x, w) + np.tile(b, (batch_size, 1)))
    # Your code ends here
    return a


def Output_Layer(x, w, b, batch_size):
    # Your code starts here
    a = softmax(np.dot(x, w) + np.tile(b, (batch_size, 1)))
    # Your code ends here
    return a


def Loss(label, logits):
    # lable : Actual label  BATCHSIZE *class
    # logits : The predicted results of your model
    # Your code starts here
    idx = np.where(label == 1)
    idx = list(zip(idx[0], idx[1]))
    loss_temp = np.sum([-np.log(logits[x] + 1e-7) for x in idx]) / label.shape[0]
    # Your code ends here
    return loss_temp


def Back_propagation(logits, label, w1, b1, w2, b2, w3, b3, a2, a1, image_blob):
    # label : Actual label  BATCHSIZE * class
    # logits : The predicted results of your model
    # Your code starts here
    d_w1 = np.zeros(w1.shape)
    d_b1 = np.zeros(b1.shape)
    d_w2 = np.zeros(w2.shape)
    d_b2 = np.zeros(b2.shape)
    d_w3 = np.zeros(w3.shape)
    d_b3 = np.zeros(b3.shape)
    for i in range(label.shape[0]):
        # output layer
        loss_dz3 = loss_d_z(label[i], logits[i])  # 1 * feature
        d_w3 += np.dot(a2[i].reshape(a2[i].shape[0], 1), loss_dz3)
        d_b3 += loss_dz3
        # 2nd hidden layer
        loss_dz2 = np.dot(loss_dz3, w3.transpose())  # 1 * feature
        loss_dz2 *= d_relu(a2[i])
        d_w2 += np.dot(a1[i].reshape(a1[i].shape[0], 1), loss_dz2)
        d_b2 += loss_dz2
        # 1st hidden layer
        loss_dz1 = np.dot(loss_dz2, w2.transpose())  # 1 * feature
        loss_dz1 *= d_relu(a1[i])
        d_w1 += np.dot(image_blob[i].reshape(image_blob[i].shape[0], 1), loss_dz1)
        d_b1 += loss_dz1
    # Your code ends here
    return d_w1, d_b1, d_w2, d_b2, d_w3, d_b3


if __name__ == '__main__':
    (train_images, train_labels, test_images, test_labels) = pickle.load(open('data.pkl', 'rb'), encoding='latin1')

    EPOCH = 100
    ITERS = 100
    BATCHSIZE = 100
    LR_BASE = 0.1
    k = 0.0005  # lambda
    num_input = 784
    num_layer1 = 300
    num_layer2 = 100
    num_output = 10
    ### 1. Data preprocessing: normalize all pixels to [0,1) by dividing 256
    train_images = train_images / 256.0
    test_images = test_images / 256.0
    print(type(train_images[0][0]))

    ### 2. Weight initialization: Xavier
    Parameter = Parameter_initialization(num_input, num_layer1, num_layer2, num_output)
    w1, b1, w2, b2, w3, b3 = Parameter['w1'], Parameter['b1'], Parameter['w2'], Parameter['b2'], Parameter['w3'], \
                             Parameter['b3']

    ### 3. training of neural network
    loss = np.zeros((EPOCH))  # save the loss of each epoch
    accuracy = np.zeros((EPOCH))  # save the accuracy of each epoch
    for epoch in range(0, EPOCH):
        if epoch <= EPOCH / 2:
            lr = LR_BASE
        else:
            lr = LR_BASE / 10.0
        for iters in range(0, ITERS):
            image_blob = train_images[iters * BATCHSIZE:(iters + 1) * BATCHSIZE, :]  # 100*784
            label_blob = train_labels[iters * BATCHSIZE:(iters + 1) * BATCHSIZE]  # 100*1
            label = getLabel(label_blob, BATCHSIZE, num_output)
            # Forward propagation  Hidden Layer
            a1 = Hidden_Layer(image_blob, w1, b1, BATCHSIZE)
            a2 = Hidden_Layer(a1, w2, b2, BATCHSIZE)
            # Forward propagation  output Layer
            a3 = Output_Layer(a2, w3, b3, BATCHSIZE)
            # if np.count_nonzero(a3) != 1000:
            #     print(a3)
            # comupte loss
            loss_tmp = Loss(label, a3)
            if iters % 100 == 99:
                loss[epoch] = loss_tmp
                print('Epoch ' + str(epoch + 1) + ': ')
                print(loss_tmp)
            # Back propagation
            d_w1, d_b1, d_w2, d_b2, d_w3, d_b3 = Back_propagation(a3, label, w1, b1, w2, b2, w3, b3, a2, a1, image_blob)

            # Gradient update
            w1 = w1 - lr * d_w1 / BATCHSIZE - lr * k * w1
            b1 = b1 - lr * d_b1 / BATCHSIZE
            w2 = w2 - lr * d_w2 / BATCHSIZE - lr * k * w2
            b2 = b2 - lr * d_b2 / BATCHSIZE
            w3 = w3 - lr * d_w3 / BATCHSIZE - lr * k * w3
            b3 = b3 - lr * d_b3 / BATCHSIZE

            # Testing for accuracy
            if iters % 100 == 99:
                z1 = np.dot(test_images, w1) + np.tile(b1, (1000, 1))
                a1 = relu(z1)
                z2 = np.dot(a1, w2) + np.tile(b2, (1000, 1))
                a2 = relu(z2)
                z3 = np.dot(a2, w3) + np.tile(b3, (1000, 1))
                a3 = softmax(z3)  # 1000*10
                predict = np.argmax(a3, axis=1)
                print('Accuracy: ')
                accuracy[epoch] = 1 - np.count_nonzero(predict - test_labels) * 1.0 / 1000
                print(accuracy[epoch])

    ### 4. Plot
    plt.figure(figsize=(12, 5))
    ax1 = plt.subplot(121)
    ax1.plot(np.arange(EPOCH) + 1, loss[0:], 'r', label='Loss', linewidth=2)
    plt.xlabel('Epoch', fontsize=16)
    plt.ylabel('Loss on trainSet', fontsize=16)
    plt.grid()
    ax2 = plt.subplot(122)
    ax2.plot(np.arange(EPOCH) + 1, accuracy[0:], 'b', label='Accuracy', linewidth=2)
    plt.xlabel('Epoch', fontsize=16)
    plt.ylabel('Accuracy on trainSet', fontsize=16)
    plt.grid()
    plt.tight_layout()
    plt.savefig('figure.pdf', dbi=300)
    plt.show()
