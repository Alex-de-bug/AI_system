package com.prologApi;

/**
 * Логика (Logic Layer) Основной класс программы, который связывает все
 * компоненты вместе.
 */
public class Main {

    /**
     * Организует общий поток данных между слоями.
     *
     * @param args (не предусмотрены)
     */
    public static void main(String[] args) {
        UserInteraction userInteraction = new UserInteraction();
        userInteraction.askUserName();

        PreferencesParser preferencesParser = new PreferencesParser();
        boolean success = false;

        while (!success) {
            try {
                preferencesParser.parsePreferences(userInteraction.getUserPreferences());
                System.out.println("Предпочтения успешно приняты!\n");
                PrologInterface prologInterface = new PrologInterface();
                prologInterface.queryUserPref(preferencesParser.toPrevTerms());
                success = true;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, повторите ввод.");
            }
        }

    }
}
