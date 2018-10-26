FROM sonarqube:7.1
RUN curl -s https://api.github.com/repos/kwoding/sonar-webdriver-plugin/releases/latest \
| grep browser_download_url \
| cut -d '"' -f 4 \
| wget -qi -
RUN mv sonar-webdriver-plugin-*.jar /opt/sonarqube/extensions/plugins/