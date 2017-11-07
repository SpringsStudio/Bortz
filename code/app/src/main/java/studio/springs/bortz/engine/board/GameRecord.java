package studio.springs.bortz.engine.board;

import java.util.LinkedList;
import java.util.Queue;

public class GameRecord {
    Queue<GameMove> moves = new LinkedList<>();

    public void addMove(GameMove move){
        moves.add(move);
    }
}
