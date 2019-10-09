package io.mohami.test.backend.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author andrey
 */
@Component
public class StatsService {
    private final StorageService storageService;
    private final ExecutorService executorService;

    @Autowired
    public StatsService(StorageService storageService, ExecutorService executorService) {
        this.storageService = storageService;
        this.executorService = executorService;
    }

    public void store(File file, String filename) {
        executorService.submit(()->this.parseFile(file, filename));
    }
    
    public Map<String, Long> getByFilename(String filename) {
        return storageService.getByFilename(filename);
    }
    public Map<String, Long> getAll() {
        return storageService.getAll().stream().collect(HashMap::new,
         (u,  t) -> {
            t.entrySet().stream().forEach(e -> {
                Long v = u.getOrDefault(e.getKey(), 0L);
                v += e.getValue();
                u.put(e.getKey(), v);
            });
        }, (u, t) -> {});
    }

    private void parseFile(File file, String filename) {
        Map<String, Long> result = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\W");
                Arrays.stream(parts).forEach(s->{
                    if (StringUtils.isEmpty(s)) {
                        return;
                    }
                    Long v = result.getOrDefault(s, 0L);
                    v++;
                    result.put(s, v);
                });
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            file.delete();
        }

        storageService.save(filename, result);
    }
}
