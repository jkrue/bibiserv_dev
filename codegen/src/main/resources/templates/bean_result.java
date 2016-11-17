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

import de.unibi.techfak.bibiserv.exception.BiBiToolsException;
import de.unibi.techfak.bibiserv.tools.<#getToolId/#>.<#getFunctionId/#>;
import de.unibi.techfak.bibiserv.util.ontoaccess.bibiontotypes.OntoRepresentation;
import de.unibi.techfak.bibiserv.web.beans.session.ToolFunctionResult;
import javax.faces.context.FacesContext;
import de.unibi.cebitec.bibiserv.utils.SiblingGetter;
import de.unibi.cebitec.bibiserv.utils.UniversalVisualizer;
import de.unibi.techfak.bibiserv.util.ontoaccess.bibiontotypes.impl.OntoAccessException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;
import de.unibi.techfak.bibiserv.util.Pair;

import de.unibi.techfak.bibiserv.util.ontoaccess.bibiontotypes.impl.OntoRepresentationImplementation;
import de.unibi.techfak.bibiserv.web.beans.session.MessagesInterface;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import de.unibi.techfak.bibiserv.web.beans.session.InputBeanInterface;
import de.unibi.techfak.bibiserv.web.beans.session.ToolChainItem;
import de.unibi.techfak.bibiserv.web.beans.session.ToolChainingBeanInterface;
import de.unibi.techfak.bibiserv.web.beans.session.ToolFunctionController;
import de.unibi.techfak.bibiserv.web.beans.session.ToolFunctionResultChaining;
import java.io.IOException;

/**
 * This is a autogenerated bean result template class for function <i><#getClassName/#></i>
 *
 * @author Daniel Hagemeier - dhagemei[aet]cebitec.uni-bielefeld.de
 *         Armin Toepfer - atoepfer[aet]cebitec.uni-bielefeld.de
 *         Jan Krueger - jkrueger[aet]cebitec.uni-bielefeld.de
 */
