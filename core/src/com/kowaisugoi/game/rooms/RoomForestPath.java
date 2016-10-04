package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

public class RoomForestPath extends StandardRoom {

    private static final String ROOM_URL = "rooms/path/path.png";
    //private boolean _firstTime = true; //S-s-senpai (≧◡≦) ♡

    private SnowAnimation _snowAnimation;

    public RoomForestPath() {
        super(new Sprite(new Texture(ROOM_URL)));

        _snowAnimation = new SnowAnimation(50, 6);

        Describable treesDescription = new GeneralDescribable(
                Messages.getText("forestpath.trees.description"),
                new Rectangle(0, 10, 25, 65));
        Describable treesDescription2 = new GeneralDescribable(
                Messages.getText("forestpath.trees.description2"),
                new Rectangle(110, 10, 25, 65));

        Passage forward = new DirectionalPassage(RoomId.ROAD, RoomId.FRONTYARD, new Rectangle(60, 20, 30, 60), GameUtil.Direction.UP);
        Passage backward = new DirectionalPassage(RoomId.ROAD, RoomId.PARKING_AREA, new Rectangle(55, 0, 50, 10), GameUtil.Direction.DOWN);

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
