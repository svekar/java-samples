# Configure Maven to generate Java from an XSD schema

This project shows how to configure the [jaxb-maven-plugin](https://github.com/highsource/jaxb-tools) (the Jakarta-namespace continuation of the old `maven-jaxb2-plugin`) to generate JAXB code, enhanced with [jaxb-plugins](https://github.com/highsource/jaxb-tools) (formerly `jaxb2-basics`) to get basic `Object` behavior (`hashCode`, `equals` and `toString`), and fluent builders. There is also a small JUnit test included, which verifies that the Object enhancements to plain JAXB code is present.

## To generate, compiler and check the generated code
```sh
$ mvn test
```

