package jass.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import jass.client.utils.WindowUtil;

/**
 * The main class for the client application.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public class Main extends Application {
    public static String dbLocation = "data/jass.sqlite3";

    public static void main(String[] args) {
        // Create all arguments for the command line interface
        Options options = new Options();
        options
            .addOption(Option.builder("l").longOpt("db-location").desc("Define where the database is saved").hasArg().build())
            .addOption(Option.builder("v").longOpt("verbose").desc("Show more extensive logs").hasArg(false).build());

        // Check the arguments validity
        CommandLine cmd;
        try {
            cmd = (new DefaultParser()).parse(options, args);
        } catch (ParseException e) {
            // When using an unknown argument
            System.out.println(e.getMessage()); // Prints "Unrecognized option: ..."
            (new HelpFormatter()).printHelp("client.jar", options);
            return;
        }

        // Check if the user wants to use a different location for the database
        if (cmd.hasOption("db-location")) {
            dbLocation = cmd.getOptionValue("db-location");
        }

        // Check if the user wants more output in the console
        if (cmd.hasOption("verbose")) {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            ctx.getConfiguration().getRootLogger().setLevel(Level.DEBUG);
            ctx.updateLoggers();
        }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WindowUtil.switchToSplashScreen();
    }
}
