package studio.springs.bortz.engine.utils;

import java.util.ArrayList;
import java.util.List;

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
    public static List<Position> listPositions(Position from, Position to){
        List<Position> list = new ArrayList<>(((to.x - from.x + 1) * (to.y - from.y + 1)));
        for (int xi = from.x; xi <= to.x; xi++){
            for (int yi = from.y; yi <= to.y; yi++){
                list.add(new Position(xi,yi));
            }
        }
        return list;
    }
    public String toStr(){
        return "(" + x + "," + y + ")";
    }
}
