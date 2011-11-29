package com.github.samples.jvmmaintenance;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/** name of the parameter which specifies the operation to execute */
	public static final String OPERATION_PARAM = "operation";
	
	/** enum of all available operations. the values are used as arguments for the {@link ActionServlet#OPERATION_PARAM} parameter */
	public enum ACTIONS {GC, HEAPDUMPIBM};

	/**
	 * Simply calls {@link #doGet(HttpServletRequest, HttpServletResponse)}
	 * 
	 * @see #doGet(HttpServletRequest, HttpServletResponse)
	 */
	@Override
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
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String operation = req.getParameter(OPERATION_PARAM);
		System.out.println("operation: " + operation);
		
		if(operation != null && operation.equals(ACTIONS.GC.toString()))
			performGarbageCollection(req, resp);
		
		else if(operation != null && operation.equals(ACTIONS.HEAPDUMPIBM.toString()))
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
			Class<?> ibmHeap = Class.forName("com.ibm.jvm.Dump.HeapDump");
			ibmHeap.newInstance();
		}catch (Throwable ex) {
			System.err.println("error while generating heap dump: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Performs a garbace collection cycle.
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
