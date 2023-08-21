public class Board {
    Pair<Integer> initPrev;
    Pair<Integer> goalPrev;
    Integer width;
    Integer length;
    Tile[][] tiles;
    public Board(Integer width, Integer length) {
        this.width = width;
        this.length = length;
        this.tiles = new Tile[width][length];
        this.initPrev = null;
        this.goalPrev = null;
    }
}
