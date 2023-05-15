package com.sparta.atchaclonecoding.domain.member.email;

import com.sparta.atchaclonecoding.domain.member.dto.EmailRequestDto;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender emailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private MimeMessage createMessage(String receiverEmail)throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();
        ConfirmationToken emailConfirmationToken = ConfirmationToken.createConfirmationToken(receiverEmail);
        confirmationTokenRepository.save(emailConfirmationToken);

        message.addRecipients(RecipientType.TO, receiverEmail);//보내는 대상
        message.setSubject("[앗챠!] 새로운 비밀번호를 설정해 주세요.");//제목

        String msgg="";
        msgg+= "<div style=\"padding: 26px 18px;\">";
        msgg+= "<img src=\"https://within-s3-bucket.s3.ap-northeast-2.amazonaws.com/%EA%B7%B8%EB%A6%BC1.png\" style=\"width: 105px; height: 31px;\" loading=\"lazy\">";
        msgg+= "<h1 style=\"margin-top: 23px; margin-bottom: 9px; color: #222222; font-size: 19px; line-height: 25px; letter-spacing: -0.27px;\">새 비밀번호 설정</h1>";
        msgg+= "<div style=\"margin-top: 7px; margin-bottom: 22px; color: #222222;\">";
        msgg+= "<p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 8px 0 0;\">안녕하세요, 앗챠!입니다.</p>";
        msgg+= "<p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 8px 0 0;\">아래 버튼을 눌러 새 비밀번호를 설정해 주세요.</p>";
        msgg+= "<p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 8px 0 0;\">";
        msgg+= "<a href=\"http://localhost:8080/atcha/members/confirm-email?token="+emailConfirmationToken.getId()+"\" style=\"text-decoration: none; color: white; display: inline-block; font-size: 15px; font-weight: 500; font-stretch: normal; font-style: normal; line-: normal; letter-spacing: normal; border-radius: 2px; background-color: #141517; margin: 24px 0 19px; padding: 11px 6px;\" rel=\"noreferrer noopener\" target=\"_blank\">비밀번호 변경하기</a>";
        msgg+= "</p>";
        msgg+= "<p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 20px 0;\">";
        msgg+= "감사합니다.<br>";
        msgg+= "앗챠! 팀 드림";
        msgg+= "</p>";
        msgg+= "<hr style=\"display: block; height: 1px; background-color: #ebebeb; margin: 14px 0; padding: 0; border-width: 0;\">";
        msgg+= "<div>";
        msgg+= "<div>";
        msgg+= "<p style=\"margin-block: 0; margin-inline: 0; font-weight: normal; font-size: 14px; font-stretch: normal; font-style: normal; line-height: 1.43; letter-spacing: normal; color: #8a8a8a; margin: 5px 0 0;\">본 메일은 발신전용 메일로 회신되지 않습니다. 본 메일과 관련되어 궁금하신 점이나 불편한 사항은 고객센터에 문의해 주시기 바랍니다.</p>";
        msgg+= "</div>";
        msgg+= "<div>";
        msgg+= "<p style=\"margin-block: 0; margin-inline: 0; font-weight: normal; font-size: 14px; font-stretch: normal; font-style: normal; line-height: 1.43; letter-spacing: normal; color: #8a8a8a; margin: 5px 0 0;\">주식회사 앗챠! | 허리도 가늘군 만지면 부서지리 343, 3층 | cs@watcha.co.kr<br>";
        msgg+= "전화번호: 02-777-9999 | 통신판매업 신고번호: 제 2019-아몰랑-0965호<br>";
        msgg+= "Copyright © 2023 by <b>Atcha, Inc.</b> All rights reserved.</p>";
        msgg+= "</div>";
        msgg+= "</div>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("dev.dunkin00@gmail.com","Atcha"));

        return message;
    }

    @Override
    public String sendSimpleMessage(EmailRequestDto requestDto)throws Exception {

        MimeMessage message = createMessage(requestDto.getEmail());
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        return "이메일 보내기 성공";
    }
}
