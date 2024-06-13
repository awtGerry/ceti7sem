args="$1"

# Compile the program if args is -c
if [ "$args" == "-c" ]; then
    echo "Compiling..."
    # javac -d bin src/*.java src/algorithms/*.java src/pacman/*.java
    javac -d bin src/*.java src/algorithms/*.java
    echo "Done."
fi

# Run the program if args is -r
if [ "$args" == "-r" ]; then
    echo "Running..."
    if [ "$2" == "main" ]; then
        java -cp bin Main
    elif [ "$2" == "pacman" ]; then
        java -cp bin Animation
    elif [ "$2" == "tridi" ]; then
        java -cp bin Tridi
    else
        echo "Invalid argument."
    fi
fi

# Clean the bin directory if args is -t
if [ "$args" == "-t" ]; then
    echo "Cleaning..."
    rm -rf bin/*
    echo "Done."
fi

# Compile and run the program if args is -cr
if [ "$args" == "-cr" ]; then
    echo "Compiling..."
    # javac -d bin src/*.java src/algorithms/*.java src/pacman/*.java
    javac -d bin src/*.java src/algorithms/*.java
    echo "Done."
    echo "Running..."
    if [ "$2" == "main" ]; then
        java -cp bin Main
    elif [ "$2" == "pacman" ]; then
        java -cp bin Animation
    elif [ "$2" == "tridi" ]; then
        java -cp bin Tridi
    elif [ "$2" == "8" ]; then
        java -cp bin Surface
    else
        echo "Invalid argument."
    fi
fi
