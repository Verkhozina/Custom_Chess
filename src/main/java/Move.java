public class Move {
    final Pair<Integer> coords;
    final Boolean jump;
    public Move(Pair<Integer> coords,
                Boolean jump) {
        this.coords = coords;
        this.jump = jump;
    }
}
