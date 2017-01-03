---
layout: post
title:  A Building Blocks Approach towards Domain Specific Workflow Systems
date:   2017-01-03 14:54
category: references
---

For many reasons, the traditional approach for building workflow systems has
been to build as much of the required capability as possible into the system
itself, relying very little on external services or even third party code. This
does not track will with the course of history though since high-level 
functionality tends to slowly creep down the software stack and into kernels
and kernel services. Jha and Turilli discuss this trend as it relates to 
workflows from a cyber infrastructure perspective and to existing large scale
scientific workflow efforts, \[Jha, 2016]. They propose that, while 
historically successful, monolithic workflow systems present many problems for
users, developers, and maintainers. Instead, they propose that a new "Lego 
style" approach might work better where individual "building blocks" of 
capability are assembled into the final workflow product. These building blocks
would includes things like programming interfaces to queuing systems, 
programmable pilot systems for scheduling jobs, workload balancers, and the
ensemble execution tools, among others.

Jha and Turilli provide an ambiguous definition of workflows, stating that 
workflows are both comprised of tasks and provide a description of the 
resources and constraints for each task. The ambiguity rises from the
definition of "tasks." If tasks are compute processes, then their definition is
equivalent to that of grid workflows. However, if a task could include human 
interaction during execution, then they have a much broader definition than 
what is normally found in the grid literature, and their definition more
closely resembles a business workflow. Building blocks, on the other hand, 
are concretely defined as those pieces of middleware that are self-sufficient,
interoperable, composable, and extensible, (with detailed definitions of each
provided). The RADICAL-Cybertools suite of software modules from Jha's group is
presented as a sample set of building blocks and two case studies where these
tools were successfully as such are presented.

The definition of a building block bears a striking resemblance to the concept
of Actor-Oriented Programming, which has been previously noted as well. For 
example, when talking about the Kepler workflow systems' Actor-Oriented design,
Barker and Hemert, \[Barker, 2008], said the following on page 750 of their 
paper:

```
Actors are re-usable independent blocks of computation, such as: Web Services,
database calls, etc.
```

The similarity is notable and the exact relationship between Jha's blocks and
Actor-Oriented Programming should be investigated further.

### Notes

Jha worked with Barker and Hemert around the time they published their article 
as a visiting professor at the University of Edinburgh, so the similarity in 
thinking may come from their close relationship.

### Full Citation

_Jha, Shantenu, and Matteo Turilli. “A Building Blocks Approach towards Domain Specific Workflow Systems?” arXiv:1609.03484 [cs] (2016): n. pag. arXiv.org. Web. 14 Dec. 2016._

### Additional References

Barker, Adam, and Jano van Hemert. “Scientific Workflow: A Survey and Research Directions.” Parallel Processing and Applied Mathematics. Springer, Berlin, Heidelberg, 2007. 746–753. link.springer.com. Web. 20 Dec. 2016.
