# fhnw-jass

The IT-Project in the FHNW

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

* [Oracle JDK 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
* [JetBrains IntelliJ IDEA](https://www.jetbrains.com/de-de/idea/) (Preferred)

### Installing

Get the project (through the console):
```shell script
$ git clone https://github.com/D3strukt0r/fhnw-jass.git
```
In case you want to change to the develop branch for the latest and greatest:
```shell script
$ git checkout develop
```

Now import the project to IntelliJ (from the start screen):
1. `Import Project`
2. Select the directory of the downloaded project
3. `[OK]`
4. `(o) Import project from external model`
5. `Gradle`
6. `[Finish]`
In the popup that appears on the bottom right:
7. `Import Gradle Project`

(On Windows) If a message appears concerning "Windows Defender might be impacting your build performance":
8. `Fix...` -> `Configure Automatically`

## Running the tests

Through the terminal
```shell script
$ ./gradlew :lib:check
$ ./gradlew :client:check
$ ./gradlew :server:check
```

Through IntelliJ
1. Click on the "Gradle" Tab on the top right
2. Go to: `fhnw-jass` -> `client` (or `server`) -> `Tasks` -> `verification` -> `check`. And double-click.

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

### Docker

This project uses Docker for easy deployment.

Therefore you can use a `docker-compose.yml` file and then run `docker-compose up -d`
```yaml
version: '2'

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

### `.jar` file

Download the jar from the releases page and enter `java -jar server.jar` in the command line

## Built With

* [Java](https://www.java.com/de/) - Programming Language
* [Gradle](https://gradle.org/) - Dependency Management
* [JUnit5](https://junit.org/junit5/) - Testing the Java Code
* [SQLite3](https://www.sqlite.org/index.html) - Data Storage
* [ORMLite](http://ormlite.com/) - ORM for Data Storage
* [JSON](https://www.json.org/json-en.html) - Data Model for Transmission between Server <-> Client
* [Apache Commons Cli](https://commons.apache.org/proper/commons-cli/) - Handle CLI arguments
* [Apache Log4J](https://logging.apache.org/log4j/2.x/) - Handle logging uniquely
* [JFoenix](http://www.jfoenix.com/) - Theme for the GUI
* [Travis CI](https://travis-ci.com/) - Automatic CI (Testing) / CD (Deployment)
* [Docker](https://www.docker.com/) - Building a Container for the Server

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/D3strukt0r/fhnw-jass/tags). 

## Authors

* **Manuele Vaccari** - [D3strukt0r](https://github.com/D3strukt0r) - *Initial work, Basic Client and Server App (Server connection, Login, Register), JSON Messaging, CI/CD setup*
* **Victor Hargrave** - [jokerengine](https://github.com/jokerengine)
* **Sasa Trajkova** - [sasatrajkova](https://github.com/sasatrajkova)
* **Thomas Weber** - [tjw52](https://github.com/tjw52)

See also the list of [contributors](https://github.com/D3strukt0r/fhnw-jass/contributors) who participated in this project.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.txt](LICENSE.txt) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
