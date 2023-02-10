package icu.moral.alg;

import java.util.Scanner;

public class MinorReduce {

    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);

        int cnt = Integer.parseInt(scanner.nextLine());
        int a,b;
        boolean flag;
        while (cnt -- > 0) {
            String cur = scanner.nextLine();
            flag = false;

            char[] x = cur.toCharArray();

            if(cur.length() == 1){
                System.out.println(cur);
                continue;
            }

            for (int i = x.length - 1 ; i > 0; i--) {
                a = x[i] - 48;
                b = x[i-1] - 48;

                if(a + b >= 10){
                    x[i-1] = (char)((a + b) / 10 + 48);
                    x[i] = (char)((a + b) % 10 + 48);

                    System.out.println(String.valueOf(x));
                    flag = true;
                    break;
                }
            }


            if(!flag){
                a = x[0] - 48;
                b = x[1] - 48;
                x[1] = (char)((a + b) + 48);

                char[] y = new char[x.length - 1];
                System.arraycopy(x, 1, y, 0, x.length - 1);
                System.out.println(String.valueOf(y));
            }
        }
    }
}
