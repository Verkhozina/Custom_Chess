import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovePawn extends Move{
    List<Pair<Integer>> possibleMoves(Pair<Integer> init, Board board) {
        List<Pair<Integer>> moves = new ArrayList<>();
        if (board.tiles[init.x][init.y].piece.white) {
            moves.add(new Pair<>(init.x, init.y + 1));
            moves.add(new Pair<>(init.x, init.y + 2));
            moves.add(new Pair<>(init.x + 1, init.y + 1));
            moves.add(new Pair<>(init.x - 1, init.y + 1));
        }
        else {
            moves.add(new Pair<>(init.x, init.y - 1));
            moves.add(new Pair<>(init.x, init.y - 2));
            moves.add(new Pair<>(init.x + 1, init.y - 1));
            moves.add(new Pair<>(init.x - 1, init.y - 1));
        }
        moves = moves.stream().filter(o -> (o.x < board.width && o.y < board.length && o.x > -1 && o.y > -1)).collect(Collectors.toList());
        moves = moves.stream().filter(o -> checkMove(init, o, board)).collect(Collectors.toList());
        return moves;
    }
    boolean makeAMove(Pair<Integer> init, Pair<Integer> goal, Board board) {
        if (checkMove(init, goal, board)) {
            if ((goal.x == init.x + 1 || goal.x == init.x - 1) &&
                    !board.tiles[goal.x][goal.y].occupied) {
                board.tiles[board.goalPrev.x][board.goalPrev.y].occupied = false;
                board.tiles[board.goalPrev.x][board.goalPrev.y].piece = null;
            }
            board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
            board.tiles[goal.x][goal.y].occupied = true;
            board.tiles[init.x][init.y].occupied = false;
            board.tiles[init.x][init.y].piece = null;
            board.tiles[goal.x][goal.y].piece.moved = true;
            return true;
        }
        return false;
    }
    boolean checkMove(Pair<Integer> init, Pair<Integer> goal, Board board) {
        if (board.tiles[goal.x][goal.y].occupied &&
                board.tiles[goal.x][goal.y].piece.white == board.tiles[init.x][init.y].piece.white)
            return false;
        if (Objects.equals(init.x, goal.x)) {
            if (!board.tiles[goal.x][goal.y].occupied) {
                if ((board.tiles[init.x][init.y].piece.white && goal.y == init.y + 1) ||
                        (!board.tiles[init.x][init.y].piece.white && goal.y == init.y - 1))
                    return true;
                else return (board.tiles[init.x][init.y].piece.white && goal.y == init.y + 2 &&
                        !board.tiles[init.x][init.y].piece.moved) || (!board.tiles[init.x][init.y].piece.white && goal.y == init.y - 2 &&
                        !board.tiles[init.x][init.y].piece.moved);
            }
        }
        else if (board.tiles[init.x][init.y].piece.white && goal.y == init.y + 1) {
            if (goal.x == init.x + 1) {
                if (board.tiles[goal.x][goal.y].occupied) return true;
                else {
                    if (board.goalPrev != null && Objects.equals(board.tiles[board.goalPrev.x][board.goalPrev.y].piece.name, "Pawn") &&
                            board.goalPrev.y.equals(init.y) && board.goalPrev.x == init.x + 1 &&
                            board.initPrev.y == init.y + 2 && board.initPrev.x == init.x + 1)
                        return true;
                } //Взятие на проходе
            }
            if (goal.x == init.x - 1) {
                if (board.tiles[goal.x][goal.y].occupied) return true;
                else {
                    return board.goalPrev != null && Objects.equals(board.tiles[board.goalPrev.x][board.goalPrev.y].piece.name, "Pawn") &&
                            board.goalPrev.y.equals(init.y) && board.goalPrev.x == init.x - 1 &&
                            board.initPrev.y == init.y + 2 && board.initPrev.x == init.x - 1;
                } //Взятие на проходе
            }
        }
        else if (!board.tiles[init.x][init.y].piece.white && goal.y == init.y - 1) {
            if (goal.x == init.x + 1) {
                if (board.tiles[goal.x][goal.y].occupied) return true;
                else {
                    if (board.goalPrev != null && Objects.equals(board.tiles[board.goalPrev.x][board.goalPrev.y].piece.name, "Pawn") &&
                            board.goalPrev.y.equals(init.y) && board.goalPrev.x == init.x + 1 &&
                            board.initPrev.y == init.y - 2 && board.initPrev.x == init.x + 1)
                        return true;
                } //Взятие на проходе
            }
            if (goal.x == init.x - 1) {
                if (board.tiles[goal.x][goal.y].occupied) return true;
                else {
                    return board.goalPrev != null && Objects.equals(board.tiles[board.goalPrev.x][board.goalPrev.y].piece.name, "Pawn") &&
                            board.goalPrev.y.equals(init.y) && board.goalPrev.x == init.x - 1 &&
                            board.initPrev.y == init.y - 2 && board.initPrev.x == init.x - 1;
                } //Взятие на проходе
            }
        }
        return false;
    }
    public MovePawn() {
    }
}
