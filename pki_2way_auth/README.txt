cp Server.java /tmp
cp Main.java /tmp
javac /tmp/Server.java
javac /tmp/Main.java

##############
### SERVER ###
##############
rm -f /tmp/certs.jks && keytool -storepass changeit -keystore /tmp/certs.jks -genkey -alias foo -dname 'CN=foo.com,L=Franklin,ST=TN,C=US'
keytool -list -storepass changeit -keystore /tmp/certs.jks
java -Djavax.net.ssl.keyStore=/tmp/certs.jks \
     -Djavax.net.ssl.keyStorePassword=changeit -cp /tmp Server 9999 &

##############
### SERVER ###
##############
cat <<EOF >/tmp/policy.all
grant {
        permission java.security.AllPermission;
};
EOF
keytool -keystore /tmp/certs.jks -genkeypair -alias myalias -dname 'CN=khallware.com,L=Franklin,ST=TN,C=US'
keytool -import -file ~/project/homepage/khallware_1_0_dat/sub.class1.server.ca.pem -alias khallware -keystore /tmp/certs.jks
keytool -list -storepass changeit -keystore /tmp/certs.jks
java -Djava.security.policy=/tmp/policy.all -cp /tmp Server 9999 /tmp/certs.jks &


###############
### CLIENTS ###
###############
openssl s_client -connect localhost:9999 -tls1

keytool -export -alias myalias -keystore /tmp/certs.jks -file /tmp/pub.cert
keytool -import -alias myalias -keystore /tmp/pub.jks -file /tmp/pub.cert
rm /tmp/pub.cert
java -Djavax.net.ssl.trustStore=/tmp/pub.jks -cp /tmp Main localhost 9999
