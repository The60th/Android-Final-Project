package com.github.the60th.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.github.the60th.tictactoe.ActivitySelectForm.difficultyDataTag;
import static com.github.the60th.tictactoe.gameInstance.EXTRA_MESSAGE;

public class ActivityWinForm extends AppCompatActivity {
    Difficulty myDiff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        TextView demoDisplay = (TextView) findViewById(R.id.demoDisplay);
        myDiff = (Difficulty) intent.getSerializableExtra(difficultyDataTag);
        Log.i(gameInstance.debugTag, "Displaying message: (" + message + ")");
        demoDisplay.setText(message);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this,gameForm.class);
        intent.putExtra(difficultyDataTag,myDiff);
        startActivity(intent);
    }
    public void changeDiff(View v){
        Intent intent = new Intent(this,ActivitySelectForm.class);
        startActivity(intent);
    }
}
