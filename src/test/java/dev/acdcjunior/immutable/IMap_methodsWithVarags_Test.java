package dev.acdcjunior.immutable;

import dev.acdcjunior.immutable.tuple.IPair;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.HashMap;
import java.util.Map;

import static dev.acdcjunior.immutable.TestUtils.assertThatThrownBy;


public class IMap_methodsWithVarags_Test {

    static final IPair<String, Integer> pairK1 = IPair.pairOf("k1", 11111);
    static final IPair<String, Integer> pairK2 = IPair.pairOf("k2", 22222);
    static final IPair<String, Integer> pairK3 = IPair.pairOf("k3", 33333);
    static final IPair<String, Integer> pairK4 = IPair.pairOf("k4", 44444);
    static final IPair<String, Integer> pairK5 = IPair.pairOf("k5", 55555);
    static final IPair<String, Integer> pairK6 = IPair.pairOf("k6", 66666);

    static void verifyMapOfVarargs(Map<String, Integer> iMap, int n) {
        Map<String, Integer> map = createHashMap(n);
        Assertions.assertThat(iMap).isEqualTo(map);
    }

    static Map<String, Integer> createHashMap(int n) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 1; i <= n; i++) {
            map.put("k" + i, Integer.valueOf("" + i + i + i + i + i));
        }
        return map;
    }

    @Test
    public void mapOf_pairs() {
        verifyMapOfVarargs(IMap.mapOf(pairK1).toMap(), 1);
        verifyMapOfVarargs(IMap.mapOf(pairK1, pairK2).toMap(), 2);
        verifyMapOfVarargs(IMap.mapOf(pairK1, pairK2, pairK3).toMap(), 3);
        verifyMapOfVarargs(IMap.mapOf(pairK1, pairK2, pairK3, pairK4).toMap(), 4);
        verifyMapOfVarargs(IMap.mapOf(pairK1, pairK2, pairK3, pairK4, pairK5).toMap(), 5);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void mapOf_pairs__varags() {
        verifyMapOfVarargs(IMap.mapOf(pairK1, pairK2, pairK3, pairK4, pairK5, pairK6).toMap(), 6);
    }

    @Test
    public void mapOf_IList_of_pairs() {
        verifyMapOfVarargs(IMap.mapOf(IList.listOf(pairK1, pairK2, pairK3, pairK4, pairK5, pairK6)).toMap(), 6);
    }

    @Test
    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    public void mapOf__null_pair() {
        assertThatThrownBy(new ThrowingRunnable() {
            @Override
            public void run() {
                IMap.mapOf(pairK1, null, pairK3);
            }
        }).isInstanceOf(NullPointerException.class).hasMessageContaining(
                "Entry on index 1 passed to mapOf() is null. If this is intentional, use #mapOfNonNull() instead"
        );
        assertThatThrownBy(new ThrowingRunnable() {
            @Override
            public void run() {
                IMap.mapOf(IList.listOf(pairK1, null, pairK3));
            }
        }).isInstanceOf(NullPointerException.class).hasMessageContaining(
                "Entry on index 1 passed to mapOf() is null. If this is intentional, use #mapOfNonNull() instead"
        );
    }

    @SuppressWarnings("unchecked")
    @Test
    public void mapOfNonNull() {
        verifyMapOfVarargs(IMap.mapOfNonNull(pairK1, pairK2).toMap(), 2);
        verifyMapOfVarargs(IMap.mapOfNonNull(pairK1, null, pairK2).toMap(), 2);
    }

}