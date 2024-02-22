package surveasy.global.common.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EmailUtils {

    private final JavaMailSender javaMailSender;
    private final static LocalDate NOW = LocalDate.now();
    private final static String FILE_LOCATION = "output" + File.separator;
    private final static String TITLE_CSV = "[" + NOW + "] 정산 CSV 파일";

    public void sendCsvMail() {
        javaMailSender.send(createCsvMail());
    }

    private MimeMessage createCsvMail() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        FileSystemResource fileSystemResource = new FileSystemResource(new File(FILE_LOCATION + NOW + ".csv"));

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo("official@gosurveasy.com");
            mimeMessageHelper.setSubject(TITLE_CSV);
            mimeMessageHelper.setText("정산 완료 !");
            mimeMessageHelper.addAttachment(MimeUtility.encodeText(String.valueOf(NOW), "UTF-8", "B"), fileSystemResource);
            return mimeMessage;
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
