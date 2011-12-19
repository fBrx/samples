<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">

		<xsl:comment>
			XSLT Version = <xsl:copy-of select="system-property('xsl:version')" />
			XSLT Vendor = <xsl:copy-of select="system-property('xsl:vendor')" />
			XSLT Vendor URL = <xsl:copy-of select="system-property('xsl:vendor-url')" />
		</xsl:comment>

	</xsl:template>

</xsl:stylesheet>