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
feedback-driven way where solutions and workflows are developed iteratively and
simultaneously. This work describes common activities in workflows, how 
combinations of these activities form unique workflows, and presents the Eclipse
Integrated Computational Environment as a workflow management system and
development environment for the modeling and simulation community. Examples of
the environment's applicability to problems in energy science, general 
multiphysics simulations, quantum computing, and other areas are presented along 
with the environment's impact on the community and user base.

Motivation and Significance
===========================

In previous work, Billings et. al., discovered through requirements
gathering interviews that many of the difficulties using high-performance 
modeling and simulation software fall broadly into five
distinct categories, @billings_designing_2009. These activities,
detailed in section [workflow-model], include (1) creating input, (2) executing jobs,
(3) analyzing results, (4) managing data, and (5) modifying code. There are many
tools that address these problems individually, but the same research found
that the excess number and specialization of these tools also contribute
to the learning curve.

Efforts to address these five issues have previously resulted in general
purpose scientific workflow tools like Kepler
@ludascher_scientific_2006 or myopic tools that only satisfy
a single set of requirements for a single piece of software or platform.
These are opposing extremes, but a middle-of-the-road solution is also
possible. A workflow engine could be developed that limits its scope to
high-performance computing (HPC) and to the set of possible workflows
associated with the five previously mentioned activities. With only 
minor additional development, a rich application programming interface (API) 
could be exposed so that highly-customized solutions could still be 
made based on this limited workflow engine.

It is not clear which, if any, of these solutions is better than the others, and
practical requirements will ultimately dictate the path of a project's progress.
This work considers a middle ground solution and presents the Eclipse
Integrated Computational Environment (ICE) as proof that it is possible
to create such a system. Specifically, the work described here shows that:

-   Modeling and simulation activities can be described in a
    succinct workflow model, (see "Workflow Model").

