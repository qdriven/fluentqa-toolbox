package org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class JSONObject {
    private Map map;
    public static final Object NULL = new Null();

    public JSONObject() {
        this.map = new HashMap();
    }

    public JSONObject(JSONObject var1, String[] var2) {
        this();

        for(int var3 = 0; var3 < var2.length; ++var3) {
            try {
                this.putOnce(var2[var3], var1.opt(var2[var3]));
            } catch (Exception var5) {
                ;
            }
        }

    }

    public JSONObject(JSONTokener var1) throws JSONException {
        this();
        if(var1.nextClean() != 123) {
            throw var1.syntaxError("A JSONObject text must begin with \'{\'");
        } else {
            while(true) {
                char var2 = var1.nextClean();
                switch(var2) {
                    case '\u0000':
                        throw var1.syntaxError("A JSONObject text must end with \'}\'");
                    case '}':
                        return;
                    default:
                        var1.back();
                        String var3 = var1.nextValue().toString();
                        var2 = var1.nextClean();
                        if(var2 == 61) {
                            if(var1.next() != 62) {
                                var1.back();
                            }
                        } else if(var2 != 58) {
                            throw var1.syntaxError("Expected a \':\' after a key");
                        }

                        this.putOnce(var3, var1.nextValue());
                        switch(var1.nextClean()) {
                            case ',':
                            case ';':
                                if(var1.nextClean() == 125) {
                                    return;
                                }

                                var1.back();
                                break;
                            case '}':
                                return;
                            default:
                                throw var1.syntaxError("Expected a \',\' or \'}\'");
                        }
                }
            }
        }
    }

    public JSONObject(Map var1) {
        this.map = new HashMap();
        if(var1 != null) {
            Iterator var2 = var1.entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry var3 = (Map.Entry)var2.next();
                Object var4 = var3.getValue();
                if(var4 != null) {
                    this.map.put(var3.getKey(), wrap(var4));
                }
            }
        }

    }

    public JSONObject(Object var1) {
        this();
        this.populateMap(var1);
    }

    public JSONObject(Object var1, String[] var2) {
        this();
        Class var3 = var1.getClass();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            String var5 = var2[var4];

            try {
                this.putOpt(var5, var3.getField(var5).get(var1));
            } catch (Exception var7) {
                ;
            }
        }

    }

    public JSONObject(String var1) throws JSONException {
        this(new JSONTokener(var1));
    }

    public JSONObject(String var1, Locale var2) throws JSONException {
        this();
        ResourceBundle var3 = ResourceBundle.getBundle(var1, var2, Thread.currentThread().getContextClassLoader());
        Enumeration var4 = var3.getKeys();

        while(true) {
            Object var5;
            do {
                if(!var4.hasMoreElements()) {
                    return;
                }

                var5 = var4.nextElement();
            } while(!(var5 instanceof String));

            String[] var6 = ((String)var5).split("\\.");
            int var7 = var6.length - 1;
            JSONObject var8 = this;

            for(int var9 = 0; var9 < var7; ++var9) {
                String var10 = var6[var9];
                JSONObject var11 = var8.optJSONObject(var10);
                if(var11 == null) {
                    var11 = new JSONObject();
                    var8.put(var10, (Object)var11);
                }

                var8 = var11;
            }

            var8.put(var6[var7], (Object)var3.getString((String)var5));
        }
    }

    public JSONObject accumulate(String var1, Object var2) throws JSONException {
        testValidity(var2);
        Object var3 = this.opt(var1);
        if(var3 == null) {
            this.put(var1, var2 instanceof JSONArray ?(new JSONArray()).put(var2):var2);
        } else if(var3 instanceof JSONArray) {
            ((JSONArray)var3).put(var2);
        } else {
            this.put(var1, (Object)(new JSONArray()).put(var3).put(var2));
        }

        return this;
    }

    public JSONObject append(String var1, Object var2) throws JSONException {
        testValidity(var2);
        Object var3 = this.opt(var1);
        if(var3 == null) {
            this.put(var1, (Object)(new JSONArray()).put(var2));
        } else {
            if(!(var3 instanceof JSONArray)) {
                throw new JSONException("JSONObject[" + var1 + "] is not a JSONArray.");
            }

            this.put(var1, (Object)((JSONArray)var3).put(var2));
        }

        return this;
    }

    public static String doubleToString(double var0) {
        if(!Double.isInfinite(var0) && !Double.isNaN(var0)) {
            String var2 = Double.toString(var0);
            if(var2.indexOf(46) > 0 && var2.indexOf(101) < 0 && var2.indexOf(69) < 0) {
                while(var2.endsWith("0")) {
                    var2 = var2.substring(0, var2.length() - 1);
                }

                if(var2.endsWith(".")) {
                    var2 = var2.substring(0, var2.length() - 1);
                }
            }

            return var2;
        } else {
            return "null";
        }
    }

    public Object get(String var1) throws JSONException {
        if(var1 == null) {
            throw new JSONException("Null key.");
        } else {
            Object var2 = this.opt(var1);
            if(var2 == null) {
                throw new JSONException("JSONObject[" + quote(var1) + "] not found.");
            } else {
                return var2;
            }
        }
    }

    public boolean getBoolean(String var1) throws JSONException {
        Object var2 = this.get(var1);
        if(!var2.equals(Boolean.FALSE) && (!(var2 instanceof String) || !((String)var2).equalsIgnoreCase("false"))) {
            if(!var2.equals(Boolean.TRUE) && (!(var2 instanceof String) || !((String)var2).equalsIgnoreCase("true"))) {
                throw new JSONException("JSONObject[" + quote(var1) + "] is not a Boolean.");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public double getDouble(String var1) throws JSONException {
        Object var2 = this.get(var1);

        try {
            return var2 instanceof Number?((Number)var2).doubleValue():Double.parseDouble((String)var2);
        } catch (Exception var4) {
            throw new JSONException("JSONObject[" + quote(var1) + "] is not a number.");
        }
    }

    public int getInt(String var1) throws JSONException {
        Object var2 = this.get(var1);

        try {
            return var2 instanceof Number?((Number)var2).intValue():Integer.parseInt((String)var2);
        } catch (Exception var4) {
            throw new JSONException("JSONObject[" + quote(var1) + "] is not an int.");
        }
    }

    public JSONArray getJSONArray(String var1) throws JSONException {
        Object var2 = this.get(var1);
        if(var2 instanceof JSONArray) {
            return (JSONArray)var2;
        } else {
            throw new JSONException("JSONObject[" + quote(var1) + "] is not a JSONArray.");
        }
    }

    public JSONObject getJSONObject(String var1) throws JSONException {
        Object var2 = this.get(var1);
        if(var2 instanceof JSONObject) {
            return (JSONObject)var2;
        } else {
            throw new JSONException("JSONObject[" + quote(var1) + "] is not a JSONObject.");
        }
    }

    public long getLong(String var1) throws JSONException {
        Object var2 = this.get(var1);

        try {
            return var2 instanceof Number?((Number)var2).longValue():Long.parseLong((String)var2);
        } catch (Exception var4) {
            throw new JSONException("JSONObject[" + quote(var1) + "] is not a long.");
        }
    }

    public static String[] getNames(JSONObject var0) {
        int var1 = var0.length();
        if(var1 == 0) {
            return null;
        } else {
            Iterator var2 = var0.keys();
            String[] var3 = new String[var1];

            for(int var4 = 0; var2.hasNext(); ++var4) {
                var3[var4] = (String)var2.next();
            }

            return var3;
        }
    }

    public static String[] getNames(Object var0) {
        if(var0 == null) {
            return null;
        } else {
            Class var1 = var0.getClass();
            Field[] var2 = var1.getFields();
            int var3 = var2.length;
            if(var3 == 0) {
                return null;
            } else {
                String[] var4 = new String[var3];

                for(int var5 = 0; var5 < var3; ++var5) {
                    var4[var5] = var2[var5].getName();
                }

                return var4;
            }
        }
    }

    public String getString(String var1) throws JSONException {
        Object var2 = this.get(var1);
        if(var2 instanceof String) {
            return (String)var2;
        } else {
            throw new JSONException("JSONObject[" + quote(var1) + "] not a string.");
        }
    }

    public boolean has(String var1) {
        return this.map.containsKey(var1);
    }

    public JSONObject increment(String var1) throws JSONException {
        Object var2 = this.opt(var1);
        if(var2 == null) {
            this.put(var1, 1);
        } else if(var2 instanceof Integer) {
            this.put(var1, ((Integer)var2).intValue() + 1);
        } else if(var2 instanceof Long) {
            this.put(var1, ((Long)var2).longValue() + 1L);
        } else if(var2 instanceof Double) {
            this.put(var1, ((Double)var2).doubleValue() + 1.0D);
        } else {
            if(!(var2 instanceof Float)) {
                throw new JSONException("Unable to increment [" + quote(var1) + "].");
            }

            this.put(var1, (double)(((Float)var2).floatValue() + 1.0F));
        }

        return this;
    }

    public boolean isNull(String var1) {
        return NULL.equals(this.opt(var1));
    }

    public Iterator keys() {
        return this.map.keySet().iterator();
    }

    public int length() {
        return this.map.size();
    }

    public JSONArray names() {
        JSONArray var1 = new JSONArray();
        Iterator var2 = this.keys();

        while(var2.hasNext()) {
            var1.put(var2.next());
        }

        return var1.length() == 0?null:var1;
    }

    public static String numberToString(Number var0) throws JSONException {
        if(var0 == null) {
            throw new JSONException("Null pointer");
        } else {
            testValidity(var0);
            String var1 = var0.toString();
            if(var1.indexOf(46) > 0 && var1.indexOf(101) < 0 && var1.indexOf(69) < 0) {
                while(var1.endsWith("0")) {
                    var1 = var1.substring(0, var1.length() - 1);
                }

                if(var1.endsWith(".")) {
                    var1 = var1.substring(0, var1.length() - 1);
                }
            }

            return var1;
        }
    }

    public Object opt(String var1) {
        return var1 == null?null:this.map.get(var1);
    }

    public boolean optBoolean(String var1) {
        return this.optBoolean(var1, false);
    }

    public boolean optBoolean(String var1, boolean var2) {
        try {
            return this.getBoolean(var1);
        } catch (Exception var4) {
            return var2;
        }
    }

    public double optDouble(String var1) {
        return this.optDouble(var1, 0.0D / 0.0);
    }

    public double optDouble(String var1, double var2) {
        try {
            return this.getDouble(var1);
        } catch (Exception var5) {
            return var2;
        }
    }

    public int optInt(String var1) {
        return this.optInt(var1, 0);
    }

    public int optInt(String var1, int var2) {
        try {
            return this.getInt(var1);
        } catch (Exception var4) {
            return var2;
        }
    }

    public JSONArray optJSONArray(String var1) {
        Object var2 = this.opt(var1);
        return var2 instanceof JSONArray?(JSONArray)var2:null;
    }

    public JSONObject optJSONObject(String var1) {
        Object var2 = this.opt(var1);
        return var2 instanceof JSONObject?(JSONObject)var2:null;
    }

    public long optLong(String var1) {
        return this.optLong(var1, 0L);
    }

    public long optLong(String var1, long var2) {
        try {
            return this.getLong(var1);
        } catch (Exception var5) {
            return var2;
        }
    }

    public String optString(String var1) {
        return this.optString(var1, "");
    }

    public String optString(String var1, String var2) {
        Object var3 = this.opt(var1);
        return NULL.equals(var3)?var2:var3.toString();
    }

    private void populateMap(Object var1) {
        Class var2 = var1.getClass();
        boolean var3 = var2.getClassLoader() != null;
        Method[] var4 = var3?var2.getMethods():var2.getDeclaredMethods();

        for(int var5 = 0; var5 < var4.length; ++var5) {
            try {
                Method var6 = var4[var5];
                if(Modifier.isPublic(var6.getModifiers())) {
                    String var7 = var6.getName();
                    String var8 = "";
                    if(var7.startsWith("get")) {
                        if(!var7.equals("getClass") && !var7.equals("getDeclaringClass")) {
                            var8 = var7.substring(3);
                        } else {
                            var8 = "";
                        }
                    } else if(var7.startsWith("is")) {
                        var8 = var7.substring(2);
                    }

                    if(var8.length() > 0 && Character.isUpperCase(var8.charAt(0)) && var6.getParameterTypes().length == 0) {
                        if(var8.length() == 1) {
                            var8 = var8.toLowerCase();
                        } else if(!Character.isUpperCase(var8.charAt(1))) {
                            var8 = var8.substring(0, 1).toLowerCase() + var8.substring(1);
                        }

                        Object var9 = var6.invoke(var1, (Object[])null);
                        if(var9 != null) {
                            this.map.put(var8, wrap(var9));
                        }
                    }
                }
            } catch (Exception var10) {
                ;
            }
        }

    }

    public JSONObject put(String var1, boolean var2) throws JSONException {
        this.put(var1, (Object)(var2?Boolean.TRUE:Boolean.FALSE));
        return this;
    }

    public JSONObject put(String var1, Collection var2) throws JSONException {
        this.put(var1, (Object)(new JSONArray(var2)));
        return this;
    }

    public JSONObject put(String var1, double var2) throws JSONException {
        this.put(var1, (Object)(new Double(var2)));
        return this;
    }

    public JSONObject put(String var1, int var2) throws JSONException {
        this.put(var1, (Object)(new Integer(var2)));
        return this;
    }

    public JSONObject put(String var1, long var2) throws JSONException {
        this.put(var1, (Object)(new Long(var2)));
        return this;
    }

    public JSONObject put(String var1, Map var2) throws JSONException {
        this.put(var1, (Object)(new JSONObject(var2)));
        return this;
    }

    public JSONObject put(String var1, Object var2) throws JSONException {
        if(var1 == null) {
            throw new JSONException("Null key.");
        } else {
            if(var2 != null) {
                testValidity(var2);
                this.map.put(var1, var2);
            } else {
                this.remove(var1);
            }

            return this;
        }
    }

    public JSONObject putOnce(String var1, Object var2) throws JSONException {
        if(var1 != null && var2 != null) {
            if(this.opt(var1) != null) {
                throw new JSONException("Duplicate key \"" + var1 + "\"");
            }

            this.put(var1, var2);
        }

        return this;
    }

    public JSONObject putOpt(String var1, Object var2) throws JSONException {
        if(var1 != null && var2 != null) {
            this.put(var1, var2);
        }

        return this;
    }

    public static String quote(String var0) {
        if(var0 != null && var0.length() != 0) {
            char var2 = 0;
            int var5 = var0.length();
            StringBuffer var6 = new StringBuffer(var5 + 4);
            var6.append('\"');

            for(int var4 = 0; var4 < var5; ++var4) {
                char var1 = var2;
                var2 = var0.charAt(var4);
                switch(var2) {
                    case '\b':
                        var6.append("\\b");
                        continue;
                    case '\t':
                        var6.append("\\t");
                        continue;
                    case '\n':
                        var6.append("\\n");
                        continue;
                    case '\f':
                        var6.append("\\f");
                        continue;
                    case '\r':
                        var6.append("\\r");
                        continue;
                    case '\"':
                    case '\\':
                        var6.append('\\');
                        var6.append(var2);
                        continue;
                    case '/':
                        if(var1 == 60) {
                            var6.append('\\');
                        }

                        var6.append(var2);
                        continue;
                }

                if(var2 >= 32 && (var2 < 128 || var2 >= 160) && (var2 < 8192 || var2 >= 8448)) {
                    var6.append(var2);
                } else {
                    String var3 = "000" + Integer.toHexString(var2);
                    var6.append("\\u" + var3.substring(var3.length() - 4));
                }
            }

            var6.append('\"');
            return var6.toString();
        } else {
            return "\"\"";
        }
    }

    public Object remove(String var1) {
        return this.map.remove(var1);
    }

    public static Object stringToValue(String var0) {
        if(var0.equals("")) {
            return var0;
        } else if(var0.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if(var0.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        } else if(var0.equalsIgnoreCase("null")) {
            return NULL;
        } else {
            char var1 = var0.charAt(0);
            if(var1 >= 48 && var1 <= 57 || var1 == 46 || var1 == 45 || var1 == 43) {
                if(var1 == 48 && var0.length() > 2 && (var0.charAt(1) == 120 || var0.charAt(1) == 88)) {
                    try {
                        return new Integer(Integer.parseInt(var0.substring(2), 16));
                    } catch (Exception var4) {
                        ;
                    }
                }

                try {
                    if(var0.indexOf(46) <= -1 && var0.indexOf(101) <= -1 && var0.indexOf(69) <= -1) {
                        Long var2 = new Long(var0);
                        if(var2.longValue() == (long)var2.intValue()) {
                            return new Integer(var2.intValue());
                        }

                        return var2;
                    }

                    return Double.valueOf(var0);
                } catch (Exception var3) {
                    ;
                }
            }

            return var0;
        }
    }

    public static void testValidity(Object var0) throws JSONException {
        if(var0 != null) {
            if(var0 instanceof Double) {
                if(((Double)var0).isInfinite() || ((Double)var0).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            } else if(var0 instanceof Float && (((Float)var0).isInfinite() || ((Float)var0).isNaN())) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }

    }

    public JSONArray toJSONArray(JSONArray var1) throws JSONException {
        if(var1 != null && var1.length() != 0) {
            JSONArray var2 = new JSONArray();

            for(int var3 = 0; var3 < var1.length(); ++var3) {
                var2.put(this.opt(var1.getString(var3)));
            }

            return var2;
        } else {
            return null;
        }
    }

    public String toString() {
        try {
            Iterator var1 = this.keys();
            StringBuffer var2 = new StringBuffer("{");

            while(var1.hasNext()) {
                if(var2.length() > 1) {
                    var2.append(',');
                }

                Object var3 = var1.next();
                var2.append(quote(var3.toString()));
                var2.append(':');
                var2.append(valueToString(this.map.get(var3)));
            }

            var2.append('}');
            return var2.toString();
        } catch (Exception var4) {
            return null;
        }
    }

    public String toString(int var1) throws JSONException {
        return this.toString(var1, 0);
    }

    String toString(int var1, int var2) throws JSONException {
        int var4 = this.length();
        if(var4 == 0) {
            return "{}";
        } else {
            Iterator var5 = this.keys();
            int var6 = var2 + var1;
            StringBuffer var8 = new StringBuffer("{");
            Object var7;
            if(var4 == 1) {
                var7 = var5.next();
                var8.append(quote(var7.toString()));
                var8.append(": ");
                var8.append(valueToString(this.map.get(var7), var1, var2));
            } else {
                while(true) {
                    int var3;
                    if(!var5.hasNext()) {
                        if(var8.length() > 1) {
                            var8.append('\n');

                            for(var3 = 0; var3 < var2; ++var3) {
                                var8.append(' ');
                            }
                        }
                        break;
                    }

                    var7 = var5.next();
                    if(var8.length() > 1) {
                        var8.append(",\n");
                    } else {
                        var8.append('\n');
                    }

                    for(var3 = 0; var3 < var6; ++var3) {
                        var8.append(' ');
                    }

                    var8.append(quote(var7.toString()));
                    var8.append(": ");
                    var8.append(valueToString(this.map.get(var7), var1, var6));
                }
            }

            var8.append('}');
            return var8.toString();
        }
    }

    public static String valueToString(Object var0) throws JSONException {
        if(var0 != null && !var0.equals((Object)null)) {
            if(var0 instanceof JSONString) {
                String var1;
                try {
                    var1 = ((JSONString)var0).toJSONString();
                } catch (Exception var3) {
                    throw new JSONException(var3);
                }

                if(var1 instanceof String) {
                    return (String)var1;
                } else {
                    throw new JSONException("Bad value from toJSONString: " + var1);
                }
            } else {
                return var0 instanceof Number?numberToString((Number)var0):(!(var0 instanceof Boolean) && !(var0 instanceof JSONObject) && !(var0 instanceof JSONArray)?(var0 instanceof Map?(new JSONObject((Map)var0)).toString():(var0 instanceof Collection?(new JSONArray((Collection)var0)).toString():(var0.getClass().isArray()?(new JSONArray(var0)).toString():quote(var0.toString())))):var0.toString());
            }
        } else {
            return "null";
        }
    }

    static String valueToString(Object var0, int var1, int var2) throws JSONException {
        if(var0 != null && !var0.equals((Object)null)) {
            try {
                if(var0 instanceof JSONString) {
                    String var3 = ((JSONString)var0).toJSONString();
                    if(var3 instanceof String) {
                        return (String)var3;
                    }
                }
            } catch (Exception var4) {
                ;
            }

            return var0 instanceof Number?numberToString((Number)var0):(var0 instanceof Boolean?var0.toString():(var0 instanceof JSONObject?((JSONObject)var0).toString(var1, var2):(var0 instanceof JSONArray?((JSONArray)var0).toString(var1, var2):(var0 instanceof Map?(new JSONObject((Map)var0)).toString(var1, var2):(var0 instanceof Collection?(new JSONArray((Collection)var0)).toString(var1, var2):(var0.getClass().isArray()?(new JSONArray(var0)).toString(var1, var2):quote(var0.toString())))))));
        } else {
            return "null";
        }
    }

    public static Object wrap(Object var0) {
        try {
            if(var0 == null) {
                return NULL;
            } else if(!(var0 instanceof JSONObject) && !(var0 instanceof JSONArray) && !NULL.equals(var0) && !(var0 instanceof JSONString) && !(var0 instanceof Byte) && !(var0 instanceof Character) && !(var0 instanceof Short) && !(var0 instanceof Integer) && !(var0 instanceof Long) && !(var0 instanceof Boolean) && !(var0 instanceof Float) && !(var0 instanceof Double) && !(var0 instanceof String)) {
                if(var0 instanceof Collection) {
                    return new JSONArray((Collection)var0);
                } else if(var0.getClass().isArray()) {
                    return new JSONArray(var0);
                } else if(var0 instanceof Map) {
                    return new JSONObject((Map)var0);
                } else {
                    Package var1 = var0.getClass().getPackage();
                    String var2 = var1 != null?var1.getName():"";
                    return !var2.startsWith("java.") && !var2.startsWith("javax.") && var0.getClass().getClassLoader() != null?new JSONObject(var0):var0.toString();
                }
            } else {
                return var0;
            }
        } catch (Exception var3) {
            return null;
        }
    }

    public Writer write(Writer var1) throws JSONException {
        try {
            boolean var2 = false;
            Iterator var3 = this.keys();
            var1.write(123);

            for(; var3.hasNext(); var2 = true) {
                if(var2) {
                    var1.write(44);
                }

                Object var4 = var3.next();
                var1.write(quote(var4.toString()));
                var1.write(58);
                Object var5 = this.map.get(var4);
                if(var5 instanceof JSONObject) {
                    ((JSONObject)var5).write(var1);
                } else if(var5 instanceof JSONArray) {
                    ((JSONArray)var5).write(var1);
                } else {
                    var1.write(valueToString(var5));
                }
            }

            var1.write(125);
            return var1;
        } catch (IOException var6) {
            throw new JSONException(var6);
        }
    }

    private static final class Null {
        private Null() {
        }

        protected final Object clone() {
            return this;
        }

        public boolean equals(Object var1) {
            return var1 == null || var1 == this;
        }

        public String toString() {
            return "null";
        }
    }
}
