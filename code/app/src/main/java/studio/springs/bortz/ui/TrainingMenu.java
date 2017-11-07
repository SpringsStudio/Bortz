package studio.springs.bortz.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import studio.springs.bortz.R;

public class TrainingMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_menu);


    }
    public void fightAI(View view) {
        Intent game = new Intent(this, Game.class);
        startActivity(game);
    }
}
