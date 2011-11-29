package com.github.samples.jvmmaintenance;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public static final String OPERATION_PARAM = "operation";
	
	public enum ACTIONS {GC, HEAPDUMP};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String operation = req.getParameter(OPERATION_PARAM);
		System.out.println("operation: " + operation);
		
		if(operation != null && operation.equals(ACTIONS.GC.toString()))
			performGarbageCollection();
		
		else if(operation != null && operation.equals(ACTIONS.HEAPDUMP.toString()))
			generateHeapDump();

		resp.sendRedirect("");
			
	}
	
	private void generateHeapDump() {
		System.out.println("generating heap dump");
		try{
			//TODO generate heap dump
		}catch (Throwable ex) {
			System.err.println("error while generating heap dump: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void performGarbageCollection() {
		System.out.println("perfoming gc");
		try{
			System.gc();
		}catch (Throwable ex) {
			System.err.println("error while performing gc: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
