#JAVA_HOME="/opt/java6/jre"
MEM_ARGS="-Xms256m"

CLASSPATH=""

for i in ../target/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done


for i in ../dependencies/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done

for i in ../conf/*; do
    CLASSPATH=$CLASSPATH:$i
done

CLASSPATH=$CLASSPATH:../conf

echo $CLASSPATH


RUNCMD="${JAVA_HOME}/bin/java -DBitiBoxServer ${MEM_ARGS} -Djava.library.path=/usr/lib/jni -Dlog4j.configuration=file:../conf/log4j.properties -cp ${CLASSPATH} com.mesoft.bitibox.BitiBoxServerStarter"

$RUNCMD &
