<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:cus="http://customerservice.example.com/" version="2.0">
	<xsl:output method="xml" />
	<xsl:template match="cus:updateCustomer">
		<xsl:element name="customerList">
			<xsl:apply-templates select="customer" />
		</xsl:element>
	</xsl:template>
	<xsl:template match="customer">
		<xsl:element name="customer">
			<xsl:element name="id">
				<xsl:value-of select="customerId" />
			</xsl:element>
			<xsl:element name="fullName">
				<xsl:value-of select="name" />
			</xsl:element>
			<xsl:element name="postalAddress">
				<xsl:value-of select="address" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>