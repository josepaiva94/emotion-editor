package pt.up.hs.emotioneditor.models;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Emotion highlight JavaScript Definition
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class EmotionHighlight extends JavaScriptObject {

    protected EmotionHighlight() {
    }

    public final native int getLine() /*-{
        return this.line;
    }-*/;

    public final native void setLine(int line) /*-{
        this.line = line;
    }-*/;

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
