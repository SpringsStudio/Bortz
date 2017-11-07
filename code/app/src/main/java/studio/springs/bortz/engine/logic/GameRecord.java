package studio.springs.bortz.engine.logic;

import java.util.ArrayList;
import java.util.List;

public class GameRecord {
    List<GameMove> moves = new ArrayList<>();

    public void addMove(GameMove move){
        moves.add(move);
    }
}
