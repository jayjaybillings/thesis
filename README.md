# Thesis

This is the git repository for my PhD thesis and related research. It acts simulatenously as a testing ground for new ideas, an aggregator of my related publications, and the log of my research for that work.

## Prereqs

LaTeX
pdflatex
BibTex
BibTool
git

## Recommended Tools

Texlipse
Overleaf

## Setup

For Texlipse, create bin/ and tmp/ directories. Git will ignore these. Texlipse needs them for the build. Texlipse will automatically try to build the project once Eclipse is started. 

## Building the bibliography

1) From the base directory ("thesis"), run mergeBibs.sh
2) Enter the tmp directory
3) Copy the bib.bib file from the parent directory into the tmp directory
4) Run bibtex
5) Rebuild or tell Texlipse to rebuild

## Building Graphs

The graphs in the document were created using .dot file exports from the OntoGraf plugin in Protege. Dot files can be converted to images using Graphviz.

1) Install Graphviz
2) Enter the figures directory
3) Run dot, e.g. -

```bash
dot -Tpng action.dot -o action.png
```
