package com.gabriaum.ultimate.itemactions.infra.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public final class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, int amount, int durability) {
        this.itemStack = new ItemStack(material, amount, (short) durability);
    }

    public ItemBuilder changeItem(Consumer<ItemStack> consumer) {
        consumer.accept(itemStack);
        return this;
    }

    public ItemBuilder changeMeta(Consumer<ItemMeta> consumer) {
        return changeItem(
                stack -> {
                    final ItemMeta meta = stack.getItemMeta();
                    if (meta != null) {
                        consumer.accept(meta);
                        stack.setItemMeta(meta);
                    }
                });
    }

    public ItemBuilder setMaterial(Material material) {
        return changeItem(stack -> stack.setType(material));
    }

    public ItemBuilder setAmount(int amount) {
        return changeItem(stack -> stack.setAmount(amount));
    }

    public ItemBuilder setDurability(int durability) {
        return changeItem(stack -> stack.setDurability((short) durability));
    }

    public ItemBuilder setEnchant(Enchantment enchantment, int level) {
        return changeItem(stack -> stack.addUnsafeEnchantment(enchantment, level));
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        return changeItem(stack -> stack.removeEnchantment(enchantment));
    }

    public ItemBuilder setName(String name) {
        return changeMeta(meta -> meta.setDisplayName(colorize(name)));
    }

    public ItemBuilder addLore(String lore) {
        return changeMeta(
                meta -> {
                    if (lore == null) {
                        meta.setLore(null);
                    } else {
                        meta.setLore(splitLines(lore));
                    }
                });
    }

    public ItemBuilder addLore(List<String> lines) {
        return changeMeta(
                meta -> {
                    if (lines == null) {
                        meta.setLore(null);
                    } else {
                        List<String> flat = new ArrayList<>();
                        for (String line : lines) {
                            if (line == null) {
                                flat.add("");
                                continue;
                            }
                            if (line.contains("\n")) flat.addAll(splitLines(line));
                            else flat.add(colorize(line));
                        }
                        meta.setLore(flat);
                    }
                });
    }

    public ItemBuilder addLore(String lore, int limit) {
        return addLore(formatLore(lore, limit));
    }

    public ItemBuilder setFlag(ItemFlag... itemFlags) {
        return changeMeta(meta -> meta.addItemFlags(itemFlags));
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        return changeMeta(meta -> meta.spigot().setUnbreakable(unbreakable));
    }

    public ItemBuilder setEffect(PotionEffect potionEffect, boolean splash) {
        return setEffect(potionEffect.getType(), potionEffect, splash);
    }

    public ItemBuilder setEffect(
            PotionEffectType potionEffectType, PotionEffect potionEffect, boolean splash) {
        return changeMeta(
                meta -> {
                    if (meta instanceof PotionMeta) {
                        PotionMeta potionMeta = (PotionMeta) meta;
                        potionMeta.addCustomEffect(potionEffect, true);
                        final Potion potion = new Potion(PotionType.getByEffect(potionEffectType));
                        potion.setSplash(splash);
                        potion.apply(itemStack);
                    }
                });
    }

    public ItemBuilder setColor(Color color) {
        return changeMeta(
                meta -> {
                    if (meta instanceof LeatherArmorMeta) {
                        LeatherArmorMeta leather = (LeatherArmorMeta) meta;
                        leather.setColor(color);
                    }
                });
    }

    public ItemBuilder setSkullOwner(String owner) {
        return changeMeta(
                meta -> {
                    if (meta instanceof SkullMeta) {
                        SkullMeta skull = (SkullMeta) meta;
                        skull.setOwner(owner);
                    }
                });
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setSkull(String texture) {
        Bukkit.getUnsafe()
                .modifyItemStack(
                        itemStack,
                        "{SkullOwner:{Id:"
                                + new UUID(texture.hashCode(), texture.hashCode())
                                + ",Properties:{textures:[{Value:"
                                + texture
                                + "}]}}}");
        return this;
    }

    public ItemStack getStack() {
        return itemStack.clone();
    }

    private List<String> formatLore(String input, int limit) {
        final var lore = new ObjectArrayList<String>();
        final var builder = new StringBuilder();

        for (final var word : input.split(" ")) {
            final var stripped = ChatColor.stripColor(builder.toString());
            final var shouldBreak =
                    stripped.length() > limit || stripped.endsWith(".") || stripped.endsWith("!");

            if (shouldBreak && builder.length() > 0) {
                lore.add("§7" + builder);
                if (stripped.endsWith(".") || stripped.endsWith("!")) lore.add("");
                builder.setLength(0);
            }

            if (word.contains("\n")) {
                final int newlineIndex = word.indexOf('\n');
                final String before = word.substring(0, newlineIndex);
                final String after = word.substring(newlineIndex + 1);

                if (builder.length() > 0) builder.append(" ");
                builder.append(before);
                lore.add("§7" + builder);
                builder.setLength(0);
                if (!after.isEmpty()) builder.append(after);
            } else {
                if (builder.length() > 0) builder.append(" ");
                builder.append(word);
            }
        }

        if (builder.length() > 0) lore.add("§7" + builder);
        lore.trim();

        return lore;
    }

    private static String colorize(String s) {
        if (s == null) return "";
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static List<String> splitLines(String s) {
        if (s == null) return java.util.Collections.emptyList();
        String normalized = s.replace("\r\n", "\n").replace("\\r\\n", "\n").replace("\\n", "\n");
        String[] arr = normalized.split("\n", -1);

        List<String> out = new ArrayList<>(arr.length);
        for (String line : arr) out.add(colorize(line));
        return out;
    }
}
