package pt.up.hs.emotioneditor.client.models;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsMap extends JavaScriptObject {

    // Overlay types always have protected, zero-arg constructors
    protected JsMap() {
    }

    /**
     * Create an empty instance.
     *
     * @return new Object
     */
    public static native JsMap create() /*-{
        return {};
    }-*/;

    /**
     * Convert a JSON encoded string into a JSOModel instance.
     * <p/>
     * Expects a JSON string structured like '{"foo":"bar","number":123}'
     *
     * @return a populated JSOModel object
     */
    public static native JsMap fromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

    /**
     * Convert a JSON encoded string into an array of JSOModel instance.
     * <p/>
     * Expects a JSON string structured like '[{"foo":"bar","number":123}, {...}]'
     *
     * @return a populated JsArray
     */
    public static native JsArray<JsMap> arrayFromJson(String jsonString) /*-{
        return eval('(' + jsonString + ')');
    }-*/;

    public static JsMap fromMap(Map<String, String> stringStringMap) {
        JsMap instance = JsMap.create();
        for (String key: stringStringMap.keySet()) {
            instance.set(key, stringStringMap.get(key));
        }
        return instance;
    }

    public final native boolean hasKey(String key) /*-{
        return this[key] !== undefined;
    }-*/;

    public final native JsArrayString keys() /*-{
        var a = [];
        for (var p in this) {
            if (this.hasOwnProperty(p)) {
                a.push(p);
            }
        }
        return a;
    }-*/;

    public final Set<String> keySet() {
        JsArrayString array = keys();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < array.length(); i++) {
            set.add(array.get(i));
        }
        return set;
    }

    public final native String get(String key) /*-{
        return "" + this[key];
    }-*/;

    public final native String get(String key, String defaultValue) /*-{
        return this[key] ? ("" + this[key]) : defaultValue;
    }-*/;

    public final native void set(String key, String value) /*-{
        this[key] = value;
    }-*/;

    public final int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public final boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public final native JsMap getObject(String key) /*-{
        return this[key];
    }-*/;

    public final native JsArray<JsMap> getArray(String key) /*-{
        return this[key] ? this[key] : [];
    }-*/;

}
