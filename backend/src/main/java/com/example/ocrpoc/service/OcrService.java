package com.example.ocrpoc.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class OcrService {

    private final ITesseract tesseract;

    public OcrService() {
        tesseract = new Tesseract();
        // If running in container, tessdata should be available in system path; otherwise set datapath
        // tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata"); // adjust if needed
        tesseract.setLanguage("chi_sim+eng");
        tesseract.setTessVariable("user_defined_dpi", "300");
    }

    public List<String> extractTextFromBytes(byte[] bytes, String filename) throws IOException {
        List<String> pagesText = new ArrayList<>();
        if (isPdf(bytes, filename)) {
            try (PDDocument doc = PDDocument.load(bytes)) {
                PDFRenderer renderer = new PDFRenderer(doc);
                int pageCount = doc.getNumberOfPages();
                for (int i = 0; i < pageCount; i++) {
                    BufferedImage image = renderer.renderImageWithDPI(i, 300);
                    String text = doOcr(image);
                    pagesText.add(text);
                }
            } catch (IOException e) {
                throw e;
            }
        } else {
            try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
                BufferedImage img = ImageIO.read(in);
                if (img == null) {
                    throw new IOException("无法读取图片，可能格式不支持");
                }
                String text = doOcr(img);
                pagesText.add(text);
            }
        }
        return pagesText;
    }

    private boolean isPdf(byte[] bytes, String filename) {
        if (filename != null && filename.toLowerCase().endsWith(".pdf")) return true;
        if (bytes.length >= 4 && bytes[0] == '%' && bytes[1] == 'P' && bytes[2] == 'D' && bytes[3] == 'F') return true;
        return false;
    }

    private String doOcr(BufferedImage image) throws IOException {
        try {
            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            throw new IOException("OCR 处理失败: " + e.getMessage(), e);
        }
    }
}
