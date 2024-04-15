% Definir la máscara de filtro gaussiano
kernel = [
    0 1 2 1 0;
    1 3 5 3 1;
    2 5 9 5 2;
    1 3 5 3 1;
    0 1 2 1 0
];

% gaussian filter function
function filter = gaussian(image)
    % Convertir la imagen a double
    image = double(image);
    % Obtener las dimensiones de la imagen
    [x, y] = size(image);
    for r=3:x-2
        for c=3:y-2
            % Aplicar la máscara
            filter(r, c) = sum(sum(kernel .* image(r-2:r+2, c-2:c+2))) / 25;
        end
    end
end
