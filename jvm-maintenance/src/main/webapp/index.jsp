<%@page import="java.util.Iterator"%>
<%@page import="javax.naming.Binding"%>
<%@page import="javax.naming.NamingEnumeration"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.github.samples.jvmmaintenance.ActionServlet"%>
<html>
<head>
<title>JVM Maintenance App</title>
<link type="text/css" href="css/ui-darkness/style.css" rel="stylesheet" />

<!-- include jquery ui -->
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>

<!-- init jquery ui tab container -->
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
			<li><a href="#tabs-5">JNDI Environment</a></li>
		</ul>
				<div id="tabs-1">
			<h3><a href="#">Operations</a></h3>
			<div>
				<ul>
					<li><a href="action?<%=ActionServlet.OPERATION_PARAM%>=<%=ActionServlet.ACTION_GC%>">Perform Garbage Collection</a></li>
					<li><a href="action?<%=ActionServlet.OPERATION_PARAM%>=<%=ActionServlet.ACTION_JAVADUMPIBM%>">Generate Java Dump (IBM JVM)</a></li>
					<li><a href="action?<%=ActionServlet.OPERATION_PARAM%>=<%=ActionServlet.ACTION_HEAPDUMPIBM%>">Generate Heap Dump (IBM JVM)</a></li>
					<li><a href="action?<%=ActionServlet.OPERATION_PARAM%>=<%=ActionServlet.ACTION_SYSTEMDUMPIBM%>">Generate System Dump (IBM JVM)</a></li>
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
						Iterator propertyKeys = System.getProperties().keySet().iterator();
						while(propertyKeys.hasNext()){
							Object key = propertyKeys.next();
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
						Iterator envKeys = System.getenv().keySet().iterator();
						while(envKeys.hasNext()){
							Object key = envKeys.next();
							String value = System.getenv(key.toString());
							out.println("<li>" + key + " = " + value + "</li>");
						}
					%>
				</ul>
			</div>
		</div>
		<div id="tabs-5">
			<h3><a href="?jndiRoot=#tabs-5">JNDI Environment</a></h3>
			<%
				try{
					String jndiRoot = request.getParameter("jndiRoot");
					if(jndiRoot == null)
						jndiRoot = "";
					
					InitialContext ctx = new InitialContext();
			
			%>
			<span><b>current context:</b> <%= jndiRoot %></span>
			<div>
				<ul>
				<%
					NamingEnumeration names = ctx.listBindings(jndiRoot);
					while(names.hasMore()){
						Binding b = (Binding)names.next();
						String name = b.getName();
						Object value = b.getObject();
						String className = value.getClass().getName();
						String fullname = (jndiRoot == null || jndiRoot.equals("") ? "" : jndiRoot + "/") + name;
						String linkAdress = request.getContextPath() + "?jndiRoot=" + fullname + "#tabs-5";
						out.println("<li><b><a href=\"" + linkAdress + "\">" + name + "</a>:</b> " + value + " <i>(" + className + ")</i></li>");
						
					}
				}catch(Throwable ex){
					out.println("error retrieving jndi context: " + ex.getMessage());
				}
				%>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
