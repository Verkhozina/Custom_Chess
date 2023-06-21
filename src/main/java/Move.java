import java.util.List;
import java.util.function.Function;

public class Move {
    final Pair<Integer> fMove;
    final Boolean fCapture;
    final List<Pair<Integer>> fEmptyReq;
    public Move(Pair<Integer> fMove,
                Boolean fCapture,
                List<Pair<Integer>> fEmptyReq) {
        this.fMove = fMove;
        this.fCapture = fCapture;
        this.fEmptyReq = fEmptyReq;
    }
}