-   An architecture for such a workflow system can satisfy the model
    of workflows in an extensible way, (see "Software 
    Architecture").

-   Such a system is applicable to a suite of problems in energy
    science, including virtual battery simulations, additive
    manufacturing and other areas, (see "Illlustrative Examples").

This section concludes with an introduction of the workflow model
addressed by ICE. "Software Description" presents the details of
the software from an architecture perspective, while the "Illustrative 
Examples" section provides a set of comprehensive examples. Finally,
this paper concludes with a presentation of the impact and sample code.

### Workflow Model

Computational scientists perform a variety of tasks in modeling
and simulation that can be abstracted into a lightweight theoretical framework 
based broadly on five high-level _activities_: (1) creating input, (2) executing jobs, 
(3) analyzing results, (4) managing data, and (5) modifying code. Those same computational 
scientist would most likely find these activities difficult for all codes with 
which they lack experience, whereas with their own codes&mdash;or those with which 
they are most familiar&mdash;these tasks may be so simple that they are taken for 
granted. Any particular combination of these activities across one or more 
scientific software package or code results in a unique _workflow_. Such a 
workflow is normally, but not always, requested by a human user and 
orchestrated by a _Workflow Management System (WMS)_.

The most obvious workflow for any individual simulation code or collection of
codes is to string the activities together: the user's workflow is to create
the input, launch the job, perform some analysis, and manage the data&mdash;possibly 
modifying the code in the process. There are many other combinations
however, including re-running jobs with conditions or modifications or analyzing 
someone else's data (See footnote <sup>1</sup>).

**Creating input** is the process of describing the physical model or state
of a system that will be simulated. This could include creating an input file(s) 
or making calls to an external process to configure a
running program. In most situations, a computational scientist will modify 
existing input or create new input from a template. "Input" generally includes
runtime parameters for the simulation framework (e.g., tolerances); 
configuration options (e.g., data locations, output locations, module configurations); 
properties of the materials to be simulated; and a 
discretization of the simulation space (e.g., mesh, grid, particle distribution). 
The collection of all required input can be quite large and may go by
many names, including "input set," "input package," "problem," or, simply, 
"input." Often, the set of input files will be described in a "main" input file
that acts as a kind of manifest, describing and providing links to all 
necessary information for a given problem. 

In this work, it should be assumed&mdash;unless otherwise noted&mdash;that "input"
refers to the entire set of input, not to a single file.

[comment]: # (The following sub-subsection got a good workover; make sure I didn't overstep. stc)
**Executing jobs**, or "running the workflow" in this context, is the process 
of performing calculations using a simulation code or framework 
based on known variables&mdash;the input. 
Models and simulation codes are typically run locally for small
jobs or for development. Large simulations, on the other hand, 
typically require a large amount of hardware resources; these resources 
are usually off site (i.e., physically unavailable to the user)
and are accessed remotely remotely through Secure Shell (SSH)
connections or similar protocols. Remote execution requires moving the input
in advance of the execution and copying or moving the output to the user's 
machine. In many cases, though, the output is too large to move to the user's local machine.

Local and remote jobs are often monitored to ascertain a job's status. 
This monitoring may be a simple check as to whether or not the execution has 
completed, or it may involve monitoring the output of individual quantities to examine the 
calculation state. The latter is often used to detect calculation errors that 
will result in incorrect results. If such problems are found, the job is 
typically cancelled ("killed") to save compute resources and is then re-run later. 

In this work, it should be assumed&mdash;unless otherwise noted&mdash;that "executing a 
job" includes monitoring that job in one or more ways, possibly including
real-time updates to visualizations.

**Analyzing results** includes executing special jobs to transform data in one
or more prescribed ways and producing artifacts with scientific significance
from the transformed data. This may include, for example, post-processing 
results and visualizing the new data with dedicated visualization tools. For
many types of scientific computing, this includes viewing the results of a 
simulation on a mesh or grid and extracting publication quality images or 
movies from that data. Other cases may include analyzing results in preparation
for follow simulations or performing feature extraction, classification, or 
activities for machine learning and data mining. 

While this has many similarities to executing a job, it is distinctly different 
because the activity changes focus to satisfy the needs of a human operator. 
Simple data reduction, where the exact reduction is known, certainly qualifies as
executing a job; however, analysis of model and simulation results is far from
simple data reduction, and is generally far more interactive for
computational scientists.

**Managing data** includes moving, copying, storing, sharing, or otherwise 
interacting with data for or from simulations. This activity is the most 
pervasive because each of the other activities requires interacting
with data in some way. In many cases, though, data is still managed for its own
purposes, without performing a simulation, generating new input, or analyzing
results. Examples include archiving data, packaging data for publications, and 
updating values manually (often in light of new information from publications).

**Modifying code** is not typically considered a part of a scientific computing
workflow. However, modeling and simulation use cases often require users to 
explicitly modify code before execution, such as with the computational fluid
dynamics code Nek5000, \[Nek5000], or to issue special re-build instructions. Many 
computational scientists consider "their workflow" to be re-running software 
after modifications for purely exploratory purposes. This may be required if 
the model that the author is manipulating cannot be configured directly as 
part of the input but can be easily manipulated by hand.

### Comparison to Other Models

This model of workflows differs significantly from those of similar efforts in 
workflow science because it defines workflows in terms of activities. Other workflow 
models in the literature define a workflow as a collection of computing processes. For 
example, Yu and Buyya define grid workflows as "a collection of tasks that are
processed on distributed resources in a well-defined order to accomplish a 
specific goal," \[Yu, 2005]. Others, such as Pizzi et al., subscribe to similar
definitions, \[Pizzi, 2015]. This "process" view is acceptable where the 
workflow is static and does not require additional human input or "human in 
the loop" behavior after the all the initial human input is provided. That is, 
a "process-oriented" definition is acceptable where all human input is provided
in advance. However, workflows within ICE are fully interactive with regular 
call-backs to humans. It is simpler to discuss "activities" than it is to 
create a distinction between "human processes" and "computer processes." 
Focusing on "activities" over processes (human or computer) also has the 
benefit of removing concrete elements such as hardware properties or software 
details that distract from details of workflows and WMSes. That is, 
considerations such as memory usage and raw performance are important, but 
questions about the abstract workflow or what the WMS should do are far more 
important.

[comment]: # (Please define the WMS acronym in its first instance in the paragraph above.)

