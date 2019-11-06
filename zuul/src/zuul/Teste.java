
package zuul;

import java.util.Scanner;

public class Teste {

    public static void main(String[] args) {
        boolean save = true;
        System.out.println("Do you wanna continue your saved game? yes(1) no(0)");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        if(n == 0)
        save = false;
        else
        save = true;
        Game game = new Game(save);
        game.play();
    }
    
}
