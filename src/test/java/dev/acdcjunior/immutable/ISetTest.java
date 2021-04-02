package dev.acdcjunior.immutable;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashSet;

import static dev.acdcjunior.immutable.IList.listOf;


public class ISetTest {

    private final ISet<String> iSet = ISet.setOf("a", "b", "c");

    @Test
    public void setOf() {
        HashSet<String> set = new HashSet<String>(listOf("a", "b", "c").toList());

        Assertions.assertThat(iSet.toSet()).isEqualTo(set);
    }

    @Test
    public void toString__equals__hashCode() {
        Assertions.assertThat(iSet.toString()).isEqualTo("ISet[a, b, c]");
        Assertions.assertThat(iSet.equals(ISet.setOf("a", "b", "c"))).isTrue();
        Assertions.assertThat(iSet.hashCode()).isEqualTo(ISet.setOf("a", "b", "c").hashCode());
    }

}