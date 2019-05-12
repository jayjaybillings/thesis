#!/bin/bash
# This script will merge the bibliographies from the papers in the pubs directory into a single bib file.
bibtool -s -d pubs/billings-workflows-blockchain/phdThesis.bib pubs/ice-softwarex-2017/src/bib.bib pubs/workflows-ontology-paper/src/bib.bib > bib.bib
