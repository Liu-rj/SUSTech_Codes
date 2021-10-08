import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Lab3_B {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        int T = in.nextInt();
        long start = System.currentTimeMillis();
        for (int i = 0; i < T; i++) {
            String s = in.next();
            ArrayList<String> strings = new ArrayList<>();
            HashMap<Character, Integer> map = new HashMap<>();
            int left = 0, right = 0, index = 0;
            for (int j = 0; j < s.length(); j++) {
                if (judge(s.charAt(j))) {
                    right = j;
                    while (j < s.length() && judge(s.charAt(j))) {
                        ++j;
                    }
                    if (right - left > 1) {
                        strings.add(s.substring(left, right));
                    }
                    left = j;
                }
                if (j == s.length() - 1 && left < j) {
                    strings.add(s.substring(left));
                }
            }
            for (String slice : strings) {
                for (int k = 0; k < slice.length(); k++) {
                    char item = slice.charAt(k);
                    if (!map.containsKey(item)) {
                        map.put(item, index++);
                    }
                }
            }
            int[][] link = new int[map.size()][map.size()];
            int[] sum = new int[map.size()];
            int[] upper = new int[map.size()];
            for (String slice : strings) {
                for (int k = 0; k < slice.length(); k++) {
                    int pos = map.get(slice.charAt(k));
                    int neighbor;
                    if (k > 0) {
                        neighbor = map.get(slice.charAt(k - 1));
                        ++link[pos][neighbor];
                        ++sum[pos];
                    }
                    if (k < slice.length() - 1) {
                        neighbor = map.get(slice.charAt(k + 1));
                        ++link[pos][neighbor];
                        ++sum[pos];
                    }
                }
            }
            int max = sum[0] - link[0][0];
            upper[0] = 1;
            max = findMax(link, sum, upper, max, 1);
            out.println(max);
        }
        long end = System.currentTimeMillis(); //获取结束时间
        out.println("程序运行时间： " + (end - start) + "ms");
        out.close();
    }

    public static boolean judge(char item) {
        switch (item) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }

    public static int findMax(int[][] link, int[] sum, int[] upper, int max, int index) {
        if (index < sum.length) {
            int upperMax = max, lowerMax = max;
            upperMax += sum[index];
            upper[index] = 1;
            for (int i = 0; i < sum.length; i++) {
                if (link[index][i] != 0 && upper[i] == 1) {
                    if (i == index) {
                        upperMax -= link[index][i];
                    } else {
                        upperMax -= 2 * link[index][i];
                    }
                }
            }
            max = Math.max(findMax(link, sum, upper, upperMax, index + 1), max);
            upper[index] = 0;
            max = Math.max(findMax(link, sum, upper, lowerMax, index + 1), max);
        }
        return max;
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

//341
//108
//253
//324
//207
//191
//269