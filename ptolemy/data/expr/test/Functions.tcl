# Tests for function evaluation in expressions
#
# @Author: Edward A. Lee and Steve Neuendorffer
#
# @Version: $Id$
#
# @Copyright (c) 1998-2003 The Regents of the University of California.
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
#######################################################################

# Tycho test bed, see $TYCHO/doc/coding/testing.html for more information.

# Load up the test definitions.
if {[string compare test [info procs test]] == 1} then { 
    source testDefs.tcl
} {}

# Uncomment this to get a full report, or set in your Tcl shell window.
# set VERBOSE 1

# If a file contains non-graphical tests, then it should be named .tcl
# If a file contains graphical tests, then it should be called .itcl
# 
# It would be nice if the tests would work in a vanilla itkwish binary.
# Check for necessary classes and adjust the auto_path accordingly.
#

proc evaluate {root} {
    set evaluator [java::new ptolemy.data.expr.ParseTreeEvaluator]
    $evaluator evaluateParseTree $root
}

proc theTest {expression} {
    set p1 [java::new ptolemy.data.expr.PtParser]
    set root [ $p1 {generateParseTree String} $expression]
    [evaluate $root] toString
}

# Call ptclose on the results.
# Use this proc if the results are slightly different under Solaris 
# and Windows
proc theTestPtClose {expression results} {
    set p1 [java::new ptolemy.data.expr.PtParser]
    set root [ $p1 {generateParseTree String} $expression]
    ptclose [[evaluate $root] toString] $results
}

####################################################################
# acos

test Function-acos {Test acos} {
    list [theTestPtClose {acos(0.0)} 1.5707963267949] \
         [theTest {acos(1.0)}] \
         [theTest {acos(1)}] \
         [theTest {acos(1ub)}] \
         [theTestPtClose {acos(1.0+0.0i)} {0.0 + 0.0i}] \
     } {1 0.0 0.0 0.0 1}

####################################################################
# asin

test Function-asin {Test asin} {
    list [theTestPtClose {asin(1.0)} 1.5707963267949] \
         [theTest {asin(0.0)}] \
         [theTest {asin(0)}] \
         [theTest {asin(0ub)}] \
         [theTestPtClose {asin(1.0+0.0i)} {1.5707963267949 + 0.0i}] \
     } {1 0.0 0.0 0.0 1}

####################################################################
# atan

test Function-atan {Test atan} {
    list [theTestPtClose {atan(1.0)} 0.7853981633974] \
         [theTestPtClose {atan(-1)} -0.7853981633974] \
         [theTest {atan(0ub)}] \
         [theTestPtClose {atan(Infinity)} 1.5707963267949] \
         [theTestPtClose {atan(0.0+0.0i)} {0.0 + 0.0i}]
     } {1 1 0.0 1 1}

####################################################################
# atan2

test Function-atan2 {Test atan2} {
    list [theTestPtClose {atan2(1.0, 1.0)} 0.7853981633974] \
         [theTestPtClose {atan2(-1, 1)} -0.7853981633974] \
         [theTest {atan2(0ub, 1ub)}] \
         [theTestPtClose {atan2(Infinity, 1.0)} 1.5707963267949] \
     } {1 1 0.0 1}

####################################################################
# acosh

test Function-acosh {Test acosh} {
    list [theTestPtClose {acosh(1.0)} 0.0] \
         [theTestPtClose {acosh(1)} 0.0] \
         [theTestPtClose {acosh(1ub)} 0.0] \
         [theTestPtClose {acosh(1.0+0.0i)} {0.0 + 0.0i}] \
     } {1 1 1 1}

####################################################################
# asinh

test Function-asinh {Test asinh} {
    list [theTestPtClose {asinh(0.0)} 0.0] \
         [theTestPtClose {asinh(0)} 0.0] \
         [theTestPtClose {asinh(0ub)} 0.0] \
         [theTestPtClose {asinh(0.0+0.0i)} {0.0 + 0.0i}] \
     } {1 1 1 1}

