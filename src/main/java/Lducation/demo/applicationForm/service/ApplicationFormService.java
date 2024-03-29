package Lducation.demo.applicationForm.service;

import Lducation.demo.applicationForm.dto.ApplicationFormDto;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ApplicationFormService {
    private final ResourceLoader resourceLoader;
    public String AddTextToPdf(ApplicationFormDto applicationFormDto) throws IOException, URISyntaxException {

        Module module = getClass().getModule();
        InputStream inputStream = module.getResourceAsStream("static/appform4.pdf");

        /*Module module1 = getClass().getModule();
        InputStream inputStream1 = module1.getResourceAsStream("static/appform4.pdf");

        OutputStream outputStream = new FileOutputStream("appform4_saving.pdf");
        byte[] buffer = new byte[1024]; // 버퍼 생성

        int bytesRead;
        while ((bytesRead = inputStream1.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead); // 읽은 데이터를 출력
        }
        inputStream1.close();*/

        ClassLoader classLoader = ApplicationFormService.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("static/appform4_saving.pdf");
        log.info("URL: " + resourceUrl);
        File file = new File(resourceUrl.getFile());
        log.info("FilePath : " + file.getAbsolutePath());
        String filePath = file.getCanonicalPath();

//        PDDocument document = PDDocument.load(file);
        PDDocument document = PDDocument.load(inputStream);

        PDPage page = document.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(document, page,
                PDPageContentStream.AppendMode.APPEND, true);
        contentStream.beginText();

        Module module2 = getClass().getModule();
        InputStream inputStream2 = module2.getResourceAsStream("static/AppleSDGothicNeoT.ttf");

//        PDFont font = PDType0Font.load(document, new ClassPathResource("static/Apple_산돌고딕_Neo/AppleSDGothicNeoT.ttf").getFile());
        PDFont font = PDType0Font.load(document,inputStream2);
        contentStream.setFont(font,10);

        contentStream.newLineAtOffset(173, 718);
        String name = applicationFormDto.getName();
        contentStream.showText(name);

        contentStream.newLineAtOffset(0, -20);
        String birthday = applicationFormDto.getBirthday();
        contentStream.showText(birthday);

        contentStream.setFont(font, 8);
        contentStream.newLineAtOffset(0, -34);
        String phone = applicationFormDto.getPhone();
        contentStream.showText(phone);

        contentStream.newLineAtOffset(0, -23);
        String address = applicationFormDto.getAddress();
        contentStream.showText(address);

        contentStream.newLineAtOffset(0, -15);
        String addressDetail = applicationFormDto.getAddress_detail();
        contentStream.showText(addressDetail);

        contentStream.setFont(font, 5);
        String sex = applicationFormDto.getSex();
        if (sex.equals("male")) {
            contentStream.newLineAtOffset((float) -2.8, 57);
        } else {
            contentStream.newLineAtOffset((float) 18, 57);
        }
        String dot = "●";
        contentStream.showText(dot);

        contentStream.endText();
        contentStream.fill();
        contentStream.stroke();

        contentStream.close();

//        File savefile = new ClassPathResource("static/appform4.pdf").getFile();

//        document.save(new File("/home/ec2-user"));
//        document.saveIncremental(outputStream);
        document.save(filePath);
        document.close();
        inputStream.close();
//        outputStream.close();
        inputStream2.close();

        return filePath;
    }

    public String DateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

}
