import java.io.*;

public class Lab9_B {
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        lanRan[] lanRans = new lanRan[n];
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            lanRans[i] = new lanRan(in.nextLong(), in.nextLong(), i);
            nums[i] = 0;
        }
        lanRans[0].happiness = 0;
        nums[0]++;
        mergeSort(0, n, lanRans);
        for (int i = 1; i < n; i++) {
            nums[lanRans[i].index - lanRans[i].happiness]++;
        }
        for (int i = 0; i < n; i++) {
            System.out.println(nums[i]);
        }
        out.close();
    }

    public static void mergeSort(int left, int right, lanRan[] lanRans) {
        if (right - left == 1) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(left, mid, lanRans);
        mergeSort(mid, right, lanRans);
        merge(left, mid, right, lanRans);
    }

    public static void merge(int left, int mid, int right, lanRan[] lanRans) {
        int i = left, j = mid;
        lanRan[] temp = new lanRan[right - left];
        for (int k = 0; k < right - left; k++) {
            if (j >= right || (i < mid && lanRans[i].a <= lanRans[j].a)) {
                temp[k] = lanRans[i++];
            } else {
                temp[k] = lanRans[j++];
                temp[k].happiness += mid - i;
            }
        }
        System.arraycopy(temp, 0, lanRans, left, right - left);
    }

    static class lanRan {
        long a;
        long b;
        int happiness;
        int index;

        lanRan(long a, long b, int index) {
            this.a = a;
            this.b = b;
            happiness = 0;
            this.index = index;
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
