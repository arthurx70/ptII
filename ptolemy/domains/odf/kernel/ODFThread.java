/* A thread for controlling actors according to ODF semantics.

 Copyright (c) 1997-1999 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTelelIAL DAMAGES
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

@ProposedRating Red (davisj@eecs.berkeley.edu)
@AcceptedRating Red (cxh@eecs.berkeley.edu)

*/

package ptolemy.domains.odf.kernel;

import ptolemy.kernel.*;
import ptolemy.kernel.util.*;
import ptolemy.data.*;
import ptolemy.actor.*;
import ptolemy.actor.process.*;
import java.util.Enumeration;
import collections.LinkedList;

//////////////////////////////////////////////////////////////////////////
//// ODFThread
/** 
A thread for controlling actors according to ODF semantics.



The base class for ODF actors. ODFThreads are intended to run as threaded
processes that maintain a distributed notion of time. Each ODFThread in
a system of connected actors, must maintain a local notion of time 
which is dependent on the time of events that flow through its input
receivers. An event is simply an object which contains a token, a
time stamp and a receiver (to which the event is destined).
<P>
To facilitate this process, the ODFConservativeRcvrs contained by ODFThreads 
each have 
three important variables: rcvrTime, lastTime and priority. The rcvrTime 
of an ODFConservativeRcvr is equal to the time of the oldest event that resides 
on the receiver. The lastTime is equal to the time of the newest event 
residing in the receiver. 
<P>
An ODFThread consumes tokens from the input receiver which has the oldest
(smallest valued) rcvrTime. Such consumption is accomplished via blocking
reads from the corresponding input receiver. The priority variable is used
in cases where multiple input receivers share a common rcvrTime. Each
receiver has an integer-valued priority. The receiver with the highest 
priority is utilized if a common rcvrTime is shared by multiple receivers. 
<P>
The receiver priorities are set using the method setPriorities() in the
following manner. All of the input receivers for a given ODFThread are 
grouped by their respective container input ports. If the ODFIOPorts which 
contain the receivers have been explicitly assigned priorities, then the 
groups are ordered accordingly. If port priorities have not been explicitly
assigned, then the groups are ordered according to the inverse order in 
which their corresponding ODFIOPorts were added to the containing ODFThread. 
I.e., if two input ports (pA and pB) are added to an ODFThread without explicit
priorities such that port pA is added before port pB, then all of the 
receivers of port pB will have a higher priority than the receivers of port 
pA.
<P>
Within a group the receiver priorities are further refined so that receivers
of the same group can be ordered relative to one another. Receiver priorities 
within a group are ordered according to the inverse order in which they were 
added to the containing ODFIOPort. I.e., if two input receivers (rA and rB) 
are added to an ODFThread such that receiver rA is added before receiver rB, 
then rB will have a higher priority than rA.
<P>
The above approach provides each receiver contained by a given ODFThread with 
a unique priority, such that the set of receiver priorities for the  
containing ODFThread is totally ordered. Note that currently setPriorities() 
calls the method port.getPriority(). This requires the port to be of type 
ODFIOPort and hence precludes polymorphic actors. A later version of this 
class will not have this constraint.
<P>
RcvrTimeTriple objects are used to facilitate the ordering of receivers 
contained by an ODFThread according to rcvrTime/lastTime and priority. A
RcvrTimeTriple is an object containing an ODFConservativeRcvr, the _rcvrTime of
the receiver and the priority of the receiver. Each actor contains a list 
consisting of one RcvrTimeTriple per receiver contained by the actor. As 
tokens are placed in and taken out of the receivers of an actor, the list 
of RcvrTimeTriples is updated.

@author John S. Davis II
@version $Id$
*/
public class ODFThread extends ProcessThread {

