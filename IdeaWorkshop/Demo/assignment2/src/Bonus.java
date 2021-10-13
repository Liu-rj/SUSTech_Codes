import java.util.Scanner;

public class Bonus {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int n = in.nextInt();
        String[] arr = new String[m];
        char[][] chars = new char[m][n];
        for (int i = 0; i < m; i++) {
            arr[i] = in.next();
            chars[i] = arr[i].toCharArray();
        }
        if (n > 1 & m > 1) {
            for (int i = 0; i < n; i++) {
                if (chars[0][i] == '*') {
                    System.out.print('F');
                } else if (i == 0) {
                    int a = 0;
                    for (int j = 0; j < 2; j++) {
                        for (int k = 0; k < 2; k++) {
                            if (chars[j][k] == '*') {
                                a++;
                            }
                        }
                    }
                    if (a == 0) {
                        System.out.print('-');
                    } else {
                        System.out.print(a);
                    }
                } else if (i == n - 1) {
                    int a = 0;
                    for (int j = 0; j <= 1; j++) {
                        for (int k = n - 2; k < n; k++) {
                            if (chars[j][k] == '*') {
                                a++;
                            }
                        }
                    }
                    if (a == 0) {
                        System.out.print('-');
                    } else {
                        System.out.print(a);
                    }
                } else {
                    int a = 0;
                    for (int j = 0; j < 2; j++) {
                        for (int k = i - 1; k <= i + 1; k++) {
                            if (chars[j][k] == '*') {
                                a++;
                            }
                        }
                    }
                    if (a == 0) {
                        System.out.print('-');
                    } else {
                        System.out.print(a);
                    }
                }
            }
            System.out.println();
            for (int i = 1; i < m - 1; i++) {
                for (int j = 0; j < n; j++) {
                    if (chars[i][j] == '*') {
                        System.out.print('F');
                    } else if (j == 0) {
                        int a = 0;
                        for (int k = i - 1; k <= i + 1; k++) {
                            for (int l = 0; l < 2; l++) {
                                if (chars[k][l] == '*') {
                                    a++;
                                }
                            }
                        }
                        if (a == 0) {
                            System.out.print('-');
                        } else {
                            System.out.print(a);
                        }
                    } else if (j == n - 1) {
                        int a = 0;
                        for (int k = i - 1; k <= i + 1; k++) {
                            for (int l = n - 2; l < n; l++) {
                                if (chars[k][l] == '*') {
                                    a++;
                                }
                            }
                        }
                        if (a == 0) {
                            System.out.print('-');
                        } else {
                            System.out.print(a);
                        }
                    } else {
                        int a = 0;
                        for (int k = i - 1; k <= i + 1; k++) {
                            for (int l = j - 1; l <= j + 1; l++) {
                                if (chars[k][l] == '*') {
                                    a++;
                                }
                            }
                        }
                        if (a == 0) {
                            System.out.print('-');
                        } else {
                            System.out.print(a);
                        }
                    }
                }
                System.out.println();
            }
            for (int i = 0; i < n; i++) {
                if (chars[m - 1][i] == '*') {
                    System.out.print('F');
                } else if (i == 0) {
                    int a = 0;
                    for (int j = m - 2; j <= m - 1; j++) {
                        for (int k = 0; k < 2; k++) {
                            if (chars[j][k] == '*') {
                                a++;
                            }
                        }
                    }
                    if (a == 0) {
                        System.out.print('-');
                    } else {
                        System.out.print(a);
                    }
                } else if (i == n - 1) {
                    int a = 0;
                    for (int j = m - 2; j <= m - 1; j++) {
                        for (int k = n - 2; k < n; k++) {
                            if (chars[j][k] == '*') {
                                a++;
                            }
                        }
                    }
                    if (a == 0) {
                        System.out.print('-');
                    } else {
                        System.out.print(a);
                    }
                } else {
                    int a = 0;
                    for (int j = m - 2; j < m; j++) {
                        for (int k = i - 1; k <= i + 1; k++) {
                            if (chars[j][k] == '*') {
                                a++;
                            }
                        }
                    }
                    if (a == 0) {
                        System.out.print('-');
                    } else {
                        System.out.print(a);
                    }
                }
            }
        }else if (n == 1&m>1) {
            for (int i = 0; i < m; i++) {
                if (i==0){
                    if (chars[0][0] == '*'){
                        System.out.println('F');
                    }else if (chars[1][0] == '*'){
                        System.out.println(1);
                    }else if (chars[1][0] != '*'){
                        System.out.println('-');
                    }
                }else if (i == m-1){
                    if (chars[m-1][0] == '*'){
                        System.out.println('F');
                    }else if (chars[m-2][0] == '*'){
                        System.out.println(1);
                    }else if (chars[m-2][0] != '*'){
                        System.out.println('-');
                    }
                }else {
                    if (chars[i][0] == '*'){
                        System.out.println('F');
                    }else {
                        int a = 0;
                        for (int j = i-1; j <= i+1; j++) {
                            if (chars[j][0] == '*'){
                                a++;
                            }
                        }
                        if (a == 0) {
                            System.out.println('-');
                        } else {
                            System.out.println(a);
                        }
                    }
                }
            }
        }else if (n>1&m==1){
            for (int i = 0; i < n; i++) {
                if (i==0){
                    if (chars[0][0] == '*'){
                        System.out.print('F');
                    }else if (chars[0][1] == '*'){
                        System.out.print(1);
                    }else if (chars[0][1] != '*'){
                        System.out.print('-');
                    }
                }else if (i == n-1){
                    if (chars[0][n-1] == '*'){
                        System.out.print('F');
                    }else if (chars[0][n-2] == '*'){
                        System.out.print(1);
                    }else if (chars[0][n-2] != '*'){
                        System.out.print('-');
                    }
                }else {
                    if (chars[0][i] == '*'){
                        System.out.print('F');
                    }else {
                        int a = 0;
                        for (int j = i-1; j <= i+1; j++) {
                            if (chars[0][j] == '*'){
                                a++;
                            }
                        }
                        if (a == 0) {
                            System.out.print('-');
                        } else {
                            System.out.print(a);
                        }
                    }
                }
            }
        }else {
            if (chars[0][0] =='*'){
                System.out.print('F');
            }else {
                System.out.print('-');
            }
        }
    }
}
/*
16 31
*..*.......**....*......**..*..
..*..*.....*.*...............**
*........**.*..*.*.**......*...
.**..*...*.*.....*.*.*.........
............*...*....*......*..
....*.*..*...........*......*.*
..**..........**..*.*..*.*.....
.......*..........*.......*..*.
.**.*..**.....*....*..*.**.....
........*..............*..***..
........*.......*.....*........
..*.......*.......*...*.....*..
..*.....*.*..*.....*...........
..**.............*............*
**..**...........*.*.*..*..*...
......*...*..........**..*.....
*/
/*F22F211---2FF21-1F1----1FF11F32
23F22F1-124F5F21323221-122223FF
F432222-2FF4F22F3F4FF21---1F222
2FF11F1-2F4F32124F4F5F2---1221-
122223212232F1-1F2214F3----2F31
-123F2F11F11122322123F312112F3F
-1FF2222211--1FF12F3F22F2F22232
1344212F31---23312F4222344F11F1
1FF2F12FF2---1F1-12F11F3FF44321
12221114F3---1121111123F33FFF1-
-111---2F311---1F211-2F3112432-
-2F2---224F2111112F212F2---1F1-
-3F41--1F3F21F1-123F1111---1121
24FF32111211111-2F423111111111F
FF33FF21-111----2F3F3F32F22F111
221123F1-1F1----11213FF22F211--*/

