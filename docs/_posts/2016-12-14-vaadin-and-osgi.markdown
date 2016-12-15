---
layout: post
title:  "Running Vaadin with OSGi"
date:   2016-12-14 20:55
categories: eclipse
---

Vaadin is a web framework for business applications with Java and HTML programming interfaces. It can be used to provide web interfaces for OSGi applications by 
deploying an OSGi framework, such as Equinox, [in a servlet container.][deployment]

## Prerequisites

The Vaadin development team has provided code generators and other utilities for working with the framework in the Eclipse IDE. It is best to install these tools in either the "Java EE" or "JavaScript and Web" versions of Eclipse that also includes all WST packages, all JST packages, and the Eclipse Java Web Developer Tools. The author configured a version of Eclipse with all of these packages and the Java EE and web tools. 

## Creating a Basic Vaadin 7 Project

Jesus Boadas provides a [good tutorial][boadas_2016] on creating a Vaadin project with OSGi support. There have been some changes, mostly minor, in the way Vaadin projects are generated since Boadas' article was published, but the instructions on how to link to the OSGi are still correct, with the exception of a small menu change. Also note that it is not necessary to use Apache Karaf - Eclipse is built on Equinox, the reference implementation of the OSGi, which can be used in place of Karaf as discussed below.

Vaadin projects are now [generated using Maven][generation], whereas Boadas' article uses the "Vaadin Legacy Generator." The new generator does not provide the opportunity to add OSGi support as described in Boadas' article, but it can be configured after the project is configured by right clicking on the project and selecting "Convert to Faceted Form." Adding the OSGI facet will install the MANIFEST.MF file needed for the OSGi in the WebContent/META-INF directory and configure the library path. If the default values are used in the simple Vaadin project example, then the web.xml file should look like the following

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
  	<display-name>VaadinOsgi</display-name>
  	<servlet>
    	<servlet-name>VaadinOsgi</servlet-name>
    	<servlet-class>
			com.vaadin.server.VaadinServlet
		</servlet-class>
    	<init-param>
      		<param-name>UI</param-name>
      		<param-value>com.example.myapplication.MyUI</param-value>
    	</init-param>
  	</servlet>
  	<servlet-mapping>
    	<servlet-name>MyUIServlet</servlet-name>
    	<url-pattern>/*</url-pattern>
  	</servlet-mapping>
	<context-param>
	  <description>Vaadin production mode</description>
	  <param-name>productionMode</param-name>
	  <param-value>true</param-value>
	</context-param>    
</web-app>
```

and the application, when launched in the server will, appear at http://localhost:8080/myapplication. The "myapplication" root is specified in the MANIFEST.MF file. By default, the Vaadin libraries are not exported by the Eclipse project, which will result in a "ClassNotFound" error, (see below).

Boadas' article describes the basic steps to compile CSS assets, but they must be manually copied to the WebContent directory in Eclipse to be picked up by the server. 

## Possible Errors

### Dynamic Web project Error “Loading descriptor”

This error can be fixed by making sure that the Eclipse Java Web Developer Tools are installed.

### "Class" is not a servlet

If the servlet class is not configured correctly in web.xml, the server will not be able to load the servlet. If the server fails and logs an error saying that the UI class is not a servlet, it may be possible to fix the error by [setting the servlet class][servletError] to the base VaadinServlet class instead of the UI subclass as follows

```xml
<servlet-class>com.vaadin.server.VaadinServlet</servlet-class>
``` 

### The servlets named [VaadinOsgi] and [MyUIServlet] are both mapped to the url-pattern [/*] which is not permitted

Boadas' article uses a different name for the tutorial project than the default name used by the newer Maven project generator. It is possible - and, indeed, likely - 
that the name in the generated servlet does not match that in the WebContent/WEB-INF/web.xml. This will lead to an error where the server reports two servlets mapped to the same URL.

### java.lang.NoClassDefFoundError: com/vaadin/server/VaadinServlet

Eclipse will not include the Vaadin library in the "Deployment Assembly" by default. This list can be configured by right clicking on the project, opening the properties menu, and selecting "Deployment Assembly." New "Java Build Path Libraries" should be be added. The Maven Dependencies will add the Vaadin libraries, but the Plugin Dependencies should also be added to support OSGi dependencies in addition to Vaadin.

### Requested resource [/VAADIN/themes/mytheme/styles.css] not found

The "Compile Vaadin Theme" tool does not copy the stylesheets into the correct place after compiling the SCSS file. These files can be found in the project in the
src/main/java/resources/webapp/VAADIN directory. Copying the VAADIN directory into the project's WebContent directory and adding it to the Deployment Assembly as a Folder mapped to "/" will fix the problem.

[deployment]: http://www.jayjaybillings.com/thesis/eclipse/2016/12/14/equinox-on-tomcat.html
[servletError]: http://stackoverflow.com/questions/15794473/cannot-run-vaadin-project-in-eclipse-juno-with-tomcat-7-throws-classnotfoundexc
[boadas_2016]: https://examples.javacodegeeks.com/enterprise-java/vaadin/vaadin-osgi-example/
[generation]: https://vaadin.com/docs/-/part/framework/getting-started/getting-started-first-project.html#getting-started.first-project.creation