    /** Construct a thread to be used to execute the iteration methods 
     *  of an ODFActor. This increases the count of active actors in the 
     *  director.
     *  @param actor The ODFActor that needs to be executed.
     *  @param director The director responsible for the execution of this
     *  actor.
     */
    public ODFThread(Actor actor, ProcessDirector director) {
        super(actor, director);
        _director = director;
        _manager = ((CompositeActor)
                ((NamedObj)actor).getContainer()).getManager();
        _rcvrTimeList = new LinkedList();
    }
    
    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** Return the current time of this ODFThread. The current time is
     *  equal to the time stamp associated with the token most recently
     *  consumed by one of the receivers managed by this ODFThread.
     * @return The current time of this ODFThread.
     */
    public double getCurrentTime() {
        return _currentTime;
    }
    
    /**
     */
    public void setCurrentTime(double time) {
	if( time < _currentTime && time != -1.0 ) {
	    throw new IllegalArgumentException( 
		    ((NamedObj)getActor()).getName() + ": Attempt to "
		    + "set current time in the past.");
	}
	if( ((NamedObj)getActor()).getName().equals("printer") ) {
	    /*
	    System.out.println("\t" + ((NamedObj)getActor()).getName() +
		    ": ODFThread current time set to " + time );
	    */
	}
        _currentTime = time;
	_outputTime = _currentTime;
    }
    
    /** Return the RcvrTimeTriple consisting of the receiver with the 
     *  highest priority given that it has the lowest nonnegative rcvrTime
     *  of all receivers managed by this ODFThread. Return null if this 
     *  thread's list of RcvrTimeTriples is empty.
     * @return The RcvrTimeTriple consisting of the receiver with the 
     *  highest priority and lowest nonnegative rcvrTime. If no triples 
     *  exist, return null.
     *  FIXME: Should this be synchronized?
     */
    public RcvrTimeTriple getHighestPriorityTriple() {
        double time = -10.0;
	double firstTime = -10.0;
        int maxPriority = 0;
	int cnt = 0;
	boolean rcvrNotFound = true;
        RcvrTimeTriple highPriorityTriple = null;

	while( rcvrNotFound ) {
	    if( cnt == _rcvrTimeList.size() ) {
	        return highPriorityTriple;
	    }

	    RcvrTimeTriple triple = (RcvrTimeTriple)_rcvrTimeList.at(cnt);
	    if( time == -10.0 ) {
	        time = triple.getTime();
	        firstTime = time;
		maxPriority = triple.getPriority();
		highPriorityTriple = triple;
	    } else {
	        time = triple.getTime();
	    }

	    if( time > firstTime || time == -1.0 ) {
	        rcvrNotFound = false;
	    } else if( maxPriority < triple.getPriority() ) {
		maxPriority = triple.getPriority();
		highPriorityTriple = triple;
	    }
	    cnt++;
	}
	return highPriorityTriple;
    }
   
    /** Return the earliest possible time stamp of the next token to be
     *  processed or produced by the actor controlled by this thread. 
     *  The next time is equal to the oldest (smallest valued) rcvrTime 
     *  of all receivers managed by this thread.
     * @return The next earliest possible time stamp to be produced by 
     *  this actor.
     * @see TimedQueueReceiver
     */
    public double getNextTime() {
        if( _rcvrTimeList.size() == 0 ) {
            return _currentTime;
        }
        RcvrTimeTriple triple = (RcvrTimeTriple)_rcvrTimeList.first();
        return triple.getTime();
    }
    
    /**
     */
    public double getOutputTime() {
        return _outputTime;
    }
    
    /**
     */
    public synchronized ODFConservativeRcvr getFirstRcvr() {
        RcvrTimeTriple triple = (RcvrTimeTriple)_rcvrTimeList.first();
	return (ODFConservativeRcvr)triple.getReceiver();
    }

