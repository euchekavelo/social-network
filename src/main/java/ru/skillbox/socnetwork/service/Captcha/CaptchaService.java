package ru.skillbox.socnetwork.service.Captcha;

import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rqdto.CaptchaDto;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;

import java.util.HashMap;

@Service
public class CaptchaService {

    private static final HashMap<Integer, CaptchaDto> captchaDtoHashMap = new HashMap<>();

    public void addCaptcha(CaptchaDto captchaDto) {
        captchaDtoHashMap.put(captchaDto.getId(), captchaDto);
    }

    public void removeCaptcha(int id) {
        captchaDtoHashMap.remove(id);
    }

    public boolean isCorrectCode(RegisterDto registerDto) {
        if (captchaDtoHashMap.isEmpty()) {
            return false;
        }
        CaptchaDto captchaDto = captchaDtoHashMap.get(registerDto.getCodeId());
        return captchaDto.getHidden().equals(registerDto.getEmail());
    }
}
