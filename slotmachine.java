import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**1
 * main
 */
public class slotmachine {
    private static int n;
    private static int k;
    private static Scanner sc;
    private static String input;
    private static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));;

    public static void main(String[] args){
        sc = new Scanner(System.in);
        input = sc.nextLine();
        n = Integer.parseInt(input);
        input = sc.nextLine();
        k = Integer.parseInt(input);
        if (k == 1) {
        System.exit(0);
        }
        diferencia();
        int[] movimientos = iguales();
        for (int u = 2; u <= n; u++) {
            int movimiento = movimientos[u]; 
            printOI(u,-movimiento);
    
        }
        if (k == 1) {
        System.exit(0);
        }
        sc.close();
    }

    private static void diferencia() {
        int kOld = k;
        for (int i = 2; i <= n; i++) {
            int posicionKmaximo = 0;
            for (int u = 1; u <= n; u++) {
                printOI(i,1);
                if (k == 1) {
                    System.exit(0);
                }
                if (kOld < k) {
                    posicionKmaximo = u;
                    kOld = k;
                }
            }
            if (posicionKmaximo != 0) {
                printOI(i,posicionKmaximo);
                if (k == 1) {
                    System.exit(0);
                }
            }
        }
       
    }

    private static int[] iguales() {
        int[] movimientos = new int[n + 1];
        for (int i = 1; i < n; i++) {
            printOI(1,1);
            if (k == 1) {
                System.exit(0);
            }
            int kOld = k;
            for (int u = 2; u <= n; u++) {
                printOI(u,-i);
                if (k == 1) {
                    System.exit(0);
                }
                if(kOld < k) {
                    movimientos[u] = i;
                    printOI(u,i);
                    if (k == 1) {
                        System.exit(0);
                    }
                    break;
                }
                else {
                    printOI(u,i);
                    if (k == 1) {
                        System.exit(0);
                    }
                }
            }
        }
        printOI(1,1);
    

        return movimientos;
    }

    private static void printOI(int m, int u) {
        String print = m + " " + u;
        out.write(print + "\n");
        out.flush();
        String input = sc.nextLine();
        k = Integer.parseInt(input);
    }
}