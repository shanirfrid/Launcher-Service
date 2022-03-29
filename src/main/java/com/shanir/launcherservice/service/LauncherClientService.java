package com.shanir.launcherservice.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LauncherClientService {
    private final static String CLIENT_FILENAME_REGEX =
            "launcher-client-[0-9]+\\.[0-9]+\\.[0-9]+\\.zip";
    private final static String CLIENTS_DIR = "/var/clients/";

    public String extractVersion(String fileName){
        return fileName.split("-")[2].split("\\.zip")[0];
    }

    public String getLatestVersion() throws Exception {
        Exception failureException = new Exception("No clients found");
        File clientsDir = new File(CLIENTS_DIR);
        File[] files = clientsDir.listFiles();

        if (files == null) throw failureException;

        List<String> versions = Arrays.stream(files)
                .map(File::getName)
                .filter(fileName -> fileName.matches(CLIENT_FILENAME_REGEX))
                .map(this::extractVersion)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        if (versions.isEmpty())
            throw failureException;

        return versions.get(0);
    }

    public String getPath(String version) {
        return String.format("%slauncher-client-%s.zip", CLIENTS_DIR, version);
    }

    public boolean versionExists(String version) {
        File launcherFile = new File(getPath(version));

        return launcherFile.exists();
    }
}