test Function-asinh-2 {Test asinh} {
    list [theTestPtClose {asinh(1+i)} \
	{1.0612750619050355 + 0.6662394324925153i}] \
         [theTestPtClose {asinh({1+i, 1-i})} \
	{{1.0612750619050355 + 0.6662394324925153i, 1.0612750619050355 - 0.6662394324925153i}}] \
	[theTestPtClose {asinh([1+i, 1-i])} \
	{[1.0612750619050357 + 0.6662394324925153i, 1.0612750619050357 - 0.6662394324925153i]}]
} {1 1 1}

####################################################################
# cos

test Function-cos {Test cos} {
    list [theTestPtClose {cos(0.0)} 1.0] \
         [theTestPtClose {cos(0)} 1.0] \
         [theTestPtClose {cos(0ub)} 1.0] \
         [theTestPtClose {cos(0.0+0.0i)} {1.0 + 0.0i}] \
     } {1 1 1 1}

test Function-cos-2 {Test cos} {
    list [theTest {cos(1+i)}] \
         [theTest {cos({1+i, 1-i})}] \
         [theTest {cos([1+i, 1-i])}]
} {{0.8337300251311491 - 0.9888977057628653i} {{0.8337300251311491 - 0.9888977057628653i, 0.8337300251311491 + 0.9888977057628653i}} {[0.8337300251311491 - 0.9888977057628653i, 0.8337300251311491 + 0.9888977057628653i]}}

####################################################################
# cosh

test Function-cosh {Test cosh} {
    list [theTestPtClose {cosh(0.0)} 1.0] \
         [theTestPtClose {cosh(0)} 1.0] \
         [theTestPtClose {cosh(0ub)} 1.0] \
         [theTestPtClose {cosh(0.0+0.0i)} {1.0 + 0.0i}] \
     } {1 1 1 1}

 test Function-cosh-2 {Test cosh} {
    list [theTest {cosh(1+i)}] \
         [theTest {cosh({1+i, 1-i})}] \
         [theTest {cosh([1+i, 1-i])}]
 } {{0.8337300251311491 + 0.9888977057628653i} {{0.8337300251311491 + 0.9888977057628653i, 0.8337300251311491 - 0.9888977057628653i}} {[0.8337300251311491 + 0.9888977057628653i, 0.8337300251311491 - 0.9888977057628653i]}}

####################################################################
# sin

test Function-sin {Test sin} {
    list [theTestPtClose {sin(0.0)} 0.0] \
         [theTestPtClose {sin(0)} 0.0] \
         [theTestPtClose {sin(0ub)} 0.0] \
         [theTestPtClose {sin(0.0+0.0i)} {0.0 + 0.0i}] \
     } {1 1 1 1}

test Function-sin-2 {Test sin} {
    list [theTest {sin(1+i)}] \
         [theTest {sin({1+i, 1-i})}] \
         [theTest {sin([1+i, 1-i])}]
} {{1.2984575814159776 + 0.6349639147847362i} {{1.2984575814159776 + 0.6349639147847362i, 1.2984575814159776 - 0.6349639147847362i}} {[1.2984575814159776 + 0.6349639147847362i, 1.2984575814159776 - 0.6349639147847362i]}}

####################################################################
# sinh

test Function-sinh {Test sinh} {
    list [theTestPtClose {sinh(0.0)} 0.0] \
         [theTestPtClose {sinh(0)} 0.0] \
         [theTestPtClose {sinh(0ub)} 0.0] \
         [theTestPtClose {sinh(0.0+0.0i)} {0.0 + 0.0i}] \
     } {1 1 1 1}

 test Function-sinh-2 {Test sinh} {
    list [theTest {sinh(1+i)}] \
         [theTest {sinh({1+i, 1-i})}] \
         [theTest {sinh([1+i, 1-i])}]
 } {{0.6349639147847362 + 1.2984575814159776i} {{0.6349639147847362 + 1.2984575814159776i, 0.6349639147847362 - 1.2984575814159776i}} {[0.6349639147847362 + 1.2984575814159776i, 0.6349639147847362 - 1.2984575814159776i]}}

