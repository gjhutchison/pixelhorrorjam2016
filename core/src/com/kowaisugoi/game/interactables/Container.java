package com.kowaisugoi.game.interactables;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.screens.PlayGame;

public class Container implements Interactable {

    private Rectangle _interactionBox;
    private Sprite[] _sprites;
    private int _state;
    private boolean _unlocked = true;
    private boolean _itemInteractable = false;

    private PickupableItem _item;

    private ItemId _keyId;
    private SoundId _soundEffect;

    private String _thoughts[];

    public Container(Sprite state1, Sprite state2, Sprite state3, Rectangle interactionBox) {
        _sprites = new Sprite[3];
        _thoughts = new String[3];
        _sprites[0] = state1;
        _sprites[1] = state2;
        _sprites[2] = state3;
        _interactionBox = interactionBox;
        for (Sprite sprite : _sprites) {
            if (sprite != null) {
                sprite.setPosition(_interactionBox.getX(), _interactionBox.getY());
            }
        }
        _state = 0;
    }

    public void setThoughts(String thought1, String thought2, String thought3) {
        _thoughts[0] = thought1;
        _thoughts[1] = thought2;
        _thoughts[2] = thought3;
    }


    @Override
    public Rectangle getInteractionBox() {
        return _interactionBox;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (_sprites[_state] != null) {
            _sprites[_state].draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (PlayGame.getDebug()) {
            renderer.setColor(0, 0, 1, 0.25f);
            renderer.rect(_interactionBox.x,
                    _interactionBox.y,
                    _interactionBox.width,
                    _interactionBox.height);
        }
    }

    @Override
    public boolean click(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            PlayGame.getPlayer().think(_thoughts[_state]);
            if (_unlocked) {
                if (_state < 2) {
                    _state++;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void beautifyCursor(float curX, float curY) {
        if (_interactionBox.contains(curX, curY)) {
            if (_state < 2) {
                PlayGame.getPlayer().setCursor(Player.CursorType.PICKUP);
            } else {
                PlayGame.getPlayer().setCursor(Player.CursorType.EXAMINE);
            }
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
        _soundEffect = soundId;
    }

    @Override
    public boolean isItemInteractable() {
        return _itemInteractable;
    }

    @Override
    public boolean itemIteract(ItemId id) {
        if (_keyId == id) {
            _unlocked = true;
            return true;
        }
        return false;
    }

    public void setUnlockableItem(ItemId id) {
        _keyId = id;
        _unlocked = false;
        _itemInteractable = true;
    }

    public void setPickupableItem(PickupableItem item) {
        _item = item;
    }

    public PickupableItem getPickupableItem() {
        return _item;
    }

    public int getState() {
        return _state;
    }
}
