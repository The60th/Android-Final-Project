package com.github.the60th.tictactoe;

import android.widget.ImageButton;

/**
 * Created by Justin on 5/2/2017.
 * All code handled by Justin.
 */

public class Player {
    private String _Name;



    private PieceType _PieceType;
    private boolean _MyTurn;
public Player(String name, PieceType type,boolean myTurn){
    _Name = name;
    _PieceType = type;
    _MyTurn = myTurn;
}
    public void placeTile(ImageButton tile){
        if(_PieceType == PieceType.X){
            tile.setImageResource(R.drawable.largeximage);
        }
        else if(_PieceType == PieceType.O){
            tile.setImageResource(R.drawable.largeoimage);
        }
        else{
           //?
        }
    }



    public String get_Name() {
        return _Name;
    }

    public boolean is_MyTurn() {
        return _MyTurn;
    }

    public void set_MyTurn(boolean _MyTurn) {
        this._MyTurn = _MyTurn;
    }
    public PieceType get_PieceType() {
        return _PieceType;
    }
}
