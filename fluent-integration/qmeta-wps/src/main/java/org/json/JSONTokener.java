package org.json;

import java.io.*;

public class JSONTokener {
    private int character;
    private boolean eof;
    private int index;
    private int line;
    private char previous;
    private Reader reader;
    private boolean usePrevious;

    public JSONTokener(Reader var1) {
        this.reader = (Reader)(var1.markSupported()?var1:new BufferedReader(var1));
        this.eof = false;
        this.usePrevious = false;
        this.previous = 0;
        this.index = 0;
        this.character = 1;
        this.line = 1;
    }

//    public JSONTokener(InputStream var1) throws JSONException {
//        this((Reader) (new InputStreamReader(var1)));
//    }

    public JSONTokener(String var1) {
        this((Reader)(new StringReader(var1)));
    }

    public void back() throws JSONException {
        if(!this.usePrevious && this.index > 0) {
            --this.index;
            --this.character;
            this.usePrevious = true;
            this.eof = false;
        } else {
            throw new JSONException("Stepping back two steps is not supported");
        }
    }

    public static int dehexchar(char var0) {
        return var0 >= 48 && var0 <= 57?var0 - 48:(var0 >= 65 && var0 <= 70?var0 - 55:(var0 >= 97 && var0 <= 102?var0 - 87:-1));
    }

    public boolean end() {
        return this.eof && !this.usePrevious;
    }

    public boolean more() throws JSONException {
        this.next();
        if(this.end()) {
            return false;
        } else {
            this.back();
            return true;
        }
    }

    public char next() throws JSONException {
        int var1;
        if(this.usePrevious) {
            this.usePrevious = false;
            var1 = this.previous;
        } else {
            try {
                var1 = this.reader.read();
            } catch (IOException var3) {
                throw new JSONException(var3);
            }

            if(var1 <= 0) {
                this.eof = true;
                var1 = 0;
            }
        }

        ++this.index;
        if(this.previous == 13) {
            ++this.line;
            this.character = var1 == 10?0:1;
        } else if(var1 == 10) {
            ++this.line;
            this.character = 0;
        } else {
            ++this.character;
        }

        this.previous = (char)var1;
        return this.previous;
    }

    public char next(char var1) throws JSONException {
        char var2 = this.next();
        if(var2 != var1) {
            throw this.syntaxError("Expected \'" + var1 + "\' and instead saw \'" + var2 + "\'");
        } else {
            return var2;
        }
    }

    public String next(int var1) throws JSONException {
        if(var1 == 0) {
            return "";
        } else {
            char[] var2 = new char[var1];

            for(int var3 = 0; var3 < var1; ++var3) {
                var2[var3] = this.next();
                if(this.end()) {
                    throw this.syntaxError("Substring bounds error");
                }
            }

            return new String(var2);
        }
    }

    public char nextClean() throws JSONException {
        char var1;
        do {
            var1 = this.next();
        } while(var1 != 0 && var1 <= 32);

        return var1;
    }

    public String nextString(char var1) throws JSONException {
        StringBuffer var3 = new StringBuffer();

        while(true) {
            char var2 = this.next();
            switch(var2) {
                case '\u0000':
                case '\n':
                case '\r':
                    throw this.syntaxError("Unterminated string");
                case '\\':
                    var2 = this.next();
                    switch(var2) {
                        case '\"':
                        case '\'':
                        case '/':
                        case '\\':
                            var3.append(var2);
                            continue;
                        case 'b':
                            var3.append('\b');
                            continue;
                        case 'f':
                            var3.append('\f');
                            continue;
                        case 'n':
                            var3.append('\n');
                            continue;
                        case 'r':
                            var3.append('\r');
                            continue;
                        case 't':
                            var3.append('\t');
                            continue;
                        case 'u':
                            var3.append((char)Integer.parseInt(this.next((int)4), 16));
                            continue;
                        default:
                            throw this.syntaxError("Illegal escape.");
                    }
                default:
                    if(var2 == var1) {
                        return var3.toString();
                    }

                    var3.append(var2);
            }
        }
    }

    public String nextTo(char var1) throws JSONException {
        StringBuffer var2 = new StringBuffer();

        while(true) {
            char var3 = this.next();
            if(var3 == var1 || var3 == 0 || var3 == 10 || var3 == 13) {
                if(var3 != 0) {
                    this.back();
                }

                return var2.toString().trim();
            }

            var2.append(var3);
        }
    }

    public String nextTo(String var1) throws JSONException {
        StringBuffer var3 = new StringBuffer();

        while(true) {
            char var2 = this.next();
            if(var1.indexOf(var2) >= 0 || var2 == 0 || var2 == 10 || var2 == 13) {
                if(var2 != 0) {
                    this.back();
                }

                return var3.toString().trim();
            }

            var3.append(var2);
        }
    }

    public Object nextValue() throws JSONException {
        char var1 = this.nextClean();
        switch(var1) {
            case '\"':
            case '\'':
                return this.nextString(var1);
            case '[':
                this.back();
                return new JSONArray(this);
            case '{':
                this.back();
                return new JSONObject(this);
            default:
                StringBuffer var3;
                for(var3 = new StringBuffer(); var1 >= 32 && ",:]}/\\\"[{;=#".indexOf(var1) < 0; var1 = this.next()) {
                    var3.append(var1);
                }

                this.back();
                String var2 = var3.toString().trim();
                if(var2.equals("")) {
                    throw this.syntaxError("Missing value");
                } else {
                    return JSONObject.stringToValue(var2);
                }
        }
    }

    public char skipTo(char var1) throws JSONException {
        char var2;
        try {
            int var3 = this.index;
            int var4 = this.character;
            int var5 = this.line;
            this.reader.mark(2147483647);

            do {
                var2 = this.next();
                if(var2 == 0) {
                    this.reader.reset();
                    this.index = var3;
                    this.character = var4;
                    this.line = var5;
                    return var2;
                }
            } while(var2 != var1);
        } catch (IOException var6) {
            throw new JSONException(var6);
        }

        this.back();
        return var2;
    }

    public JSONException syntaxError(String var1) {
        return new JSONException(var1 + this.toString());
    }

    public String toString() {
        return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
    }
}
