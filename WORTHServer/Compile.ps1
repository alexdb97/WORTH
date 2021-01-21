javac  ./Utility/Utility.java
jar cf Utility.jar ./Utility/Utility.class
move-item ./Utility.jar ./lib
javac -d ./bin -Xlint  -cp ".;./lib/gson-2.8.6.jar;./lib/Utility.jar;" ./src/*.java
