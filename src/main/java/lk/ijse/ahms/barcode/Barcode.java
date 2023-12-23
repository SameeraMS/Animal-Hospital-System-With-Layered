package lk.ijse.ahms.barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;

public class Barcode {

    public static String barcodeRead(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no Barcode code in the image");
            return null;
        }
    }


    /*public static String barcodeRead(BufferedImage read) {
        try {
            LuminanceSource source= new BufferedImageLuminanceSource(read);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result decode = new MultiFormatReader().decode(binaryBitmap);
            System.out.println(decode.getText());
            return decode.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }finally {
            System.out.println("----come--");
        }
        return "";

    }*/

}
