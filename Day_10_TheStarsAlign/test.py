from itertools import *
import re


x, y, vx, vy = [], [], [], []
for line in open('coordinates_input.txt'):
    for l, v in zip([x, y, vx, vy], map(int, re.findall(r'-?\d+', line))):
        l.append(v)
n = len(x)


def print_result():
    mx, my = min(x), min(y)
    w = max(x) - mx + 1
    h = max(y) - my + 1
    if h > 10:
        return False
    t = [['.'] * w for _ in range(h)]
    for i in range(n):
        t[y[i] - my][x[i] - mx] = '#'
    for l in t:
        print(''.join(l))
    print()
    return True


for t in count(1):
    for i in range(n):
        x[i] += vx[i]
        y[i] += vy[i]
    if print_result():
        print(t)
        break