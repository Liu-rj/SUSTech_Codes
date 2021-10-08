import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Lab8_B {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Task {
        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            long m = in.nextLong();
            String[] strings = new String[n];
            Long[] nums = new Long[n];
            for (int i = 0; i < n; i++) {
                strings[i] = in.next();
                nums[i] = in.nextLong();
            }
            long min = 0;
            long max = (long) Math.pow(2, (int) (Math.log10(m) / Math.log10(2)) + 1) - 1;
            long third = 1;
            for (int i = 0; i < n; i++) {
                if (strings[i].equals("AND")) {
                    min = min & nums[i];
                    max = max & nums[i];
                    third = third & nums[i];
                } else if (strings[i].equals("OR")) {
                    min = min | nums[i];
                    max = max | nums[i];
                    third = third | nums[i];
                } else if (strings[i].equals("XOR")) {
                    min = min ^ nums[i];
                    max = max ^ nums[i];
                    third = third ^ nums[i];
                }
            }
            int coe = 1, tpMin, tpMax;
            long temp = Math.max(min, max);
            long result = 0;
            while (temp != 0) {
                tpMax = (int) (max % 2);
                tpMin = (int) (min % 2);
                result += Math.max(tpMax, tpMin) * coe;
                coe *= 2;
                max /= 2;
                min /= 2;
                temp /= 2;
            }
            out.println(result);
        }
    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }
}
