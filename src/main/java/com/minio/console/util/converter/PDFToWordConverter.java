package com.minio.console.util.converter;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PDFToWordConverter {
    public static void convert(String inputPath, String outputPath) {
        try {
            // 加载 Word 文档
            Document doc = new Document(inputPath);
            // 保存为 PDF
            doc.save(outputPath, SaveFormat.DocX);

            System.out.println("转换成功: " + outputPath);
        } catch (Exception e) {
            System.err.println("转换失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static byte[] escalateConvert_pdf(InputStream inputStream) {
        try {
            // 加载 Pdf 文档
            Document doc = new Document(inputStream);
            // 创建 ByteArrayOutputStream 用于保存转换后的 Word 流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 将文档保存为 Word 格式
            doc.save(outputStream, SaveFormat.DocX);
            // 获取字节数组并上传到 MinIO
            byte[] fileBytes = outputStream.toByteArray();
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
