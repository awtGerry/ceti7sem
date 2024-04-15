% Aplicar el filtro mexican hat
mexican_hat = laplace(image);

% Mostrar la imagen original
subplot(1, 2, 1);
imshow(image);
title('Original');

% Mostrar la imagen con el filtro
subplot(1, 2, 2);
imshow(uint8(mexican_hat));
title('Con filtro mexican hat');

% Aplicar el filtro box
filter = box(image);

% Mostrar la imagen con el filtro
figure;
subplot(1, 2, 1);
imshow(image);
title('Original');

subplot(1, 2, 2);
imshow(uint8(filter));
title('Con filtro box');

% Aplicar el filtro gaussiano
filter = gaussian(image);

% Mostrar la imagen con el filtro
figure;
subplot(1, 2, 1);
imshow(image);
title('Original');

subplot(1, 2, 2);
imshow(uint8(filter));
title('Con filtro gaussiano');

% Aplicar el filtro de suavizado
filter = smooth(image);

% Mostrar la imagen con el filtro
figure;
subplot(1, 2, 1);
imshow(image);
title('Original');

subplot(1, 2, 2);
imshow(uint8(filter));
title('Con filtro de suavizado');
