package pt.up.hs.emotioneditor.client.models;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Highlight JavaScript Definition
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Highlight extends JavaScriptObject {

    protected Highlight() {
    }

    public final native int getStart() /*-{
        return this.start;
    }-*/;

    public final native void setStart(int start) /*-{
        this.start = start;
    }-*/;

    public final native int getSize() /*-{
        return this.size;
    }-*/;

    public final native void setSize(int size) /*-{
        this.size = size;
    }-*/;

    public final native void setProps(JsMap props) /*-{
        this.props = props;
    }-*/;

    public final native JsMap getProps() /*-{
        return this.props || {};
    }-*/;

    public final native String getGlobalEmotion() /*-{
        return this.globalEmotion;
    }-*/;

    public final native void setGlobalEmotion(String globalEmotion) /*-{
        this.globalEmotion = globalEmotion;
    }-*/;

    public final native String getIntermediateEmotion() /*-{
        return this.intermediateEmotion;
    }-*/;

    public final native void setIntermediateEmotion(String intermediateEmotion) /*-{
        this.intermediateEmotion = intermediateEmotion;
    }-*/;

    public final native String getSpecificEmotion() /*-{
        return this.specificEmotion;
    }-*/;

    public final native void setSpecificEmotion(String specificEmotion) /*-{
        this.specificEmotion = specificEmotion;
    }-*/;
}
