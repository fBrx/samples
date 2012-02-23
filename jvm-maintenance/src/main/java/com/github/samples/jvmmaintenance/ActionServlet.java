package com.github.samples.jvmmaintenance;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/** name of the parameter which specifies the operation to execute */
	public static final String OPERATION_PARAM = "operation";
	
	/** constants for all available operations. the values are used as arguments for the {@link ActionServlet#OPERATION_PARAM} parameter */
	public static final String ACTION_GC = "GC";
	public static final String ACTION_HEAPDUMPIBM = "HEAPDUMPIBM";
	

	/**
	 * Simply calls {@link #doGet(HttpServletRequest, HttpServletResponse)}
	 * 
	 * @see #doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	/**
	 * Evaluates the {@link ActionServlet#OPERATION_PARAM} request parameter and performs the corresponding operations.
	 * The response is then redirected to "/" which causes the default welcome page to be opened.
	 * 
	 * @see #generateHeapDumpIbm(HttpServletRequest, HttpServletResponse)
	 * @see #performGarbageCollection(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String operation = req.getParameter(OPERATION_PARAM);
		System.out.println("operation: " + operation);
		
		if(operation != null && operation.equals(ACTION_GC.toString()))
			performGarbageCollection(req, resp);
		
		else if(operation != null && operation.equals(ACTION_HEAPDUMPIBM.toString()))
			generateHeapDumpIbm(req, resp);

		resp.sendRedirect("");
			
	}
	
	/**
	 * Generates a HeapDump on a IBM JVM.
	 *  
	 * @param req the {@link HttpServletRequest}
	 * @param resp the {@link HttpServletResponse}
	 */
	private void generateHeapDumpIbm(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("generating heap dump");
		try{
			//get object for ibm Dump class
			Class ibmHeap = Class.forName("com.ibm.jvm.Dump");
			
			//get handler to HeapDump method
			Method heapDump = ibmHeap.getDeclaredMethod("HeapDump", new Class[] {});
			
			//invoke method
			heapDump.invoke(null, (Object[])null);
			
		}catch (Throwable ex) {
			System.err.println("error while generating heap dump: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Request a garbage collection cycle.
	 *  
	 * @param req the {@link HttpServletRequest}
	 * @param resp the {@link HttpServletResponse}
	 * @see System#gc()
	 */
	private void performGarbageCollection(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("perfoming gc");
		try{
			System.gc();
		}catch (Throwable ex) {
			System.err.println("error while performing gc: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
