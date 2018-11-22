FROM docker.elastic.co/beats/filebeat:6.2.2
COPY ./filebeat/filebeat.yml /usr/share/filebeat/filebeat.yml
COPY ./filebeat/application.log /usr/share/filebeat/logs/application.log

USER root
RUN chmod go-w /usr/share/filebeat/filebeat.yml
USER filebeat