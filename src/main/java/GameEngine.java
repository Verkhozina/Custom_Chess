import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameEngine {
    Board board;
    List<Piece> pieces;
    public GameEngine(File config) throws Exception{
        pieces = ConfigParser.parseConfig(config); //Parse config into pieces
        board = new Board(8, 8);
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.width; j++)
                board.tiles[i][j] = new Tile(new Pair<>(i, j), false,null,true);
        for (Piece piece : pieces) {
            for (int j = 0; j < piece.starts.size(); j++) {
                int x = piece.starts.get(j).x;
                int y = piece.starts.get(j).y;
                board.tiles[x][y].piece = new Piece(piece, piece.white);
                board.tiles[x][y].occupied = true;
            }
        } //Arrange board
    }
    String [][] printTheBoard(){
        String[][] boardStr = new String[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.tiles[i][j].piece != null) boardStr[i][j] = board.tiles[i][j].piece.shortName;
                else boardStr[i][j] = "0";
        return boardStr;
    }
    boolean makeAMove(String input, Boolean whiteTurn){
        String[] strings = input.split(" ");
        Pair<Integer> init = parseTile(strings[0]);
        Pair<Integer> goal = parseTile(strings[1]);
        if (board.tiles[init.x][init.y].piece == null ||
                whiteTurn != board.tiles[init.x][init.y].piece.white ||
                (board.tiles[goal.x][goal.y].piece != null && whiteTurn == board.tiles[goal.x][goal.y].piece.white))
            return false;
        List<Pair<Integer>> moves = possibleOrdMoves(init);
        if (moves.contains(parseTile(strings[1]))) {
            board.tiles[goal.x][goal.y].occupied = true;
            board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
            board.tiles[goal.x][goal.y].piece.moved = true;
            board.tiles[init.x][init.y].occupied = false;
            board.tiles[init.x][init.y].piece = null;
            board.initPrev = init;
            board.goalPrev = goal;
            return true;
        }
        moves = possibleSpecMoves(strings[0]);
        if (moves.contains(parseTile(strings[1]))) {
            Board tmp = SpecialMoves.makeASpecMove(init, goal, board);
            if (tmp == null) return false;
            board = tmp;
            return true;
        }
        return false;
    }
    List<Pair<Integer>> possibleOrdMoves(Pair<Integer> tile){
        if (tile.x >= board.width || tile.y >= board.length ||
                board.tiles[tile.x][tile.y].piece == null) {
            //System.out.println("Invalid tile");
            return null;
        }
        Boolean white = board.tiles[tile.x][tile.y].piece.white;
        List<Pair<Integer>> result = new ArrayList<>();
        move:
        for (int i = 0; i < board.tiles[tile.x][tile.y].piece.moves.size(); i++) {
            int xMove = board.tiles[tile.x][tile.y].piece.moves.get(i).coords.x;
            int yMove = board.tiles[tile.x][tile.y].piece.moves.get(i).coords.y;
            if (tile.x + xMove >= board.width || tile.y + yMove >= board.length ||
                    tile.x + xMove < 0 || tile.y + yMove < 0) continue;
            if (board.tiles[tile.x + xMove][tile.y + yMove].piece != null &&
                    white == board.tiles[tile.x + xMove][tile.y + yMove].piece.white) continue;
            if (!board.tiles[tile.x][tile.y].piece.moves.get(i).jump) {
                int ix = 0;
                int iy = 0;
                while (ix < xMove || iy < yMove) {
                    if (ix < xMove) ix++;
                    if (iy < yMove) iy++;
                    if (!(ix < xMove || iy < yMove)) break;
                    if (board.tiles[tile.x + ix][tile.y + iy].occupied) continue move;
                }
                while (ix > xMove || iy > yMove) {
                    if (ix > xMove) ix--;
                    if (iy > yMove) iy--;
                    if (!(ix > xMove || iy > yMove)) break;
                    if (board.tiles[tile.x + ix][tile.y + iy].occupied) continue move;
                }
            }
            result.add(new Pair<>(tile.x + xMove, tile.y + yMove));
        }
        return result;
    }
    List<Pair<Integer>> possibleSpecMoves(String input){
        Pair<Integer> tile = parseTile(input);
        if (tile.x >= board.width || tile.y >= board.length ||
                board.tiles[tile.x][tile.y].piece == null) {
            //System.out.println("Invalid tile");
            return null;
        }
        List<Pair<Integer>> result = new ArrayList<>();
        for (int i = 0; i < board.tiles[tile.x][tile.y].piece.movesSpecial.size(); i++) {
            int xMove = board.tiles[tile.x][tile.y].piece.movesSpecial.get(i).coords.x;
            int yMove = board.tiles[tile.x][tile.y].piece.movesSpecial.get(i).coords.y;
            if (tile.x + xMove >= board.width || tile.y + yMove >= board.length ||
                    tile.x + xMove < 0 || tile.y + yMove < 0)
                continue;
            result.add(new Pair<>(tile.x + xMove, tile.y + yMove));
        }
        return result;
    }
    String [][] printPossibleMoves(String input) {
        String [][] result = printTheBoard();
        List<Pair<Integer>> posOrdMoves = possibleOrdMoves(parseTile(input));
        if (posOrdMoves == null) {return result;}
        List<Pair<Integer>> posSpecMoves = possibleSpecMoves(input);
        for (Pair<Integer> posOrdMove : posOrdMoves) {
            result[posOrdMove.x][posOrdMove.y] = "#";
        }
        for (Pair<Integer> posSpecMove : posSpecMoves) {
            result[posSpecMove.x][posSpecMove.y] = "#";
        }
        return result;
    }
    private static Pair<Integer> parseTile(String tile){
        if (tile == null) return null;
        char ch = tile.charAt(0);
        int x = ch - 'a';
        int y = Character.getNumericValue(tile.charAt(1) - 1);
        return new Pair<>(x, y);
    }
    public boolean checkCheck () {
        List<Pair<Integer>> moves = possibleOrdMoves(board.goalPrev);
        Pair<Integer> prevCords = new Pair<>(board.goalPrev.x, board.goalPrev.y);
        Pair<Integer> kingCords = moves.stream().filter(o -> (
                board.tiles[o.x][o.y].piece != null && Objects.equals(board.tiles[o.x][o.y].piece.name, "King") &&
                board.tiles[prevCords.x][prevCords.y].piece.white != board.tiles[o.x][o.y].piece.white)).findFirst().orElse(null);
        if (kingCords != null) {
            //INSANE
            return true;
        }
        return false;
    }

}
