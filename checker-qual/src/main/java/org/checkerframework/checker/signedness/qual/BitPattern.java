package org.checkerframework.checker.signedness.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.SubtypeOf;

/**
 * The value represents a bit pattern that should be manipulated using bitwise operations rather
 * than arithmetic operations. This qualifier is intended for values that are not arithmetic
 * quantities but are patterns of bits, such as return values from {@code Double.doubleToLongBits}
 * or values used as bitsets.
 *
 * <p>Values annotated with {@code @BitPattern} support bitwise operations (AND, OR, XOR, NOT,
 * shifts) but arithmetic operations (addition, subtraction, multiplication, division, modulo) and
 * relational comparisons (less than, greater than) are forbidden to prevent misuse.
 *
 * <p>Examples of appropriate uses:
 *
 * <pre>{@code
 * @BitPattern long bits = Double.doubleToLongBits(3.14);
 * @BitPattern int flags = 0xFF00FF00;
 *
 * // Allowed bitwise operations:
 * @BitPattern long result1 = bits & 0xFFFFFFFF00000000L;
 * @BitPattern int result2 = flags | 0x00FF0000;
 * @BitPattern int result3 = flags ^ 0xFFFFFFFF;
 * @BitPattern int result4 = ~flags;
 * @BitPattern int result5 = flags << 8;
 * @BitPattern int result6 = flags >> 4;
 * @BitPattern int result7 = flags >>> 2;
 *
 * // Forbidden arithmetic operations (will cause type errors):
 * // @BitPattern int invalid1 = flags + 10;     // ERROR
 * // @BitPattern int invalid2 = flags * 2;      // ERROR
 * // boolean invalid3 = flags < 100;            // ERROR
 * }</pre>
 *
 * <p>The {@code @BitPattern} qualifier is positioned in the signedness type hierarchy as a subtype
 * of {@code @UnknownSignedness} and is unrelated to {@code @Signed} and {@code @Unsigned}. This
 * design prevents accidental mixing of bit patterns with arithmetic values while allowing safe
 * bitwise manipulation.
 *
 * <p>Common sources of bit pattern values include:
 *
 * <ul>
 *   <li>{@code Double.doubleToLongBits(double)} - IEEE 754 bit representation of a double
 *   <li>{@code Float.floatToIntBits(float)} - IEEE 754 bit representation of a float
 *   <li>Bitset operations and flag manipulations
 *   <li>Hash code computations that manipulate bit patterns
 *   <li>Cryptographic operations on bit sequences
 * </ul>
 *
 * @checker_framework.manual #signedness-checker Signedness Checker
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf({UnknownSignedness.class})
public @interface BitPattern {}
