//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short STATIC=279;
public final static short INSTANCEOF=280;
public final static short LESS_EQUAL=281;
public final static short GREATER_EQUAL=282;
public final static short EQUAL=283;
public final static short NOT_EQUAL=284;
public final static short REPEAT=285;
public final static short UNTIL=286;
public final static short CALLAT=287;
public final static short SHAPE=288;
public final static short DOLLAR=289;
public final static short UMINUS=290;
public final static short EMPTY=291;
public final static short COMPLEX=292;
public final static short SUPER=293;
public final static short CASE=294;
public final static short DEFAULT=295;
public final static short DO=296;
public final static short OD=297;
public final static short DOP=298;
public final static short DCOPY=299;
public final static short SCOPY=300;
public final static short PRINTCOMP=301;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   14,   14,
   14,   26,   26,   23,   23,   25,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   28,
   28,   27,   27,   32,   32,   29,   33,   33,   35,   34,
   18,   36,   36,   37,   16,   17,   22,   15,   38,   38,
   19,   19,   20,   30,   31,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    1,    2,    2,    2,    2,    1,    3,    1,
    0,    2,    0,    2,    4,    5,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    2,    2,    2,
    2,    3,    3,    1,    1,    4,    5,    6,    5,    1,
    1,    1,    0,    3,    1,    8,    2,    0,    4,    4,
    4,    3,    0,    3,    5,    9,    1,    6,    2,    0,
    2,    1,    4,    4,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,    0,    0,   13,   10,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   81,   74,    0,    0,
    0,    0,   97,    0,    0,    0,    0,   80,    0,    0,
    0,    0,   25,    0,    0,    0,   75,    0,   93,    0,
    0,    0,   28,   38,   26,    0,   30,   31,   32,   33,
    0,    0,    0,    0,    0,    0,    0,    0,   49,   50,
   51,   52,    0,    0,    0,   47,    0,   48,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   29,   34,   35,   36,
   37,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   42,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   72,   73,    0,    0,   66,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   76,    0,    0,  103,    0,
    0,    0,    0,   91,   92,  104,  105,  106,   45,    0,
    0,   95,    0,    0,   77,    0,    0,   79,   88,   94,
   46,    0,    0,   98,   78,    0,    0,   99,    0,    0,
    0,   87,    0,    0,    0,   86,   96,    0,    0,   90,
   89,
};
final static short yydgoto[] = {                          2,
    3,    4,   73,   21,   34,    8,   11,   23,   35,   36,
   74,   46,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,   96,   86,   98,   88,  190,   89,   90,   91,
   92,  145,  206,  211,  212,  113,  153,  204,
};
final static short yysindex[] = {                      -242,
 -251,    0, -242,    0, -219,    0, -223,  -53,    0,    0,
  166,    0,    0,    0,    0, -200,  226,    0,    0,    0,
   23,  -87,    0,    0,  -85,    0,   40,   -9,   48,  226,
    0,  226,    0,  -83,   52,   50,   68,    0,  -24,  226,
  -24,    0,    0,    0,    0,   12,    0,    0,   73,   82,
   91,  175,    0,  444,   93,   94,   96,    0,  100,  175,
  175,   99,    0,  175,  175,  175,    0,  102,    0,  106,
  109,  112,    0,    0,    0,   88,    0,    0,    0,    0,
   95,   98,  101,  105,   92,  875,    0, -118,    0,    0,
    0,    0,  175,  175,  175,    0,  875,    0,  121,   76,
  175,  129,  137,  175,  -29,  -29,  -97,   59,  875,  875,
  875,  175,  175,  175,  175,  175,    0,    0,    0,    0,
    0,  175,  175,  175,  175,  175,  175,  175,  175,  175,
  175,  175,  175,  175,  175,    0,  175,  140,  559,  124,
  586,  143,  142,  875,  -21,    0,    0,  597,  144,    0,
  619,  649, -249,  686,  785,  -10,  875,   83,  126,   32,
   32,   21,   21,   -5,   -5,  -29,  -29,  -29,   32,   32,
  845,  175,   58,  175,   58,    0,  897,  175,    0,  -82,
  175,   75,   58,    0,    0,    0,    0,    0,    0,  156,
  157,    0,  934,  -66,    0,  875,  162,    0,    0,    0,
    0,  175,   58,    0,    0, -240,  163,    0,  147,  148,
   87,    0,   58,  175,  175,    0,    0,  955,  981,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  207,    0,  139,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  164,    0,    0,  180,
    0,  180,    0,    0,    0,  181,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,    0,  -60,
  -60,  -60,    0,  -60,  -60,  -60,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  992,    0,  -32,    0,    0,    0,
    0,    0,  -60,  -58,  -60,    0,  171,    0,    0,    0,
  -60,    0,    0,  -60,  461,  488,    0,    0,   -8,   97,
  151,  -60,  -60,  -60,  -60,  -60,    0,    0,    0,    0,
    0,  -60,  -60,  -60,  -60,  -60,  -60,  -60,  -60,  -60,
  -60,  -60,  -60,  -60,  -60,    0,  -60,  435,    0,    0,
    0,    0,  -60,   66,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -12,  -20,    2,  107,
  118,  155,  594, 1015, 1037,  497,  524,  533,  631,  914,
    0,  -23,  -58,  -60,  -58,    0,    0,  -60,    0,    0,
  -60,    0,  -58,    0,    0,    0,    0,    0,    0,    0,
  247,    0,    0,  -33,    0,   74,    0,    0,    0,    0,
    0,   -1,  -58,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -58,  -60,  -60,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  286,  279,   84,   54,    0,    0,    0,  261,    0,
   15,    0,  -86,  -91,    0,    0,    0,    0,    0,    0,
    0,    0,  584, 1242,  621,    0,    0,   89,    0,    0,
    0, -100,    0,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=1457;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        100,
   41,  102,  140,   28,   48,   28,  100,   28,   40,   48,
   48,  100,   48,   48,   48,  156,  136,   83,    1,  179,
   64,   47,  178,   64,    5,  100,   40,   48,   39,   48,
  188,  133,   69,  178,   58,   69,  131,   64,   64,   41,
  136,  132,   65,    7,   61,   65,   39,  184,  185,   69,
   69,   62,    9,   43,  209,   45,   60,  133,   48,   65,
   65,  137,  131,  129,   22,  130,  136,  132,  133,   10,
   25,  191,   64,  131,  129,   24,  130,  136,  132,   30,
  135,   26,  134,   31,   69,  137,  192,   32,  194,  100,
   61,  100,   39,   40,   65,  133,  200,   62,   42,  150,
  131,  129,   60,  130,  136,  132,   85,  100,   41,   85,
  207,  137,   93,   33,   84,   33,  208,   84,  135,  133,
  134,   94,  137,   44,  131,  129,  217,  130,  136,  132,
   95,   61,  101,  102,   42,  103,   63,   71,   62,  104,
   71,  112,  135,   60,  134,  114,  117,   62,  115,  137,
   62,  116,  122,  118,   71,   71,  119,  138,   63,  120,
  142,   63,  133,  121,   62,   62,  143,  131,  129,  146,
  130,  136,  132,  137,   61,   63,   63,  147,  149,  172,
   42,   62,  174,  176,  181,  135,   60,  134,   27,   71,
   29,   70,   38,  197,   70,   58,  201,  199,   58,   62,
  178,  203,  205,  213,  214,  215,    1,   61,   70,   70,
   63,  216,   58,   58,   62,   43,  137,   43,   43,   60,
   20,   19,    5,  100,  100,  100,  100,  100,  100,  101,
  100,  100,  100,  100,   31,  100,  100,  100,  100,  100,
  100,  100,  100,   70,   48,   48,  100,   58,   48,   48,
   48,   48,   43,  100,  100,  100,   64,   64,  100,  100,
  100,   15,  100,  100,  100,  100,  100,  100,   12,   13,
   14,   15,   16,   47,   43,   48,   49,   50,   51,   65,
   52,   53,   54,   55,   56,   57,   58,   82,    6,   20,
   18,   59,   37,    0,  210,    0,    0,    0,   64,   65,
   66,  125,  126,   19,   67,   68,    0,   69,    0,    0,
   70,   71,   72,    0,   12,   13,   14,   15,   16,   47,
    0,   48,   49,   50,   51,    0,   52,   53,   54,   55,
   56,   57,   58,    0,    0,  123,  124,   59,    0,  125,
  126,  127,  128,    0,   64,   65,   66,    0,    0,   19,
   67,   68,    0,   69,    0,    0,   70,   71,   72,  107,
   47,    0,   48,  125,  126,  127,  128,    0,    0,   54,
    0,   56,   57,   58,    0,    0,    0,    0,   59,    0,
    0,    0,    0,   62,   62,   64,   65,   66,    0,   62,
   62,   67,   68,    0,   63,   63,    0,   70,   71,    0,
   63,   63,  123,   47,    0,   48,  125,  126,  127,  128,
    0,    0,   54,    0,   56,   57,   58,    0,    0,    0,
    0,   59,   12,   13,   14,   15,   16,    0,   64,   65,
   66,   58,   58,    0,   67,   68,   47,    0,   48,    0,
   70,   71,    0,    0,   17,   54,    0,   56,   57,   58,
    0,    0,    0,    0,   59,    0,    0,   19,    0,    0,
    0,   64,   65,   66,    0,    0,    0,   67,   68,    0,
    0,   44,    0,   70,   71,   44,   44,   44,   44,   44,
   44,   44,   12,   13,   14,   15,   16,    0,    0,    0,
    0,    0,   44,   44,   44,   44,   44,   67,    0,    0,
    0,   67,   67,   67,   67,   67,    0,   67,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   19,   67,   67,
   67,    0,   67,    0,   68,   44,    0,   44,   68,   68,
   68,   68,   68,   55,   68,    0,    0,   55,   55,   55,
   55,   55,    0,   55,    0,   68,   68,   68,    0,   68,
    0,    0,    0,   67,   55,   55,   55,    0,   55,    0,
   56,    0,    0,    0,   56,   56,   56,   56,   56,   57,
   56,    0,    0,   57,   57,   57,   57,   57,    0,   57,
   68,   56,   56,   56,    0,   56,    0,    0,    0,   55,
   57,   57,   57,    0,   57,  133,    0,    0,    0,  173,
  131,  129,    0,  130,  136,  132,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   56,    0,  135,    0,
  134,    0,  133,    0,    0,   57,  175,  131,  129,   85,
  130,  136,  132,  133,   59,    0,    0,   59,  131,  129,
  180,  130,  136,  132,    0,  135,    0,  134,    0,  137,
    0,   59,   59,    0,    0,  133,  135,    0,  134,  182,
  131,  129,    0,  130,  136,  132,   87,    0,    0,    0,
    0,   61,    0,    0,   61,    0,  137,   85,  135,    0,
  134,    0,    0,    0,    0,  133,   59,  137,   61,   61,
  131,  129,    0,  130,  136,  132,    0,    0,    0,    0,
   12,   13,   14,   15,   16,    0,  183,    0,  135,  137,
  134,   44,   44,    0,   87,   44,   44,   44,   44,   99,
    0,    0,  133,   61,    0,    0,  186,  131,  129,    0,
  130,  136,  132,    0,    0,   19,    0,   67,   67,  137,
    0,   67,   67,   67,   67,  135,    0,  134,    0,    0,
    0,    0,    0,    0,    0,    0,   85,    0,   85,    0,
    0,    0,    0,    0,   68,   68,   85,    0,   68,   68,
   68,   68,    0,   55,   55,    0,  137,   55,   55,   55,
   55,    0,    0,    0,    0,   85,   85,    0,    0,    0,
    0,    0,    0,   87,    0,   87,   85,    0,    0,    0,
   56,   56,    0,   87,   56,   56,   56,   56,    0,   57,
   57,    0,    0,   57,   57,   57,   57,    0,    0,    0,
    0,  133,   87,   87,    0,  187,  131,  129,    0,  130,
  136,  132,    0,   87,    0,  123,  124,    0,    0,  125,
  126,  127,  128,    0,  135,    0,  134,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  123,  124,    0,    0,  125,  126,  127,  128,
   59,   59,    0,  123,  124,  137,    0,  125,  126,  127,
  128,  133,    0,    0,    0,    0,  131,  129,    0,  130,
  136,  132,    0,    0,    0,  123,  124,    0,    0,  125,
  126,  127,  128,    0,  135,    0,  134,   61,   61,    0,
    0,  133,    0,   61,   61,    0,  131,  129,    0,  130,
  136,  132,    0,    0,    0,  123,  124,    0,    0,  125,
  126,  127,  128,  133,  135,  137,  134,  189,  131,  129,
    0,  130,  136,  132,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   60,    0,  135,   60,  134,    0,
    0,    0,  123,  124,    0,  137,  125,  126,  127,  128,
  133,   60,   60,    0,    0,  131,  129,    0,  130,  136,
  132,    0,    0,    0,    0,    0,    0,  137,    0,  195,
    0,  133,  202,  135,    0,  134,  131,  129,    0,  130,
  136,  132,    0,    0,    0,    0,   60,    0,    0,    0,
    0,    0,    0,  220,  135,    0,  134,  133,    0,    0,
    0,    0,  131,  129,  137,  130,  136,  132,   47,    0,
    0,    0,    0,   47,   47,    0,   47,   47,   47,  221,
  135,    0,  134,    0,    0,  137,    0,    0,    0,    0,
    0,   47,    0,   47,    0,   53,    0,   53,   53,   53,
    0,  123,  124,    0,    0,  125,  126,  127,  128,    0,
    0,  137,   53,   53,   53,    0,   53,   54,    0,   54,
   54,   54,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   54,   54,   54,    0,   54,    0,
    0,    0,    0,    0,    0,    0,    0,   53,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  123,  124,    0,    0,  125,  126,  127,  128,   54,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  123,  124,    0,    0,  125,  126,  127,  128,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  123,  124,    0,    0,  125,  126,  127,
  128,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,   60,    0,    0,    0,    0,   60,   60,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  123,  124,    0,    0,  125,  126,  127,  128,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  123,  124,    0,    0,  125,  126,  127,  128,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  123,  124,    0,
    0,  125,  126,  127,  128,    0,    0,    0,   47,   47,
    0,    0,   47,   47,   47,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   53,   53,   97,    0,   53,   53,   53,   53,    0,
    0,  105,  106,  108,    0,  109,  110,  111,    0,    0,
    0,    0,    0,   54,   54,    0,    0,   54,   54,   54,
   54,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  139,    0,  141,    0,    0,    0,
    0,    0,  144,    0,    0,  148,    0,    0,    0,    0,
    0,    0,    0,  151,  152,  154,  155,  144,    0,    0,
    0,    0,    0,  157,  158,  159,  160,  161,  162,  163,
  164,  165,  166,  167,  168,  169,  170,    0,  171,    0,
    0,    0,    0,    0,  177,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  144,    0,  193,    0,    0,    0,  196,
    0,    0,  198,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  218,  219,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   94,   91,   37,   91,   40,   91,   41,   42,
   43,   45,   45,   46,   47,  116,   46,   41,  261,   41,
   41,  262,   44,   44,  276,   59,   59,   60,   41,   62,
   41,   37,   41,   44,  275,   44,   42,   58,   59,   41,
   46,   47,   41,  263,   33,   44,   59,  297,  298,   58,
   59,   40,  276,   39,  295,   41,   45,   37,   91,   58,
   59,   91,   42,   43,   11,   45,   46,   47,   37,  123,
   17,  172,   93,   42,   43,  276,   45,   46,   47,   40,
   60,   59,   62,   93,   93,   91,  173,   40,  175,  123,
   33,  125,   41,   44,   93,   37,  183,   40,  123,   41,
   42,   43,   45,   45,   46,   47,   41,   54,   41,   44,
  202,   91,   40,   30,   41,   32,  203,   44,   60,   37,
   62,   40,   91,   40,   42,   43,  213,   45,   46,   47,
   40,   33,   40,   40,  123,   40,  125,   41,   40,   40,
   44,   40,   60,   45,   62,   40,   59,   41,   40,   91,
   44,   40,   61,   59,   58,   59,   59,  276,   41,   59,
   40,   44,   37,   59,   58,   59,   91,   42,   43,   41,
   45,   46,   47,   91,   33,   58,   59,   41,  276,   40,
  123,   40,   59,   41,   41,   60,   45,   62,  276,   93,
  276,   41,  276,  276,   44,   41,   41,  123,   44,   93,
   44,  268,   41,   41,   58,   58,    0,   33,   58,   59,
   93,  125,   58,   59,   40,  276,   91,  276,  276,   45,
   41,   41,   59,  257,  258,  259,  260,  261,  262,   59,
  264,  265,  266,  267,   93,  269,  270,  271,  272,  273,
  274,  275,  276,   93,  277,  278,  280,   93,  281,  282,
  283,  284,  276,  287,  288,  289,  277,  278,  292,  293,
  294,  123,  296,  297,  298,  299,  300,  301,  257,  258,
  259,  260,  261,  262,  276,  264,  265,  266,  267,  278,
  269,  270,  271,  272,  273,  274,  275,   41,    3,   11,
  125,  280,   32,   -1,  206,   -1,   -1,   -1,  287,  288,
  289,  281,  282,  292,  293,  294,   -1,  296,   -1,   -1,
  299,  300,  301,   -1,  257,  258,  259,  260,  261,  262,
   -1,  264,  265,  266,  267,   -1,  269,  270,  271,  272,
  273,  274,  275,   -1,   -1,  277,  278,  280,   -1,  281,
  282,  283,  284,   -1,  287,  288,  289,   -1,   -1,  292,
  293,  294,   -1,  296,   -1,   -1,  299,  300,  301,  261,
  262,   -1,  264,  281,  282,  283,  284,   -1,   -1,  271,
   -1,  273,  274,  275,   -1,   -1,   -1,   -1,  280,   -1,
   -1,   -1,   -1,  277,  278,  287,  288,  289,   -1,  283,
  284,  293,  294,   -1,  277,  278,   -1,  299,  300,   -1,
  283,  284,  277,  262,   -1,  264,  281,  282,  283,  284,
   -1,   -1,  271,   -1,  273,  274,  275,   -1,   -1,   -1,
   -1,  280,  257,  258,  259,  260,  261,   -1,  287,  288,
  289,  277,  278,   -1,  293,  294,  262,   -1,  264,   -1,
  299,  300,   -1,   -1,  279,  271,   -1,  273,  274,  275,
   -1,   -1,   -1,   -1,  280,   -1,   -1,  292,   -1,   -1,
   -1,  287,  288,  289,   -1,   -1,   -1,  293,  294,   -1,
   -1,   37,   -1,  299,  300,   41,   42,   43,   44,   45,
   46,   47,  257,  258,  259,  260,  261,   -1,   -1,   -1,
   -1,   -1,   58,   59,   60,   61,   62,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  292,   58,   59,
   60,   -1,   62,   -1,   37,   91,   -1,   93,   41,   42,
   43,   44,   45,   37,   47,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   -1,   58,   59,   60,   -1,   62,
   -1,   -1,   -1,   93,   58,   59,   60,   -1,   62,   -1,
   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,
   47,   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,
   93,   58,   59,   60,   -1,   62,   -1,   -1,   -1,   93,
   58,   59,   60,   -1,   62,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   60,   -1,
   62,   -1,   37,   -1,   -1,   93,   41,   42,   43,   46,
   45,   46,   47,   37,   41,   -1,   -1,   44,   42,   43,
   44,   45,   46,   47,   -1,   60,   -1,   62,   -1,   91,
   -1,   58,   59,   -1,   -1,   37,   60,   -1,   62,   41,
   42,   43,   -1,   45,   46,   47,   46,   -1,   -1,   -1,
   -1,   41,   -1,   -1,   44,   -1,   91,   94,   60,   -1,
   62,   -1,   -1,   -1,   -1,   37,   93,   91,   58,   59,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,   -1,   58,   -1,   60,   91,
   62,  277,  278,   -1,   94,  281,  282,  283,  284,  276,
   -1,   -1,   37,   93,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,  292,   -1,  277,  278,   91,
   -1,  281,  282,  283,  284,   60,   -1,   62,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  173,   -1,  175,   -1,
   -1,   -1,   -1,   -1,  277,  278,  183,   -1,  281,  282,
  283,  284,   -1,  277,  278,   -1,   91,  281,  282,  283,
  284,   -1,   -1,   -1,   -1,  202,  203,   -1,   -1,   -1,
   -1,   -1,   -1,  173,   -1,  175,  213,   -1,   -1,   -1,
  277,  278,   -1,  183,  281,  282,  283,  284,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,   37,  202,  203,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,  213,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   60,   -1,   62,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
  277,  278,   -1,  277,  278,   91,   -1,  281,  282,  283,
  284,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   60,   -1,   62,  277,  278,   -1,
   -1,   37,   -1,  283,  284,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   37,   60,   91,   62,   93,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   41,   -1,   60,   44,   62,   -1,
   -1,   -1,  277,  278,   -1,   91,  281,  282,  283,  284,
   37,   58,   59,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,
   -1,   37,   59,   60,   -1,   62,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   37,   -1,   -1,
   -1,   -1,   42,   43,   91,   45,   46,   47,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   59,
   60,   -1,   62,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   -1,   60,   -1,   62,   -1,   41,   -1,   43,   44,   45,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   91,   58,   59,   60,   -1,   62,   41,   -1,   43,
   44,   45,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   60,   -1,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   93,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   52,   -1,  281,  282,  283,  284,   -1,
   -1,   60,   61,   62,   -1,   64,   65,   66,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   93,   -1,   95,   -1,   -1,   -1,
   -1,   -1,  101,   -1,   -1,  104,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  112,  113,  114,  115,  116,   -1,   -1,
   -1,   -1,   -1,  122,  123,  124,  125,  126,  127,  128,
  129,  130,  131,  132,  133,  134,  135,   -1,  137,   -1,
   -1,   -1,   -1,   -1,  143,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  172,   -1,  174,   -1,   -1,   -1,  178,
   -1,   -1,  181,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  214,  215,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=301;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","REPEAT",
"UNTIL","CALLAT","SHAPE","DOLLAR","UMINUS","EMPTY","COMPLEX","SUPER","CASE",
"DEFAULT","DO","OD","DOP","DCOPY","SCOPY","PRINTCOMP",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : COMPLEX",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : DoStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : PrintCompStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : CaseS",
"Expr : Dcopy",
"Expr : Scopy",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : CALLAT Expr",
"Expr : DOLLAR Expr",
"Expr : SHAPE Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : SUPER",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"CaseS : CASE '(' Expr ')' '{' CaseStmtList DefaultStmt '}'",
"CaseStmtList : CaseStmtList CaseStmt",
"CaseStmtList :",
"CaseStmt : Constant ':' Expr ';'",
"DefaultStmt : DEFAULT ':' Expr ';'",
"DoStmt : DO DoBranch DoSubStmt OD",
"DoBranch : DoBranch DoSubStmt DOP",
"DoBranch :",
"DoSubStmt : Expr ':' Stmt",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"Dcopy : DCOPY '(' Expr ')'",
"Scopy : SCOPY '(' Expr ')'",
"PrintCompStmt : PRINTCOMP '(' ExprList ')'",
};

