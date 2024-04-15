% Obtener la imagen
image = imread('../flower.jpeg');

% Gray scale
image = rgb2gray(image);

% Definir el kernel laplaciano
kernel = [
    0 0 1 0 0;
    0 1 2 1 0;
    1 2 -16 2 1;
    0 1 2 1 0;
    0 0 1 0 0
];

% mexican hat filter function
function mexican_hat = laplace(image)
    % Convertir la imagen a double
    image = double(image);
    % Obtener las dimensiones de la imagen
    [x, y] = size(image);
    for r=3:x-2
        for c=3:y-2
            % Aplicar la máscara
            mexican_hat(r, c) = sum(sum(kernel .* image(r-2:r+2, c-2:c+2))) / 25;
        end
    end
end
