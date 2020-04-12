package com.pinyougou.manager.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public class StringUtil {
    public static String iSO8859ToUTF8(String str) {
        if (StringUtils.isNoneBlank(str)) {
            try {
                return new String(str.getBytes("ISO8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
