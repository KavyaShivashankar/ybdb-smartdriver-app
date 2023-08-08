# Create 3 node cluster. 1 region, 3 zone, 1 node per zone

export PATH=$PATH:<path of yugabyte>

yugabyted start --advertise_address=127.0.0.1 --cloud_location=aws.us-east-2.us-east-2a --base_dir=/Users/kshivashankar/Documents/keys/connection-pool-test/ybdb/yugabyte-2.19.0.0/var/node1 --master_flags=ysql_enable_packed_row=true --tserver_flags=ysql_enable_packed_row=true,ysql_beta_features=true,yb_enable_read_committed_isolation=true

yugabyted start --join=127.0.0.1 --advertise_address=127.0.0.2 --cloud_location=aws.us-east-2.us-east-2b --base_dir=/Users/kshivashankar/Documents/keys/connection-pool-test/ybdb/yugabyte-2.19.0.0/var/node2 --master_flags=ysql_enable_packed_row=true --tserver_flags=ysql_enable_packed_row=true,ysql_beta_features=true,yb_enable_read_committed_isolation=true

yugabyted start --join=127.0.0.1 --advertise_address=127.0.0.3 --cloud_location=aws.us-east-2.us-east-2c --base_dir=/Users/kshivashankar/Documents/keys/connection-pool-test/ybdb/yugabyte-2.19.0.0/var/node3 --master_flags=ysql_enable_packed_row=true --tserver_flags=ysql_enable_packed_row=true,ysql_beta_features=true,yb_enable_read_committed_isolation=true
