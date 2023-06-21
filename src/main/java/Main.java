import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    static Integer boardWidth = 8;
    static Tile[][] board = new Tile[8][8];
    static Pair<Integer> initPrev = new Pair<>(0, 0);
    static Pair<Integer> goalPrev = new Pair<>(0, 0);

    public static void main(String[] args) {
       boolean end = false;
       int winner = 2;
        List<Piece> figureSet = new ArrayList<>();
        figureSet.add(new Pawn(8));
        figureSet.add(new Rook(8));
        figureSet.add(new Knight(8));
        figureSet.add(new Bishop(8));
        figureSet.add(new Queen(8));
        figureSet.add(new King(8));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                board[i][j] = new Tile(new Pair<>(i, j), false, null, null, null);
        for (int i = 0; i < figureSet.size(); i++) {
            for (int j = 0; j < figureSet.get(i).starts.size(); j++) {
                int x = figureSet.get(i).starts.get(j).x;
                int y = figureSet.get(i).starts.get(j).y;
                board[x][y].occupied = true;
                board[x][y].piece = figureSet.get(i);
                board[x][y].pIsWhite = true;
                board[x][y].notMoved = true;
                board[x][inv(y)].occupied = true;
                board[x][inv(y)].piece = figureSet.get(i);
                board[x][inv(y)].pIsWhite = false;
                board[x][inv(y)].notMoved = true;
            }
        }
        printTheBoard(seeTheBoard());
        Scanner in = new Scanner(System.in);
        String str;
        boolean moveCompleted;
        while (!end) {
            moveCompleted = false;
            if (winner == 2) winner = 1;
            else if (winner == 1) winner = 2;
            while (!moveCompleted) {
                switch (winner) {
                    case 1 -> System.out.print("White move: ");
                    case 2 -> System.out.print("Black move: ");
                }
                str = in.nextLine();
                switch (str) {
                    case "board" -> {
                        printTheBoard(seeTheBoard());
                    }
                    case "moves" -> {
                        System.out.print("Enter a tile to see possible moves: ");
                        str = in.nextLine();
                        String[][] result = seeTheBoard();
                        List<Pair<Integer>> moves = null;
                        try {
                            moves = possibleMoves(parseTile(str));
                        }
                        catch (NullPointerException e) {
                        }
                        if (moves == null || moves.size() == 0) {
                            System.out.println("No possible moves.");
                            break;
                        }
                        for (Pair<Integer> integerPair : moves) {
                            if (integerPair.x > -1 && integerPair.x < boardWidth &&
                                    integerPair.y > -1 && integerPair.y < boardWidth)
                                result[integerPair.x][integerPair.y] = "X";
                        }
                        printTheBoard(result);
                    }
                    case "special" -> {
                        System.out.print("Enter a move: ");
                        str = in.nextLine();
                        String[] move = str.split(" ");
                        if (move.length != 2) {
                            System.out.println("Unknown command");
                        }
                        Pair<Integer> init = parseTile(move[0]);
                        Pair<Integer> goal = parseTile(move[1]);
                        switch (winner) {
                            case 1 -> {
                                if (board[init.x][init.y].pIsWhite == null || !board[init.x][init.y].pIsWhite) {
                                    System.out.println("Nothing to move");
                                }
                                else {
                                    Tile[][] newBoard = board[init.x][init.y].piece.specialMove(init, goal, board, initPrev, goalPrev);
                                    moveCompleted = !(newBoard == null);
                                    if (!moveCompleted) System.out.println("Invalid move");
                                    else {
                                        board = newBoard;
                                        end = checkWin(goal);
                                    }
                                }
                            }
                            case 2 -> {
                                if (board[init.x][init.y].pIsWhite == null || board[init.x][init.y].pIsWhite) {
                                    System.out.println("Nothing to move");
                                }
                                else {
                                    Tile[][] newBoard = board[init.x][init.y].piece.specialMove(init, goal, board, initPrev, goalPrev);
                                    moveCompleted = !(newBoard == null);
                                    if (!moveCompleted) System.out.println("Invalid move");
                                    else {
                                        board = newBoard;
                                        end = checkWin(goal);
                                    }
                                }
                            }
                        }

                    }
                    case "draw" -> {
                        moveCompleted = true;
                        end = true;
                        winner = 0;
                    }
                    default -> {
                        String[] move = str.split(" ");
                        if (move.length != 2) {
                            System.out.println("Unknown command");
                        }
                        Pair<Integer> init = parseTile(move[0]);
                        Pair<Integer> goal = parseTile(move[1]);
                        switch (winner) {
                            case 1 -> {
                                if (board[init.x][init.y].pIsWhite == null || !board[init.x][init.y].pIsWhite) {
                                    System.out.println("Nothing to move");
                                }
                                else {
                                    moveCompleted = makeAMove(init, goal);
                                    if (!moveCompleted) System.out.println("Invalid move");
                                    else end = checkWin(goal);
                                }
                            }
                            case 2 -> {
                                if (board[init.x][init.y].pIsWhite == null || board[init.x][init.y].pIsWhite) {
                                    System.out.println("Nothing to move");
                                }
                                else {
                                    moveCompleted = makeAMove(init, goal);
                                    if (!moveCompleted) System.out.println("Invalid move");
                                    else end = checkWin(goal);
                                }
                            }
                        }
                    }
                }
            }

            if (end) break;
            System.out.println("Move completed");
        }
        in.close();
        switch (winner) {
            case 0 -> System.out.println("DRAW");
            case 1 -> System.out.println("WHITE WINS");
            case 2 -> System.out.println("BLACK WINS");
        }
    }
    static List<Pair<Integer>> possibleMoves(Pair<Integer> init) throws NullPointerException{
        List<Pair<Integer>> list = new ArrayList<>();
        if (board[init.x][init.y].pIsWhite)
            loop:
                    for (Move m : board[init.x][init.y].piece.moves) {
                        for (Pair<Integer> req : m.fEmptyReq) {
                            if ((!(init.x + req.x < 0 || init.x + req.x >= boardWidth ||
                                    init.y + req.y < 0 || init.y + req.y >= boardWidth) &&
                                    board[init.x + req.x][init.y + req.y].occupied))
                                continue loop;
                        }
                        if (init.x + m.fMove.x < 0 || init.x + m.fMove.x >= boardWidth ||
                                init.y + m.fMove.y < 0 || init.y + m.fMove.y >= boardWidth)
                            continue;
                        if (board[init.x + m.fMove.x][init.y + m.fMove.y].occupied) {
                            if (board[init.x + m.fMove.x][init.y + m.fMove.y].pIsWhite) {
                            }
                            else {
                                if (m.fCapture == null || m.fCapture == true)
                                    list.add(new Pair<>(init.x + m.fMove.x, init.y + m.fMove.y));
                            }
                        }
                        else {
                            if (m.fCapture == null || m.fCapture == false)
                                list.add(new Pair<>(init.x + m.fMove.x, init.y + m.fMove.y));
                        }
                    }
        else
            loop:
                    for (Move m : board[init.x][init.y].piece.moves) {
                        for (Pair<Integer> req : m.fEmptyReq) {
                            if ((!(init.x + req.x < 0 || init.x + req.x >= boardWidth ||
                                    init.y - req.y < 0 || init.y - req.y >= boardWidth) &&
                                    board[init.x + req.x][init.y - req.y].occupied))
                                continue loop;
                        }
                        if (init.x + m.fMove.x < 0 || init.x + m.fMove.x >= boardWidth ||
                                init.y - m.fMove.y < 0 || init.y - m.fMove.y >= boardWidth)
                            continue;
                        if (board[init.x + m.fMove.x][init.y - m.fMove.y].occupied) {
                            if (!board[init.x + m.fMove.x][init.y - m.fMove.y].pIsWhite) {
                            }
                            else {
                                if (m.fCapture == null || m.fCapture == true)
                                    list.add(new Pair<>(init.x + m.fMove.x, init.y - m.fMove.y));
                            }
                        }
                        else {
                            if (m.fCapture == null || m.fCapture == false)
                                list.add(new Pair<>(init.x + m.fMove.x, init.y - m.fMove.y));
                        }
                    }
        return list;
    }
    static void printPossibleMoves(Pair<Integer> init)  throws NullPointerException{
        System.out.println("-----------");
        List<Pair<Integer>> list = possibleMoves(init);
        for (int j = 0; j < boardWidth; j++) {
            for (int i = 0; i < boardWidth; i++)
                if (list.contains(new Pair<>(i, j)))
                    System.out.print("X" + " ");
                else System.out.print("O" + " ");
            System.out.println();
        }
        System.out.println("-----------");
    }
    static boolean makeAMove(Pair<Integer> init, Pair<Integer> goal) throws NullPointerException{
        List<Pair<Integer>> moves;
        try {
            moves = possibleMoves(init);
        }
        catch (NullPointerException e) {
            return false;
        }
        if (moves.contains(goal)) {
            board[goal.x][goal.y].occupied = true;
            board[goal.x][goal.y].piece = board[init.x][init.y].piece;
            board[goal.x][goal.y].pIsWhite = board[init.x][init.y].pIsWhite;
            board[goal.x][goal.y].notMoved = false;
            board[init.x][init.y].occupied = false;
            board[init.x][init.y].piece = null;
            board[init.x][init.y].pIsWhite = null;
            board[init.x][init.y].notMoved = null;
            initPrev = init;
            goalPrev = goal;
            return true;
        }
        return false;
    }
    static String[][] seeTheBoard() {
        String[][] result = new String[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                result[i][j] = board[i][j].toString();
            }
        }
        return result;
    }
    static void printTheBoard(String[][] boardStr){
        System.out.println("-----------");
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++)
                System.out.print(boardStr[i][j] + " ");
            System.out.println();
        }
        System.out.println("-----------");
    }
    static Pair<Integer> parseTile(String tile) {
        if (tile == null) return null;
        char ch = tile.charAt(0);
        int x = ch - 'a';
        int y = Character.getNumericValue(tile.charAt(1) - 1);
        if (x >= boardWidth || y >= boardWidth) {
            System.out.println("Invalid tile");
            return null;
        }
        return new Pair<>(x, y);
    }
    static int inv(int y) {
        return boardWidth - y - 1;
    }
    static boolean checkWin(Pair<Integer> goal) {
        List<Pair<Integer>> moves;
        try {
            moves = possibleMoves(goal);
        }
        catch (NullPointerException e) {
            return false;
        }
        for (Pair<Integer> m : moves) {
            if (board[m.x][m.y].occupied && board[m.x][m.y].piece.shortName.equals("K") &&
                        ((board[m.x][m.y].pIsWhite && !board[goal.x][goal.y].pIsWhite)||
                                (!board[m.x][m.y].pIsWhite && board[goal.x][goal.y].pIsWhite))) {
                return true;
            }
        }
        return false;
    }
}