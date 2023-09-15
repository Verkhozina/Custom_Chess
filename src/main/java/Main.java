import java.io.File;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static GameEngine game;
    static String inputStr;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Main menu. Inter command.");
            inputStr = scanner.nextLine();
            switch (inputStr) {
                case "start" -> {
                    if (initialise())
                        play();
                }
                case "help" -> System.out.println("Imagine commands here.");
                case "quit" -> {
                    return;
                }
                default -> System.out.println("Unknown command. Type \"help\" to see possible commands.");
            }
        }
    }
    static boolean initialise(){
        File config = new File("./pieces.xml");
        try {
            game = new GameEngine(config);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    static void play(){
        boolean whiteTurn = false;
        while (true) {
            whiteTurn = !whiteTurn;
            boolean moveCompleted = false;
            while (!moveCompleted) {
                if (whiteTurn) System.out.print("White move: ");
                else System.out.print("Black move: ");
                inputStr = scanner.nextLine();
                switch (inputStr) {
                    case "board" -> {
                        String [][] boardStr = game.printTheBoard();
                        for (int j = boardStr[0].length - 1; j >= 0; j--) {
                            for (String[] strings : boardStr) System.out.print(strings[j] + " ");
                            System.out.println();
                        }
                    }
                    case "moves" -> {
                        System.out.print("Enter a tile to see possible moves: ");
                        inputStr = scanner.nextLine();
                        String [][] posMovesStr = game.printPossibleMoves(inputStr);
                        for (int j = posMovesStr[0].length - 1; j >= 0; j--) {
                            for (String[] strings : posMovesStr) System.out.print(strings[j] + " ");
                            System.out.println();
                        }
                    }
                    case "draw" -> {
                        System.out.println("Draw submitted.");
                        return;
                    }
                    case "help" -> System.out.println("Imagine commands here.");
                    case "quit" -> {
                        return;
                    }
                    default -> {
                        moveCompleted = game.makeAMove(inputStr, whiteTurn);
                        if (!moveCompleted)
                            System.out.println("Invalid command");
                    }
                }
            }
            System.out.println("Move completed");
        }
    }
}
