class main {
    // Colores para la salida
    public String red = "\033[31m";
    public String green = "\033[32m";
    public String reset = "\033[0m";

    void main(String[] args) {
        /* Verificar que se ingresen los parámetros -hex o -ip y la cadena correspondiente */
        if (args.length != 2) {
            System.out.println(red + "Error: Faltan parámetros" + reset);
            return;
        }

        if (args[0].equals("-hex"))
            System.out.println(green + hex2ip(args[1]) + reset);
        else if (args[0].equals("-ip"))
            System.out.println(green + ip2hex(args[1]) + reset);
        else
            System.out.println(red + "Error: Parámetro inválido" + reset);
    }

    String hex2ip(String hex) {
        String ip = "";
        // checar que la cadena sea de longitud 8 & que sea un número hexadecimal
        if (hex.length() != 8 || !hex.matches("[0-9A-Fa-f]+")) {
            String err = red + "Error: Cadena inválida" + reset;
            return err;
        }
        for (int i = 0; i < hex.length(); i += 2) {
            ip += Integer.parseInt(hex.substring(i, i + 2), 16); // Convertir de hexadecimal a decimal
            /* Agregar punto si no es ultimo */
            if (i < hex.length() - 2) {
                ip += ".";
            }
        }
        return ip;
    }

    String ip2hex(String ip) {
        String hex = "";
        String[] octetos = ip.split("\\."); // Separar la cadena por puntos
        for (int i = 0; i < octetos.length; i++) { // Convertir de decimal a hexadecimal
            // Agregar ceros de ser necesario
            hex += String.format("%02X", Integer.parseInt(octetos[i]));
        }
        return hex;
    }
}
