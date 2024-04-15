% Definir el kernel
kernel = [
    0 0 0 0 0;
    0 1 1 1 0;
    0 1 1 1 0;
    0 1 1 1 0;
    0 0 0 0 0
];

% box filter function
function filter = box(image)
    % Convertir la imagen a double
    image = double(image);
    % Obtener las dimensiones de la imagen
    [x, y] = size(image);
    for r=2:x-1
        for c=2:y-1
            % Aplicar la máscara
            filter(r, c) = sum(sum(kernel .* image(r-1:r+1, c-1:c+1))) / 25;
        end
    end
end
