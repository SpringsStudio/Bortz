package studio.springs.bortz.engine;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean Equals(Position other) {
        return other.x == x && other.y == y;
    }
}
