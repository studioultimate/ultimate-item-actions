package com.gabriaum.ultimate.itemactions;

public class UltimateItemActionsRegistry {
    public static UltimateItemActions getByKey(String key) {
        return UltimateItemActionsMain.getInstance().getManager().stream()
                .filter(item -> item.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }
}
