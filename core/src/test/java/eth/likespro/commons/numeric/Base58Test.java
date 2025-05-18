/*
 * Copyright 2025 likespro.
 *
 * From https://github.com/likespro/commons
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eth.likespro.commons.numeric;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class Base58Test {

    @Test
    void testDecodeToBigIntegerEmptyString() {
        String input = "";
        BigInteger result = Base58.decodeToBigInteger(input);
        assertEquals(BigInteger.ZERO, result, "Decoding an empty string should result in BigInteger.ZERO.");
    }

    @Test
    void testDecodeToBigIntegerValidCases() {
        assertEquals(BigInteger.valueOf(1), Base58.decodeToBigInteger("2"),
                "Decoding '2' should result in BigInteger 1.");
        assertEquals(new BigInteger("255"), Base58.decodeToBigInteger("5Q"),
                "Decoding '5Q' should result in BigInteger 255.");
        assertEquals(new BigInteger("123456789"), Base58.decodeToBigInteger("BukQL"),
                "Decoding 'BukQL' should result in BigInteger 123456789.");
        assertEquals(new BigInteger("9223372036854775831"), Base58.decodeToBigInteger("NQm6nKp8qFc"),
                "Decoding 'NQm6nKp8qFc' should result in BigInteger 9223372036854775831.");
    }

    @Test
    void testDecodeToBigIntegerInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> Base58.decodeToBigInteger("0"),
                "Decoding an invalid string (e.g., containing '0') should throw IllegalArgumentException.");
        assertThrows(IllegalArgumentException.class, () -> Base58.decodeToBigInteger("O"),
                "Decoding an invalid string (e.g., containing 'O') should throw IllegalArgumentException.");
        assertThrows(IllegalArgumentException.class, () -> Base58.decodeToBigInteger("I"),
                "Decoding an invalid string (e.g., containing 'I') should throw IllegalArgumentException.");
    }

    @Test
    void testDecodeEmptyString() {
        String input = "";
        byte[] decoded = Base58.decode(input);
        assertArrayEquals(new byte[0], decoded, "Decoding an empty string should result in an empty byte array.");
    }

    @Test
    void testDecodeWithLeadingZeros() {
        String input = "11Ldp";
        byte[] decoded = Base58.decode(input);
        assertArrayEquals(new byte[]{0, 0, 1, 2, 3}, decoded, "Decoding should handle leading zeros correctly.");
    }

    @Test
    void testDecodeVariousCases() {
        assertArrayEquals(new byte[]{0}, Base58.decode("1"),
                "Decoding '1' should result in a single zero byte.");
        assertArrayEquals(new byte[]{1}, Base58.decode("2"),
                "Decoding '2' should result in a single byte of 1.");
        assertArrayEquals(new byte[]{0, (byte) 255, (byte) 254}, Base58.decode("1LUu"),
                "Decoding '1LUu' should produce the correct byte array.");
    }

    @Test
    void testDecodeInvalidStrings() {
        assertThrows(IllegalArgumentException.class, () -> Base58.decode("0"),
                "Decoding an invalid string (e.g., containing '0') should throw IllegalArgumentException.");
        assertThrows(IllegalArgumentException.class, () -> Base58.decode("O"),
                "Decoding an invalid string (e.g., containing 'O') should throw IllegalArgumentException.");
        assertThrows(IllegalArgumentException.class, () -> Base58.decode("I"),
                "Decoding an invalid string (e.g., containing 'I') should throw IllegalArgumentException.");
    }

    @Test
    void testEncodeEmptyArray() {
        byte[] input = new byte[0];
        String encoded = Base58.encode(input);
        assertEquals("", encoded, "Encoding an empty array should result in an empty string.");
    }

    @Test
    void testEncodeWithLeadingZeros() {
        byte[] input = new byte[]{0, 0, 1, 2, 3};
        String encoded = Base58.encode(input);
        assertEquals("11Ldp", encoded, "Encoding an array with leading zeros should preserve them in the output.");
    }

    @Test
    void testEncodeVariousCases() {
        assertEquals("1", Base58.encode(new byte[]{0}),
                "Encoding a single zero byte should return '1'.");
        assertEquals("2", Base58.encode(new byte[]{1}),
                "Encoding a single byte of 1 should return '2'.");
        assertEquals("1LUu", Base58.encode(new byte[]{0, (byte) 255, (byte) 254}),
                "Encoding should correctly handle more complex cases.");
    }
}