javac -d ./bin -cp ".;./lib/gson-2.8.6.jar;./lib/Serializers.jar;" ./src/*.java
cd bin
java -cp ".;./lib/gson-2.8.6.jar;./lib/Serializers.jar;" ServerMain

