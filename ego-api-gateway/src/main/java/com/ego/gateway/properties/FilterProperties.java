package com.ego.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/21 18:42
 * @Description
 */
@Data
@ConfigurationProperties(prefix = "ego.filter")
public class FilterProperties {
    private String pref;
    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

}
