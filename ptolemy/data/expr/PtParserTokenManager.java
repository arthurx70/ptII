/* Generated By:JJTree&JavaCC: Do not edit this line. PtParserTokenManager.java */
/* 
 Copyright (c) 1998-2001 The Regents of the University of California.
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

@ProposedRating Yellow (nsmyth@eecs.berkeley.edu)
@AcceptedRating Yellow (yuhong@eecs.berkeley.edu)

Created : May 1998

*/

package ptolemy.data.expr;
import ptolemy.kernel.*;
import ptolemy.kernel.util.*;
import ptolemy.data.*;
import ptolemy.math.Complex;
import java.util.Hashtable;
import java.util.LinkedList;
import java.io.*;

public class PtParserTokenManager implements PtParserConstants
{
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x40000000000L) != 0L)
            return 80;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         jjmatchedKind = 24;
         return jjMoveStringLiteralDfa1_0(0x100000L);
      case 35:
         return jjStopAtPos(0, 28);
      case 37:
         return jjStopAtPos(0, 14);
      case 38:
         jjmatchedKind = 26;
         return jjMoveStringLiteralDfa1_0(0x400000L);
      case 40:
         return jjStopAtPos(0, 43);
      case 41:
         return jjStopAtPos(0, 45);
      case 42:
         return jjStopAtPos(0, 12);
      case 43:
         return jjStopAtPos(0, 10);
      case 44:
         return jjStopAtPos(0, 44);
      case 45:
         return jjStopAtPos(0, 11);
      case 46:
         return jjStartNfaWithStates_0(0, 42, 80);
      case 47:
         jjmatchedKind = 13;
         return jjMoveStringLiteralDfa1_0(0x6L);
      case 58:
         return jjStopAtPos(0, 41);
      case 59:
         return jjStopAtPos(0, 49);
      case 60:
         jjmatchedKind = 17;
         return jjMoveStringLiteralDfa1_0(0x80000L);
      case 61:
         jjmatchedKind = 48;
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 62:
         jjmatchedKind = 16;
         return jjMoveStringLiteralDfa1_0(0x40000L);
      case 63:
         return jjStopAtPos(0, 40);
      case 91:
         return jjStopAtPos(0, 46);
      case 93:
         return jjStopAtPos(0, 50);
      case 94:
         return jjStopAtPos(0, 15);
      case 123:
         return jjStopAtPos(0, 47);
      case 124:
         jjmatchedKind = 27;
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 125:
         return jjStopAtPos(0, 51);
      case 126:
         return jjStopAtPos(0, 25);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private final int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x400000L) != 0L)
            return jjStopAtPos(1, 22);
         break;
      case 42:
         if ((active0 & 0x4L) != 0L)
            return jjStopAtPos(1, 2);
         break;
      case 47:
         if ((active0 & 0x2L) != 0L)
            return jjStopAtPos(1, 1);
         break;
      case 61:
         if ((active0 & 0x40000L) != 0L)
            return jjStopAtPos(1, 18);
         else if ((active0 & 0x80000L) != 0L)
            return jjStopAtPos(1, 19);
         else if ((active0 & 0x100000L) != 0L)
            return jjStopAtPos(1, 20);
         else if ((active0 & 0x200000L) != 0L)
            return jjStopAtPos(1, 21);
         break;
      case 124:
         if ((active0 & 0x800000L) != 0L)
            return jjStopAtPos(1, 23);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 80;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 80:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 35)
                        kind = 35;
                     jjCheckNAddStates(0, 2);
                  }
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 34)
                        kind = 34;
                     jjCheckNAddStates(3, 5);
                  }
                  break;
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddStates(6, 19);
                  else if (curChar == 46)
                     jjCheckNAddTwoStates(41, 46);
                  else if (curChar == 34)
                     jjCheckNAddStates(20, 22);
                  else if (curChar == 39)
                     jjCheckNAddStates(23, 25);
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddTwoStates(1, 2);
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddStates(26, 28);
                  }
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddTwoStates(1, 2);
                  break;
               case 12:
                  if ((0x3ff001800000000L & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 13:
                  if (curChar == 39)
                     jjCheckNAddStates(23, 25);
                  break;
               case 14:
                  if (curChar == 34)
                     jjCheckNAddStates(23, 25);
                  break;
               case 16:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddStates(23, 25);
                  break;
               case 17:
                  if (curChar == 39 && kind > 39)
                     kind = 39;
                  break;
               case 18:
               case 19:
                  if (curChar == 34)
                     jjCheckNAddStates(20, 22);
                  break;
               case 21:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddStates(20, 22);
                  break;
               case 22:
                  if (curChar == 34 && kind > 39)
                     kind = 39;
                  break;
               case 36:
                  if (curChar != 48)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddStates(26, 28);
                  break;
               case 38:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddTwoStates(38, 2);
                  break;
               case 39:
                  if ((0xff000000000000L & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddTwoStates(39, 2);
                  break;
               case 40:
                  if (curChar == 46)
                     jjCheckNAddTwoStates(41, 46);
                  break;
               case 41:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAddStates(3, 5);
                  break;
               case 43:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(44);
                  break;
               case 44:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAddTwoStates(44, 45);
                  break;
               case 46:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddStates(0, 2);
                  break;
               case 48:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(49);
                  break;
               case 49:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddTwoStates(49, 50);
                  break;
               case 51:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddStates(6, 19);
                  break;
               case 52:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(52, 53);
                  break;
               case 53:
                  if (curChar == 46)
                     jjCheckNAdd(54);
                  break;
               case 54:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAddStates(29, 31);
                  break;
               case 56:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(57);
                  break;
               case 57:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAddTwoStates(57, 45);
                  break;
               case 58:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(58, 59);
                  break;
               case 60:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(61);
                  break;
               case 61:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAddTwoStates(61, 45);
                  break;
               case 62:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddStates(32, 34);
                  break;
               case 64:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(65);
                  break;
               case 65:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(65, 45);
                  break;
               case 66:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(66, 67);
                  break;
               case 67:
                  if (curChar == 46)
                     jjCheckNAdd(68);
                  break;
               case 68:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddStates(35, 37);
                  break;
               case 70:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(71);
                  break;
               case 71:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddTwoStates(71, 50);
                  break;
               case 72:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(72, 73);
                  break;
               case 74:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(75);
                  break;
               case 75:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddTwoStates(75, 50);
                  break;
               case 76:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddStates(38, 40);
                  break;
               case 78:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(79);
                  break;
               case 79:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(79, 50);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 37)
                        kind = 37;
                     jjCheckNAddTwoStates(11, 12);
                  }
                  if (curChar == 70)
                     jjAddStates(41, 42);
                  else if (curChar == 84)
                     jjAddStates(43, 44);
                  else if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 9;
                  else if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 2:
                  if ((0x100000001000L & l) != 0L && kind > 29)
                     kind = 29;
                  break;
               case 3:
                  if (curChar == 101 && kind > 36)
                     kind = 36;
                  break;
               case 4:
               case 27:
                  if (curChar == 117)
                     jjCheckNAdd(3);
                  break;
               case 5:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 6:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 7:
               case 33:
                  if (curChar == 115)
                     jjCheckNAdd(3);
                  break;
               case 8:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 9:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 11:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAddTwoStates(11, 12);
                  break;
               case 12:
                  if ((0x7fffffe87ffffffL & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAdd(12);
                  break;
               case 15:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 16:
                  jjAddStates(23, 25);
                  break;
               case 20:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 21:
                  jjAddStates(20, 22);
                  break;
               case 23:
                  if (curChar == 84)
                     jjAddStates(43, 44);
                  break;
               case 24:
                  if (curChar == 69 && kind > 36)
                     kind = 36;
                  break;
               case 25:
                  if (curChar == 85)
                     jjCheckNAdd(24);
                  break;
               case 26:
                  if (curChar == 82)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 28:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 29:
                  if (curChar == 70)
                     jjAddStates(41, 42);
                  break;
               case 30:
                  if (curChar == 83)
                     jjCheckNAdd(24);
                  break;
               case 31:
                  if (curChar == 76)
                     jjstateSet[jjnewStateCnt++] = 30;
                  break;
               case 32:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 34:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 35:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 37:
                  if ((0x100000001000000L & l) != 0L)
                     jjCheckNAdd(38);
                  break;
               case 38:
                  if ((0x7e0000007eL & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddTwoStates(38, 2);
                  break;
               case 42:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(45, 46);
                  break;
               case 45:
                  if ((0x5000000050L & l) != 0L && kind > 34)
                     kind = 34;
                  break;
               case 47:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(47, 48);
                  break;
               case 50:
                  if ((0x60000000000L & l) != 0L && kind > 35)
                     kind = 35;
                  break;
               case 55:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(49, 50);
                  break;
               case 59:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(51, 52);
                  break;
               case 63:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(53, 54);
                  break;
               case 69:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(55, 56);
                  break;
               case 73:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(57, 58);
                  break;
               case 77:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(59, 60);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 16:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(23, 25);
                  break;
               case 21:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(20, 22);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 80 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjMoveStringLiteralDfa0_2()
{
   switch(curChar)
   {
      case 42:
         return jjMoveStringLiteralDfa1_2(0x10L);
      default :
         return 1;
   }
}
private final int jjMoveStringLiteralDfa1_2(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 47:
         if ((active0 & 0x10L) != 0L)
            return jjStopAtPos(1, 4);
         break;
      default :
         return 2;
   }
   return 2;
}
private final int jjMoveStringLiteralDfa0_1()
{
   return jjMoveNfa_1(0, 0);
}
private final int jjMoveNfa_1(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 3;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x2400L & l) != 0L)
                  {
                     if (kind > 3)
                        kind = 3;
                  }
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 1:
                  if (curChar == 10 && kind > 3)
                     kind = 3;
                  break;
               case 2:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjMoveStringLiteralDfa0_3()
{
   return 1;
}
static final int[] jjnextStates = {
   46, 47, 50, 41, 42, 45, 52, 53, 58, 59, 62, 63, 45, 66, 67, 72, 
   73, 76, 77, 50, 20, 21, 22, 15, 16, 17, 37, 39, 2, 54, 55, 45, 
   62, 63, 45, 68, 69, 50, 76, 77, 50, 32, 35, 26, 28, 43, 44, 48, 
   49, 56, 57, 60, 61, 64, 65, 70, 71, 74, 75, 78, 79, 
};
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, "\53", "\55", "\52", 
"\57", "\45", "\136", "\76", "\74", "\76\75", "\74\75", "\41\75", "\75\75", "\46\46", 
"\174\174", "\41", "\176", "\46", "\174", "\43", null, null, null, null, null, null, null, 
null, null, null, null, "\77", "\72", "\56", "\50", "\54", "\51", "\133", "\173", 
"\75", "\73", "\135", "\175", };
public static final String[] lexStateNames = {
   "DEFAULT", 
   "IN_SINGLE_LINE_COMMENT", 
   "IN_MULTI_LINE_COMMENT", 
   "IN_FORMAL_COMMENT", 
};
public static final int[] jjnewLexState = {
   -1, 1, 2, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, 
};
static final long[] jjtoToken = {
   0xfffbc3ffffc01L, 
};
static final long[] jjtoSkip = {
   0x3d8L, 
};
static final long[] jjtoSpecial = {
   0x18L, 
};
static final long[] jjtoMore = {
   0x26L, 
};
private ASCII_CharStream input_stream;
private final int[] jjrounds = new int[80];
private final int[] jjstateSet = new int[160];
StringBuffer image;
int jjimageLen;
int lengthOfMatch;
protected char curChar;
public PtParserTokenManager(ASCII_CharStream stream)
{
   if (ASCII_CharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}
public PtParserTokenManager(ASCII_CharStream stream, int lexState)
{
   this(stream);
   SwitchTo(lexState);
}
public void ReInit(ASCII_CharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 80; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
public void ReInit(ASCII_CharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
public void SwitchTo(int lexState)
{
   if (lexState >= 4 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

private final Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

public final Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }
   image = null;
   jjimageLen = 0;

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         try { input_stream.backup(0);
            while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
               curChar = input_stream.BeginToken();
         }
         catch (java.io.IOException e1) { continue EOFLoop; }
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         if (jjmatchedPos == 0 && jjmatchedKind > 5)
         {
            jjmatchedKind = 5;
         }
         break;
       case 2:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_2();
         if (jjmatchedPos == 0 && jjmatchedKind > 5)
         {
            jjmatchedKind = 5;
         }
         break;
       case 3:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_3();
         if (jjmatchedPos == 0 && jjmatchedKind > 5)
         {
            jjmatchedKind = 5;
         }
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
              SkipLexicalActions(matchedToken);
           }
           else 
              SkipLexicalActions(null);
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
        jjimageLen += jjmatchedPos + 1;
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

final void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
}
