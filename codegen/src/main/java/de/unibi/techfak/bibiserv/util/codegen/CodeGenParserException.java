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
package de.unibi.techfak.bibiserv.util.codegen;

/**
 * CodeGenParserException is thrown by the CodeGenParser, if the input
 * document is invalid or something unexpected happened during parsing.
 *
 * @author jkrueger
 */
public class CodeGenParserException extends Exception{

       public CodeGenParserException(){
           super();
       }

       public CodeGenParserException(String message){
           super(message);
       }

       public CodeGenParserException(Throwable cause){
           super(cause);
       }

       public CodeGenParserException(String message, Throwable cause) {
           super(message, cause);
        }

}
