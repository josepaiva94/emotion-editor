package pt.up.hs.emotioneditor.client.models;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.Map;

/**
 * Definition of an emotion on the editor
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Emotion extends JavaScriptObject {

    protected Emotion() {
    }

    public final native void addEmotion(String emotionKey, Emotion emotion) /*-{
        this.emotions = this.emotions || {};
        this.emotions[emotionKey] = emotion;
    }-*/;

    public final native Map<String, Emotion> getEmotions() /*-{
        return this.emotions || {};
    }-*/;

    public final native void setLabel(String label) /*-{
        this.label = label;
    }-*/;

    public final native String getLabel() /*-{
        return this.label;
    }-*/;
}