    /** Return true if the minimum receiver time is unique to a single
     *  receiver. Return true if there are no input receivers. Return
     *  false if two or more receivers share the same rcvrTime and this 
     *  rcvrTime is less than that of any other receivers contained by 
     *  the same actor.
     *  FIXME: If there are no receivers shouldn't the return value be false?
     *  FIXME: Consider changing the name to "hasUniqueMinRcvrTime()"
     * @return True if the minimum rcvrTime is unique to a single receiver 
     *  or if there are no receivers; otherwise return false.
     */
    public synchronized boolean hasMinRcvrTime() {
        if( _rcvrTimeList.size() < 2 ) {
            return true;
        }

        RcvrTimeTriple firstTriple = (RcvrTimeTriple)_rcvrTimeList.first(); 
	RcvrTimeTriple secondTriple = (RcvrTimeTriple)_rcvrTimeList.at(1);

	if( firstTriple.getTime() == secondTriple.getTime() ) {
	    return false;
	}
	return true;
    }
    
    /** Start this thread by first setting the receiver priorities 
     *  of the ODFActor that this thread controls, initializing the 
     *  RcvrTimeTriple and then calling the run method for this thread. 
     */
    public void start() {
	try {
	    setPriorities();
	    setRcvrList();
	    super.start();
        } catch (IllegalActionException e) {
            _manager.fireExecutionError(e);
            // _director._decreaseActiveCount();
	}
    }

    /**
     */
    public void setOutputTime(double time) 
            throws IllegalArgumentException {
	if( time < _currentTime ) {
	    throw new IllegalArgumentException();
	}
        _outputTime = time;
    }
    
    /**
     */
    public void setRcvrThreads() {
    }

    /** Notify actors connected via output ports of the actor that 
     *  this thread controls, that this thread's actor will no
     *  longer be producing tokens. Send events with time stamps of
     *  -1.0 to these "downstream" actors. 
     */
    public synchronized void noticeOfTermination() { 
        ODFActor actor = (ODFActor)getActor();
	System.out.println(actor.getName()+": calling noticeOfTermination()");
	Enumeration outputPorts = actor.outputPorts();
	if( outputPorts != null ) {
	while( outputPorts.hasMoreElements() ) {
	    IOPort port = (IOPort)outputPorts.nextElement();
	    Receiver rcvrs[][] = (Receiver[][])port.getRemoteReceivers();
	    if( rcvrs == null ) {
	        break;
	    }
            for (int i = 0; i < rcvrs.length; i++) {
                for (int j = 0; j < rcvrs[i].length; j++) {
	            ((ODFConservativeRcvr) rcvrs[i][j]).put(null, -1.0);
		}
            }
	}
	}
	/*
	System.out.println(actor.getName()+": about to call requestFinish()");
	ODFConservativeRcvr rcvr = getFirstRcvr();
	rcvr.requestFinish();
	getFirstRcvr().get();
	*/
    }

    /** Prepare to cease iterations of the actor that this thread 
     *  controls. Notify actors which are connected downstream of 
     *  of the cessation of this thread's actor. Return false
     *  to indicate that future execution can not occur.
     * @return False to indicate that future execution can not occur.
     * @exception IllegalActionException Not thrown in this class. May be
     *  thrown in derived classes.
     *  FIXME: Why is noticeOfTermination() contained in this method
     *  instead of in wrapup()?
    public boolean postfire() throws IllegalActionException {
        // noticeOfTermination();
        return false;
    }
     */
    
    /** Cause the actor controlled by this thread to send a NullToken 
     *  to all output channels that have a rcvrTime less than the 
     *  current time of this thread. Associate a time stamp with each 
     *  NullToken that is equal to the current time of this thread.
     */
    public void sendOutNullTokens() {
	/*
        ODFActor actor = (ODFActor)getActor();
	Enumeration ports = actor.outputPorts();
        while( ports.hasMoreElements() ) {
            IOPort port = (IOPort)ports.nextElement();
	    Receiver rcvrs[][] = (Receiver[][])port.getRemoteReceivers();
	    if( rcvrs == null ) {
	        return;
	    }
            for (int i = 0; i < rcvrs.length; i++) {
                for (int j = 0; j < rcvrs[i].length; j++) {
                    double time = getCurrentTime();
                    if( time > ( (ODFConservativeRcvr)rcvrs[i][j]
                            ).getRcvrTime() ) {
                        ((ODFConservativeRcvr)rcvrs[i][j]).put( 
                                new NullToken(), time );
                    }
		}
            }
        }
	*/
    }
    
