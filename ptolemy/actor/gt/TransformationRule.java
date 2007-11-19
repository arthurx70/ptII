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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ptolemy.actor.Initializable;
import ptolemy.actor.TypedIOPort;
import ptolemy.actor.gt.data.MatchResult;
import ptolemy.actor.lib.hoc.MultiCompositeActor;
import ptolemy.data.ActorToken;
import ptolemy.data.ObjectToken;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.StringAttribute;
import ptolemy.kernel.util.Workspace;

//////////////////////////////////////////////////////////////////////////
//// SingleRuleTransformer

/**

@author Thomas Huining Feng
@version $Id$
@since Ptolemy II 6.1
@Pt.ProposedRating Red (tfeng)
@Pt.AcceptedRating Red (tfeng)
*/
public class TransformationRule extends MultiCompositeActor
implements MatchCallback {

    public TransformationRule(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
        _init();
    }

    public TransformationRule(Workspace workspace)
    throws IllegalActionException, NameDuplicationException {
        super(workspace);
        _init();
    }
    
    public void stop() {
        _stopRequested = true;

        if (_debugging) {
            _debug("Called stop()");
        }
    }

    public void fire() throws IllegalActionException {
        if (modelInput.hasToken(0)) {
            ActorToken token = (ActorToken) modelInput.get(0);
            _lastModel = (CompositeEntity) token.getEntity();
            _lastResults.clear();
            
            GraphMatcher matcher = new GraphMatcher();
            matcher.setMatchCallback(this);
            matcher.match(getPattern(), _lastModel);
        }

        if (matchInput.getWidth() > 0 && matchInput.hasToken(0)
                && _lastModel != null) {
            ObjectToken token = (ObjectToken) matchInput.get(0);
            MatchResult match = (MatchResult) token.getValue();
            if (match != null) {
                CompositeEntity host =
                    (CompositeEntity) match.get(getPattern());
                if (_lastModel != host && !_lastModel.deepContains(host)) {
                    throw new IllegalActionException(this, "The match result "
                            + "cannot be used with the current model.");
                }
                try {
                    GraphTransformer.transform(this, match);
                    modelOutput.send(0, new ActorToken(_lastModel));
                } catch (Exception e) {
                    throw new IllegalActionException(this, e,
                            "Unable to transform model.");
                }
            }
        }

        if (trigger.getWidth() > 0 && trigger.hasToken(0)
                && !_lastResults.isEmpty()) {
            trigger.get(0);
            matchOutput.send(0, new ObjectToken(_lastResults.remove()));
        }
    }

    public boolean foundMatch(GraphMatcher matcher) {
        _lastResults.add((MatchResult) matcher.getMatchResult().clone());
        return false;
    }

    public Pattern getPattern() {
        return (Pattern) getEntity("Pattern");
    }

    public Replacement getReplacement() {
        return (Replacement) getEntity("Replacement");
    }

    public void initialize() throws IllegalActionException {
        if (_debugging) {
            _debug("Called initialize()");
        }
        // First invoke initializable methods.
        if (_initializables != null) {
            for (Initializable initializable : _initializables) {
                initializable.initialize();
            }
        }
        // Update the version only after everything has been
        // preinitialized because there might have been additional
        // workspace version updates during preinitialize().
        // We assume they were properly handled and new receivers
        // were created if needed.
        _receiversVersion = workspace().getVersion();
    }

    public boolean isOpaque() {
        return true;
    }

    public boolean postfire() throws IllegalActionException {
        if (_debugging) {
            _debug("Called postfire()");
        }

        return !_stopRequested;
    }

    public boolean prefire() throws IllegalActionException {
        return modelInput.hasToken(0)
                || matchInput.getWidth() > 0 && matchInput.hasToken(0)
                        && _lastModel != null
                || trigger.getWidth() > 0 && trigger.hasToken(0)
                        && !_lastResults.isEmpty();
    }

    public void preinitialize() throws IllegalActionException {
        if (_debugging) {
            _debug("Called preinitialize()");
        }

        _stopRequested = false;

        // First invoke initializable methods.
        if (_initializables != null) {
            for (Initializable initializable : _initializables) {
                initializable.preinitialize();
            }
        }

        // As an optimization, avoid creating receivers if
        // the workspace version has not changed.
        if (workspace().getVersion() != _receiversVersion) {
            // NOTE:  Receivers are also getting created
            // in connectionChanged().  Perhaps this is here to ensure
            // that the receivers are reset?
            _createReceivers();
        }
    }

    public void stopFire() {
    }

    public List<?> typeConstraintList() {
        return new LinkedList<Object>();
    }

    public void wrapup() throws IllegalActionException {
        if (_debugging) {
            _debug("Called wrapup()");
        }
        // Invoke initializable methods.
        if (_initializables != null) {
            for (Initializable initializable : _initializables) {
                initializable.wrapup();
            }
        }
    }

    public TypedIOPort matchInput;

    public TypedIOPort matchOutput;

    public TypedIOPort modelInput;

    public TypedIOPort modelOutput;

    public TypedIOPort trigger;

    protected void _init()
    throws IllegalActionException, NameDuplicationException {
        setClassName("ptolemy.actor.gt.TransformationRule");

        // Create the default refinement.
        new Pattern(this, "Pattern");
        new Replacement(this, "Replacement");

        // Create ports.
        modelInput = new TypedIOPort(this, "modelInput", true, false);
        modelInput.setTypeEquals(ActorToken.TYPE);
        modelOutput = new TypedIOPort(this, "modelOutput", false, true);
        modelOutput.setTypeEquals(ActorToken.TYPE);
        matchInput = new TypedIOPort(this, "matchInput", true, false);
        matchInput.setTypeEquals(BaseType.OBJECT);
        matchOutput = new TypedIOPort(this, "matchOutput", false, true);
        matchOutput.setTypeEquals(BaseType.OBJECT);
        trigger = new TypedIOPort(this, "trigger", true, false);
        trigger.setTypeEquals(BaseType.BOOLEAN);
        StringAttribute resetCardinal = new StringAttribute(trigger, "_cardinal");
        resetCardinal.setExpression("SOUTH");
    }

    private CompositeEntity _lastModel;

    private Queue<MatchResult> _lastResults = new LinkedList<MatchResult>();

    /** Record of the workspace version the last time receivers were created. */
    private long _receiversVersion = -1;

}
