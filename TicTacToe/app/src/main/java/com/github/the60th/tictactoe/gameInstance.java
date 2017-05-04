package com.github.the60th.tictactoe;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Justin on 5/2/2017.
 * All code handled by Justin.
 */

public class gameInstance {
    //Class variables.
    //3by3 magic square:
    //  8 1 6
    //  3 5 7
    //  4 9 2
    //sum of a colume is 15.
    private boolean debugMode = true;
    private String debugTag = "<:My Debug Data:>";
    private int[] magicSquare = { 8, 1, 6,
                                  3, 5, 7,
                                  4, 9, 2
                                };

    private String _PlayerTile = "p";
    private String _AITile = "a";
    private String _EmptyTile = "e";
    private String _PlayerWinCondition = "ppp";
    private String _AiWinCondition = "aaa";
    private Player _player;
    private AI _AI;
    private ImageButton[] _myButtons;
    private String[] _myGameField = {
            _EmptyTile, _EmptyTile, _EmptyTile,
            _EmptyTile, _EmptyTile, _EmptyTile,
            _EmptyTile, _EmptyTile, _EmptyTile
                                 };

    //Class getters
    public Player get_player() {
        return _player;
    }

    public AI get_AI() {
        return _AI;
    }

    public ImageButton[] get_myButtons() {
        return _myButtons;
    }
    public ImageButton get_myButtons(int index){
        return _myButtons[index];
    }

    //Class constructor
    public gameInstance(ImageButton[] myButtons, Player player, AI ai){
        int _size = 9;
        if(myButtons.length != _size){
            throw new IllegalArgumentException(
                    "You must create a game instance with an array of nine buttons."
            );
        }
        _myButtons = myButtons;
        _player = player;
        _AI = ai;
    }

    public void placeTile(ImageButton clicked,Player whoClicked){
        String tile;
        if(whoClicked instanceof AI){
            //Ai click methods.
            tile = _AITile;
        }
        else {
            //Player click method.
            tile = _PlayerTile;
        }
        //else{
        //    throw new NullPointerException(
          //          "Instance of whoClicked is null."
          //  );
       // }

        int _index;
        for(int i = 0; i < _myButtons.length; i++){
            if(_myButtons[i].getId() == clicked.getId()){
                if(debugMode)Log.i(debugTag,"my index = " +i);
                _index = i;
                _myGameField[_index] = tile;
                whoClicked.placeTile(_myButtons[_index]);
                Player returnValue = checkWinner(whoClicked);
                if(debugMode) Log.i(debugTag,"my game field: " + System.lineSeparator() +
                        _myGameField[0] + " " + _myGameField[1] + " " + _myGameField[2] + System.lineSeparator() +
                        _myGameField[3] + " " + _myGameField[4] + " " + _myGameField[5] + System.lineSeparator() +
                        _myGameField[6] + " " + _myGameField[7] + " " + _myGameField[8] + " "

                );

                //Have to update the display!
                //This should call some sort of win display method rather then handle winning right here.
                if(checkWinner(returnValue) !=null){
                    //Someone has won.
                    if(checkWinner(returnValue) instanceof AI){
                        AI winner = (AI) returnValue;
                    }
                    else{
                        Player winner = returnValue;
                    }

                }
                //^^

                break;
            }
        }
    }

    /**
     * Warning this method can return null objects.
     * All calls should be null checked.
     */
    private Player checkWinner(Player player){
        //What conditions win?
        // 0 1 2
        // 3 4 5
        // 6 7 8

        String tile;
        Player returnType;
        if(player instanceof AI){
            tile = _AITile;
            returnType = _AI;
        }
        else{
            tile = _PlayerTile;
            returnType = _player;
        }
        //else{
          //  throw new NullPointerException(
          //          "Instance of player is null."
          //  );
       // }


        //Check vert
        if(_myGameField[0].equals(tile) && _myGameField[3].equals(tile) && _myGameField[6].equals(tile)){
            return returnType;
        }
        else if(_myGameField[1].equals(tile) && _myGameField[4].equals(tile) && _myGameField[7].equals(tile)){
            return returnType;
        }
        else if(_myGameField[2].equals(tile) && _myGameField[5].equals(tile) && _myGameField[8].equals(tile)){
            return returnType;
        }
        //Check horz
        else if(_myGameField[0].equals(tile) && _myGameField[1].equals(tile) && _myGameField[2].equals(tile)){
            return returnType;
        }
        else if(_myGameField[3].equals(tile) && _myGameField[4].equals(tile) && _myGameField[5].equals(tile)){
            return returnType;
        }
        else if(_myGameField[6].equals(tile) && _myGameField[7].equals(tile) && _myGameField[8].equals(tile)){
            return returnType;
        }
        //Check dia
        else if(_myGameField[0].equals(tile) && _myGameField[4].equals(tile) && _myGameField[8].equals(tile)){
            return returnType;
        }
        else if(_myGameField[2].equals(tile) && _myGameField[5].equals(tile) && _myGameField[6].equals(tile)){
            return returnType;
        }
        else{
            return null;
        }

    }

}
