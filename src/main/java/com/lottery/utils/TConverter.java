package com.lottery.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TConverter {
    //转为字符串
    public static String toSafeString(Object value) {
        try {
            return (value == null) ? "" : value.toString();
        } catch (Exception e) {
            return "";
        }

    }

    /*
    转为long型
     */
    public static long ObjectToLong(Object value) {
        return ObjectToLong(value, 0l);
    }

    /*
    转为long型
     */
    public static long ObjectToLong(Object value, long defalutValue) {
        if (value == null) {
            return defalutValue;
        }
        String strValue = value.toString();
        if (strValue.indexOf(".") >= 0)
            strValue = strValue.substring(0, strValue.indexOf("."));
        try {
            return Long.parseLong(strValue);
        } catch (Exception ex) {
            return defalutValue;
        }
    }


    public static short ObjectToShort(Object value, short defualtValue) {
        if (value == null) {
            return defualtValue;
        }
        String strValue = value.toString();
        if (strValue.indexOf(".") >= 0)
            strValue = strValue.substring(0, strValue.indexOf("."));

        try {
            return Short.parseShort(strValue);
        } catch (Exception ex) {
            return defualtValue;
        }
    }

    public static BigDecimal ObjectToBigDecimal(Object value, BigDecimal defualtValue) {
        if (value == null) {
            return defualtValue;
        }
        String strValue = value.toString();
        try {
            return new BigDecimal(strValue);
        } catch (Exception ex) {
            return defualtValue;
        }
    }

    public static BigDecimal ObjectToBigDecimal(Object value) {
        if (value == null) {
            return new BigDecimal(0);
        }
        String strValue = value.toString();
        try {
            return new BigDecimal(strValue);
        } catch (Exception ex) {
            return new BigDecimal(0);
        }
    }

    public static short ObjectToShort(Object value) {
        return ObjectToShort(value, (short) 0);
    }


    /*
    转为int
     */
    public static int ObjectToInt(Object value, int defualtValue) {
        if (value == null) {
            return defualtValue;
        }

        String strValue = value.toString();
        if (strValue.indexOf(".") >= 0)
            strValue = strValue.substring(0, strValue.indexOf("."));
        try {
            return Integer.parseInt(strValue);
        } catch (Exception ex) {

        }
        return defualtValue;
    }

    /*
    转为int
     */
    public static int ObjectToInt(Object value) {
        return ObjectToInt(value, 0);
    }

    public static float ObjectToFloat(Object value, float defualtValue) {
        if (value == null) {
            return defualtValue;
        }
        String strValue = value.toString();
        try {
            return Float.parseFloat(strValue);
        } catch (Exception ex) {
            return defualtValue;
        }
    }

    public static float ObjectToFloat(Object value) {
        return ObjectToFloat(value, 0);
    }

    //获取列表首元素
    public static <T> T GetFirstOrDefualt(List<T> list) {
        if (list == null || list.size() == 0)
            return null;
        return list.get(0);
    }

    //安全转换类型
    public static <T> T safeConvert(Object obj) {
        try {
            if (obj == null)
                return null;

            return (T) obj;
        } catch (Exception ex) {
            return null;
        }
    }


    //对象转为map
    public static Map<String, Object> convertObjToMap(Object thisObj) {
        Map map = new HashMap();
        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            Method[] m = c.getMethods();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();
                if (method.startsWith("get")) {
                    try {
                        Object value = m[i].invoke(thisObj);
                        if (value != null) {
                            String key = method.substring(3);
                            key = key.substring(0, 1).toLowerCase() + key.substring(1);
                            map.put(key, value);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    public static <T> T GetListFromMap(Object result) {
        if (result == null)
            return null;
        Map<String, Object> map = (Map<String, Object>) result;
        Object list = map.get("list");
        if (list == null)
            return null;
        return (T) list;
    }

    /**
     * 父类赋值给子类
     *
     * @param <T>
     * @param father 父类
     * @param child  子类
     * @throws Exception
     */
    public static <D, T> void FatherToChild(T father, D child) throws Exception {
        if (child.getClass().getSuperclass() != father.getClass()) {
            throw new Exception("child 不是 father 的子类");
        }
        Class<?> fatherClass = father.getClass();
        Field[] declaredFields = fatherClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            Method method;
            try {
                method = fatherClass.getDeclaredMethod("get" + upperHeadChar(field.getName()));
            } catch (NoSuchMethodException e) {
                continue;
            }
            if(method==null) continue;
            Object obj = null;
            try {
                obj = method.invoke(father);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                continue;
            }
            field.setAccessible(true);
            try {
                field.set(child, obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 首字母大写，in:deleteDate，out:DeleteDate
     */
    private static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }
}