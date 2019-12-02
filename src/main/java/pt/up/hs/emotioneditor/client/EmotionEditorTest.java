package pt.up.hs.emotioneditor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import pt.up.hs.emotioneditor.client.models.Highlight;
import pt.up.hs.emotioneditor.client.models.JsMap;

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
                        ", who married the blacksmith.\nAs I never saw my fat" +
                        "her or my mother, and never saw any likeness of eit" +
                        "her of them (for their days were long before the da" +
                        "ys of photographs), my first fancies regarding what" +
                        " they were like were unreasonably derived from thei" +
                        "r tombstones...");

        List<Highlight> highlights = new ArrayList<>();

        Highlight highlight1 = JavaScriptObject.createObject().cast();
        highlight1.setStart(133);
        highlight1.setSize(8);

        JsMap props1 = JsMap.create();
        props1.set("global", "BENEVOLENCE");
        props1.set("intermediate", "AFFECTION");
        props1.set("specific", "DESIRE");
        highlight1.setProps(props1);

        Highlight highlight2 = JavaScriptObject.createObject().cast();
        highlight2.setStart(271);
        highlight2.setSize(9);

        JsMap props2 = JsMap.create();
        props2.set("global", "SAFETY");
        props2.set("intermediate", "COURAGE");
        props2.set("specific", "EXTROVERSION");
        highlight2.setProps(props2);

        Highlight highlight3 = JavaScriptObject.createObject().cast();
        highlight3.setStart(288);
        highlight3.setSize(6);

        JsMap props3 = JsMap.create();
        props3.set("global", "DISCOMFORT");
        props3.set("intermediate", "MADNESS");
        props3.set("specific", "MENTAL_DISEASE");
        highlight3.setProps(props3);

        highlights.add(highlight1);
        highlights.add(highlight2);
        highlights.add(highlight3);

        emotionEditor.highlight(highlights);

        RootPanel.get(EDITOR_CONTAINER_ID).add(emotionEditor);

        Button btn = new Button("Highlights");
        btn.addClickHandler(h -> {
            List<Highlight> highlightsFromEditor = emotionEditor.highlights();
            for (Highlight hl: highlightsFromEditor) {
                Window.alert(hl.getStart() + " " + hl.getSize());
            }
        });

        RootPanel.get(EDITOR_CONTAINER_ID).add(btn);
    }
}
