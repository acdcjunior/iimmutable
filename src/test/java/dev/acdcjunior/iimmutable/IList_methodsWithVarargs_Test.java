package dev.acdcjunior.iimmutable;

import dev.acdcjunior.iimmutable.fn.IFunction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("SameParameterValue")
public class IList_methodsWithVarargs_Test {

    private static <R extends N<R>> IList<R> iListOfTen(R r) {
        return IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4), r.e(5), r.e(6), r.e(7), r.e(8), r.e(9), r.e(10));
    }

    private static <R extends N<R>> IList<R> iListOfOne(R r) {
        return IList.listOf(r.e(1));
    }

    private static class N<R> {
        final int n;
        private N(int n) { this.n = n; }
        @SuppressWarnings({"unchecked", "rawtypes"}) R e(int x) { return (R) new N(x); }
        @SuppressWarnings({"unchecked", "rawtypes"}) Iterable<R> i(int x) { return Arrays.asList((R) new N(x)); }
        @Override public String toString() { return "N(" + n + ')'; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            N<?> n1 = (N<?>) o;
            return n == n1.n;
        }
        @Override public int hashCode() { return n; }
    }

    @SuppressWarnings("rawtypes")
    public static final N nOne = new N(1);

    private static <R extends N<R>> List<Integer> asInts(IList<R> rs) {
        return rs.map(new IFunction<R, Integer>() {
            @Override
            public Integer apply(R input) {
                return input.n;
            }
        }).toList();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void listOf_varargs() {
        listOfCheckWarnings(nOne);
        listOfCheckWarningsElevenPlus(nOne);
    }

    private static <R extends N<R>> void listOfCheckWarnings(R r) {
        /* 1  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1)), 1);
        /* 2  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2)), 2);
        /* 3  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3)), 3);
        /* 4  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4)), 4);
        /* 5  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4), r.e(5)), 5);
        /* 6  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4), r.e(5), r.e(6)), 6);
        /* 7  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4), r.e(5), r.e(6), r.e(7)), 7);
        /* 8  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4), r.e(5), r.e(6), r.e(7), r.e(8)), 8);
        /* 9  */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4), r.e(5), r.e(6), r.e(7), r.e(8), r.e(9)), 9);
        /* 10 */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4), r.e(5), r.e(6), r.e(7), r.e(8), r.e(9), r.e(10)), 10);
    }

    private static <R extends N<R>> void verifyIListAsIntsIsEqualTo(IList<R> ilist, int n) {
        List<Integer> ints = new ArrayList<Integer>();
        for (int i = 1; i <= n; i++) {
            ints.add(i);
        }
        assertThat(asInts(ilist)).isEqualTo(ints);
    }

    @SuppressWarnings("unchecked")
    private static <R extends N<R>> void listOfCheckWarningsElevenPlus(R r) {
        /* 11+ */ verifyIListAsIntsIsEqualTo(IList.listOf(r.e(1), r.e(2), r.e(3), r.e(4), r.e(5), r.e(6), r.e(7), r.e(8), r.e(9), r.e(10), r.e(11)), 11);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void subtract_varargs() {
        subtractCheckWarnings(nOne);
        subtractCheckWarningsSixPlus(nOne);
    }

    private static <R extends N<R>> void subtractCheckWarnings(R r) {
        /* 1 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).subtract(r.e(10)), 9);
        /* 2 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).subtract(r.e(10), r.e(9)), 8);
        /* 3 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).subtract(r.e(10), r.e(9), r.e(8)), 7);
        /* 4 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).subtract(r.e(10), r.e(9), r.e(8), r.e(7)), 6);
        /* 5 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).subtract(r.e(10), r.e(9), r.e(8), r.e(7), r.e(6)), 5);

        /* 1 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).minus(r.e(10)), 9);
        /* 2 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).minus(r.e(10), r.e(9)), 8);
        /* 3 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).minus(r.e(10), r.e(9), r.e(8)), 7);
        /* 4 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).minus(r.e(10), r.e(9), r.e(8), r.e(7)), 6);
        /* 5 */ verifyIListAsIntsIsEqualTo(iListOfTen(r).minus(r.e(10), r.e(9), r.e(8), r.e(7), r.e(6)), 5);
    }

    @SuppressWarnings("unchecked")
    private static <R extends N<R>> void subtractCheckWarningsSixPlus(R r) {
        /* 6+ */ verifyIListAsIntsIsEqualTo(iListOfTen(r).subtract(r.e(10), r.e(9), r.e(8), r.e(7), r.e(6), r.e(5)), 4);

        /* 6+ */ verifyIListAsIntsIsEqualTo(iListOfTen(r).minus(r.e(10), r.e(9), r.e(8), r.e(7), r.e(6), r.e(5)), 4);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void plus_varargs() {
        plusCheckWarnings(nOne);
        plusCheckWarningsSixPlus(nOne);
    }

    private static <R extends N<R>> void plusCheckWarnings(R r) {
        /* 1 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.e(2)), 2);
        /* 2 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.e(2), r.e(3)), 3);
        /* 3 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.e(2), r.e(3), r.e(4)), 4);
        /* 4 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.e(2), r.e(3), r.e(4), r.e(5)), 5);
        /* 5 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.e(2), r.e(3), r.e(4), r.e(5), r.e(6)), 6);

        /* 1 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.e(2)), 2);
        /* 2 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.e(2), r.e(3)), 3);
        /* 3 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.e(2), r.e(3), r.e(4)), 4);
        /* 4 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.e(2), r.e(3), r.e(4), r.e(5)), 5);
        /* 5 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.e(2), r.e(3), r.e(4), r.e(5), r.e(6)), 6);
    }

    @SuppressWarnings("unchecked")
    private static <R extends N<R>> void plusCheckWarningsSixPlus(R r) {
        /* 6+ */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.e(2), r.e(3), r.e(4), r.e(5), r.e(6), r.e(7)), 7);
        /* 6+ */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.e(2), r.e(3), r.e(4), r.e(5), r.e(6), r.e(7)), 7);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void plusIterable_varargs() {
        plusIterableCheckWarnings(nOne);
        plusIterableCheckWarningsSixPlus(nOne);
    }

    private static <R extends N<R>> void plusIterableCheckWarnings(R r) {
        /* 1 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.i(2)), 2);
        /* 2 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.i(2), r.i(3)), 3);
        /* 3 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.i(2), r.i(3), r.i(4)), 4);
        /* 4 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.i(2), r.i(3), r.i(4), r.i(5)), 5);
        /* 5 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.i(2), r.i(3), r.i(4), r.i(5), r.i(6)), 6);

        /* 1 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.i(2)), 2);
        /* 2 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.i(2), r.i(3)), 3);
        /* 3 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.i(2), r.i(3), r.i(4)), 4);
        /* 4 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.i(2), r.i(3), r.i(4), r.i(5)), 5);
        /* 5 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.i(2), r.i(3), r.i(4), r.i(5), r.i(6)), 6);
    }

    @SuppressWarnings("unchecked")
    private static <R extends N<R>> void plusIterableCheckWarningsSixPlus(R r) {
        /* 6+ */ verifyIListAsIntsIsEqualTo(iListOfOne(r).plus(r.i(2), r.i(3), r.i(4), r.i(5), r.i(6), r.i(7)), 7);
        /* 6+ */ verifyIListAsIntsIsEqualTo(iListOfOne(r).add(r.i(2), r.i(3), r.i(4), r.i(5), r.i(6), r.i(7)), 7);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void concat_varargs() {
        concatCheckWarnings(nOne);
        concatCheckWarningsSixPlus(nOne);
    }

    private static <R extends N<R>> void concatCheckWarnings(R r) {
        /* 1 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).concat(r.i(2)), 2);
        /* 2 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).concat(r.i(2), r.i(3)), 3);
        /* 3 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).concat(r.i(2), r.i(3), r.i(4)), 4);
        /* 4 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).concat(r.i(2), r.i(3), r.i(4), r.i(5)), 5);
        /* 5 */ verifyIListAsIntsIsEqualTo(iListOfOne(r).concat(r.i(2), r.i(3), r.i(4), r.i(5), r.i(6)), 6);
    }

    @SuppressWarnings("unchecked")
    private static <R extends N<R>> void concatCheckWarningsSixPlus(R r) {
        /* 6+ */ verifyIListAsIntsIsEqualTo(iListOfOne(r).concat(r.i(2), r.i(3), r.i(4), r.i(5), r.i(6), r.i(7)), 7);
    }

}
