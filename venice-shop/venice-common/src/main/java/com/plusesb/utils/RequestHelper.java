package com.plusesb.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: altchen
 */
public class RequestHelper {

    public static Map<String, Object> getParameters(ServletRequest request) {
        return getParametersStartingWith(request, "");
    }

    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Validate.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    private static final String[] MOBILE_SPECIFIC_SUBSTRING = {
            "iPhone", "Android", "MIDP", "Opera Mobi",
            "Opera Mini", "BlackBerry", "HP iPAQ", "IEMobile",
            "MSIEMobile", "Windows Phone", "HTC", "LG",
            "MOT", "Nokia", "Symbian", "Fennec",
            "Maemo", "Tear", "Midori", "armv",
            "Windows CE", "WindowsCE", "Smartphone", "240x320",
            "176x220", "320x320", "160x160", "webOS",
            "Palm", "Sagem", "Samsung", "SGH",
            "SonyEricsson", "MMP", "UCWEB"};

    public static boolean isMobileBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (StringUtils.isBlank(userAgent)) {
            return false;
        }
        for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
            if (userAgent.contains(mobile)
                    || userAgent.contains(mobile.toUpperCase())
                    || userAgent.contains(mobile.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isWeixinBrowser(HttpServletRequest request) {
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPcBrowser(HttpServletRequest request) {
        boolean isPcBrowser = true;
        if (RequestHelper.isMobileBrowser(request)) {
            isPcBrowser = false;
        }
        return isPcBrowser;
    }

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (servletRequestAttributes == null) {
            return null;
        } else {
            return servletRequestAttributes.getRequest();
        }

    }

    public static HttpServletResponse getCurrentResponse() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (servletRequestAttributes == null) {
            return null;
        } else {
            return servletRequestAttributes.getResponse();
        }

    }

    public static String getContextBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = "";
        if (request.getServerPort() == 80) {
            basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
        } else {
            //basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
            //微信登陆限制只使用80
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        }
        return basePath;
    }

    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    public static void setCookie(HttpServletResponse response, String key, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSeconds);
        response.addCookie(cookie);
    }

}
