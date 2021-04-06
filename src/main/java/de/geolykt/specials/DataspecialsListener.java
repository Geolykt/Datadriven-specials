package de.geolykt.specials;

import de.geolykt.starloader.api.event.EventHandler;
import de.geolykt.starloader.api.event.Listener;
import de.geolykt.starloader.api.event.lifecycle.ApplicationStartEvent;

/**
 * Since this can't be an anonymous class due to an error that I made within the starloader API,
 * I will have to make this insanely useless class
 */
public class DataspecialsListener implements Listener {

    private final Dataspecials ploogen;

    DataspecialsListener(Dataspecials ploogen) {
        this.ploogen = ploogen;
    }

    @EventHandler
    public void onInit(ApplicationStartEvent evt) {
        ploogen.start();
    }
}
