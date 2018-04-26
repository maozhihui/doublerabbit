package com.comba.web.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

/**
 * 生成随机验证码
 */
public class GenerateCodeUtils {

    private static final String strTable = "1234567890";


    public static String generateCode(int length) {
        StringBuffer random = new StringBuffer();

        for (int i = 0; i < length; i++) {
            double num = Math.random() * strTable.length();
            int intR = (int) Math.floor(num);
            char c = strTable.charAt(intR);
            random.append(c);
        }
        return random.toString();
    }

    public static String generateCategoryCode(Optional<String> lastCode,String parentCode){
        String genCode = "10";
        if (lastCode.isPresent()){
            genCode = String.valueOf(Integer.parseInt(lastCode.get())+1);
        }else {
            if (!parentCode.equals("-1")){
                genCode = parentCode + "01";
            }
        }
        return genCode;
    }
}
