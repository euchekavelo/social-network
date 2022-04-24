package ru.skillbox.socnetwork.service.storage;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import java.io.BufferedInputStream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.filedto.FileUploadDTO;
import ru.skillbox.socnetwork.repository.PersonRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

@Service
@RequiredArgsConstructor
public class StorageService {

  private static String token = "";
  private static final DbxRequestConfig config = DbxRequestConfig.newBuilder("socNet").build();
  private static DbxClientV2 client = new DbxClientV2(config, token);
  private final PersonRepository personRepository;

  public String getDefaultProfileImage() throws DbxException {
    return client.sharing().listSharedLinksBuilder().withPath("/default.jpg").start()
        .getLinks().get(0).getUrl().replace("dl=0", "raw=1");
  }

  public FileUploadDTO uploadFile(MultipartFile file) {

    Person person = null;
    FileMetadata fileMetadata = null;
    try {
      InputStream stream = new BufferedInputStream(file.getInputStream());
      String fileName = generateName(file.getOriginalFilename());

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
      person = personRepository.getByEmail(securityUser.getUsername());

      deleteFile(getRelativePath(person.getPhoto()));
      person.setPhoto(getAbsolutePath("/" + fileName));
      personRepository.updatePhoto(person);
      fileMetadata = client.files().uploadBuilder("/" + fileName)
          .withMode(WriteMode.ADD).uploadAndFinish(stream);
      if (client.sharing().listSharedLinksBuilder().withPath("/" + fileName).start()
          .getLinks().isEmpty()) {
        client.sharing().createSharedLinkWithSettings(fileMetadata.getPathDisplay());
      }
    } catch (DbxException | IOException e) {
      e.printStackTrace();
    }
    return new FileUploadDTO(person, fileMetadata);
  }

  public void deleteFile(String path) throws DbxException {
    if (!path.equals("/default.jpg")) {
      client.files().deleteV2(path);
    }
  }

  //Temporary entry to update dropbox auth token (currently expires in 4 hours)
  public void updateToken(String newToken) {
    token = newToken;
    client = new DbxClientV2(config, token);
  }

  private String getRelativePath(String path) {
    Pattern pattern = Pattern.compile(".*(/\\w*\\.[A-z]*)\\?raw=1");
    Matcher matcher = pattern.matcher(path);
    return (matcher.find()) ? matcher.group(1) : "";
  }

  public String getAbsolutePath(String path) throws DbxException {
    return client.sharing().listSharedLinksBuilder().withPath(path).start()
        .getLinks().get(0).getUrl().replace("dl=0", "raw=1");
  }

  private String generateName(String name) {
    RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(65, 90).build();
    Pattern pattern = Pattern.compile(".*(\\.[A-z]*)$");
    Matcher matcher = pattern.matcher(name);
    String format = (matcher.find()) ? matcher.group(1) : "";
    return generator.generate(10) + format;
  }
}
