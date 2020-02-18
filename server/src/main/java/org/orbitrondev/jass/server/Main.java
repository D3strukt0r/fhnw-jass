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
import org.orbitrondev.jass.lib.ServiceLocator.ServiceLocator;
import org.orbitrondev.jass.server.Utils.DatabaseUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code is licensed under the terms of the BSD
 * 3-clause license (see the file license.txt).
 *
 * @author Brad Richards
 * @author Manuele Vaccari
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static String dbLocation = "jass_server.sqlite3";

    public static void main(String[] args) {
        // Create all arguments for the command line interface
        Options options = new Options();
        options
            .addOption(Option.builder("p").longOpt("port").desc("Defines the port to use").required().hasArg().build())
            .addOption(Option.builder("l").longOpt("db-location").desc("Define where the database is saved").hasArg().build())
            .addOption(Option.builder("v").longOpt("verbose").desc("Show more extensive logs").hasArg(false).build())
            .addOption(Option.builder("s").longOpt("ssl").desc("Accept secure connections").hasArg(false).build());

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            // When using an unknown argument
            System.out.println(e.getMessage()); // Prints "Unrecognized option: ..."
            formatter.printHelp("server.jar", options);
            return;
        }

        // Check for the ports argument
        int port;
        try {
            port = Integer.parseInt(cmd.getOptionValue("port"));
        } catch (NumberFormatException e) {
            logger.fatal("The value you used for port is not an integer");
            return;
        }

        // Check if the user wants to use a different location for the database
        if (cmd.hasOption("db-location")) {
            dbLocation = cmd.getOptionValue("db-location");
        }
        try {
            DatabaseUtil db = new DatabaseUtil(dbLocation);
            ServiceLocator.add(db);
        } catch (SQLException e) {
            logger.fatal("Error creating connection to database");
            return;
        }

        // Check if the user wants more output in the console
        if (cmd.hasOption("verbose")) {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            ctx.getConfiguration().getRootLogger().setLevel(Level.DEBUG);
            ctx.updateLoggers();
        }

        // Check if the user wants to use ssl
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
