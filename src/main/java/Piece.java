import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    List<Pair<Integer>> starts = new ArrayList<>();
    String shortName = "";
    List<Move> moves = new ArrayList<>();

    public Tile[][] specialMove(Pair<Integer> init, Pair<Integer> goal, Tile[][] board, Pair<Integer> initPrev, Pair<Integer> goalPrev) {
        return null;
    }
}
