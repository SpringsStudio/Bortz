package studio.springs.bortz.engine;

public class Position {
    final public int x;
    final public int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean Equals(Position other) {
        return other.x == x && other.y == y;
    }
    public boolean MoreThan(Position other) {return x > other.x || y > other.y; }
    public boolean LessThan(Position other) {return x < other.x || y < other.y; }
    public static Position Subtract(Position one, Position two){
        return new Position(one.x - two.x, one.y - two.y);
    }
    public static Position Add(Position one, Position two){
        return new Position(one.x + two.x, one.y + two.y);
    }
}
