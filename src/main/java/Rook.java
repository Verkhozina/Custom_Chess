import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    public Rook(Integer board){
        shortName = "R";
        starts.add(new Pair<>(0, 0));
        starts.add(new Pair<>(7, 0));
        Pair<Integer> fMove;
        List<Pair<Integer>> fEmptyReq;
        for (int i = - board + 1; i < board; i++) {
            if (i == 0) continue;
            fMove = new Pair<>(0, i);
            fEmptyReq = new ArrayList<>();
            for (int j = 1; j < i; j++) {
                fEmptyReq.add(new Pair<>(0, j));
            }
            for (int j = i + 1; j < 0; j++) {
                fEmptyReq.add(new Pair<>(0, j));
            }
            moves.add(new Move(fMove, null, fEmptyReq));
            fMove = new Pair<>(i, 0);
            fEmptyReq = new ArrayList<>();
            for (int j = 1; j < i; j++) {
                fEmptyReq.add(new Pair<>(j, 0));
            }
            for (int j = i + 1; j < 0; j++) {
                fEmptyReq.add(new Pair<>(j, 0));
            }
            moves.add(new Move(fMove, null, fEmptyReq));
        }
    }
}
