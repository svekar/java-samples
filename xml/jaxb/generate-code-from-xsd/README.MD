# Configure Maven to generate Java from an XSD schema

This project shows how to configure the [maven-jaxb2-plugin](https://github.com/highsource/maven-jaxb2-plugin) to generate JAXB code, enhanced with the [jaxb2-basics](https://github.com/highsource/jaxb2-basics) to get basic `Object` behavior (`hashCode`, `equals` and `toString`), and fluent builders. There is also a small JUnit test included, which verifies that the Object enhancements to plain JAXB code is present.

## To generate, compiler and check the generated code
```sh
$ mvn test
```

