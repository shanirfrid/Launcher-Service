package com.shanir.launcherservice.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LauncherClientService {
    private final static String CLIENT_FILENAME_REGEX =
            "launcher-client-[0-9]+\\.[0-9]+\\.[0-9]+\\.zip";
    private final static String CLIENTS_DIR = "c:/clients/";
    private final static String FAILURE_MESSAGE = "No client version was found";

    public String extractVersion(String fileName){
        return fileName.split("-")[2].split("\\.zip")[0];
    }

    public String getLatestVersion() {
        File clientsDir = new File(CLIENTS_DIR);
        File[] files = clientsDir.listFiles();

        if (files == null) return FAILURE_MESSAGE;

        List<String> versions = Arrays.stream(files)
                .map(File::getName)
                .filter(fileName -> fileName.matches(CLIENT_FILENAME_REGEX))
                .map(this::extractVersion)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        return versions.isEmpty() ? FAILURE_MESSAGE : versions.get(0);
    }

    public String getPath(String version) {
        return String.format("%slauncher-client-%s.zip", CLIENTS_DIR, version);
    }

    public boolean versionExists(String version) {
        File launcherFile = new File(getPath(version));

        return launcherFile.exists();
    }
}
