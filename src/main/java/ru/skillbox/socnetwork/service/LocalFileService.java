package ru.skillbox.socnetwork.service;

import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.logging.DebugLogs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@DebugLogs
public class LocalFileService {

    public List<File> getAllFilesInADirectory(String path) throws IOException {

        List<File> result;
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {
            result = walk
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }
        return result;
    }

    public void deleteLocalFilesInADirectory(String path) throws IOException {

        try (Stream<Path> walk = Files.walk(Paths.get(path))) {
            walk
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
