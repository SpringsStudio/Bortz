package studio.springs.bortz;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Queue;

import studio.springs.bortz.engine.GameChange;
import studio.springs.bortz.engine.GameEngine;
import studio.springs.bortz.engine.IllegalMoveException;
import studio.springs.bortz.engine.Position;
import studio.springs.bortz.engine.pieces.PieceColor;

public class Game extends AppCompatActivity {
    Resources res;
    final GameEngine engine = new GameEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        res = getResources();
        updateView();
    }

    public void buttonPressed(View v) {
        final String id = res.getResourceEntryName(v.getId());
        final int buttonX = Character.getNumericValue(id.charAt(6));
        final int buttonY = Character.getNumericValue(id.charAt(7));
        try {
            engine.selectBoardSquare(new Position(buttonX,buttonY));
            updateView();
        }
        catch (IllegalMoveException ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void updateView(){
        final Queue<GameChange> changes = engine.getChanges();
        while (changes.peek() != null) {
            GameChange change = changes.remove();
            final ImageButton button = (ImageButton) findViewById(res.getIdentifier("button" + change.getPosition().x + change.getPosition().y, "id", this.getPackageName()));
            switch (change.getType()) {
                case PIECE_ADDED:
                    button.setAlpha(1.0f);
                    switch (change.getPiece().getType()){
                        case LION:
                            button.setImageResource(R.drawable.ic_lion);
                            break;
                        case GIRAFFE:
                            break;
                        case ELEPHANT:
                            break;
                    }
                    if (change.getPiece().getColor() == PieceColor.WHITE) {
                        button.setColorFilter(Color.WHITE);
                    }
                    else button.setColorFilter(null);
                    break;
                case PIECE_REMOVED:
                    button.setAlpha(0.0f);
                    break;
            }
        }
    }
}