    /** Set the priorities of the receivers contained in the input 
     *  ports of the actor controlled by this thread. Group the input 
     *  receivers for the controlled actor according to their respective 
     *  container input ports. If the containing ports are ODFIOPorts 
     *  and these ports have been explicitly assigned priorities, then 
     *  the groups are ordered in a fashion consistent with this ordering. 
     *  If the ports are not ODFIOPorts or the port priorities have not 
     *  been explicitly assigned, then the groups are ordered according to 
     *  the inverse order in which their corresponding ports were added to 
     *  the containing actor. I.e., if two input ports (port A and port B) 
     *  are added to an actor without explicit priorities such that port 
     *  A is added before port B, then all of the receivers of port B will 
     *  have a higher priority than the receivers of port A.
     *  <P> 
     *  Within a group, order the receiver priorities relative to one 
     *  another according to the inverse order in which they were added to 
     *  the containing ODFIOPort. I.e., if two input receivers (receiver
     *  A and receiver B) are added to an actor such that receiver A is 
     *  added before receiver B, then receiver B will have a higher priority 
     *  than receiver A. 
     *  <P> 
     *  This above approach provides each receiver contained by a given 
     *  ODFThread with a unique priority, such that the set of receiver 
     *  priorities for the containing ODFThread is totally ordered. 
     *  FIXME: Note that receiverThreads are set.
     * @exception IllegalActionException If receiver access leads to an error.
     */
    public synchronized void setPriorities() throws IllegalActionException {
        LinkedList listOfPorts = new LinkedList();
        Actor actor = (Actor)getActor();
	Enumeration enum = actor.inputPorts();
	if( !enum.hasMoreElements() ) {
            return;
	}
        
        //
        // First Order Input Ports According To Priority
        //
        while( enum.hasMoreElements() ) {
            IOPort port = (IOPort)enum.nextElement();
            // int priority;
            // int priority = port.getPriority();
            boolean portNotInserted = true;
            if( listOfPorts.size() == 0 ) {
                listOfPorts.insertAt( 0, port ); 
                portNotInserted = false;
            } else {
	        if( actor instanceof ODFActor ) {
                    for( int cnt = 0; cnt < listOfPorts.size(); cnt++ ) {
			ODFIOPort odfport = (ODFIOPort)port;
                        ODFIOPort nextPort = (ODFIOPort)listOfPorts.at(cnt); 
			if( odfport.getPriority() < nextPort.getPriority() ) {
			    if( odfport != nextPort ) {
				listOfPorts.insertAt( cnt, odfport ); 
				cnt = listOfPorts.size(); 
                            } 
                            portNotInserted = false;
			}
		    }
	        } 
            }
            if( portNotInserted ) {
                listOfPorts.insertLast(port);
                portNotInserted = false;
            }
        } 
        
        //
        // Now Set The Priorities Of Each Port's Receiver
        // And Initialize RcvrList
        //
        int cnt = 0;
        int currentPriority = 0;
        while( cnt < listOfPorts.size() ) {
            ODFIOPort port = (ODFIOPort)listOfPorts.at(cnt);
            Receiver[][] rcvrs = port.getReceivers();
            for( int i = 0; i < rcvrs.length; i++ ) {
                for( int j = 0; j < rcvrs[i].length; j++ ) {
                    ((ODFConservativeRcvr)rcvrs[i][j]).setPriority(
			    currentPriority); 
                    ((ODFConservativeRcvr)rcvrs[i][j]).setThread(this); 
                    RcvrTimeTriple triple = new RcvrTimeTriple( 
                            (ODFConservativeRcvr)rcvrs[i][j], 
			    _currentTime, currentPriority );
                    updateRcvrList( triple );
                    currentPriority++;
                }
            }
            cnt++;
        }
    }

