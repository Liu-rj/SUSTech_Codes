import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int row = in.nextInt();
        int line = in.nextInt();
        char[][] c = new char[row+2][line+2];
        for (int i = 0; i < row; i++) {
            String s = in.next();
            char[] a = s.toCharArray();
            for (int b = 0; b <line; b++) {
                a[b] = c[i+1][b+1];
            }
        }
        int count = 0;
        for (int i = 1; i <row+1; i++) {
            for (int r = 1; r <line+1 ; r++) {
                if (c[i][r] == '*'){
                    c[i][r] = 'F';
                }else{
                    for (int j = 0; j <3 ; j++) {
                        for (int k = 0; k <3 ; k++) {
                            if (c[i-1+j][r-1+k] == '*'){
                                count++;
                            }
                        }
                    }
                    if (count > 0) {
                        c[i][r] = Character.forDigit(count,1);
                    }else if (count == 0){
                        c[i][r] = '-';
                    }
                }

            }
        }
        for (int i = 1; i <row+1 ; i++) {
            for (int r = 1; r <line+1 ; r++) {
                System.out.print(c[i][r]);
            }
            System.out.print("\n");
        }
    }
}
/*
2
3 2
1 2
2 1
0 -1
2 3
1 0 1
2 1 -2
 */