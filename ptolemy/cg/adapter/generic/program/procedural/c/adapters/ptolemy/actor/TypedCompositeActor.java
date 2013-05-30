/*
 @Copyright (c) 2005-2013 The Regents of the University of California.
 All rights reserved.

 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the
 above copyright notice and the following two paragraphs appear in all
 copies of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS.

 PT_COPYRIGHT_VERSION_2
 COPYRIGHTENDKEY


 */
package ptolemy.cg.adapter.generic.program.procedural.c.adapters.ptolemy.actor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ptolemy.actor.Actor;
import ptolemy.actor.CompositeActor;
import ptolemy.actor.Director;
import ptolemy.actor.Receiver;
import ptolemy.actor.TypedIOPort;
import ptolemy.actor.util.CausalityInterfaceForComposites;
import ptolemy.cg.kernel.generic.CodeGeneratorAdapter;
import ptolemy.cg.kernel.generic.program.CodeStream;
import ptolemy.cg.kernel.generic.program.NamedProgramCodeGeneratorAdapter;
import ptolemy.cg.kernel.generic.program.ProgramCodeGenerator;
import ptolemy.cg.kernel.generic.program.procedural.ProceduralCodeGenerator;
import ptolemy.cg.lib.CompiledCompositeActor;
import ptolemy.data.BooleanToken;
import ptolemy.domains.de.kernel.DEDirector;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NamedObj;

/**
 * A C adapter class for ptolemy.actor.lib.TypedCompositeActor.
 *
 * @author William Lucas
 * @version $Id$
 * @since Ptolemy II 9.1
 * @Pt.ProposedRating Red (wlc)
 * @Pt.AcceptedRating Red (wlc)
 */
