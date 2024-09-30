package com.example;

import java.util.Arrays;

import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.fli.Prolog;

public class Main {

    public static void main(String[] args) {
        if (System.getenv("SWI_HOME_DIR") != null
                || System.getenv("SWI_EXEC_FILE") != null
                || System.getenv("SWIPL_BOOT_FILE") != null) {
            String init_swi_config
                    = String.format("%s %s %s -g true -q --no-signals --no-packs",
                            System.getenv("SWI_EXEC_FILE") == null ? "swipl"
                            : System.getenv("SWI_EXEC_FILE"),
                            System.getenv("SWIPL_BOOT_FILE") == null ? ""
                            : String.format("-x %s", System.getenv("SWIPL_BOOT_FILE")),
                            System.getenv("SWI_HOME_DIR") == null ? ""
                            : String.format("--home=%s", System.getenv("SWI_HOME_DIR")));
            System.out.println(String.format("\nSWIPL initialized with: %s", init_swi_config));

            JPL.setDefaultInitArgs(init_swi_config.split("\\s+"));    // initialize SWIPL engine
        } else {
            System.out.println("No explicit initialization done: no SWI_HOME_DIR, SWI_EXEC_FILE, or SWIPL_BOOT_FILE defined");
        }

        JPL.setTraditional(); // yyyyyyaaas bebe
        JPL.init();
        System.out.println("Prolog engine actual init args: " + Arrays.toString(Prolog.get_actual_init_args()));

        Query q1
                = new Query(
                        "consult",
                        new Term[]{new Atom("test.pl")}
                );

        System.out.println("consult " + (q1.hasSolution() ? "succeeded" : "failed"));

        Query q2
                = new Query(
                        "child_of",
                        new Term[]{new Atom("joe"), new Atom("ralf")}
                );

        System.out.println("child_of(joe,ralf) is " + (q2.hasSolution() ? "provable" : "not provable") + " solution " + q2.oneSolution().toString());
    }
}
