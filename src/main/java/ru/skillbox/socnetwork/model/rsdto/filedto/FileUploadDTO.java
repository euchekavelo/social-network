package ru.skillbox.socnetwork.model.rsdto.filedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.Person;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class FileUploadDTO {
  private Integer id;
  private Integer ownerID;
  private String fileName;
  private String relativeFilePath;
  private String rawFileURL;
  private String fileFormat;
  private Long bytes;
  private FileType fileType;
  private Long createdAt;

  public FileUploadDTO(Person person, File file){
    id = 1;
    ownerID = person.getId();
    this.fileName = file.getName();
    this.relativeFilePath = file.getPath();
    this.rawFileURL = file.getPath();
    fileFormat = getFileFormat(file.getPath());
    this.bytes = file.getTotalSpace();
    fileType = FileType.IMAGE;
    createdAt = file.lastModified();
  }

  private String getFileFormat(String name){
    Pattern pattern = Pattern.compile(".*\\.([A-z]*)\\?.*");
    Matcher matcher = pattern.matcher(name);
    return matcher.group(0);
  }
}
