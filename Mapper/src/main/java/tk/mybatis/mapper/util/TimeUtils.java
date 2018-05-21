package tk.mybatis.mapper.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间相关帮助类
 * @date: 2018年1月4日 下午7:08:42
 * @author: jiuzhou.hu
 */
public class TimeUtils {
	/**
	 * 获取系统时间Timestamp
	 * @return
	 */
	public static Date getSysTimestamp(){
		return Calendar.getInstance().getTime();
	}
	
	/** 
     * 将未指定格式的日期字符串转化成java.util.Date类型日期对象 <br> 
     * @param date,待转换的日期字符串 
     * @return 
     * @throws ParseException 
     */  
    public static Calendar toCalendar(String date){
        String parse=date;  
        parse=parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");  
        parse=parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");  
        parse=parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");  
        parse=parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");  
        parse=parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");  
        parse=parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");  
        parse=parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
        return toCalendar(date,new SimpleDateFormat(parse));
    }
    
    /**
     * 把字符串按照指定的转换格式转换为Calendar
     * 
     * @param time 时间字符串
     * @param formator 指定的格式
     * @return 带指定格式的Calendar对象
     */
    public static Calendar toCalendar(String time, DateFormat formator) {

        try {
            return toRawCalendar(time, formator);
        } catch(Exception ex) {
            ex.printStackTrace();
            return Calendar.getInstance();
        }
    }
    
    /**
     * 将字符串转化为时间对象
     * 
     * @param time 转换的时间
     * @param formator 指定格式
     * @return 带指定格式的Calendar时间对象
     * @throws Exception 转换失败错误
     */
    public static Calendar toRawCalendar(String time, DateFormat formator)
            throws Exception {
        Calendar cal = Calendar.getInstance();
        java.util.Date d = formator.parse(time);
        cal.setTime(d);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
    
    public static String getNowStr(String format) {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(format);
        return df.format(cal.getTime());
    }
}
