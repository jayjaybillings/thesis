---
layout: post
title:  A review of workflows and taxonomies
date:   2017-01-04 20:13
category: background
---

# Workflows

As mentioned previously, one of the most challenging aspects of studying 
workflows is the way the vocabulary has been overloaded unintentionally. It is
somewhat clearer to understand them by considering the historial perspective. 

The use and study of workflows and the initial implementation of workflow 
management systems developed in the business world with the need to automate
business processes. Ludäscher et al. ascribe the origins of workflows and
workflow management systems to "office automation," trends in the 1970s,
\[Ludäscher, 2006]. Van Der Aalst argues that "workflows" arose from the needs
of businesses to not only execute tasks, but "to manage the flow of work 
through the organization," and that managing workflows is the natural evolution
from the monolithic applications of the 1960s to applications that rely on 
external functionality in the 1990s, \[Van der Aalst, 1998]. (One might argue
that Van Der Aalst's depiction continues today with the development of the 
growth of the "microservices" architectural movement.) By 1995, in the presence
of many workflow tools, the Workflow Management Coalition had developed a
"standard" definition of workflows, \[Hollingsworth, 1995],

```
A Workflow is the automation of a business process, in whole or part, during
which documents, information or tasks are passed from one participant (a 
resource; human or machine) to another for action, according a set of 
procedural rules. 
```

In the early 2000s, workflow systems started finding use in scientific
contexts where process automation was required for scientific uses instead of
traditional business uses. The focus of scientific workflows, at the time,
also shifted to focus primarily on data processing for large "grids" of 
networked services, \[Yu, 2005]. Yu and Buyya define a workflow as a

```
... a collection of tasks that are processed on distributed resources in a
well-defined order to accomplish a specific goal.
```

This definition is important because of what is missing: the human element. For
many in the scientific workflows community this has become the standard
definition of a workflow and the involvement of humans results not in a single
workflow, but two workflows spanned by a human. Machines or instruments are
absent from the definition as well, but in practice many modern scientific
workflows are launched automatically when data "comes off" of instruments
because they remain the primary source of data in these types of workflows, 
(c.f. - \[Megino, 2015]).

In addition to "grid workflows," the scientific community started exploring
"modeling and simulation" workflows which focus not on data flow, but on the 
orchestration of activities related to modeling and simulation instead,
sometimes on small local computers, but often on the largest of the world's 
"Leadership Class" supercomputers. These workflows typically fall into a subset
of their more general cousins that can be found in the grid or business
communities, but unlike grid workflows they tend to require human interaction
in one way or another. Some of these workflows are defined in the context of a
particular way of working, such as the Automation, Data, Environment, and
Sharing (ADES) model of Pizzi et al., \[Pizzi, 2015], the "Design-to-Analysis"
model of Clay et al., \[Clay, 2015], or the model of Billings et al. presented
later in this work during the discussion on the Eclipse Integrated
Computational Environment. However, many scientific workflows, while
exceptionally well defined, remain hard coded into dedicated environments
developed for the sole purpose of executing that one or at most a few related
workflows, as cited by John Drake for workflow tools developed at the National
Center for Atmospheric Research (NCAR) and the significant need to preserve the
tools given their non-standard nature, \[Drake, 2016].

Additional types of workflows in the scientific community include workflows
that process ensembles of calculations, \[Montoya, 2016], and workflows that
are used for testing software.

# Taxonomies and Classification

There have been several efforts classify, survey or develop taxonomies for
workflows. Yu and Buyya are the only source that provides what can be truly
considered a "taxonomy," showing the hierarchical relationships between
workflow concepts. Most of the other authors discussed below, while claiming to
produce taxonomies, in fact produce controlled vocabularies.

Human involvement is critical in some workflows which requires adaptive
management, as shown by Han and Bussler, \[Han, 1998]. Their work considers
adaptive workflow management in the context of healthcare workflows and
argues that workflow technology in 2002 was incapable of adapting sufficiently
to meet the unexpected needs of medical personnel. Along with unexpected needs
("changing environment"), they cite the evolution of software systems
("technical advances") as another critical area that leads to required changes
in workflow management systems. 

