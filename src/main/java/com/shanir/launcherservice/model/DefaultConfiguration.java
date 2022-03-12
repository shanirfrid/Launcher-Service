package com.shanir.launcherservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "defaultConfiguration")
public class DefaultConfiguration {
    @Id
    private String Id;
    private boolean isCritical;
    private String version;
    private boolean isOlympus;
    private String webAddress;
    private Map<String, List<String>> baseToProxyAddress;

    public DefaultConfiguration(boolean isCritical, String version,
                                boolean isOlympus, String webAddress,
                                Map<String, List<String>> baseToProxyAddress) {
        this.isCritical = isCritical;
        this.version = version;
        this.isOlympus = isOlympus;
        this.webAddress = webAddress;
        this.baseToProxyAddress = baseToProxyAddress;
    }

    public Configuration getConfiguration(String proxyBase) {
        return new Configuration(this.version, this.isOlympus, this.webAddress,
                this.baseToProxyAddress.get(proxyBase));
    }
}
