import java.util.ArrayList;
import java.util.List;

public class TinyFloat {
    private float value;

    // Method to convert binary string to integer for the exponent
    public static int binary2Integer(String binary) {
        int exponent = Integer.parseInt(binary, 2);
        // Handle two's complement for the exponent
        if (binary.charAt(0) == '1') { // If the first bit (sign bit) is 1
            exponent -= 16; // 2^4 = 16, for a 4-bit exponent
        }
        return exponent;
    }

    // Method to convert binary string to decimal for the mantissa
    public static float binary2Decimal(String binary) {
        float decimalValue = 0.0f;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                decimalValue += Math.pow(2, -(i + 1)); // 2^-(i+1) for fractional part
            }
        }
        return decimalValue + 1; // Adding the implicit leading 1
    }

    // Method to create a TinyFloat from a binary string of 8 bits
    public static TinyFloat fromString(String binary) {
        if (binary.length() != 8) {
            throw new IllegalArgumentException("Binary string must be 8 bits long.");
        }

        // Extracting the parts of the binary string
        int signBit = Character.getNumericValue(binary.charAt(0));
        String exponentBits = binary.substring(1, 5);
        String mantissaBits = binary.substring(5, 8);

        // Converting exponent and mantissa
        int exponent = binary2Integer(exponentBits);
        float mantissa = binary2Decimal(mantissaBits);

        // Calculating the TinyFloat value
        float floatValue = mantissa * (float) Math.pow(2, exponent); // Adjusting exponent

        // Adjusting sign
        if (signBit == 1) {
            floatValue = -floatValue;
        }

        TinyFloat tinyFloat = new TinyFloat();
        tinyFloat.value = floatValue;
        return tinyFloat;
    }

    // Method to get the value of TinyFloat
    public float getValue() {
        return value;
    }

    // Method to count how many TinyFloat objects are integers
    public static int numberOfIntegers() {
        int count = 0;
        List<String> validBitSequences = getValidBitSequences();

        for (String bits : validBitSequences) {
            TinyFloat tinyFloat = fromString(bits);
            if (tinyFloat.isInteger()) {
                count++;
            }
        }
        return count;
    }

    // Check if the TinyFloat value is an integer
    public boolean isInteger() {
        return value == Math.floor(value);
    }

    // Method to generate all valid 8-bit sequences
    public static List<String> getValidBitSequences() {
        List<String> sequences = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            String bits = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            sequences.add(bits);
        }
        return sequences;
    }

    // Main method for testing
    public static void main(String[] args) {
        for (String binaryString : getValidBitSequences()) {
            TinyFloat tinyFloat = TinyFloat.fromString(binaryString);
            System.out.printf("%s == %.2f%n", binaryString, tinyFloat.getValue());
        }

        int integerCount = numberOfIntegers();
        System.out.println(integerCount);
    }
}