####################################################################
# tan

test Function-tan {Test tan} {
    list [theTestPtClose {tan(0.0)} 0.0] \
         [theTestPtClose {tan(pi)} 0.0] \
         [theTestPtClose {tan(0)} 0.0] \
         [theTestPtClose {tan(0ub)} 0.0] \
         [theTestPtClose {tan(0.0+0.0i)} {0.0 + 0.0i}] \
     } {1 1 1 1 1}

 test Function-tan-2 {Test tan} {
    list [theTest {tan(1+i)}] \
         [theTest {tan({1+i, 1-i})}] \
         [theTest {tan([1+i, 1-i])}]
 } {{0.27175258531951163 + 1.0839233273386946i} {{0.27175258531951163 + 1.0839233273386946i, 0.27175258531951163 - 1.0839233273386946i}} {[0.27175258531951163 + 1.0839233273386946i, 0.27175258531951163 - 1.0839233273386946i]}}

####################################################################
# tanh

test Function-tanh {Test tanh} {
    list [theTestPtClose {tanh(0.0)} 0.0] \
         [theTestPtClose {tanh(0)} 0.0] \
         [theTestPtClose {tanh(0ub)} 0.0] \
         [theTestPtClose {tanh(0.0+0.0i)} {0.0 + 0.0i}] \
     } {1 1 1 1}

 test Function-tanh {Test tanh} {
    list [theTest {tanh(1+i)}] \
         [theTest {tanh({1+i, 1-i})}] \
         [theTest {tanh([1+i, 1-i])}]
 } {{1.0839233273386946 + 0.27175258531951163i} {{1.0839233273386946 + 0.27175258531951163i, 1.0839233273386946 - 0.27175258531951163i}} {[1.0839233273386946 + 0.27175258531951163i, 1.0839233273386946 - 0.27175258531951163i]}}

####################################################################
####################################################################
####################################################################
####################################################################
# New table in the docs:

####################################################################
# abs

test Function-abs {Test abs on scalars} {
    list [theTestPtClose {abs(1+i)} 1.4142135623731] \
         [theTest {abs(-1.0)}] \
         [theTest {abs(-1)}] \
         [theTest {abs(1ub)}] \
         [theTest {abs(-1L)}] \
         [theTest {abs(1ub)}] \
} {1 1.0 1 1 1L 1}

test Function-abs-2 {Test abs on arrays} {
    list [theTestPtClose {abs({1+i, 1-i})} {{1.4142135623731, 1.4142135623731}}] \
         [theTest {abs({-1.0, -2.0})}] \
         [theTest {abs({-1, -2})}] \
         [theTest {abs({-1L, -2L})}] \
     } {1 {{1.0, 2.0}} {{1, 2}} {{1L, 2L}}}

test Function-abs-3 {Test abs on matrices} {
    list [theTestPtClose {abs([1+i, 1-i])} \
                  {[1.4142135623731, 1.4142135623731]}] \
         [theTest {abs(-identityDouble(2))}] \
         [theTest {abs(-identityInt(2))}] \
         [theTest {abs(-identityLong(2))}] \
         [theTest {abs(-identityComplex(2))}] \
} {1 {[1.0, 0.0; 0.0, 1.0]} {[1, 0; 0, 1]} {[1L, 0L; 0L, 1L]} {[1.0, 0.0; 0.0, 1.0]}}

####################################################################
# angle

 test Function-angle {Test angle} {
    list [theTestPtClose {angle(1+i)} 0.7853981633974] \
         [theTestPtClose {angle({1+i, 1-i})} \
                 {{0.7853981633974, -0.7853981633974}}] \
         [theTestPtClose {angle([1+i, 1-i])} \
                 {[0.7853981633974, -0.7853981633974]}] \
         [theTestPtClose {angle(i)} {1.5707963267949}] \
         [theTest {angle(0.0i)}] \
 } {1 1 1 1 0.0}


