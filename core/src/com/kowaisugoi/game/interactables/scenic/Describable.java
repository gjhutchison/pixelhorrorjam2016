package com.kowaisugoi.game.interactables.scenic;

import com.kowaisugoi.game.interactables.Interactable;

public interface Describable extends Interactable{

    public abstract void setDescription(String description);

    public abstract String getDescription();

}
