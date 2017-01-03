---
layout: post
title:  "The Eclipse Integrated Computational Environment"
date:   2016-12-19 21:19
address: 'Oak Ridge National Laboratory, PO Box 2008 MS6016 Oak Ridge TN 37830'
category: workflows
author: 
- Jay Jay Billings (1,2),&nbsp;
- Andrew R. Bennett (1,3),&nbsp;
- Jordan Deyton (1,4),&nbsp;
- Kasper Gammeltoft (1,5),&nbsp;
- Jonah Graham (6),&nbsp;
- Dasha Gorin (1,7),&nbsp;
- Hari Krishnan (8),&nbsp;
- Menghan Li (9),&nbsp;
- Alexander J. McCaskey (1),&nbsp;
- Taylor Patterson (1,10),&nbsp;
- Robert Smith (1),&nbsp;
- Gregory R. Watson (1),&nbsp;
- Anna Wojtowicz (1,11)
---

Affiliations
============

1. Oak Ridge National Laboratory PO Box 2008 MS6173 Oak Ridge TN 37830
2. The Bredesen Center for Interdisciplinary Research and Graduate Education, The University of Tennessee, 444 Greve Hall, 821 Volunteer Blvd. Knoxville, TN 37996-3394 
3. University of Washington, Seattle, WA 98105
4. General Electric Company, 3200 North Grandview Blvd Waukesha, WI 53188-1678
5. Georgia Institute of Technology North Avenue, Atlanta, GA 30332
6. Kichwa Coders Ltd. 1 Plomer Green Avenue Downley, High Wycombe HP13 5LN United Kingdom
7. Northwestern University 633 Clark Street Evanston, IL 60208
8. Lawrence Berkeley National Laboratory, 1 Cyclotron Rd, Berkeley, CA 94720
9. Department of Computer Science, and Department of Biological Sciences, Purdue University, West Lafayette, IN 47906
10. Acato Information Management, LLC 114 Union Valley Rd. Oak Ridge, TN 37830
11. Colorado State University Fort Collins, CO  80523

_This article is to be submitted to the Journal "Software X" and is thus presented in the journal's required format. Mostly._

Notice of Copyright
===========================

