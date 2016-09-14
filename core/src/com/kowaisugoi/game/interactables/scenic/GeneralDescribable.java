package com.kowaisugoi.game.interactables.scenic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.interactables.InteractionListener;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.screens.PlayGame;


public class GeneralDescribable implements Describable {

    private Rectangle _interactionBox;
    private String _description;

    private static final float HOLD_LENGTH = 2.0f;

    public GeneralDescribable(String description, Rectangle interactionBox) {
        _description = description;
        _interactionBox = interactionBox;
    }

    @Override
    public void setDescription(String description) {
        _description = description;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    @Override
    public Rectangle getInteractionBox() {
        return _interactionBox;
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    //Debug drawing
    @Override
    public void draw(ShapeRenderer renderer) {
        if (PlayGame.getDebug()) {
            renderer.setColor(0.5f, 0f, 0.5f, 0.25f);
            renderer.rect(_interactionBox.x, _interactionBox.y, _interactionBox.width, _interactionBox.height);
        }
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            PlayGame.getPlayer().think(_description, HOLD_LENGTH);
            return true;
        }
        return false;
    }

    @Override
    public void beautifyCursor(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            PlayGame.getPlayer().setCursor(Player.CursorType.EXAMINE);
        }
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void registerListener(InteractionListener listener) {
    }

    @Override
    public void setSoundEffect(SoundId soundId) {
    }

    @Override
    public boolean isItemInteractable() {
        return false;
    }

    @Override
    public boolean itemIteract(ItemId id) {
        return false;
    }
}
