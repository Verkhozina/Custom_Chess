public class Tile {
    final Pair<Integer> coordinates;
    Boolean occupied;
    Piece piece;
    Boolean pIsWhite;
    Boolean notMoved;
    public Tile (Pair<Integer> coordinates,
    Boolean occupied,
    Piece piece,
                 Boolean pIsWhite,
                 Boolean notMoved) {
      this.coordinates = coordinates;
      this.occupied = occupied;
      this.piece = piece;
      this.pIsWhite = pIsWhite;
      this.notMoved = notMoved;
    }

    @Override
    public String toString() {
        if (!occupied) return "0";
        else return piece.shortName;
    }
}
