/*

 Copyright (c) 1997-1999 The Regents of the University of California.
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

@ProposedRating Red (davisj@eecs.berkeley.edu)

*/

package ptolemy.domains.dde.lib;

import ptolemy.domains.dde.kernel.*;
import ptolemy.kernel.util.*;
import ptolemy.actor.*;
import ptolemy.data.*;
import collections.LinkedList;

//////////////////////////////////////////////////////////////////////////
//// TimedSource
/**


@author John S. Davis II
@version $Id$
*/
public class TimedSource extends DDEActor {

    /**
     */
    public TimedSource(TypedCompositeActor container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
	output = new TypedIOPort( this, "output", false, true );
	output.setTypeEquals(Token.class);
    }

    ///////////////////////////////////////////////////////////////////
    ////                        public variables                   ////

    public TypedIOPort output;

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /**
     */
    public void fire() throws IllegalActionException {
	output.broadcast(new Token());
	Director dir = ((CompositeActor)getContainer()).getDirector();
	dir.fireAt( this, getCurrentTime() + _interval );
    }

    /**
     */
    public void setInterval(double interval) {
	_interval = interval;
    }

    ///////////////////////////////////////////////////////////////////
    ////                        private variables                  ////

    private double _interval = 1.0;

}
