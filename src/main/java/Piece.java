import java.util.List;

public class Piece {
    String name;
    String shortName;
    List<Pair<Integer>> starts;
    Boolean moved;
    Boolean white;
    List<Move> moves;
    List<Move> movesSpecial;

    public Piece (String name, String shortName, List<Pair<Integer>> starts, Boolean white, List<Move> moves, List<Move> movesSpecial) {
        this.name = name;
        this.shortName = shortName;
        this.starts = starts;
        this.moved = false;
        this.white = white;
        this.moves = moves;
        this.movesSpecial = movesSpecial;
    }
    public Piece (Piece piece, Boolean white) {
        this.name = piece.name;
        this.shortName = piece.shortName;
        this.starts = piece.starts;
        this.moved = false;
        this.white = white;
        this.moves = piece.moves;
        this.movesSpecial = piece.movesSpecial;
    }
}
