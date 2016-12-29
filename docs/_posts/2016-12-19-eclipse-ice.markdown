---
layout: post
title:  "The Eclipse Integrated Computational Environment"
date:   2016-12-19 21:19
address: 'Oak Ridge National Laboratory, PO Box 2008 MS6016 Oak Ridge TN 37830'
categories: eclipse
---

_This article is to be submitted to the Journal "Software X" and is thus presented in the journal's required format. Mostly._

# Abstract

FIXME!

FIXME! - keyword 1 ,keyword 2 ,keyword 3

Motivation and Significance
===========================

High performance modeling and simulation software is hard to use. Much of the
challenge comes from the inherent nature of the science
under consideration, but perhaps just as much of the problem comes from
focusing very heavily on raw compute performance. Focusing on performance is
understandable, given the field, but it leaves the rest of us to figure out how
to actually use the software in a way that scales for a much larger set of
users, many of whom may be novices or migrate between software packages regularly.

** REMOVE or merge with text below!** In previous work, Billings et. al., discovered through requirements gathering
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
considers the middle ground solution and presents the Eclipse Integrated
Computational Environment (ICE) as proof that it is possible to create such a
system. Specifically, we show
* an architecture for such a workflow system that accomplishes all five of
the required activities outlined above in a simple, extensible way, \ref{}.
* that such a system can be cross-platform for workstations and
simultaneously deployable on the web, \ref{}.
* that smart design decisions enable not only authoring code for simulation
software, but also make it possible for the system to extend itself, thereby
enabling heavy customization, \ref{}.
* that the system can be easily integrated with other tools, including the
general-purpose workflow engines and single-focus tools on opposite ends of this
tool space, \ref{}.

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
someone else's data.<sup>1</sup>

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
example, Yu and Buyya define workflows as ... FIXME! ..., \[Yu, 2005]. Others 
such as Pizzi et al. subscribe to the same definition, \[Pizzi, 2015]. This 
"process" view is acceptable where the workflow is static and does not require 
additional human input or "human in the loop" behavior after the all the 
initial human input is provided. That is, a "process-oriented" definition is 
acceptable where all human input is provided in advance. However, workflows 
within ICE are fully interactive with regular call-backs to humans. It is 
simpler to discuss "activities" than it is to create a distinction between 
"human processes" and "computer processes." Focusing on "activities" over 
processes (human or computer) also has the benefit of removing concrete 
elements such as hardware properties or software details that distract from 
details of workflows and WMSes per se. That is, considerations such as memory 
usage and raw performance are important, but questions about the abstract 
workflow or what the WMS should do are far more important.

# Software Description

a sdfasdfa


## Software Architecture

ICE's architecture is the standard architecture of Eclipse RCP applications,
which is to say a plugin-based OSGi application.

WORKSPACE

SELF-HOSTING

SYSTEM-CALLS

ITEM-STATES

The architecture of ICE was originally based on a minimal set of use cases that 
were derived from extensive requirements gathering efforts and upfront design, 
in response to needs of the Nuclear Energy Advanced Modeling and Simulation
program, \[Billings, 2009]. 

Software Functionalities
------------------------


Sample code snippets analysis (optional)
----------------------------------------

Illustrative Examples
=====================

Impact
======


Conclusions
===========


Acknowledgements {#acknowledgements .unnumbered}
================


Required Metadata {#required-metadata .unnumbered}
=================

Current code version {#current-code-version .unnumbered}
====================


  **Nr.**   **Code metadata description**                                     **Please fill in this column**
  --------- ----------------------------------------------------------------- --------------------------------------------------------
  C1        Current code version                                              For example v42
  C2        Permanent link to code/repository used for this code version      For example: $https://github.com/mozart/mozart2$
  C3        Legal Code License                                                List one of the approved licenses
  C4        Code versioning system used                                       For example svn, git, mercurial, etc. put none if none
  C5        Software code languages, tools, and services used                 For example C++, python, r, MPI, OpenCL, etc.
  C6        Compilation requirements, operating environments & dependencies   
  C7        If available Link to developer documentation/manual               For example: $http://mozart.github.io/documentation/$
  C8        Support email for questions                                       

  : Code metadata (mandatory)<span data-label=""></span>

Current executable software version {#current-executable-software-version .unnumbered}
===================================


  **Nr.**   **(Executable) software metadata description**                                                                           **Please fill in this column**
  --------- ------------------------------------------------------------------------------------------------------------------------ -----------------------------------------------------------------------------------------------------------------
  S1        Current software version                                                                                                 For example 1.1, 2.4 etc.
  S2        Permanent link to executables of this version                                                                            For example: $https://github.com/combogenomics/$ $DuctApe/releases/tag/DuctApe-0.16.4$
  S3        Legal Software License                                                                                                   List one of the approved licenses
  S4        Computing platforms/Operating Systems                                                                                    For example Android, BSD, iOS, Linux, OS X, Microsoft Windows, Unix-like , IBM z/OS, distributed/web based etc.
  S5        Installation requirements & dependencies                                                                                 
  S6        If available, link to user manual - if formally published include a reference to the publication in the reference list   For example: $http://mozart.github.io/documentation/$
  S7        Support email for questions                                                                                              

  : Software metadata (optional)<span data-label=""></span>


References
====================================

Yu, Jia, and Rajkumar Buyya. “A Taxonomy of Workflow Management Systems for Grid Computing.” Journal of Grid Computing 3.3–4 (2005): 171–200. link.springer.com. Web.

Billings, Jay J. et al. “Designing a Component-Based Architecture for the Modeling and Simulation of Nuclear Fuels and Reactors.” Proceedings of the 2009 Workshop on Component-Based High Performance Computing. New York, NY, USA: ACM, 2009. 6:1–6:4. ACM Digital Library. Web. 25 May 2016. CBHPC ’09.

Pizzi, Giovanni et al. “AiiDA: Automated Interactive Infrastructure and Database for Computational Science.” Computational Materials Science 111 (2016): 218–230. ScienceDirect. Web.

The Nek5000 Team. “Nek5000 \| A Spectral Element Code for CFD.” Nek5000 Website. 22 Oct. 2014. Web. 29 Dec. 2016.



1: The authors have identified many different combinations of these activities 
that define workflow "classes." When possible, every effort is made to give the 
classes funny names such as "The Re-Run" or "The Graduate Student."