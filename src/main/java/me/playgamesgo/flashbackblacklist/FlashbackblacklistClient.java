package me.playgamesgo.flashbackblacklist;

import net.fabricmc.api.ClientModInitializer;

public final class FlashbackblacklistClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();
    }
}
