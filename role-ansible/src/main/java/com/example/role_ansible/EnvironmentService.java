package com.example.role_ansible;


import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
public class EnvironmentService {

    private static final String MAIN_ENV_PATH = "/path/to/main-env.yaml";
    private static final String IPS_FROM_POOL_PATH = "/path/to/ips-from-pool-all.yaml";
    private static final String NEUTRON_SRIOV_PATH = "/path/to/environments/neutron-sriov.yaml";

    public void generateNeutronSriovYaml(
            String roleName,
            String isolCpusList,
            String kernelArgs,
            String novaVcpuPinSet,
            String novaComputeCpuDedicatedSet,
            int novaReservedHostMemory,
            String novaComputeCpuSharedSet,
            String neutronBridgeMappings) {

        String template = """
                Compute%sParameters:
                  IsolCpusList: "%s"
                  KernelArgs: "%s"
                  NovaVcpuPinSet: "%s"
                  NovaComputeCpuDedicatedSet: "%s"
                  NovaReservedHostMemory: %d
                  NovaComputeCpuSharedSet: "%s"
                  NeutronBridgeMappings:
                    - datacentre:%s
                """;

        String content = String.format(
                template,
                roleName,
                isolCpusList,
                kernelArgs,
                novaVcpuPinSet,
                novaComputeCpuDedicatedSet,
                novaReservedHostMemory,
                novaComputeCpuSharedSet,
                neutronBridgeMappings
        );

        try (FileWriter writer = new FileWriter(NEUTRON_SRIOV_PATH)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}