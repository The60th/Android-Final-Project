package com.github.the60th.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.github.the60th.tictactoe.gameInstance.EXTRA_MESSAGE;

public class ActivitySelectForm extends AppCompatActivity {
    //variable to store the tag that we will save data to in the intent.
    public static final String difficultyDataTag = "DiffDataTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_form);
    }
    //Different methods depending on which button is clicked to save the difficulty to a intent,
    //then show it to the player.
    public void easy(View v){
        Difficulty data = Difficulty.Easy;
        Intent myIntent = new Intent(this, gameForm.class);
        myIntent.putExtra(difficultyDataTag, data);
        startActivity(myIntent);
    }
    public void medium(View v){
        Difficulty data = Difficulty.Medium;
        Intent myIntent = new Intent(this, gameForm.class);
        myIntent.putExtra(difficultyDataTag, data);
        startActivity(myIntent);
    }
    public void hard(View v){
        Difficulty data = Difficulty.Hard;
        Intent myIntent = new Intent(this, gameForm.class);
        myIntent.putExtra(difficultyDataTag, data);
        startActivity(myIntent);

    }
}
