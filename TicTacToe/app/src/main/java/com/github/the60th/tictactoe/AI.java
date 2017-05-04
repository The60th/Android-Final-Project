package com.github.the60th.tictactoe;


import android.util.Log;

import java.util.Random;

import static com.github.the60th.tictactoe.gameInstance.debugTag;

/**
 * Created by Justin on 5/2/2017.
 * All code handled by Justin.
 */

public class AI extends Player {

    public AI(String name, PieceType type, boolean myTurn) {
        super(name, type, myTurn);
    }

    public boolean playTurn(gameInstance gameInstance){ //Return true if the AI was able to finish its turn.
        if(is_MyTurn()){
        if(gameInstance.is_DebugMode()) Log.i(debugTag,"Called within AiClass: my game field: " + System.lineSeparator() +
                gameInstance.get_myGameField()[0] + " " + gameInstance.get_myGameField()[1] + " " + gameInstance.get_myGameField()[2] + System.lineSeparator() +
                gameInstance.get_myGameField()[3] + " " + gameInstance.get_myGameField()[4] + " " + gameInstance.get_myGameField()[5] + System.lineSeparator() +
                gameInstance.get_myGameField()[6] + " " + gameInstance.get_myGameField()[7] + " " + gameInstance.get_myGameField()[8] + " "

        );
            Random random = new Random();
            int x = random.nextInt(9);
            while(!(gameInstance.get_myGameField()[x].equals("e"))){
                x = random.nextInt(9);
            }
                gameInstance.placeTile(gameInstance.get_myButtons(x),this);
        }
        else{
            Log.i(debugTag,"Invalid call on AI not currently its turn.");
            return false;
        }
        return true;
    }
}
