package pt.up.hs.emotioneditor.client;

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
import pt.up.hs.emotioneditor.client.models.EmotionHierarchy;
import pt.up.hs.emotioneditor.client.models.Highlight;
import pt.up.hs.emotioneditor.client.models.JsArray;
import pt.up.hs.emotioneditor.client.models.JsMap;
import pt.up.hs.emotioneditor.client.resources.Resources;

import java.util.ArrayList;
import java.util.List;

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

        editorOptions.set("emotionHighlighter",
                JsonUtils.safeEval("{ \"emotions\": " + Resources.INSTANCE.emotionsJson().getText() + " }"));

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

    public void highlight(List<Highlight> highlights) {

        String text = editor.getText() + " ";

        String[] chars = text.split("");
        for (Highlight highlight : highlights) {
            StringBuilder startTag = new StringBuilder();
            startTag.append("<mark class='emotion-highlight'");

            JsMap props = highlight.getProps();
            for (String key: props.keySet()) {
                String value = props.get(key);
                if (value != null) {
                    startTag
                            .append(" data-")
                            .append(key)
                            .append("='")
                            .append(value)
                            .append("'");
                }
            }

            startTag.append('>');

            int startIndex = highlight.getStart() + 1,
                    endIndex = startIndex + highlight.getSize() - 1;

            chars[startIndex] = startTag.toString() + chars[startIndex];
            chars[endIndex] = chars[endIndex] + "</mark>";
        }

        editor.setText(String.join("", chars));
    }

    public List<Highlight> highlights() {

        return getHighlights().getAsList();
    }

    private native String getEmotionLabel(String emotion, String[] parents) /*-{

        if (!parents) {
            parents = [];
        }

        var levels = parents.slice();
        levels.push(emotion);

        var emotionObj = this.@pt.up.hs.emotioneditor.client.EmotionEditor::emotionHierarchy;
        for (var i = 0; i < levels.length; i++) {
            var emotionKey = levels[i];
            emotionObj = emotionObj.emotions[emotionKey];
        }

        return emotionObj.label ||
            this.@pt.up.hs.emotioneditor.client.EmotionEditor::toTitle(Ljava/lang/String;)(emotion);
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

    private native JsArray<Highlight> getHighlights() /*-{

        var editor = this.@pt.up.hs.emotioneditor.client.EmotionEditor::editorElement;

        var highlights = [];

        var offset = 0;
        var ps = editor.getElementsByTagName('p');
        for (var i = 0; i < ps.length; i++) {
            var html = ps[i].innerHTML;

            var tags;
            try { // direct childs only
                tags = ps[i].querySelectorAll(':scope > *');
            } catch (e) { // whatever, cross your fingers
                tags = ps[i].querySelectorAll('*');
            }

            for (var j = 0; j < tags.length; j++) {
                var index = html.indexOf(tags[j].outerHTML);
                html = html.slice(index);
                offset += index;

                var tagName = tags[j].tagName.toLowerCase();
                if (tagName === 'mark')  {

                    var data = {};
                    [].forEach.call(tags[j].attributes, function(attr) {
                        if (/^data-/.test(attr.name)) {
                            var name = attr.name.substr(5).toLowerCase();
                            data[name] = attr.value;
                        }
                    });

                    highlights.push({
                        start: offset,
                        size: tags[j].innerHTML.length,
                        props: data
                    });

                    offset += tags[j].innerText.length;
                } else if (tagName === 'br') {
                    offset += 1;
                } else {
                    // do nothing
                    offset += tags[j].innerText.length;
                }

                html = html.slice(tags[j].outerHTML.length);
            }

            offset += html.length + 2;
        }

        return highlights;
    }-*/;
}
