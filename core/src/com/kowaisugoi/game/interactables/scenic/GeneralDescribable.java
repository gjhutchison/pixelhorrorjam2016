package com.kowaisugoi.game.interactables.scenic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.interactables.InteractionListener;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.screens.PlayGame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class GeneralDescribable implements Describable {

    private Rectangle _interactionBox;
    private String _description;
    private Queue<String> _descriptionList;
    private static final float HOLD_LENGTH = 2.0f;
    private Map<ItemId, String> _itemInteractionMessages;

    public GeneralDescribable(String description, Rectangle interactionBox) {
        _interactionBox = interactionBox;

        _descriptionList = new LinkedList<String>();
        _descriptionList.add(description);
        _description = description;

        _itemInteractionMessages = new HashMap<ItemId, String>();
    }

    public void addDescription(String description) {
        _descriptionList.add(description);
    }

    @Override
    public void removeAllDescriptions() {
        _descriptionList = new LinkedList<String>();
        _description = "";
    }

    @Override
    public void setDescription(String description) {
        _description = description;
        _descriptionList.clear();
        _descriptionList.add(description);
    }

    @Override
    public String getDescription() {
        if (_descriptionList.size() > 1) {
            String description = _descriptionList.poll();
            _descriptionList.add(description);
            return description;
        } else {
            return _description;
        }
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
            PlayGame.getPlayer().think(getDescription(), HOLD_LENGTH);
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
    public String getItemInteractionMessage(ItemId id) {
        if (_itemInteractionMessages.containsKey(id)) {
            return _itemInteractionMessages.get(id);
        }
        return "";
    }

    @Override
    public void setItemInteractionMessage(ItemId id, String message) {
        _itemInteractionMessages.put(id, message);
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
