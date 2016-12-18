---
layout: post
title:  "Running Vaadin with OSGi"
date:   2016-12-14 20:55
categories: eclipse
---

Vaadin is a web framework for business applications with Java and HTML programming interfaces. It can be used to provide web interfaces for OSGi applications by 
deploying an OSGi framework, such as Equinox, [in a servlet container.][deployment]

Same code showing it running the OSGi framework is available in [myapplication directory of the thesis repository.](https://github.com/jayjaybillings/thesis/tree/master/myapplication)

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

## Create a basic run configuration

The best way to launch the Vaadin bundle in the framework is in a minimal OSGi framework instance, which can be configured and launched via an Eclipse run configuration. The full list of bundles is provided below and a copy of the minimal configuration can be found in the [sample code site.](https://github.com/jayjaybillings/thesis/blob/master/myapplication/vaadin_osgi.launch)

```
osgi> ss
"Framework is launched."


id	State       Bundle
0	ACTIVE      org.eclipse.osgi_3.11.1.v20160708-1632
	            Fragments=28, 39, 42, 29, 23, 26
1	ACTIVE      org.eclipse.equinox.simpleconfigurator_1.1.200.v20160504-1450
2	ACTIVE      javax.el_2.2.0.v201303151357
3	ACTIVE      javax.servlet_3.1.0.v201410161800
4	ACTIVE      javax.servlet_3.0.0.v201112011016
5	ACTIVE      javax.servlet.jsp_2.2.0.v201112011158
6	ACTIVE      javax.xml_1.3.4.v201005080400
7	ACTIVE      myapplication_1.0.0.qualifier
8	ACTIVE      org.apache.commons.codec_1.6.0.v201305230611
9	ACTIVE      org.apache.commons.httpclient_3.1.0.v201012070820
10	ACTIVE      org.apache.commons.logging_1.1.1.v201101211721
11	ACTIVE      org.apache.felix.gogo.command_0.10.0.v201209301215
12	ACTIVE      org.apache.felix.gogo.runtime_0.10.0.v201209301036
13	ACTIVE      org.apache.felix.gogo.shell_0.10.0.v201212101605
14	ACTIVE      org.apache.httpcomponents.httpclient_4.3.6.v201511171540
15	ACTIVE      org.apache.httpcomponents.httpcore_4.3.3.v201411290715
16	ACTIVE      org.eclipse.equinox.common_3.8.0.v20160509-1230
17	ACTIVE      org.eclipse.equinox.console_1.1.200.v20150929-1405
18	ACTIVE      org.eclipse.equinox.ds_1.4.400.v20160226-2036
19	ACTIVE      org.eclipse.equinox.http.jetty_3.3.0.v20160324-1850
20	ACTIVE      org.eclipse.equinox.http.registry_1.1.400.v20150715-1528
21	ACTIVE      org.eclipse.equinox.http.servlet_1.3.1.v20160808-1329
22	ACTIVE      org.eclipse.equinox.http.servletbridge_1.0.300.v20130327-1442
23	RESOLVED    org.eclipse.equinox.region_1.3.1.v20160816-1331
	            Master=0
24	ACTIVE      org.eclipse.equinox.registry_3.6.100.v20160223-2218
25	ACTIVE      org.eclipse.equinox.servletbridge_1.3.200.v20160128-1435
26	RESOLVED    org.eclipse.equinox.transforms.hook_1.1.0.v20131021-1933
	            Master=0
27	ACTIVE      org.eclipse.equinox.util_1.0.500.v20130404-1337
28	RESOLVED    org.eclipse.equinox.weaving.hook_1.1.200.v20150730-1648
	            Master=0
29	RESOLVED    org.eclipse.fx.osgi_2.4.0.201605100504
	            Master=0
30	ACTIVE      org.eclipse.jetty.continuation_9.3.9.v20160517
31	ACTIVE      org.eclipse.jetty.http_9.3.9.v20160517
32	ACTIVE      org.eclipse.jetty.io_9.3.9.v20160517
33	ACTIVE      org.eclipse.jetty.security_9.3.9.v20160517
34	ACTIVE      org.eclipse.jetty.server_9.3.9.v20160517
35	ACTIVE      org.eclipse.jetty.servlet_9.3.9.v20160517
36	ACTIVE      org.eclipse.jetty.util_9.3.9.v20160517
37	ACTIVE      org.eclipse.jetty.webapp_9.3.9.v20160517
38	ACTIVE      org.eclipse.jetty.xml_9.3.9.v20160517
39	RESOLVED    org.eclipse.osgi.compatibility.state_1.0.200.v20160504-1419
	            Master=0
40	ACTIVE      org.eclipse.osgi.services_3.5.100.v20160504-1419
41	ACTIVE      org.eclipse.osgi.util_3.3.100.v20150423-1351
42	RESOLVED    org.eclipse.wst.jsdt.nashorn.extension_1.0.0.v201605131737
	            Master=0
43	ACTIVE      test_1.0.0.qualifier
44	ACTIVE      test2_1.0.0.qualifier
osgi> bundle 7
myapplication_1.0.0.qualifier [7]
  Id=7, Status=ACTIVE      Data Root=/home/bkj/thesis-private/test-workspace/.metadata/.plugins/org.eclipse.pde.core/OSGi Framework/org.eclipse.osgi/7/data
  "No registered services."
  No services in use.
  Exported packages
    com.example.myapplication; version="0.0.0"[exported]
  Imported packages
    javax.el; version="2.2.0" <javax.el_2.2.0.v201303151357 [2]>
    javax.servlet; version="3.1.0" <javax.servlet_3.1.0.v201410161800 [3]>
    javax.servlet.http; version="3.1.0" <javax.servlet_3.1.0.v201410161800 [3]>
    javax.servlet.jsp; version="2.2.0" <javax.servlet.jsp_2.2.0.v201112011158 [5]>
    javax.servlet.jsp.el; version="2.2.0" <javax.servlet.jsp_2.2.0.v201112011158 [5]>
    javax.servlet.jsp.tagext; version="2.2.0" <javax.servlet.jsp_2.2.0.v201112011158 [5]>
  No fragment bundles
  No required bundles

```
Notice that bundle 7 is the "myapplication" Vaadin demonstration bundle, but it neither consumes nor produces services and does not yet publish web services. The MyUIServlet needs to be explicitly registered with the OSGi HTTP service to do this.

## Create an OSGi Dynamic Service Component

The OSGi HTTP service is provided by the framework as a standard service and can be obtained in a variety of ways. The simplest is to use OSGi's Declarative Services mechanism to declare that the service should be provided to the MyUI class using Dependency Injection at runtime. The MyUI class must be modified to accept the service by adding setters, as shown below.

```java
package com.example.myapplication;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private HttpService httpService;

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
	@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }
    
    /**
     * OSGi bundle activator with annotation instead of activator class.
     * @param context
     */
    @Activate
	public void start(BundleContext context) {
		System.out.println("VAADIN bundle started.");
		try {
			httpService.registerServlet("/", new MyUIServlet(), null, null);
		} catch (ServletException | NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
    
    public void setService(HttpService httpService) {
    	this.httpService = httpService;
    }   
}
```

The "Declarative" part of Declarative Services comes from creating an XML file that explicit declares the need for the service. The component file, (OSGI-INF/component.xml in the example), should look like the following

```xml
<?xml version="1.0" encoding="UTF-8"?>

<scr:component xmlns:scr="http://www.osgi.org/xmlns/scr/v1.1.0" activate="start" enabled="true" immediate="true" name="org.eclipse.ice.client.vaadin">
   <implementation class="com.example.myapplication.MyUI"/>
   <reference bind="setService" cardinality="1..1" interface="org.osgi.service.http.HttpService" name="HttpService" policy="static"/>
</scr:component>
```

If the component definition is added through the UI, Eclipse will automatically update the META-INF/MANIFEST.MF file to point to it so that the OSGi framework finds it. If the UI is not used, the following line must be added manually to META-INF/MANIFEST.MF

```
Service-Component: OSGI-INF/*.xml
```

## What about Vaadin on Class path?

Running the framework at this point will produce a class not found error because Vaadin is not on the runtime classpath of the OSGi framework. This is fixed by downloading a standard zip file of Vaadin and adding all jar files in the root and lib directories to WebContent/WEB-INF/lib. Each jar must then be added to the myapplication bundle classpath uniquely: It is not sufficient to add only the WebContent/WEB-INF/lib directory!

The entire META-INF/MANIFEST.MF file should now look like

```
Manifest-Version: 1.0
Bundle-ManifestVersion: 2
Bundle-Name: Myapplication
Bundle-SymbolicName: myapplication;singleton:=true
Bundle-Version: 1.0.0.qualifier
Bundle-ClassPath: WEB-INF/classes/,
 ../lib/vaadin-client-7.7.6.jar,
 ../lib/vaadin-client-compiler-7.7.6.jar,
 WEB-INF/lib/asm-5.0.3.jar,
 WEB-INF/lib/asm-commons-5.0.3.jar,
 WEB-INF/lib/asm-tree-5.0.3.jar,
 WEB-INF/lib/asm-util-5.0.3.jar,
 WEB-INF/lib/atmosphere-runtime-2.2.9.vaadin2.jar,
 WEB-INF/lib/flute-1.3.0.gg2.jar,
 WEB-INF/lib/gwt-dev-2.7.0.vaadin4.jar,
 WEB-INF/lib/gwt-elemental-2.7.0.vaadin4.jar,
 WEB-INF/lib/gwt-user-2.7.0.vaadin4.jar,
 WEB-INF/lib/jsoup-1.8.3.jar,
 WEB-INF/lib/sac-1.3.jar,
 WEB-INF/lib/vaadin-push-7.7.6.jar,
 WEB-INF/lib/vaadin-sass-compiler-0.9.13.jar,
 WEB-INF/lib/vaadin-server-7.7.6.jar,
 WEB-INF/lib/vaadin-shared-7.7.6.jar,
 WEB-INF/lib/vaadin-slf4j-jdk14-1.6.1.jar,
 WEB-INF/lib/vaadin-themes-7.7.6.jar,
 WEB-INF/lib/validation-api-1.0.0.GA-sources.jar,
 WEB-INF/lib/validation-api-1.0.0.GA.jar,
 WEB-INF/lib/vaadin-widgets-7.7.6.jar,
 WEB-INF/lib/vaadin-client-compiled-7.7.6.jar
Export-Package: com.example.myapplication
Bundle-RequiredExecutionEnvironment: JavaSE-1.8
Import-Package: javax.el,
 javax.servlet,
 javax.servlet.http,
 javax.servlet.jsp,
 javax.servlet.jsp.el,
 javax.servlet.jsp.tagext,
 org.osgi.framework;version="1.8.0",
 org.osgi.service.component.annotations;version="1.2.0",
 org.osgi.service.http;version="1.2.1"
Web-ContextPath: /myapplication
Service-Component: OSGI-INF/*.xml
Bundle-ActivationPolicy: lazy
```

## Final Result

Vaadin should now run correctly in an OSGi framework. The output should look roughly like the following.

```
2016-12-17 16:01:12.114:INFO::Start Level: Equinox Container: f0b67ef0-9bc4-0016-19b5-b5f160b29616: Logging initialized @933ms
osgi> VAADIN bundle started.
Dec 17, 2016 4:01:12 PM com.vaadin.server.DefaultDeploymentConfiguration checkProductionMode
WARNING: 
=================================================================
Vaadin is running in DEBUG MODE.
Add productionMode=true to web.xml to disable debug features.
To show debug window, add ?debug to your application URL.
=================================================================

... lots of other stuff ...

osgi> b 7
myapplication_1.0.0.qualifier [7]
  Id=7, Status=ACTIVE      Data Root=/home/bkj/thesis-private/test-workspace/.metadata/.plugins/org.eclipse.pde.core/OSGi Framework/org.eclipse.osgi/7/data
  "Registered Services"
    {javax.servlet.Servlet}={equinox.legacy.tccl=org.eclipse.osgi.internal.framework.ContextFinder@71794dbb, osgi.http.whiteboard.servlet.name=com.example.myapplication.MyUI$MyUIServlet, service.ranking=2147483647, osgi.http.whiteboard.context.select=(service.id=70), osgi.http.whiteboard.servlet.pattern=[/,/*], osgi.http.whiteboard.target=(equinox.http.id=-2255318698340326480), service.id=71, service.bundleid=7, service.scope=singleton}
  Services in use:
    {org.osgi.service.http.context.ServletContextHelper}={osgi.http.whiteboard.context.name=org_eclipse_equinox_http_servlet_internal_HttpServiceImpl_DefaultHttpContext-0, equinox.legacy.http.context.initiating.id=7, equinox.legacy.context.helper=true, osgi.http.whiteboard.target=(equinox.http.id=-2255318698340326480), osgi.http.whiteboard.context.path=/, service.id=70, service.bundleid=21, service.scope=bundle}
    {org.osgi.service.http.HttpService, org.eclipse.equinox.http.servlet.ExtendedHttpService}={service.vendor=Eclipse.org, multipart.servlet.name=Equinox Jetty-based Http Service - Multipart Servlet, osgi.http.endpoint=[/], equinox.http.id=-2255318698340326480, service.description=Equinox Jetty-based Http Service, http.port=8082, service.id=69, service.bundleid=21, service.scope=bundle}
  Exported packages
    com.example.myapplication; version="0.0.0"[exported]
  Imported packages
    javax.el; version="2.2.0" <javax.el_2.2.0.v201303151357 [2]>
    javax.servlet; version="3.1.0" <javax.servlet_3.1.0.v201410161800 [3]>
    javax.servlet.http; version="3.1.0" <javax.servlet_3.1.0.v201410161800 [3]>
    javax.servlet.jsp; version="2.2.0" <javax.servlet.jsp_2.2.0.v201112011158 [5]>
    javax.servlet.jsp.el; version="2.2.0" <javax.servlet.jsp_2.2.0.v201112011158 [5]>
    javax.servlet.jsp.tagext; version="2.2.0" <javax.servlet.jsp_2.2.0.v201112011158 [5]>
    org.osgi.framework; version="1.8.0" <org.eclipse.osgi_3.11.1.v20160708-1632 [0]>
    org.osgi.service.component.annotations; version="1.2.0" <org.eclipse.osgi.services_3.5.100.v20160504-1419 [40]>
    org.osgi.service.http; version="1.2.1" <org.eclipse.osgi.services_3.5.100.v20160504-1419 [40]>
  No fragment bundles
  No required bundles
```

The result should be viewable at http://localhost:8082 or whatever port is specified with the -Dorg.osgi.service.http.port VM argument in the run configuration and resemble the figure below. 

![Vaadin running in the OSGi](vaadin_osgi_20161216.png).

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

### INFO: Requested resource [/VAADIN/widgetsets/com.vaadin.DefaultWidgetSet/com.vaadin.DefaultWidgetSet.nocache.js] not found from filesystem or through class loader. Add widgetset and/or theme JAR to your classpath or add files to WebContent/VAADIN folder.

The vaadin-client-compiled-*.jar bundle must be added to the myapplication bundle's class path.