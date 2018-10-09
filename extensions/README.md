# Extension Parent Module

This is the parent module for extensions and customizations to the headless server.
All extensions are included in the build process but by default not added to the built *Spring Boot Application*/*WAR File*.

To add an extension to the application enabled it's dependency in the `extensions/extensions-bom/pom.xml` file. 