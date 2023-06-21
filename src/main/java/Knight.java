import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Knight extends Piece{

    public Knight(Integer board){
        shortName = "N";
        starts.add(new Pair<>(1, 0));
        starts.add(new Pair<>(6, 0));
        Pair<Integer> fMove;
        List<Pair<Integer>> fEmptyReq;
        fMove = new Pair<>(1, 2);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(2, 1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(-1, -2);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(-2, -1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(-1, 2);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(-2, 1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(1, -2);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
        fMove = new Pair<>(2, -1);
        fEmptyReq = new ArrayList<>();
        moves.add(new Move(fMove, null, fEmptyReq));
    }
}
