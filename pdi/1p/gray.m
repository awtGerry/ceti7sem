% load image
img = imread('../flower.jpeg');

% extract the color channels
r = img(:,:,1);
g = img(:,:,2);
b = img(:,:,3);

% convert to grayscale
img_gray = (r + g + b) / 3;

% display the grayscale image
imshow(img_gray); % convert to uint8
imwrite(img_gray, 'flower_gray.jpeg'); % save the grayscale image
