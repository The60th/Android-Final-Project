package com.github.the60th.tictactoe;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class gameForm extends AppCompatActivity {
    private ImageButton[] myButtons = new ImageButton[9];
    private gameInstance myGame;
    private Player player = new Player("hi", PieceType.X, true);
    private AI ai = new AI("hi", PieceType.O, false);
    private int _Index;
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

        myGame = new gameInstance(myButtons, player, ai,this);
    }

    public void clickTopLeft(View v) {

        _Index = 0;
        if(!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.", v);
            return;
        }
        if (checkTurn(player, v)) {

            myGame.placeTile(myButtons[_Index], player);
            createMessage("Top left was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }
            }

        }


    public void clickTopMid(View v) {
        _Index = 1;
        if(!myGame.get_myGameBoolean()[_Index]){
            createMessage("Invalid tile, already placed.",v);
            return;
        }
        if (checkTurn(player, v)) {
            myGame.placeTile(myButtons[_Index], player);
            createMessage("Top mid was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }
        }
    }

    public void clickTopRight(View v) {
        _Index = 2;
        if(!myGame.get_myGameBoolean()[_Index]){
            createMessage("Invalid tile, already placed.",v);
            return;
        }
        if (checkTurn(player, v)) {
            myGame.placeTile(myButtons[_Index], player);
            createMessage("Top right was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }
        }
    }

    public void clickMidLeft(View v) {
        _Index = 3;
        if(!myGame.get_myGameBoolean()[_Index]){
            createMessage("Invalid tile, already placed.",v);
            return;
        }
        if (checkTurn(player, v)) {
            myGame.placeTile(myButtons[_Index], player);
            createMessage("Mid left was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }

        }
    }

    public void clickMidMid(View v) {
        _Index = 4;
        if(!myGame.get_myGameBoolean()[_Index]){
            createMessage("Invalid tile, already placed.",v);
            return;
        }
        if (checkTurn(player, v)) {
            myGame.placeTile(myButtons[_Index], player);
            createMessage("Mid mid was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }
        }
    }

    public void clickMidRight(View v) {
        _Index = 5;
        if(!myGame.get_myGameBoolean()[_Index]){
            createMessage("Invalid tile, already placed.",v);
            return;
        }
        if (checkTurn(player, v)) {
            myGame.placeTile(myButtons[_Index], player);
            createMessage("Mid right was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }
        }
    }

    public void clickBotLeft(View v) {
        _Index = 6;
        if(!myGame.get_myGameBoolean()[_Index]){
            createMessage("Invalid tile, already placed.",v);
            return;
        }
        if (checkTurn(player, v)) {
            myGame.placeTile(myButtons[_Index], player);
            createMessage("Bot left was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }
        }
    }

    public void clickBotMid(View v) {
        _Index = 7;
        if(!myGame.get_myGameBoolean()[_Index]){
            createMessage("Invalid tile, already placed.",v);
            return;
        }
        if (checkTurn(player, v)) {
            myGame.placeTile(myButtons[_Index], player);
            createMessage("Bot mid was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }
        }
    }

    public void clickBotRight(View v) {
        _Index = 8;
        if(!myGame.get_myGameBoolean()[_Index]){
            createMessage("Invalid tile, already placed.",v);
            return;
        }
        if (checkTurn(player, v)) {
            myGame.placeTile(myButtons[_Index], player);
            createMessage("Bot right was clicked", v);
            ai.set_MyTurn(true);
            if(ai.playTurn(myGame)){
                player.set_MyTurn(true);
            }
            else{
                createMessage("Invalid AI turn call",v);
            }
        }
    }


    private void createMessage(String message, View v) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean checkTurn(Player player, View v) {
        if (player.is_MyTurn()) {
            player.set_MyTurn(false);
            return true;
        } else {
            createMessage("It is currently not the players turn", v);
            return false;
        }
    }

}
