/*

 Copyright (c) 2003-2006 The Regents of the University of California.
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
package ptolemy.actor.gt;

import java.util.Collection;

import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.NamedObj;
import ptolemy.kernel.util.StringAttribute;
import ptolemy.kernel.util.Workspace;

//////////////////////////////////////////////////////////////////////////
//// RuleListAttribute

/**

@author Thomas Huining Feng
@version $Id$
@since Ptolemy II 6.1
@Pt.ProposedRating Red (tfeng)
@Pt.AcceptedRating Red (tfeng)
*/
public class RuleListAttribute extends StringAttribute {

    public RuleListAttribute() {
    }

    public RuleListAttribute(NamedObj container, String name)
    throws IllegalActionException, NameDuplicationException {
        super(container, name);
    }

    public RuleListAttribute(Workspace workspace) {
        super(workspace);
    }

    public String getExpression() {
        // FIXME: serialize _ruleList
        return super.getExpression();
    }

    public RuleList getRuleList() throws MalformedStringException {
        if (!_parsed) {
            _parse();
        }
        return _ruleList;
    }

    public void setExpression(String expression) throws IllegalActionException {
        _parsed = false;
        super.setExpression(expression);
    }

    public Collection<?> validate() throws IllegalActionException {
        if (!_parsed) {
            try {
                _parse();
            } catch (MalformedStringException e) {
                throw new IllegalActionException(e.getMessage());
            }
        }
        return null;
    }

    private void _parse() throws MalformedStringException {
        _parsed = true;
        _ruleList = RuleList.parse(super.getExpression());
    }

    private boolean _parsed = false;

    private RuleList _ruleList = null;

    private static final long serialVersionUID = 3642067100376815343L;

}
