"""
Train LeNet5

Example:
python train_lenet5.py \
  --checkpoint-dir ./checkpoints/LeNet5/ \
  --epoch-end 10 \
  --device cpu
"""
import argparse
import os
import torch

from torch import nn
from torchvision import datasets, transforms

from models.YourNet import YourNet
from eval.metrics import get_accuracy

import numpy as np
import random

# --------------- Arguments ---------------

parser = argparse.ArgumentParser()

parser.add_argument('--checkpoint-dir', type=str, required=True)
parser.add_argument('--last-checkpoint', type=str, default=None)

parser.add_argument('--device', type=str, choices=['cpu', 'cuda'], default='cpu')
parser.add_argument('--batch-size', type=int, default=64)
parser.add_argument('--epoch-start', type=int, default=0)
parser.add_argument('--epoch-end', type=int, required=True)

args = parser.parse_args()


def setup_seed(seed):
    torch.manual_seed(seed)
    torch.cuda.manual_seed_all(seed)
    np.random.seed(seed)
    random.seed(seed)
    torch.backends.cudnn.deterministic = True


# --------------- Loading ---------------

def train(model, train_loader, test_loader, optimizer, loss_fn):
    scheduler = torch.optim.lr_scheduler.ReduceLROnPlateau(optimizer, mode='min', factor=0.1, patience=10, verbose=True,
                                                           threshold=0.0001, threshold_mode='rel', cooldown=0, min_lr=0,
                                                           eps=1e-8)
    acc = 0
    for epoch in range(args.epoch_start, args.epoch_end):
        print(f"Epoch {epoch}\n-------------------------------")
        size = len(train_loader.dataset)
        model.train()
        running_loss = 0
        for batch_idx, (X, y) in enumerate(train_loader):

            X, y = X.to(args.device), y.to(args.device)

            # Compute prediction error
            pred_y = model(X)
            loss = loss_fn(pred_y, y)

            # Backpropagation
            optimizer.zero_grad()
            loss.backward()
            optimizer.step()

            # accumulate batch loss
            running_loss += loss.item()

            if batch_idx % 200 == 199:
                avg_loss = running_loss / 200
                scheduler.step(avg_loss)
                current = batch_idx * len(X)
                print(f"average loss: {avg_loss:>7f}  [{current:>5d}/{size:>5d}]")
                running_loss = 0

        accuracy = get_accuracy(model, test_loader, args.device)
        print("Accuracy: %.3f}" % accuracy)

        if accuracy > acc:
            acc = accuracy
            torch.save(model.state_dict(), args.checkpoint_dir + 'epoch-best.pth')


if __name__ == '__main__':
    # setup_seed(3)

    train_loader = torch.utils.data.DataLoader(
        datasets.MNIST(root='./data', train=True, download=False,
                       transform=transforms.Compose([
                           transforms.ToTensor(),
                           transforms.Normalize((0.1307,), (0.3081,))
                       ])),
        batch_size=args.batch_size, shuffle=True)

    test_loader = torch.utils.data.DataLoader(
        datasets.MNIST(root='./data', train=False,
                       transform=transforms.Compose([
                           transforms.ToTensor(),
                           transforms.Normalize((0.1307,), (0.3081,))
                       ])),
        batch_size=args.batch_size, shuffle=True)

    model = YourNet().to(device=args.device)

    if args.last_checkpoint is not None:
        model.load_state_dict(torch.load(args.last_checkpoint, map_location=args.device))

    optimizer = torch.optim.Adam(model.parameters(), lr=0.01)
    loss_fn = nn.CrossEntropyLoss()

    if not os.path.exists(args.checkpoint_dir):
        os.makedirs(args.checkpoint_dir)

    train(model, train_loader, test_loader, optimizer, loss_fn)
