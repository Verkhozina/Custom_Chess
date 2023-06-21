import java.util.ArrayList;
import java.util.List;

public class King extends Piece{

    public King(Integer board){
        shortName = "K";
        starts.add(new Pair<>(3, 0));
        Pair<Integer> fMove;
        List<Pair<Integer>> fEmptyReq;
        fMove = new Pair<>(1, 1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(1, -1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(1, 0);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(-1, 1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(-1, -1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(-1, 0);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(0, 1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(0, -1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
    }
    @Override
    public Tile[][] specialMove(Pair<Integer> init, Pair<Integer> goal, Tile[][] board,  Pair<Integer> initPrev, Pair<Integer> goalPrev) {
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
        return null;
    }
}