    /** Update the list of RcvrTimeTriples by positioning the 
     */
    public void setRcvrList() throws IllegalActionException {
        Actor actor = (Actor)getActor();
	Enumeration enum = actor.inputPorts();
	if( !enum.hasMoreElements() ) {
            return;
	}
        while( enum.hasMoreElements() ) {
	    IOPort port = (IOPort)enum.nextElement();
            Receiver[][] rcvrs = port.getReceivers();
            for( int i = 0; i < rcvrs.length; i++ ) {
                for( int j = 0; j < rcvrs[i].length; j++ ) {
		    ODFConservativeRcvr rcvr =
                            (ODFConservativeRcvr)rcvrs[i][j]; 
                    RcvrTimeTriple triple = new RcvrTimeTriple( 
			    rcvr, rcvr.getRcvrTime(), 
			    rcvr.getPriority() );
		    updateRcvrList(triple);
		}
	    }
	}
    }

    /** Update the list of RcvrTimeTriples by positioning the 
     *  specified triple. If the specified triple is already
     *  contained in the list, then the triple is removed and
     *  then added back to the list. The position of the triple
     *  is based on the triple's time value. If all receivers
     *  contained in the RcvrTimeTriple list have rcvrTimes of
     *  -1.0, then notify all actors connected via the output
    *  ports of the actor that this thread controls, that this
     *  actor is ceasing execution.
     * @param triple The RcvrTimeTriple to be positioned in the list.
     */
    public synchronized void updateRcvrList(RcvrTimeTriple triple) {
	_removeRcvrTriple( triple );
	_addRcvrTriple( triple );

	/*
	triple = (RcvrTimeTriple)_rcvrTimeList.first();
	if( triple.getTime() == -1.0 ) {
	    // noticeOfTermination();
	    ODFConservativeRcvr firstRcvr = 
		    (ODFConservativeRcvr)triple.getReceiver();
	    firstRcvr.requestFinish();
	    firstRcvr.get();
	}
	*/
    }
    
    /** 
     */
    public void wrapup() throws IllegalActionException {
	noticeOfTermination();
	super.wrapup();
    }

    ///////////////////////////////////////////////////////////////////
    ////                   package friendly methods		   ////

    /** Print the contents of the RcvrTimeTriple list contained by 
     *  this actor. Use this method for testing purposes only.
     * @deprecated
     */
    synchronized void printRcvrList() {
	String name = ((NamedObj)getActor()).getName();
        System.out.println("\n***Print "+name+"'s RcvrList.");
        System.out.println("   Number of Receivers in RcvrList = " 
                + _rcvrTimeList.size() );
        if( _rcvrTimeList.size() == 0 ) {
            System.out.println("\tList is empty");
            System.out.println("***End of printRcvrList()\n");
	    return;
        }
        for( int i = 0; i < _rcvrTimeList.size(); i++ ) {
	    RcvrTimeTriple testTriple = (RcvrTimeTriple)_rcvrTimeList.at(i);
	    Receiver testRcvr = testTriple.getReceiver(); 
            double time = testTriple.getTime();
            String testPort = testRcvr.getContainer().getName();
            String testString = "null";
            String testString2 = "null";
            if( getName().equals("printer") ) {
		System.out.println("   Printer -> size() = "
                        +((ODFConservativeRcvr)testRcvr)._queue.size());
		if( ((ODFConservativeRcvr)testRcvr)._queue.size() > 1 ) {
		    /*
                    Event testEvent2 = 
		            ((Event)((ODFConservativeRcvr)testRcvr)._queue.get(1));
                    StringToken testToken2 = 
		            (StringToken)testEvent2.getToken();
		    testString2 = testToken2.stringValue();
                    System.out.println("\t"+name+"'s Receiver "+i+ 
		            " has a 2nd time of "+testEvent2.getTime()+
			    " and string: ");
		    */
		}
		if( ((ODFConservativeRcvr)testRcvr)._queue.size() > 0 ) {
                    Event testEvent = 
                            ((Event)((ODFConservativeRcvr)testRcvr)._queue.get(0));
                    StringToken testToken = (StringToken)testEvent.getToken();
		    if( testToken != null ) {
                        testString = testToken.stringValue();
		    }
		} else {
                    testString = "null";
		}
            }
            System.out.println("\t"+name+"'s Receiver "+i+
	            " has a time of " +time+" and string: "+testString);
        }
        System.out.println("***End of printRcvrList()\n");
    }

