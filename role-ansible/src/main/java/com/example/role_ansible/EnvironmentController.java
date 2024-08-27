package com.example.role_ansible;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class EnvironmentController {

    private final EnvironmentService environmentService;

    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }
    @PostMapping("/generate-neutron-sriov")
    public ResponseEntity<String> generateNeutronSriov(
            @RequestParam String roleName,
            @RequestParam String isolCpusList,
            @RequestParam String kernelArgs,
            @RequestParam String novaVcpuPinSet,
            @RequestParam String novaComputeCpuDedicatedSet,
            @RequestParam int novaReservedHostMemory,
            @RequestParam String novaComputeCpuSharedSet,
            @RequestParam String neutronBridgeMappings) {

        environmentService.generateNeutronSriovYaml(
                roleName,
                isolCpusList,
                kernelArgs,
                novaVcpuPinSet,
                novaComputeCpuDedicatedSet,
                novaReservedHostMemory,
                novaComputeCpuSharedSet,
                neutronBridgeMappings
        );

        return ResponseEntity.ok("neutron-sriov.yaml generated successfully!");
    }
}
