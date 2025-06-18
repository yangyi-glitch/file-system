package com.minio.console.util.converter;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class WordToPDFConverter {
    public static void convert(String inputPath, String outputPath) {
        try {
            // 加载 Word 文档
            Document doc = new Document(inputPath);
            // 保存为 Pdf
            doc.save(outputPath, SaveFormat.PDF);

            System.out.println("转换成功: " + outputPath);
        } catch (Exception e) {
            System.err.println("转换失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static byte[] escalateConvert_word(InputStream inputStream) {
        // 创建 ByteArrayOutputStream 用于保存转换后的 PDF 流
        try (InputStream bufferedInput = new BufferedInputStream(inputStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 1024)){
            long startTime = System.currentTimeMillis();
            Document doc = new Document(bufferedInput);
            doc.save(outputStream, SaveFormat.PDF);
            byte[] fileBytes = outputStream.toByteArray();
            System.out.println("Word 转 PDF 耗时: " + (System.currentTimeMillis() - startTime) + " ms");
            return fileBytes;
        } catch (Exception e) {
            System.err.println("转换失败: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        convert("Z:\\work\\minio\\output.pdf", "input.docx");
    }
}
