package com.nailton.managerpassword.configuration.util;

import java.util.regex.Pattern;

public class UserMiddleware {

    private static boolean patternMatches(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public static boolean validCredentials(String name, String email, String password) {
        boolean isValidEmail = patternMatches(email);
        return name.length() >= 3 && isValidEmail && password.length() >= 8;
    }

    public static boolean validPass(String app, String pass) {
        return app.length() >= 4 && pass.length() >= 8;
    }
}
