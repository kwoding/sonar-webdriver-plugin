# Sonar WebDriver Plugin

Build status: [![build_status](https://travis-ci.org/kwoding/sonar-webdriver-plugin.svg?branch=master)](https://travis-ci.org/kwoding/sonar-webdriver-plugin)

## Description
The Sonar WebDriver Plugin is a static code analysis tool that helps following best practices for writing WebDriver (Selenium or Appium) tests.

## Build and run
1. Build
```
mvn clean package
```
2. Copy `sonar-webdriver-plugin-<version>.jar` to `/path/to/sonarqube/extensions/plugins/` directory of your SonarQube server
3. (Re)start SonarQube

## Scan both main and test sources
Scan code in both `src/main/java` and `src/test/java` in the same Sonar project:
```
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<a valid token> -Dsonar.sources=src -Dsonar.test.inclusions=src/test/java
```
## Scan main and test sources separately
Recommended: Scan `src/test/java` code in a separate Sonar project, because metrics like code coverage get thrown off when scanning test code alongside application code (as Sonar will report 0% coverage on all the test classes themselves). To scan separately:

For application code (src/main/java), run:
```
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<a valid token>
```

For test code (src/test/java), run:
```
mvn sonar:sonar -Dsonar.projectKey=<my project>-tests -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<a valid token> -Dsonar.sources=src/test -Dsonar.test.inclusions=src/test/java
```
