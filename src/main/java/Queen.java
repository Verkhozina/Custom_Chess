import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{

    public Queen(Integer board){
        shortName = "Q";
        starts.add(new Pair<>(4, 0));
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
