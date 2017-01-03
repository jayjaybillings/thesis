---
layout: post
title:  A Taxonomy of Adaptive Workflow Management
date:   2017-01-02 21:45
category: references
---

Human involvement is critical in some workflows which requires adaptive
management, as shown by Han and Bussler, \[Han, 2002]. Their work considers
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
this context they also share some important wisdom that is equally applicable
to scientific workflows:

```
Workflow systems do not exist for their own purposes. They are a constituent
component of a business system. A business system is usually domain specific.
```

This is an important consideration for scientific workflows because the
business logic of scientific workflows is "domain logic" that is highly
specific to the scientific domain under consideration. This necessarily leads 
to a diverse ecosystem of workflow systems.

### Full Citation
_Han, Yanbo, Amit Sheth, and Christoph Bussler. “A Taxonomy of Adaptive Workflow Management.” Workshop of the 1998 ACM Conference on Computer Supported Cooperative Work. N.p., 1998. Google Scholar. Web. 20 Dec. 2016._


