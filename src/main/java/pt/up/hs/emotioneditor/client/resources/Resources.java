package pt.up.hs.emotioneditor.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;

/**
 * Resources of the highlight text editor
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public interface Resources extends ClientBundle {
    Resources INSTANCE = GWT.create(Resources.class);

    interface Style extends CssResource {

        @ClassName("editable-container")
        String editableContainer();
    }

    @Source("css/style.gss")
    @CssResource.NotStrict
    Style style();

    /* JSON configs */
    @Source("emotions.json")
    TextResource emotionsJson();
}
