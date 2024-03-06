clc; 
close all;
clear

imaqhwinfo
imaqhwinfo('winvideo')
imaqhwinfo('winvideo',1)

% analizar la camara
vid = videoinput('winvideo', 1, 'YUY2_640x480');
propinfo(vid) % muestra las propiedades de la camara
start(vid) % inicia la camara
preview(vid) % muestra la imagen de la camara
img = getsnapshot(vid); % captura una imagen
