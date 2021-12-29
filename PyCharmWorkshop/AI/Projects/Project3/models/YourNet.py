from torch import nn
import torch.nn.functional as F


class SeparableConv2d(nn.Module):

    def __init__(self, in_channels, out_channels, kernel_size, stride, padding, bias=False):
        super(SeparableConv2d, self).__init__()
        if padding == 'same':
            padding = 1
        self.depth_wise = nn.Conv2d(in_channels, in_channels, kernel_size=kernel_size, stride=stride,
                                    groups=in_channels, bias=bias, padding=padding)
        self.point_wise = nn.Conv2d(in_channels, out_channels,
                                    kernel_size=(1, 1), bias=bias)

    def forward(self, x):
        out = self.depth_wise(x)
        out = self.point_wise(out)
        return out


class YourNet(nn.Module):
    def __init__(self):
        super(YourNet, self).__init__()
        self.conv1 = nn.Conv2d(1, 6, (3, 3))
        self.conv2 = nn.Conv2d(6, 10, (3, 3))
        self.fc1 = nn.Linear(10 * 5 * 5, 10)
        # self.fc2 = nn.Linear(60, 16)
        # self.fc3 = nn.Linear(16, 10)

    def forward(self, x):
        x = F.max_pool2d(F.relu(self.conv1(x)), (2, 2))
        x = F.max_pool2d(F.relu(self.conv2(x)), (2, 2))
        x = x.view(-1, int(x.nelement() / x.shape[0]))
        x = self.fc1(x)
        # x = F.relu(self.fc1(x))
        # x = F.relu(self.fc2(x))
        # x = self.fc3(x)
        return x

    # def __init__(self):
    #     super(YourNet, self).__init__()
    #     self.conv1 = nn.Conv2d(1, 4, (3, 3), stride=(1, 1), padding='valid')
    #     self.conv2 = nn.Conv2d(4, 6, (3, 3), stride=(2, 2), padding='valid')
    #     self.conv3 = SeparableConv2d(6, 8, (3, 3), stride=(2, 2), padding='valid')
    #     self.conv4 = SeparableConv2d(8, 10, (3, 3), stride=(2, 2), padding='same')
    #     self.conv5 = SeparableConv2d(10, 12, (3, 3), stride=(2, 2), padding='valid')
    #     self.conv6 = nn.Conv2d(12, 10, (1, 1), stride=(1, 1), padding='same')
    #
    # def forward(self, x):
    #     x = nn.ZeroPad2d(padding=(2, 2, 2, 2))(x)
    #     x = F.relu(self.conv1(x))
    #     x = F.relu(self.conv2(x))
    #     x = F.relu(self.conv3(x))
    #     x = F.relu(self.conv4(x))
    #     x = F.relu(self.conv5(x))
    #     x = F.softmax(self.conv6(x))
    #     x = x.view(-1, int(x.nelement() / x.shape[0]))
    #     print(x.shape)
    #     exit()
    #     return x
