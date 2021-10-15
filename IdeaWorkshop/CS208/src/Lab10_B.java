import java.io.*;
import java.util.Arrays;

public class Lab10_B {
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        Edge[] edges = new Edge[n - 1];
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }
        for (int i = 0; i < n - 1; i++) {
            edges[i] = new Edge(nodes[in.nextInt() - 1], nodes[in.nextInt() - 1], in.nextInt());
        }
        Arrays.sort(edges);
        long[] num = new long[edges[n - 2].w + 1];
        Node u, v, ru, rv, root;
        for (int i = 0; i < n - 1; i++) {
            u = edges[i].u;
            v = edges[i].v;
            ru = u.root;
            rv = v.root;
            while (ru.root != ru) {
                ru = ru.root;
            }
            while (rv.root != rv) {
                rv = rv.root;
            }
            if (ru == rv) {
                continue;
            }
            if (ru.height >= rv.height) {
                rv.root = ru;
                v.root = ru;
                if (ru.height == rv.height) {
                    ru.height++;
                }
            } else {
                ru.root = rv;
                u.root = rv;
            }
            root = ru.root;
            num[edges[i].w] += ru.nodeNum * rv.nodeNum;
            root.nodeNum = ru.nodeNum + rv.nodeNum;
        }
        for (int i = 1; i < num.length; i++) {
            num[i] += num[i - 1];
        }
        int pos;
        for (int i = 0; i < m; i++) {
            pos = in.nextInt();
            if (pos >= num.length) {
                System.out.print(num[num.length - 1] + " ");
            } else {
                System.out.print(num[pos] + " ");
            }
        }
        out.close();
    }

    static class Node {
        Node root;
        int height;
        long nodeNum;

        Node() {
            root = this;
            height = 1;
            nodeNum = 1;
        }
    }

    static class Edge implements Comparable<Edge> {
        Node u;
        Node v;
        int w;

        Edge(Node u, Node v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return this.w - o.w;
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
