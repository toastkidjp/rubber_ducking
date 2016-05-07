package jp.toastkid.gui.jfx.rubberducking;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Noodle timer.
 * @author Toast kid
 *
 */
public class Main extends Application {

    /** Application title. */
    private static final String TITLE     = "Rubber ducking";

    /** fxml ファイル. */
    private static final String FXML_PATH = "rubber_ducking.fxml";

    /** Stage. */
    private Stage stage;

    @Override
    public void start(final Stage arg0) throws Exception {
        stage = new Stage(StageStyle.DECORATED);
        final Image image = new Image(
                getClass().getClassLoader().getResourceAsStream(Resources.ICON));

        try {
            final FXMLLoader loader
                = new FXMLLoader(getClass().getClassLoader().getResource(FXML_PATH));
            final Parent load = loader.load();
            final Scene scene = new Scene(load);
            stage.setScene(scene);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        Application.launch(Main.class);
    }
}
