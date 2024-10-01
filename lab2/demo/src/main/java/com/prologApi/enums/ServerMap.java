package com.prologApi.enums;

import lombok.Getter;

/**
 * Перечисление для карт сервера.
 */
@Getter
public enum ServerMap {
    CHERNARUS_PLUS("chernarus_plus"),
    ENOCH("enoch"),
    LIVONIA("livonia"),
    NMC_CHERNOBYL("nmchernobyl");

    private final String map;

    ServerMap(String map) {
        this.map = map;
    }

    public static ServerMap fromString(String map) {
        for (ServerMap sm : ServerMap.values()) {
            if (sm.map.equalsIgnoreCase(map)) {
                return sm;
            }
        }
        throw new IllegalArgumentException("Неверная карта сервера. Допустимые значения: chernarus_plus, enoch, livonia, nmchernobyl");
    }
}
