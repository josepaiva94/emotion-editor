package pt.up.hs.emotioneditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import pt.up.fc.dcc.mediumeditor.client.MediumEditor;
import pt.up.fc.dcc.mediumeditor.client.models.EditorOptions;
import pt.up.fc.dcc.mediumeditor.client.models.ToolbarOptions;
import pt.up.hs.emotioneditor.models.EmotionHierarchy;
import pt.up.hs.emotioneditor.models.EmotionHighlight;
import pt.up.hs.emotioneditor.models.JsArray;
import pt.up.hs.emotioneditor.resources.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Widget that allows the user to write text and highlight sentiments with
 * colors
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class EmotionEditor extends Composite implements HasValueChangeHandlers<String> {

    interface EmotionEditorUiBinder extends UiBinder<HTMLPanel, EmotionEditor> {
    }

    private static EmotionEditorUiBinder uiBinder = GWT.create(EmotionEditorUiBinder.class);

    @UiField
    HTMLPanel editorContainer;

    private MediumEditor editor;
    private Element editorElement;

    private EmotionHierarchy emotionHierarchy;

    public EmotionEditor() {
        initWidget(uiBinder.createAndBindUi(this));

        // inject css stylesheets
        Resources.INSTANCE.style().ensureInjected();

        EditorOptions editorOptions = JavaScriptObject.createObject().cast();

        initEditor(editorOptions);
    }

    private void initEditor(EditorOptions editorOptions) {
        ToolbarOptions toolbarOptions = JavaScriptObject.createObject().cast();
        toolbarOptions.setButtons(new String[]{"emotion-highlighter"});

        editorOptions.setToolbar(toolbarOptions);

        emotionHierarchy = JsonUtils.safeEval(
                Resources.INSTANCE.emotionsJson().getText()).cast();

        editorOptions.set("emotionHighlighter",
                JsonUtils.safeEval("{ \"emotions\": " + JsonUtils.stringify(emotionHierarchy) + " }"));

        editor = new MediumEditor(
                '.' + Resources.INSTANCE.style().editableContainer(),
                editorOptions);
        editorElement = editor.getElement();

        editorContainer.add(editor);
    }

    public String getText() {
        return editor.getText();
    }

    public void setText(String text) {
        editor.setText(text);
    }

    public String getHTML() {
        return editor.getHTML();
    }

    public void setHTML(String html) {
        editor.setHTML(html);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return editor.addValueChangeHandler(handler);
    }

    public int countCharacters() {
        return editor.countCharacters();
    }

    public int countWords() {
        return editor.countWords();
    }

    public void highlight(List<EmotionHighlight> emotionHighlights) {

        String text = editor.getText();

        List<List<String>> htmlChars = Arrays.stream(text.split("\n"))
                .map(ln -> ln.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        for (EmotionHighlight emotionHighlight : emotionHighlights) {

            List<String> emotionLabels = new ArrayList<>();

            StringBuilder startTag = new StringBuilder();
            startTag.append("<mark class='emotion-highlight'");
            String global = emotionHighlight.getGlobalEmotion(),
                   intermediate = emotionHighlight.getIntermediateEmotion(),
                   specific = emotionHighlight.getSpecificEmotion();
            if (global != null) {
                startTag
                        .append(" data-global='")
                        .append(global.toLowerCase())
                        .append("'");

                emotionLabels.add(getEmotionLabel(global.toLowerCase(), null));

                if (intermediate != null) {
                    startTag
                            .append(" data-intermediate='")
                            .append(intermediate.toLowerCase())
                            .append("'");

                    emotionLabels.add(getEmotionLabel(
                            intermediate.toLowerCase(),
                            new String[]{ global.toLowerCase() }));

                    if (specific != null) {
                        startTag
                                .append(" data-specific='")
                                .append(specific.toLowerCase())
                                .append("'");

                        emotionLabels.add(getEmotionLabel(
                                specific.toLowerCase(),
                                new String[]{
                                        global.toLowerCase(),
                                        intermediate.toLowerCase()
                                }));
                    }
                }
            }

            startTag
                    .append(" data-preview='")
                    .append(String.join(" > ", emotionLabels))
                    .append("'");

            startTag.append('>');

            int startIndex = emotionHighlight.getStart(),
                endIndex = startIndex + emotionHighlight.getSize() - 1,
                actualLine = emotionHighlight.getLine();

            htmlChars.get(actualLine)
                    .set(startIndex, startTag.toString() + htmlChars.get(actualLine).get(startIndex));
            htmlChars.get(actualLine)
                    .set(endIndex, htmlChars.get(actualLine).get(endIndex) + "</mark>");
        }

        List<String> lines = htmlChars.stream()
                .map(ln -> String.join("", ln))
                .collect(Collectors.toList());

        editor.setText(String.join("\n", lines));
    }

    public List<EmotionHighlight> highlights() {

        return getHighlights().getAsList();
    }

    private native String getEmotionLabel(String emotion, String[] parents) /*-{

        if (!parents) {
            parents = [];
        }

        var levels = parents.slice();
        levels.push(emotion);

        var emotionObj = this.@pt.up.hs.emotioneditor.EmotionEditor::emotionHierarchy;
        for (var i = 0; i < levels.length; i++) {
            var emotionKey = levels[i];
            emotionObj = emotionObj.emotions[emotionKey];
        }

        return emotionObj.label ||
            this.@pt.up.hs.emotioneditor.EmotionEditor::toTitle(Ljava/lang/String;)(emotion);
    }-*/;

    private String toTitle(String str) {

         str = str.replaceAll("_", " ");

         String[] words = str.split(" ");
         for (int i = 0; i < words.length; i++)
             words[i] = uppercaseFirst(words[i]);

         return String.join(" ", words);
    }

    private String uppercaseFirst(String src) {
        return src.substring(0, 1).toUpperCase() + src.substring(1).toLowerCase();
    }

    private native JsArray<EmotionHighlight> getHighlights() /*-{

        var editor = this.@pt.up.hs.emotioneditor.EmotionEditor::editorElement;

        var highlights = [];

        var prevSiblings = function (target, tag) {
            var siblings = [], n = target;
            while(n = n.previousElementSibling) {
                if (n.tagName.toLowerCase() === tag)
                    siblings.push(n);
            }
            return siblings;
        };

        var line = 0;
        var ps = editor.querySelectorAll('p');
        for (var i = 0; i < ps.length; i++) {

            var marks = ps[i].querySelectorAll('mark');
            var pHTML = ps[i].innerHTML;
            var offset = 0;
            for (var j = 0; j < marks.length; j++) {

                var brs = prevSiblings(marks[j], "br").length;

                var index = pHTML.indexOf(marks[j].outerHTML) - brs * 4;
                var global = marks[j].getAttribute('data-global');
                var intermediate = marks[j].getAttribute('data-intermediate');
                var specific = marks[j].getAttribute('data-specific');

                highlights.push({
                    line: line + brs,
                    start: offset + index,
                    size: marks[j].innerHTML.length,
                    globalEmotion: global,
                    intermediateEmotion: intermediate,
                    specificEmotion: specific
                });

                pHTML = pHTML.slice(index + marks[j].outerHTML.length);
                offset += index + marks[j].innerHTML.length;
            }

            line += ps[i].querySelectorAll('br').length + 2;
        }
        
        return highlights;
    }-*/;
}
