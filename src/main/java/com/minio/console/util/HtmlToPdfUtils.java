package com.minio.console.util;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HtmlToPdfUtil
 *
 * @author Administrator
 * @summary
 * @Copyright (c) 2024， Viatris Group All Rights Reserved.
 * @date 2024-06-14 10:53:06
 */
@Slf4j
public class HtmlToPdfUtils {
    public static String toPdf() {
        String outputFilePath = "output.pdf";
        String htmlContent = getHtml();

        String content2 = getContent2();

        htmlContent = htmlContent.replace("policy.content", content2);
        try {
            ITextRenderer renderer = new ITextRenderer();

            ITextFontResolver fontResolver = renderer.getFontResolver();

            // 注册中文字体
            fontResolver.addFont("C:\\Users\\19659\\Desktop\\simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            // Parse the HTML content from the string
            renderer.setDocumentFromString(htmlContent);

            // Render the document to PDF and write to the output stream
            renderer.layout();

            // 使用ByteArrayOutputStream来捕获PDF输出的字节
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // 创建PDF并输出到ByteArrayOutputStream
            renderer.createPDF(baos);

            // 关闭流
            baos.close();

            FileOutputStream fos = new FileOutputStream(outputFilePath);
            baos.writeTo(fos);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private static String getHtml() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head> \n" +
                "  <title>查看电子合同</title>\n" +
                "\n" +
                "\n" +
                "<style>\n" +
                "body {\n" +
                "    font-family: SimSun;\n" +
                "}\n" +
                "  .page {\n" +
                "    background-color: #fff;\n" +
                "    padding: 0 15px 15px 15px;\n" +
                "    height: auto;\n" +
                "  }\n" +
                "\n" +
                "  .contract-top {\n" +
                "    line-height: 50px;\n" +
                "    border-bottom: 1px solid #e9e9e9;\n" +
                "    font-size: 16px;\n" +
                "    font-weight: 600;\n" +
                "  }\n" +
                "\n" +
                "  .contract-top>img {\n" +
                "    width: 30px;\n" +
                "    height: 30px;\n" +
                "    margin-top: 10px;\n" +
                "    margin-right: 15px;\n" +
                "    float: left;\n" +
                "  }\n" +
                "\n" +
                "  .detail-title {\n" +
                "    position: relative;\n" +
                "    margin-top: 10px;\n" +
                "  }\n" +
                "\n" +
                "  .detail-title::before {\n" +
                "    content: '';\n" +
                "    display: block;\n" +
                "    width: 5px;\n" +
                "    height: 20px;\n" +
                "    background-color: #bd0d18;\n" +
                "    position: absolute;\n" +
                "    left: 0;\n" +
                "    top: 10px;\n" +
                "  }\n" +
                "\n" +
                "  .info-li>view {\n" +
                "    padding: 2.5px 0;\n" +
                "    border-color: #fff !important;\n" +
                "  }\n" +
                "\n" +
                "  .contract-container {\n" +
                "    position: relative;\n" +
                "\n" +
                "  }\n" +
                "\n" +
                "  .chapter-image {\n" +
                "    position: absolute;\n" +
                "    bottom: -15px;\n" +
                "    right: 25px;\n" +
                "    width: 140px;\n" +
                "    height: 140px;\n" +
                "    z-index: 99;\n" +
                "  }\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "  .detail-title {\n" +
                "    line-height: 39px;\n" +
                "    font-weight: 600;\n" +
                "    padding: 0 15px;\n" +
                "  }\n" +
                "\n" +
                "  .info-li {\n" +
                "    background-color: #fff;\n" +
                "    padding: 0 15px;\n" +
                "  }\n" +
                "\n" +
                "  .info-li>div {\n" +
                "    line-height: 25px;\n" +
                "    padding: 15px 0;\n" +
                "  }\n" +
                "\n" +
                "  /* .info-li>div:not(:first-child) {\n" +
                "    border-top: 1px solid #e9e9e9;\n" +
                "  } */\n" +
                "\n" +
                "  .info-li>div:after {\n" +
                "    display: block;\n" +
                "    content: '';\n" +
                "    clear: both;\n" +
                "    font-size: 0;\n" +
                "  }\n" +
                "\n" +
                "  .info-li>div>span {\n" +
                "    float: left;\n" +
                "    width: 120px;\n" +
                "  }\n" +
                "\n" +
                "  .info-li>div>span:nth-last-child(1) {\n" +
                "    float: right;\n" +
                "    text-align: right;\n" +
                "    width: calc(100% - 120px);\n" +
                "  }\n" +
                "\n" +
                "  .info-li-img {\n" +
                "    width: 99px;\n" +
                "    height: 70px;\n" +
                "    border-radius: 3px;\n" +
                "    border: 1px solid #e9e9e9;\n" +
                "    margin-left: 15px;\n" +
                "    overflow: hidden;\n" +
                "    display: inline-block;\n" +
                "  }\n" +
                "\n" +
                "  .info-li .van-tag {\n" +
                "    margin-left: 15px;\n" +
                "  }\n" +
                "\n" +
                "  .statement {\n" +
                "    margin-top: 30px;\n" +
                "  }\n" +
                "\n" +
                "  .statement .statement-label,\n" +
                "  .statement-title {\n" +
                "    font-size: 12px;\n" +
                "    font-weight: bold;\n" +
                "  }\n" +
                "table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "        th, td {\n" +
                "            border: 1px solid black;\n" +
                "            padding: 8px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "        th {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"page\">\n" +
                "    <div class=\"contract-container\">\n" +
                "      <div class=\"detail-title\">匹配报告</div>\n" +
                "          policy.content" +
                "    </div>\n" +
                "  </div>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    public static String getContent() {
        return  "      <div class=\"info-li\">\n" +
                "        <div>\n" +
                "          <span>细则名称</span>\n" +
                "          <span>policy.detailName </span>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "          <span>匹配度</span>\n" +
                "          <span>policy.matchingDegree </span>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "          <span>政策申报时间</span>\n" +
                "          <span>policy.declarationTime </span>\n" +
                "        </div>\n" +
                "      </div>\n";
    }

    public static String getContent2() {
        return  "<div class=\"info-li\">\n" +
                "<p><strong>  政策编号：</strong>FWD202406071400001</p>\n" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>企业名称：龙眼风尚网络科技公司</th>\n" +
                "            <th>行业类型：互联网</th>\n" +
                "        </tr>\n" +
                "    </table>" +
                "</div>\n";
    }

    public static String readTxtFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 逐行读取文件内容
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        toPdf();
    }
}
