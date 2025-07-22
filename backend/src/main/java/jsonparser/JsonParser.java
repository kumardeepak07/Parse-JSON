package jsonparser;

public class JsonParser {
    private String json;
    private int pos = 0;

    public JsonParser(String json) {
        this.json = json.trim();
    }

    public JsonValue parse() {
        skipWhitespace();
        char ch = peek();
        if (ch == '{') return parseObject();
        if (ch == '[') return parseArray();
        throw new RuntimeException("Invalid JSON");
    }

    private JsonObject parseObject() {
        JsonObject obj = new JsonObject();
        expect('{');
        skipWhitespace();
        if (peek() == '}') {
            next(); return obj;
        }
        while (true) {
            skipWhitespace();
            String key = parseString();
            skipWhitespace();
            expect(':');
            skipWhitespace();
            JsonValue value = parseValue();
            obj.put(key, value);
            skipWhitespace();
            if (peek() == '}') { next(); break; }
            expect(',');
        }
        return obj;
    }

    private JsonArray parseArray() {
        JsonArray arr = new JsonArray();
        expect('[');
        skipWhitespace();
        if (peek() == ']') {
            next(); return arr;
        }
        while (true) {
            skipWhitespace();
            arr.add(parseValue());
            skipWhitespace();
            if (peek() == ']') { next(); break; }
            expect(',');
        }
        return arr;
    }

    private JsonValue parseValue() {
        skipWhitespace();
        char ch = peek();
        if (ch == '"') return new JsonString(parseString());
        if (ch == '{') return parseObject();
        if (ch == '[') return parseArray();
        if (Character.isDigit(ch) || ch == '-') return parseNumber();
        if (json.startsWith("true", pos)) { pos += 4; return new JsonBoolean(true); }
        if (json.startsWith("false", pos)) { pos += 5; return new JsonBoolean(false); }
        if (json.startsWith("null", pos)) { pos += 4; return new JsonNull(); }
        throw new RuntimeException("Unexpected character: " + ch);
    }

    private String parseString() {
        expect('"');
        StringBuilder sb = new StringBuilder();
        while (true) {
            char ch = next();
            if (ch == '"') break;
            if (ch == '\\') {
                char esc = next();
                switch (esc) {
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case 'n': sb.append('\n'); break;
                    case 't': sb.append('\t'); break;
                    case 'r': sb.append('\r'); break;
                    default: sb.append(esc);
                }
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private JsonNumber parseNumber() {
        int start = pos;
        while (pos < json.length() &&
               (Character.isDigit(json.charAt(pos)) || json.charAt(pos) == '.' || json.charAt(pos) == '-')) {
            pos++;
        }
        return new JsonNumber(Double.parseDouble(json.substring(start, pos)));
    }

    private void skipWhitespace() {
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) pos++;
    }

    private char peek() {
        return json.charAt(pos);
    }

    private char next() {
        return json.charAt(pos++);
    }

    private void expect(char expected) {
        if (next() != expected) {
            throw new RuntimeException("Expected: " + expected);
        }
    }
}