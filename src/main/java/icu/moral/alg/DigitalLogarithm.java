package icu.moral.alg;

import java.util.PriorityQueue;
import java.util.Scanner;

public class DigitalLogarithm {

    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);

        int cnt = Integer.parseInt(scanner.nextLine());

        int x;
        PriorityQueue<Integer> a1;
        PriorityQueue<Integer> b1;
        Integer a,b;
        int rst;
        while (cnt -- > 0) {
            rst = 0;
            x = scanner.nextInt();
            a1 = new PriorityQueue<>((o1, o2) -> -o1.compareTo(o2));
            b1 = new PriorityQueue<>((o1, o2) -> -o1.compareTo(o2));

            for(int i=0 ; i<x ; i++){
                a1.add(scanner.nextInt());
            }

            for(int i=0 ; i<x ; i++){
                b1.add(scanner.nextInt());
            }
            while (a1.size() != 0 && b1.size() != 0){
                a = a1.peek();
                b = b1.peek();

                if(a.equals(b)){
                    a1.poll();
                    b1.poll();
                    continue;
                }

                rst++;
                if(a.compareTo(b) > 0){
                    a1.poll();
                    a1.add(a.toString().length());
                } else {
                    b1.poll();
                    b1.add(b.toString().length());
                }
            }

            System.out.println(rst);
        }
    }
}
