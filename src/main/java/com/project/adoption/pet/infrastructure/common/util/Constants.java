package com.project.adoption.pet.infrastructure.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final String CITY_INDEX = "city_index";
    public static final String NEIGHBORHOOD_INDEX = "neighborhood_index";
    public static final String MSG_CREATE_SUCCESSFULLY = "Usuario %s %s creado correctamente";
    public static final String INACTIVE = "inactive";
    public static final String MSG_DELETE_SUCCESSFULLY = "Usuario %s %s inactivado correctamente";
    public static final String EMAIL_NOT_FOUND = "email %s no encontrado";
    public static final String MSG_USER_UPDATE_SUCCESSFULLY = "Usuario %s %s actualizado correctamente";
    public static final String MSG_UPDATE_USER_NOT_FOUND = "No se encontro el usuario %s por lo que se procede a crearlo";
    public static final String DYNAMO_ROL = "DYNAMO_ROL";
    public static final String ROLE_SESSION_NAME_DYNAMO = "dynamo-user";
    public static final String TABLE_USER_NAME = "user";
    public static final String TABLE_EXIST = "La tabla existe";
    public static final String TABLE_DONT_EXIST = "La tabla no existe";
    public static final String MSG_NOT_CREATE_USER = "No se pudo crear el usuario %s %s, intenta mas tarde";
    public static final String MSG_USER_NOT_FOUND = "Usuario con email %s no encontrado";
    public static final String MSG_USER_NOT_GET_EXCEPTION = "usuario con el email %s no pudo ser obtenido, intenta mas tarde";
    public static final String MSG_USER_NOT_UPDATE_EXCEPTION = "Usuario %s %s no pudo ser actualizado, intenta mas tarde";
    public static final String MSG_USER_NOT_DELETED_EXCEPTION = "Usuario con email %s no pudo ser eliminado, intenta mas tarde";
    public static final String MSG_USERS_BY_CITY_NOT_GET_EXCEPTION = "No se pudo obtener los usuarios por la ciudad %s, intenta mas tarde";
    public static final String MSG_USERS_BY_NEIGHBORHOOD_NOT_GET_EXCEPTION = "No se pudo obtener los usuarios por el barrio %s, intenta mas tarde";
    public static final String MSG_ERROR_CLIENT_ADOPTION = "Se presentó un error interno, intenta mas tarde";
}
