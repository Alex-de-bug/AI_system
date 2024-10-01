package com.prologApi.enums;

import lombok.Getter;

/**
 * Перечисление для стиля игры.
 */
@Getter
public enum PlayStyle {
    PVP("pvp"),
    PVE("pve"),
    LITE("lite"),
    RP("rp");

    private final String style;

    PlayStyle(String style) {
        this.style = style;
    }

    public static PlayStyle fromString(String style) {
        for (PlayStyle ps : PlayStyle.values()) {
            if (ps.style.equalsIgnoreCase(style)) {
                return ps;
            }
        }
        throw new IllegalArgumentException("Неверный стиль игры. Допустимые значения: pvp, pve, lite, rp");
    }
}
