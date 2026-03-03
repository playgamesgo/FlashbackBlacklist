package me.playgamesgo.flashbackblacklist;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.network.chat.Component;

public final class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        Config.HANDLER.load();

        return parentScreen -> YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Flashback Blacklist"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("General"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Whitelist Mode"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.literal("If enabled, will allow recording on servers in the list instead of blocking them."))
                                        .build())
                                .binding(false, () -> Config.HANDLER.instance().whitelistMode, value -> {
                                    Config.HANDLER.instance().whitelistMode = value;
                                    Config.HANDLER.save();
                                })
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .group(ListOption.<String>createBuilder()
                                .name(Component.literal("Servers"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.literal("The list of servers to block or allow recording on, depending on the whitelist mode setting."))
                                        .build())
                                .binding(Config.HANDLER.instance().servers, () -> Config.HANDLER.instance().servers, value -> {
                                    Config.HANDLER.instance().servers = value;
                                    Config.HANDLER.save();
                                })
                                .initial(() -> "localhost")
                                .controller(StringControllerBuilder::create)
                                .build())
                        .build())
                .build().generateScreen(parentScreen);
    }
}