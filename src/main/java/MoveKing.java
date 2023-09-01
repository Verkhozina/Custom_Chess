import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoveKing extends Move{
    List<Pair<Integer>> possibleMoves(final Pair<Integer> init, final  Board board) {
        List<Pair<Integer>> moves = new ArrayList<>();

        return moves;
    }
    boolean makeAMove(final Pair<Integer> init,final  Pair<Integer> goal, Board board) {
        if (checkMove(init, goal, board)) {
            board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
            board.tiles[goal.x][goal.y].occupied = true;
            board.tiles[init.x][init.y].occupied = false;
            board.tiles[init.x][init.y].piece = null;
            board.tiles[goal.x][goal.y].piece.moved = true;
            Pair<Integer> initR;
            Pair<Integer> goalR;
            if (goal.x == init.x + 2) {
                initR = new Pair<>(init.x + 3, init.y);
                goalR = new Pair<>(init.x + 1, init.y);
            }
            else {
                initR = new Pair<>(init.x - 4, init.y);
                goalR = new Pair<>(init.x - 1, init.y);
            }
            board.tiles[goalR.x][goalR.y].piece = board.tiles[initR.x][initR.y].piece;
            board.tiles[goalR.x][goalR.y].occupied = true;
            board.tiles[initR.x][initR.y].occupied = false;
            board.tiles[initR.x][initR.y].piece = null;
            board.tiles[goalR.x][goalR.y].piece.moved = true;
            return true;
        }
        return false;
    }
    boolean checkMove(final Pair<Integer> init ,final  Pair<Integer> goal, final Board board) {
        if (board.tiles[goal.x][goal.y].occupied &&
                board.tiles[goal.x][goal.y].piece.white == board.tiles[init.x][init.y].piece.white)
            return false;
        if (Objects.equals(init.y, goal.y))
            if      ((init.x - 2 == goal.x) && //CHECK COORDS
                    board.tiles[init.x - 4][init.y].occupied && board.tiles[init.x - 4][init.y].piece.name == "Rook" && //CHECK ROOK
                    !board.tiles[init.x - 4][init.y].piece.moved && !board.tiles[init.x][init.y].piece.moved && //CHECK NOT MOVED
                    !board.tiles[init.x - 3][init.y].occupied && !board.tiles[init.x - 2][init.y].occupied &&
                    !board.tiles[init.x - 1][init.y].occupied)
            return true;
            else return (init.x + 2 == goal.x) &&
                    board.tiles[init.x + 3][init.y].occupied && board.tiles[init.x + 3][init.y].piece.name == "Rook" && //CHECK ROOK
                    !board.tiles[init.x + 3][init.y].piece.moved && !board.tiles[init.x][init.y].piece.moved && //CHECK NOT MOVED
                    !board.tiles[init.x + 1][init.y].occupied && !board.tiles[init.x + 2][init.y].occupied;
        return false;
    }
    public MoveKing() {
    }
}
