import java.io.*;
import java.util.ArrayList;

public class Lab4_B {
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(in.nextLong());
        }
        int a, b;
        for (int i = 0; i < n - 1; i++) {
            a = in.nextInt() - 1;
            b = in.nextInt() - 1;
            nodes[a].neighbors.add(nodes[b]);
            nodes[b].neighbors.add(nodes[a]);
        }
        qSum(nodes[0], null);
        System.out.println(maxBeauty(nodes[0].neighbors.get(0), nodes[0], beautyRoot(nodes[0], null, 0)));
        out.close();
    }

    public static long qSum(Node node, Node root) {
        if (node.neighbors.size() == 1 && node.neighbors.get(0) == root) {
            node.sum = node.weight;
            return node.weight;
        }
        for (int i = 0; i < node.neighbors.size(); i++) {
            if (node.neighbors.get(i) != root) {
                node.sum += qSum(node.neighbors.get(i), node);
            }
        }
        node.sum += node.weight;
        return node.sum;
    }

    public static long beautyRoot(Node node, Node root, int index) {
        long beauty = node.weight * index;
        for (int i = 0; i < node.neighbors.size(); i++) {
            if (node.neighbors.get(i) != root) {
                beauty += beautyRoot(node.neighbors.get(i), node, index + 1);
            }
        }
        return beauty;
    }

    public static long maxBeauty(Node node, Node root, long beauty) {
        long local = beauty + root.sum - 2 * node.sum;
        root.sum -= node.sum;
        node.sum += root.sum;
        long localMax = local;
        for (int i = 0; i < node.neighbors.size(); i++) {
            if (node.neighbors.get(i) != root) {
                localMax = Math.max(maxBeauty(node.neighbors.get(i), node, local), localMax);
            }
        }
        node.sum -= root.sum;
        root.sum += node.sum;
        localMax = Math.max(local, localMax);
        return Math.max(localMax, beauty);
    }

    static class Node {
        long weight;
        long sum = 0;
        ArrayList<Node> neighbors;

        Node(long weight) {
            this.weight = weight;
            neighbors = new ArrayList<>();
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
