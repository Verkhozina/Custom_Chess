public class Board {
    Pair<Integer> initPrev;
    Pair<Integer> goalPrev;
    final Integer width;
    final Integer length;
    Tile[][] tiles;
    Pair<Integer> kingW;
    Pair<Integer> kingB;
    public Board(Integer width, Integer length) {
        this.width = width;
        this.length = length;
        this.tiles = new Tile[width][length];
        this.initPrev = null;
        this.goalPrev = null;
        this.kingW = null;
        this.kingB = null;
    }
    public Board (Board board) {
        if (board.initPrev != null)
            this.initPrev = new Pair<>(board.initPrev);
        if (board.goalPrev != null)
            this.goalPrev = new Pair<>(board.goalPrev);
        this.width = board.width;
        this.length = board.length;
        this.tiles = new Tile[board.width][board.length];
        for (int i = 0; i < board.width; i++)
            for (int j = 0; j < board.length; j++) {
                this.tiles[i][j] = new Tile(board.tiles[i][j]);
            }
        if (board.kingW != null)
        this.kingW = new Pair<>(board.kingW);
        if (board.kingB != null)
        this.kingB = new Pair<>(board.kingB);
    }
}
