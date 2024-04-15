% Definir la máscara de filtro de suavizado (3x3)
kernel = [
    1 1 1;
    1 1 1;
    1 1 1
];

% smooth filter function
function filter = smooth(image)
    % Convertir la imagen a double
    image = double(image);
    % Obtener las dimensiones de la imagen
    [x, y] = size(image);
    for r=2:x-1
        for c=2:y-1
            % Aplicar la máscara
            filter(r, c) = sum(sum(kernel .* image(r-1:r+1, c-1:c+1))) / 9;
        end
    end
end
