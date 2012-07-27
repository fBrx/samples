package com.github.schali.samples.jaxwsprovider.jaxws.utils;

import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

public class XMLUtils {

	public static void serializeXml(Node node, OutputStream out) {
		DOMSource ds = new DOMSource(node);
		
		TransformerFactory factory = TransformerFactory.newInstance();
		StreamResult streamResult = new StreamResult(out);

		try {
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(ds, streamResult);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
