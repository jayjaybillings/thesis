<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:owl="http://www.w3.org/2002/07/owl#"
	xmlns:xml="http://www.w3.org/XML/1998/namespace"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#">

	<xsl:output method="text" />

	<xsl:template match="owl:Class">
	\subsubsection{<xsl:value-of select="rdfs:label" />}
	<xsl:value-of select="rdfs:comment" />
	</xsl:template>
</xsl:stylesheet>