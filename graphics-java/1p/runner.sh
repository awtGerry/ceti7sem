#!/bin/sh

case $1 in
    "2") file="HelloWorld.java" ;;
    "3"|"random") file="RandomNumbers.java" ;;
    "4") file="OrdNumber.java" ;;
    "5") file="Substring.java" ;;
    "6") file="Hex2IP.java" ;;
    *)
        echo "No hay programa para el número $1."
        exit 1
        ;;
esac

echo "Compiling $file..."
echo ""
java --source 21 --enable-preview $file
