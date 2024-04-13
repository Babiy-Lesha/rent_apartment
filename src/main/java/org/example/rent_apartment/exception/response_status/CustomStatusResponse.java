package org.example.rent_apartment.exception.response_status;

public class CustomStatusResponse {
   /**
    * Auth status cod
    */
    public static final int BAD_REG = 800;

    public static final int NOT_UNIQ_REG = 801;

    public static final int BAD_AUTH_LOG = 804;

    public static final int BAD_PASS_LOG = 806;

    /**
     * Apartment status code
     */

    public static final int NOT_UNIQ_ADDRES = 601;
    public static final int BAD_INPUT_LOCATION = 602;

 /**
  * message error
  */
 public static final String NICKNAME_EXISTS = "пользователь с таким никнеймом уже существует";
 public static final String LOGIN_EXISTS = "пользователь с таким логином существует";
 public static final String APARTMENT_EXISTS = "Такой апартамент существует";
 public static final String APARTMENT_NO_EXISTS = "Такой апартамант не существует";
 public static final String LOGIN_NO_EXISTS = "пользователя с таким логином нет";
 public static final String INCORRECT_PASSWORD = "неверный пароль";
 public static final String USER_NOT_ONLINE = "Такой пользователь не в сети";
 public static final String NOT_INPUT_LOCATION = "Вы не ввели широту или долготу, проверте вводимые данные";
}
