package org.orbitrondev.jass.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.orbitrondev.jass.client.Controller.SplashController;
import org.orbitrondev.jass.client.Model.SplashModel;
import org.orbitrondev.jass.client.View.SplashView;

public class Main extends Application {
    public static String dbLocation = "jass.sqlite3";

    public static void main(String[] args) {
        // Create all arguments for the command line interface
        Options options = new Options();
        options
            .addOption(Option.builder("l").longOpt("db-location").desc("Define where the database is saved").hasArg().build())
            .addOption(Option.builder("v").longOpt("verbose").desc("Show more extensive logs").hasArg(false).build());

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            // When using an unknown argument
            System.out.println(e.getMessage()); // Prints "Unrecognized option: ..."
            formatter.printHelp("client.jar", options);
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
        SplashModel splashModel = new SplashModel();
        SplashView splashView = new SplashView(primaryStage, splashModel);
        new SplashController(splashModel, splashView);
        splashView.start();
        splashModel.initialize();
    }
}