public class TypedCompositeActor extends 
    ptolemy.cg.adapter.generic.program.procedural.adapters.ptolemy.actor.TypedCompositeActor {
    
    /**
     * Constructor method for the CompositeActor adapter.
     * @param actor the associated actor
     */
    public TypedCompositeActor(ptolemy.actor.TypedCompositeActor actor) {
        super(actor);
    }
    
    /**
     * In this function we generate the code for the construction
     * of the actors within the C Code. It generates the calls to
     * ActorSet recursively.
     *
     * @return The generated code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    public String generateConstructorActorsFunction() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList args = new LinkedList();
        _enumPortNumbersDefinition = "";
        
        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        Director director = TopActor.getDirector();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        String sanitizedDirectorName = CodeGeneratorAdapter.generateName(director);
        NamedProgramCodeGeneratorAdapter directorAdapter = (NamedProgramCodeGeneratorAdapter)getAdapter(director);
        
        codeStream.append(_eol + "void " + sanitizedContainerName + "_constructorActors() {" + _eol);
        
        // We initialize the container 
        args.add(sanitizedContainerName);
        args.add(sanitizedDirectorName);
        int nbAtomicActors = 0;
        int nbCompositeActors = 0;
        List actorList = TopActor.deepEntityList();
        Iterator<?> actors = actorList.iterator();
        while (actors.hasNext()) {
            Actor actor = (Actor) actors.next();
            if (actor instanceof CompositeActor)
                nbCompositeActors++;
            else
                nbAtomicActors++;
        }
        args.add(Integer.toString(nbAtomicActors));
        args.add(Integer.toString(nbCompositeActors));
        codeStream.appendCodeBlock("containerSetBlock", args);
        
        args.clear();
        int depth = 0;
        if (director instanceof ptolemy.domains.de.kernel.DEDirector) {
            // The depth in only relevant for DE models
            // In SDF the computation of the depth can lead to the detection
            // of false dependency loops
            CausalityInterfaceForComposites causality = (CausalityInterfaceForComposites)TopActor.getCausalityInterface();
            depth = causality.getDepthOfActor(TopActor);
        }
        args.add(sanitizedContainerName);
        if (TopActor.getContainer() == null)
            // Top level
            args.add("NULL");
        else {
            // Embedded
            String sanitizedContainerContainerName = CodeGeneratorAdapter.generateName(TopActor.getContainer());
            args.add("&" + sanitizedContainerContainerName);
        }
        args.add(Integer.toString(TopActor.portList().size()));
        args.add(Integer.toString(depth));
        // FIXME : handle the value of the priority
        args.add(Integer.toString(0));
        codeStream.appendCodeBlock("compositeActorSet", args);
        
        // We initialize the actors
        actors = actorList.iterator();
        int i = 0;
        while (actors.hasNext()) {
            NamedObj actor = (NamedObj) actors.next();
            String sanitizedActorName = CodeGeneratorAdapter.generateName(actor);
            args.clear();
            if (actor instanceof CompositeActor) {
                // FIXME : for now nothing to do
            }
            else {
                args.add(sanitizedContainerName);
                args.add(Integer.toString(i++));
                args.add(sanitizedActorName);
                codeStream.appendCodeBlock("atomicActorsSet", args);
            }
        }
        
        // We initialize the director
        codeStream.append(directorAdapter.generatePreinitializeCode());
        
        // We initialize the actor list
        actors = actorList.iterator();
        while (actors.hasNext()) {
            NamedObj actor = (NamedObj) actors.next();
            String sanitizedActorName = CodeGeneratorAdapter.generateName(actor);
            Actor act = (Actor) actor;
            if (act instanceof CompositeActor)
                // We call the construction recursively
                codeStream.append(_eol + sanitizedActorName + "_constructorActors();");
            else {
                args.clear();
                if (director instanceof ptolemy.domains.de.kernel.DEDirector) {
                    // The depth in only relevant for DE models
                    // In SDF the computation of the depth can lead to the detection
                    // of false dependency loops
                    CausalityInterfaceForComposites causality = (CausalityInterfaceForComposites)TopActor.getCausalityInterface();
                    depth = causality.getDepthOfActor(act);
                }
                args.add(sanitizedActorName);
                args.add("&" + sanitizedContainerName);
                int nbPorts = act.inputPortList().size() + act.outputPortList().size();
                args.add(Integer.toString(nbPorts));
                args.add(Integer.toString(depth));
                // FIXME : handle the value of the priority
                args.add(Integer.toString(0));
                codeStream.appendCodeBlock("actorSet", args);
            }
        }
        
        codeStream.append(_eol + "}" + _eol);
        
        return codeStream.toString();
    }
    
    /**
     * In this function we generate the code for the construction
     * of the ports within the C Code. It generates the calls to
     * IOPortSet recursively.
     *
     * @return The generated code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    public String generateConstructorPortsFunction() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList args = new LinkedList();
        
        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        String sanitizedContainerNameForArgs = "(" + sanitizedContainerName + ".actor)";
        
        codeStream.append(_eol + "void " + sanitizedContainerName + "_constructorPorts() {" + _eol);
        
        // We construct the ports of this composite actor
        String enumString = _eol + "enum {";
        List inputPortList = TopActor.inputPortList();
        List outputPortList = TopActor.outputPortList();
        Iterator<?> ports = inputPortList.iterator();
        int j = 0;
        // Sets the inputs ports
        while (ports.hasNext()) {
            TypedIOPort port = (TypedIOPort)ports.next();
            // We only deal with connected ports
            if (!port.isOutsideConnected())
                continue;
            args.clear();
            args.add(sanitizedContainerNameForArgs);
            args.add(Integer.toString(j++));
            args.add("\"" + port.getName() + "\"");
            args.add("\"" + getCodeGenerator().codeGenType(port.getType()) + "\"");
            args.add(Boolean.toString(port.isInput()));
            args.add(Boolean.toString(port.isOutput()));
            args.add(Boolean.toString(port.isMultiport()));
            int widthInside = 0 ;
            for (Receiver[] r : port.getInsideReceivers())
                if (r != null)
                    widthInside += r.length;
            args.add(Integer.toString(widthInside));
            args.add(Integer.toString(port.getWidth()));
            codeStream.appendCodeBlock("IOPortSet", args);
            enumString += "enum_" + sanitizedContainerName + "_" + port.getName() + ", ";
        }
        ports = outputPortList.iterator();
        // Sets the output ports
        while (ports.hasNext()) {
            TypedIOPort port = (TypedIOPort)ports.next();
            // We only deal with connected ports
            if (!port.isOutsideConnected())
                continue;
            args.clear();
            args.add(sanitizedContainerNameForArgs);
            args.add(Integer.toString(j++));
            args.add("\"" + port.getName() + "\"");
            args.add("\"" + getCodeGenerator().codeGenType(port.getType()) + "\"");
            args.add(Boolean.toString(port.isInput()));
            args.add(Boolean.toString(port.isOutput()));
            args.add(Boolean.toString(port.isMultiport()));
            args.add(Integer.toString(port.getWidth()));
            int widthOutside = 0;
            for (Receiver[] r : port.getRemoteReceivers())
                if (r != null)
                    widthOutside += r.length;
            args.add(Integer.toString(widthOutside));
            //args.add(Integer.toString(port.getRemoteReceivers()[0].length));
            codeStream.appendCodeBlock("IOPortSet", args);
            enumString += "enum_" + sanitizedContainerName + "_" + port.getName() + ", ";
        }
        if (enumString.length() > 7) {
            enumString = enumString.substring(0, enumString.length() - 2);
            enumString += "};" + _eol;
            _enumPortNumbersDefinition += enumString;
        }
        
        List actorList = TopActor.deepEntityList();
        Iterator<?> actors = actorList.iterator();
        
        // We initialize the port list for each actor
        actors = actorList.iterator();
        while (actors.hasNext()) {
            NamedObj actor = (NamedObj) actors.next();
            String sanitizedActorName = CodeGeneratorAdapter.generateName(actor);
            String sanitizedActorNameForArgs = sanitizedActorName;
            Actor act = (Actor) actor;
            if (act instanceof CompositeActor)
                // We call the construction recursively
                codeStream.append(_eol + sanitizedActorName + "_constructorPorts();");
            else {
                args.clear();
                enumString = _eol + "enum {";
                inputPortList = act.inputPortList();
                outputPortList = act.outputPortList();
                ports = inputPortList.iterator();
                j = 0;
                // Sets the inputs ports
                while (ports.hasNext()) {
                    TypedIOPort port = (TypedIOPort)ports.next();
                    // We only deal with connected ports
                    if (!port.isOutsideConnected())
                        continue;
                    args.clear();
                    args.add(sanitizedActorNameForArgs);
                    args.add(Integer.toString(j++));
                    args.add("\"" + port.getName() + "\"");
                    args.add("\"" + getCodeGenerator().codeGenType(port.getType()) + "\"");
                    args.add(Boolean.toString(port.isInput()));
                    args.add(Boolean.toString(port.isOutput()));
                    args.add(Boolean.toString(port.isMultiport()));
                    args.add(Integer.toString(port.getWidth()));
                    args.add(Integer.toString(0));
                    codeStream.appendCodeBlock("IOPortSet", args);
                    enumString += "enum_" + sanitizedActorName + "_" + port.getName() + ", ";
                }
                ports = outputPortList.iterator();
                // Sets the output ports
                while (ports.hasNext()) {
                    TypedIOPort port = (TypedIOPort)ports.next();
                    // We only deal with connected ports
                    if (!port.isOutsideConnected())
                        continue;
                    args.clear();
                    args.add(sanitizedActorNameForArgs);
                    args.add(Integer.toString(j++));
                    args.add("\"" + port.getName() + "\"");
                    args.add("\"" + getCodeGenerator().codeGenType(port.getType()) + "\"");
                    args.add(Boolean.toString(port.isInput()));
                    args.add(Boolean.toString(port.isOutput()));
                    args.add(Boolean.toString(port.isMultiport()));
                    args.add(Integer.toString(0));
                    int widthOutside = 0;
                    for (Receiver[] r : port.getRemoteReceivers()) 
                        if (r != null)
                            widthOutside += r.length;
                    args.add(Integer.toString(widthOutside));
                    //args.add(Integer.toString(port.getRemoteReceivers()[0].length));
                    codeStream.appendCodeBlock("IOPortSet", args);
                    enumString += "enum_" + sanitizedActorName + "_" + port.getName() + ", ";
                }
                if (enumString.length() > 7) {
                    enumString = enumString.substring(0, enumString.length() - 2);
                    enumString += "};" + _eol;
                    _enumPortNumbersDefinition += enumString;
                }
            }
        }
        codeStream.append(_eol + "}" + _eol);
        
        return codeStream.toString();
    }
    
    /**
     * In this function we generate the code for the construction
     * of the receivers within the C Code. It generates the calls to
     * RemoteReceiverSet and RemoteFarReceiverSet recursively.
     *
     * @return The generated code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    public String generateConstructorReceiversFunction() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList args = new LinkedList();

        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        String sanitizedContainerNameForArgs = "(" + sanitizedContainerName + ".actor)";
        
        codeStream.append(_eol + "void " + sanitizedContainerName + "_constructorReceivers() {" + _eol);
        
        // Setup the receivers of the composite actor
        List inputPortList = TopActor.inputPortList();
        List outputPortList = TopActor.outputPortList();
        Iterator<?> ports = inputPortList.iterator();
        int i = 0;
        // Sets the inputs ports
        while (ports.hasNext()) {
            TypedIOPort port = (TypedIOPort)ports.next();
            // We only deal with connected ports
            if (!port.isOutsideConnected())
                continue;
            Receiver[][] farReceivers = port.getRemoteReceivers();
            for (int j = 0 ; j < farReceivers.length ; j++) {
                Receiver r = farReceivers[j][0];
                args.clear();
                args.add(sanitizedContainerNameForArgs);
                args.add(Integer.toString(i));
                args.add(Integer.toString(j));
                String farActorName = CodeGeneratorAdapter.generateName(r.getContainer().getContainer());
                args.add(farActorName);
                args.add(r.getContainer().getName());
                codeStream.appendCodeBlock("ReceiverSetBlock", args);
            }
            // Create a far receivers for Composite actors input ports
            
            Receiver[][] receivers = port.deepGetReceivers();
            int cpt = 0;
            for (int j = 0 ; j < receivers.length ; j++) {
                for (int k = 0 ; k < receivers[j].length ; k++) {
                    Receiver r = receivers[j][k];
                    
                    args.clear();
                    args.add(sanitizedContainerNameForArgs);
                    args.add(Integer.toString(i));
                    args.add(Integer.toString(cpt++));
                    String farActorName = CodeGeneratorAdapter.generateName(r.getContainer().getContainer());
                    String farActorNameForArgs = farActorName;
                    if (r.getContainer().getContainer() instanceof CompositeActor)
                        farActorNameForArgs = "(" + farActorName + ".actor)";
                    args.add(farActorName);
                    args.add(farActorNameForArgs);
                    args.add(r.getContainer().getName());
                    int farChannelNumber = r.getContainer().getChannelForReceiver(r);
                    args.add(Integer.toString(farChannelNumber));
                    codeStream.appendCodeBlock("FarReceiverSetBlock", args);
                }
            }
            i++;
        }
        ports = outputPortList.iterator();
        // Sets the outputs ports
        while (ports.hasNext()) {
            TypedIOPort port = (TypedIOPort)ports.next();
            // We only deal with connected ports
            if (!port.isOutsideConnected())
                continue;
            Receiver[][] farReceivers = port.getRemoteReceivers();
            String enumFarReceivers = _eol + "enum {";
            int channel = 0;
            for (int k = 0 ; k < farReceivers.length ; k++) {
                for (int j = 0 ; j < farReceivers[k].length ; j++) {
                    Receiver r = farReceivers[k][j];
                    args.clear();
                    args.add(sanitizedContainerNameForArgs);
                    args.add(Integer.toString(i));
                    args.add(Integer.toString(channel++));
                    NamedObj farActor = r.getContainer().getContainer();
                    String farActorName = CodeGeneratorAdapter.generateName(farActor);
                    String farActorNameForArgs;
                    if (farActor instanceof CompositeActor)
                        farActorNameForArgs = "(" + farActorName + ".actor)";
                    else
                        farActorNameForArgs = farActorName;
                    args.add(farActorName);
                    args.add(farActorNameForArgs);
                    args.add(r.getContainer().getName());
                    int farChannelNumber = r.getContainer().getChannelForReceiver(r);
                    args.add(Integer.toString(farChannelNumber));
                    codeStream.appendCodeBlock("FarReceiverSetBlock", args);
                    enumFarReceivers += sanitizedContainerName + "_" + port.getName() + "_" + 
                            farActorName + "_" + r.getContainer().getName() + "_" + farChannelNumber + ", ";
                }
            }
            if (enumFarReceivers.length() > 7) {
                enumFarReceivers = enumFarReceivers.substring(0, enumFarReceivers.length() - 2);
                enumFarReceivers += "};" + _eol;
                _enumPortNumbersDefinition += enumFarReceivers;
            }
            
            // Create a local receivers for Composite actors output ports
            Receiver[][] insideReceivers = port.getInsideReceivers();
            for (int j = 0 ; j < insideReceivers.length ; j++) {
                Receiver r = farReceivers[j][0];
                args.clear();
                args.add(sanitizedContainerNameForArgs);
                args.add(Integer.toString(i));
                args.add(Integer.toString(j));
                String farActorName = CodeGeneratorAdapter.generateName(r.getContainer().getContainer());
                args.add(farActorName);
                args.add(r.getContainer().getName());
                codeStream.appendCodeBlock("ReceiverSetBlock", args);
            }
            i++;
        }
        
        
        
        List actorList = TopActor.deepEntityList();
        Iterator<?> actors = actorList.iterator();
        
        // We loop on the actors to setup the receivers
        actors = actorList.iterator();
        while (actors.hasNext()) {
            NamedObj actor = (NamedObj) actors.next();
            String sanitizedActorName = CodeGeneratorAdapter.generateName(actor);
            Actor act = (Actor) actor;
            
            if (act instanceof CompositeActor) {
                // Recursive call
                codeStream.append(_eol + sanitizedActorName + "_constructorReceivers();");
                continue;
            }
            args.clear();
            inputPortList = act.inputPortList();
            outputPortList = act.outputPortList();
            ports = inputPortList.iterator();
            i = 0;
            // Sets the inputs ports
            while (ports.hasNext()) {
                TypedIOPort port = (TypedIOPort)ports.next();
                // We only deal with connected ports
                if (!port.isOutsideConnected())
                    continue;
                Receiver[][] farReceivers = port.getRemoteReceivers();
                for (int j = 0 ; j < farReceivers.length ; j++) {
                    Receiver r = farReceivers[j][0];
                    args.clear();
                    args.add(sanitizedActorName);
                    args.add(Integer.toString(i));
                    args.add(Integer.toString(j));
                    String farActorName = CodeGeneratorAdapter.generateName(r.getContainer().getContainer());
                    args.add(farActorName);
                    args.add(r.getContainer().getName());
                    codeStream.appendCodeBlock("ReceiverSetBlock", args);
                }
                
                
                // Create a far receivers for Composite actors input ports
                if (act instanceof CompositeActor) {
                    Receiver[][] insideReceivers = port.getInsideReceivers();
                    int cpt = 0;
                    for (int j = 0 ; j < insideReceivers.length ; j++) {
                        for (int k = 0 ; k < insideReceivers[j].length ; k++) {
                            Receiver r = insideReceivers[j][k];
                            args.clear();
                            args.add(sanitizedActorName);
                            args.add(Integer.toString(i));
                            args.add(Integer.toString(cpt++));
                            String farActorName = CodeGeneratorAdapter.generateName(r.getContainer().getContainer());
                            String farActorNameForArgs = "(" + farActorName + ".actor)";
                            args.add(farActorName);
                            args.add(farActorNameForArgs);
                            args.add(r.getContainer().getName());
                            int farChannelNumber = r.getContainer().getChannelForReceiver(r);
                            args.add(Integer.toString(farChannelNumber));
                            codeStream.appendCodeBlock("FarReceiverSetBlock", args);
                        }
                    }
                }
                i++;
            }
            ports = outputPortList.iterator();
            // Sets the outputs ports
            while (ports.hasNext()) {
                TypedIOPort port = (TypedIOPort)ports.next();
                // We only deal with connected ports
                if (!port.isOutsideConnected())
                    continue;
                Receiver[][] farReceivers = port.getRemoteReceivers();
                String enumFarReceivers = _eol + "enum {";
                int channel = 0;
                for (int k = 0 ; k < farReceivers.length ; k++) {
                    // Fix for the case of a disconnected outside and connected inside port
                    if (farReceivers[k] == null)
                        continue;
                    for (int j = 0 ; j < farReceivers[k].length ; j++) {
                        Receiver r = farReceivers[k][j];
                        args.clear();
                        args.add(sanitizedActorName);
                        args.add(Integer.toString(i));
                        args.add(Integer.toString(channel++));
                        NamedObj farActor = r.getContainer().getContainer();
                        String farActorName = CodeGeneratorAdapter.generateName(farActor);
                        String farActorNameForArgs;
                        if (farActor instanceof CompositeActor)
                            farActorNameForArgs = "(" + farActorName + ".actor)";
                        else
                            farActorNameForArgs = farActorName;
                        args.add(farActorName);
                        args.add(farActorNameForArgs);
                        args.add(r.getContainer().getName());
                        int farChannelNumber = r.getContainer().getChannelForReceiver(r);
                        args.add(Integer.toString(farChannelNumber));
                        codeStream.appendCodeBlock("FarReceiverSetBlock", args);
                        enumFarReceivers += sanitizedActorName + "_" + port.getName() + "_" + 
                                farActorName + "_" + r.getContainer().getName() + "_" + farChannelNumber + ", ";
                    }
                }
                if (enumFarReceivers.length() > 7) {
                    enumFarReceivers = enumFarReceivers.substring(0, enumFarReceivers.length() - 2);
                    enumFarReceivers += "};" + _eol;
                    _enumPortNumbersDefinition += enumFarReceivers;
                }
                
                // Create a local receivers for Composite actors output ports
                if (act instanceof CompositeActor) {
                    Receiver[][] insideReceivers = port.getInsideReceivers();
                    for (int j = 0 ; j < insideReceivers.length ; j++) {
                        Receiver r = farReceivers[j][0];
                        args.clear();
                        args.add(sanitizedActorName);
                        args.add(Integer.toString(i));
                        args.add(Integer.toString(j));
                        String farActorName = CodeGeneratorAdapter.generateName(r.getContainer().getContainer());
                        args.add(farActorName);
                        args.add(r.getContainer().getName());
                        codeStream.appendCodeBlock("ReceiverSetBlock", args);
                    }
                }
                i++;
            }
        }
        codeStream.append(_eol + "}" + _eol);
        
        return codeStream.toString();
    }
    
    /** Generate The fire function code. This method is called when
     *  the firing code of each actor is not inlined. Each actor's
     *  firing code is in a function with the same name as that of the
     *  actor.
     *
     *  @return The fire function code.
     *  @exception IllegalActionException If thrown while generating fire code.
     */
    @Override
    public String generateFireFunctionCode() throws IllegalActionException {
        StringBuffer code = new StringBuffer();
        CompositeActor compositeActor = (CompositeActor) getComponent();
        if (!(compositeActor instanceof CompiledCompositeActor && ((BooleanToken) ((ProceduralCodeGenerator) getCodeGenerator()).generateEmbeddedCode
                .getToken()).booleanValue())) {
            // Generate the code for the TypedComposite before generating code for the director.
            // Needed by:
            // $PTII/bin/ptcg -language java  -inline false  -variablesAsArrays false $PTII/ptolemy/cg/adapter/generic/program/procedural/java/adapters/ptolemy/actor/lib/test/auto/ActorOrientedClass.xml
            // See generateFireFunctionCode() in ptolemy/cg/adapter/generic/program/procedural/adapters/ptolemy/domains/sdf/kernel/SDFDirector.java
            String fireCode = _generateFireCode();
            // Append _fireFunction_ to the class names so as to
            // differentiate from the inner classes that are generated for
            // the first few Composites when inline is false.
            String[] splitFireCode = getCodeGenerator()._splitBody(
                    "_fireFunction_"
                            + CodeGeneratorAdapter.generateName(getComponent())
                            + "_", fireCode);
            code.append(splitFireCode[0]);
            ProgramCodeGenerator codeGenerator = getCodeGenerator();
            code.append(_eol + "void "
                    + codeGenerator.generateFireFunctionMethodName(getComponent()) + "() {" + _eol);
            // code.append(_generateFireCode());
            code.append(splitFireCode[1]);
            code.append("}" + _eol);
        }
        return processCode(code.toString());
    }
    
    /**
     * Generate the initialize code for this composite actor
     *
     * @return The generated initialize code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    @Override
    public String generateInitializeCode() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList<String> args = new LinkedList();
        
        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        args.add(sanitizedContainerName + ".director");
        codeStream.appendCodeBlock("initializeBlock", args);
        
        return processCode(codeStream.toString());
    }
    
    /**
     * Generate the postfire code for this composite actor
     *
     * @return The generated postfire code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    @Override
    public String generatePostfireCode() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList<String> args = new LinkedList();
        
        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        args.add(sanitizedContainerName + ".director");
        
        codeStream.appendCodeBlock("postFireBlock", args);
        
        return processCode(codeStream.toString());
    }

    /**
     * Generate the prefire code for this composite actor
     *
     * @return The generated prefire code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    @Override
    public String generatePrefireCode() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList<String> args = new LinkedList();
        
        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        args.add(sanitizedContainerName + ".director");
        codeStream.appendCodeBlock("prefireBlock", args);
        
        return processCode(codeStream.toString());
    }
    
    /**
     * Generate the preinitialize code. We do not call the super
     * method, because we have arguments to add here
     * This code contains the variable declarations
     *
     * @return The generated preinitialize code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    @Override
    public String generatePreinitializeCode() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
                    
        codeStream.appendCodeBlock("variableDeclareBlock");
        
        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        
        // Here we declare the contained actors
        List actorList = TopActor.deepEntityList();
        Iterator<?> actors = actorList.iterator();
        while (actors.hasNext()) {
            NamedObj actor = (NamedObj) actors.next();
            String actorName = CodeGeneratorAdapter.generateName(actor);
            codeStream.append("$include(\"" + actorName + ".h\")");
        }
        
        // After the actors we declare the receivers of this container
        Director director = TopActor.getDirector();
        ptolemy.cg.adapter.generic.adapters.ptolemy.actor.Director directorAdapter = 
                (ptolemy.cg.adapter.generic.adapters.ptolemy.actor.Director) getAdapter(director);
        String directorName = CodeGeneratorAdapter.generateName(directorAdapter);
        codeStream.append("$include(\"" + directorName + ".h\")");
        
        // Appends the enum definition for the ports
        codeStream.append(_enumPortNumbersDefinition);
        
        return processCode(codeStream.toString());
    }
    
    /**
     * Generate the preinitialize code. We do not call the super
     * method, because we have arguments to add here
     * This code contains the different constructions and initializations.
     *
     * @return The generated preinitialize Method code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    @Override
    public String generatePreinitializeMethodBodyCode() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList args = new LinkedList();

        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        
        if ((ptolemy.actor.CompositeActor) getComponent().getContainer() == null) {
            // Appending the construction of the actors
            codeStream.append(_eol + sanitizedContainerName + "_constructorActors();");
            // Appending the construction of the ports
            codeStream.append(_eol + sanitizedContainerName + "_constructorPorts();");
            // Appending the construction of the receivers
            codeStream.append(_eol + sanitizedContainerName + "_constructorReceivers();" + _eol);
        }
        // We initialize the corresponding receivers and call the director method
        args.clear();
        args.add(sanitizedContainerName + ".director");
        codeStream.appendCodeBlock("preinitializeBlock", args);
        
        return processCode(codeStream.toString());
    }
    
    /**
     * Generate the transfer inputs code. This is to ensure
     * compatibility between different directors.
     *
     * @return The generated code.
     * @exception IllegalActionException If thrown while appending to the
     * the block or processing the macros.
     */
    public String generateTransfersCode() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        
        ptolemy.actor.CompositeActor container = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(container);
        
        codeStream.append(_eol + "void " + sanitizedContainerName + "_TransferInputs() {" + _eol);
        Iterator<?> inputPorts = container.inputPortList().iterator();
        while (inputPorts.hasNext()) {
            TypedIOPort inputPort = (TypedIOPort) inputPorts.next();
            if (!inputPort.isInsideConnected())
                // No need to transfer tokens from a disconnected port
                continue;
            for (int channel = 0 ; channel < inputPort.getWidth() ; channel++) {
                String hasTokenString = "if (ReceiverHasToken((" + sanitizedContainerName + ".actor).ports[enum_" + sanitizedContainerName 
                        + "_" + inputPort.getName() + "].receivers + " + channel + ")) {" + _eol;
                String getString = "ReceiverGet((" + sanitizedContainerName + ".actor).ports[enum_" + sanitizedContainerName 
                        + "_" + inputPort.getName() + "].receivers + " + channel + ")";
                String putString = "ReceiverPut((" + sanitizedContainerName + ".actor).ports[enum_" + sanitizedContainerName 
                        + "_" + inputPort.getName() + "].farReceivers[" + channel + "], " + getString + ");" + _eol;
                String fireAtString = "";
                if (container.getDirector() instanceof DEDirector) {
                    NamedObj insideActor = inputPort.deepGetReceivers()[channel][0].getContainer().getContainer();
                    String sanitizedNameInsideActor = CodeGeneratorAdapter.generateName(insideActor);
                    if (insideActor instanceof CompositeActor)
                        sanitizedNameInsideActor = "(" + sanitizedNameInsideActor + ".actor)";
                    fireAtString = _eol + "(*(" + sanitizedContainerName + ".director->fireAtFunction))(&"+ sanitizedNameInsideActor
                    +", " + sanitizedContainerName + ".director->currentModelTime, " 
                    + sanitizedContainerName + ".director->currentMicrostep);" + _eol;
                }
                codeStream.append(_eol + hasTokenString + putString + fireAtString + _eol + "}" + _eol);
            }
        }
        codeStream.append(_eol + "}" + _eol);
        
        codeStream.append(_eol + "void " + sanitizedContainerName + "_TransferOutputs() {" + _eol);
        Iterator<?> outputPorts = container.outputPortList().iterator();
        while (outputPorts.hasNext()) {
            TypedIOPort outputPort = (TypedIOPort) outputPorts.next();
            if (!outputPort.isOutsideConnected())
                // No need to transfer tokens from a disconnected port
                continue;
            for (int channel = 0 ; channel < outputPort.getWidth() ; channel++) {
                String hasTokenString = "if (ReceiverHasToken((" + sanitizedContainerName + ".actor).ports[enum_" + sanitizedContainerName 
                        + "_" + outputPort.getName() + "].receivers + " + channel + ")) {" + _eol;
                String getString = "ReceiverGet((" + sanitizedContainerName + ".actor).ports[enum_" + sanitizedContainerName 
                        + "_" + outputPort.getName() + "].receivers + " + channel + ")";
                String putString = "ReceiverPut((" + sanitizedContainerName + ".actor).ports[enum_" + sanitizedContainerName 
                        + "_" + outputPort.getName() + "].farReceivers[" + channel + "], " + getString + ");" + _eol;
                String fireAtString = "";
                CompositeActor topContainer = ((CompositeActor)container.getContainer());
                String sanitizedTopContainerName = CodeGeneratorAdapter.generateName(topContainer);
                if (topContainer.getDirector() instanceof DEDirector) {
                    // if top level is DE we have to call a fireAt
                    // FIXME : a call to the Receiver get and put methods would be better and non domain dependent
                    NamedObj outsideActor = outputPort.getRemoteReceivers()[channel][0].getContainer().getContainer();
                    String sanitizedNameOutsideActor = CodeGeneratorAdapter.generateName(outsideActor);
                    if (outsideActor instanceof CompositeActor)
                        sanitizedNameOutsideActor = "(" + sanitizedNameOutsideActor + ".actor)";
                    fireAtString = _eol + "(*(" + sanitizedTopContainerName + ".director->fireAtFunction))(&"+ sanitizedNameOutsideActor
                    +", " + sanitizedTopContainerName + ".director->currentModelTime, " 
                    + sanitizedTopContainerName + ".director->currentMicrostep);" + _eol;
                }
                
                codeStream.append(_eol + hasTokenString + putString + fireAtString + _eol + "}" + _eol);
            }
        }
        codeStream.append(_eol + "}" + _eol);
        
        return processCode(codeStream.toString());
    }
    
    /** Generate variable declarations for inputs and outputs and parameters.
     *  Append the declarations to the given string buffer.
     *  @return code The generated code.
     *  @exception IllegalActionException If the adapter class for the model
     *   director cannot be found.
     */
    @Override
    public String generateVariableDeclaration() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        
        return processCode(codeStream.toString());
    }

    /** Generate variable declarations for inputs and outputs and parameters.
     *  Append the declarations to the given string buffer.
     *  @return code The generated code.
     *  @exception IllegalActionException If the adapter class for the model
     *   director cannot be found.
     */
    @Override
    public String generateWrapupCode() throws IllegalActionException {
        CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList<String> args = new LinkedList();
        
        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        args.add(sanitizedContainerName + ".director");           
        codeStream.appendCodeBlock("WrapupBlock", args);
        
        return processCode(codeStream.toString());
    }
    
    /**
     * Generate the fire code of a Composite Actor.
     * @return The generated code.
     * @exception IllegalActionException 
     */
    @Override
    protected String _generateFireCode() throws IllegalActionException {
    	CodeStream codeStream = _templateParser.getCodeStream();
        codeStream.clear();
        LinkedList<String> args = new LinkedList();
        
        ptolemy.actor.CompositeActor TopActor = (ptolemy.actor.CompositeActor) getComponent();
        String sanitizedContainerName = CodeGeneratorAdapter.generateName(TopActor);
        args.add(sanitizedContainerName + ".director");
        args.add(sanitizedContainerName);
        codeStream.appendCodeBlock("fireBlock", args);
        
        return processCode(codeStream.toString());
    }
    
    
    private String _enumPortNumbersDefinition;
}
