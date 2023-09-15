import java.util.ArrayList;
import java.util.List;

public class Piece {
    final String name;
    final String shortName;
    final List<Pair<Integer>> starts;
    Boolean moved;
    final Boolean white;
    final List<Move> moves;
    boolean makeAMove(Pair<Integer> init, Pair<Integer> goal, Board board){
        for (Move m : moves) {
            if (m.makeAMove(init, goal, board))
                return true;
        }
        return false;
    }
    List<Pair<Integer>> possibleMoves(Pair<Integer> init, Board board){
        List<Pair<Integer>> posMoves = new ArrayList<>();
        for (Move m : moves) {
            posMoves.addAll(m.possibleMoves(init, board));
        }
        return posMoves;
    }
    public Piece (String name, String shortName, List<Pair<Integer>> starts, Boolean white, List<Move> moves) {
        this.name = name;
        this.shortName = shortName;
        this.starts = starts;
        this.moved = false;
        this.white = white;
        this.moves = moves;
    }
    public Piece (Piece piece, Boolean white) {
        this.name = piece.name;
        this.shortName = piece.shortName;
        this.starts = piece.starts;
        this.moved = false;
        this.white = white;
        this.moves = piece.moves;
    }
}
