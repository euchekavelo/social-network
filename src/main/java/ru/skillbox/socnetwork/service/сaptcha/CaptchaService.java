package ru.skillbox.socnetwork.service.сaptcha;

import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rqdto.CaptchaDto;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.service.Constants;

import java.util.HashMap;

@Service
public class CaptchaService {

    private static final HashMap<Long, CaptchaDto> captchaDtoHashMap = new HashMap<>();

    public void addCaptcha(CaptchaDto captchaDto) {
        captchaDtoHashMap.put(captchaDto.getId(), captchaDto);
    }

    public void removeCaptcha(Long id) {
        captchaDtoHashMap.remove(id);
    }

    public boolean isCorrectCode(RegisterDto registerDto) {
        if (captchaDtoHashMap.isEmpty()) {
            return false;
        }
        CaptchaDto captchaDto = captchaDtoHashMap.get(registerDto.getCodeId());
        if (captchaDtoHashMap.size() > 50) {
            this.clearCaptchaMap();
        }
        return captchaDto.getHidden().equals(registerDto.getCode());
    }

    private void clearCaptchaMap() {
        HashMap<Long, CaptchaDto> map = new HashMap<>();
        captchaDtoHashMap.forEach((k, v) -> {
            if (k > System.currentTimeMillis() + Constants.FIFTY_SECONDS_IN_MILLIS) {
                map.put(k, v);
            }
        });
        captchaDtoHashMap.clear();
        captchaDtoHashMap.putAll(map);
    }
}
