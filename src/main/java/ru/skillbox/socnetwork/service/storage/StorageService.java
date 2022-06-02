package ru.skillbox.socnetwork.service.storage;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.filedto.FileUploadDTO;
import ru.skillbox.socnetwork.repository.PersonRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.io.*;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@DebugLogs
public class StorageService {

  private static String token = "";
  private static final DbxRequestConfig config = DbxRequestConfig.newBuilder("socNet").build();
  private static DbxClientV2 client = new DbxClientV2(config, token);
  private final PersonRepository personRepository;
  private final StorageCache cache;

  public String getDefaultProfileImage(){
    return cache.getLink(StorageConstants.PHOTO_DEFAULT);
  }

  public String getDeletedProfileImage() {
    return cache.getLink(StorageConstants.PHOTO_DELETED);
  }

  public FileUploadDTO uploadFile(MultipartFile file) throws IOException, DbxException {
    Person person = null;
    FileMetadata fileMetadata = null;

    //Generate new random file name
    String fileName = "/".concat(generateName(file.getOriginalFilename()));

    File resizedImage = ImageScale.resize(file.getInputStream(), fileName);
    InputStream stream = new FileInputStream(resizedImage);
    fileMetadata = client.files().uploadBuilder(fileName).withMode(WriteMode.ADD).uploadAndFinish(stream);
    stream.close();

    Files.delete(resizedImage.toPath());

    //Share image if not shared yet
    if(client.sharing().listSharedLinksBuilder().withPath(fileName).start().getLinks().isEmpty()) {
        client.sharing().createSharedLinkWithSettings(fileMetadata.getPathDisplay());
    }

    //Get current user
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
    person = personRepository.getByEmail(securityUser.getUsername());

    deleteFile(getRelativePath(person.getPhoto()));

    cache.addLink(fileName, getAbsolutePath(fileName));
    person.setPhoto(getAbsolutePath(fileName));
    personRepository.updatePhoto(person);

    return new FileUploadDTO(person, fileMetadata);
  }

  public void deleteFile(String path) throws DbxException {
    if (!path.equals(StorageConstants.PHOTO_DEFAULT)) {
      client.files().deleteV2(path);
    }
  }

  public static void updateToken(String newToken) {
    token = newToken;
    client = new DbxClientV2(config, token);
  }

  private String getRelativePath(String path) {
    Pattern pattern = Pattern.compile(".*(/\\w*\\.[A-z]*)\\?raw=1");
    Matcher matcher = pattern.matcher(path);
    return (matcher.find()) ? matcher.group(1) : "";
  }

  private String getAbsolutePath(String path) throws DbxException {
    if(cache.isLinkExists(path)){
      return cache.getLink(path);
    }
    return client.sharing().listSharedLinksBuilder().withPath(path).start()
            .getLinks().get(0).getUrl().replace("dl=0", "raw=1");
  }

  private String generateName(String name) {
    RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(65, 90).build();
    Pattern pattern = Pattern.compile(".{1,10}(\\.[A-z]{3,5})$");
    Matcher matcher = pattern.matcher(name);
    String format = (matcher.find()) ? matcher.group(1) : "";
    return generator.generate(10) + format;
  }
}