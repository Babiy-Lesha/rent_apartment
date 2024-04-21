package org.example.rent_apartment.app_const;

public class AppConst {
    public static final String BASE_AUTH = "/api/v1";
    public static final String BASE_FUNCTION = "/api/v2";
    public static final String BASE_PHOTO = "/api/v3";
    public static final String URL_REGISTRATION = BASE_AUTH + "/registration";
    public static final String URL_AUTH = BASE_AUTH + "/auth";
    public static final String URL_AUTH_ADMIN = BASE_AUTH + "/auth_admin";
    public static final String URL_LOG_AUTH = BASE_AUTH + "/exit";
    public static final String URL_ADD_NEW_APARTMENT = BASE_FUNCTION + "/add_new_apartment";
    public static final String URL_DELETE_ADDRESS_APARTMENT = BASE_FUNCTION + "/delete_apartment";
    public static final String URL_FIND_INFO_APARTMENT = BASE_FUNCTION + "/info_apartment";

    public static final String URL_ADD_PHOTO_APARTMENT = BASE_PHOTO + "/add_photo";


    /**
     * message success
     */

    public static final String REGISTRATION_SUCCESS = "Пользователь успешно зарегистрирован в системе";
    public static final String AUTH_SUCCESS = "Авторизация успешна";
    public static final String LOG_AUTH_SUCCESS = "Выход успешно выполнен";
    public static final String REGISTRATION_APARTMENT_SUCCESS = "Апартамент зарегестрирован";
    public static final String PHOTO_ADD_SUCCESS = "Фотография успешно добавлена";
    public static final String APARTMENT_DELETE_SUCCESS = "Апартамент успешно удален";

}
