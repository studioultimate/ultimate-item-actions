package com.gabriaum.ultimate.itemactions.menu;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.infra.util.ItemBuilder;
import com.gabriaum.ultimate.itemactions.service.ItemActionsUpdateParamService;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.component.Pagination;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.state.State;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class UltimateItemActionsEditActionsMenu extends View {
    private final ItemActionsUpdateParamService updateParamService = new ItemActionsUpdateParamService();

    private final State<Pagination> paginationState = lazyPaginationState(
            context -> {
                UltimateItemActions ultimateItemActions = (UltimateItemActions) context.getInitialData();
                if (ultimateItemActions == null)
                    return new ArrayList<>();

                return ultimateItemActions.getActions();
            },
            (context, builder, index, lore) -> {
                UltimateItemActions ultimateItemActions = (UltimateItemActions) context.getInitialData();
                if (ultimateItemActions == null) return;

                builder.onRender(render -> render.setItem(
                                new ItemBuilder(Material.PAPER)
                                        .setName("§a#" + index)
                                        .addLore(
                                                Arrays.asList(
                                                        "",
                                                        "§e§l● §f" + lore,
                                                        "",
                                                        "§6§lCLICK TO REMOVE"
                                                )
                                        )
                                        .getStack()
                        ))
                        .onClick(event -> {
                            updateParamService.removeAction(
                                    ultimateItemActions,
                                    index - 1
                            );

                            event.openForPlayer(
                                    UltimateItemActionsEditActionsMenu.class,
                                    ultimateItemActions
                            );
                        });
            }
    );

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Ultimate Item Actions Editor");
        config.size(6);
        config.layout(
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "         ",
                "<   A   >"
        );

        config.cancelInteractions();
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        handlePaginationItems(render);

        Player player = render.getPlayer();
        UltimateItemActions ultimateItemActions = (UltimateItemActions) render.getInitialData();
        if (ultimateItemActions == null) return;

        render.layoutSlot(
                        'A',
                        new ItemBuilder(Material.SKULL_ITEM, 1, 3)
                                .setSkull("https://textures.minecraft.net/texture/d0b1dac4f7f9ab7ca09662dda0bc76b64637b892ad807e798a17e4a04cdec64b")
                                .setName("§aAdd new action")
                                .getStack()
                )
                .onClick(event -> {
                    giveMetadataTo(player, ultimateItemActions.getKey());
                    sendMessage(player);
                    render.closeForPlayer();
                });
    }

    private void handlePaginationItems(RenderContext render) {
        Player player = render.getPlayer();
        UltimateItemActions ultimateItemActions = (UltimateItemActions) render.getInitialData();
        if (ultimateItemActions == null) return;

        Pagination pagination = paginationState.get(render);

        render.layoutSlot(
                        '<',
                        new ItemBuilder(Material.ARROW)
                                .setName("§cBack")
                                .getStack()
                )
                .updateOnStateChange(paginationState)
                .onClick(click -> {
                    if (pagination.isFirstPage()) {
                        click.openForPlayer(
                                UltimateItemActionsEditMenu.class,
                                ultimateItemActions
                        );

                        return;
                    }

                    pagination.back();
                });

        render.layoutSlot(
                        '<',
                        new ItemBuilder(Material.ARROW)
                                .setName("§aAdvance")
                                .getStack()
                )
                .displayIf(ctx -> pagination.currentPageIndex() < pagination.lastPageIndex())
                .updateOnStateChange(paginationState)
                .onClick(click -> pagination.advance());
    }


    private void giveMetadataTo(Player player, String key) {
        player.setMetadata(
                "ultimate_item_actions_edit_actions",
                new FixedMetadataValue(
                        UltimateItemActionsMain.getInstance(),
                        key
                )
        );
    }

    private void sendMessage(Player player) {
        player.sendMessage(" ");
        player.sendMessage("§e§lEnter the new value of this function.");
        player.sendMessage("   §e* §7sendmessage:§8[message]");
        player.sendMessage("   §e* §7title:§8[title];[subtitle];[fade in];[stay];[fade out]");
        player.sendMessage("   §e* §7console:§8[command]");
        player.sendMessage("   §e* §7play:§8[sound name]");
        player.sendMessage("§c§lIf you want to cancel, type \"cancel\".");
        player.sendMessage(" ");
    }
}