//#line 521 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 727 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 55 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 61 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 65 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 75 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 81 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 85 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 89 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 93 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 97 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
                	}
break;
case 11:
//#line 101 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 105 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 111 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 117 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 121 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 127 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 131 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 135 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 143 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 150 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 154 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 161 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 165 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 171 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 177 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 181 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 188 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 193 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 39:
//#line 211 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 40:
//#line 215 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 41:
//#line 219 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 226 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 44:
//#line 232 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 45:
//#line 239 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 46:
//#line 245 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 47:
//#line 254 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 53:
//#line 263 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 267 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 271 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 275 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 279 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 283 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 287 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 291 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 295 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 299 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 303 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 307 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 311 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 315 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 67:
//#line 319 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 323 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 327 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.CALLAT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 331 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.DOLLAR, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 335 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.SHAPE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 339 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 73:
//#line 343 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 347 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 75:
//#line 351 "Parser.y"
{
                		yyval.expr = new Tree.SuperExpr(val_peek(0).loc);
                	}
break;
case 76:
//#line 355 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 77:
//#line 359 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 78:
//#line 363 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 79:
//#line 367 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 80:
//#line 373 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 81:
//#line 377 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 83:
//#line 384 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 84:
//#line 391 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 85:
//#line 395 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 86:
//#line 402 "Parser.y"
{
						yyval.expr = new Tree.Cases(val_peek(5).expr, val_peek(2).caselist, val_peek(1).deflt, val_peek(7).loc);
					}
