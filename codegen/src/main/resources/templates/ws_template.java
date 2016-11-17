/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 BiBiServ Curator Team, http://bibiserv.cebitec.uni-bielefeld.de,
 * All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common
 * Development and Distribution License("CDDL") (the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at http://www.sun.com/cddl/cddl.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.  When distributing the software, include
 * this License Header Notice in each file.  If applicable, add the following
 * below the License Header, with the fields enclosed by brackets [] replaced
 *  by your own identifying information:
 *
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 */
package <#getPackageName/#>;


import de.unibi.cebitec.bibiserv.util.convert.ConversionException;
import de.unibi.cebitec.bibiserv.util.validate.ValidationException;
import de.unibi.techfak.bibiserv.BiBiTools;
import de.unibi.techfak.bibiserv.Status;
import de.unibi.techfak.bibiserv.exception.BiBiToolsException;
import <#getPackageName/#>.param.<#getClassName/#>.Param;
import de.unibi.techfak.bibiserv.util.Pair;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

@WebService(targetNamespace="<#getTargetNameSpace/#>")
public class <#getClassName/#> {

    <#getImplementationClassName/#> service_implementation = new <#getImplementationClassName/#>();


    /**
     * The request method(s) are complete autogenerated from the tool specification.
     * The number of request methods depend on the specified input format and the
     * number of sibbling formats, a converter is found by the code generator.
     * These methods do only conversion, the implementation logic is inside the
     * implementing class.
     */
    <#generate_ws_request#>service_implementation<#/generate_ws_request#>

    /**
     * The response method(s) are complete autogenerated from the tool specification.
     * The number of request methods depend on the specified input format and the
     * number of sibbling formats, a converter is found by the code generator.
     * These methods do only conversion, the implementation logic is inside the
     * implementing class.
     */
    <#generate_ws_response#>service_implementation<#/generate_ws_response#>


    /** Service method that return the status code of a job - specified by the given ID -
     *  as integer value. The status code following the HOBIT status code specification
     *  (<a href="http://hobit.sourceforge.net/statuscodes_list.html">
     *  http://hobit.sourceforge.net/statuscodes_list.html</a>).
     *
     * @param id - Job id
     * @return Return the status code as int.
     */
    @WebMethod()
    @WebResult(name="statuscode")
    public int getStatusCode(@WebParam(name="id") String id){
        try {
            return service_implementation.getStatusCode(id);
        } catch (BiBiToolsException e){
            throw BiBiTools.createSOAPFaultException(e);
        }
    }

    /**
     * Service method that return the status of a job - specified by the given ID -
     * as human readable representation (as string).
     *
     * @param id - Job id.
     * @return Return the status as string.
     */
    @WebMethod()
    @WebResult(name="status")
    public String getStatus(@WebParam(name="id")String id) {
        try {
            Status status = service_implementation.getStatus(id);
            return status.getStatuscode()+" :: "+status.getDescription();
        } catch (BiBiToolsException e) {
            throw BiBiTools.createSOAPFaultException(e);
        }
    }

    /**
     * Service method that return that maintenanc state of this service.
     * The maintenance state is String that is not specified, but we recommend
     * to use one of the key words "alpha", "beta", "stable", "deprecated", "dead".
     *
     * @return Return the maintenance state as String.
     */
    @WebMethod()
    @WebResult(name="maintenancestate")
    public String getMaintenanceState(){
        return "alpha"; // could be one of alpha|beta|stable|deprecated|dead
    }



    /**
     *  private application specific helper method, that converts a param JAXB object into
     *  a DOM represention.
     * 
     *  <em>DO NOT MODIFY THIS METHOD UNTIL YOU ARE SURE WHAT TO DO !</em>
     *
     *  @param param - JAXB Param object
     *  @return Returns a DOM document representation of the given JAXB Param object.
     *
     *  @throws  ParserConfigurationException
     *  @throws JAXBException
     */
    private Document jaxb2doc(Param param) throws  ParserConfigurationException, JAXBException {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document param_doc = dbf.newDocumentBuilder().newDocument();

            JAXBContext jaxbcontext = JAXBContext.newInstance(<#getPackageName/#>.param.<#getClassName/#>.Param.class);
            Marshaller marshaller = jaxbcontext.createMarshaller();
            marshaller.marshal(param, (Node)param_doc);

            return param_doc;
    }
}
