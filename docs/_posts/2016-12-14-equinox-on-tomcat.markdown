---
layout: post
title:  "Running Equinox in Tomcat"
date:   2016-12-14 19:49
category: eclipse
---

Equinox is the reference implementation of the [Open Service Gateway Initiative (OSGi) framework][osgi]. The service infrastructure of the OSGi makes it useful in many
different contexts, and Equinox is so sophisticated that the entire Eclipse Workbench runs on it. OSGi applications can also be deployed to webservers, such as Tomcat,
that act as Servlet Containers.

The [quickstart guide][guide] for running Equinox in a servlet container describes the process and provides a sample web application, *bridge.war.* This application can be deployed in Tomcat by configuring a Tomcat server in Eclipse, double clicking on the server configuration, and adding an "external module." The bridge.war file must be extracted in a directory using 

```bash
jar -xvf bridge.war
```

which is the Document Base for the external module. The Path of the external module is web path in the browser. For bridge.war with a path of /, the default URL is
http://localhost:8080/sp_test. Tomcat can be started through as usual in Eclipse after the external module is committed. 

[osgi]: https://www.osgi.org/
[guide]: http://www.eclipse.org/equinox/server/http_in_container.php