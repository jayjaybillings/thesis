#!/bin/bash

cp -r ../../images src/images

pandoc -o src/introduction.tex ../../_posts/2017-01-04-introduction.markdown
sed -i '1s/^/\\chapter{Introduction}\n\n/' src/introduction.tex

pandoc -o src/workflows-review.tex ../../_posts/2017-01-04-workflows-review.markdown
sed -i '1s/^/\\chapter{A review of workflows and taxonomies}\n\n/' src/workflows-review.tex

#pandoc -o src/eclipse-ice.tex ../../_posts/2016-12-19-eclipse-ice.markdown
#sed -i '1s/^/\\chapter{The Eclipse Integrated Computational Environment}\n\n/' src/eclipse-ice.tex

pandoc -o src/problems.tex ../../_posts/2017-01-05-problems.markdown
sed -i '1s/^/\\chapter{Problems}\n\n/' src/problems.tex

pandoc -o src/proposal.tex ../../_posts/2017-01-05-proposal.markdown
sed -i '1s/^/\\chapter{Work Proposal}\n\n/' src/proposal.tex