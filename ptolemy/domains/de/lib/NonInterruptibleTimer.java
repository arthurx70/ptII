/* A non-interruptible timer that produces an event with a time delay 
specified by the input.

Copyright (c) 1998-2004 The Regents of the University of California.
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

*/

package ptolemy.domains.de.lib;

import java.util.LinkedList;

import ptolemy.actor.util.Time;
import ptolemy.data.DoubleToken;
import ptolemy.data.Token;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

//////////////////////////////////////////////////////////////////////////
//// NonInterruptibleTimer
/**
   A NonInterruptibleTimer actor works similar to the {@link Timer} actor, 
   except that if a NonInterruptibleTimer actor has not finished processing 
   the previous input, a new input has to be delayed for processing.  
   In other words, it can not be interrupted to respond new inputs. Instead, 
   the new inputs will be queued and processed in a first come first serve 
   (FCFS) fashion.
   <p>
   This actor extends the {@link Timer} actor. 
      
   @see Timer
   @author Haiyang Zheng
   @version $Id$
   @since Ptolemy II 4.1
   @Pt.ProposedRating Red (hyzheng)
   @Pt.AcceptedRating Red (hyzheng)
*/
public class NonInterruptibleTimer extends Timer {

    /** Construct an actor with the specified container and name.
     *  @param container The container.
     *  @param name The name of this actor.
     *  @exception IllegalActionException If the entity cannot be contained
     *   by the proposed container.
     *  @exception NameDuplicationException If the container already has an
     *   actor with this name.
     */
    public NonInterruptibleTimer(CompositeEntity container, String name)
            throws NameDuplicationException, IllegalActionException  {
        super(container, name);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** Read one token from the input. If the value of the input is bigger than
     *  0.0, save the input to be processed in the postfire method. Otherwise, 
     *  check whether there is any output scheduled to be produced at the 
     *  current time. If there is one, send out that output and save the 
     *  current input for future processing in the postfire method. If there 
     *  is no output to produce, the timer is not processing other inputs,
     *  and the input value is 0.0, send an output immediately and reset the 
     *  current input to null, indicating no further processing of the current 
     *  input is necessary.
     * 
     *  @exception IllegalActionException If there is no director, or can not
     *  send or get tokens from ports.
     */
    public void fire() throws IllegalActionException {
        _delay = -1.0;
        if (input.hasToken(0)) {
            _currentInput = input.get(0);
            _delayedInputTokensList.addLast(_currentInput);
            double delayValue = ((DoubleToken)_currentInput).doubleValue();
            if (delayValue < 0) {
                throw new IllegalActionException(
                    "Delay can not be negative.");
            } else {
                _delay = delayValue;
            }
        } else {
            _currentInput = null;
        }
        Time currentTime = getDirector().getModelTime();
        _currentOutput = null;
        if (_delayedTokens.size() > 0) {
            _currentOutput = (Token)_delayedTokens.get(currentTime);
            if (_currentOutput != null) {
                output.send(0, value.getToken());
                return;
            } else {
                // no tokens to be produced at the current time.
            }
        } else if (_delay == 0.0 && _delayedInputTokensList.size() > 0) {
            _delayedInputTokensList.removeFirst();
            output.send(0, value.getToken());
            _currentInput = null;
        }
     }

    /** Reset the states of the server to indicate that the timer is not
     *  processing any inputs.
     *  @exception IllegalActionException If the base class throws it.
     */
    public void initialize() throws IllegalActionException {
        super.initialize();
        _nextTimeFree = Time.NEGATIVE_INFINITY;
        _delayedInputTokensList = new LinkedList();
    }

    /** If there are delayed inputs that are not processed and the timer 
     *  is not busy. Begin processing the earliest input and schedule 
     *  a future firing to produce it.
     *  @exception IllegalActionException If there is no director or can not
     *  schedule future firings to handle delayed input events.
     */
    public boolean postfire() throws IllegalActionException {
        Time currentTime = getDirector().getModelTime();

        // Remove the curent output token from _delayedTokens.
        if (_currentOutput != null) {
            _delayedTokens.remove(currentTime);
        }
        // If the delayedInputTokensList is not empty, and the delayedTokens
        // is empty (ready to process a new input), get the first input in the
        // delayedInputTokensList, put it into the delayedTokens, and begin 
        // processing it. Schedule a refiring to produce the corresponding
        // output at the time: current time + delay specified by the input 
        // being processed.
        if (_delayedInputTokensList.size() != 0 && _delayedTokens.isEmpty()) {
            // NOTE: the input has a fixed data type as double.
            DoubleToken delayToken = (DoubleToken)_delayedInputTokensList.removeFirst();
            double delay = delayToken.doubleValue();
            _nextTimeFree = currentTime.add(delay);
            _delayedTokens.put(_nextTimeFree, delayToken);
            getDirector().fireAt(this, _nextTimeFree);
        }
        return !_stopRequested;
    }

    ///////////////////////////////////////////////////////////////////
    ////                       private variables                   ////

    // Next time the server becomes free.
    private Time _nextTimeFree;
    
    // List of delayed input tokens, whose finishing times can not be decided.
    private LinkedList _delayedInputTokensList;
}
