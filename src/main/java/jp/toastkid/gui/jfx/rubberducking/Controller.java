package jp.toastkid.gui.jfx.rubberducking;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * Controller.
 * @author Toast kid
 *
 */
public class Controller implements Initializable {

    /** duck icon. */
    private static final String DUCK_ICON = findFile(Resources.ICON);

    /** default icon. */
    private static final String YOUR_ICON = findFile(Resources.DEFAULT);

    /** key combination for posting. */
    private static final KeyCodeCombination KEY_CODE_COMBINATION
        = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);

    /** response texts. */
    private final List<String> responses;

    /** random integer generator. */
    private static final Random RANDOM = new Random();

    /** contents container. */
    @FXML
    private Pane contents;

    /** image. */
    @FXML
    private ImageView image;

    /** inout. */
    @FXML
    private TextArea text;

    /**
     * init with responses.
     * @throws URISyntaxException
     * @throws IOException.
     */
    public Controller() throws IOException, URISyntaxException {

        responses = Files.readAllLines(Files.exists(Paths.get(Resources.RESPON1SES))
                ? Paths.get(Resources.RESPON1SES)
                : Paths.get(getClass().getClassLoader().getResource(Resources.RESPON1SES).toURI())
                );
    }

    /**
     * store messages to text file.
     * .
     */
    @FXML
    private void save() {
        final StringBuilder textContent = new StringBuilder();
        contents.getChildren()
            .forEach(node -> {
                textContent.append((((Label)((HBox) node).getChildren().get(1))).getText());
                });
        try {
            Files.write(Paths.get("rubber_duck_" + System.currentTimeMillis() + ".txt"),
                    textContent.toString().getBytes());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * close this app.
     */
    @FXML
    private void post() {
        if (text.getText().length() == 0) {
            return;
        }
        addMessage("You: " + text.getText());
        try {
            Thread.sleep(500L);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        addMessage("Duck: " + selectMessage(), DUCK_ICON);
    }

    /**
     * select response message.
     * @return. selected message.
     */
    private String selectMessage() {
        return responses.get(Math.abs(RANDOM.nextInt(responses.size())));
    }

    /**
     * add message with default avatar.
     * @param text.
     */
    private void addMessage(final String text) {
        addMessage(text, YOUR_ICON);
    }

    /**
     * add message with default avatar.
     * @param text
     * @param avator.
     */
    private void addMessage(final String text, final String iconUrl) {
        final Label label = new Label(text + System.lineSeparator());
        label.setFont(new Font("System Bold", 12.0));
        final ImageView image = new ImageView(iconUrl);
        contents.getChildren().add(new HBox(image, label));
    }

    /**
     * find icon's url.
     * @param path path to icon
     * @return. icon's url
     */
    private static String findFile(final String path) {
        final File file = new File(path);
        return file.exists()
                ? file.toURI().toString()
                : Controller.class.getClassLoader().getResource(path).toString();
    }

    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {
        text.setOnKeyPressed(event -> {
            if (KEY_CODE_COMBINATION.match(event)) {
                post();
            }
        });
        image.setImage(new Image(DUCK_ICON));
    }
}
