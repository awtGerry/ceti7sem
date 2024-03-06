#!/bin/sh

case $1 in
    "2") file="HelloWorld.java" ;;
    "3"|"random") file="RandomNumbers.java" ;;
    "4") file="OrdNumber.java" ;;
    "5") file="Substring.java" ;;
    "6") file="Hex2IP.java" ;;
    "7") file="Ventana.java" ;;
    "8") file="Arquimedes.java" ;;
    "9") file="Pastel.java" ;;
    "10") file="Monito.java" ;;
    "11") file="Visor.java" ;;
    "12") file="Mouse.java" ;;
    *)
        echo "No hay programa para el número $1."
        exit 1
        ;;
esac

echo "Running $file..."
echo ""
java --source 21 --enable-preview $file
