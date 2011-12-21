<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<head>
				<title>CD Collection</title>
			</head>
			<body>
				<h1>CD Collection</h1>
				<xsl:for-each select="//cd">
					<br />
					<table>
						<tr>
							<th>Artist</th>
							<td><xsl:value-of select="artist" /></td>
						</tr>
						<tr>
							<th>Title</th>
							<td><xsl:value-of select="title" /></td>
						</tr>
						<tr>
							<th>Year</th>
							<td><xsl:value-of select="year" /></td>
						</tr>
					</table>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>