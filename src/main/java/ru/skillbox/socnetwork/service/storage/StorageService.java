package ru.skillbox.socnetwork.service.storage;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.filedto.FileUploadDTO;
import ru.skillbox.socnetwork.repository.PersonRepository;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.LocalFileService;
import ru.skillbox.socnetwork.service.storage.StorageCache;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@DebugLogs
public class StorageService {

  @Value("${skillbox.app.logRootPath}")
  private String localRootPath;
  private static String token = "";
  private static final DbxRequestConfig config = DbxRequestConfig.newBuilder("socNet").build();
  private static DbxClientV2 client = new DbxClientV2(config, token);
  private final PersonRepository personRepository;
  private final LocalFileService localFileService;
  private final StorageCache cache;

  public String getDefaultProfileImage(){
    return cache.getLink(StorageCache.DEFAULT);
  }

  public String getDeletedProfileImage() {
    return cache.getLink(StorageCache.DELETED);
  }

  @Scheduled(cron = "${skillbox.app.cronUploadLogFiles}")
  public void uploadLogFiles() throws IOException, DbxException {
    List<File> logFiles = localFileService.getAllFilesInADirectory(localRootPath);

    for (File file : logFiles) {
      try (InputStream in = new FileInputStream(file)) {
        String abstractFilePath = file.getPath().replaceAll("\\\\", "/");
        client.files().uploadBuilder("/" + abstractFilePath).withMode(WriteMode.OVERWRITE).uploadAndFinish(in);
      }
    }

    localFileService.deleteLocalFilesInADirectory(localRootPath);
  }

  @Scheduled(cron = "${skillbox.app.cronDeleteLogFolderInRemoteStorage}")
  public void deleteLogFolderInRemoteStorage() throws DbxException {
    deleteFile("/" + localRootPath);
  }

  public FileUploadDTO uploadFile(MultipartFile file) {
    Person person = null;
    FileMetadata fileMetadata = null;
    try {
      InputStream stream = new BufferedInputStream(file.getInputStream());

      //Generate new random file name
      String fileName = "/" + generateName(file.getOriginalFilename());

      //Get current user
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
      person = personRepository.getByEmail(securityUser.getUsername());

      deleteFile(getRelativePath(person.getPhoto()));

      fileMetadata = client.files().uploadBuilder(fileName)
              .withMode(WriteMode.ADD).uploadAndFinish(stream);

      //Share image if not shared yet
      if (client.sharing().listSharedLinksBuilder().withPath(fileName).start()
              .getLinks().isEmpty()) {
        client.sharing().createSharedLinkWithSettings(fileMetadata.getPathDisplay());
      }

      person.setPhoto(getAbsolutePath(fileName));
      personRepository.updatePhoto(person);
    } catch (DbxException | IOException e) {
      e.printStackTrace();
    }
    return new FileUploadDTO(person, fileMetadata);
  }

  public void deleteFile(String path) throws DbxException {
    if (!path.equals(StorageCache.DEFAULT)) {
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