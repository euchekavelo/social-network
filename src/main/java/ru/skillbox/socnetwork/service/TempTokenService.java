package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.TempToken;
import ru.skillbox.socnetwork.repository.TempTokenRepository;

@Service
@RequiredArgsConstructor
public class TempTokenService {

  private final TempTokenRepository tempTokenRepository;

  public TempToken getToken(String token) {
    return tempTokenRepository.getToken(token);
  }

  public void deleteToken(String token) {
    tempTokenRepository.deleteToken(token);
  }

  public void addToken(TempToken token) {
    tempTokenRepository.addToken(token);
  }
}
