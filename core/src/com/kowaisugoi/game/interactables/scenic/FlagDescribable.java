package com.kowaisugoi.game.interactables.scenic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.interactables.InteractionListener;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.screens.PlayGame;

/**
 * A describable which advances the plot.
 */
public class FlagDescribable extends GeneralDescribable {
    FlagId _flagId;

    public FlagDescribable(String description, Rectangle interactionBox, FlagId toSetTrue) {
        super(description, interactionBox);
        _flagId = toSetTrue;
    }

    @Override
    public boolean click(float curX, float curY) {
        if (super.click(curX, curY)) {
            PlayGame.getFlagManager().setFlag(_flagId, true);
            return true;
        }
        return false;
    }
}
