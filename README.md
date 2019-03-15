![Status: Active](https://documentation.coremedia.com/badges/badge_status_active.png "Status: Active")
![For CoreMedia CMS](https://documentation.coremedia.com/badges/badge_coremedia_cms.png "For CoreMedia CMS")
![Tested: 9.1807.1](https://documentation.coremedia.com/badges/badge_tested_coremedia_9-1807-1.png "Tested: 9.1807.1")

![CoreMedia Labs Logo](https://documentation.coremedia.com/badges/banner_coremedia_labs_wide.png "CoreMedia Labs Logo Title Text")

# This repository is not maintained anymore. The latest version is now released to https://github.com/coremedia-contributions/coremedia-headless-server-app (for CoreMedia Blueprint customers only).


# CoreMedia Content as a Service (CaaS)

This open-source workspace is the backend side of the CoreMedia Content as a Service (CaaS) experience.

## Overview

CoreMedia CaaS builds on the proven CoreMedia Unified API and leverages modern technologies (Spring Boot, REST, JSON, GraphQL) to deliver CoreMedia content and services to a broad range of applications.
Integrate ready-to-go content fragments and new dynamic client-side modules into your (non CMS) webpages, build Single-page applications using your favorite framework and seamlessly integrate and reuse content from your CoreMedia CMS or syndicate/publish content using the headless API.

This README contains an overview of the code and how to start the server. For more detailed documentation, see the [wiki](https://github.com/CoreMedia/coremedia-headless-server/wiki). 


## Versions in this Repository

* **master**: This is the release branch.
* **develop**: This is the branch for all current, unreleased work.


## Workspace Structure

The workspace is comprised of the following modules:
* **headless-server-lib**: The library module
* **headless-server-core**: The core application module
* **headless-server-doc**: The documentation module
* **headless-server-app**: The Spring Boot Application packaged as executable JAR
* **headless-server-webapp**: The Spring Boot Application packaged as WAR
* **headless-schema-generator**: The Generator Application for creating a schema definition from the CoreMedia Doctype Model
* **headless-pd**: Parent module for different processing descriptions (schema/queries etc.)
* **headless-performance-test**: Simple local performance test using a list of URLs
* **test-data**: Test content for sample client applications. Must be manually imported into the CoreMedia repository.
* **extensions**: Examples for extending/customizing the server. See the modules' `README.md` files for more details.


## Building and running the server

Build the workspace with
    
    mvn clean install -Dinstallation.server.host=<CMS-SERVER-HOSTNAME> -Dinstallation.server.port=<CMS-SERVER-PORT>
    
or specify a global profile defining the required properties `installation.server.host` and `installation.server.port`.

Add the profile 

    -P performance-test
    
if you want to run the performance tests while building the workspace. The default setup requires the test data to be imported into the
CoreMedia content repository.

Run the server with

    mvn spring-boot:run -pl headless-server-app -Dinstallation.server.host=<CMS-SERVER-HOSTNAME> -Dinstallation.server.port=<CMS-SERVER-PORT>

or start the Tomcat Webapp

    mvn cargo:run -pl headless-server-webapp -Dinstallation.server.host=<CMS-SERVER-HOSTNAME> -Dinstallation.server.port=<CMS-SERVER-PORT>

### Executable Jar

A fully executable jar, which can be executed as binary or registered with `init.d` or `systemd`, can be created by adding the profile

    -P executable-jar`
    
at build time. For more information see ["Installing Spring Boot Applications"](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html).


## Testing the API

### Using the test site and content

Import the test data into your CoreMedia content repository. The test data contains a new site for the sample React client which is already enabled for delivery
with the headless server.   
Run the headless server and try the following URLs:

    http://localhost:8080/caas/v1/coremedia/sites/caassiopeia-en-DE/navigation
    http://localhost:8080/caas/v1/coremedia/sites/caassiopeia-en-DE/teasables/home.hero
    http://localhost:8080/caas/v1/coremedia/sites/caassiopeia-en-DE/teasables/home.teaser-left
    http://localhost:8080/caas/v1/coremedia/sites/caassiopeia-en-DE/teasables/home.teaser-right
    http://localhost:8080/caas/v1/coremedia/sites/caassiopeia-en-DE/teasables/home.teaser-bottom
    http://localhost:8080/caas/v1/coremedia/sites/caassiopeia-en-DE/articles/caas.article

### Enable a site for use with the headless server

1. Log in to CoreMedia Studio and open the *Site Indicator* document of the site

2. Note the *ID* property on the content tab.
   The value will be used for the `{siteId}` placeholder in the REST URLs

3. Go to the system tab end expand the *Local Settings* property box.
   1. Add a *String* property to it's root: 

      *Property:* tenantId   
      *Value:* your tenant identifier (only alphanumerical characters/lowercase)   

      This value will be used for the `{tenantId}` placeholder in the REST URLs

   2. Add a *Struct* property to it's root:
   
      *Property:* caasClients
      
      Add a sub *Struct* property (empty UUID):
      
      *Property*: 00000000-0000-0000-0000-000000000000
      
      Add a *String* property to the sub struct:
      
      *Property:* pd   
      *value:* default
      
      This maps all 'unknown' clients to the processing definition named 'default'. See the [wiki](https://github.com/CoreMedia/coremedia-headless-server/wiki/Processing-Definitions) for more information.
      
4. Try to fetch the JSON description of the enabled site:
    
    `http://localhost:8080/caas/v1/{tenantId}/sites/{siteId}`
    
    A response similar to the following one should be returned:
    ```json
    {
    "__typename": "CMSiteImpl",
    "__baseinterface": "CMSite",
    "_id": "coremedia:///cap/content/5838",
    "_name": "CaaS [Site]",
    "_type": "CMSite",
    "id": "caassiopeia-en-DE",
    "name": "CaaS Fragment Demo Site",
    "languageId": "en-DE"
    }
    ```
    
5. Test your site with the Swagger UI.

### Swagger

Test the API with your browser by calling the embedded Swagger UI: `http://localhost:8080/swagger-ui.html`.

#### Static documentation

During the test phase of the Maven build process HTML and PDF documentation is generated from the Swagger Specification. It can be accessed
from the running server at:

    http://localhost:8080/docs/index.html
    http://localhost:8080/docs/index.pdf
    
A JSON representation of the Swagger Specification for import in third party tools, i.e. Postman, is available at:

    http://localhost:8080/docs/swagger.json


*******


# CoreMedia Labs

Welcome to [CoreMedia Labs](https://blog.coremedia.com/labs/)! This repository is part of a platform for developers who want to have a look under the hood or get some hands-on understanding of the vast and compelling capabilities of CoreMedia. Whatever your experience level with CoreMedia is, we've got something for you.

Each project in our Labs platform is an extra feature to be used with CoreMedia, including extensions, tools and 3rd party integrations. We provide some test data and explanatory videos for non-customers and for insiders there is open-source code and instructions on integrating the feature into your CoreMedia workspace. 

The code we provide is meant to be example code, illustrating a set of features that could be used to enhance your CoreMedia experience. We'd love to hear your feedback on use-cases and further developments! If you're having problems with our code, please refer to our issues section. 
