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

    public void sendCsvMail() {
        javaMailSender.send(createCsvMail());
    }

    public void sendSurveyCannotMail(String email, String surveyTarget) {
        javaMailSender.send(createSurveyCannotMail(email, surveyTarget));
    }

    private MimeMessage createCsvMail() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        FileSystemResource fileSystemResource = new FileSystemResource(new File("output" + File.separator + LocalDate.now() + ".csv"));

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo("official@gosurveasy.com");
            mimeMessageHelper.setSubject("[" + LocalDate.now() + "] 정산 CSV 파일");
            mimeMessageHelper.setText("정산 완료 !");
            mimeMessageHelper.addAttachment(MimeUtility.encodeText(String.valueOf(LocalDate.now()), "UTF-8", "B"), fileSystemResource);
            return mimeMessage;
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private MimeMessage createSurveyCannotMail(String email, String surveyTarget) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("[서베이지] 설문 검수 결과 안내");
            mimeMessageHelper.setText("안녕하세요, 서베이지입니다.\n" +
                    "\n" +
                    "이번에 주문 넣어주신 설문의 경우,\n" +
                    surveyTarget + "을(를) 대상으로 하고 있는데\n" +
                    "현재 서베이지가 해당 타깃으로 설문 배포가 어렵습니다.\n" +
                    "\n" +
                    "도움을 드리지 못해 죄송합니다.\n" +
                    "계좌 정보 알려주시면 전액 환불해드리겠습니다.\n" +
                    "\n" +
                    "감사합니다.\n" +
                    "서베이지 드림\n" +
                    "\n" +
                    "\n" +
                    "--\n" +
                    "서베이지 Surveasy\n" +
                    "\n" +
                    "E-mail: official@gosurveasy.com | Website: www.gosurveasy.com");
            return mimeMessage;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
