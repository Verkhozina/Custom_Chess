import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveKnight extends Move {
    final Integer distance1;
    final Integer distance2;
    List<Pair<Integer>> possibleMoves(Pair<Integer> init, Board board) {
        List<Pair<Integer>> moves;
        moves = knightMoves(init, board).stream().filter(o -> !(board.tiles[o.x][o.y].occupied &&
                board.tiles[o.x][o.y].piece.white == board.tiles[init.x][init.y].piece.white)).collect(Collectors.toList());
        return moves;
    }
    boolean makeAMove(Pair<Integer> init, Pair<Integer> goal, Board board) {
        if (checkMove(init, goal, board)) {
            board.tiles[goal.x][goal.y].piece = board.tiles[init.x][init.y].piece;
            board.tiles[goal.x][goal.y].occupied = true;
            board.tiles[init.x][init.y].occupied = false;
            board.tiles[init.x][init.y].piece = null;
            board.tiles[goal.x][goal.y].piece.moved = true;
            return true;
        }
        return false;
    }
    boolean checkMove(Pair<Integer> init, Pair<Integer> goal, Board board) {
        if (board.tiles[goal.x][goal.y].occupied &&
                board.tiles[goal.x][goal.y].piece.white == board.tiles[init.x][init.y].piece.white)
            return false;
        return knightMoves(init, board).contains(goal);
    }
    List<Pair<Integer>> knightMoves (Pair<Integer> init, Board board) {
        List<Pair<Integer>> moves = new ArrayList<>();
        moves.add(new Pair<>(init.x + distance1, init.y + distance2));
        moves.add(new Pair<>(init.x + distance1, init.y - distance2));
        moves.add(new Pair<>(init.x - distance1, init.y + distance2));
        moves.add(new Pair<>(init.x - distance1, init.y - distance2));
        moves.add(new Pair<>(init.x + distance2, init.y + distance1));
        moves.add(new Pair<>(init.x + distance2, init.y - distance1));
        moves.add(new Pair<>(init.x - distance2, init.y + distance1));
        moves.add(new Pair<>(init.x - distance2, init.y - distance1));
        moves = moves.stream().filter(o -> (o.x > -1 && o.x < board.width && o.y > -1 && o.y < board.length)).collect(Collectors.toList());
        return moves;
    }
    public MoveKnight(Integer distance1, Integer distance2) {
        this.distance1 = distance1;
        this.distance2 = distance2;
    }
}
