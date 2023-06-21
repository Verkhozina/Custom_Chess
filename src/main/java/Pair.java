class Pair<T> {
    T x, y;
    Pair(T x, T y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair<T> other = (Pair<T>) obj;
        return x == other.x && y == other.y;
    }
}
