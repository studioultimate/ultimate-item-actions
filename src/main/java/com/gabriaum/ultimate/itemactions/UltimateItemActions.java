package com.gabriaum.ultimate.itemactions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UltimateItemActions {
    private final String key;
    private final String displayName;
    private final Material icon;
    private final short durability;
    private final List<String> description;
    private final List<String> actions;
}