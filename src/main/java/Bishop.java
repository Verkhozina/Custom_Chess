import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(Integer board){
        shortName = "B";
        starts.add(new Pair<>(2, 0));
        starts.add(new Pair<>(5, 0));
        Pair<Integer> fMove;
        List<Pair<Integer>> fEmptyReq;
        for (int i = 1; i < board; i++) {
            fMove = new Pair<>(i, i);
            fEmptyReq = new ArrayList<>();
            for (int j = 1; j < i; j++) {
                fEmptyReq.add(new Pair<>(j, j));
            }
            moves.add(new Move(fMove, null, fEmptyReq));
            fMove = new Pair<>(i, -i);
            fEmptyReq = new ArrayList<>();
            for (int j = 1; j < i; j++) {
                fEmptyReq.add(new Pair<>(j, -j));
            }
            moves.add(new Move(fMove, null, fEmptyReq));
            fMove = new Pair<>(-i, -i);
            fEmptyReq = new ArrayList<>();
            for (int j = 1; j < i; j++) {
                fEmptyReq.add(new Pair<>(-j, -j));
            }
            moves.add(new Move(fMove, null, fEmptyReq));
            fMove = new Pair<>(-i, i);
            fEmptyReq = new ArrayList<>();
            for (int j = 1; j < i; j++) {
                fEmptyReq.add(new Pair<>(-j, j));
            }
            moves.add(new Move(fMove, null, fEmptyReq));
        }
    }
}
