public class Tile {
    final Pair<Integer> coordinates;
    Boolean occupied;
    Piece piece;
    final Boolean white;
    public Tile (Pair<Integer> coordinates,
                 Boolean occupied,
                 Piece piece,
                 Boolean white) {
        this.coordinates = coordinates;
        this.occupied = occupied;
        this.piece = piece;
        this.white = white;
    }
    public Tile (Tile tile) {
        this.coordinates = new Pair<>(tile.coordinates);
        this.occupied = tile.occupied;
        if (tile.piece != null)
            this.piece = new Piece(tile.piece, tile.piece.white);
        this.white = tile.white;
    }

    @Override
    public String toString() {
        if (!occupied) return "0";
        else return piece.shortName;
    }
}
