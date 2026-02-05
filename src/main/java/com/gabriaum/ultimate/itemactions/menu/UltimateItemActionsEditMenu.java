package com.gabriaum.ultimate.itemactions.menu;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.infra.util.ItemBuilder;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class UltimateItemActionsEditMenu extends View {
    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Ultimate Item Actions Editor");
        config.size(5);
        config.layout(
                "         ",
                " I D N L ",
                "         ",
                "    A    ",
                "<        "
        );

        config.cancelInteractions();
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        Player player = render.getPlayer();
        UltimateItemActions ultimateItemActions = (UltimateItemActions) render.getInitialData();
        if (ultimateItemActions == null) return;

        render.layoutSlot('I', new ItemBuilder(ultimateItemActions.getIcon(), 1, ultimateItemActions.getDurability())
                        .setName("§aIcon")
                        .addLore(
                                Arrays.asList(
                                        "",
                                        "§e§l● §f" + ultimateItemActions.getIcon().name(),
                                        ""
                                )
                        )
                        .getStack())
                .onClick(event -> {
                    giveMetadataTo(player, "icon", ultimateItemActions.getKey());
                    sendMessage(player);

                    event.closeForPlayer();
                });

        render.layoutSlot('D', new ItemBuilder(Material.STONE_BUTTON)
                        .setName("§aDurability")
                        .addLore(
                                Arrays.asList(
                                        "",
                                        "§e§l● §f" + ultimateItemActions.getDurability(),
                                        ""
                                )
                        )
                        .getStack())
                .onClick(event -> {
                    giveMetadataTo(player, "durability", ultimateItemActions.getKey());
                    sendMessage(player);

                    event.closeForPlayer();
                });

        render.layoutSlot('N', new ItemBuilder(Material.NAME_TAG)
                        .setName("§aDisplay Name")
                        .addLore(
                                Arrays.asList(
                                        "",
                                        "§e§l● §f" + ultimateItemActions.getDisplayName(),
                                        ""
                                )
                        )
                        .getStack())
                .onClick(event -> {
                    giveMetadataTo(player, "displayname", ultimateItemActions.getKey());
                    sendMessage(player);

                    event.closeForPlayer();
                });

        render.layoutSlot('L', new ItemBuilder(Material.PAPER)
                        .setName("§aDescription")
                        .addLore("§eClick to modify!")
                        .getStack())
                .onClick(event -> {

                });

        render.layoutSlot('A', new ItemBuilder(Material.PAPER)
                        .setName("§aActions")
                        .addLore("§eClick to modify!")
                        .getStack())
                .onClick(event ->
                        event.openForPlayer(
                                UltimateItemActionsEditActionsMenu.class,
                                ultimateItemActions
                        )
                );
    }

    private void giveMetadataTo(Player player, String action, String key) {
        player.setMetadata(
                "ultimate_item_actions_edit_" + action,
                new FixedMetadataValue(
                        UltimateItemActionsMain.getInstance(),
                        key
                )
        );
    }

    private void sendMessage(Player player) {
        player.sendMessage(" ");
        player.sendMessage("§e§lEnter the new value of this function.");
        player.sendMessage("§c§lIf you want to cancel, type \"cancel\".");
        player.sendMessage(" ");
    }
}