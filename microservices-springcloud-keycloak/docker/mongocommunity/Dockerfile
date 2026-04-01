FROM mongo
ENV MONGO_INITDB_DATABASE db_community
ENV MONGO_INITDB_ROOT_USERNAME root
ENV MONGO_INITDB_ROOT_PASSWORD root

ADD ./init/init.js /docker-entrypoint-initdb.d/
