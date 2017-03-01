package com.hujz.framework.orm.util;

import java.awt.Color;
import java.util.Random;

/** 
 * 随机数工具类
 * @ClassName: RandomUtils 
 * @Create Author: hjz
 * @Create Date: 2015-1-12 下午1:00:13 
 */ 
public class RandomUtils {
	
    public static String KEY = "~!@#$%^&*()_";

    /**
     * 包含0的可能数字随机数值
     * @Title KEY1
     * @type String
     * @Create Author: hjz
     * @date 2015-1-12 下午1:00:25
     * 含义 
     */
    public static String KEY1 = "0123456789";
    
    /**
     * 不包含0的可能数字随机数值
     * @Title KEY11
     * @type String
     * @Create Author: hjz
     * @date 2015-1-12 下午1:00:34
     * 含义 
     */
    public static String KEY11 = "123456789";
    
    /**
     * 可能随机数的大写字母
     * @Title KEY2
     * @type String
     * @Create Author: hjz
     * @date 2015-1-12 下午1:00:41
     * 含义 
     */
    public static String KEY2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * 可能随机数的小写字母
     * @Title KEY3
     * @type String
     * @Create Author: hjz
     * @date 2015-1-12 下午1:00:49
     * 含义 
     */
    public static String KEY3 = "abcdefghijklmnopqrstuvwxyz";
    
    /**
     * 可能随机数的数字和大写字母
     * @Title KEY4
     * @type String
     * @Create Author: hjz
     * @date 2015-1-12 下午1:00:58
     * 含义 
     */
    public static String KEY4 = KEY1+KEY2;

    /**
     * 可能随机数的数字和小写字母
     * @Title KEY5
     * @type String
     * @Create Author: hjz
     * @date 2015-1-12 下午1:01:05
     * 含义 
     */
    public static String KEY5 = KEY1+KEY3;

    /**
     * 可能随机数的大小写字母
     * @Title KEY6
     * @type String
     * @Create Author: hjz
     * @date 2015-1-12 下午1:01:13
     * 含义 
     */
    public static String KEY6 = KEY2+KEY3;
    
    /**
     * 可能随机数的数字、大小写字母
     * @Title KEY7
     * @type String
     * @Create Author: hjz
     * @date 2015-1-12 下午1:13:10
     * 含义 
     */
    public static String KEY7 = KEY1+KEY6;
    
    /**
     * 随机数种子
     * @Title seed
     * @type Random
     * @Create Author: hjz
     * @date 2015-1-12 下午1:13:21
     * 含义 
     */
    static Random seed;


    static {
        seed = new Random(System.currentTimeMillis());
    }
    
    /**
     * 按照指定长度产生随机数，随机数可能含有数字和大小写字母
     * @author hjz
     * @date 2015-1-12 下午1:13:29
     * @param length	长度
     * @return
     */
    public static String getRandomValue(int length) {
        return _getRandomValue(KEY7, length);
    }

    /**
     * 按照指定长度和键值产生随机数
     * @author hjz
     * @date 2015-1-12 下午1:13:50
     * @param key		随机种子
     * @param length	指定长度
     * @return			随机字符串
     */
    public static String getRandomValue(String key, int length) {
        return _getRandomValue(key, length);
    }

    /**
     * 按照指定长度和键值产生随机数
     * @author hjz
     * @date 2015-1-12 下午1:14:11
     * @param key		随机种子
     * @param length	指定长度
     * @return			随机字符串
     */
    private static String _getRandomValue(String key, int length) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            sb.append(key.charAt((int) (Math.random() * key.length())));
        }
        return sb.toString();
    }
    
    /**
     * 在一定范围内获取随机数
     * @author hjz
     * @date 2015-1-12 下午1:14:56
     * @param range		指定的范围
     * @return			随机数值
     */
    public static int getRandom(int range) {
        return (int) (seed.nextDouble() * range);
    }
    
    /**
     * 生成一个long类型的随机数 
     * @author hjz
     * @date 2015-1-12 下午1:15:52
     * @param begin
     * @param end
     * @return 返回long类型的随机数
     */
    public static long random(long begin,long end){
    	long rtn = begin + (long)(Math.random() * (end - begin));
        if(rtn == begin || rtn == end){
            return random(begin,end);
        }
        return rtn;
    }
    
    /**
     * 生成一个double类型的随机数 
     * @author hjz
     * @date 2015-1-12 下午1:15:52
     * @param begin
     * @param end
     * @return 返回double类型的随机数
     */
    public static double randomDouble(long begin,long end){
    	double rtn = begin + (double)(Math.random() * (end - begin));
        if(rtn == begin || rtn == end){
            return randomDouble(begin,end);
        }
        return rtn;
    }
    public static void main(String[] args) {
		System.out.println(random(1, 100));
	}
    
    /**
	 * 生成随即颜色
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String randomColor() {   
        Color color = new Color((new Double(Math.random() * 128)).intValue() + 128,(new Double(Math.random() * 128)).intValue() + 128,(new Double(Math.random() * 128)).intValue() + 128);   
        String R, G, B;   
	    StringBuffer sb = new StringBuffer();   
	  
	    R = Integer.toHexString(color.getRed());   
	    G = Integer.toHexString(color.getGreen());   
	    B = Integer.toHexString(color.getBlue());   
	  
	    R = R.length() == 1 ? "0" + R : R;   
	    G = G.length() == 1 ? "0" + G : G;   
	    B = B.length() == 1 ? "0" + B : B;   
	  
	    sb.append(R);   
	    sb.append(G);   
	    sb.append(B);   
	  
	    return sb.toString();   
	} 
}
