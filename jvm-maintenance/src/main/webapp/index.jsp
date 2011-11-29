<%@page import="com.github.samples.jvmmaintenance.ActionServlet.ACTIONS"%>
<%@page import="com.github.samples.jvmmaintenance.ActionServlet"%>
<html>
<head>
<title>JVM Maintenance App</title>
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.16.custom.css"
	rel="stylesheet" />
<link type="text/css" href="css/ui-darkness/style.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#tabs').tabs();
	});
</script>
</head>
<body>
	<h1>JVM Maintenance App</h1>

	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Operations</a></li>
			<li><a href="#tabs-2">JVM Status</a></li>
			<li><a href="#tabs-3">System Properties</a></li>
			<li><a href="#tabs-4">System Environment</a></li>
		</ul>
				<div id="tabs-1">
			<h3><a href="#">Operations</a></h3>
			<div>
				<ul>
					<li><a href="action?<%=ActionServlet.OPERATION_PARAM%>=<%=ACTIONS.GC%>">Perform Garbage Collection</a></li>
					<li><a href="action?<%=ActionServlet.OPERATION_PARAM%>=<%=ACTIONS.HEAPDUMP%>">Generate Heap Dump</a></li>
				</ul>
			</div>
		</div>
		<div id="tabs-2">
			<h3><a href="#">JVM Status</a></h3>
			<div>
				<ul>
					<li>Available Processors: <%=Runtime.getRuntime().availableProcessors()%></li>
					<li>Free Mem: <%=Runtime.getRuntime().freeMemory() / 1024 / 1024%>MB
					<li>Total Mem: <%=Runtime.getRuntime().totalMemory() / 1024 / 1024%>MB
					<li>Max Mem: <%=Runtime.getRuntime().maxMemory() / 1024 / 1024%>MB
				</ul>
			</div>
		</div>
		<div id="tabs-3">
			<h3><a href="#">System Properties</a></h3>
			<div>
				<ul>
					<%
						for (Object key : System.getProperties().keySet()) {
							String value = System.getProperty(key.toString());
							out.println("<li>" + key + " = " + value + "</li>");
						}
					%>
				</ul>
			</div>
		</div>
		<div id="tabs-4">
			<h3><a href="#">System Environment</a></h3>
			<div>
				<ul>
					<%
						for (String key : System.getenv().keySet()) {
							String value = System.getenv(key);
							out.println("<li>" + key + " = " + value + "</li>");
						}
					%>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
