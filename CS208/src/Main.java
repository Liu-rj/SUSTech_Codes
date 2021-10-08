import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        n = n + 3;
        int[] array1 = new int[n];
        int[] array2 = new int[4*n];
        for (int i = 0; i < n-3 ; i++) {
            array1[i] = in.nextInt();
        }
        int e = 1;
        int f = 0;
        int s = 0;
        for (int j = 0; j < n; j++) {
            if (array1[e-1] <= array1[e]){
                e++;
            }else{
                array2[f] = s;
                array2[f+1] = e-1;
                s = e;
                e = e + 1;
                f= f + 2;
            }
            if (array1[e-1] == 0){
                break;
            }

        }
        int a = 0;
        int b = 0;
        int c = 0;
        for (int x = 0; x < n; x++) {

            if (array2[c+1] - array2[c] > b){
                a = a+2;
                b = array2[a+1] - array2[a];
            }else{
                c = a;
            }

        }
        int num = array2[c+1] - array2[c];
        for (int k = 0; k < n; k++) {
            System.out.printf("%d ",array1[array2[c]]);
            array2[c]++;
            if (k == num)
                break;
        }
        in.close();
    }
}
