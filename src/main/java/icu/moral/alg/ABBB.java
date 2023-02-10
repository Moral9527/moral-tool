package icu.moral.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ABBB {

    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);

        int cnt = Integer.parseInt(scanner.nextLine());
        List<Character> res;
        while (cnt -- > 0) {
            res = new ArrayList<>();
            String cur = scanner.nextLine();
            for(int i = 0 ;i < cur.length(); i++){
                if(cur.charAt(i) == 'B' && res.size() > 0){
                    res.remove(res.size() - 1);
                } else {
                    res.add(cur.charAt(i));
                }
            }

            System.out.println(res.size());
        }
    }
}
