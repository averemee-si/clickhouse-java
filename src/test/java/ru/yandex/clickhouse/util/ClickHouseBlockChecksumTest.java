package ru.yandex.clickhouse.util;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.yandex.clickhouse.response.ClickHouseLZ4Stream;

import javax.xml.bind.DatatypeConverter;

/**
 * @author Anton Sukhonosenko <a href="mailto:algebraic@yandex-team.ru"></a>
 * @date 08.06.18
 */
public class ClickHouseBlockChecksumTest {
    private static final int HEADER_SIZE_BYTES = 9;

    @Test
    public void calculateChecksum() {
        byte[] compressedData = DatatypeConverter.parseHexBinary("1F000100078078000000B4000000");
        int uncompressedSizeBytes = 35;

        ClickHouseBlockChecksum checksum = ClickHouseBlockChecksum.calculateForBlock(
            (byte) ClickHouseLZ4Stream.MAGIC,
            compressedData.length + HEADER_SIZE_BYTES,
            uncompressedSizeBytes,
            compressedData,
            compressedData.length
        );

        Assert.assertEquals(
            DatatypeConverter.printHexBinary(checksum.asBytes()),
            "923AF569343D26F98EAEF00EEFEC36A9"
        );
    }
}