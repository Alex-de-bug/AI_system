package com.prologApi;

import java.util.Scanner;

/**
 * Взаимодействие с пользователем (User Interaction Layer)
 */
public class UserInteraction {

    private final Scanner scanner;
    private String userName;

    public UserInteraction() {
        scanner = new Scanner(System.in);
    }

    /* Запрос у пользователя его имени */
    public void askUserName() {
        boolean valid = false;
        while (!valid) {
            System.out.print("Ваше имя: ");
            this.userName = scanner.nextLine();
            if (containsInvalidCharacters(userName)) {
                System.out.println("Ошибка: Имя пользователя должно содержать только буквы и цифры.");
                System.out.println("Пожалуйста, введите имя заново.");
            } else {
                valid = true;
            }
        }
    }

    //валидатор от экранирования
    private boolean containsInvalidCharacters(String input) {
        return input.matches(".*[\"'\\\\/].*");
    }

    /**
     * Запрос у пользователя предпочтений без парсинга
     *
     * @return Полный запрос пользователя, подлежащий разделению
     */
    public String getUserPreferences() {
        System.out.println(PrologInterface.getRules() + this.userName + ", введите ваши предпочтения или <выход>/<exit> для выхода:\n");
        return scanner.nextLine();
    }

    /**
     * Вывод рекомендаций исходя из БЗ prolog
     *
     * @param
     */
    public void displayResult(String result) {
        System.out.println("Вам следует попробовать следующие сервера:");
    }
}
