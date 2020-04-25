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

package jass.server;

import jass.server.util.CardUtil;
import jass.server.util.DatabaseUtil;
import jass.server.util.SearchGameUtil;
import jass.server.util.ServerSocketUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import jass.lib.servicelocator.ServiceLocator;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;

/**
 * The main class for the server application.
 *
 * @author Manuele Vaccari
 * @version %I%, %G%
 * @since 0.0.1
 */
public final class Main {
    /**
     * The logger to print to console and save in a .log file.
     */
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * The location of the database storage.
     */
    public static String dbLocation = "data/jass_server.sqlite3";

    /**
     * The default port of the server.
     */
    public static final int DEFAULT_PORT = 2000;

    /**
     * Utility classes, which are collections of static members, are not meant
     * to be instantiated.
     */
    private Main() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param args The arguments passed in the console.
     */
    public static void main(final String[] args) {
        // Create all arguments for the command line interface
        Options options = new Options();
        options
            .addOption(Option.builder("p").longOpt("port").desc("Defines the port to use").hasArg().build())
            .addOption(Option.builder("l").longOpt("db-location").desc("Define where the database is saved").hasArg().build())
            .addOption(Option.builder("v").longOpt("verbose").desc("Show more extensive logs").hasArg(false).build())
            .addOption(Option.builder("s").longOpt("ssl").desc("Accept secure connections").hasArg(false).build());

        // Check the arguments validity
        CommandLine cmd;
        try {
            cmd = (new DefaultParser()).parse(options, args);
        } catch (ParseException e) {
            // When using an unknown argument
            System.out.println(e.getMessage()); // Prints "Unrecognized option: ..."
            (new HelpFormatter()).printHelp("server.jar", options);
            return;
        }

        // Check for the ports argument
        int port = DEFAULT_PORT;
        if (cmd.hasOption("port")) {
            try {
                port = Integer.parseInt(cmd.getOptionValue("port"));
            } catch (NumberFormatException e) {
                logger.fatal("The value you used for port is not an integer");
                return;
            }
        }
        logger.info("Using port " + port);

        // Check if the user wants to use a different location for the database
        if (cmd.hasOption("db-location")) {
            dbLocation = cmd.getOptionValue("db-location");
        }
        try {
            DatabaseUtil db = new DatabaseUtil(dbLocation);
            ServiceLocator.add(db);
            logger.info("Connection to database created");
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
        logger.info("SSL is " + (secure ? "enabled" : "disabled"));

        // Initialize Other Util Classes and add to ServiceLocator
        SearchGameUtil sGU = new SearchGameUtil();
        ServiceLocator.add(sGU);

        CardUtil cardUtil = new CardUtil();
        ServiceLocator.add(cardUtil);

        // Start the listener
        try {
            (new ServerSocketUtil(port, secure)).start();
        } catch (IOException e) {
            if (secure && e.getCause() instanceof GeneralSecurityException) {
                logger.fatal("Error creating secure socket connection - does the keystore exist?");
            }
            logger.info(e.toString());
        } catch (CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
            logger.fatal("Error creating secure socket connection - does the keystore exist?");
        }
    }
}
