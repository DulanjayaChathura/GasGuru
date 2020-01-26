/**
 * 
 */
package com.GasGuru.GasGuru.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * @author TCC-RPL-16
 *
 */
@Component
public class JacksonConfig {
	


	
	private static final Logger logger = LoggerFactory.getLogger(JacksonConfig.class.getName());
	
	public String convertToJson(Object object) {
		String json = "";
		try {
			if(StringUtils.isEmpty(object)) {
				return "Object is Empty";
			}
			ObjectMapper objectMapper = new ObjectMapper();
			json = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error("error occur when executing convertToJson :: ", e);
		}
		return json;
	}
/**
	public String convertToXML(Object object) {
		
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(object, sw);
		String xmlString = sw.toString();
		return xmlString;
	}
**/
	public String convertToXML(Object object) {
	    String xmlString = "";
	    try {
	        JAXBContext context = JAXBContext.newInstance(Object.class);
	        Marshaller m = context.createMarshaller();

	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

	        StringWriter sw = new StringWriter();
	        m.marshal(object, sw);
	        xmlString = sw.toString();

	    } catch (JAXBException e) {
	    	logger.error("error occured while convert to XML : ",e);
	        return null;
	    }

	    return xmlString;
	}
	
	
}