This manuscript has been authored by UT-Battelle, LLC under Contract No. 
DEAC05-00OR22725 with the U.S. Department of Energy. The United States 
Government retains and the publisher, by accepting the article for publication, 
acknowledges that the United States Government retains a nonexclusive, paid-up, 
irrevocable, world-wide license to publish or reproduce the published form of 
this manuscript, or allow others to do so, for United States Government 
purposes. The Department of Energy will provide public access to these results 
of federally sponsored research in accordance with the DOE Public Access Plan 
(http://energy.gov/downloads/doe-public-access-plan ).

Abstract
===========================

Problems in modeling and simulation require significantly different workflow
management technologies than standard grid-based workflow management systems.
Computational scientists typically interact with simulation software in a
feedback driven way were solutions and workflows are developed iteratively and
simultaneously. This work describes common activities in workflows and how 
combinations of these activities form unique workflows. It presents the Eclipse
Integrated Computational Environment as a workflow management system and
development environment for the modeling and simulation community. Examples of
the Environment's applicability to problems in energy science, general 
multiphysics simulations, quantum computing and other areas are presented as
well as its impact on the community.

Motivation and Significance
===========================

High performance modeling and simulation software is hard to use. Much of the
challenge comes from the inherent nature of the science
under consideration, but perhaps just as much of the problem comes from
focusing very heavily on raw compute performance. Focusing on performance is
understandable, given the field, but it leaves the rest of us to figure out how
to actually use the software in a way that scales for a much larger set of
users, many of whom may be novices or migrate between software packages regularly.

** REMOVE or merge with text below!** 

In previous work, Billings et. al., discovered through requirements gathering
interviews that many of the difficulties using high performance modeling and
simulation software fall broadly into five distinct categories,
\cite{billings_cbhpc}. This includes input generation and preprocessing (also
called ``model setup''); job execution and monitoring; postprocessing,
visualization and data analysis; data management; and customizing the software.
There are many tools that address these problems individually, but the same
work found that the excess number and specialization of these tools also
contribute to the learning curve.

Efforts to address these five issues typically fall in with general purpose
scientific workflow tools like Kepler, \cite{kepler}, or are reduced to myopic
tools that satisfy some set of requirements for a single piece of software or
platform. That is, the proposed solution to this problem is often to shoe-horn
it into existing workflow tools that are so general that they focus on nothing
in particular or to ignore the general problem entirely and deploy a completely
tailored solution for the given application. These are opposing extremes, but a
middle-of-the-road solution is also possible. A workflow engine could be
developed that limits its scope to High-Performance Computing (HPC) and to the
set of possible workflows that come from the previously mentioned five
activities. A rich enough Application Programming Interface (API) could be
exposed so that highly customized solutions could still be made based on this
limited workflow engine with only a relatively minor amount of additional
development required.

It is not clear that one of these solutions is better than the others.
Practical requirements will ultimately dictate which way projects go. This work
considers a middle ground solution and presents the Eclipse Integrated
Computational Environment (ICE) as proof that it is possible to create such a
system. Specifically, we show
* an architecture for such a workflow system that satisfies the model of
workflows presented below in an extensible way.
* that such a system can be cross-platform for workstations and
simultaneously deployable on the web.
* that smart design decisions enable not only authoring code for simulation
software, but also make it possible for the system to extend itself, thereby
enabling heavy customization.

**FIXME! Needs to transition smoothly!** 

### Workflow Model

Computational scientists perform a variety of tasks with respect to modeling
and simulation that can be abstracted into a lightweight theoretical framework 
broadly based on five high-level _activities_: creating input, executing jobs, 
analyzing results, managing data, and modifying code. The same computational 
scientist would most likely find these activities difficult for all codes with 
which they lack experience, whereas with their own codes or those with which 
they are most familiar these tasks may be so easy that they are taken for 
granted. Any particular combination of these activities across one or more 
scientific software packages or codes results in a unique _workflow_. Such a 
workflow is normally, but not always, requested by a human user and 
orchestrated by a _Workflow Management System (WMS)_.

The most obvious workflow for any individual simulation code or collection of
codes is to string the activities together: the user's workflow is to create
the input, launch the job, perform some analysis and manage their data, 
possibly modifying their code in the process. There are many other combinations
though, such as re-running jobs with conditions or modifications or analyzing 
someone else's data. (See footnote <sup>1</sup>.)

**Creating input** is the process of describing the physical model or state
of a system that will be simulated. This could include creating an input file
or file(s), but could also include external calls to a process to configure a
running program. In most situations a computational scientist will modify 
existing input or create new input from a template. "Input" generally includes
runtime parameters for the simulation framework (tolerances, etc.); 
configuration options (data locations, output locations, module configurations,
etc.); material properties for the materials that will be simulated; and a 
discretization of the simulation space (mesh, grid, particle distribution, 
etc.). The collection of all required input can be quite large and may go by
many names such as "input set," "input package," "problem," or, simply, 
"input." Often the set of input files will be described in a "main" input file
that acts as a kind of manifest, describing and providing links to all 
necessary information for the given problem. 

In this work, it should be assumed unless otherwise noted that "input"
refers to the entire set of input, not a single file.

**Executing jobs** or "running the workflow" in this context is the process 
of performing some calculation based on the input with a simulation code or
framework. Modeling and simulation codes are normally run locally for small
jobs or development. Large simulations typically require equally large
hardware resources that are utilized remotely through Secure Shell (SSH)
connections or similar protocols. Remote execution requires moving the input
in advance of the execution and copying or moving the output to the user's 
machine, if possible and desirable. (In many cases the output cannot be moved
because it is too large.) 

Local and remote jobs are often monitored in one or more ways to ascertain the 
status of job. This can include simply checking whether or not execution has 
finished or monitoring the output of individual quantities to examine the 
calculation state. The latter is often used to detect calculation errors that 
will result in incorrect results. If such results are found, the job is 
typically cancelled ("killed") to save compute resources and re-run later. 

In this work, it should be assumed unless otherwise noted that "executing a 
job" includes monitoring that job in one or more ways, possibly including
real-time updates to visualizations.

**Analyzing results** includes executing special jobs to transform data in one
or more prescribed ways and producing artifacts with scientific significance
from the transformed data. This may include, for example, post-processing 
results and visualizing the new data with dedicated visualization tools. For
many types of scientific computing this includes viewing the results of a 
simulation on a mesh or grid and extracting publication quality images or 
movies from that data. Other cases may include analyzing results in preparation
for follow simulations or performing feature extraction, classification or 
activities for machine learning and data mining. 

While this has many similarities to executing a job, it is distinctly different 
because the activity changes focus to satisfy the needs of a human operator. 
Simple data reduction where the exact reduction is known certainly qualifies as
executing a job, but analysis of modeling and simulation results is far from
simple data reduction. It is generally a far more interactive process for
computational scientists.

**Managing data** includes moving, copying, storing, sharing or otherwise 
interacting with data for or from simulations. This activity is the most 
pervasive of all the activities because each of the others requires interacting
with data in some way. In many cases though, data is still managed its own
purposes, without performing a simulation, generating new input, or analysing
results. Examples include archiving data, packaging data for publications, and 
updating values manually (often in light of new information from publications).

**Modifying code** is not typically considered a part of a scientific computing
workflow. However, modeling and simulation use cases often require users to 
explicitly modify code before execution, such as with the computational fluid
dynamics code Nek5000, \[Nek5000]; or to issue special re-build instructions. Many 
computational scientists consider "their workflow" to be re-running software 
after modifications for purely exploratory purposes. This may be required if 
the model the author is manipulating can not be configured as part of the input
directly but can be easily manipulated by hand.

### Comparison to Other Models

This model of workflows differs significantly from those of similar efforts in 
workflow science because it defines workflows in terms of activities. Others in
the literature define a workflow as a collection of computing processes. For 
example, Yu and Buyya define grid workflows as "a collection of tasks that are
processed on distributed resources in a wel-defined order to accomplish a 
specific goal," \[Yu, 2005]. Others such as Pizzi et al. subscribe to similar
definitions, \[Pizzi, 2015]. This "process" view is acceptable where the 
workflow is static and does not require additional human input or "human in 
the loop" behavior after the all the initial human input is provided. That is, 
a "process-oriented" definition is acceptable where all human input is provided
in advance. However, workflows within ICE are fully interactive with regular 
call-backs to humans. It is simpler to discuss "activities" than it is to 
create a distinction between "human processes" and "computer processes." 
Focusing on "activities" over processes (human or computer) also has the 
benefit of removing concrete elements such as hardware properties or software 
details that distract from details of workflows and WMSes per se. That is, 
considerations such as memory usage and raw performance are important, but 
questions about the abstract workflow or what the WMS should do are far more 
important.

Software Description
=====================

ICE was specifically created to address the workflow model described above, 
which is to say that it was created specifically to address hands-on workflows 
for computational scientists. Users download and execute ICE locally and it 
orchestrates workflows locally or remotely as required. It provides a 
comprehensive workbench for modeling and simulation that includes
tools for workflows, visualization, data management, and software development.

Software Architecture
------------------------

Workflows and tasks in ICE are not explicitly treated as trees - 
Directed Acyclic Graphs, (DAGS) - as is common with grid workflow tools, 
\[Yu, 2005]. Instead, ICE's design is inspired by Representational State 
Transfer (REST), \[Fielding, 2000]. 

Users initiate requests to create, edit, update or delete workflows from the 
_ICE Client_ (the workbench). The list of available workflow that can be 
created is provided dynamically to the _ICE Client_ by the _ICE Core_, which 
acts as a kind of server. This information is provided dynamically since it 
often changes as run time based on the configuration of available workflow
components in the registry and on persisted workflows that users have saved in
their workspace. Information about workflows are provided from the Core to the
Client through _stateless Forms_ that describe the workflow and provide all the
necessary information to understand _what_ should process the workflow, (but 
not _how_ it should be processed). Once users modify the description of the
workflow in the Form to provide their specific details, the Client dispatches
a request to the Core to modify or process the workflow. The Core then uses
the information in the Form to perform a service lookup for _what_ should 
process the workflow. 

Workflows in ICE are encoded in and processed by _Items_ and each workflow type
is a subclass of Item. Items are registered dynamically through a service 
registry in ICE, (see section \ref{framework}), and provide the Forms
for the ICE Core and Client. Since ICE's design is highly object oriented, it
is easiest to think of the Item class as a description of an abstract workflow
and an Item object (an instance of the class) as a concrete workflow with all
required execution details provided by its Form.

Individual components of workflows, i.e. - workflow "tasks" or "nodes" - are 
either encoded directly in the workflow's subclass of Item or provided as 
_Actions_ that are dynamically registered with an _Action Factory_ and
obtained at runtime. Table 1. describes the differences between Items, Actions,
and Forms. (See footnote <sup>2</sup>.)

Table 1: Class Descriptions for Items, Forms and Actions

| Class | Class Description | Object Description |
|-------|-------------------|--------------------|
| Item  | Java class with code to execute an abstract workflow. Provides a Form. | Concrete workflow executor. |  
| Form  | Description and template of the data needed for the Item to process the workflow. | User-modified workflow data. |
| Action | Java class for executing a specific task in the workflow. Used by Items. | Concrete workflow task executor. | 


In addition to running as a desktop workbench, ICE can be run as a headless
web server with a remote service interface and a web Application Programming
Interface (API). The web API is also used as the primary means of providing
real-time feedback and monitoring support in ICE.

### Item States

All Items in ICE are finite state machines where the states represent the 
abstract state of the workflow. For example, when an Item is first created, 
it enters the "Form Ready" state to indicate that it could, in theory, be 
processed after a user reviews it. After that review it enters the "Ready 
to Process" state before and the "Processed" state after it is processed. 
There are several additional states for errors.

This design is very important. First, it means that all workflows in ICE,
regardless of their actual details and function, can only behave in a specific 
set of known, predictable ways. This greatly simplifies the way that the Core 
manages and interacts with Items. Second, it explicitly delineates which
workflows can be executed from those that must still receive some
configuration because it formalizes state and error checks. Finally, it allows 
developers implementing Items and Actions to specify by contract what is
required before proceeding to the next task, processing the workflow, or
declaring a successful execution.

### Persistence and Workspaces

ICE persists permanent copies of Forms to disk when workflows are created and 
modified in a special directory called a _workspace_. Workspaces can contain 
projects, folders and files, including data, code, input and output. ICE
automatically manages local and remote (or even local _to_ remote) transfers of
data files when executing workflows if the files are detected in the same 
directory of the workspace as the workflow itself. For example, when executing
a remote job, ICE will automatically move the input file if it is specified
in the workflow and available in the project directory of the workspace.
Likewise, if output is small enough, ICE will automatically move it back to
local directory. By conventional, all paths in ICE's Forms are relative to the
workspace root path. Workspace directories are specified by the user.

ICE handles persistence using a _persistence provider_. The only default
persistence provider uses JAX-RS, \[Burke, 2009], to write Forms to XML. In
principle other persistence providers could replace the XML-based provider 
since it is registered as a dynamic service, and in the past a JAX-P based 
provider has been used.

### Extending the Framework to Add Custom Workflows

ICE is an Eclipse Rich Client Platform (RCP) application, \[McAffer 2, 2010].
It has a plugin architecture based on Equinox, the reference implementation of 
the Open Service Gateway Initiative (OSGi) framework specification, \[McAffer, 
2010]. It uses over 1200 additional packages from the Eclipse ecosystem to
provide services such as language support and visualization to users. Each 
unique element of ICE described in this work, including the client, core, all 
Items, data structures and many others are provided as plugins to Equinox. Most
plugins are dynamically managed and provided as services that can be obtained 
from a service registry. All file I/O in ICE, with only a few exceptions, 
interacts with the RCP's Resources plugin. This includes remote resources that 
are managed with the Eclipse Parallel Tools Platform, \[Tibbitts, 2009].

It is not possible to graphically create new Items in ICE. Users can only use 
the workflow Items provided with their version of ICE unless they provide their
own plugin to the framework. This can seem like an onerous task to the novice 
user especially if they do not know Java, but this design was chosen because it
is, in the opinion of the authors, a far more sophisticated and sustainable way
to create workflows than through wiring diagram.

ICE provides built-in development tools to auto-generate "stubs" of plugins 
that can then be installed into the framework. This "self-hosting" makes it 
possible for new users to create complex, sophisticated workflows very quickly 
because they are not required to know how to interact with the framework. 
Furthermore, ICE provides a rich API, documentation, tutorials, and tools to 
further simply the process.

There are many situations where graphically configuring workflows is 
unacceptable, such as when a very large number of workflows will be executed or
the type of information required is very fine-grained. In these cases, it is
often necessary to provide scripts to the workflow management system. ICE 
includes the Eclipse Advanced Scripting Environment, \[Pontesegger, 2017], 
(EASE), for scripting because it provides a native way to script Eclipse RCP
projects in Javascript, Jython, and Python. This also makes it possible to 
extend the environment by adding Items in these languages. 

Software Functionalities
------------------------

The most important function of ICE is to serve as an easily extended workflow
management system for computational scientists in support of the activities
described above. In practice, it is most often used as a combination of 
workflow management system and development environment since it contains a 
significant amount of Eclipse's software development tooling in addition to the
workflow tools. The project has many users, but it is most heavily used by the
development team to support workflow science and software development for 
energy science projects. The development team regularly uses the platform to
quickly deploy new domain-specific workbenches in a matter of hours for small 
collections of workflows that are easy to encode. 

Outside of the development team, ICE is commonly deployed as a sophisticated
user environment for computational science projects (see "Impact" below) and as
a visualization tool. ICE originally a significant amount of visualization
support, but at the request of users in the community that support was "spun 
off" in early 2016 as a separate visualization project, the Eclipse Advanced 
Visualization Project, EAVP, \[EAVP, 2009].

Illustrative Examples
=====================

The role that ICE plays as a workflow tool is best illustrated by the various
ways that it has been deployed. 

Virtual Battery Simulations
-----

Pannala et al. developed a Virtual Integrated Battery Environment (VIBE) as 
part of their research into safety and performance characteristics of lithium 
ion batteries, \[Pannala, 2015]. VIBE provides ICE to users as part of its
distribution. New workflows were added to ICE to create multiple types of 
input, configure the simulation software, and launch simulations of virtual 
batteries. Interactive 3D visualizations of the results were embedded in the 
launcher so that users would quickly find their results. Figure 1 shows the 
results from a temperature distribution of a prismatic cell battery simulated 
during discharge.

![vibe]({{ site.url }}/{{site.baseurl}}/images/vibe_20151016.png)

VIBE 1.0 is available as a virtual machine in which the simulation software and
ICE are provided side by side. More recent efforts for VIBE 1.1 by the VIBE 
development team include providing the simulation software in Docker containers
so that users can download the latest, native version of ICE for their machine
while simultaneously benefiting from a smaller virtual machine for the
simulator.

Multiphysics Simulations with MOOSE
-----

The MOOSE Framework is a powerful, easy to use multiphysics framework developed
at the Idaho National Laboratory, \[Gaston, 2009]. ICE provides workflow tools
for MOOSE as well as specialized class generation utilities for developing 
custom MOOSE kernels. Many of the MOOSE tools in ICE were developed closely 
with the MOOSE team to closely reproduce various aspects of MOOSE's user 
interface, Peacock. Figure 2 shows an example of the ICE workbench for a simple
structural mechanics problem solved with the MOOSE framework, 
\[McCaskey, 2016].

![moose]({{ site.url }}/{{site.baseurl}}/images/ice-moose.png)

There are over three hundred MOOSE-based applications and it is very easy to 
create new MOOSE applications. The ICE development team uses ICE and MOOSE to 
quickly solve energy science problems with high-performance computing 
resources and deploy domain specific workbenches. ICE includes support for
automatically downloading and building MOOSE from its GitHub project, which 
then makes it possible to immediately begin developing complex multiphysics
applications using the Eclipse C Development Tools, which are pre-installed in
ICE to facilitate this interaction. Once the new MOOSE-based application is 
built, it will automatically work with the MOOSE workflow tools in ICE, 
although developers can also create customized workflow tools as needed.  

Binder Jet Modeling
-----

Solid-state sintering of parts printed using binder jetting significantly 
increases part strength by decreasing the part porosity and eliminating voids. 
The downside of this process is that it causes the part to shrink and warp. An 
ideal near-net-shape process that combined binder jetting with solid-state 
sintering would thus consider warpage and deformation due to sintering in the 
design phase of the part. Figure 3 shows an ICE-based workbench for perform
simulations of this process with visualizations of the pre- and post-simulation
properties of a central body with eight cantilevers. The primary deformation in
this type of geometry is bending or drooping of the cantilevers, and ICE 
currently calls a custom MOOSE-based application (which was written in ICE as
described above) for simulating the deformation of the cantilevers.

![bjm]({{ site.url }}/{{site.baseurl}}/images/ice-bjm.png)

The full application for this work is expected to be released near the end of
2017.

Neutron Reflectivity
-----

ICE includes a small utility for simulating neutron reflectivity and comparing
the results to data, \[Billings, 2015]. This was developed in collaboration 
with colleagues at the Spallation Neutron Source to replace an older utility
written in Visual Basic and distributed via Excel macros. The newer utility in
ICE is capable of processing a single workflow and is shown in figure 4.

![reflectivity]({{ site.url }}/{{site.baseurl}}/images/reflectivity-screenshot.png)

Quantum Computing
-----

As quantum computing grows, so to does the need for sophisticated software that
can utilize quantum hardware or perform calculations on simulated quantum 
hardware. Humble et al. created a simulator for adiabatic quantum computers 
where workflows were added to ICE to support interaction with the simulator, 
but also to process large sets of quadratic binary optimimzation problems, 
\[Humble, 2014]. Figure 5 shows the workbench for this project.

![qc]({{ site.url }}/{{site.baseurl}}/images/jaded.png)

ICE also supports other quantum computing projects, including the eXtreme-scale
ACCelerator (XACC) programming framework, \[ORNL-QCI/XACC, 2016]. XACC is a 
programming framework for extreme-scale, post-exascale accelerator 
architectures that integrates alongside existing conventional applications.

Nuclear Energy
-----

There are many examples of ICE's role in modeling and simulation projects for
nuclear energy, but readers are referred to the work in \[Billings, 2015] for 
an extreme example of the level of customization that is possible in ICE. 
Support for the "Reactor Analyzer" was dropped in ICE 2.1.8, but it 
demonstrated ICE's ability to integrate many different nuclear energy tools for
complex analysis.

Impact
======

The impact of software tools such as ICE are hard to quantify. However, there
are several places where ICE has significantly assisted its development team
and others.

One pressing area of interest and impact is that of interoperability between 
workflow systems. There is significant prior art in combining large workflow 
management systems, such as that Mandal et al., \[Mandal, 2007], but the end
goal of gaining the greatest advantage by using the best capabilities from
multiple systems remains elusive. ICE's unique perspective on workflows and its
well-defined API make it possible to integrate multiple systems in a 
straightforward way. This allows it to connect to other workflow environments, 
such as Triquetrum, quite easily, \[Brooks 1, 2015]. Triquetrum, (like Kepler in 
\[Mandal, 2007]), is a Ptolemy-based workflow engine, \[Brooks 2, 2015]. 
Several of the authors of this paper are using it to investigate these issues.

It is widely known that tools that make researchers more productive tend to
improve the pursuit of existing research. The high extensibility of ICE and the
tools that it combines from the larger Eclipse ecosystem have made it possible
for researchers on the development team to quickly deploy new simulation 
environments for problems, such as the binder jet modeler and reflectivity 
tools mentioned earlier. Other tools create with ICE may not radically invent
something new, but they tend to streamline interactions with those tools. Many
ICE users (and certainly the development team!) have experienced improvements
in software development activities because of the tools that ICE provides or
learned new technologies because access to new tooling was a simple as 
installing more plugins through the Eclipse Marketplace.

The ICE development team does not track ICE's user base, as useful as that
would be, because of the extra work involved. However, various sources such as
the VIBE mailing list, ICE's own mailing lists, and website download statistics
suggest that ICE has been used by over 350 people at one time or another and 
currently has about twenty "superusers," including the development team.

A new cloud based development tool based on ICE is under development by RNET 
LLC out of Dayton, Ohio in response to an SBIR award from the U.S. Department
of Energy. This web based version of ICE will continue ICE's support for 
nuclear energy and integrate with cloud services such as Amazon Web Services
and Oak Ridge National Laboratory's Compute and Data Environment for Science
(CADES). Additionally, while ICE has not led to the development of "spin-off"
companies, ICE source code has been used in two spin-off projects: EAVP 
(mentioned earlier) for advanced visualizations and the Eclipse January 
project for scientific data structures, \[Graham, 2016]. ICE was also one of
the founding projects of the Eclipse Science Working Group. 

Sample Code, Tutorials and Other Resources
================

The quintessential resource for information on ICE is the projects web page,
\[ICE, 2016]. The "Resources" menu includes links to detailed tutorials and
documentation of multiple types. Examples of how to create new workflow Items
are available at 
https://github.com/eclipse/ice/tree/master/org.eclipse.ice.demo. Examples of
how to use the scripting engine (in this case for neutron reflectivity) are
available at https://github.com/eclipse/ice/tree/master/examples. ICE 
includes an extensive test suite of unit, integration, and UI tests and these
are also excellent examples of how to work with the platform.

Conclusions
===========

Workflows for computational scientists in modeling and simulation differ
greatly from experimentalists or those who primarily interact with grid-based
workflow management systems. This work presented the Eclipse Integrated
Computational Environment (ICE) and described how its has been used to address
interdisciplinary problems in modeling and simulation for energy science. One
interesting avenue for future exploration is coupling or integrating ICE with
other workflow tools such as Aiida, Triquetrum, Kepler and Pegasus. It remains
as abundantly clear now as it was in Mandal's 2007 paper that the best of all 
workflow management tools will be needed to address high-performance computing
problems at the exascale and beyond.

Acknowledgements {#acknowledgements .unnumbered}
================

The authors are grateful for the assistance and support of the following people
and institutions without which this work would not have been possible. This
includes many people who directly contributed to the project, either in its
early days as "NiCE" or once it moved to Eclipse, including: Ronald Allen, 
Andrew Belt, Tim Bohn, David E. Bernholdt, Erica Grant, Mike Guidry, Forest 
Hull, Eric J. Lingerfelt, Sebastien Jourdain, JiSoo Kim, Allison Koenecke, 
Fangzhou Lin, Greg Lyon, Tony McCrary, John M. Hetrick III, Elizabeth 
Piersall, Neeti Pokhriyal, Adrian Sanchez, Claire Saunders, Nick Stanish, 
Matthew Wang, Scott Wittenberg. The support of both the NEAMS and CASL programs
is greatly appreciated. The authors would like to acknowledge the special 
contribution of the Eclipse Foundation, the Eclipse Community, the Eclipse 
Science Working Group and our many colleagues who use and contribute to open 
source projects in the Eclipse ecosystem.

Finally, the development team is especially grateful to Barney Maccabe, David
Pointer and John Turner of Oak Ridge National Laboratory for their endless 
support and advocacy for this work.

This work has been supported by the US Department of Energy, Offices of Nuclear
Energy (DOE-NE) and Energy Efficiency and Renewable Energy (DOE-EERE), and by
the ORNL Undergraduate Research Participation Program, which is sponsored by
ORNL and administered jointly by ORNL and the Oak Ridge Institute for Science
and Education (ORISE). This work was also supported in part by the Oak Ridge
National Laboratory Director's Research and Development Fund. ORNL is managed by
UT-Battelle, LLC, for the US Department of Energy under contract no.
DE-AC05-00OR22725. ORISE is managed by Oak Ridge Associated Universities for
the US Department of Energy under contract no. DE-AC05-00OR22750.

Required Metadata {#required-metadata .unnumbered}
=================

Current code version {#current-code-version .unnumbered}
====================

| --------- | :-----------------------------------------------------------------: | :-------------------------------------------------------- : |
| C1 | Current Code Version | 'next' |
| C2 | Permanent link to code/repository used for this code version | https://github.com/eclipse/ice/tree/next |
| C3 | Legal Code License | Eclipse Public License 1.0 |
| C4 | Code versioning system use | Git |
| C5 | Software code languages, tools, and services used | Java, OSGi, Eclipse Rich Client Platform, and Maven |
| C6 | Compilation requirements, operating environments & dependencies | Java 1.8 or greater, Maven, and an internet connection for dependencies |
| C7 | If available Link to developer documentation/manual  | https://wiki.eclipse.org/ICE |
| C8 | Support email for questions | ice-dev@eclipse.org |

Current executable software version {#current-executable-software-version .unnumbered}
===================================

| --------- | :-----------------------------------------------------------------: | :-------------------------------------------------------- : |
| S1 | Current Code Version | 2.2.1 |
| S2 | Permanent link to executables of this version | http://www.eclipse.org/downloads/download.php?file=/ice/builds/2.2.1/ |
| S3 | Legal Software License | Eclipse Public License 1.0 |
| S4 | Computing platforms/Operating Systems  | Windows (32/64-bit), Mac OS/X, Linux (32/64-bit) |
| S5 | Installation requirements & dependencies | Java 1.8 or greater |
| S6 | If available, link to user manual - if formally published include a reference to the publication in the reference list  | https://wiki.eclipse.org/ICE |
| S7 | Support email for questions | ice-users@eclipse.org |

References
====================================

Yu, Jia, and Rajkumar Buyya. “A Taxonomy of Workflow Management Systems for Grid Computing.” Journal of Grid Computing 3.3–4 (2005): 171–200. link.springer.com. Web.

Billings, Jay J. et al. “Designing a Component-Based Architecture for the Modeling and Simulation of Nuclear Fuels and Reactors.” Proceedings of the 2009 Workshop on Component-Based High Performance Computing. New York, NY, USA: ACM, 2009. 6:1–6:4. ACM Digital Library. Web. 25 May 2016. CBHPC ’09.

Pizzi, Giovanni et al. “AiiDA: Automated Interactive Infrastructure and Database for Computational Science.” Computational Materials Science 111 (2016): 218–230. ScienceDirect. Web.

The Nek5000 Team. “Nek5000 \| A Spectral Element Code for CFD.” Nek5000 Website. 22 Oct. 2014. Web. 29 Dec. 2016.

1. McAffer, Jeff, Paul VanderLei, and Simon Archer. OSGi and Equinox. Boston: Addison-Wesley, 2010. Print.

2. McAffer, Jeff, Jean-Michel Lemieux, and Chris Aniszczyk. Eclipse Rich Client Platform Second Edition. Boston: Addison-Wesley, 2010. Print.

Gaston, Derek et al. “MOOSE: A Parallel Computational Framework for Coupled Systems of Nonlinear Equations.” Nuclear Engineering and Design 239.10 (2009): n. pag. Print.

Fielding, Roy Thomas. “Architectural Styles and the Design of Network-Based Software Architectures.” University of California, Irvine, 2000. Print.

Burke, Bill. RESTful Java with JAX-RS. California: O-Reilly, 2010. Print.

Pontesegger, Christian. “Eclipse Advanced Scripting Environment.” N.p., n.d. Web. 1 Jan. 2017.

Billings, Jay Jay. “Eclipse ICE.” Project Website. Eclipse ICE. N.p., 21 Oct. 2016. Web. 1 Jan. 2017.

Tibbitts, Beth R., Gregory R. Watson, and Craig E. Rasmussen. “An Integrated Approach to Improving the Parallel Application Development Process.” 2009 IEEE International Symposium on Parallel & Distributed Processing (IPDPS). Los Alamitos, CA, USA: IEEE Computer Society, 2009. 1–8. IEEE Computer Society. Web.

Billings, Jay Jay. “Eclipse Advanced Visualization Project.” Text. projects.eclipse.org. N.p., 16 Dec. 2015. Web. 1 Jan. 2017.

Billings, Jay Jay et al. “A Domain-Specific Analysis System for Examining Nuclear Reactor Simulation Data for Light-Water and Sodium-Cooled Fast Reactors.” Annals of Nuclear Energy 85 (2015): 856–868. ScienceDirect. Web.

Billings, Jay Jay. “The Brand New Neutron Reflectivity Simulator in Eclipse ICE and What It Took to Make It.” EclipseCon Europe 2015. Ludwigsburg Germany: Eclipse Foundation, 2015. Web. 25 May 2016.

1. Brooks, Christopher. “Triquetrum: Models of Computation for Workflows.” EclipseCon NA 2016. N.p., 4 Dec. 2015. Web. 1 Jan. 2017.

2. Brooks, Christopher, and Jay Jay Billings. “Introducing Triquetrum, A Possible Future for Kepler and Ptolemy II.” Procedia Computer Science 80 (2016): 2449–2454. ScienceDirect. Web. International Conference on Computational Science 2016, ICCS 2016, 6-8 June 2016, San Diego, California, USA.

Humble, T. S. et al. “An Integrated Programming and Development Environment for Adiabatic Quantum Optimization.” Computational Science & Discovery 7.1 (2014): 015006. Institute of Physics. Web.

McCaskey. “Scientific Simulation with Eclipse - From Zero Code to Running on Lots of Cores in 10 Minutes.” EclipseCon NA 2016. Reston VA: Eclipse Foundation, 2015. Web. 26 May 2016.

Pannala, S. et al. “Multiscale Modeling and Characterization for Performance and Safety of Lithium-Ion Batteries.” Journal of Applied Physics 118.7 (2015): 072017. scitation.aip.org. Web.

“ORNL-QCI/Xacc.” GitHub. N.p., n.d. Web. 1 Jan. 2017.

Mandal, Nandita et al. “Integrating Existing Scientific Workflow Systems: The Kepler/Pegasus Example.” Proceedings of the 2Nd Workshop on Workflows in Support of Large-Scale Science. New York, NY, USA: ACM, 2007. 21–28. ACM Digital Library. Web. 20 Dec. 2016. WORKS ’07.

Graham, Jonah. “Eclipse January.” Text. projects.eclipse.org. N.p., 6 Apr. 2016. Web. 1 Jan. 2017.

Footnotes
====================================

1: The authors have identified many different combinations of these activities 
that define workflow "classes." When possible, every effort is made to give the 
classes funny names such as "The Re-Run" or "The Graduate Student."

2: An upcoming update to the API will see the formal introduction of IWorkflow, 
IWorkflowTask, and IWorkflowEngine interfaces to bring ICE's API language 
closer to other systems. This must be done with care to satisfy backwards
compatibility requirements on the present API.