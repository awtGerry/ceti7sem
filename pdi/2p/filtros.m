% Obtener la imagen
image = imread('~/Documents/ceti/ceti7sem/pdi/parrot.jpg');

% Gray scale
image = rgb2gray(image);

% Definir el kernel laplaciano
hat_kernel = [
    0 0 1 0 0;
    0 1 2 1 0;
    1 2 -16 2 1;
    0 1 2 1 0;
    0 0 1 0 0
];

% Definir el kernel
box_kernel = [
    0 0 0 0 0;
    0 1 1 1 0;
    0 1 1 1 0;
    0 1 1 1 0;
    0 0 0 0 0
];

% Definir la máscara de filtro gaussiano
gauss_kernel = [
    0 1 2 1 0;
    1 3 5 3 1;
    2 5 9 5 2;
    1 3 5 3 1;
    0 1 2 1 0
];

% Definir la máscara de filtro de suavizado (3x3)
smooth_kernel = [
    1 1 1;
    1 1 1;
    1 1 1
];

% Aplicar el filtro mexican hat
mexican_hat = laplace(image, hat_kernel);
% Mostrar la imagen original
subplot(1, 2, 1);
imshow(image);
title('Original');
% Mostrar la imagen con el filtro
subplot(1, 2, 2);
imshow(uint8(mexican_hat));
title('Con filtro mexican hat');

% Aplicar el filtro box
box = box_filter(image, box_kernel);
% Mostrar la imagen con el filtro
figure;
subplot(1, 2, 1);
imshow(image);
title('Original');
subplot(1, 2, 2);
imshow(uint8(box));
title('Con filtro box');

% Aplicar el filtro gaussiano
gaussian = gaussian_filter(image, gauss_kernel);
% Mostrar la imagen con el filtro
figure;
subplot(1, 2, 1);
imshow(image);
title('Original');
subplot(1, 2, 2);
imshow(uint8(gaussian));
title('Con filtro gaussiano');

% Aplicar el filtro de suavizado
smooth = smooth_filter(image);
% Mostrar la imagen con el filtro
figure;
subplot(1, 2, 1);
imshow(image);
title('Original');
subplot(1, 2, 2);
imshow(uint8(smooth));
title('Con filtro de suavizado');

% mexican hat filter function
function mexican_hat = laplace(image, kernel)
    % Convertir la imagen a double
    image = double(image);
    % Obtener las dimensiones de la imagen
    [x, y] = size(image);
    % Liberar la memoria
    mexican_hat = zeros(x, y);
    for r=3:x-2
        for c=3:y-2
            % Aplicar la máscara
            mexican_hat(r, c) = sum(sum(kernel .* image(r-2:r+2, c-2:c+2))) / 25;
        end
    end
end

% box filter function
function box = box_filter(image, kernel)
    % Convertir la imagen a double
    image = double(image);
    % Obtener las dimensiones de la imagen
    [x, y] = size(image);
    % Liberar la memoria
    box = zeros(x, y);
    for r=2:x-1
        for c=2:y-1
            % Aplicar la máscara
            box(r, c) = sum(sum(kernel .* image(r-1:r+1, c-1:c+1))) / 25;
        end
    end
end

% gaussian filter function
function gaussian = gaussian_filter(image, kernel)
    % Convertir la imagen a double
    image = double(image);
    % Obtener las dimensiones de la imagen
    [x, y] = size(image);
    % Liberar la memoria
    gaussian = zeros(x, y);
    for r=3:x-2
        for c=3:y-2
            % Aplicar la máscara
            gaussian(r, c) = sum(sum(kernel .* image(r-2:r+2, c-2:c+2))) / 25;
        end
    end
end

% smooth filter function
function smooth = smooth_filter(image)
    % Convertir la imagen a double
    image = double(image);
    % Obtener las dimensiones de la imagen
    [x, y] = size(image);
    % Liberar la memoria
    smooth = zeros(x, y);
    for r=2:x-1
        for c=2:y-1
            % Aplicar la máscara
            smooth(r, c) = sum(sum(kernel .* image(r-1:r+1, c-1:c+1))) / 9;
        end
    end
end