####################################################################
# ceil

test Function-ceil {Test ceil on scalars} {
    list [theTest {ceil(-1.1)}] \
         [theTest {ceil(-1)}] \
         [theTest {ceil(1ub)}] \
         [theTest {ceil({1.1, 2.1})}] \
         [theTest {ceil([1.1, 2.1])}] \
     } {-1.0 -1.0 1.0 {{2.0, 3.0}} {[2.0, 3.0]}}

####################################################################
# compare

 test Function-compare {Test compare} {
    list [theTest {compare(1.0, 2.0)}] \
         [theTest {compare(1, 2)}] \
         [theTest {compare(1ub, 2ub)}] \
         [theTest {compare({1, 2}, {3, 4})}] \
         [theTest {compare([1, 2], [3, 4])}] \
     } {-1 -1 -1 {{-1, -1}} {[-1, -1]}}

####################################################################
# conjugate

 test Function-conjugate {Test conjugate} {
    list [theTest {conjugate(1+i)}] \
         [theTest {conjugate({1+i, 1-i})}] \
         [theTest {conjugate([1+i, 1-i])}] \
 } {{1.0 - 1.0i} {{1.0 - 1.0i, 1.0 + 1.0i}} {[1.0 - 1.0i, 1.0 + 1.0i]}}

####################################################################
# exp

####################################################################
# floor

####################################################################
# imag

 test Function-7.1 {Test imag} {
    list [theTest {imag(1+i)}] \
         [theTest {imag({1+i, 1-i})}] \
         [theTest {imag([1+i, 1-i])}] \
     } {1.0 {{1.0, -1.0}} {[1.0, -1.0]}}

####################################################################
# log

####################################################################
# log10

####################################################################
# log2

####################################################################
# max

####################################################################
# min

####################################################################
# pow

####################################################################
# random

# Tough to test?

####################################################################
# real

 test Function-15.1 {Test real} {
    list [theTest {real(1+i)}] \
         [theTest {real({1+i, 1-i})}] \
         [theTest {real([1+i, 1-i])}] \
     } {1.0 {{1.0, 1.0}} {[1.0, 1.0]}}

####################################################################
# remainder

####################################################################
# round

####################################################################
# sgn

####################################################################
# sqrt

####################################################################
# toDegrees

####################################################################
# toRadians





####################################################################
# acos

test Function-19.2 {Test various function calls} {
    list [theTestPtClose {acos(1+i)} \
	{0.9045568943023814 - 1.0612750619050355i}] \
         [theTestPtClose {acos({1+i, 1-i})} \
	{{0.9045568943023814 - 1.0612750619050355i, 0.9045568943023812 + 1.0612750619050355i}}] \
         [theTestPtClose {acos([1+i, 1-i])} \
	{[0.9045568943023814 - 1.0612750619050355i, 0.9045568943023812 + 1.0612750619050355i]}]
} {1 1 1}

test Function-19.5 {Test various function calls} {
    list [theTestPtClose {asin(1+i)} \
	{0.6662394324925155 + 1.0612750619050355i}] \
         [theTestPtClose {asin({1+i, 1-i})} \
	{{0.6662394324925155 + 1.0612750619050355i, 0.6662394324925153 - 1.0612750619050355i}}] \
         [theTestPtClose {asin([1+i, 1-i])} \
	{[0.6662394324925155 + 1.0612750619050355i, 0.6662394324925153 - 1.0612750619050355i]}]
} {1 1 1}