    ///////////////////////////////////////////////////////////////////
    ////                         methods			   ////

    /** Add the specified RcvrTimeTriple to the list of triples.
     *  If the time stamp of the specified triple is -1.0, then
     *  insert the triple into the last position of the RcvrTimeTriple
     *  list. Otherwise, insert the triple immediately after all
     *  other triples with time stamps less than or equal to the
     *  time stamp of the specified triple. ALWAYS call _removeRcvrTriple 
     *  immediately before calling this method if the RcvrTimeTriple list 
     *  already contains the triple specified in the argument.
     */
    private void _addRcvrTriple(RcvrTimeTriple newTriple) {
        if( _rcvrTimeList.size() == 0 ) {
            _rcvrTimeList.insertAt( 0, newTriple );
            return;
        }

	if( newTriple.getTime() == -1.0 ) {
	    _rcvrTimeList.insertLast(newTriple);
	    return;
	}
        
	int cnt = 0;
        boolean notAddedYet = true;
	while( cnt < _rcvrTimeList.size() ) {
	    RcvrTimeTriple triple = (RcvrTimeTriple)_rcvrTimeList.at(cnt);
            
	    if( triple.getTime() == -1.0 ) {
	        _rcvrTimeList.insertAt( cnt, newTriple );
		cnt = _rcvrTimeList.size();
                notAddedYet = false;
	    } else if( newTriple.getTime() < triple.getTime() ) {
	        _rcvrTimeList.insertAt( cnt, newTriple );
		cnt = _rcvrTimeList.size();
                notAddedYet = false;
	    }
	    cnt++;
	}
        
        if( notAddedYet ) {
            _rcvrTimeList.insertLast( newTriple );
        }
    }
    
    /** Remove the specified RcvrTimeTriple from the list of triples.
     */
    private void _removeRcvrTriple(RcvrTimeTriple triple) {

        Receiver rcvrToBeRemoved = triple.getReceiver();
        
	for( int cnt = 0; cnt < _rcvrTimeList.size(); cnt++ ) {
	    RcvrTimeTriple nextTriple = (RcvrTimeTriple)_rcvrTimeList.at(cnt);
	    Receiver nextRcvr = nextTriple.getReceiver(); 
            
	    if( rcvrToBeRemoved == nextRcvr ) {
	        _rcvrTimeList.removeAt( cnt );
		cnt = _rcvrTimeList.size();
	    }
	}
    }

    ///////////////////////////////////////////////////////////////////
    ////                        private variables                  ////

    private Manager _manager;
    private ProcessDirector _director;

    // The _rcvrTimeList stores RcvrTimeTriples and is used to
    // order the receivers according to time and priority.
    public LinkedList _rcvrTimeList;
    
    // The currentTime of the actor that is controlled by this
    // thread is equivalent to the minimum positive rcvrTime of 
    // each input receiver. This value is updated every time
    // updateRcvrList() is called.
    private double _currentTime = 0.0;

    private double _outputTime = 0.0;
}








