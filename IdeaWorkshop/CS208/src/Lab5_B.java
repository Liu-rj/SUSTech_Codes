import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Lab5_B {
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(in.nextLong());
        }
        int u, v;
        long cost;
        for (int i = 0; i < n - 1; i++) {
            u = in.nextInt() - 1;
            v = in.nextInt() - 1;
            cost = in.nextLong();
            nodes[u].neighbors.add(nodes[v]);
            nodes[u].costs.add(cost);
            nodes[v].neighbors.add(nodes[u]);
            nodes[v].costs.add(cost);
        }
        pureProfit(nodes[0], null);
        long result = restTime(nodes[0], null);
        out.println(result);
        out.close();
    }

    public static long restTime(Node node, Node root) {
        node.hp += node.food;
        long rest = 0;
        Node neighbor;
        long cost;
        int index;
        if (!(node.neighbors.size() == 1 && node.neighbors.get(0) == root)) {
            Queue<Node> upper = new PriorityQueue<>(new Comparator<Node>() {
                public int compare(Node u, Node v) {
                    return (int) (u.cost - v.cost);
                }
            });
            Queue<Node> lower = new PriorityQueue<>(new Comparator<Node>() {
                public int compare(Node u, Node v) {
                    return (int) (v.income - u.income);
                }
            });
            for (int i = 0; i < node.neighbors.size(); i++) {
                neighbor = node.neighbors.get(i);
                if (neighbor.pureProfit > 0) {
                    neighbor.cost = node.costs.get(i);
                    upper.add(neighbor);
                } else {
                    neighbor.income = neighbor.food - node.costs.get(i);
                    lower.add(neighbor);
                }
            }
            while (upper.size() != 0) {
                neighbor = upper.poll();
                if (neighbor == root) {
                    continue;
                }
                index = node.neighbors.indexOf(neighbor);
                cost = node.costs.get(index);
                if (node.hp < cost) {
                    neighbor.hp = 0;
                    rest += cost - node.hp;
                } else {
                    neighbor.hp = node.hp - cost;
                }
                rest += restTime(neighbor, node);
            }
            while (lower.size() != 0) {
                neighbor = lower.poll();
                if (neighbor == root) {
                    continue;
                }
                index = node.neighbors.indexOf(neighbor);
                cost = node.costs.get(index);
                if (node.hp < cost) {
                    neighbor.hp = 0;
                    rest += cost - node.hp;
                } else {
                    neighbor.hp = node.hp - cost;
                }
                rest += restTime(neighbor, node);
            }
        }
        if (root != null) {
            index = node.neighbors.indexOf(root);
            cost = node.costs.get(index);
            if (node.hp < cost) {
                root.hp = 0;
                rest += cost - node.hp;
            } else {
                root.hp = node.hp - cost;
            }
        }
        return rest;
    }

    public static long pureProfit(Node node, Node root) {
        if (node.neighbors.size() == 1 && node.neighbors.get(0) == root) {
            node.pureProfit = node.food - 2 * node.costs.get(node.neighbors.indexOf(root));
            return node.pureProfit;
        }
        Node neighbor;
        for (int i = 0; i < node.neighbors.size(); i++) {
            neighbor = node.neighbors.get(i);
            if (neighbor != root) {
                node.pureProfit += pureProfit(neighbor, node);
            }
        }
        if (root != null) {
            node.pureProfit += node.food - 2 * node.costs.get(node.neighbors.indexOf(root));
        } else {
            node.pureProfit += node.food;
        }
        return node.pureProfit;
    }

    static class Node {
        long food;
        long pureProfit;
        long hp;
        long cost;
        long income;
        ArrayList<Node> neighbors;
        ArrayList<Long> costs;

        Node(long food) {
            this.food = food;
            neighbors = new ArrayList<>();
            costs = new ArrayList<>();
            pureProfit = 0;
            hp = 0;
            cost = 0;
            income = 0;
        }
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}