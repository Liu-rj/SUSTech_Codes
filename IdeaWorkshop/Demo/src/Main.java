import java.io.*;
import java.util.ArrayList;

//快读模板1，较快，但无next()

public class Main {
    static long sums = 0, sumf = 0, sump = 0, result = 0;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int n = in.nextInt();
        int k;
        long s, f, p;
        ArrayList<Dish>[] kinds = new ArrayList[n];
        Max[] maxes = new Max[n];
        for (int i = 0; i < n; i++) {
            kinds[i] = new ArrayList<>();
            maxes[i] = new Max();
        }
        for (int i = 0; i < n; i++) {
            k = in.nextInt();
            s = in.nextLong();
            f = in.nextLong();
            p = in.nextLong();
            if (s <= maxes[k - 1].s && f <= maxes[k - 1].f && p <= maxes[k - 1].p) {
                continue;
            }
            Dish dish = new Dish();
            dish.s = s;
            dish.f = f;
            dish.p = p;
            kinds[k - 1].add(dish);
            if (s >= maxes[k - 1].s && f >= maxes[k - 1].f && p >= maxes[k - 1].p) {
                maxes[k - 1].s = s;
                maxes[k - 1].f = f;
                maxes[k - 1].p = p;
            }
        }
        findMax(kinds, 0, n);
        out.print(result);
        out.close();
    }

    static void findMax(ArrayList<Dish>[] kinds, int index, int size)
    {
        if (index == size)
        {
            if (sums * sumf * sump > result)
            {
                result = sums * sumf * sump;
            }
            return;
        }
        if (kinds[index].size() == 0)
        {
            findMax(kinds, index + 1, size);
            return;
        }
        for (int i = 0; i < kinds[index].size(); i++)
        {
            sums += kinds[index].get(i).s;
            sumf += kinds[index].get(i).f;
            sump += kinds[index].get(i).p;
            findMax(kinds, index + 1, size);
            sums -= kinds[index].get(i).s;
            sumf -= kinds[index].get(i).f;
            sump -= kinds[index].get(i).p;
        }
    }

    static class Dish {
        long s, f, p;
    }

    static class Max {
        long s = 0, f = 0, p = 0;
    }

    static class Reader {
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