Han and Bussler discuss situations that lead to "ad-hoc derivation" of 
workflows. "Ad-hoc derivation" in this case means generating extra workflow 
steps or details, such as converting from an abstract to a concrete workflow as
Pegasus and other grid workflow systems do. Specifically, Han and Bussler cite
dynamic refinement, user involvement, unpredictable events and erroneous
situations as systems that require the workflow to behave in an unplanned way,
but as situations for which workflow managements systems should be prepared.
Meta-models, open-point (more commonly known as "extension point") or hybrid 
approaches are proposed as solutions.

It is important to note that Han and Bussler consider only business workflows
and management systems, not scientific workflows and management systems. In
this context they also share some important wisdom that is, arguably, of equal
importance to scientific workflows:

```
Workflow systems do not exist for their own purposes. They are a constituent
component of a business system. A business system is usually domain specific.
```

This is an important consideration for scientific workflows because the
"business logic" of scientific workflows is "domain logic" that is highly
specific to the scientific domain under consideration. This necessarily leads 
to a diverse ecosystem of workflow systems.

Yu and Buyya developed a taxonomy for workflow management systems on grids that
sought to capture the architectural style while identifying comparison 
criteria, \[Yu, 2005]. Their work is notable because it largely avoids a 
discussion of applications and focuses purely on the functional properties of 
the workflow management systems as they exist on the grids. Yu and Buyya root 
their taxonomy on five core elements of grid-based workflow management systems: 
workflow design, information retrieval, workflow scheduling, fault tolerance, 
and data movement. While many of the properties and taxonomic elements they 
describe seem common to all systems, others, such as "Workflow QoS Constraints"
would appear to be grid-specific at present. Their work also shows how thirteen
common grid workflow management systems, such as Pegasus and Kepler, are
covered by the taxonomy. Like other authors, Yu and Buyya cite the lack of 
standardized workflow syntax and language as sources of interoperability
issues. Yu's and Buyya's work is extremely detailed and a very helpful resource
for understanding grid workflows, but that is the extent to which the article
investigates workflows.

Scientific workflow management systems have flourished since their inception,
although not without significant overlap and duplication of effort. The survey
of scientific workflow management systems by Barker and Hemert illustrates both
the growth and problems while also providing important observations and
recommendations on the topic, \[Barker, 2008].

Barker and Hemert also provide key insights into the history of workflows
management systems as an important part of business automation. The authors 
make an important comparison between traditional business workflow management 
systems and their scientific counterparts, citing in particular that 
traditional business workflow tools employ the wrong abstraction for scientists
They define workflows using the "standard" definition from the Workflow 
Management Coalition, previously mentioned above.

Notably, in their discussion about the Kepler workflow management system, 
Barker and Hemert state that

```
Actors are re-usable independent blocks of computation, such as: Web Services,
database calls, etc.
```

Jha and Turilli have proposed using independent "building blocks" as an 
approach to scientific workflows which espouses a similar relationship to 
infrastructure services, \[Jha, 2016]. The similarity is notable and the exact
relationship between Jha's blocks and Actor-Oriented Programming merit further
investigation.