test Function-19.7 {Test various function calls} {
    list [theTest {atan(1+i)}] \
         [theTest {atan({1+i, 1-i})}] \
         [theTest {atan([1+i, 1-i])}]
} {{1.0172219678978514 + 0.402359478108525i} {{1.0172219678978514 + 0.402359478108525i, 1.0172219678978514 - 0.4023594781085251i}} {[1.0172219678978514 + 0.402359478108525i, 1.0172219678978514 - 0.4023594781085251i]}}

 test Function-19.8 {Test various function calls} {
    list [theTest {atanh(1+i)}] \
         [theTest {atanh({1+i, 1-i})}] \
         [theTest {atanh([1+i, 1-i])}]
 } {{0.4023594781085251 + 1.0172219678978514i} {{0.4023594781085251 + 1.0172219678978514i, 0.4023594781085251 - 1.0172219678978514i}} {[0.4023594781085251 + 1.0172219678978514i, 0.4023594781085251 - 1.0172219678978514i]}}

test Function-19.9 {Test various function calls} {
    list [theTest {conjugate(1+i)}] \
         [theTest {conjugate({1+i, 1-i})}] \
         [theTest {conjugate([1+i, 1-i])}]
 } {{1.0 - 1.0i} {{1.0 - 1.0i, 1.0 + 1.0i}} {[1.0 - 1.0i, 1.0 + 1.0i]}}

####################################################################

test Function-23.2 {Test various function calls} {
    list [theTest {cot(1+i)}] \
         [theTest {cot({1+i, 1-i})}] \
         [theTest {cot([1+i, 1-i])}]
} {{0.21762156185440262 - 0.8680141428959249i} {{0.21762156185440262 - 0.8680141428959249i, 0.21762156185440262 + 0.8680141428959249i}} {[0.21762156185440262 - 0.8680141428959249i, 0.21762156185440262 + 0.8680141428959249i]}}

#  test Function-23.4 {Test various function calls} {
#     list [theTest {coth(1+i)}] \
#          [theTest {coth({1+i, 1-i})}] \
#          [theTest {coth([1+i, 1-i])}]
#  } {} {Not Implemented}

test Function-23.5 {Test various function calls} {
    list [theTest {csc(1+i)}] \
         [theTest {csc({1+i, 1-i})}] \
         [theTest {csc([1+i, 1-i])}]
} {{0.6215180171704283 - 0.3039310016284264i} {{0.6215180171704283 - 0.3039310016284264i, 0.6215180171704283 + 0.3039310016284264i}} {[0.6215180171704283 - 0.3039310016284264i, 0.6215180171704283 + 0.3039310016284264i]}}

 # test Function-23.6 {Test various function calls} {
#     list [theTest {csch(1+i)}] \
#          [theTest {csch({1+i, 1-i})}] \
#          [theTest {csch([1+i, 1-i])}]
#  } {} {We don't have this method}


test Function-23.6.5 {Test various function calls: createArray} {
    # FIXME: what about UnsignedByteArray and FixMatrixToken
    list \
	[theTest {createArray([true,false;true,true])}] \
	[theTest {createArray([1,2;3,4])}] \
	[theTest {createArray([1L,2L;3L,4L])}] \
	[theTest {createArray([1.0,2.0;3.0,4.0])}] \
	[theTest {createArray([1.0 + 0i, 2.0 + 1i; 3.0 - 1i, 4.0 + 4i])}]
} {{{true, false, true, true}} {{1, 2, 3, 4}} {{1L, 2L, 3L, 4L}} {{1.0, 2.0, 3.0, 4.0}} {{1.0 + 0.0i, 2.0 + 1.0i, 3.0 - 1.0i, 4.0 + 4.0i}}}


