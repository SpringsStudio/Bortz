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
    public void Subtract(Position other){
        x -= other.x;
        y -= other.y;
    }
    public static Position Subtract(Position one, Position two){
        return new Position(one.x - two.x, one.y - two.y);
    }
}
