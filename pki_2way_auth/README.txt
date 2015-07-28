#############
### BUILD ###
#############
cp Server.java /tmp
cp Main.java /tmp
javac /tmp/Server.java
javac /tmp/Main.java

##############
### SERVER ###
##############
keytool -list -storepass changeit -keystore /tmp/server.jks
keytool -list -storepass changeit -keystore /tmp/server-trust.jks
# keyStore must contain server private key, server cert and client cert
# trustStore must contain the trusted CA cert (required for two-way auth)
java -Djavax.net.debug=ssl,handshake -Djavax.net.ssl.keyStore=/tmp/server.jks \
     -Djavax.net.ssl.keyStorePassword=changeit \
     -Djavax.net.ssl.trustStore=/tmp/server-trust.jks -cp /tmp Server 9999 &

# one-time
bash setup-ca.sh
rm -f /tmp/server.jks /tmp/server-trust.jks
keytool -import -trustcacerts -alias root -file ${CAROOT}/ca.crt -keystore /tmp/server-trust.jks -storepass changeit
keytool -import -trustcacerts -alias root -file ${CAROOT}/ca.crt -keystore /tmp/server.jks -storepass changeit
openssl pkcs12 -export -inkey ${CAROOT}/ca.key -in ${CAROOT}/ca.crt -out /tmp/server.p12
keytool -importkeystore -srckeystore /tmp/server.p12 -srcstoretype pkcs12 -destkeystore /tmp/server.jks -deststoretype jks -storepass changeit
rm /tmp/server.p12

openssl genrsa -out client.key 1024
openssl req -new -key client.key -out client.csr # ANSWER ALL QUESTIONS!!! CN=localhost
export CAROOT=/tmp/fake-ca
openssl ca -config ${CAROOT}/ca.conf -in client.csr -cert ${CAROOT}/ca.crt -keyfile ${CAROOT}/ca.key -out client.crt
keytool -import -trustcacerts -alias client -file client.crt -keystore /tmp/server.jks -storepass changeit


###############
### CLIENTS ###
###############
# keyStore must contain client private key and cert (combined)
# trustStore must contain server public cert
keytool -list -storepass changeit -keystore /tmp/client.jks
keytool -list -storepass changeit -keystore /tmp/client-trust.jks
java -Djavax.net.debug=ssl,handshake -Djavax.net.ssl.trustStore=/tmp/client-trust.jks -Djavax.net.ssl.keyStorePassword=changeit -Djavax.net.ssl.keyStore=/tmp/client.jks -cp /tmp Main localhost 9999

openssl s_client -msg -tls1 -connect localhost:9999 -CApath $CAROOT -CAfile ca.crt -prexit -cert client.crt -key client.key

# one-time (depends on key generation from before, see "SERVER")
openssl pkcs12 -export -inkey client.key -in client.crt -out /tmp/client.p12
keytool -importkeystore -srckeystore /tmp/client.p12 -srcstoretype pkcs12 -destkeystore /tmp/client.jks -deststoretype jks -storepass changeit
rm /tmp/client.p12
# keytool -importcert -trustcacerts -keystore /tmp/client.jks -storetype jks -storepass changeit -file client.crt -alias client-cert
keytool -importcert -trustcacerts -keystore /tmp/client-trust.jks -storetype jks -storepass changeit -file ${CAROOT}/ca.crt -alias server-cert