The discussion points that Barker and Hemert raise are important because
of their continuing importance and relevance today, particularly the need to
enable programmability through standard languages instead of custom, 
proprietary languages. (The reader is encouraged to read the Barker and 
Hemert's entire paper for more details.) Sticking to standards is also 
important and perhaps illustrated best by Barker's and Hemert's statement that

```
If software development and tool support terminates on one proprietary 
framework, workflows will need to be re-implemented from scratch.
```

This is an important point even for workflow tools that do not use proprietary
standards, but "roll their own" solutions. What can be done to support those
tools and reproduce those workflows once support for continued development 
ends?

Montoya et al. discuss workflows needs for the Alliance for Application 
Performance at Extreme Scale (APEX), \[APEX, 2017], and describe three main
classes of workflows: Simulation Science, Uncertainty Quantification (UQ), and
High Throughput Computing (HTC), \[Montoya, 2016]. HTC workflows start with the
collection of data from experiments that is in turn transported to large
compute facilities for processing. Many grid workflows are HTC workflows, but
not all HTC workflows are grid workflows since some HTC workflows, such as
those those presented by Montoya et al., maybe be run on large resources that
are not traditionally "grid machines." Simulation science workflows, referred
to above as modeling and simulation workflows, are those workflows that are
primarily focused on modeling and simulation activities. UQ workflows build on
modeling and simulation workflows by, essentially, executing ensembles of jobs
or ensembles of whole workflows to quantify uncertainty in simulation results.
Montoya et al. also provide a detailed mapping of each workflow type to optimal
hardware resources for the APEX program.

The U.S. Department of Energy sponsored the _DOE NGNS/CS Scientific Workflows 
Workshop_ on April 20-21st 2015. In the report, Deelman et al. describe the
requirements and research directions for scientific workflows for the exascale
environment, [Deelman, 2015]. The report broadly covers the requirements of the
DOE Complex, although often with too much of a focus on performance and 
grid-computing workflows, the latter being very uncommon in the DOE community.
The report describes scientific workflows primarily by three application types:
Simulations, Instruments, and Collaborations. The findings of the workshop are
comprehensive and encouraging, with recommendations for research priorities in
Application Requirements, Hardware Systems, System Software, WMS Design and 
Execution, Programming and Usability, Provenance Capture, Validation, and 
Workflow Science. 

The definitions of a "workflow" and "workflow management systems" are 
thoroughly explored and put into context for the purposes of the workshop. The
authors of the report are very careful to define workflows not just as a 
collection of managed processes, which is common, but in such a way that it is
clear that reproducibility, mobility and some degree of generality are 
required by both the description of the workflow and the management system.
(The report appears to provides three separate definitions for "workflow" on 
pages 6, 9 and 10.)

The brief summary of different workflow models above is a sample of the
confusion in the "marketplace" and illustrates the lack of a coherent
understanding of workflows.

The next section presents the workflow model, system architecture and
applications of the Eclipse Integrated Computational Environment. This model
limits its scope to high-performance computing (HPC) and to the set of possible
workflows that come from input generation and preprocessing (also called
``model setup''); job execution and monitoring; postprocessing, visualization
and data analysis; data management; and customizing the software. However, as
"limited" as ICE's model may be, it shows significant ability to interoperate
with other workflow engines. This and other qualities of the system are why it
is revisited later as a proposed platform for testing an interoperability
layer.

# References

_Van Der Aalst, W. M. P. “The Application of Petri Nets to Workflow Management.” Journal of Circuits, Systems and Computers 08.01 (1998): 21–66. worldscientific.com (Atypon). Web._

_Ludäscher, Bertram et al. “Scientific Workflow Management and the Kepler System.” Concurrency and Computation: Practice and Experience 18.10 (2006): 1039–1065. Wiley Online Library. Web._

_Hollingsworth, David, and U. K. Hampshire. “Workflow Management Coalition the Workflow Reference Model.” Workflow Management Coalition 68 (1993): 26. Print._

_Yu, Jia, and Rajkumar Buyya. “A Taxonomy of Workflow Management Systems for Grid Computing.” Journal of Grid Computing 3, no. 3–4 (September 1, 2005): 171–200. doi:10.1007/s10723-005-9010-8._

_Megino, Fernando Barreiro et al. “PanDA: Evolution and Recent Trends in LHC Computing.” Procedia Computer Science 66 (2015): 439–447. ScienceDirect. Web. 4th International Young Scientist Conference on Computational Science._

_Montoya, David. “APEX Workflows.” N.p., n.d. Web. 23 Dec. 2016._

_Pizzi, Giovanni, Andrea Cepellotti, Riccardo Sabatini, Nicola Marzari, and Boris Kozinsky. “AiiDA: Automated Interactive Infrastructure and Database for Computational Science.” Computational Materials Science 111 (January 2016): 218–30. doi:10.1016/j.commatsci.2015.09.013._

_Clay, Robert L. Incorporating Workflow for V&v/Uq in the Sandia Analysis Workbench. Sandia National Laboratories (SNL-CA), Livermore, CA (United States), 2015. www.osti.gov. Web. 26 Dec. 2016._

_Drake, John. “Re: Billings PhD committee: Introductions and comprehensive exam schedule.” Received by Jay Jay Billings, 11 August 2016._

_Han, Yanbo, Amit Sheth, and Christoph Bussler. “A Taxonomy of Adaptive Workflow Management.” Workshop of the 1998 ACM Conference on Computer Supported Cooperative Work. N.p., 1998. Google Scholar. Web. 20 Dec. 2016._

_Jha, Shantenu, and Matteo Turilli. “A Building Blocks Approach towards Domain Specific Workflow Systems?” arXiv:1609.03484 [cs] (2016): n. pag. arXiv.org. Web. 14 Dec. 2016._

_“APEX.” N.p., n.d. Web. 5 Jan. 2017._
