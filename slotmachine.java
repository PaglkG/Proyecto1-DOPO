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
    public static void main(String[] args){
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        sc = new Scanner(System.in);
        String input = sc.nextLine();
        n = Integer.parseInt(input);
        input = sc.nextLine();
        k = Integer.parseInt(input);
        if (k == 1) {
        System.exit(0);
        }
        diferencia(k,n,sc);
        int[] movimientos = iguales(k,n,sc);
        for (int u = 2; u <= n; u++) {
            int movimiento = movimientos[u]; 
    
            String print = u + " " + -movimiento;
            out.write(print + "\n");
            out.flush();
            input = sc.nextLine();
            k = Integer.parseInt(input);
    
        }
        if (k == 1) {
        System.exit(0);
        }
        sc.close();
    }

    private static void diferencia(int k, int n,Scanner sc) {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        int kOld = k;
        for (int i = 2; i <= n; i++) {
            int posicionKmaximo = 0;
            for (int u = 1; u <= n; u++) {
                String print = i + " " + 1;
                out.write(print + "\n");
                out.flush();
                String input = sc.nextLine();
                k = Integer.parseInt(input);
                if (k == 1) {
                    System.exit(0);
                }
                if (kOld < k) {
                    posicionKmaximo = u;
                    kOld = k;
                }
            }
            if (posicionKmaximo != 0) {
                String print = i + " " + posicionKmaximo;
                out.write(print + "\n");
                out.flush();
                String input = sc.nextLine();
                k = Integer.parseInt(input);
                if (k == 1) {
                    System.exit(0);
                }
            }
        }
       
    }

    private static int[] iguales(int k, int n,Scanner sc) {
        int[] movimientos = new int[n + 1];
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        for (int i = 1; i < n; i++) {
            String print = 1 + " " + 1;
            out.write(print + "\n");
            out.flush();
            String input = sc.nextLine();
            k = Integer.parseInt(input);
            if (k == 1) {
                System.exit(0);
            }
            int kOld = k;
            for (int u = 2; u <= n; u++) {
                print = u + " " + -i;
                out.write(print + "\n");
                out.flush();
                input = sc.nextLine();
                k = Integer.parseInt(input);
                if (k == 1) {
                    System.exit(0);
                }
                if(kOld < k) {
                    movimientos[u] = i;
                    print = u + " " + i;
                    out.write(print + "\n");
                    out.flush();
                    input = sc.nextLine();
                    k = Integer.parseInt(input);
                    if (k == 1) {
                        System.exit(0);
                    }
                    break;
                }
                else {
                    print = u + " " + i;
                    out.write(print + "\n");
                    out.flush();
                    input = sc.nextLine();
                    k = Integer.parseInt(input);
                    if (k == 1) {
                        System.exit(0);
                    }
                }
            }
        }
        String print = 1 + " " + 1;
        out.write(print + "\n");
        out.flush();
        String input = sc.nextLine();
    

        return movimientos;
    }
}