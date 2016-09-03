package com.kowaisugoi.game.interactables;

/**
 * Created by Owner on 9/3/2016.
 */
public interface InteractionListenerManager {
    public void pollNotifications();

    public void registerListener(InteractionListener listener);
}
