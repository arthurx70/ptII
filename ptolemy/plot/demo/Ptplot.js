// $Id$
// @Copyright (c) 1997 The Regents of the University of California.
// All rights reserved.
// 
// Permission is hereby granted, without written agreement and without
// license or royalty fees, to use, copy, modify, and distribute this
// software and its documentation for any purpose, provided that the
// above copyright notice and the following two paragraphs appear in all
// copies of this software.
// 
// IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
// FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE.
//
// THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
// INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
// ENHANCEMENTS, OR MODIFICATIONS.
//
//                                                PT_COPYRIGHT_VERSION_2
//                                                COPYRIGHTENDKEY


// 
function checkBoxValue(flag, onstring, offstring) {
	if (flag == "on") 
		return onstring
	else
		return offstring
}
function computeArgs(theform) {
	var retval = "";
	retval += ( theform.ticks.value == "on" ) ? "-tk ": "";
	retval += ( theform.binary.value == "on" ) ? "-binary ": "";
	return retval;
}

function computeArgs(ticks, binary) {
	var retval = "";
	retval += ( ticks == "on" ) ? "-tk ": "";
	retval += ( binary == "on" ) ? "-binary ": "";
	return retval;
}

// Create an applet with the following parameters
function ptplot(theform) {
var width = theform.width.value;
var height = theform.height.value; 
var background = theform.background.value;
var foreground = theform.foreground.value;
var urlbase = theform.urlbase.value;
fileObj = theform.fileSelection;
var file = fileObj.options[fileObj.selectedIndex].text;
var pxgraphargs = theform.pxgraphargs.value; 
var displayargs = theform.displayargs.checked;
var otherargs = "";

otherargs += ( theform.bar.checked ) ? "-bar ": "";
otherargs += ( theform.binary.checked ) ? "-binary ": "";
otherargs += ( theform.nolines.checked ) ? "-nl ": "";
otherargs += ( theform.smallpoints.checked ) ? "-p ": "";
otherargs += ( theform.largepoints.checked ) ? "-P ": "";
otherargs += ( theform.reversevideo.checked ) ? "-rv ": "";
otherargs += ( theform.ticks.checked ) ? "-tk ": "";
otherargs += ( theform.title.value != "" ) ? 
	"-t '" + theform.title.value + "' " : "";
//otherargs += ( theform.titlefont.value != "" ) ? 
//	"-tf '" + theform.titlefont.value + "' " : "";

otherargs += "-tf " +
 theform.titleFontFamily.options[theform.titleFontFamily.selectedIndex].text +
 "-" +
 theform.titleFontStyle.options[theform.titleFontStyle.selectedIndex].text +
 "-" +
 theform.titleFontSize.options[theform.titleFontSize.selectedIndex].text +
 " ";

otherargs += ( theform.xaxis.value != "" ) ? 
	"-x '" + theform.xaxis.value + "' " : "";
otherargs += ( theform.yaxis.value != "" ) ? 
	"-y '" + theform.yaxis.value + "' " : "";

if (urlbase != "" && file != "(NONE)") {
	pxgraphargs = urlbase + file + " " + pxgraphargs;
}

pxgraphargs = otherargs + pxgraphargs;


document.write("<html>");
document.write("<head>");
document.write("<title>Plot</title>");
document.write("<body bgcolor=\"#faf0e6\">");
document.write("<H1>Plot</H1>");
//document.write("<pre>");
document.write("<applet name=\"JSPlot\" code=\"ptplot.PlotApplet\"");
document.write("   width=" + parseInt(width));
document.write("   height=" + parseInt(height));
document.write("   codebase=\"../..\"\n");
document.write("   alt=\"If you had a java-enabled browser you would see an applet here.\">\n");
if ( background != "" )
 document.write("<param name=\"background\" value=\"" + background +"\">\n");
if ( foreground.value != "" )
 document.write("<param name=\"foreground\" value=\"" + foreground +"\">\n");
if ( pxgraphargs != "" )
 document.write("<param name=\"pxgraphargs\" value=\"" + pxgraphargs +"\">\n");
document.write("</applet>");
document.write("<form name=\"aboutplot\">")
document.write("<input type=\"button\" Value=\"About\"");
document.write("onClick=\"alert(document.JSPlot.getAppletInfo())\">");
document.write("</form>");
//document.write("</pre>");
if ( displayargs ) {
	document.write("<table border> <tr><th>Variable<th>Value");
	document.write("<tr><td>width<td>" + width);
	document.write("<tr><td>height<td>" + height);
	document.write("<tr><td>background<td>" + background);
	document.write("<tr><td>foreground<td>" + foreground);
	document.write("<tr><td>pxgraphargs<td>" + pxgraphargs);
	document.write("</table>");
}

document.write("</body>");
document.write("</html>");

}
