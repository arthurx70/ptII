# Makefile for PtolemyII Wireless demos 
#
# @Version: $Id$
# @Author: Christopher Hylands Brooks (makefile only)
#
# @Copyright (c) 2003-2014 The Regents of the University of California.
# All rights reserved.
#
# Permission is hereby granted, without written agreement and without
# license or royalty fees, to use, copy, modify, and distribute this
# software and its documentation for any purpose, provided that the
# above copyright notice and the following two paragraphs appear in all
# copies of this software.
#
# IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
# FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
# ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
# THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
# SUCH DAMAGE.
#
# THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
# INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
# MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
# PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
# CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
# ENHANCEMENTS, OR MODIFICATIONS.
#
# 						PT_COPYRIGHT_VERSION_2
# 						COPYRIGHTENDKEY

ME =		ptolemy/domains/wireless/demo

# Keep these alphabetical please.
DIRS = \
	AntennaPattern \
	CircularRange \
	Collisions \
	EvaderAndPursuer \
	Intersections \
	LinkVisualizer \
	MatlabWirelessSoundDetection \
	Network \
	PowerVariability \
	SmallWorld \
	SmartParking \
	TerrainModel \
	WirelessSoundDetection \
	Zigbee

# Directories we are not shipping yet
# NOTE: If you add these to DIRS above, add them in alphabetical order
#       and add an entry to PTCLASSALLJARS below.
OTHER_DIRS = \
	PersonalAreaNetwork

# Root of Ptolemy II directory
ROOT =		../../../..

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	demo
PTCLASSJAR =

# Include the .class files from these jars in PTCLASSALLJAR
# These models are listed in ptII/ptolemy/configs/visualsense/intro.htm
PTCLASSALLJARS = \
	AntennaPattern/AntennaPattern.jar \
	CircularRange/CircularRange.jar \
	Collisions/Collisions.jar \
	EvaderAndPursuer/EvaderAndPursuer.jar \
	Intersections/Intersections.jar \
	MatlabWirelessSoundDetection/MatlabWirelessSoundDetection.jar \
	LinkVisualizer/LinkVisualizer.jar \
	PowerVariability/PowerVariability.jar \
	SmallWorld/SmallWorld.jar \
	SmartParking/SmartParking.jar \
	TerrainModel/TerrainModel.jar \
	WirelessSoundDetection/WirelessSoundDetection.jar \
	Zigbee/Zigbee.jar

# Not clear what this demo does
#	Network/Network.jar
#	PersonalAreaNetwork/PersonalAreaNetwork.jar

PTCLASSALLJAR = $(PTPACKAGE).jar

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES = 	$(DIRS) \
	index.htm

# make checkjunk will not report OPTIONAL_FILES as trash
# make realclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc \
	$(OTHER_DIRS)

all: jclass
	@echo "To run all the demos, run 'make demo'"

install: jclass jars

demo: subdemo

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
