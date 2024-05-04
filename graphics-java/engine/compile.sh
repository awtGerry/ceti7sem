javac -d ./bin ./src/*.java ./src/algorithms/*.java ./src/pacman/*.java
# create variable to store the name of the file
name=$1
# Make only first letter uppercase
name=$(echo $name | tr '[:lower:]' '[:upper:]')
# Make the rest of the letters lowercase
name=$(echo ${name:0:1}$(echo $name | cut -c 2- | tr '[:upper:]' '[:lower:]'))
# Run the program
java -cp ./bin $name
