import java.util.Arrays;
import java.io.*;

public class Lab2 {
    public static void main(String[] args) throws IOException {
        Reader scanner = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int T = scanner.nextInt();
        int n;
        for (int i = 0; i < T; i++) {
            n = scanner.nextInt();
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = scanner.nextInt();
            }
            Arrays.sort(a);
            long result;
            long temp = 0;
            int index = n - 2, count = 1;
            int[] nums = new int[3];
            nums[0] = a[n - 1];
            while (index >= 0 && count < 3) {
                for (int j = 0; j < count; j++) {
                    if (nums[j] % a[index] == 0) {
                        break;
                    }
                    if (j == count - 1) {
                        nums[count++] = a[index];
                    }
                }
                index--;
            }
            result = nums[0] + nums[1] + nums[2];
            boolean half = true, oneThird = true, oneFifth = true;
            for (int j = 0; j < n - 1; j++) {
                if (a[j] == a[n - 1] / 2 && half) {
                    temp += a[j];
                    half = false;
                } else if (a[j] == a[n - 1] / 3 && oneThird) {
                    temp += a[j];
                    oneThird = false;
                } else if (a[j] == a[n - 1] / 5 && oneFifth) {
                    temp += a[j];
                    oneFifth = false;
                }
                if (!(half || oneThird || oneFifth)) {
                    break;
                }
            }
            if (temp > result) {
                result = temp;
            }
            System.out.println(result);
        }
        out.close();
    }

    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
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

        public double nextDouble() throws IOException
        {
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

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
}
