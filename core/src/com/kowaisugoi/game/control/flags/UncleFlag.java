package com.kowaisugoi.game.control.flags;

import com.kowaisugoi.game.rooms.RoomBathroomPeek;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.rooms.RoomManager;

// TODO: Not sure if Flag is being used as intended
/**
 * Contains any special handling for spotting your uncle
 */
public class UncleFlag extends Flag {
    public UncleFlag(FlagId id) {
        super(id);
    }

    @Override
    public void setState(boolean value) {
        if (value) {
            RoomManager.getRoomFromId(RoomId.CAR).pushEnterRemark("car.enter.wannaleave");

            // TODO: Need an elegant way to update Describable descriptions
            ((RoomBathroomPeek)RoomManager.getRoomFromId(RoomId.BATHROOM)).swapUncleText();
        }
        super.setState(value);
    }
}

