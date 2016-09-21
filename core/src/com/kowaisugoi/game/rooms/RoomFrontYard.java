package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.BlockedPassage;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil.Direction;

public class RoomFrontYard extends StandardRoom {

    private static final String ROOM_URL = "rooms/frontyard/frontyard_v2.png";

    private SnowAnimation _snowAnimation;

    public RoomFrontYard() {
        super(new Sprite(new Texture(ROOM_URL)));

        _snowAnimation = new SnowAnimation(50, 6);

        // TODO: Rooms should probably locally store their own id (associated refactoring required)
        Passage backToRoad = new DirectionalPassage(RoomId.FRONTYARD, RoomId.ROAD, new Rectangle(55, 0, 50, 10), Direction.DOWN);
        Passage frontDoor = new BlockedPassage(RoomId.FRONTYARD,
                RoomId.MAIN_HALL,
                new Rectangle(65, 35, 20, 40),
                Direction.UP, ItemId.KEY_HOUSE,
                Messages.getText("frontyard.door.interact.locked"),
                Messages.getText("frontyard.interaction.key.door"),
                SoundId.DOOR_LOCKED);

        frontDoor.setSoundEffect(SoundId.DOOR_CREAK);

        PickupableItem dankKey = new PickupableItem(new Sprite(new Texture("items/key.png")),
                new Rectangle(10, 10, 32, 32),
                ItemId.KEY_HOUSE);

        addPickupableItem(dankKey);

        addPassage(frontDoor);
        addPassage(backToRoad);
    }

    @Override
    public void update(float delta) {
        _snowAnimation.updateSnow(delta);
        super.update(delta);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        _snowAnimation.draw(renderer);
        super.draw(renderer);
    }
}
