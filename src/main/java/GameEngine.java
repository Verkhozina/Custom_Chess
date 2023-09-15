import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameEngine {
    Board board;
    List<Piece> pieces;
    Boolean whiteCheck = null;
    public GameEngine(File config) throws Exception{
        Pair<Integer> boardSize = new Pair<>(8, 8);
        pieces = ConfigParser.parseConfig(config, boardSize); //Parse config into pieces
        board = new Board(boardSize.x, boardSize.y);
        if (pieces == null) throw new Exception("Parsing error");
        for (int i = 0; i < board.width; i++)
            for (int j = 0; j < board.length; j++)
                board.tiles[i][j] = new Tile(new Pair<>(i, j), false,null,true);
        for (Piece piece : pieces) {
            for (int j = 0; j < piece.starts.size(); j++) {
                int x = piece.starts.get(j).x;
                int y = piece.starts.get(j).y;
                board.tiles[x][y].piece = new Piece(piece, piece.white);
                board.tiles[x][y].occupied = true;
                if (Objects.equals(piece.name, "King")) {
                    if (board.tiles[x][y].piece.white) board.kingW = new Pair<>(x, y);
                    else board.kingB = new Pair<>(x, y);
                }
            }
        } //Arrange board
    }
    public String [][] printTheBoard(){
        String[][] boardStr = new String[board.width][board.length];
        for (int i = 0; i < board.width; i++)
            for (int j = 0; j < board.length; j++)
                if (board.tiles[i][j].piece != null) boardStr[i][j] = board.tiles[i][j].piece.shortName;
                else boardStr[i][j] = "00";
        return boardStr;
    }
    private boolean checkMoveBasic(Pair<Integer> init, Pair<Integer> goal, Boolean whiteTurn) {
        return init.x < board.width && init.y < board.length && init.x >= 0 && init.y >= 0 &&
                goal.x < board.width && goal.y < board.length && goal.x >= 0 && goal.y >= 0 &&
                board.tiles[init.x][init.y].occupied &&
                whiteTurn == board.tiles[init.x][init.y].piece.white && (
                board.tiles[goal.x][goal.y].piece == null ||
                        whiteTurn != board.tiles[goal.x][goal.y].piece.white
        );
    }
    public boolean makeAMove(String input, Boolean whiteTurn){
        String[] strings = input.split(" ");
        if (strings.length != 2) return false;
        Pair<Integer> init = parseTile(strings[0]);
        Pair<Integer> goal = parseTile(strings[1]);
        if (init == null || goal == null || !checkMoveBasic(init, goal, whiteTurn)) return false;
        Board boardTmp = new Board(board);
        boolean result = board.tiles[init.x][init.y].piece.makeAMove(init, goal, boardTmp);
        if (result)
            if (checkCheck(boardTmp, whiteTurn))
                result = false;
            else {
                whiteCheck = null;
                board = boardTmp;
                board.initPrev = init;
                board.goalPrev = goal;
                if (Objects.equals(board.tiles[goal.x][goal.y].piece.name, "King")) {
                    if (board.tiles[goal.x][goal.y].piece.white) board.kingW = goal;
                    else board.kingB = goal;
                }
                board.tiles[goal.x][goal.y].piece.moved = true;
                if (checkCheck(board, !whiteTurn))
                    if (checkMate(board, !whiteTurn)) System.out.println("GAME OVER");
                    else whiteCheck = !whiteTurn;
            }
        return result;
    }
    private List<Pair<Integer>> possibleMoves(Pair<Integer> init, Board boardTmp){
        if ((init.x < board.width && init.x > -1 && init.y < board.length && init.y > -1) &&
                (board.tiles[init.x][init.y].occupied))
            return board.tiles[init.x][init.y].piece.possibleMoves(init, boardTmp);
        else return new ArrayList<>();
    }
    public String [][] printPossibleMoves(String input) {
        String [][] result = printTheBoard();
        Pair<Integer> tile = parseTile(input);
        if (tile == null) return result;
        List<Pair<Integer>> posMoves = possibleMoves(tile, board);
        for (Pair<Integer> posMove : posMoves) {
            result[posMove.x][posMove.y] = "##";
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
        return new Pair<>(x, y);
    }
    private boolean checkCheck (Board boardCheck, Boolean white) {
        for (int i = 0; i < boardCheck.width; i++)
            for (int j = 0; j < boardCheck.length; j++) {
                if (boardCheck.tiles[i][j].occupied && boardCheck.tiles[i][j].piece.white != white) {
                    List<Pair<Integer>> moves = possibleMoves(new Pair<>(i, j), boardCheck);
                    if ((white && moves.contains(boardCheck.kingW)) || (!white && moves.contains(boardCheck.kingB)))
                        return true;
                }
            }
        return false;
    }
    private boolean checkMate (Board boardCheck, Boolean white) {
        for (int i = 0; i < boardCheck.width; i++)
            for (int j = 0; j < boardCheck.length; j++) {
                if (boardCheck.tiles[i][j].occupied && boardCheck.tiles[i][j].piece.white == white) {
                    List<Pair<Integer>> moves = possibleMoves(new Pair<>(i, j), boardCheck);
                    for (Pair<Integer> m : moves) {
                        if (!(checkMoveBasic(new Pair<>(i,j), m, white))) continue;
                        Board boardTmp = new Board(boardCheck);
                        boolean result = boardCheck.tiles[i][j].piece.makeAMove(new Pair<>(i, j), m, boardTmp);
                        if (result) {
                            if (!checkCheck(boardTmp, white))
                                return false;
                        }
                    }
                }
            }
        return true;
    }

}
