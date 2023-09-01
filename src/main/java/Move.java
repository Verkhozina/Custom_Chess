import java.util.List;

public abstract class Move {
    abstract List<Pair<Integer>> possibleMoves(Pair<Integer> init, Board board);
    abstract boolean makeAMove(Pair<Integer> init, Pair<Integer> goal, Board board);
    abstract boolean checkMove(Pair<Integer> init, Pair<Integer> goal, Board board);
}
