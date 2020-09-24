# fhnw-jass

As part of the IT-Project course in the FHNW Basel, four students, which are mentioned in the authors section of this document, created this game.

Project

[![License](https://img.shields.io/github/license/d3strukt0r/fhnw-jass)][license]
[![Docker Stars](https://img.shields.io/docker/stars/d3strukt0r/fhnw-jass-server.svg)][docker]
[![Docker Pulls](https://img.shields.io/docker/pulls/d3strukt0r/fhnw-jass-server.svg)][docker]

master-branch (alias stable, latest)

[![GH Action CI/CD](https://github.com/D3strukt0r/fhnw-jass/workflows/CI/CD/badge.svg?branch=master)][gh-action]
[![Codacy grade](https://img.shields.io/codacy/grade/a5ffb2e6c88a4345aba88b5ff948fe88/master)][codacy]

develop-branch (alias nightly)

[![GH Action CI/CD](https://github.com/D3strukt0r/fhnw-jass/workflows/CI/CD/badge.svg?branch=develop)][gh-action]
[![Codacy grade](https://img.shields.io/codacy/grade/a5ffb2e6c88a4345aba88b5ff948fe88/develop)][codacy]

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

#### User environment prerequisites

-   [Oracle JRE 8 (Java 8)](https://www.java.com/de/)

#### Developer environment prerequisites

-   [Oracle JDK 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
-   [JetBrains IntelliJ IDEA](https://www.jetbrains.com/de-de/idea/) (Preferred)

### Installing (the client)

#### User environment installation

After having installed Java 8, you have to download the latest client software under the releases page, or click on this [link](https://github.com/D3strukt0r/fhnw-jass/releases/latest/download/jass.zip).

The package you just downloaded needs to be unpacked now.

Go to `jass/bin/` and in there you will find two files. If you are on a linux system double-click `client` otherwise (Windows) double-click on `client.bat`.

#### Developer environment installation

Get the project (through the console):

```shell
git clone https://github.com/D3strukt0r/fhnw-jass.git
```

In case you want to change to the `develop` branch for the latest and greatest:

```shell
git checkout develop
```

Now import the project to IntelliJ (from the start screen):

1.  `Import Project`
2.  Select the directory of the downloaded project
3.  `[OK]`
4.  `(o) Import project from external model`
5.  `Gradle`
6.  `[Finish]`

In the popup that appears on the bottom right:

7.  `Import Gradle Project`

(On Windows) If a message appears concerning "Windows Defender might be impacting your build performance":

8.  `Fix...` -> `Configure Automatically`

## Running the tests

Through the terminal

```shell
./gradlew :lib:check
./gradlew :client:check
./gradlew :server:check
```

Through IntelliJ

1.  Click on the "Gradle" Tab on the top right
2.  Go to: `fhnw-jass` -> `client` (or `server`) -> `Tasks` -> `verification` -> `check` and double-click.

## Deployment (of the server)

### Docker

This project uses Docker for easy deployment of the server(!).

Therefore, you can use a `docker-compose.yml` file and then run `docker-compose up -d`

```yaml
version: "2"

services:
  jass:
    image: d3strukt0r/fhnw-jass
    restart: on-failure
    command: --ssl --verbose
    ports:
      - 2000:2000
    volumes:
      - ./data:/app/data
```

Or just s simple command: `docker run -p 2000:2000 -v ./data:/app/data d3strukt0r/fhnw-jass`

For more example check the [wiki page](https://github.com/D3strukt0r/fhnw-jass/wiki/Example-docker-compose.yml)

### `.jar` file

After having installed Java 8, you have to download the latest server software under the releases page, or click on this [link](https://github.com/D3strukt0r/fhnw-jass/releases/latest/download/jass-server.zip) for the compressed `.zip` or [here](https://github.com/D3strukt0r/fhnw-jass/releases/latest/download/jass-server.jar) for only the `.jar`.

If you downloaded the package, you have to unpack it first.

Go to `jass-server/bin/` and in there you will find two files. If you are on a linux system double-click `server` otherwise (Windows) double-click on `server.bat`.

If you downloaded the jar file only:

Go to the console and enter enter `java -jar server.jar` in the command line.

## Built With

-   [Java](https://www.java.com/de/) - Programming Language
-   [Gradle](https://gradle.org/) - Dependency Management
-   [JUnit5](https://junit.org/junit5/) - Testing the Java Code
-   [SQLite3](https://www.sqlite.org/index.html) - Data Storage
-   [ORMLite](http://ormlite.com/) - ORM for Data Storage
-   [JSON](https://www.json.org/json-en.html) - Data Model for Transmission between Server <-> Client
-   [Apache Commons Cli](https://commons.apache.org/proper/commons-cli/) - Handle CLI arguments
-   [Apache Log4J](https://logging.apache.org/log4j/2.x/) - Handle logging uniquely
-   [JFoenix](http://www.jfoenix.com/) - Theme for the GUI
-   [Github Actions](https://github.com/features/actions) - Automatic CI (Testing) / CD (Deployment)
-   [Docker](https://www.docker.com/) - Building a Container for the Server

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/D3strukt0r/fhnw-jass/tags).

## Authors

-   **Manuele Vaccari** - [D3strukt0r](https://github.com/D3strukt0r) - _Initial work, Basic Client and Server App (Server connection, Login, Register, Change Password, Delete Account, Logout), JSON Messaging, CI/CD setup, Game configuration, Validate moves, Find winner_
-   **Victor Hargrave** - [jokerengine](https://github.com/jokerengine) - _Create a deck, Display deck, Make move, Round over_
-   **Sasa Trajkova** - [sasatrajkova](https://github.com/sasatrajkova) - _Lobby design, Game design_
-   **Thomas Weber** - [tjw52](https://github.com/tjw52) - _Game finder, Game creation, Initialize new game_

See also the list of [contributors](https://github.com/D3strukt0r/fhnw-jass/contributors) who participated in this project.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.txt](LICENSE.txt) file for details.

## Acknowledgments

-   Hat tip to anyone whose code was used
-   Inspiration
-   etc

[license]: https://github.com/D3strukt0r/fhnw-jass/blob/master/LICENSE.txt
[docker]: https://hub.docker.com/repository/docker/d3strukt0r/fhnw-jass-server
[gh-action]: https://github.com/D3strukt0r/fhnw-jass/actions
[codacy]: https://app.codacy.com/manual/D3strukt0r/fhnw-jass/dashboard
