package pt.up.hs.emotioneditor;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.RootPanel;
import pt.up.hs.emotioneditor.models.EmotionHighlight;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for testing EmotionEditor
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class EmotionEditorTest implements EntryPoint {
    private static final String EDITOR_CONTAINER_ID = "editorContainer";

    @Override
    public void onModuleLoad() {

        EmotionEditor emotionEditor = new EmotionEditor();
        emotionEditor.setText(
                "My father’s family name being Pirrip, and my Christian name" +
                        " Philip, my infant tongue could make of both names " +
                        "nothing longer or more explicit than Pip. So, I cal" +
                        "led myself Pip, and came to be called Pip.\n\nI giv" +
                        "e Pirrip as my father’s family name, on the authori" +
                        "ty of his tombstone and my sister,—Mrs. Joe Gargery" +
                        ", who married the blacksmith. As I never saw my fat" +
                        "her or my mother, and never saw any likeness of eit" +
                        "her of them (for their days were long before the da" +
                        "ys of photographs), my first fancies regarding what" +
                        " they were like were unreasonably derived from thei" +
                        "r tombstones...");

        List<EmotionHighlight> highlights = new ArrayList<>();

        EmotionHighlight highlight1 = JavaScriptObject.createObject().cast();
        highlight1.setLine(0);
        highlight1.setStart(133);
        highlight1.setSize(8);
        highlight1.setGlobalEmotion("BENEVOLENCE");
        highlight1.setIntermediateEmotion("AFFECTION");
        highlight1.setSpecificEmotion("DESIRE");

        EmotionHighlight highlight2 = JavaScriptObject.createObject().cast();
        highlight2.setLine(2);
        highlight2.setStart(66);
        highlight2.setSize(9);
        highlight2.setGlobalEmotion("SAFETY");
        highlight2.setIntermediateEmotion("COURAGE");
        highlight2.setSpecificEmotion("EXTROVERSION");

        EmotionHighlight highlight3 = JavaScriptObject.createObject().cast();
        highlight3.setLine(2);
        highlight3.setStart(83);
        highlight3.setSize(6);
        highlight3.setGlobalEmotion("DISCOMFORT");
        highlight3.setIntermediateEmotion("MADNESS");
        highlight3.setSpecificEmotion("MENTAL_DISEASE");

        highlights.add(highlight1);
        highlights.add(highlight2);
        highlights.add(highlight3);

        emotionEditor.highlight(highlights);

        RootPanel.get(EDITOR_CONTAINER_ID).add(emotionEditor);
    }
}
