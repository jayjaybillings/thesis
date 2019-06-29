<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:owl="http://www.w3.org/2002/07/owl#"
	xmlns:xml="http://www.w3.org/XML/1998/namespace"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#">

	<xsl:output method="text" />

	<xsl:template match="/">

		<xsl:text>\section{Classes}</xsl:text>
		<xsl:for-each select="rdf:RDF/owl:Class">
			\subsection{
			<xsl:value-of select="rdfs:label" />
			}
			<xsl:value-of select="rdfs:comment" />
		</xsl:for-each>
		<xsl:text>&#xa;</xsl:text>

		<xsl:text>\section{Object Properties}</xsl:text>
		<xsl:for-each select="rdf:RDF/owl:ObjectProperty">
			\subsection{
			<xsl:value-of select="rdfs:label" />
			}
			<xsl:value-of select="rdfs:comment" />
		</xsl:for-each>
		<xsl:text>&#xa;</xsl:text>

		<xsl:text>\section{Datatype Properties}</xsl:text>
		<xsl:for-each select="rdf:RDF/owl:DatatypeProperty">
			\subsection{
			<xsl:value-of select="rdfs:label" />
			}
			<xsl:value-of select="rdfs:comment" />
		</xsl:for-each>
		<xsl:text>&#xa;</xsl:text>

	</xsl:template>
</xsl:stylesheet>