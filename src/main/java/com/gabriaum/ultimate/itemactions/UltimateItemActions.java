package com.gabriaum.ultimate.itemactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UltimateItemActions {
    private final String key;
    private String displayName;
    private Material icon;
    private short durability;
    private List<String> description;
    private List<String> actions;
}