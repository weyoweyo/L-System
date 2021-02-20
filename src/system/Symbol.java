/*
 * author: Weiyue Cai
 * date: feb 20, 2021
 * */

package system;

import java.util.Iterator;

/**
 * Symbol in an L-system's alphabet.
 *
 */
public class Symbol {
    private final char value;

    public Symbol(char c) {
        this.value = c;
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }

    /**
     * Common interface to a string of symbols.
     *
     */
    public interface Seq extends Iterable<Symbol> {
        Iterator<Symbol> iterator();
    }

    public boolean equals(Symbol otherSym) {
        if (this.value == otherSym.value) {
            return true;
        }
        return false;
    }
}
