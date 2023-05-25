cd ..\api-gateway\build\libs\
W:\jdk-17.0.6\bin\java  -jar -Djarmode=layertools api-gateway-0.0.1-SNAPSHOT.jar extract
cd ..\..\..\
cd .\scripts

cd ..\auth-service\build\libs\
W:\jdk-17.0.6\bin\java  -jar -Djarmode=layertools auth-service-0.0.1-SNAPSHOT.jar extract
cd ..\..\..\
cd .\scripts

cd ..\eureka-server\build\libs\
W:\jdk-17.0.6\bin\java  -jar -Djarmode=layertools eureka-server-0.0.1.jar extract
cd ..\..\..\
cd .\scripts

cd ..\task-manager\build\libs\
W:\jdk-17.0.6\bin\java  -jar -Djarmode=layertools task-manager-0.0.1-SNAPSHOT.jar extract
cd ..\..\..\
cd .\scripts


