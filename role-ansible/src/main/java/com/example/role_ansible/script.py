import sys
import yaml

def load_main_env_config():
    with open('/path/to/main-env.yaml', 'r') as file:
        return yaml.safe_load(file)

def modify_main_env_yaml(role_name):
    with open('/path/to/main-env.yaml', 'r') as file:
        lines = file.readlines()

    with open('/path/to/main-env.yaml', 'w') as file:
        for line in lines:
            file.write(line.replace('**RoleName**', role_name))

def append_to_ips_yaml(role_name):
    new_entries = {
        f"OS::TripleO::{role_name}::Ports::ExternalPort": "/usr/share/openstack-tripleo-heat-templates/network/ports/external_from_pool.yaml",
        f"OS::TripleO::{role_name}::Ports::InternalApiPort": "/usr/share/openstack-tripleo-heat-templates/network/ports/internal_api.yaml",
        f"OS::TripleO::{role_name}::Ports::StoragePort": "/usr/share/openstack-tripleo-heat-templates/network/ports/storage.yaml",
        f"OS::TripleO::{role_name}::Ports::StorageMgmtPort": "/usr/share/openstack-tripleo-heat-templates/network/ports/noop.yaml",
        f"OS::TripleO::{role_name}::Ports::TenantPort": "/usr/share/openstack-tripleo-heat-templates/network/ports/tenant.yaml",
        f"{role_name}IPs": {"external": []}
    }

    try:
        with open('/path/to/ips-from-pool-all.yaml', 'r') as file:
            ips_config = yaml.safe_load(file) or []
    except FileNotFoundError:
        ips_config = []

    ips_config.append(new_entries)

    with open('/path/to/ips-from-pool-all.yaml', 'w') as file:
        yaml.dump(ips_config, file, default_flow_style=False)

def main(role_name):
    modify_main_env_yaml(role_name)
    append_to_ips_yaml(role_name)

if __name__ == "__main__":
    if len(sys.argv) > 1:
        role_name = sys.argv[1]
        main(role_name)
    else:
        print("No role name provided")
