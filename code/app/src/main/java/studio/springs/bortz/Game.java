package studio.springs.bortz;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import studio.springs.bortz.engine.GameEngine;
import studio.springs.bortz.engine.IllegalMoveException;
import studio.springs.bortz.engine.Position;

public class Game extends AppCompatActivity {
    Resources res;
    final GameEngine engine = new GameEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        res = getResources();
    }

    public void buttonPressed(View v) {
        String id = res.getResourceEntryName(v.getId());
        int buttonX = Character.getNumericValue(id.charAt(6));
        int buttonY = Character.getNumericValue(id.charAt(7));
        Toast.makeText(getApplicationContext(), "Pressed button at position x: " + buttonX + " y: " + buttonY, Toast.LENGTH_SHORT).show();
        try {
            engine.selectBoardSquare(new Position(buttonX,buttonY));
        }
        catch (IllegalMoveException ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}