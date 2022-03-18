package com.appleyk.auth.common.util;

import java.util.*;

/**
 * <p>自定义的常用数据判断和类型转换工具类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:08
 */
public class SeGeneralUtils {
    private SeGeneralUtils() {
    }
    public static String validStringValue(Object object) {
        return object == null ? "" : object.toString();
    }
    public static int validIntValue(Object object) {
        return object == null ? 0 : Integer.valueOf(object.toString());
    }
    public static long validLongValue(Object object) {
        return object == null ? 0L : Long.valueOf(object.toString());
    }
    public static boolean isNotEmpty(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof Integer) {
            return Integer.valueOf(object.toString()) > 0;
        } else if (object instanceof Long) {
            return Long.valueOf(object.toString()) > 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() > 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() > 0;
        } else if (object instanceof Boolean) {
            return Boolean.valueOf(object.toString());
        } else if (object instanceof List) {
            return ((List)object).size() > 0;
        } else if (object instanceof Set) {
            return ((Set)object).size() > 0;
        } else if (object instanceof Map) {
            return ((Map)object).size() > 0;
        } else if (object instanceof Iterator) {
            return ((Iterator)object).hasNext();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).size() > 0;
        } else {
            return true;
        }
    }
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Integer) {
            return Integer.valueOf(object.toString()) == 0;
        } else if (object instanceof Long) {
            return Long.valueOf(object.toString()) == 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() == 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() == 0;
        } else if (object instanceof Boolean) {
            return Boolean.valueOf(object.toString());
        } else if (object instanceof List) {
            return ((List)object).size() == 0;
        } else if (object instanceof Set) {
            return ((Set)object).size() == 0;
        } else if (object instanceof Map) {
            return ((Map)object).size() == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator)object).hasNext();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).size() == 0;
        } else {
            return false;
        }
    }
    public static List<Long> set2ListLong(Set<Long> sets) {
        List<Long> list = new ArrayList();
        if (sets != null && sets.size() > 0) {
            Iterator var2 = sets.iterator();
            while(var2.hasNext()) {
                Object o = var2.next();
                Long id = Long.valueOf(o.toString());
                list.add(id);
            }
            return list;
        } else {
            return null;
        }
    }
    public static List<String> set2ListStr(Set<?> set) {
        ArrayList result = new ArrayList();
        try {
            if (set.size() > 0) {
                set.forEach((s) -> {
                    result.add(s.toString());
                });
            }
        } catch (Exception var3) {
            System.err.println("HashSet转换失败");
        }
        return result;
    }
    public static Set<Long> list2Set(List<String> list) {
        HashSet result = new HashSet();
        try {
            if (list.size() > 0) {
                list.forEach((s) -> {
                    result.add(Long.parseLong(s));
                });
            }
        } catch (Exception var3) {
            System.err.println("List转换失败");
        }
        return result;
    }
    public static Set<Long> hashSet2Set(HashSet<?> sets) {
        Set<Long> result = new HashSet();
        if (sets != null && sets.size() > 0) {
            Iterator var2 = sets.iterator();
            while(var2.hasNext()) {
                Object o = var2.next();
                Long id = Long.valueOf(o.toString());
                result.add(id);
            }
            return result;
        } else {
            return null;
        }
    }
    public static List<String> set2List(Set<?> set) {
        ArrayList result = new ArrayList();
        try {
            if (set.size() > 0) {
                set.forEach((s) -> {
                    result.add(s.toString());
                });
            }
        } catch (Exception var3) {
            System.err.println("HashSet转换失败");
        }
        return result;
    }
    public static List<String> list2List(List<?> list) {
        ArrayList result = new ArrayList();
        try {
            if (list.size() > 0) {
                list.forEach((s) -> {
                    result.add(s.toString());
                });
            }
        } catch (Exception var3) {
            System.err.println("List转换失败");
        }
        return result;
    }
    public static String list2String(List<String> list) {
        if (list == null) {
            return null;
        } else {
            String result = "";
            try {
                if (list.size() <= 0) {
                    return result;
                }
                String s;
                for(Iterator var2 = list.iterator(); var2.hasNext(); result = result + s + ";") {
                    s = (String)var2.next();
                }
                result = result.substring(0, result.length() - 1);
            } catch (Exception var4) {
                System.err.println("List转换失败");
            }
            return result;
        }
    }
    public static List<String> String2List(String str) {
        if (str != null && str.length() != 0) {
            ArrayList list = new ArrayList();
            try {
                String[] strs = str.substring(1, str.length() - 1).replaceAll("\"", "").split(",");
                String[] var3 = strs;
                int var4 = strs.length;
                for(int var5 = 0; var5 < var4; ++var5) {
                    String result = var3[var5];
                    list.add(result);
                }
            } catch (Exception var7) {
                System.err.println("List转换失败");
            }
            return list;
        } else {
            return null;
        }
    }
}
