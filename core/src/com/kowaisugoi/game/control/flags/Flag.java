package com.kowaisugoi.game.control.flags;

public class Flag {

    FlagId _id;
    boolean _state;

    public Flag(FlagId id) {
        _id = id;
        _state = false;
    }

    public void toggleState() {
        _state = !_state;
    }

    public void setState(boolean state) {
        _state = state;
    }

    public boolean getState() {
        return _state;
    }

    public FlagId getFlagId() {
        return _id;
    }
}
