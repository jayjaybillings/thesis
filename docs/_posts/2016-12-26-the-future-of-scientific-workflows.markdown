---
layout: post
title:  The Future of Scientific Workflows
date:   2016-12-26 15:33
category: references
---

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

Research in "Workflow Science" would appear to be an especially important
suggestion of the report, and it represents the first time this author has
encountered this concept in the literature. The report states that "Workflow
Science is a new field that studies the formal theory and principles of 
workflow systems, develops analytical models for their performance and 
validation, and empirically measures the predicted behavior through
experimentation." A formal theory of workflows would be especially valuable
in efforts to develop interoperability frameworks or create common building
blocks.

The report contains several notable mistakes that should be mentioned.<sup>1</sup> 
The scope of workflow management systems is limited to those systems under 
development by attendees and does not consider larger community efforts. For 
example, the [BigPanDA project](http://news.pandawms.org/bigpanda.html) is not 
discussed, even though it is funded by the sponsors of the workshop. The report 
also incorrectly states that the state of the art in HPC workflows is execution 
via Python scripts or embedding in the simulation environment. Although such 
execution is common practice, this author's research record would suggest that 
it is hardly the state of the art, as would the work by several attendees to 
the same workshop (notably that of Ernest "Foss" Friedman-Hill described in 
\[Clay, 2015]), and that of 
[Pizzi et. al](2016-12-26-pizzi-aiida-2015.markdown).

### Full Citation
*Deelman, Ewa, Tom Peterka, Ilkay Altintas, Christopher Carothers, Kerstin Kleese van Dam, Kenneth Moreland, Lavanya Ramakrishnan, and Jeffrey Vetter. “The Future of Scientific Workflows.” Workshop. U.S. Department of Energy, April 20, 2015. http://science.energy.gov/~/media/ascr/pdf/programdocuments/docs/workflows_final_report.pdf.*

### Other References
*Clay, Robert L. “Incorporating Workflow for V&v/Uq in the Sandia Analysis Workbench.” Sandia National Laboratories (SNL-CA), Livermore, CA (United States), March 1, 2015. https://www.osti.gov/scitech/biblio/1241123.*

### Footnotes
1: Mistakes are common with "community" workshops because the final
report often lacks rigorous external peer review. This is not a significant
concern in this case. 


