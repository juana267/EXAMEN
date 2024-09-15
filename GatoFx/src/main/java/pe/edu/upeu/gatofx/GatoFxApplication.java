package pe.edu.upeu.gatofx;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GatoFxApplication extends Application {
    ConfigurableApplicationContext configurableApplicationContext;
    Parent parent;

    public static void main(String[] args) {
        System.out.println("Hola estas funcionando");
        launch(args);
    }

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(GatoFxApplication.class);
        builder.application().setWebApplicationType(WebApplicationType.NONE);
        configurableApplicationContext = builder.run(getParameters().getRaw().toArray(new String[0]));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gato.fxml"));
        fxmlLoader.setControllerFactory(configurableApplicationContext::getBean);
        parent = fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        Scene scene = new Scene(parent);
        double preferredWidth = parent.prefWidth(-1);
        double preferredHeight = parent.prefHeight(-1);
        stage.setMinWidth(preferredWidth);
        stage.setMinHeight(preferredHeight);

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setTitle("Tic-Tac-Toe");
        stage.show();
    }
}
