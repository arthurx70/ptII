/** An interface to an SMT solver.
 * 
 */

package ptolemy.apps.interfaces;

import yices.YicesLite;

import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;

/** An interface to an SMT solver.
 * 
 *  Its behavior is encapsulated in a single method,
 *  <pre>check</pre>, which takes in a list of constraints
 *  and returns a satisfying assignment if one exists,
 *  and "unsat" if no such assignment exists.
 *  Currently, the argument and return value are strings in
 *  the form accepted by the Yices SMT solver.
 * 
 *  @author Ben Lickly
 *
 */
public class SMTSolver {
    
    ///////////////////////////////////////////////////////////////////
    ////                       public methods                      ////
    
    /** Check the satisfiability of the given formula.
     * 
     *  @param formula A formula to be checked.
     *  @return A satisfying assignment if it exists, "unsat" if it does not,
     *   and the empty string if no assertion can be made.
     */
    public String check(String formula) {
        int ctx = yl.yicesl_mk_context();
        formula += "(set-evidence! true)\n(check)\n";
        
        StringBuffer result = new StringBuffer();
        try {
            File tmpfile = File.createTempFile("yicesout", "ycs");
            yl.yicesl_set_output_file(tmpfile.getAbsolutePath());

            yl.yicesl_read(ctx, formula);
            yl.yicesl_del_context(ctx);

            BufferedReader resultBuf = new BufferedReader(new FileReader(tmpfile));
            while (resultBuf.ready()) {
                result.append(resultBuf.readLine());
            }
            resultBuf.close();
            if (!tmpfile.delete()) {
                System.err.println("Error deleting temporary file:");
            }
        } catch (IOException e) {
            System.err.println("Error accessing temporary file:");
            e.printStackTrace();
        }
        return result.toString();   
    }

    /** Test that the SMT Solver works correctly.
     * 
     *  @param args Ignored.
     *  @throws AssertionError If the test fails.
     */
    public static void main(String[] args) {
        SMTSolver ytm = new SMTSolver();
        String result = ytm.check("(define x::int)"
                    + "\n(assert (= x 9))"
                    + "\n(set-evidence! true)"
                    + "\n(check)");
        System.out.println("Result: " + result);
        assert(result == "sat(= x 9)");
    }
    
    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    /** The interface to Yices SMT solver using the Yices Java API Lite.
     */
    private YicesLite yl = new YicesLite();

}
