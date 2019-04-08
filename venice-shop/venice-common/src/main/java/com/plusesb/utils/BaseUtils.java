package com.plusesb.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseUtils {

    public static final char UNDERLINE='_';

    /**
     * 获取随机字符串
     *
     * @param num
     * @return
     */
    public static String getRandomString(Integer num) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * input 转 字节数组
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input)
            throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    public static int copy(InputStream input, OutputStream output)
            throws IOException
    {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int)count;
    }
    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException
    {
        byte[] buffer = new byte[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    /**
     * 加密刷法
     * @param str
     * @return
     */
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 字符串返回
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null || obj.equals("null") || obj.toString().isEmpty()) {return "";}
        else{
            return obj.toString().trim();
        }

    }

    public static BigDecimal formatBigDecimalM2(BigDecimal bg) {
        BigDecimal scaleValue = BigDecimal.valueOf(bg.doubleValue()).setScale(2);
        return scaleValue;
    }
    /**
     * 复制对象 去掉不需要的字段
     */
    public static void copyProperties(Object product,Object pProduct) throws Exception{
        BeanUtils.copyProperties(product, pProduct, new String[] { "id", "createTime", "updateTime","optlock" });
    }

    /**
     * 驼峰转下划线
     * @param param
     * @return
     */

    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取32位的UUID
     *
     * @return uuid
     */
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    /**
     * 字符串返回str
     *
     * @param obj
     * @param str 默认值
     * @return
     */
    public static String toString(Object obj, String str) {
        if (obj == null || obj.equals("null") || obj.toString().isEmpty())
            return str;
        else
            return obj.toString().trim();
    }

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj.equals(""))
            return true;
        if (pObj == "")
            return true;
        if (pObj instanceof String) {
            return ((String) pObj).length() < 1;
        } else if (pObj instanceof Collection) {
            return ((Collection) pObj).size() < 1;
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() < 1;
        } else if (pObj.getClass().isArray()) {
            return Array.getLength(pObj) < 1;
        }
        return false;
    }

    public static boolean isNotEmpty(Object pObj) {
        return !isEmpty(pObj);
    }

    public static boolean isNumeric(String str) {
        if (str == null)
            return true;
        return str.matches("^[0-9]*$");
    }

    public static boolean isDouble(String str) {
        if (str == null)
            return true;
        return str.matches("^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)$");
    }

    public static boolean isDouble(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof Double) {
            return true;
        }
        return isDouble(obj.toString());
    }

    public static boolean isNumeric(Object obj) {
        if (obj == null)
            return true;
        return isNumeric(obj.toString());
    }

    public static Integer objtoint(Object obj) {
        return objtoint(obj, 0);
    }

    public static Integer objtoint(Object obj, Integer iter) {
        if (isNumeric(obj)) {
            return isEmpty(obj) ? iter : Integer.parseInt(obj.toString());
        } else {
            return iter;
        }
    }

    public static long objtolong(Object obj) {
        return objtolong(obj, 0l);
    }

    public static long objtolong(Object obj, Long iter) {
        Long l = 0l;
        if (isNumeric(obj)) {
            l = isEmpty(obj) ? iter : Long.parseLong(obj.toString());
        } else {
            l = iter;
        }
        return l.longValue();
    }

    public static Double objToDouble(Object obj) {
        return objToDouble(obj, (double) 0l);
    }

    public static Double objToDoubleHasNull(Object obj) {
        if (isDouble(obj)) {
            return isEmpty(obj) ? null : Double.parseDouble(obj.toString());
        } else {
            return null;
        }
    }

    public static Double objToDouble(Object obj, Double num) {
        if (isDouble(obj)) {
            return isEmpty(obj) ? num : Double.parseDouble(obj.toString());
        } else {
            return num;
        }
    }

    public static Boolean objToBoolean(Object obj) {
        return objToBoolean(obj, false);
    }

    public static Boolean objToBoolean(Object obj, Boolean bool) {
        String value = toString(obj).trim();
        if (value.isEmpty()) {
            return isEmpty(bool) ? false : bool;
        }
        if ("是".equals(value) || "真".equals(value) || "1".equals(value) || "true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("否".equals(value) || "假".equals(value) || "0".equals(value) || "false".equalsIgnoreCase(value)) {
            return false;
        }

        return Boolean.parseBoolean(value);
    }

    public static Long[] objTolongArray(Object[] obj) {
        int len = obj.length;
        Long[] lg = new Long[len];
        for (int i = 0; i < len; i++) {
            lg[i] = objtolong(obj[i].toString().trim());
        }
        return lg;
    }

    /**
     * 判断字符串是否是整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (str == null)
            return true;
        return str.matches("^[-\\+]?\\d+$");
    }

    /**
     * 判断字符串是否是浮点数
     *
     * @param str
     * @return
     */
    public static boolean isFloat(String str) {
        if (str == null)
            return true;
        return str.matches("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$") || isInteger(str);
    }

    /**
     * 去右边一个字符
     *
     * @param str
     * @param ch
     * @return
     */
    public static String rtrim(String str, char ch) {
        return org.springframework.util.StringUtils.trimTrailingCharacter(str, ch);
    }

    /**
     * 去左边一个字符
     *
     * @param str
     * @param ch
     * @return
     */
    public static String ltrim(String str, char ch) {
        return org.springframework.util.StringUtils.trimLeadingCharacter(str, ch);
    }

    /**
     * http 请求对象(request)
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest request() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * double 加法 v1+v2
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Double doubleAdd(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    /**
     * double 减法 v1-v2
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Double doubleSub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }

    /**
     * double 乘法 v1*v2
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Double doubleMul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }

    /**
     * double 除法 v1/v2 默认保留小数点后六位
     *
     * @param v1
     * @param v2
     * @return if v2==0D return 0D
     */
    public static Double doubleDivde(Double v1, Double v2) {
        return doubleDivde(v1, v2, 6);
    }

    /**
     * double 除法 v1/v2
     *
     * @param v1
     * @param v2
     * @param scale 除不尽时候精确的小数点位数
     * @return
     */
    public static Double doubleDivde(Double v1, Double v2, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        if (v2.compareTo(0D) == 0) {
            return 0D;
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double 小数位精确
     *
     * @param v1    需要四舍五入的double数字
     * @param scale 保留小数点后几位
     * @return
     */
    public static Double doubleRound(Double v1, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal("1");
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * @param
     * @return
     * @Description 一天开始日期 wwc
     */
    public static Date getBeginDate(Date from) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = String.format("%s %s", sdf.format(from), "00:00:00");
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = from;
        try {
            date = sdf.parse(sd);
        } catch (ParseException e) {
            date = from;
        }
        return date;
    }

    /**
     * @param
     * @return
     * @Description 一天最晚日期 wwc
     */
    public static Date getEndDate(Date to) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = String.format("%s %s", sdf.format(to), "23:59:59");
        Date date = to;
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = sdf.parse(sd);
        } catch (ParseException e) {
            date = to;
        }
        return date;
    }

    /**
     * Description:String转为List，
     *
     * @param str ","为分隔符
     * @return
     * @author weichao.wang 2017年6月2日上午11:08:07
     */
    public static List<String> StrtoList(String str) {
        if (isEmpty(str)) {
            return new ArrayList<String>();
        }
        String[] arrayStr = new String[]{};
        arrayStr = str.split(",");
        return Arrays.asList(arrayStr);
    }

    /**
     * 得到格式化后的时间
     *
     * @param date
     * @param sim  格式化
     * @return 得到sim格式的date 如果date为空返回空字符串
     */
    public static String getDateString(Date date, SimpleDateFormat sim) {
        if (date != null)
            return sim.format(date);
        else
            return "";
    }
    /**
     * 得到几个月后的日期
     */
    public static Date getMonthCount(Date date,int mounth){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, mounth);
        Date m = c.getTime();
        return m;
    }

    /**
     * 得到(yyyy-MM-dd HH:mm:ss)格式的date字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss格式的时间,如果为空返回空字符串
     */
    public static String getDatetimeString(Date date) {
        return getDateString(date, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 得到(yyyy-MM-dd)格式的date字符串
     *
     * @param date
     * @return yyyy-MM-dd格式的时间,如果为空返回空字符串
     */
    public static String getDateString(Date date) {
        return getDateString(date, new SimpleDateFormat("yyyy-MM-dd"));
    }

    /**
     * 获得当前日期时间字符串
     *
     * @return yyyyMMddHHmmss格式时间
     */
    public static String getDate() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(dt);
    }

    public static Date getDateByString(String datestr, SimpleDateFormat sim) {
        try {
            return sim.parse(datestr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDateByString(String datestr) {
        return getDateByString(datestr, new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static Date getDateTimeByString(String datestr) {
        return getDateByString(datestr, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static Date getTimeByString(String datestr) {
        return getDateByString(datestr, new SimpleDateFormat("HH:mm:ss"));
    }

    //double类型转换为BigDecimal
    public static BigDecimal doubleToBigDecimal(Double value) {
        BigDecimal bd = new BigDecimal(objToDouble(value));
        return bd;
    }

    /**
     * 生成随机数
     *
     * @param digit 几位随机数
     * @return
     */
    public static String createRandomString(int digit) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digit; i++) {
            sb.append((int) (Math.random() * 9));
        }
        return sb.toString();
    }

    /**
     * 是否是2003的excel，返回true是2003
     *
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     *
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");

    }

    /**
     * 交期转换  <=14 天的以 "3天-5天"形式展示， 14 天以上以"3周-6周"展示
     * @param startRange
     * @param endRange
     * @return
     */
    public static String getDateRange(Integer startRange, Integer endRange) {
        StringBuffer dateRange = new StringBuffer();
        if (null != startRange && null != endRange) {
            try {
                if (startRange > endRange) {
                    throw new Exception("startRange must less than endRange");
                }
                if (endRange <= 14) {
                    dateRange.append(startRange).append("天").append("-").append(endRange).append("天");
                } else {
                    endRange = endRange % 7 == 0 ? endRange / 7 : endRange / 7 + 1;
                    startRange = startRange / 7;
                    if (startRange == 0) {
                        startRange = 1;
                    }
                    if (startRange == endRange) {
                        startRange += 1;
                    }
                    dateRange.append(startRange).append("周").append("-").append(endRange).append("周");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateRange.toString();
    }
    /**
     * 日期是否在有效期内
     */
    public static boolean availableDate(Date beginDate ,Date endDate,Date nowDate){
        if (beginDate.compareTo(nowDate)<0&&endDate.compareTo(nowDate)>0){
            return true;
        }
        return false;
    }

    public static String formatArraytoString(Object[] objects) {
        StringBuffer sb = new StringBuffer();
        for (Object o:objects
             ) {
            sb.append(o.toString()).append(",");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }
    /**
     *
     * @Title: processFileName
     *
     * @Description: ie,chrom,firfox下处理文件名显示乱码
     */
    public static String processFileName(HttpServletRequest request, String fileNames) {
        String codedfilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                    && -1 != agent.indexOf("Trident")) {// ie

                String name = java.net.URLEncoder.encode(fileNames, "UTF8");

                codedfilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等


                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }


    /**
     * 获取随机字符串
     *
     * @param num
     * @return
     */
    public static String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
