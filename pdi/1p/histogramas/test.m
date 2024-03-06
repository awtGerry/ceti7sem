img = imread('https://i.pinimg.com/736x/51/e3/7c/51e37c2b688170cdc07888eba287bfd1.jpg');

% Muestreo de la imagen original

[m,n,p] = size(img);

% Escala de grises por ponderacion multiplicado por 1.5
imgp = img;
for i = 1 : m
    for j = 1 : n
        x = (imgp(i,j) * 0.299 + imgp(i,j) * 0.587 + imgp(i,j) * 0.114) * 1.5;
        imgp(i,j,1) = x;
        imgp(i,j,2) = x;
        imgp(i,j,3) = x;
    end
end

% Histograma sumando 100
imshow(imgp)

img3 = img;
for i = 1 : m
    for j = 1 : n
        x = (img3(i,j) * 0.299 + img3(i,j) * 0.587 + img3(i,j) * 0.114) + 100;
        img3(i,j,1) = x;
        img3(i,j,2) = x;
        img3(i,j,3) = x;
    end
end

figure(2)
imshow(img3)

% Hisotgrama imagen original
for rxp = 1 : fil
    for ryp = 1 : col
        rxyp = img(rxp,ryp);
        for val = 1 : pixmx
            if rxyp = val
                tam(val) = tam(val) + 1;
            end
        end
    end
end

stem(tam)
