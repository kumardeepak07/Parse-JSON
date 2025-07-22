package jsonparser;

import java.util.*;

public interface JsonValue {}

class JsonObject extends HashMap<String, JsonValue> implements JsonValue {}
class JsonArray extends ArrayList<JsonValue> implements JsonValue {}
class JsonString implements JsonValue {
    String value;
    JsonString(String value) { this.value = value; }
    public String toString() { return "\""+value+"\""; }
}
class JsonNumber implements JsonValue {
    double value;
    JsonNumber(double value) { this.value = value; }
    public String toString() { return Double.toString(value); }
}
class JsonBoolean implements JsonValue {
    boolean value;
    JsonBoolean(boolean value) { this.value = value; }
    public String toString() { return Boolean.toString(value); }
}
class JsonNull implements JsonValue {
    public String toString() { return "null"; }
}