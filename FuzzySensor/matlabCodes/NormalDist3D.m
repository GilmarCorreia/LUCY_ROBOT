close all
clear all
clc

time = 0:0.01:15;
porc = 0:0.1:100;
[X1,X2] = meshgrid(time,porc);
X = [X1(:) X2(:)];

%Anger, Fear, Happy, Sad, Disgust, Love, Gratitude, Sympathy
figure(1)
    %subplot(1,3,1)
        %hertensteinLight(X,porc,time);
    %subplot(1,3,2)
        %hertensteinModerate(X,porc,time);
    %subplot(1,3,3)
        hertensteinStrong(X,porc,time);