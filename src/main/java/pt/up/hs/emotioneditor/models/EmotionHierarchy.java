package pt.up.hs.emotioneditor.models;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.Map;

/**
 * Definition of the emotion hierarchy on the editor
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class EmotionHierarchy extends JavaScriptObject {

    protected EmotionHierarchy() {
    }

    public final native void addEmotion(String emotionKey, Emotion emotion) /*-{
        this.emotions = this.emotions || {};
        this.emotions[emotionKey] = emotion;
    }-*/;

    public final native Map<String, Emotion> getEmotions() /*-{
        return this.emotions || {};
    }-*/;

    public final native void addLevel(String level) /*-{
        this.levels = this.levels || [];
        this.levels.push(level);
    }-*/;

    public final native String[] getLevels() /*-{
        return this.levels || [];
    }-*/;
}
