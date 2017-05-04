package com.github.the60th.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class gameForm extends AppCompatActivity {
    private ImageButton[] myButtons = new ImageButton[9];
    private  gameInstance myGame;
    private  Player player = new Player("hi",PieceType.X,true);
    private AI ai = new AI("hi",PieceType.O,false);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);

        myButtons[0] = (ImageButton) findViewById(R.id.buttonTop1);
        myButtons[1] = (ImageButton) findViewById(R.id.buttonTop2);
        myButtons[2] = (ImageButton) findViewById(R.id.buttonTop3);

        myButtons[3] = (ImageButton) findViewById(R.id.buttonMiddle1);
        myButtons[4] = (ImageButton) findViewById(R.id.buttonMiddle2);
        myButtons[5] = (ImageButton) findViewById(R.id.buttonMiddle3);

        myButtons[6] = (ImageButton) findViewById(R.id.buttonBottom1);
        myButtons[7] = (ImageButton) findViewById(R.id.buttonBottom2);
        myButtons[8] = (ImageButton) findViewById(R.id.buttonBottom3);

        myGame = new gameInstance(myButtons,player,ai);

    }

    public void clickTopLeft(View v){
        myGame.placeTile(myButtons[0],player);
        createMessage("Top left was clicked", v);
    }
    public void clickTopMid(View v){
        myGame.placeTile(myButtons[1],player);
        createMessage("Top mid was clicked", v);
    }
    public void clickTopRight(View v){
        myGame.placeTile(myButtons[2],player);
        createMessage("Top right was clicked", v);
    }

    public void clickMidLeft(View v){
        myGame.placeTile(myButtons[3],player);
        createMessage("Mid left was clicked", v);

    }
    public void clickMidMid(View v){
        myGame.placeTile(myButtons[4],player);
        createMessage("Mid mid was clicked", v);
    }
    public void clickMidRight(View v){
        myGame.placeTile(myButtons[5],player);
        createMessage("Mid right was clicked", v);
    }

    public void clickBotLeft(View v){
        myGame.placeTile(myButtons[6],player);
        createMessage("Bot left was clicked", v);
    }
    public void clickBotMid(View v){
        myGame.placeTile(myButtons[7],player);
        createMessage("Bot mid was clicked", v);
    }
    public void clickBotRight(View v){
        myGame.placeTile(myButtons[8],player);
        createMessage("Bot right was clicked", v);
    }


    private void createMessage(String message, View v){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
