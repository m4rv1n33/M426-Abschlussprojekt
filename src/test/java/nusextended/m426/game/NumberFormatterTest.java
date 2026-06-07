package nusextended.m426.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberFormatterTest {

    @Test
    void formatsSmallAmountsAsPlainIntegers() {
        assertEquals("0", NumberFormatter.formatCurrency(0));
        assertEquals("999", NumberFormatter.formatCurrency(999));
    }

    @Test
    void formatsThousandsWithKSuffix() {
        assertEquals("1.0K", NumberFormatter.formatCurrency(1_000));
        assertEquals("12.3K", NumberFormatter.formatCurrency(12_345));
    }

    @Test
    void formatsMillionsWithMSuffix() {
        assertEquals("1.0M", NumberFormatter.formatCurrency(1_000_000));
        assertEquals("12.3M", NumberFormatter.formatCurrency(12_345_678));
    }

    @Test
    void formatsBillionsWithBSuffix() {
        assertEquals("1.0B", NumberFormatter.formatCurrency(1_000_000_000.0));
        assertEquals("12.3B", NumberFormatter.formatCurrency(12_345_678_900.0));
    }

    @Test
    void formatsTrillionsWithTSuffix() {
        assertEquals("1.0T", NumberFormatter.formatCurrency(1_000_000_000_000.0));
        assertEquals("12.3T", NumberFormatter.formatCurrency(12_345_678_900_000.0));
    }
}
