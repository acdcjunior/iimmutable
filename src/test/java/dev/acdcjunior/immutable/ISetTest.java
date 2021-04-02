package dev.acdcjunior.immutable;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static dev.acdcjunior.immutable.IList.listOf;


public class ISetTest {

    private final ISet<String> iSet = ISet.setOf("a", "b", "c");

    @Test
    public void toString__equals__hashCode() {
        Assertions.assertThat(iSet.toString()).isEqualTo("ISet[a, b, c]");
        Assertions.assertThat(iSet.equals(ISet.setOf("a", "b", "c"))).isTrue();
        Assertions.assertThat(iSet.hashCode()).isEqualTo(ISet.setOf("a", "b", "c").hashCode());
    }

    @Test
    public void setOf() {
        HashSet<String> set = new HashSet<String>(listOf("a", "b", "c").toList());

        Assertions.assertThat(iSet.toSet()).isEqualTo(set);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void toSet__returned_set_must_be_immutable() {
        Set<String> set = iSet.toSet();
        set.add("new");
    }

    @Test
    public void toMutableSet() {
        Set<String> mutableSet = iSet.toMutableSet();
        Assertions.assertThat(mutableSet).isEqualTo(iSet.toSet());
        mutableSet.add("new");
        Assertions.assertThat(mutableSet).isEqualTo(ISet.setOf("a", "b", "c", "new").toSet());
    }

}