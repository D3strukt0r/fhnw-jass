package org.orbitrondev.jass.server;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

public class Main {
    public static String dbLocation = "chat.sqlite3";

    public static void main(String[] args) {
        // Create all arguments for the command line interface
        Options options = new Options();

        Option dbOption = new Option("p", "port", true, "Defines the port to use");
        options.addOption(dbOption);

        Option dbLocationOption = new Option("l", "db-location", true, "Define where the database is saved");
        dbLocationOption.setOptionalArg(true);
        options.addOption(dbLocationOption);

        Option verboseOption = new Option("v", "verbose", true, "Show more extensive logs");
        verboseOption.setOptionalArg(true);
        options.addOption(verboseOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("server.jar", options);

            System.exit(1);
        }
        if (cmd == null) {
            System.exit(1);
        }

        // Do the arguments' task
        if (cmd.hasOption("db-location")) {
            dbLocation = cmd.getOptionValue("db-location");
        }
        if (cmd.hasOption("verbose")) {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            final Configuration config = ctx.getConfiguration();
            config.getRootLogger().addAppender(config.getAppender("Console"), Level.INFO, null);
            ctx.updateLoggers();
        }

        // TODO: Start server
    }
}
