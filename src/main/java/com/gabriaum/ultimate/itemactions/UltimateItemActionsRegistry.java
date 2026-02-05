package com.gabriaum.ultimate.itemactions;

public class UltimateItemActionsRegistry {
    public static UltimateItemActions getByKey(String key) {
        if (key == null) return null;

        return UltimateItemActionsMain.getInstance()
                .getManager()
                .stream()
                .filter(item ->
                        item.getKey() != null &&
                                item.getKey().equalsIgnoreCase(key)
                )
                .findFirst()
                .orElse(null);
    }
}