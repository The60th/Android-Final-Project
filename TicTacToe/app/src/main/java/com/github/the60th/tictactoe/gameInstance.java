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

    //Getters/Setters and variables.
    public void set_myGameBoolean(boolean[] _myGameBoolean) {
        this._myGameBoolean = _myGameBoolean;
    }

    //Used to track if a tile has been placed on.
    private boolean[] _myGameBoolean = {
            true, true, true,
            true, true, true,
            true, true, true
    };
    //Used to track whats in the game field.
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

    //Called when a user or the computer wishes to place a tile.
    public boolean placeTile(ImageButton clicked, Player whoClicked) {
        String tile;
        //Determine who called the function and set their tile to the correct instance.
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
        //For each button in the master list of buttons, compare it vs the button that was clicked
        //till we find which one that matches.
        for (int i = 0; i < _myButtons.length; i++) {
            if (_myButtons[i].getId() == clicked.getId()) {
                if (debugMode) Log.i(debugTag, "my index = " + i);
                _index = i;
                _myGameField[_index] = tile;
                //Once the tile has been found called the user instance of placeTile to place it.
                whoClicked.placeTile(_myButtons[_index]);
                _myGameBoolean[_index] = false;
                if (debugMode) Log.i(debugTag, "my game field: " + System.lineSeparator() +
                        _myGameField[0] + " " + _myGameField[1] + " " + _myGameField[2] + System.lineSeparator() +
                        _myGameField[3] + " " + _myGameField[4] + " " + _myGameField[5] + System.lineSeparator() +
                        _myGameField[6] + " " + _myGameField[7] + " " + _myGameField[8] + " "

                );
                //Check if anyone has won yet.
                //checkWinner can return null so it should always be safe checked.
                whoWins whoWon = checkWinner(whoClicked);
                if (whoWon != null) {
                    //Someone has won.
                    if (whoWon == whoWins.AI) {
                        //When the AI has won trigger the AI winning function.
                        if (debugMode) Log.i(debugTag, "AI has won!");
                        aiWins();
                        return false;
                    } else if (whoWon == whoWins.Player) {
                        //When the player has won trigger the player winning function.
                        if (debugMode) Log.i(debugTag, "Player has won!");
                        playerWins();
                        return false;
                    } else {
                        //Trigger the draw function
                        if (debugMode) Log.i(debugTag, "The game has come to a draw");
                        draw();
                        return false;
                    }

                } else {
                    //Check winner was null so no win states.
                }
                //^^
                //break out off the forloop now because the conditions have all been meet.
                break;
            }
        }
        //return true.
        return true;
    }

    //Enum to track the return types of checkWinner
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
        //See what the caller is an instance of and cast the check type to their type.
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

        //Here we will check each win condition on the board, and return the winning player if any is meet.

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
        //No winner was found so it must of been a draw if all tiles are full.
        else if (!(_myGameField[0].equals(_EmptyTile)) && !(_myGameField[1].equals(_EmptyTile)) && !(_myGameField[2].equals(_EmptyTile)) &&
                !(_myGameField[3].equals(_EmptyTile)) && !(_myGameField[4].equals(_EmptyTile)) && !(_myGameField[5].equals(_EmptyTile)) &&
                !(_myGameField[6].equals(_EmptyTile)) && !(_myGameField[7].equals(_EmptyTile)) && !(_myGameField[8].equals(_EmptyTile))) {
            return whoWins.Draw;
        } else {
            //No winner was found so return null.
            return null;
        }

    }

    //Call when the player wins to load the end game activity and pass some display data to it.
    private void playerWins() {
        Difficulty diffData = _AI.get_difficulty();
        Intent myIntent = new Intent(_Context, ActivityWinForm.class);
        String data = "The player has won!";
        myIntent.putExtra(difficultyDataTag,diffData);
        myIntent.putExtra(EXTRA_MESSAGE, data);
        _Context.startActivity(myIntent);

    }
    //Call when the computer wins to load the end game activity and pass some display data to it.

    private void aiWins() {
        Difficulty diffData = _AI.get_difficulty();

        Intent myIntent = new Intent(_Context, ActivityWinForm.class);
        String data = "The AI has won!";
        myIntent.putExtra(difficultyDataTag,diffData);
        myIntent.putExtra(EXTRA_MESSAGE, data);
        _Context.startActivity(myIntent);
    }
    //Call when a draw happens to load the end game activity and pass some display data to it.

    private void draw() {
        Difficulty diffData = _AI.get_difficulty();
        Intent myIntent = new Intent(_Context, ActivityWinForm.class);

        myIntent.putExtra(difficultyDataTag,diffData);
        String data = "The game was a draw.";
        myIntent.putExtra(EXTRA_MESSAGE, data);
        _Context.startActivity(myIntent);
    }

    //Used to check if its the first turn by making sure none of the tiles already have data saved to them.
    public boolean isFirstTurn() {
        for (int i = 0; i < _myGameField.length; i++) {
            if (!(_myGameField[i].equals(_EmptyTile))) return false;
        }
        return true;
    }

}
