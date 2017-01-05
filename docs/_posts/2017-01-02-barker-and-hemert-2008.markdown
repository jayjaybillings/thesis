---
layout: post
title:  Scientific Workflow - A Survey and Research Directions
date:   2017-01-02 22:32
category: references
---

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
Management Coalition, \[Hollingsworth, 1995],

```
A Workflow is the automation of a business process, in whole or part, during
which documents, information or tasks are passed from one participant (a 
resource; human or machine) to another for action, according a set of 
procedural rules. 
```

On page 750 of the paper, in their discussion about the Kepler workflow
management system, Barker and Hemert state that

```
Actors are re-usable independent blocks of computation, such as: Web Services,
database calls, etc.
```

Jha and Turilli have proposed using independent "building blocks" as an 
approach to scientific workflows which espouses a similar relationship to 
infrastructure services, \[Jha, 2016]. The similarity is notable and the exact
relationship between Jha's blocks and Actor-Oriented Programming should be
investigated further.

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
ends? John Drake has cited similar concerns for workflow tools at the
National Center for Atmospheric Research (NCAR) and the significant need to
preserve the tools given their non-standard nature.

### Notes

Jha worked with Barker and Hemert around the time they published their article 
as a visiting professor at the University of Edinburgh, so the similarity in 
thinking may come from their close relationship.

### Full Citation
_Barker, Adam, and Jano van Hemert. “Scientific Workflow: A Survey and Research Directions.” Parallel Processing and Applied Mathematics. Springer, Berlin, Heidelberg, 2007. 746–753. link.springer.com. Web. 20 Dec. 2016._

### Additional References

Hollingsworth, David, and U. K. Hampshire. “Workflow Management Coalition the Workflow Reference Model.” Workflow Management Coalition 68 (1993): 26. Print.

Jha, Shantenu, and Matteo Turilli. “A Building Blocks Approach towards Domain Specific Workflow Systems?” arXiv:1609.03484 [cs] (2016): n. pag. arXiv.org. Web. 14 Dec. 2016.

Drake, John. “Re: Billings PhD committee: Introductions and comprehensive exam schedule.” Received by Jay Jay Billings, 11 August 2016.