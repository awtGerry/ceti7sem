clc;
clear;
close all;

% Ejercicio 1
img = imread('img.jpg');

[m,n,p] = size(img);

% grayscaling withouth using rgb2gray
for i = 1:m
    for j = 1:n
        x = ((img(i,j,1) * 0.2989) + (img(i,j,2) * 0.5870) + (img(i,j,3) * 0.1140) * 1.5);
        img(i,j,1) = x;
        img(i,j,2) = x;
        img(i,j,3) = x;
    end
end

% Histogram original
hist_original = imhist(img);

% Histogram equalization
img_eq = histeq(img);

% histograma acumulado
hist_acum = cumsum(hist_original);

% graficar
figure;
subplot(3, 1, 1);
bar(hist_original);
title('Histograma Original');

subplot(3, 1, 2);
bar(hist_acum);
title('Histograma Acumulado');

subplot(3, 1, 3);
bar(imhist(img_eq));
title('Histograma Ecualizado');

% imagenes
figure;
subplot(1, 2, 1);
imshow(img);
title('Imagen Original');

subplot(1, 2, 2);
imshow(img_eq);
title('Imagen Ecualizada');