test Function-23.6.6.1 {Test various function calls: createMatrix} {
    # FIXME: what about UnsignedByteArray and FixMatrixToken
    list "[theTest {createMatrix({true,false,true,true,false,false}, 2, 3)}]\n \
	[theTest {createMatrix({1,2,3,4,5,6}, 2, 3)}]\n \
	[theTest {createMatrix({1L,2L,3L,4L,5L,6L}, 2, 3)}]\n \
	[theTest {createMatrix({1.0,2.0,3.0,4.0,5.0,6.0}, 2, 3)}]\n \
	[theTest {createMatrix({1.0 + 0i, 2.0 + 1i, 3.0 - 1i, 4.0 + 4i, 5.0 - 5i, 6.0 - 6.0i}, 2, 3)}]\n \
	[theTest {createMatrix({1ub,2,3.5,4,5,6}, 2, 3)}]"

} {{[true, false, true; true, false, false]
  [1, 2, 3; 4, 5, 6]
  [1L, 2L, 3L; 4L, 5L, 6L]
  [1.0, 2.0, 3.0; 4.0, 5.0, 6.0]
  [1.0 + 0.0i, 2.0 + 1.0i, 3.0 - 1.0i; 4.0 + 4.0i, 5.0 - 5.0i, 6.0 - 6.0i]
  [1.0, 2.0, 3.5; 4.0, 5.0, 6.0]}}

test Function-23.6.6.2 {Test various function calls: createMatrix with an array that is not big enough} {
    catch {theTest {createMatrix({1L,2L,3L,4L}, 2, 3)}} errMsg
    list $errMsg	
} {{ptolemy.kernel.util.IllegalActionException: Error invoking function public static ptolemy.data.MatrixToken ptolemy.data.MatrixToken.createMatrix(ptolemy.data.Token[],int,int) throws ptolemy.kernel.util.IllegalActionException

Because:
LongMatrixToken: The specified array is not of the correct length}}


test Function-23.6.7.1 {Test various function calls: createSequence} {
    # FIXME: what about UnsignedByteArray and FixMatrixToken
    list "[theTest {createSequence(false, false, 5)}]\n \
	[theTest {createSequence(false, true, 5)}]\n \
	[theTest {createSequence(true, false, 5)}]\n \
	[theTest {createSequence(true, true, 5)}]\n \
	[theTest {createSequence(-1, 1, 5)}]\n \
	[theTest {createSequence(-1L, 1L, 5)}]\n \
	[theTest {createSequence(-1.0, 1.0, 5)}]\n \
	[theTest {createSequence(-1.0 - 1i, 1.0 - 1i, 5)}]"
} {{{false, false, false, false, false}
  {false, true, true, true, true}
  {true, true, true, true, true}
  {true, true, true, true, true}
  {-1, 0, 1, 2, 3}
  {-1L, 0L, 1L, 2L, 3L}
  {-1.0, 0.0, 1.0, 2.0, 3.0}
  {-1.0 - 1.0i, 0.0 - 2.0i, 1.0 - 3.0i, 2.0 - 4.0i, 3.0 - 5.0i}}}

test Function-23.7 {Test various function calls} {
    list [theTest {exp(1+i)}] \
         [theTest {exp({1+i, 1-i})}] \
         [theTest {exp([1+i, 1-i])}]
} {{1.4686939399158854 + 2.2873552871788427i} {{1.4686939399158854 + 2.2873552871788427i, 1.4686939399158854 - 2.2873552871788427i}} {[1.4686939399158854 + 2.2873552871788427i, 1.4686939399158854 - 2.2873552871788427i]}}

test Function-23.8 {Test various function calls} {
    list [theTest {isInfinite(1+i)}] \
         [theTest {isInfinite({1+i, 1-i})}] \
         [theTest {isInfinite([1+i, 1-i])}]
} {false {{false, false}} {[false, false]}}

test Function-23.9 {Test various function calls} {
    list [theTest {isNaN(1+i)}] \
         [theTest {isNaN({1+i, 1-i})}] \
         [theTest {isNaN([1+i, 1-i])}]
} {false {{false, false}} {[false, false]}}

test Function-23.10 {Test various function calls} {
    list [theTest {log(1+i)}] \
         [theTest {log({1+i, 1-i})}] \
         [theTest {log([1+i, 1-i])}]
} {{0.3465735902799727 + 0.7853981633974483i} {{0.3465735902799727 + 0.7853981633974483i, 0.3465735902799727 - 0.7853981633974483i}} {[0.3465735902799727 + 0.7853981633974483i, 0.3465735902799727 - 0.7853981633974483i]}}

