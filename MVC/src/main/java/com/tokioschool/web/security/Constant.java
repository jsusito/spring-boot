package com.tokioschool.web.security;

public class Constant {
	public static String ADMIN_ROLE="ADMIN";
	public static String USER_ROLE="USER";
	public static String LOGIN_URL = "/login";
	public static String LOGIN_SUCCESS_URL = "/index";
	public static String LOGIN_FAILURE_URL = "/login?error=true"; //Si hay un error le pasamos este parametro
	public static String LOGOUT_URL ="/logout";
	public static String LOGOUT_SUCCESS_URL ="/index/";
}
