![Status: Active](https://documentation.coremedia.com/badges/badge_status_active.png "Status: Active")
![For CoreMedia CMS](https://documentation.coremedia.com/badges/badge_coremedia_cms.png "For CoreMedia CMS")
![Tested: 9.1707.1](https://documentation.coremedia.com/badges/badge_tested_coremedia_9-1707-1.png "Tested: 9.1707.1")

![CoreMedia Labs Logo](https://documentation.coremedia.com/badges/banner_coremedia_labs_wide.png "CoreMedia Labs Logo Title Text")


# CoreMedia Content as a Service (CaaS)

This open-source workspace is the backend side of the CoreMedia Content as a Service (CaaS) experience.

## Overview

CoreMedia CaaS builds on the proven CoreMedia Unified API and leverages modern technologies (Spring Boot, REST, JSON, GraphQL) to deliver CoreMedia content and services to a broad range of applications.
Integrate ready-to-go content fragments and new dynamic client-side modules into your (non CMS) webpages, build Single-page applications using your favorite framework and seamlessly integrate and reuse content from your CoreMedia CMS or syndicate/publish content using the headless API.


## Workspace Structure

The workspace is comprised of the following modules:
* **headless-server-lib**: The core library module
* **headless-server-app**: The Spring Boot Application
* **headless-server-webapp**: The Spring Boot Application repacked as WAR
* **headless-schema-generator**: The Generator Application for creating a schema definition from the CoreMedia Doctype Model
* **headless-pd**: Parent module for different processing descriptions (schema/queries etc.)
* **headless-performance-test**: Simple local performance test using a list of URLs
* **test-data**: Test content for sample client applications. Must be manually imported into the CoreMedia repository. 


## Building an running the server

Build the workspace with
    
    mvn clean install -Dinstallation.server.host=<CMS-SERVER-HOSTNAME> -Dinstallation.server.port=<CMS-SERVER-PORT>
    
or specify a global profile defining the required properties `installation.server.host` and `installation.server.port`.

Add the profile 

    -P performance-test
    
if you want to run the performance tests while building the workspace.

Run the server with

    mvn spring-boot:run -pl headless-server-app -Dinstallation.server.host=<CMS-SERVER-HOSTNAME> -Dinstallation.server.port=<CMS-SERVER-PORT>

or start the Tomcat Webapp

    mvn tomcat7:run -pl headless-server-webapp -Dinstallation.server.host=<CMS-SERVER-HOSTNAME> -Dinstallation.server.port=<CMS-SERVER-PORT>


*******


# CoreMedia Labs

Welcome to CoreMedia Labs! This repository is part of a platform for developers who want to have a look under the hood or get some hands-on understanding of the vast and compelling capabilities of CoreMedia. Whatever your experience level with CoreMedia is, we've got something for you.

Each project in our Labs platform is an extra feature to be used with CoreMedia, including extensions, tools and 3rd party integrations. We provide some test data and explanatory videos for non-customers and for insiders there is open-source code and instructions on integrating the feature into your CoreMedia workspace. 

The code we provide is meant to be example code, illustrating a set of features that could be used to enhance your CoreMedia experience. We'd love to hear your feedback on use-cases and further developments! If you're having problems with our code, please refer to our issues section. 
