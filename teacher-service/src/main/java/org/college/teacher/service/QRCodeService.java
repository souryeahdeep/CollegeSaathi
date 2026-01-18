package org.college.teacher.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Service
public class QRCodeService {
    public byte[] getQRCode() throws IOException, WriterException {

        String data = "http://localhost:8080/student/details";
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        return createQR(data, charset, hints, 200, 200);
    }

    public static byte[] createQR(String data,
                                  String charset,
                                  Map<EncodeHintType, ?> hints,
                                  int width,
                                  int height) throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE,
                width,
                height
        );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(
                matrix,
                "PNG",
                baos
        );

        return baos.toByteArray(); // return QR as bytes
    }

}
