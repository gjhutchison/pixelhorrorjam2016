package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kowaisugoi.game.interactables.scenic.Container;
import com.kowaisugoi.game.interactables.Interactable;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.kowaisugoi.game.screens.PlayGame.GAME_HEIGHT;
import static com.kowaisugoi.game.screens.PlayGame.GAME_WIDTH;

/**
 * Use this class as a class to build other rooms off of to avoid
 * code duplication
 */
public abstract class StandardRoom implements Room {

    protected Sprite _roomSprite;
    protected List<PickupableItem> _pickupableItemList = new LinkedList<PickupableItem>();
    protected List<Passage> _passageList = new LinkedList<Passage>();
    protected List<Describable> _describableList = new LinkedList<Describable>();
    protected Queue<String> _enterMessageQueue = new LinkedList<String>();
    protected List<Container> _containerList = new LinkedList<Container>();

    public StandardRoom(Sprite image) {
        _roomSprite = image;
        _roomSprite.setSize(GAME_WIDTH, GAME_HEIGHT);
    }

    public void enter() {
        // Think things, animate things, trigger things, etc.

        // Pop an item off the entry observation queue
        String textId = _enterMessageQueue.poll();
        if (textId != null) {
            PlayGame.getPlayer().think(Messages.getText(textId));
        }
    }

    public void addPassage(Passage interactable) {
        _passageList.add(interactable);
    }

    public void addPickupableItem(PickupableItem interactable) {
        _pickupableItemList.add(interactable);
    }

    public void addContainer(Container interactable) {
        _containerList.add(interactable);
    }

    public void addDescribable(Describable describable) {
        _describableList.add(describable);
    }

    public void setPassageList(List<Passage> passageList) {
        _passageList = passageList;
    }

    public void setDescriptionList(List<Describable> descriptionList) {
        _describableList = descriptionList;
    }

    public void setSprite(Sprite sprite) {
        _roomSprite = sprite;
    }

    @Override
    public void draw(SpriteBatch batch) {
        _roomSprite.draw(batch);
        for (Interactable interactable : _pickupableItemList) {
            interactable.draw(batch);
        }
        for (Interactable interactable : _containerList) {
            interactable.draw(batch);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        for (Passage passage : _passageList) {
            passage.draw(renderer);
        }
        for (Interactable interactable : _pickupableItemList) {
            interactable.draw(renderer);
        }
        for (Describable describable : _describableList) {
            describable.draw(renderer);
        }
        for (Container container : _containerList) {
            container.draw(renderer);
        }
    }

    @Override
    public void drawFx(SpriteBatch batch) {
    }

    @Override
    public void beautifyCursor(float curX, float curY) {
        for (Passage passage : _passageList) {
            passage.beautifyCursor(curX, curY);
        }
        for (PickupableItem pickupableItem : _pickupableItemList) {
            pickupableItem.beautifyCursor(curX, curY);
        }
        for (Describable describable : _describableList) {
            describable.beautifyCursor(curX, curY);
        }
        for (Container container : _containerList) {
            container.beautifyCursor(curX, curY);
        }
    }

    @Override
    public boolean click(float curX, float curY) {
        for (Interactable interactable : _passageList) {
            if (interactable.click(curX, curY)) {
                return true;
            }
        }

        for (Describable describable : _describableList) {
            if (describable.click(curX, curY)) {
                return true;
            }
        }

        for (PickupableItem pickupableItem : _pickupableItemList) {
            if (pickupableItem.click(curX, curY)) {
                PlayGame.getPlayer().getInventory().addItem(pickupableItem);
                _pickupableItemList.remove(pickupableItem);
                return true;
            }
        }

        for (Container container : _containerList) {
            if (container.getState() == 0) {
                return container.click(curX, curY);
            } else {
                if (container.click(curX, curY)) {
                    if (container.getPickupableItem() != null) {
                        PlayGame.getPlayer().getInventory().addItem(container.getPickupableItem());
                        container.setPickupableItem(null);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean click(float curX, float curY, ItemId itemId) {
        for (Interactable interactable : _passageList) {
            if (interactable.getInteractionBox().contains(curX, curY)) {
                if (interactable.isItemInteractable()) {
                    if (interactable.itemIteract(itemId)) {
                        return true;
                    }
                }
                if (!"".equals(interactable.getItemInteractionMessage(itemId))) {
                    PlayGame.getPlayer().think(interactable.getItemInteractionMessage(itemId));
                    return false;
                }
            }
        }
        for (Container container : _containerList) {
            if (container.getInteractionBox().contains(curX, curY)) {
                if (container.isItemInteractable()) {
                    if (container.itemIteract(itemId)) {
                        return true;
                    }
                }

                if (!"".equals(container.getItemInteractionMessage(itemId))) {
                    PlayGame.getPlayer().think(container.getItemInteractionMessage(itemId));
                    return false;
                }
            }
        }
        for (Describable describable : _describableList) {
            if (describable.getInteractionBox().contains(curX, curY)) {
                if (describable.isItemInteractable()) {
                    if (describable.itemIteract(itemId)) {
                        return true;
                    }
                }
                if (!"".equals(describable.getItemInteractionMessage(itemId))) {
                    PlayGame.getPlayer().think(describable.getItemInteractionMessage(itemId));
                    return false;
                }
            }
        }
        PlayGame.getPlayer().think(Messages.getText("interaction.invalid"));
        return false;
    }

    @Override
    public void update(float delta) {
        for (Interactable interactable : _passageList) {
            interactable.update(delta);
        }
    }

    @Override
    public void dispose() {
        _roomSprite.getTexture().dispose();
    }

    @Override
    public void pushEnterRemark(String textId) {
        _enterMessageQueue.add(textId);
    }

    @Override
    public void flagUpdate() {
    }
}
