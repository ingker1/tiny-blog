package com.ingker.blogvue.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // 生成盐并返回哈希密码
    public static String hashPassword(String password) {
        // 生成盐并对密码进行加盐哈希
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    // 校验密码
    public static boolean checkPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }
}