break;
case 87:
//#line 409 "Parser.y"
{
						yyval.caselist.add(val_peek(0).cas);
					}
break;
case 88:
//#line 413 "Parser.y"
{
						yyval = new SemValue();
						yyval.caselist = new ArrayList<Tree.Case>();
					}
break;
case 89:
//#line 420 "Parser.y"
{
						yyval.cas = new Tree.Case(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
					}
break;
case 90:
//#line 426 "Parser.y"
{
						yyval.deflt = new Tree.Default(val_peek(1).expr, val_peek(3).loc);
					}
break;
case 91:
//#line 431 "Parser.y"
{
						yyval.stmt = new Tree.DoStmt(val_peek(2).dolist, val_peek(1).dol, val_peek(3).loc);
					}
break;
case 92:
//#line 438 "Parser.y"
{
						yyval.dolist.add(val_peek(1).dol);
					}
break;
case 93:
//#line 442 "Parser.y"
{
						yyval = new SemValue();
						yyval.dolist = new ArrayList<Tree.Dol>();
					}
break;
case 94:
//#line 449 "Parser.y"
{
						yyval.dol = new Tree.Dol(val_peek(2).expr, val_peek(0).slist, val_peek(2).loc);
					}
break;
case 95:
//#line 455 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 96:
//#line 461 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 97:
//#line 468 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 98:
//#line 474 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 99:
//#line 480 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 100:
//#line 484 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 101:
//#line 490 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 102:
//#line 494 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 103:
//#line 500 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 104:
//#line 505 "Parser.y"
{
						yyval.expr = new Tree.Dcopy(val_peek(1).expr, val_peek(3).loc);
					}
break;
case 105:
//#line 510 "Parser.y"
{
						yyval.expr = new Tree.Scopy(val_peek(1).expr, val_peek(3).loc);
					}
break;
case 106:
//#line 515 "Parser.y"
{
						yyval.stmt = new Tree.PrintComp(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1418 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
