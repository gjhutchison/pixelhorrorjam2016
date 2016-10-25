package com.kowaisugoi.game.interactables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.interactables.objects.ItemId;

public interface Interactable {
    // TODO: In a perfect world, the interaction box should probably be private.
    public Rectangle getInteractionBox();

    public void draw(SpriteBatch batch);

    public void draw(ShapeRenderer renderer);

    public boolean click(float curX, float curY);

    public void beautifyCursor(float curX, float curY);

    public void update(float delta);

    public void registerListener(InteractionListener listener);

    public void setSoundEffect(SoundId soundId);

    public boolean isItemInteractable();

    public boolean itemInteract(ItemId id);

    public String getItemInteractionMessage(ItemId id);

    public void setItemInteractionMessage(ItemId id, String message);

    public boolean checkInteraction(float curX, float curY);
}
