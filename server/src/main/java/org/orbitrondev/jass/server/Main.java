/*
 * fhnw-jass is jass game programmed in java for a school project.
 * Copyright (C) 2020 Manuele Vaccari
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.orbitrondev.jass.server;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 *
 * @author Brad Richards
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static String dbLocation = "jass_server.sqlite3";

    public static void main(String[] args) {
        // Create all arguments for the command line interface
        Options options = new Options();

        Option dbOption = new Option("p", "port", true, "Defines the port to use");
        options.addOption(dbOption);

        Option dbLocationOption = new Option("l", "db-location", true, "Define where the database is saved");
        dbLocationOption.setOptionalArg(true);
        options.addOption(dbLocationOption);

        Option verboseOption = new Option("v", "verbose", false, "Show more extensive logs");
        verboseOption.setOptionalArg(true);
        options.addOption(verboseOption);

        Option secureOption = new Option("s", "ssl", false, "Accept secure connections");
        secureOption.setOptionalArg(true);
        options.addOption(secureOption);

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
        int port = 0;
        try {
            port = Integer.parseInt(cmd.getOptionValue("port"));
        } catch (NumberFormatException e) {
            logger.error("Port is not set or not a number (integer)");
            System.exit(1);
        }

        if (cmd.hasOption("db-location")) {
            dbLocation = cmd.getOptionValue("db-location");
        }
        try {
            DatabaseUtil db = new DatabaseUtil(dbLocation);
            ServiceLocator.add(db);
        } catch (SQLException e) {
            logger.fatal("Error creating connection to database");
            System.exit(1);
        }

        if (cmd.hasOption("verbose")) {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            final Configuration config = ctx.getConfiguration();
            config.getRootLogger().addAppender(config.getAppender("Console"), Level.INFO, null);
            ctx.updateLoggers();
        }

        boolean secure = cmd.hasOption("ssl");

        // Start the listener
        try {
            Listener lt = new Listener(port, secure);
            lt.start();
        } catch (IOException e) {
            if (secure && e.getCause() instanceof GeneralSecurityException) {
                logger.fatal("Error creating secure socket connection - does the keystore exist?");
            }
            logger.info(e.toString());
        }
    }
}
