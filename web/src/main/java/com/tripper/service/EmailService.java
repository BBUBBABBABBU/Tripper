package com.tripper.service;

import com.tripper.domain.user.User;
import com.tripper.dto.request.user.CreateEmailDto;
import com.tripper.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

    private final UserRepository userRepository;
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "tripper.mailservice@gmail.com";

    /**
     * 전송할 메일 생성 + 이메일 인증코드 저장 하는 함수
     */
    public CreateEmailDto createMailAndChangeAuth(String email, String memId) {

        String authCode = getTempCode();
        CreateEmailDto createEmailDto = new CreateEmailDto();
        createEmailDto.setAddress(email);
        createEmailDto.setTitle(memId+"님의 회원 인증 메일입니다.");
        createEmailDto.setMessage("인증 코드는" + authCode + " 입니다.");
        updateEmailAuthCode(authCode, memId);

        return createEmailDto;
    }

    /**
     * 회원 db의 이메일 인증코드 저장하는 함수
     */
    @Transactional
    public void updateEmailAuthCode(String authCode, String memId){

        /* 엔티티 조회 */
        User user = userRepository.findByMemId(memId);

        /* 인증코드 저장 */
        user.setEmailAuthCode(authCode);
        userRepository.save(user);

    }

    /**
     * 인증 코드 생성해주는 함수
     */
    public String getTempCode(){

        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }

        return str;

    }

    /**
     * 이메일 보내는 함수
     */
    public void mailSend(CreateEmailDto createEmailDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(createEmailDto.getAddress());
        message.setFrom(EmailService.FROM_ADDRESS);
        message.setSubject(createEmailDto.getTitle());
        message.setText(createEmailDto.getMessage());

        mailSender.send(message);

    }

    /**
     * 회원이 입력한 인증 코드와 db의 인증코드가 일치하는지 확인하는 함수
     */
    public Boolean checkEmailAuthCode(String authCode, String memId) {

        String dbAuthCode = userRepository.getEmailAuthCode(memId);

        /* 일치 여부에 따라 return */
        return authCode.equals(dbAuthCode);

    }
}
