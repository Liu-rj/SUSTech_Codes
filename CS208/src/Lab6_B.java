import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Lab6_B {
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        int u, v;
        long w;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }
        for (int i = 0; i < m; i++) {
            u = in.nextInt() - 1;
            v = in.nextInt() - 1;
            w = in.nextLong();
            nodes[u].neighbors.add(nodes[v]);
            nodes[u].weights.add(w);
        }
        for (int i = 0; i < n; i++) {
            nodes[i].a = in.nextLong();
            nodes[i].b = in.nextLong();
        }
        nodes[0].time = 0;
        dij(nodes, nodes[n - 1]);
        out.println(nodes[n - 1].time);
        in.close();
        out.close();
    }

    public static void dij(Node[] nodes, Node dest) {
        PriorityQueue<Node> q = new PriorityQueue<>(new Comparator<Node>() {
            public int compare(Node u, Node v) {
                return Long.compare(u.time, v.time);
            }
        });
//        q.addAll(Arrays.asList(nodes));
        q.add(nodes[0]);
        while (!q.isEmpty()) {
            Node node = q.poll();
            if (node.flag) {
                continue;
            }
            node.flag = true;
            if (node == dest) {
                return;
            }
            for (int i = 0; i < node.neighbors.size(); i++) {
                Node neighbor = node.neighbors.get(i);
                long weight = node.weights.get(i);
                if (neighbor.flag) {
                    continue;
                }
                long wait = neighbor.a - (node.time + weight) % (neighbor.a + neighbor.b);
                if (wait > 0) {
                    weight += wait;
                }
                if (neighbor.time > node.time + weight) {
                    neighbor.time = node.time + weight;
                    q.add(neighbor);
                }
            }
        }
    }

    static class Node {
        ArrayList<Node> neighbors;
        ArrayList<Long> weights;
        long a;
        long b;
        long time;
        boolean flag;

        Node() {
            neighbors = new ArrayList<>();
            weights = new ArrayList<>();
            time = Long.MAX_VALUE;
            flag = false;
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
            }
            while ((c = read()) >= '0' && c <= '9');
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
            }
            while ((c = read()) >= '0' && c <= '9');

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
