@echo off
echo Downloading libraries...
curl -L -o lib/flatlaf-3.2.1.jar https://repo1.maven.org/maven2/com/formdev/flatlaf/3.2.1/flatlaf-3.2.1.jar
curl -L -o lib/mysql-connector-java-8.0.33.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar
curl -L -o lib/jasperreports-6.20.0.jar https://repo1.maven.org/maven2/net/sf/jasperreports/jasperreports/6.20.0/jasperreports-6.20.0.jar
curl -L -o lib/jasperreports-fonts-6.20.0.jar https://repo1.maven.org/maven2/net/sf/jasperreports/jasperreports-fonts/6.20.0/jasperreports-fonts-6.20.0.jar
curl -L -o lib/itext-2.1.7.jar https://repo1.maven.org/maven2/com/lowagie/itext/2.1.7/itext-2.1.7.jar
curl -L -o lib/commons-io-2.11.0.jar https://repo1.maven.org/maven2/commons-io/commons-io/2.11.0/commons-io-2.11.0.jar
curl -L -o lib/commons-lang3-3.12.0.jar https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar
echo Download complete!
pause