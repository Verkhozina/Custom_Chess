public class Tile {
    final Pair<Integer> coordinates;
    Boolean occupied;
    Piece piece;
    Boolean white;
    public Tile (Pair<Integer> coordinates,
                 Boolean occupied,
                 Piece piece,
                 Boolean white) {
        this.coordinates = coordinates;
        this.occupied = occupied;
        this.piece = piece;
        this.white = white;
    }

    @Override
    public String toString() {
        if (!occupied) return "0";
        else return piece.shortName;
    }
}
