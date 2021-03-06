/**
 * Copyright (c) 2013-2017 The Regents of the University of California.
 * All rights reserved.
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, provided that the above
 * copyright notice and the following two paragraphs appear in all copies
 * of this software.
 *
 * IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 * FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 * PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 * CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 * ENHANCEMENTS, OR MODIFICATIONS.
 *
 * PT_COPYRIGHT_VERSION_2
 * COPYRIGHTENDKEY
 */
package org.ptolemy.scala.actor.lib
import ptolemy.kernel.CompositeEntity
import org.ptolemy.scala.implicits._
import org.ptolemy.scala.actor.TypedAtomicActor
import org.ptolemy.scala.actor.ApplicationActor
import org.ptolemy.scala.actor.ComponentEntity

/**
 * Construct an actor in the given container with the given name.
 *  The container argument is an implicit parameter.
 *  This class is a wrapper of the ptolemy.actor.lib.Commutator
 *
 *  @param container ( implicit) Container of the director.
 *  @param name Name of this director.
 */
/**
 * @author Moez Ben Hajhmida
 *
 */
case class Commutator(name: String)(implicit container: CompositeEntity) extends TypedAtomicActor {
  /**
   *  This field is a reference to the Java actor.
   *   It makes possible to access all attributes and methods
   *   provided by PtolemyII in Java language.
   */
  var commutator = new ptolemy.actor.lib.Commutator(container, name)
  /**
   *  These fields are references to the public fields of the
   *    ptolemy.actor.lib.Commutator.
   */
  var blockSize = commutator.blockSize
  var input_tokenConsumptionRate = commutator.input_tokenConsumptionRate
  var output_tokenProductionRate = commutator.output_tokenProductionRate
  var input = commutator.input
  var output = commutator.output
  /**
   * Returns a reference to the wrapped actor.
   * This method returns the Ptolemy actor (java) wrapped  by the scala actor.
   * It's useful when the developer wants to access the java methods.
   *
   *  @return Reference to the wrapped actor.
   */

  def getActor(): ptolemy.actor.lib.Commutator = commutator

  /**
   * Invokes the method 'setExpression(String expression)' of the Parameter named 'parameterName'
   * The field named 'parameterName' is of type 'ptolemy.data.expr.Parameter', and is a field of the object 'obj'.'
   * This function performs a java reflection to execute the java code:
   * objectName.parameterName.setExpression(expression) .
   *
   *  @param parameterName The name of the field to be set.
   *  @param expression The expression to be set to the field
   *  @return Reference to the current object.
   */
  def set(parameterName: String, expressionString: String): Commutator = {
    setExpression(commutator, parameterName, expressionString)
    this
  }
  /**
   *  Overloading constructor
   *  Permits call a Class constructor like in PtolemyII Java classes
   *  val actor  = new Actor(container , "name")
   * @param container The container.
   *  @param name The name of this actor
   */
  def this(container: ComponentEntity, name: String) { this(name)(container.getActor().asInstanceOf[CompositeEntity]) }
  /**
   *  Overloading constructor
   *  Permits call a Class constructor like in PtolemyII Java classes
   *  val actor  = new Actor(container , "name")
   * @param container The container. Here the container is of type  ApplicationActor,
   * which is used to write an application to run.
   *  @param name The name of this actor
   */
  def this(container: ApplicationActor, name: String) { this(name)(container.getActor().asInstanceOf[CompositeEntity]) }
}