test Function-23.11 {Test various function calls} {
    list [theTest {magnitudeSquared(1+i)}] \
         [theTest {magnitudeSquared({1+i, 1-i})}] \
         [theTest {magnitudeSquared([1+i, 1-i])}]
} {2.0 {{2.0, 2.0}} {[2.0, 2.0]}}

test Function-23.12 {Test various function calls} {
    list [theTest {pow(1+i, 2+i)}] \
         [theTest {pow({1+i, 1-i}, {2+i, 2-i})}] \
         [theTest {pow([1+i, 1-i], [1+i, 1-i])}]
} {{-0.30974350492849356 + 0.8576580125887358i} {{-0.30974350492849356 + 0.8576580125887358i, -0.30974350492849356 - 0.8576580125887358i}} {[0.2739572538301211 + 0.5837007587586147i, 0.2739572538301211 - 0.5837007587586147i]}}

test Function-23.13 {Test various function calls} {
    list [theTest {reciprocal(1+i)}] \
         [theTest {reciprocal({1+i, 1-i})}] \
         [theTest {reciprocal([1+i, 1-i])}]
} {{0.5 - 0.5i} {{0.5 - 0.5i, 0.5 + 0.5i}} {[0.5 - 0.5i, 0.5 + 0.5i]}}

test Function-23.14 {Test various function calls} {
    list [theTest {roots(1+i, 4)}] \
         [theTest {roots({1+i, 1-i}, 4)}]
} {{{1.0695539323639858 + 0.21274750472674303i, -0.21274750472674295 + 1.0695539323639858i, -1.0695539323639858 - 0.21274750472674314i, 0.2127475047267431 - 1.0695539323639858i}} {{{1.0695539323639858 + 0.21274750472674303i, -0.21274750472674295 + 1.0695539323639858i, -1.0695539323639858 - 0.21274750472674314i, 0.2127475047267431 - 1.0695539323639858i}, {1.0695539323639858 - 0.21274750472674303i, 0.21274750472674311 + 1.0695539323639858i, -1.0695539323639858 + 0.21274750472674342i, -0.21274750472674347 - 1.0695539323639858i}}}}

test Function-23.15 {Test various function calls} {
    list [theTest {sec(1+i)}] \
         [theTest {sec({1+i, 1-i})}] \
         [theTest {sec([1+i, 1-i])}]
} {{0.4983370305551867 + 0.591083841721045i} {{0.4983370305551867 + 0.591083841721045i, 0.4983370305551867 - 0.591083841721045i}} {[0.4983370305551867 + 0.591083841721045i, 0.4983370305551867 - 0.591083841721045i]}}

#  test Function-19.25 {Test various function calls} {
#     list [theTest {sech(1+i)}] \
#          [theTest {sech({1+i, 1-i})}] \
#          [theTest {sech([1+i, 1-i])}]
#  } {} {Not implemented}
 
test Function-23.18 {Test various function calls} {
    list [theTestPtClose {sqrt(1+i)} \
	{1.09868411346781 + 0.45508986056222733i}] \
	 [theTestPtClose {sqrt({1+i, 1-i})} \
	{{1.09868411346781 + 0.45508986056222733i, 1.09868411346781 - 0.45508986056222733i}}] \
         [theTestPtClose {sqrt([1+i, 1-i])} \
	{[1.09868411346781 + 0.45508986056222733i, 1.09868411346781 - 0.45508986056222733i]}]
} {1 1 1}

 test Function-23.19 {Test various function calls: sum is defined in DoubleArrayMath} {
    list [theTest {sum({0.1,0.2,0.3})}]
 } {0.6}

 test Function-23.20 {Test various function calls: sumOfSquares is defined in DoubleArrayMath} {
    list [theTest {sumOfSquares({1.0,2.0,3.0})}]
 } {14.0}
 

####################################################################
