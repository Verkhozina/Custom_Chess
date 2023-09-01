import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class SpecialMoves {
    public static Board makeASpecMove(Pair<Integer> init, Pair<Integer> goal, Board board){
        switch (board.tiles[init.x][init.y].piece.name) {
            case "Pawn" -> {
                return pawn(init, goal, board);
            }
            case "King" -> {
                return king(init, goal, board);
            }

        }
//        String methodName = "method";
//        java.lang.reflect.Method method;
//        try {
//            Class<?> obj = Class.forName(board.tiles[init.x][init.y].piece.name);
//            method = obj.getClass().getMethod(methodName, Pair.class, Pair.class, Board.class);
//            method.invoke(obj, init, goal, board);
//        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        return null;
    }
    private static Board pawn(Pair<Integer> init, Pair<Integer> goal, Board board) {
        if (Objects.equals(init.x, goal.x) && !board.tiles[goal.x][goal.y].occupied) {
            if (board.tiles[init.x][init.y].piece.white) {
                if (init.y + 1 == goal.y) {
                    board.tiles[goal.x][goal.y].occupied = true;
                    board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
                    board.tiles[goal.x][goal.y].piece.moved = true;
                    board.tiles[init.x][init.y].occupied = false;
                    board.tiles[init.x][init.y].piece = null;
                    board.initPrev = init;
                    board.goalPrev = goal;
                    return board;
                } else if (init.y + 2 == goal.y && !board.tiles[init.x][init.y].piece.moved) {
                    board.tiles[goal.x][goal.y].occupied = true;
                    board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
                    board.tiles[goal.x][goal.y].piece.moved = true;
                    board.tiles[init.x][init.y].occupied = false;
                    board.tiles[init.x][init.y].piece = null;
                    board.initPrev = init;
                    board.goalPrev = goal;
                    return board;
                }
            }
            else {
                if (init.y - 1 == goal.y) {
                    board.tiles[goal.x][goal.y].occupied = true;
                    board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
                    board.tiles[goal.x][goal.y].piece.moved = true;
                    board.tiles[init.x][init.y].occupied = false;
                    board.tiles[init.x][init.y].piece = null;
                    board.initPrev = init;
                    board.goalPrev = goal;
                    return board;
                } else if (init.y - 2 == goal.y && !board.tiles[init.x][init.y].piece.moved) {
                    board.tiles[goal.x][goal.y].occupied = true;
                    board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
                    board.tiles[goal.x][goal.y].piece.moved = true;
                    board.tiles[init.x][init.y].occupied = false;
                    board.tiles[init.x][init.y].piece = null;
                    board.initPrev = init;
                    board.goalPrev = goal;
                    return board;
                }
            }
        }
        else if (init.x + 1 == goal.x || init.x - 1 == goal.x) {
            if (board.tiles[goal.x][goal.y].occupied) {
            board.tiles[goal.x][goal.y].occupied = true;
            board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
            board.tiles[goal.x][goal.y].piece.moved = true;
            board.tiles[init.x][init.y].occupied = false;
            board.tiles[init.x][init.y].piece = null;
            board.initPrev = init;
            board.goalPrev = goal;
            return board;
        }
            else if (Objects.equals(board.tiles[board.goalPrev.x][board.goalPrev.y].piece.name, "Pawn") &&
                    (board.initPrev.y - 2 == board.goalPrev.y)) {
                Pair<Integer> capture = new Pair<>(board.goalPrev.x, board.goalPrev.y);
                board.tiles[capture.x][capture.y].occupied = false;
                board.tiles[capture.x][capture.y].piece = null;
                board.tiles[goal.x][goal.y].occupied = true;
                board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
                board.tiles[goal.x][goal.y].piece.moved = true;
                board.tiles[init.x][init.y].occupied = false;
                board.tiles[init.x][init.y].piece = null;
                board.initPrev = init;
                board.goalPrev = goal;
                return board;
            }
        }
        return null;
    }
    private static Board king(Pair<Integer> init, Pair<Integer> goal, Board board){
        if(!board.tiles[init.x][init.y].piece.moved) {
            if (goal.x == init.x + 2 && board.tiles[init.x + 3][init.y].piece != null &&
                    Objects.equals(board.tiles[init.x + 3][init.y].piece.name, "Rook") && !board.tiles[init.x + 3][init.y].piece.moved &&
                    !board.tiles[init.x + 1][init.y].occupied && !board.tiles[init.x + 2][init.y].occupied) {
                Pair<Integer> initRook = new Pair<>(init.x + 3, init.y);
                Pair<Integer> goalRook = new Pair<>(init.x + 1, init.y);
                board.tiles[goal.x][goal.y].occupied = true;
                board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
                board.tiles[goal.x][goal.y].piece.moved = true;
                board.tiles[init.x][init.y].occupied = false;
                board.tiles[init.x][init.y].piece = null;
                board.initPrev = init;
                board.goalPrev = goal;
                board.tiles[goalRook.x][goalRook.y].occupied = true;
                board.tiles[goalRook.x][goalRook.y].piece = board.tiles[init.x][init.y].piece;
                board.tiles[goalRook.x][goalRook.y].piece.moved = true;
                board.tiles[initRook.x][initRook.y].occupied = false;
                board.tiles[initRook.x][initRook.y].piece = null;
                return board;
            } else if (goal.x == init.x - 2 && board.tiles[init.x - 4][init.y].piece != null &&
                    Objects.equals(board.tiles[init.x - 4][init.y].piece.name, "Rook") && !board.tiles[init.x - 4][init.y].piece.moved &&
                    !board.tiles[init.x - 1][init.y].occupied && !board.tiles[init.x - 2][init.y].occupied && !board.tiles[init.x - 3][init.y].occupied) {
                Pair<Integer> initRook = new Pair<>(init.x - 4, init.y);
                Pair<Integer> goalRook = new Pair<>(init.x - 1, init.y);
                board.tiles[goal.x][goal.y].occupied = true;
                board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
                board.tiles[goal.x][goal.y].piece.moved = true;
                board.tiles[init.x][init.y].occupied = false;
                board.tiles[init.x][init.y].piece = null;
                board.initPrev = init;
                board.goalPrev = goal;
                board.tiles[goalRook.x][goalRook.y].occupied = true;
                board.tiles[goalRook.x][goalRook.y].piece = board.tiles[init.x][init.y].piece;
                board.tiles[goalRook.x][goalRook.y].piece.moved = true;
                board.tiles[initRook.x][initRook.y].occupied = false;
                board.tiles[initRook.x][initRook.y].piece = null;
                return board;
            }
        }
        return null;
    }
}
