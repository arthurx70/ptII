/* Attribute that contains attributes that identify the containing model.

 Copyright (c) 2001-2003 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

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

@ProposedRating Yellow (eal@eecs.berkeley.edu)
@AcceptedRating Red (cxh@eecs.berkeley.edu)
*/

package ptolemy.kernel.attributes;

import java.text.DateFormat;
import java.util.Date;

import ptolemy.kernel.Entity;
import ptolemy.kernel.util.Attribute;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.InternalErrorException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.Nameable;
import ptolemy.kernel.util.NamedObj;
import ptolemy.kernel.util.SingletonAttribute;
import ptolemy.kernel.util.StringAttribute;

//////////////////////////////////////////////////////////////////////////
//// IDAttribute
/**
This attribute identifies the containing model.
It provides a user interface to key properties of the containing
model, including its name and base class. If the name is changed in
this attribute, then the name of the container will be changed to match.
<p>
@author Edward A. Lee
@version $Id$
@since Ptolemy II 2.0
*/
public class IDAttribute extends SingletonAttribute {
    
    // FIXME: Add author, date, version
    // FIXME: Provide a mechanism to look inside a base class.

    /** Construct an attribute with the given name contained by the
     *  specified container. The container argument must not be null, or a
     *  NullPointerException will be thrown.  This attribute will use the
     *  workspace of the container for synchronization and version counts.
     *  If the name argument is null, then the name is set to the empty
     *  string. Increment the version of the workspace.
     *  @param container The container.
     *  @param name The name of this attribute.
     *  @exception IllegalActionException If the attribute is not of an
     *   acceptable class for the container, or if the name contains a period.
     *  @exception NameDuplicationException If the name coincides with
     *   an attribute already in the container.
     */
    public IDAttribute(Entity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        
        this.name = new StringAttribute(this, "name");
        this.name.setExpression(container.getName());
        // This should not be persistent, in case the name changes outside
        // of this parameter.
        this.name.setPersistent(false);
        
        String momlElement = container.getMoMLInfo().elementName;
        String className = container.getMoMLInfo().className;
        String superclass = container.getMoMLInfo().superclass;
        String baseClassValue = null;
        String isClassValue;
        if (momlElement.equals("class")) {
            baseClassValue = superclass;
            isClassValue = "true";
        } else {
            baseClassValue = className;
            isClassValue = "false";
        }

        baseClass = new StringAttribute(this, "baseClass");
        baseClass.setExpression(baseClassValue);
        // This should not be persistent, because the base class
        // is set already, generally.
        baseClass.setPersistent(false);
        
        isClass = new StringAttribute(this, "isClass");
        isClass.setExpression(isClassValue);
        // This should not be persistent.
        isClass.setPersistent(false);
        
        author = new StringAttribute(this, "author");
        String userName = System.getProperty("user.name");
        if (userName != null) {
            author.setExpression(userName);
        }
        
        lastUpdated = new StringAttribute(this, "lastUpdated");
        setDate(null);
        
        // Hide the name.
        new Attribute(this, "_hideName");
    }
    
    ///////////////////////////////////////////////////////////////////
    ////                         attributes                        ////

    /** The author of the class. */
    public StringAttribute author;
    
    /** The base class of the containing class or entity. */
    public StringAttribute baseClass;
        
    /** A boolean indicating whether the container is a class or an
     *  instance.  This is a string that must have value "true" or
     *  "false".
     */
    public StringAttribute isClass;

    /** The date that this model was last updated. */
    public StringAttribute lastUpdated;
    
    /** The name of the containing class or entity. */
    public StringAttribute name;

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** React to a change in an attribute.  If the attribute is <i>name</i>,
     *  then change the name of the container to match.
     *  @param attribute The attribute that changed.
     *  @exception IllegalActionException If the change is not acceptable
     *   to this container (not thrown in this base class).
     */
    public void attributeChanged(Attribute attribute)
            throws IllegalActionException {
        if (attribute == name) {
            Nameable container = getContainer();
            try {
                container.setName(name.getExpression());
            } catch (NameDuplicationException e) {
                throw new IllegalActionException(this, e,
                "Cannot change the name of the container to match.");
            }
        } else if (attribute == baseClass) {
            // FIXME: How to change the base class?
        } else if (attribute == isClass) {
            String isClassValue = isClass.getExpression();
            NamedObj container = (NamedObj)getContainer();
            boolean containerIsClass
                    = container.getMoMLInfo().elementName.equals("class");
            if (isClassValue.equals("true")) {
                if (!containerIsClass) {
                    // Change the container from an entity to a class.
                    container.getMoMLInfo().elementName = "class";
                    container.getMoMLInfo().superclass
                            = container.getMoMLInfo().className;
                }
            } else if (isClassValue.equals("false")) {
                if (containerIsClass) {
                    // Change the container from a class to an entity.
                    // NOTE: Assume here that the class name is still valid.
                    container.getMoMLInfo().elementName = "entity";
                }
            } else {
                throw new IllegalActionException(this,
                "Unrecognized value for isClass: should be true or false.");
            }
        } else {
            super.attributeChanged(attribute);
        }
    }
    
    /** Set the date for the <i>lastUpdated</i> parameter.
     *  A null argument requests that the date be set to now.
     *  @param date The date to set.
     */
    public void setDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        try {
            lastUpdated.setExpression(
                    DateFormat.getDateTimeInstance().format(date));
        } catch (IllegalActionException e) {
            throw new InternalErrorException(e);
        }
    }
}
