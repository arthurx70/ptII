/* Compute the composition of interface automata.

 Copyright (c) 1999-2001 The Regents of the University of California.
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
@ProposedRating Red (yuhong@eecs.berkeley.edu)
@AcceptedRating Red (reviewmoderator@eecs.berkeley.edu)
*/

package ptolemy.domains.fsm.kernel.test;

import ptolemy.actor.gui.MoMLApplication;
import ptolemy.moml.MoMLParser;
import ptolemy.domains.fsm.kernel.InterfaceAutomaton;

import java.net.URL;

//////////////////////////////////////////////////////////////////////////
//// Compose
/**
Compute the composition of interface automata.
This class reads the MoML description of a number of interface automata,
computes their composition, then writes the MoML description of the
composition to stdout. The usage is:
<pre>
java ptolemy.domains.fsm.kernel.test.Compose <automaton1.xml> <automaton2.xml> ...
</pre>

@author Yuhong Xiong
@version $Id$
*/

public class Compose {

    /** Compose the interface automata in the argument array and write
     *  the MoML description for the composition to stdout.
     *  @param momls An array of MoML file names for InterfaceAutomaton.
     *  @exception Exception If the automata cannot be composed.
     */
    public Compose(String[] momls) throws Exception {
        InterfaceAutomaton[] automata = new InterfaceAutomaton[momls.length];
        for (int i=0; i<momls.length; i++) {
            URL url = MoMLApplication.specToURL(momls[i]);

            // following the comments in MoMLApplication, use the same URL for
            // the two arguments (base and URL) to parse(). Also, a instance
            // of MoMLParser must be used to parse each file, otherwise
            // the same automaton will be returned the second time parse() is
            // called.
            MoMLParser parser = new MoMLParser();
            automata[i] = (InterfaceAutomaton)parser.parse(url, url);
            automata[i].addPorts();
        }

        InterfaceAutomaton composition = automata[0];
	for (int i=1; i<momls.length; i++) {
            composition = composition.compose(automata[i]);
        }
        System.out.println(composition.exportMoML());
    }

    /** Pass the command line arguments to the constructor. The command line
     *  argument is a list of MoML files for InterfaceAutomaton.
     *  @param args The command line arguments.
     */
    public static void main (String[] args) {
        try {
            new Compose(args);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public variables                  ////

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    ///////////////////////////////////////////////////////////////////
    ////                       protected variables                 ////

    ///////////////////////////////////////////////////////////////////
    ////                         protected methods                 ////

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////

    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////

}
