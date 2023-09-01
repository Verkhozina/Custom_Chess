import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameEngine {
    Board board;
    List<Piece> pieces;
    public GameEngine(File config) throws Exception{
        pieces = ConfigParser.parseConfig(config); //Parse config into pieces
        if (pieces == null) throw new Exception("Parsing error");
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
    public String [][] printTheBoard(){
        String[][] boardStr = new String[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.tiles[i][j].piece != null) boardStr[i][j] = board.tiles[i][j].piece.shortName;
                else boardStr[i][j] = "0";
        return boardStr;
    }
    public boolean makeAMove(String input, Boolean whiteTurn){
        String[] strings = input.split(" ");
        if (strings.length != 2) return false;
        Pair<Integer> init = parseTile(strings[0]);
        if (init == null) return false;
        Pair<Integer> goal = parseTile(strings[1]);
        if (goal == null) return false;
        if (board.tiles[init.x][init.y].piece == null ||
                whiteTurn != board.tiles[init.x][init.y].piece.white)
            return false;
        boolean result = board.tiles[init.x][init.y].piece.makeAMove(init, goal, board);
        if (result) {
            board.initPrev = init;
            board.goalPrev = goal;
        }
        return result;
    }
    private List<Pair<Integer>> possibleMoves(Pair<Integer> init){
        if (board.tiles[init.x][init.y].occupied)
            return board.tiles[init.x][init.y].piece.possibleMoves(init, board);
        else return new ArrayList<>();
    }
    public String [][] printPossibleMoves(String input) {
        String [][] result = printTheBoard();
        Pair<Integer> parsed = parseTile(input);
        if (parsed == null) return result;
        List<Pair<Integer>> posMoves = possibleMoves(parseTile(input));
        for (Pair<Integer> posMove : posMoves) {
            result[posMove.x][posMove.y] = "#";
        }
        return result;
    }
    private Pair<Integer> parseTile(String tile){
        if (tile == null) return null;
        char ch = tile.charAt(0);
        int x = ch - 'a';
        int y;
        try {y = Character.getNumericValue(tile.charAt(1) - 1);}
        catch (Exception e) {return null;}
        if (!(x < board.width && y < board.length && x > -1 && y > -1)) return null;
        return new Pair<>(x, y);
    }
    public boolean checkCheck () {
        List<Pair<Integer>> moves = possibleMoves(board.goalPrev);
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
