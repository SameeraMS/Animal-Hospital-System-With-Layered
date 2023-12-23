package lk.ijse.ahms.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PngToPdfConverter {

    public static void convertImagesToPDF(String[] imagePaths, String pdfOutputPath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            for (String imagePath : imagePaths) {
                BufferedImage image = ImageIO.read(new File(imagePath));
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
                    float scale = 0.5f; // You can adjust the scale factor as needed
                    contentStream.drawImage(pdImage, 20, 20, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
                }
            }

            document.save(pdfOutputPath);
        }
    }
}
