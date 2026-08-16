# CLAUDE.md

Guidance for Claude Code when working in this repository.

## Repository overview

Independent Java sample projects, each demonstrating one technique (a design pattern, an XML API, servlet scheduling, logging setup, etc.). No shared code between modules — each is self-contained with its own README explaining purpose and usage. Read that module's README before working on it.

The root `pom.xml` is a Maven aggregator (`packaging=pom`); its `<modules>` list is the authoritative index of active modules:
- `patterns/simple-factory` — Factory Method GOF pattern
- `servlet/simple` — minimal HTTP servlet (health check), runs in embedded Jetty or Tomcat
- `servlet/simple-schedule-configurer` — stateless scheduling via `java.util.Timer` in a `ServletContextListener`
- `testing/logback-logging-to-in-memory-stream` — reconfiguring Logback to capture logs in-memory for tests
- `xml/jaxb/generate-code-from-xsd` — generating Java from XSD via `maven-jaxb2-plugin` + `jaxb2-basics`
- `xml/schema-validation` — validating XML against XSD with `javax.xml.validation`
- `xml/testing/comparing-with-xmlunit` — comparing XML with XMLUnit 1.x
- `xml/xpath-query` — evaluating XPath expressions with `javax.xml.xpath`

Dependency versions (JUnit, Mockito, SLF4J/Logback, JAXB, servlet-api) are centralized in the root POM's `<dependencyManagement>`; module POMs should inherit these rather than pinning their own.

## Common commands

Build/test everything:
```sh
mvn verify
```

Build/test one module (from repo root, or `cd` into the module and run `mvn verify` directly):
```sh
mvn -pl <module-path> -am verify
```

Static analysis, matching CI (`.github/workflows/build-with-maven.yml`):
```sh
mvn checkstyle:checkstyle -Dcheckstyle.config.location=google_checks.xml -Dcheckstyle.suppressions.location=./checkstyle-suppressions.xml
mvn pmd:check pmd:cpd -DlinkXRef=false
mvn com.github.spotbugs:spotbugs-maven-plugin:4.9.8.2:spotbugs -Dspotbugs.xmlOutput=true -Dspotbugs.effort=max -Dspotbugs.threshold=low -Dspotbugs.failOnError=true
```
Checkstyle uses Google's rules; repo-wide suppressions (indentation, star imports, some Javadoc checks) are in `checkstyle-suppressions.xml`.

`servlet/simple-schedule-configurer` and `xml/testing/comparing-with-xmlunit` also support Gradle (`gradle test`).

Module-specific run instructions (e.g. launching `servlet/simple`'s server, or running `XPathEvaluator`/`XmlValidator` as CLI tools) live in each module's README.

## CI

GitHub Actions (`.github/workflows/build-with-maven.yml`) is the active CI: `mvn verify` plus separate SpotBugs, Checkstyle, and PMD jobs on every push. `circle.yml` (CircleCI) and `Jenkinsfile` are legacy and not actively used.
