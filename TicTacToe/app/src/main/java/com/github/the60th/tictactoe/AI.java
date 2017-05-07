package com.github.the60th.tictactoe;


import android.util.Log;

import java.util.HashMap;
import java.util.Random;


import static com.github.the60th.tictactoe.gameInstance.debugTag;
import static com.github.the60th.tictactoe.gameInstance.get_AITile;
import static com.github.the60th.tictactoe.gameInstance.get_EmptyTile;
import static com.github.the60th.tictactoe.gameInstance.get_PlayerTile;

/**
 * Created by Justin on 5/2/2017.
 * All code handled by Justin.
 */

public class AI extends Player {
    private final int[] DEFAULT_FIELD_VALUES = {25, 10, 25,
            10, 25, 10,
            25, 10, 25};
    private Difficulty _difficulty;

    public AI(String name, PieceType type, boolean myTurn, Difficulty difficulty) {
        super(name, type, myTurn);
        this._difficulty = difficulty;
    }

    public boolean playTurn(gameInstance gameInstance) { //Return true if the AI was able to finish its turn.
        switch (_difficulty) {
            case Easy:
                return easyMode(gameInstance);
            case Medium:
                return mediumMode(gameInstance);
            case Hard:
                return hardMode(gameInstance);
            case Super_Hard:
                return superhardMode(gameInstance);
            default:
                return false;
        }
    }

    public Difficulty get_difficulty() {
        return _difficulty;
    }

    private boolean easyMode(gameInstance gameInstance) {
        if (is_MyTurn()) {
            if (gameInstance.is_DebugMode())
                Log.i(debugTag, "Called within AiClass: -- easy mode: " + System.lineSeparator() +
                        gameInstance.get_myGameField()[0] + " " + gameInstance.get_myGameField()[1] + " " + gameInstance.get_myGameField()[2] + System.lineSeparator() +
                        gameInstance.get_myGameField()[3] + " " + gameInstance.get_myGameField()[4] + " " + gameInstance.get_myGameField()[5] + System.lineSeparator() +
                        gameInstance.get_myGameField()[6] + " " + gameInstance.get_myGameField()[7] + " " + gameInstance.get_myGameField()[8] + " "

                );
            Random random = new Random();
            int x = random.nextInt(9);
            //This loop never ends if a tile can not be placed!
            //MUST have a force exit condition!
            int tracker = 0;
            while (!(gameInstance.get_myGameField()[x].equals("e")) && tracker < 100) {
                x = random.nextInt(9);
                tracker++;
            }
            if (tracker >= 100) {
                for (int i = 0; i < 9; i++) {
                    if ((gameInstance.get_myGameField()[i].equals("e"))) {
                        if (!(gameInstance.placeTile(gameInstance.get_myButtons(x), this)))
                            return false;
                        return true;
                    }
                }
                Log.i(debugTag, "Invalid call on AI board is full, catching errors and returned.");
                return false;
            }
            gameInstance.placeTile(gameInstance.get_myButtons(x), this);
        } else {
            Log.i(debugTag, "Invalid call on AI not currently its turn.");
            return false;
        }
        return true;
    }

