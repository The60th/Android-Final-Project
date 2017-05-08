package com.github.the60th.tictactoe;

import android.animation.ObjectAnimator;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import static com.github.the60th.tictactoe.ActivitySelectForm.difficultyDataTag;


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
    public static final String EXTRA_MESSAGE = "myDisplayData";
    public boolean debugMode = false;

    public static String debugTag = "<:My Debug Data:>";
    private int[] magicSquare = {8, 1, 6,
            3, 5, 7,
            4, 9, 2
    };

    public static String get_PlayerTile() {
        return _PlayerTile;
    }

    public static String get_AITile() {
        return _AITile;
    }
    public static String get_EmptyTile() {
        return _EmptyTile;
    }
    private static String _PlayerTile = "p";
    private static String _AITile = "a";
    private static String _EmptyTile = "e";
    private String _PlayerWinCondition = "ppp";
    private String _AiWinCondition = "aaa";
    private Player _player;
    private AI _AI;
    private ImageButton[] _myButtons;
    private Context _Context;

    public String[] get_myGameField() {
        return _myGameField;
    }

    public boolean[] get_myGameBoolean() {
        return _myGameBoolean;
    }

    public void set_myGameBoolean(boolean[] _myGameBoolean) {
        this._myGameBoolean = _myGameBoolean;
    }

    private boolean[] _myGameBoolean = {
            true, true, true,
            true, true, true,
            true, true, true
    };
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

    public ImageButton get_myButtons(int index) {
        return _myButtons[index];
    }

    public boolean is_DebugMode() {
        return debugMode;
    }

    public void set_DebugMode(boolean _DebugMode) {
        this.debugMode = _DebugMode;
    }

    //Class constructor
    public gameInstance(ImageButton[] myButtons, Player player, AI ai, Context context) {
        int _size = 9;
        if (myButtons.length != _size) {
            throw new IllegalArgumentException(
                    "You must create a game instance with an array of nine buttons."
            );
        }
        _myButtons = myButtons;
        _player = player;
        _AI = ai;
        _Context = context;
    }

    public boolean placeTile(ImageButton clicked, Player whoClicked) {
        String tile;
        if (whoClicked instanceof AI) {
            //Ai click methods.
            tile = _AITile;
        } else {
            //Player click method.
            tile = _PlayerTile;
        }
        //else{
        //    throw new NullPointerException(
        //          "Instance of whoClicked is null."
        //  );
        // }

        int _index;
        for (int i = 0; i < _myButtons.length; i++) {
            if (_myButtons[i].getId() == clicked.getId()) {
                if (debugMode) Log.i(debugTag, "my index = " + i);
                _index = i;
                _myGameField[_index] = tile;
                whoClicked.placeTile(_myButtons[_index]);
                _myGameBoolean[_index] = false;
                if (debugMode) Log.i(debugTag, "my game field: " + System.lineSeparator() +
                        _myGameField[0] + " " + _myGameField[1] + " " + _myGameField[2] + System.lineSeparator() +
                        _myGameField[3] + " " + _myGameField[4] + " " + _myGameField[5] + System.lineSeparator() +
                        _myGameField[6] + " " + _myGameField[7] + " " + _myGameField[8] + " "

                );

                //Have to update the display!
                //This should call some sort of win display method rather then handle winning right here.
                whoWins whoWon = checkWinner(whoClicked);
                if (whoWon != null) {
                    //Someone has won.
                    if (whoWon == whoWins.AI) {
                        if (debugMode) Log.i(debugTag, "AI has won!");
                        aiWins();
                        return false;
                    } else if (whoWon == whoWins.Player) {
                        if (debugMode) Log.i(debugTag, "Player has won!");
                        playerWins();
                        return false;
                    } else {
                        if (debugMode) Log.i(debugTag, "The game has come to a draw");
                        draw();
                        return false;
                    }

                } else {
                    //Check winner was null so error or draw.
                }
                //^^

                break;
            }
        }
        return true;
    }

    private enum whoWins {Player, AI, Draw}

    /**
     * Warning this method can return null objects.
     * All calls should be null checked.
     */
    private whoWins checkWinner(Player player) {
        //What conditions win?
        // 0 1 2
        // 3 4 5
        // 6 7 8
        if (debugMode)
            Log.i(debugTag, "Called within checkWinner: my game field: " + System.lineSeparator() +
                    _myGameField[0] + " " + _myGameField[1] + " " + _myGameField[2] + System.lineSeparator() +
                    _myGameField[3] + " " + _myGameField[4] + " " + _myGameField[5] + System.lineSeparator() +
                    _myGameField[6] + " " + _myGameField[7] + " " + _myGameField[8] + " "

            );
        String tile;
        whoWins returnType;
        if (player instanceof AI) {
            tile = _AITile;
            returnType = whoWins.AI;
        } else {
            tile = _PlayerTile;
            returnType = whoWins.Player;
        }
        //else{
        //  throw new NullPointerException(
        //          "Instance of player is null."
        //  );
        // }

        if (debugMode) Log.i(debugTag, "My tile data: " + tile);
        //Check vert
        if (_myGameField[0].equals(tile) && _myGameField[3].equals(tile) && _myGameField[6].equals(tile)) {
            return returnType;
        } else if (_myGameField[1].equals(tile) && _myGameField[4].equals(tile) && _myGameField[7].equals(tile)) {
            return returnType;
        } else if (_myGameField[2].equals(tile) && _myGameField[5].equals(tile) && _myGameField[8].equals(tile)) {
            return returnType;
        }
        //Check horz
        else if (_myGameField[0].equals(tile) && _myGameField[1].equals(tile) && _myGameField[2].equals(tile)) {
            return returnType;
        } else if (_myGameField[3].equals(tile) && _myGameField[4].equals(tile) && _myGameField[5].equals(tile)) {
            return returnType;
        } else if (_myGameField[6].equals(tile) && _myGameField[7].equals(tile) && _myGameField[8].equals(tile)) {
            return returnType;
        }
        //Check dia
        else if (_myGameField[0].equals(tile) && _myGameField[4].equals(tile) && _myGameField[8].equals(tile)) {
            return returnType;
        } else if (_myGameField[2].equals(tile) && _myGameField[4].equals(tile) && _myGameField[6].equals(tile)) {
            return returnType;
        }
        //draw
        else if (!(_myGameField[0].equals(_EmptyTile)) && !(_myGameField[1].equals(_EmptyTile)) && !(_myGameField[2].equals(_EmptyTile)) &&
                !(_myGameField[3].equals(_EmptyTile)) && !(_myGameField[4].equals(_EmptyTile)) && !(_myGameField[5].equals(_EmptyTile)) &&
                !(_myGameField[6].equals(_EmptyTile)) && !(_myGameField[7].equals(_EmptyTile)) && !(_myGameField[8].equals(_EmptyTile))) {
            return whoWins.Draw;
        } else {
            return null;
        }

    }

    private void playerWins() {
        Difficulty diffData = _AI.get_difficulty();
        Intent myIntent = new Intent(_Context, ActivityWinForm.class);
        String data = "The player has won!";
        myIntent.putExtra(difficultyDataTag,diffData);
        myIntent.putExtra(EXTRA_MESSAGE, data);
        _Context.startActivity(myIntent);

    }

    private void aiWins() {
        Difficulty diffData = _AI.get_difficulty();

        Intent myIntent = new Intent(_Context, ActivityWinForm.class);
        String data = "The AI has won!";
        myIntent.putExtra(difficultyDataTag,diffData);
        myIntent.putExtra(EXTRA_MESSAGE, data);
        _Context.startActivity(myIntent);
    }

    private void draw() {
        Difficulty diffData = _AI.get_difficulty();
        Intent myIntent = new Intent(_Context, ActivityWinForm.class);

        myIntent.putExtra(difficultyDataTag,diffData);
        String data = "The game was a draw.";
        myIntent.putExtra(EXTRA_MESSAGE, data);
        _Context.startActivity(myIntent);
    }

    public boolean isFirstTurn() {
        for (int i = 0; i < _myGameField.length; i++) {
            if (!(_myGameField[i].equals(_EmptyTile))) return false;
        }
        return true;
    }

}
