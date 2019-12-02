package pt.up.hs.emotioneditor.client.models;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;
import java.util.List;

public class JsArray<E extends JavaScriptObject> extends JavaScriptObject {
    protected JsArray() {
    }

    public final native int length() /*-{
        return this.length;
    }-*/;

    public final native E get(int i) /*-{
        return this[i];
    }-*/;

    public final List<E> getAsList() {
        List<E> list = new ArrayList<>(length());
        for (int i = 0; i < length(); i++) {
            list.add(get(i));
        }
        return list;
    }
}
