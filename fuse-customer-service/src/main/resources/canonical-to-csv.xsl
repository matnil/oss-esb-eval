<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="text" />
	<xsl:template match="/">
		<xsl:for-each select="//customer">
<xsl:value-of select="id" />,<xsl:value-of select="fullName" /><xsl:text>&#xD;</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>