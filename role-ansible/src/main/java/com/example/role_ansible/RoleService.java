package com.example.role_ansible;

import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Service
public class RoleService {

    public void processRoleName(String roleName) {
        Map<String, Object> mainEnvConfig = loadMainEnvConfig();
        if (mainEnvConfig != null) {
            // You can use the loaded configuration as needed
            System.out.println("Loaded main-env.yaml configuration: " + mainEnvConfig);
        }
        runAnsiblePlaybook(roleName);
    }

    private void runAnsiblePlaybook(String roleName) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "ansible-playbook", "com/example/role_ansible/runpyscript.yaml", "-e", "role_name=" + roleName);
            processBuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> loadMainEnvConfig() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("main-env.yaml")) {
            return yaml.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}