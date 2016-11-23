package com.hujz.framework.orm.util;

import java.security.MessageDigest;

public class Md5Utils {
	
	private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	public final static String md5(byte[] bs) {
        try {
            MessageDigest o = MessageDigest.getInstance("MD5");
            o.update(bs);
            byte[] r = o.digest();
            return toHex(r);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static String toHex(byte[] bs) {
        int j = bs.length;
        char rs[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = bs[i];
            rs[k++] = hexDigits[byte0 >>> 4 & 0xf];
            rs[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(rs);
    }
}
