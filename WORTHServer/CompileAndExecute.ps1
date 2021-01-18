javac -d ./bin -Xlint  -cp ".;./lib/gson-2.8.6.jar;./lib/Utility.jar;" ./src/*.java
cd bin
java -cp ".;../lib/gson-2.8.6.jar;../lib/Utility.jar;" ServerMain

