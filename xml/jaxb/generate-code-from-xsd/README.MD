# Maven set-up to generate Java code from an XSD schema

This project shows how to configure the [maven-jaxb2-plugin](https://github.com/highsource/maven-jaxb2-plugin) to generate JAXB code, enhanced with the [jaxb2-basics](https://github.com/highsource/jaxb2-basics) to get basic `Object` overrides (`hashCode`, `equals` and `toString`). There is also a small JUnit test included, which verifies that the enhancements to plain JAXB code is present.

## To generate, compiler and check the generated code
```sh
$ mvn test
```

## TODO:
* It would be nice to get some sort of `Builder` pattern generated, for classes with a huge number of properties/ constructor arguments.
* Immutability: option of generating immutable pojos.

