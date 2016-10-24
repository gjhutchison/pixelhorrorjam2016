package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.system.GameUtil;

public class RoomForestPath extends StandardRoom {

    private static final String ROOM_URL = "rooms/path/path.png";
    //private boolean _firstTime = true; //S-s-senpai (≧◡≦) ♡

    private SnowAnimation _snowAnimation;

    public RoomForestPath() {
        super(new Sprite(new Texture(ROOM_URL)));

        _snowAnimation = new SnowAnimation(50, 6);

        Describable treesDescription = new GeneralDescribable(
                Messages.getText("forestpath.trees.description1_1"),
                new Rectangle(0, 10, 25, 65));
        treesDescription.addDescription(Messages.getText("forestpath.trees.description1_2"));
        treesDescription.addDescription(Messages.getText("forestpath.trees.description1_3"));

        Describable treesDescription2 = new GeneralDescribable(
                Messages.getText("forestpath.trees.description2_1"),
                new Rectangle(110, 10, 25, 65));
        treesDescription2.addDescription(Messages.getText("forestpath.trees.description2_2"));
        treesDescription2.addDescription(Messages.getText("forestpath.trees.description2_3"));

        treesDescription.setItemInteractionMessage(ItemId.STICK, Messages.getText("forestpath.interaction.stick.trees"));
        treesDescription2.setItemInteractionMessage(ItemId.STICK, Messages.getText("forestpath.interaction.stick.trees"));

        Passage forward = new DirectionalPassage(RoomId.ROAD, RoomId.FRONTYARD, new Rectangle(60, 20, 30, 60), GameUtil.Direction.UP);
        Passage backward = new DirectionalPassage(RoomId.ROAD, RoomId.PARKING_AREA, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);
        forward.setSoundEffect(SoundId.SNOW_CRUNCH);
        backward.setSoundEffect(SoundId.SNOW_CRUNCH);

        addPassage(forward);
        addPassage(backward);

        addDescribable(treesDescription);
        addDescribable(treesDescription2);

        pushEnterRemark("forestpath.enter.thought");
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
