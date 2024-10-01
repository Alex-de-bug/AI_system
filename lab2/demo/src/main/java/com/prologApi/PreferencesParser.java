package com.prologApi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.prologApi.enums.PlayStyle;
import com.prologApi.enums.ServerMap;

import lombok.Getter;

/**
 * Парсинг предпочтений (Preferences Parser Layer) Данные принимаются в формате:
 * <режим игры>; <стиль игры>; <карта сервера>
 */
@Getter
public class PreferencesParser {

    private String gameMode;
    private PlayStyle playStyle;
    private ServerMap serverMap;

    /**
     * Парсит строку предпочтений.
     *
     * @param preferences строка, содержащая предпочтения в формате:
     * "<режим игры>; <стиль игры>; <карта сервера>"
     * @throws IllegalArgumentException если строка не соответствует формату
     */
    public void parsePreferences(String preferences) throws IllegalArgumentException {
        String[] parts = preferences.split(";");

        if (parts.length != 3) {
            if (parts.length == 1 && (parts[0].equalsIgnoreCase("exit") || parts[0].equalsIgnoreCase("выход"))) {
                System.exit(0);
            }
            throw new IllegalArgumentException("Неверный формат строки. Ожидается: <режим игры>; <стиль игры>; <карта сервера>");
        }

        gameMode = parts[0].trim();
        playStyle = PlayStyle.fromString(parts[1].trim());
        serverMap = ServerMap.fromString(parts[2].trim());

        validateGameMode(gameMode);
    }

    /**
     * перевод пользовательских предпочтений в список предтермов
     *
     * @return Список предтермов для запроса в БЗ
     */
    public List<String> toPrevTerms() {
        return Arrays.asList(gameMode, playStyle.getStyle(), serverMap.getMap())
                .stream()
                .collect(Collectors.toList());
    }

    // Валидация режима игры
    private void validateGameMode(String gameMode) {
        if (!gameMode.equals("2pp") && !gameMode.equals("3pp")) {
            throw new IllegalArgumentException("Неверный режим игры. Допустимые значения: 2pp, 3pp");
        }
    }
}
