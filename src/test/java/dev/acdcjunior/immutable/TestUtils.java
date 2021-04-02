package dev.acdcjunior.immutable;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.function.ThrowingRunnable;

import static org.assertj.core.api.Assertions.assertThat;


public class TestUtils {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static AbstractThrowableAssert<?, ? extends Throwable> assertThatThrownBy(ThrowingRunnable runnable) {
        try {
            runnable.run();
            return new AbstractThrowableAssert(null, Object.class) {
                @Override
                public AbstractAssert isInstanceOf(Class type) {
                    return new AbstractAssert(null, Object.class) { };
                }
            };
        } catch (Throwable throwable) {
            return assertThat(throwable);
        }
    }

}