    // 0 1 2
    // 3 4 5
    // 6 7 8
    private boolean mediumMode(gameInstance gameInstance) {
        //Handle the first turn here.
        if (gameInstance.isFirstTurn()) {
            Random random = new Random();
            int x = random.nextInt(4);
            if (x >= 3) {
                gameInstance.placeTile(gameInstance.get_myButtons(4), this);
                return true;
                //Middle
            } else {
                x = random.nextInt(4);
                switch (x) {
                    case 0:
                        gameInstance.placeTile(gameInstance.get_myButtons(0), this);
                        return true;
                    case 1:
                        gameInstance.placeTile(gameInstance.get_myButtons(2), this);
                        return true;
                    case 2:
                        gameInstance.placeTile(gameInstance.get_myButtons(6), this);
                        return true;
                    case 3:
                        gameInstance.placeTile(gameInstance.get_myButtons(8), this);
                        return true;
                }
                //Corners
            }
            // Values map.
            // 0 1 2 --> E E E --> 25 10 25
            // 3 4 5 --> E E E --> 10 25 10
            // 6 7 8 --> E E E --> 25 10 25

            //X plays:
            //Place a x in the top left.
            //Add 20 points to each piece next to it for next placement.
            // 0 1 2 --> X E E --> 25 30 25
            // 3 4 5 --> E E E --> 30 55 10
            // 6 7 8 --> E E E --> 25 10 25

            // O plays:
            // When its only one piece ignore it for values.
            // 0 1 2 --> X O E --> 25 30 25
            // 3 4 5 --> E E E --> 30 55 10
            // 6 7 8 --> E E E --> 25 10 25

            // X plays:
            //Place in the middle because greatest value.
            //Update values again adding another 20 to each nearby piece
            //Add 100 value to any piece that makes it three in a row
            // 0 1 2 --> X O E --> _X _O 55
            // 3 4 5 --> E X E --> 50 _X 30
            // 6 7 8 --> E E E --> 55 30 155

            //O plays:
            // Blocks the bottom right preventing a win.
            // 0 1 2 --> X O E --> _X _O 55
            // 3 4 5 --> E X E --> 50 _X 30
            // 6 7 8 --> E E O --> 55 30 _O

            //Note:
            // This is where logic for this level and other levels will break in to a diffrent path.
            // Correct play here can only be seen by looking ahead a turn.

            // X plays:
            // No way for O to win so ignore that in point calculation.
            // if two spots are worth the same randomly pick from them.

            // 0 1 2 --> X O E --> _X _O 55
            // 3 4 5 --> E X E --> 50 _X 30
            // 6 7 8 --> X E O --> _X 30 _O

            // O Plays:
            // placing in the top right.

            // 0 1 2 --> X O O --> _X _O _O
            // 3 4 5 --> E X E --> 50 _X 30
            // 6 7 8 --> X E O --> _X 30 _O

            // X Plays:
            // Add 20 to each because of a new X next to the tile.
            // Now both X and O have a win condition to handle this add 250 the tile that O could win off of.
            // But before placing there check again and add 1000 to any tile that X can win with.
            // Place in the tile with the greatest amount.

            // 0 1 2 --> X O O --> _X _O _O
            // 3 4 5 --> E X E --> 1070 _X 280
            // 6 7 8 --> X E O --> _X 1050 _O

            //X has won:

            // 0 1 2 --> X O O --> _X _O _O
            // 3 4 5 --> X X E --> _X _X 280
            // 6 7 8 --> X E O --> _X 1050 _O

        } else { //Handle other turns here.
            int[] fieldValues = transformGameValues(gameInstance.get_myGameField());
            int index = -1;
            int value = 0;
            Log.i(debugTag, "Called within AiClass: -- med mode -- pre value calc: " + System.lineSeparator() +
                    fieldValues[0] + " " + fieldValues[1] + " " + fieldValues[2] + System.lineSeparator() +
                    fieldValues[3] + " " + fieldValues[4] + " " + fieldValues[5] + System.lineSeparator() +
                    fieldValues[6] + " " + fieldValues[7] + " " + fieldValues[8] + " ");

            //Relationships:
            // 0 = 1/3/4
            // 1= 0/4/2
            //2 = 1/5
            //3 = 0/4/6
            //4 = all
            //5 = 2/4/8
            //6 = 3/4/7
            //7 = 4/6/8
            //8 = 4/5/7
            //need to update values.
            // 0 1 2
            // 3 4 5
            // 6 7 8
            for (int i = 0; i < fieldValues.length; i++) {
                Log.i(debugTag, "FV[0] = " + fieldValues[0] + " at " + i);
                int _Lvalue;
                switch (i) {
                    case 0:
                        _Lvalue = fieldValues[0];
                        if (fieldValues[1] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }

                        if (fieldValues[3] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[0] == -1 || fieldValues[0] == -2) {
                            fieldValues[0] = fieldValues[0];
                        } else fieldValues[0] = _Lvalue;
                        break;
                    case 1:
                        _Lvalue = fieldValues[1];
                        if (fieldValues[0] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }

                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[7] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[2] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[1] == -1 || fieldValues[1] == -2) {
                            fieldValues[1] = fieldValues[1];
                        } else fieldValues[1] = _Lvalue;

                        break;
                    case 2:
                        _Lvalue = fieldValues[2];
                        if (fieldValues[1] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[5] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[2] == -1 || fieldValues[2] == -2) {
                            fieldValues[2] = fieldValues[2];
                        } else fieldValues[2] = _Lvalue;
                        break;
                    case 3:
                        _Lvalue = fieldValues[3];
                        if (fieldValues[0] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }

                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[5] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[6] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[3] == -1 || fieldValues[3] == -2) {
                            fieldValues[3] = fieldValues[3];
                        } else fieldValues[3] = _Lvalue;
                        break;
                    case 4:
                        _Lvalue = fieldValues[4];
                        if (fieldValues[0] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[1] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[7] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[2] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[3] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[5] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[5] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[3] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[6] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[7] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[1] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[8] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //still needed
                        if (fieldValues[4] == -1 || fieldValues[4] == -2) {
                            fieldValues[4] = fieldValues[4];
                        } else fieldValues[4] = _Lvalue;
                        break;
                    case 5:
                        _Lvalue = fieldValues[5];
                        if (fieldValues[2] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }

                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[3] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[8] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[5] == -1 || fieldValues[5] == -2) {
                            fieldValues[5] = fieldValues[5];
                        } else fieldValues[5] = _Lvalue;
                        break;
                    case 6:
                        _Lvalue = fieldValues[6];
                        if (fieldValues[3] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[7] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[6] == -1 || fieldValues[6] == -2) {
                            fieldValues[6] = fieldValues[6];
                        } else fieldValues[6] = _Lvalue;
                        break;
                    case 7:
                        _Lvalue = fieldValues[7];
                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[1] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[6] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[8] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[7] == -1 || fieldValues[7] == -2) {
                            fieldValues[7] = fieldValues[7];
                        } else fieldValues[7] = _Lvalue;
                        break;
                    case 8:
                        _Lvalue = fieldValues[8];
                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[5] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[7] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        if (fieldValues[8] == -1 || fieldValues[8] == -2) {
                            fieldValues[8] = fieldValues[8];
                        } else fieldValues[8] = _Lvalue;
                        break;
                    default:
                        Log.i(debugTag, "Invalid calls in AI medium switch case.");
                }

            }
            Log.i(debugTag, "Called within AiClass: -- med mode -- post value calc: " + System.lineSeparator() +
                    fieldValues[0] + " " + fieldValues[1] + " " + fieldValues[2] + System.lineSeparator() +
                    fieldValues[3] + " " + fieldValues[4] + " " + fieldValues[5] + System.lineSeparator() +
                    fieldValues[6] + " " + fieldValues[7] + " " + fieldValues[8] + " ");
            int[] subIndexies = {-1, -1, -1,
                    -1, -1, -1,
                    -1, -1, -1};
            for (int i = 0; i < fieldValues.length; i++) {
                //pick which values to use.
                if (fieldValues[i] > 0 && gameInstance.get_myGameField()[i].equals(get_EmptyTile())) {
                    if (fieldValues[i] > value) {
                        value = fieldValues[i];
                        index = i;
                    } else if (fieldValues[i] == value) {
                        subIndexies[i] = i;
                    }

                }
            }
            int maxVal = 0;
            for (int i = 0; i < subIndexies.length; i++) {
                if (subIndexies[i] > maxVal) maxVal = subIndexies[i];
            }
            if (maxVal >= value) {
                int val = 0;
                for (int i = 0; i < subIndexies.length; i++) {
                    if (subIndexies[i] > -1) {
                        val = val + 1;
                    }
                }
                if (val > 0) {
                    Random rand = new Random();
                    int newIndex = subIndexies[rand.nextInt(9)];
                    while (newIndex == -1) {
                        newIndex = subIndexies[rand.nextInt(9)];
                    }
                    Log.i(debugTag, "Randomly picking because of even point scores.");
                    return gameInstance.placeTile(gameInstance.get_myButtons(newIndex), this);
                }
            }
            if (index > -1) return gameInstance.placeTile(gameInstance.get_myButtons(index), this);
            else {
                Log.i(debugTag, "Failed to find a greater value calling easy AI");
                return (easyMode(gameInstance));
            }

        }

        return false;
    }

    private boolean hardMode(gameInstance gameInstance) {
        if (gameInstance.isFirstTurn()) {
            Random random = new Random();
            int x = random.nextInt(4);
            if (x >= 3) {
                gameInstance.placeTile(gameInstance.get_myButtons(4), this);
                return true;
                //Middle
            } else {
                x = random.nextInt(4);
                switch (x) {
                    case 0:
                        gameInstance.placeTile(gameInstance.get_myButtons(0), this);
                        return true;
                    case 1:
                        gameInstance.placeTile(gameInstance.get_myButtons(2), this);
                        return true;
                    case 2:
                        gameInstance.placeTile(gameInstance.get_myButtons(6), this);
                        return true;
                    case 3:
                        gameInstance.placeTile(gameInstance.get_myButtons(8), this);
                        return true;
                }
                //Corners
            }
            // Values map.
            // 0 1 2 --> E E E --> 25 10 25
            // 3 4 5 --> E E E --> 10 25 10
            // 6 7 8 --> E E E --> 25 10 25

        } else {
            int[] fieldValues = transformGameValues(gameInstance.get_myGameField());
            int index = -1;
            int value = 0;
            Log.i(debugTag, "Called within AiClass: -- hard mode -- pre value calc: " + System.lineSeparator() +
                    fieldValues[0] + " " + fieldValues[1] + " " + fieldValues[2] + System.lineSeparator() +
                    fieldValues[3] + " " + fieldValues[4] + " " + fieldValues[5] + System.lineSeparator() +
                    fieldValues[6] + " " + fieldValues[7] + " " + fieldValues[8] + " ");
            int _Lvalue;
            for (int i = 0; i < fieldValues.length; i++) {
                switch (i) {
                    case 0:
                        _Lvalue = fieldValues[0];
                        //Check the values of 1 and 2 to get modifiers
                        //add a else if afterwards to check for -2.
                        if (fieldValues[1] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[1] == -2) {
                            if (fieldValues[2] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }
                        //Check the values of 3 and 6 to get modifiers
                        if (fieldValues[3] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        } else if (fieldValues[3] == -2) {
                            if (fieldValues[6] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }
                        //Check the values of 4 and 8 to get modifiers
                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        } else if (fieldValues[4] == -2) {
                            if (fieldValues[8] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.
                        if (fieldValues[0] == -1 || fieldValues[0] == -2) {
                            fieldValues[0] = fieldValues[0];
                        } else fieldValues[0] = _Lvalue;
                        break;
                    case 1:
                        _Lvalue = fieldValues[1];
                        if (fieldValues[0] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[0] == -2) {
                            if (fieldValues[2] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[7] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[4] == -2) {
                            if (fieldValues[7] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[2] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[2] == -2) {
                            if (fieldValues[0] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.
                        if (fieldValues[1] == -1 || fieldValues[1] == -2) {
                            fieldValues[1] = fieldValues[1];
                        } else fieldValues[1] = _Lvalue;

                        break;
                    case 2:
                        _Lvalue = fieldValues[2];
                        if (fieldValues[1] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[1] == -2) {
                            if (fieldValues[0] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[4] == -2) {
                            if (fieldValues[6] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[5] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[5] == -2) {
                            if (fieldValues[8] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.
                        if (fieldValues[2] == -1 || fieldValues[2] == -2) {
                            fieldValues[2] = fieldValues[2];
                        } else fieldValues[2] = _Lvalue;
                        break;
                    case 3:
                        _Lvalue = fieldValues[3];
                        if (fieldValues[0] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[0] == -2) {
                            if (fieldValues[6] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[5] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[4] == -2) {
                            if (fieldValues[5] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[6] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[6] == -2) {
                            if (fieldValues[0] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.
                        if (fieldValues[3] == -1 || fieldValues[3] == -2) {
                            fieldValues[3] = fieldValues[3];
                        } else fieldValues[3] = _Lvalue;
                        break;
                    case 4:
                        _Lvalue = fieldValues[4];
                        if (fieldValues[0] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[0] == -2) {
                            if (fieldValues[8] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[1] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[7] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[1] == -2) {
                            if (fieldValues[7] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[2] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[2] == -2) {
                            if (fieldValues[6] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[3] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[5] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[3] == -2) {
                            if (fieldValues[5] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[5] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[3] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[5] == -2) {
                            if (fieldValues[3] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[6] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[6] == -2) {
                            if (fieldValues[2] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[7] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[1] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[7] == -2) {
                            if (fieldValues[1] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[8] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[8] == -2) {
                            if (fieldValues[0] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.
                        if (fieldValues[4] == -1 || fieldValues[4] == -2) {
                            fieldValues[4] = fieldValues[4];
                        } else fieldValues[4] = _Lvalue;
                        break;
                    case 5:
                        _Lvalue = fieldValues[5];
                        if (fieldValues[2] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[2] == -2) {
                            if (fieldValues[8] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[3] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[4] == -2) {
                            if (fieldValues[3] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[8] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[8] == -2) {
                            if (fieldValues[2] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.
                        if (fieldValues[5] == -1 || fieldValues[5] == -2) {
                            fieldValues[5] = fieldValues[5];
                        } else fieldValues[5] = _Lvalue;
                        break;
                    case 6:
                        _Lvalue = fieldValues[6];
                        if (fieldValues[3] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[3] == -2) {
                            if (fieldValues[0] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[4] == -2) {
                            if (fieldValues[2] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[7] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[7] == -2) {
                            if (fieldValues[8] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.
                        if (fieldValues[6] == -1 || fieldValues[6] == -2) {
                            fieldValues[6] = fieldValues[6];
                        } else fieldValues[6] = _Lvalue;
                        break;
                    case 7:
                        _Lvalue = fieldValues[7];
                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[1] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[4] == -2) {
                            if (fieldValues[1] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[6] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[8] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[6] == -2) {
                            if (fieldValues[8] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[8] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[8] == -2) {
                            if (fieldValues[6] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.
                        if (fieldValues[7] == -1 || fieldValues[7] == -2) {
                            fieldValues[7] = fieldValues[7];
                        } else fieldValues[7] = _Lvalue;
                        break;
                    case 8:
                        _Lvalue = fieldValues[8];
                        if (fieldValues[4] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[0] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[4] == -2) {
                            if (fieldValues[0] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[5] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[2] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[5] == -2) {
                            if (fieldValues[2] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        if (fieldValues[7] == -1) {
                            _Lvalue = _Lvalue + 20;
                            if (fieldValues[6] == -1) {
                                _Lvalue = _Lvalue + 1000;
                            }
                        }
                        //Check if this is a winning spot next turn for the player.
                        //If so add 500, so it will be picked over anything but a winning play.
                        else if (fieldValues[7] == -2) {
                            if (fieldValues[6] == -2) {
                                _Lvalue = _Lvalue + 500;
                            }
                        }

                        //Check if the spot is not already taken.

                        if (fieldValues[8] == -1 || fieldValues[8] == -2) {
                            fieldValues[8] = fieldValues[8];
                        } else fieldValues[8] = _Lvalue;
                        break;
                    default:
                        Log.i(debugTag, "Invalid calls in AI medium switch case.");

                }
            }
            Log.i(debugTag, "Called within AiClass: -- hard mode -- post value calc: " + System.lineSeparator() +
                    fieldValues[0] + " " + fieldValues[1] + " " + fieldValues[2] + System.lineSeparator() +
                    fieldValues[3] + " " + fieldValues[4] + " " + fieldValues[5] + System.lineSeparator() +
                    fieldValues[6] + " " + fieldValues[7] + " " + fieldValues[8] + " ");
            int[] subIndexies = {-1, -1, -1,
                    -1, -1, -1,
                    -1, -1, -1};
            for (int i = 0; i < fieldValues.length; i++) {
                //pick which values to use.
                if (fieldValues[i] > 0 && gameInstance.get_myGameField()[i].equals(get_EmptyTile())) {
                    if (fieldValues[i] > value) {
                        value = fieldValues[i];
                        index = i;
                    } else if (fieldValues[i] == value) {
                        subIndexies[i] = i;
                    }

                }
            }
            int maxVal = 0;
            for (int i = 0; i < subIndexies.length; i++) {
                if (subIndexies[i] > maxVal) maxVal = subIndexies[i];
            }
            if (maxVal >= value) {
                int val = 0;
                for (int i = 0; i < subIndexies.length; i++) {
                    if (subIndexies[i] > -1) {
                        val = val + 1;
                    }
                }
                if (val > 0) {
                    Random rand = new Random();
                    int newIndex = subIndexies[rand.nextInt(9)];
                    while (newIndex == -1) {
                        newIndex = subIndexies[rand.nextInt(9)];
                    }
                    Log.i(debugTag, "Randomly picking because of even point scores.");
                    return gameInstance.placeTile(gameInstance.get_myButtons(newIndex), this);
                }
            }
            if (index > -1) return gameInstance.placeTile(gameInstance.get_myButtons(index), this);
            else {
                Log.i(debugTag, "Failed to find a greater value in hard AI calling medium AI");
                return (mediumMode(gameInstance));
            }
        }
        return false;
    }

    private boolean superhardMode(gameInstance gameInstance) {

        //Relationships:
        // 0 = 1/3/4
        // 1= 0/4/2
        //2 = 1/5
        //3 = 0/4/6
        //4 = all
        //5 = 2/4/8
        //6 = 3/4/7
        //7 = 4/6/8
        //8 = 4/5/7
        //need to update values.
        // 0 1 2
        // 3 4 5
        // 6 7 8


        return hardMode(gameInstance);
    }

    //Relationships:
    // 0 = 1/3/4
    // 1= 0/4/2
    //2 = 1/5
    //3 = 0/4/6
    //4 = all
    //5 = 2/4/8
    //6 = 3/4/7
    //7 = 4/6/8
    //8 = 4/5/7

    /**
     * A value of -1 is Ai tile.
     * A value of -2 is a player tile.
     */
    private int[] transformGameValues(String[] field) {
        int[] values = new int[9];
        //Create values array.
        for (int i = 0; i < field.length; i++) {
            if (field[i].equals(get_AITile())) {
                values[i] = -1;
            } else if (field[i].equals(get_PlayerTile())) {
                values[i] = -2;
            } else {
                values[i] = DEFAULT_FIELD_VALUES[i];
            }
        }
        return values;
    }
}
