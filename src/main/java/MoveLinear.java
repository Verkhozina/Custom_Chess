import java.util.ArrayList;
import java.util.List;

public class MoveLinear extends Move {
    final Boolean jump;
    final boolean allColors;
    final Integer limit;
    List<Pair<Integer>> possibleMoves(Pair<Integer> init, Board board) {
        List<Pair<Integer>> moves = new ArrayList<>();
        for (int j = 1; init.x + j < board.width && j < limit; j++) {
            Pair<Integer> move = new Pair<>(init.x + j, init.y);
            if (checkMove(init, move, board))
                moves.add(move);
        }
        for (int j = -1; init.x + j > -1 && j < limit; j--) {
            Pair<Integer> move = new Pair<>(init.x + j, init.y);
            if (checkMove(init, move, board))
                moves.add(move);
        }
        for (int j = 1; init.y + j < board.length && j < limit; j++) {
            Pair<Integer> move = new Pair<>(init.x, init.y + j);
            if (checkMove(init, move, board))
                moves.add(move);
        }
        for (int j = -1; init.y + j > -1 && j < limit; j--) {
            Pair<Integer> move = new Pair<>(init.x, init.y + j);
            if (checkMove(init, move, board))
                moves.add(move);
        }
        return moves;
    }

    boolean makeAMove(Pair<Integer> init, Pair<Integer> goal, Board board) {
        if (checkMove(init, goal, board)) {
            board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
            board.tiles[goal.x][goal.y].occupied = true;
            board.tiles[init.x][init.y].occupied = false;
            board.tiles[init.x][init.y].piece = null;
            board.tiles[goal.x][goal.y].piece.moved = true;
            return true;
        }
        return false;
    }
    boolean checkMove(Pair<Integer> init, Pair<Integer> goal, Board board){
        if (board.tiles[goal.x][goal.y].occupied &&
                board.tiles[goal.x][goal.y].piece.white == board.tiles[init.x][init.y].piece.white)
            return false;
        if (init.x == goal.x) {
            if (!allColors && (goal.y % 2 != init.y % 2)) return false;
            if (!jump) {
                for (int i = 1; init.y + i < goal.y; i++) {
                    if (board.tiles[init.x][init.y + i].occupied)
                        return false;
                }
                for (int i = -1; init.y + i > goal.y; i--) {
                    if (board.tiles[init.x][init.y + i].occupied)
                        return false;
                }
            }
            return true;
        }
        if (init.y == goal.y) {
            if (!allColors && (goal.x % 2 != init.x % 2)) return false;
            if (!jump) {
                for (int i = 1; init.x + i < goal.x; i++) {
                    if (board.tiles[init.x + i][init.y].occupied)
                        return false;
                }
                for (int i = -1; init.x + i > goal.x; i--) {
                    if (board.tiles[init.x + i][init.y].occupied)
                        return false;
                }
            }
            return true;
        }
        return false;
    }
    public MoveLinear(Boolean jump, Boolean allColors, Integer limit) {
        this.jump = jump;
        this.allColors = true;
        this.limit = limit;
    }
}