public class <#getClassName/#> implements ToolFunctionResult, ToolFunctionResultChaining {

    Logger log = Logger.getLogger(<#getClassName/#>.class);
            
    private String visualization = "";

    /* #########################################
     * #     DI method setExecfunction          #
     * ######################################### */
    public <#getFunctionId/#> execfunction;

    public void setExecfunction(<#getFunctionId/#> execfunction){
        this.execfunction = execfunction;
    }
    
        /* #########################################
     * #     DI method setMessages             #
     * ######################################### */
    private MessagesInterface messages;

    public void setMessages(MessagesInterface messages) {
        this.messages = messages;
    }

    /* #########################################
     * #    Returns tools result as Resource   #
     * ######################################### */
    /**
     * Returns the result as type type.
     * @param type Type to get the result as.
     * @return stream with object or null on error
     */
     public StreamedContent getResult(OntoRepresentation type) {
         boolean streaming = isStreaming();
         return getResult(type, streaming);
     }
    
    
    private StreamedContent getResult(OntoRepresentation type, boolean streaming){
        try {
            Object response = execfunction.response(bibiservid, type, streaming);
            
            if (streaming) {
                 return new DefaultStreamedContent((InputStream) response); 
            } else {
                if(type.getType() == OntoRepresentation.representationType.PRIMITIVE){
                    InputStream in = new ByteArrayInputStream(((String) response).getBytes());
                    return new DefaultStreamedContent(in,"text/plain",type.getKey()+".txt");
                } else {
                    JAXBContext jaxbc = JAXBContext.newInstance(Class.forName(type.getImplementationType()));
                    Marshaller m = jaxbc.createMarshaller();
                    m.setProperty("jaxb.formatted.output", true);
                    StringWriter w = new StringWriter();
                    m.marshal(response,w);
                    return new DefaultStreamedContent(new ByteArrayInputStream(w.toString().getBytes()),"application/xml",type.getKey()+".txt");
                }
            }
        } catch (Exception e) {
            log.fatal(e.getMessage(),e);
        }
        return null;
    }
    
    /* #########################################
     * #  Returns one tools additional Result  #
     * ######################################### */
    
    public StreamedContent getAdditionalResult(String filename, String contenttype){
        try {
            InputStream in = execfunction.getSpoolFileStream(filename);
            return new DefaultStreamedContent(in,contenttype,filename);
        } catch (Exception e) {
            log.fatal(e.getMessage(),e);
        }
        return null;
    }
    
     /* #########################################
     * #  Enables Download of additional Files  #
     * ######################################### */
    /**
     * Builds up a list of all outputFiles of form <DisplayName<FileName,ContentType>>
     * @return 
     */
    public List<Pair<String,Pair<String,String>>> getAllOutputFiles(){
        
        List<Pair<String,Pair<String,String>>> files = new ArrayList<Pair<String,Pair<String,String>>>();
        Pair<String,String> tmp = null;
        int counter;
        
        <#addAllOutputFiles/#>
        
        return files;
    }
    
    
     /* #########################################
     * #    Returns tool result in visualizer  #
     * ######################################### */

    public boolean applyVisualizer(OntoRepresentation type, String visualizer){
        try {
            Object response = execfunction.response(bibiservid, type);
            visualization = UniversalVisualizer.applyVisualizer(visualizer, response);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/<#getToolId/#>?viewType=submission&subType=<#getFunctionId/#>_visualization");
        } catch (Exception e){
            log.fatal(e.getMessage(),e);
            return false;
        }
        
        return true;
    }
    
    /* #########################################
     * #    Enables Visualizers and Download   #
     * ######################################### */
    /**
     * Builds up a list of all possible result-types and their visualizer.
     * @return 
     */
    public List<Pair<OntoRepresentation,List<String>>> getAllPossibleResults(){
        
        List<Pair<OntoRepresentation,List<String>>> result = new ArrayList<Pair<OntoRepresentation,List<String>>>();
        
        // if the file is so big it needs to be streamed, Visualizers can no longer be applied
        // also conversion otions are more limited
        boolean streaming = isStreaming();
        
        OntoRepresentation base = execfunction.getRepresentationOutput();
        
        if (streaming) {
            // only add reduced streaming compatible option
            result.add(new Pair(base, new ArrayList<String>()));

            for (OntoRepresentation sibling : SiblingGetter.getSiblingsStreamConvertableFrom(base)) {
                result.add(new Pair(sibling, new ArrayList<String>()));
            }
        } else {
            result.add(new Pair(base, UniversalVisualizer.getVisualizer(base.getKey())));

            for (OntoRepresentation sibling : SiblingGetter.getSiblingsConvertableFrom(base)) {
                result.add(new Pair(sibling, UniversalVisualizer.getVisualizer(sibling.getKey())));
            }
        }

        return result;
    }
    
    public boolean isStreaming() {
        return execfunction.isStreamingOutput();
    }
    
     /* #########################################
     * #    Messages by calculate              #
     * ######################################### */
    
    private boolean showMessage = false;;
    
    public boolean isShowMessage(){
        return showMessage;
    }
    
    public void setShowMessage(boolean showMessage) {
        this.showMessage = showMessage;
    }
    
    private String calculateMessage = "";
    
    public String getCalculateMessage(){
        return calculateMessage;
    }
    
    public void setCalculateMessage(String calculateMessage) {
        this.calculateMessage = calculateMessage;
    }
    
    
    /* #########################################
     * #   Getter/Setter/ variables            #
     * ######################################### */

    /* bibiserv id  */
    private String bibiservid = "";

    public void setBibiservid(String bibiservid){
        this.bibiservid = bibiservid;
    }

    /**
     * @return Return BiBiServ id
     */
    public String getBibiservid() {
        return bibiservid;
    }

    /**
     * 
     * @return Return status code
     */
    public int getStatuscode() {
        try {
            return execfunction.getStatusCode(bibiservid);
        } catch(BiBiToolsException e){
            return e.returnFaultCode();
        }
    }

    /**
     * 
     * @return Return status description
     */
    public String getStatusdescription()  {
         try {
            return execfunction.getStatusDescription(bibiservid);
        } catch (BiBiToolsException e) {
            return e.returnFaultString();
        }
    }
    
    public List<Pair<String, String>> getUploadDownloadData()  {
         try {
            return execfunction.getUploadDownloadData(bibiservid);
        } catch (BiBiToolsException e) {
            return new ArrayList<Pair<String, String>>();
        }
    }

    /**
     * Returns true if a job run finished successfull (statuscode == 600)
     * @return
     */
    public boolean isFinishedOk() {
        return (getStatuscode() == 600);
    }

    /**
     * Returns true if a job ends in an error state (statuscode >= 700)
     * @return
     */
    public boolean isFinishedError() {
        return (getStatuscode() >= 700);
    }
    
    
    /**
     * Returns true if job still runs (statuscode > 600 && statuscode < 700)
     * 
     * @return 
     */
    public boolean isRunning(){
        return !(isFinishedError() || isFinishedOk());
    }

    /**
     * The last generated visualization.
     */
    public String getVisualization(){
        return visualization;
    }
    
    /* #########################################
     * #           Chaining extension          #
     * ######################################### */
    
    private ToolChainingBeanInterface toolChainBean;

    public void setToolChainBean(ToolChainingBeanInterface chainingBean) {
        this.toolChainBean = chainingBean;
    }
    
    @Override
    public boolean isChaining() {
        return toolChainBean.isChaining("<#getFunctionId/#>");
    }

    @Override
    public void advanceToolChain() {
        ToolChainItem chainitem = toolChainBean.getChainItem("<#getFunctionId/#>");
        if(chainitem == null) {
            return;
        }
        
        // get controller of next function
        FacesContext context = FacesContext.getCurrentInstance();
        ToolFunctionController controller =  (ToolFunctionController) context.getApplication().
                evaluateExpressionGet(context, "#{"+chainitem.getControllerExpression()+"}",
                chainitem.getControllerClass());
        
        // get input
        InputBeanInterface input;
        try {
            input = controller.getInputs().get(chainitem.getInputIndex());
        } catch(IndexOutOfBoundsException e) {
            log.fatal("Input for chaining not found!");
            return;
        }
        
        toolChainBean.removeChainItem("<#getFunctionId/#>");
        
        try {
            
            input.setFileInput(execfunction.getResultfilePath(bibiservid), messages.property("<#getToolId/#>_name") + " - "+ messages.property("<#getFunctionId/#>_name"));
            
            FacesContext.getCurrentInstance().getExternalContext().redirect(chainitem.getNextFunctionUrl());
        } catch (IOException ex) {
            log.fatal("Redirect failed:"+ ex);
        } catch (BiBiToolsException  ex) {
            log.fatal("Failed to retrieve the toolresult for chaining.");
        }
    }

    @Override
    public String getNextChainToolname() {
        
        ToolChainItem chainitem = toolChainBean.getChainItem("<#getFunctionId/#>");
        if(chainitem == null) {
            return "";
        }
        return chainitem.getNextFunctionName();
    }
    
}
