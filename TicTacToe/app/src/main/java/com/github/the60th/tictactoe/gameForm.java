package com.github.the60th.tictactoe;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

import static com.github.the60th.tictactoe.ActivitySelectForm.difficultyDataTag;
import static com.github.the60th.tictactoe.gameInstance.EXTRA_MESSAGE;
import static com.github.the60th.tictactoe.gameInstance.debugTag;

public class gameForm extends AppCompatActivity {
    private ImageButton[] myButtons = new ImageButton[9];
    private gameInstance myGame;
    private Player player;
    private AI ai;
    private int _Index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form);
        PieceType playerType;
        PieceType aiType;
        Random rand = new Random();
        int x = rand.nextInt(2);

        if(x == 0){
            playerType = PieceType.X;
            aiType = PieceType.O;
            TextView tx = (TextView) findViewById(R.id.textView11);
            tx.setText("Your piece type is X.");
        }
        else if(x == 1){
            playerType = PieceType.O;
            aiType = PieceType.X;
            TextView tx = (TextView) findViewById(R.id.textView11);
            tx.setText("Your piece type is O.");
        }
        else{
            Log.i(debugTag,"Unable to randomly select piece type.");
            playerType = PieceType.X;
            aiType = PieceType.O;
        }

        Intent intent = getIntent();
        Difficulty data = (Difficulty) intent.getSerializableExtra(difficultyDataTag);
        switch (data) {
            case Easy:
                ai = new AI("AI", aiType, false, Difficulty.Easy);
                break;
            case Medium:
                ai = new AI("AI", aiType, false, Difficulty.Medium);
                break;
            case Hard:
                ai = new AI("AI", aiType, false, Difficulty.Hard);
                break;
            case Super_Hard:
                ai = new AI("AI", aiType, false, Difficulty.Super_Hard);
                break;
            default:
                ai = new AI("AI", aiType, false, Difficulty.Medium);
                break;
        }
        player = new Player("Player", playerType, true);

        myButtons[0] = (ImageButton) findViewById(R.id.buttonTop1);
        myButtons[1] = (ImageButton) findViewById(R.id.buttonTop2);
        myButtons[2] = (ImageButton) findViewById(R.id.buttonTop3);

        myButtons[3] = (ImageButton) findViewById(R.id.buttonMiddle1);
        myButtons[4] = (ImageButton) findViewById(R.id.buttonMiddle2);
        myButtons[5] = (ImageButton) findViewById(R.id.buttonMiddle3);

        myButtons[6] = (ImageButton) findViewById(R.id.buttonBottom1);
        myButtons[7] = (ImageButton) findViewById(R.id.buttonBottom2);
        myButtons[8] = (ImageButton) findViewById(R.id.buttonBottom3);

        myGame = new gameInstance(myButtons, player, ai, this);
        if (whoGoesFirst() == ai) {
            createMessage("You are " + player.get_PieceType().toString().toUpperCase() + "s, and you are going second."
                    + System.lineSeparator() + "Good luck!");
            player.set_MyTurn(false);
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
                Log.i(debugTag,"Unable to randomly select first player.");
                createMessage("Invalid AI turn call");
            }
        } else {
            createMessage("You are " + player.get_PieceType().toString().toUpperCase() + "s, and you are going first."
                    + System.lineSeparator() + "Good luck!");
        }

    }

    public void clickTopLeft(View v) {
        _Index = 0;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!(myGame.placeTile(myButtons[_Index], player))) return;
          //  createMessage("Top left was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
               // createMessage("Invalid AI turn call");
            }
        }

    }


    public void clickTopMid(View v) {
        _Index = 1;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!myGame.placeTile(myButtons[_Index], player)) return;
          //  createMessage("Top mid was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
               // createMessage("Invalid AI turn call");
            }
        }
    }

    public void clickTopRight(View v) {
        _Index = 2;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!(myGame.placeTile(myButtons[_Index], player))) return;
           // createMessage("Top right was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
              //  createMessage("Invalid AI turn call");
            }
        }
    }

    public void clickMidLeft(View v) {
        _Index = 3;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!(myGame.placeTile(myButtons[_Index], player))) return;
           // createMessage("Mid left was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
              //  createMessage("Invalid AI turn call");
            }

        }
    }

    public void clickMidMid(View v) {
        _Index = 4;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!(myGame.placeTile(myButtons[_Index], player))) return;
            //createMessage("Mid mid was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
                //createMessage("Invalid AI turn call");
            }
        }
    }

    public void clickMidRight(View v) {
        _Index = 5;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!(myGame.placeTile(myButtons[_Index], player))) return;
           // createMessage("Mid right was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
                //createMessage("Invalid AI turn call");
            }
        }
    }

    public void clickBotLeft(View v) {
        _Index = 6;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!(myGame.placeTile(myButtons[_Index], player))) return;
            //createMessage("Bot left was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
                //createMessage("Invalid AI turn call");
            }
        }
    }

    public void clickBotMid(View v) {
        _Index = 7;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!(myGame.placeTile(myButtons[_Index], player))) return;
           // createMessage("Bot mid was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
               // createMessage("Invalid AI turn call");
            }
        }
    }

    public void clickBotRight(View v) {
        _Index = 8;
        if (!myGame.get_myGameBoolean()[_Index]) {
            createMessage("Invalid tile, already placed.");
            return;
        }
        if (checkTurn(player, v)) {
            if (!(myGame.placeTile(myButtons[_Index], player))) return;
            //createMessage("Bot right was clicked");
            ai.set_MyTurn(true);
            if (ai.playTurn(myGame)) {
                player.set_MyTurn(true);
            } else {
                //createMessage("Invalid AI turn call");
            }
        }
    }


    private void createMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean checkTurn(Player player, View v) {
        if (player.is_MyTurn()) {
            player.set_MyTurn(false);
            return true;
        } else {
            createMessage("It is currently not the players turn");
            return false;
        }
    }

    private Player whoGoesFirst() {
        Random rand = new Random();
        int x = rand.nextInt(10) + 1;
        Log.i(gameInstance.debugTag, "Checking who goes first, random number is = " + x +
                " and difficulty is " + ai.get_difficulty().toString() + " ");
        switch (ai.get_difficulty()) {
            case Easy:
                if (x >= 4) return player;
                else return ai;
            case Medium:
                if (x > 5) return player;
                else return ai;
            case Hard:
                if (x > 5) return player;
                else return ai;
            case Super_Hard:
                if (x > 7) return player;
                else return ai;
            default:
                return null;
        }
    }

}
