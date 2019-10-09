package io.mohami.test.backend.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 *
 * @author andrey
 */
@Component
public class StorageService {
    Map<String, Map<String, Long>> cache = new ConcurrentHashMap<>();
    public void save(String filename, Map<String, Long> data) {
        cache.put(filename, data);
    }
    public Map<String, Long> getByFilename(String filename) {
        return cache.get(filename);
    }
    
    public Collection<Map<String, Long>> getAll() {
        return cache.values();
    }
}
