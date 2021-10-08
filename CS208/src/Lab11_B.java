import java.io.*;
import java.util.Arrays;

public class Lab11_B {
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] arr = new int[n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
            max = Math.max(max, arr[i]);
        }
        Arrays.sort(arr);
        max = 2 * lengthExtend(max + 1); // 2^(k) >= 2 * max
        Complex[] freq = new Complex[max];
        for (int i = 0; i < max; i++) {
            freq[i] = new Complex(0, 0);
        }
        for (int i = 0; i < n; i++) {
            freq[arr[i]].re++;
        }
        FFT(freq, 1);
        for (int i = 0; i < max; i++) {
            freq[i].re = freq[i].re * freq[i].re - freq[i].im * freq[i].im;
            freq[i].im = freq[i].re * freq[i].im + freq[i].im * freq[i].re;
        }
        IFFT(freq);
        long[] real = new long[max];
        for (int i = 0; i < max; i++) {
            real[i] = (long) (freq[i].re + 0.5);
            out.print(real[i] + " ");
        }
        out.println();
        for (int i = 0; i < n; i++) {
            real[arr[i] + arr[i]]--;
        }
        for (int i = 0; i < max; i++) {
            real[i] /= 2;
        }
        long[] sum = new long[max];
        sum[0] = real[0];
        for (int i = 1; i < max; i++) {
            sum[i] = sum[i - 1] + real[i];
        }
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += sum[max - 1] - sum[arr[i]];
            result -= (long) (n - 1 - i) * i;
            result -= n - 1;
            result -= (long) (n - 1 - i) * (n - 2 - i) / 2;
        }
        out.println(result);
        out.close();
    }

    static class Complex {
        double re;
        double im;

        Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }
    }

    public static void FFT(Complex[] a, int flag) {
        Complex[] w = new Complex[a.length];
        Complex swap;
        for (int i = 0; i < a.length; i++) {
            w[i] = new Complex(Math.cos(-2 * Math.PI / a.length * i), flag * Math.sin(-2 * Math.PI / a.length * i));
        }
        int k = 0;
        while ((1 << k) < a.length) {
            k++;
        }
        for (int i = 0; i < a.length; i++) {
            int t = i;
            int mod;
            int des = 0;
            for (int j = 0; j < k; j++) {
                mod = t % 2;
                t = t >> 1;
                des = des << 1 + mod;
            }
            if (i < t) {
                swap = a[i];
                a[i] = a[t];
                a[t] = swap;
            }
        }
        for (int l = 2; l <= a.length; l *= 2) {
            int m = l / 2;
            for (int g = 0; g != a.length; g += l) {
                for (int i = 0; i < m; i++) {
                    Complex mul = w[a.length / l * i];
                    Complex t = new Complex(mul.re * a[m + i + g].re - (mul.im * a[m + i + g].im), mul.re * a[m + i + g].im + mul.im * a[m + i + g].re);
                    a[m + i + g].re = a[i + g].re - t.re;
                    a[m + i + g].im = a[i + g].im - t.im;
                    a[i + g].re += t.re;
                    a[i + g].im += t.im;
                }
            }
        }
    }

    public static void IFFT(Complex[] a) {
        FFT(a, -1);
    }

    public static int lengthExtend(int max) {
        int temp = max;
        int add = 2;
        while (temp > 1) {
            temp = temp >> 1;
            add = add << 1;
        }
        return add;
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
