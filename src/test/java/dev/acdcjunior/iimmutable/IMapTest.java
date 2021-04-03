package dev.acdcjunior.iimmutable;

import dev.acdcjunior.iimmutable.fn.ISupplier;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.Map;

import static dev.acdcjunior.iimmutable.IMap_methodsWithVarags_Test.*;
import static dev.acdcjunior.iimmutable.TestUtils.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;


public class IMapTest {

    private final IMap<String, Integer> iMap = IMap.mapOf(pairK1, pairK2, pairK3);
    private final IMap<String, Integer> iMap4 = IMap.mapOf(pairK1, pairK2, pairK3, pairK4);

    @Test
    public void equals__hashCode() {
        assertThat(iMap).isEqualTo(IMap.mapOf(pairK1, pairK2, pairK3));
        assertThat(iMap.hashCode()).isEqualTo(IMap.mapOf(pairK1, pairK2, pairK3).hashCode());

        assertThat(iMap).isNotEqualTo(IMap.mapOf(pairK1, pairK2, pairK4));
        assertThat(iMap.hashCode()).isNotEqualTo(IMap.mapOf(pairK1, pairK2, pairK4).hashCode());
    }

    @Test
    public void toString__test() {
        assertThat(iMap.toString()).isEqualTo("{k3=33333, k1=11111, k2=22222}");
    }

    @Test
    public void toMap__is_immutable() {
        assertThatThrownBy(new ThrowingRunnable() {
            @Override
            public void run() {
                iMap.toMap().put("x", 123);
            }
        }).isInstanceOf(UnsupportedOperationException.class).hasMessage(null);
    }

    @Test
    public void toMutableMap() {
        Map<String, Integer> map = iMap.toMutableMap();
        map.put(pairK4.getKey(), pairK4.getValue());
        assertThat(map).isEqualTo(createHashMap(4));
    }

    @Test
    public void put() {
        IMap<String, Integer> iMapWith4 = iMap.put(pairK4.getKey(), pairK4.getValue());
        assertThat(iMapWith4).isNotEqualTo(iMap);
        assertThat(iMapWith4).isEqualTo(iMap4);
    }

    @Test
    public void get() {
        assertThat(iMap.get("k2")).isEqualTo(22222);
        assertThat(iMap.get("@")).isNull();
    }

    @Test
    public void getOrDefault() {
        assertThat(iMap.getOrDefault("k2", 99)).isEqualTo(22222);
        assertThat(iMap.getOrDefault("@", 99)).isEqualTo(99);

        ISupplier<Integer> supplier99 = new ISupplier<Integer>() {
            @Override
            public Integer get() {
                return 99;
            }
        };
        assertThat(iMap.getOrDefault("k2", supplier99)).isEqualTo(22222);
        assertThat(iMap.getOrDefault("@", supplier99)).isEqualTo(99);
    }

    @Test
    public void getOrDefault__nullArgs() {
        assertThat(iMap.getOrDefault("@", (Integer) null)).isEqualTo(null);
        assertThatThrownBy(new ThrowingRunnable() {
            @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
            @Override
            public void run() {
                iMap.getOrDefault("@", (ISupplier<Integer>) null);
            }
        }).isInstanceOf(NullPointerException.class).hasMessage("defaultValueSupplier cannot be null");
    }

}