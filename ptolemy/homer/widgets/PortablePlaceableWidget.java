/*
 Copyright (c) 2011 The Regents of the University of California.
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

package ptolemy.homer.widgets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import org.netbeans.api.visual.widget.Scene;

import ptolemy.actor.gui.AWTContainer;
import ptolemy.actor.gui.PortablePlaceable;
import ptolemy.kernel.util.NamedObj;

///////////////////////////////////////////////////////////////////
//// PortablePlaceableWidget

public class PortablePlaceableWidget extends GlassPaneWidget {

    public PortablePlaceableWidget(Scene scene, NamedObj namedObject) {
        super(scene, namedObject);
        if (!(namedObject instanceof PortablePlaceable)) {
            throw new IllegalArgumentException(
                    "NamedObject must be instance of PortablePlaceable");
        }
        setGlassPaneSize(new Dimension(300, 200));
        ((PortablePlaceable) namedObject).place(new AWTContainer(
                _containerPanel) {
            @Override
            public void add(Object object) {
                Component component = (Component) object;
                _containerPanel.add(component, BorderLayout.CENTER);
            }
        });
    }

}
