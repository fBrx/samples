<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<head>
				<title><xsl:value-of select="/opml/head/title" /></title>
			</head>
			<body>
				<ul>
					<xsl:apply-templates select="/opml/body/outline" />
				</ul>
			</body>
		</html>
	</xsl:template>

	
	<xsl:template match="outline">
		<li><xsl:value-of select="@text" /></li>
		<ul>
			<xsl:apply-templates select="outline" />
		</ul>
	</xsl:template>
</xsl:stylesheet>