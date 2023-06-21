import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    public Pawn(Integer board){
        shortName = "P";
        for (int i = 0; i < board; i++) {
            starts.add(new Pair<>(i, 1));
        }
        Pair<Integer> fMove;
        List<Pair<Integer>> fEmptyReq;
        fMove = new Pair<>(1, 1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, true, fEmptyReq));
        fMove = new Pair<>(-1, 1);
        moves.add(new Move(fMove, true, fEmptyReq));
        fMove = new Pair<>(0, 1);
        moves.add(new Move(fMove, false, fEmptyReq));
    }
    @Override
    public Tile[][] specialMove(Pair<Integer> init, Pair<Integer> goal, Tile[][] board, Pair<Integer> initPrev, Pair<Integer> goalPrev) {
        if (board[init.x][init.y].notMoved && goal.y == init.y + 2 && goal.x == init.x &&
                !board[goal.x][goal.y].occupied) {
            board[goal.x][goal.y].occupied = true;
            board[goal.x][goal.y].piece = board[init.x][init.y].piece;
            board[goal.x][goal.y].pIsWhite = board[init.x][init.y].pIsWhite;
            board[goal.x][goal.y].notMoved = false;
            board[init.x][init.y].occupied = false;
            board[init.x][init.y].piece = null;
            board[init.x][init.y].pIsWhite = null;
            board[init.x][init.y].notMoved = null;
            return board;
        }
        else if ((goal.x == init.x + 1 || goal.x == init.x - 1) && (goal.y == init.y + 1) && //check move itself
                board[goal.x][goal.y - 1].piece.shortName.equals("P") && //check another pawn
                (initPrev.x == goal.x && initPrev.y == goal.y + 1) && (goalPrev.x == goal.x && goalPrev.y == goal.y - 1) &&
                ((board[goal.x][goal.y - 1].pIsWhite && !board[init.x][init.y].pIsWhite)||(!board[goal.x][goal.y - 1].pIsWhite && board[init.x][init.y].pIsWhite))) {
            board[goal.x][goal.y].occupied = true;
            board[goal.x][goal.y].piece = board[init.x][init.y].piece;
            board[goal.x][goal.y].pIsWhite = board[init.x][init.y].pIsWhite;
            board[goal.x][goal.y].notMoved = false;
            board[goal.x][goal.y - 1].occupied = false;
            board[goal.x][goal.y - 1].piece = null;
            board[goal.x][goal.y - 1].pIsWhite = null;
            board[goal.x][goal.y - 1].notMoved = null;
            return board;
        }
        return null;
    }
}
