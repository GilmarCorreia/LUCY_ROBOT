close all
clear all
clc

time = [0:0.001:30];
anger = normpdf(time,4.5,5.1);
fear = normpdf(time,7.6,8.5);
happy = normpdf(time,5.8,3.7);
sad = normpdf(time,6.4,4.3);
disgust = normpdf(time,5.8,7.0);
love = normpdf(time,5.3,4.7);
gratitude = normpdf(time,6.0,4.6);
sympathy = normpdf(time,6.4,5.6);

disp(hertensteinTime(time,5)*100)
% for i = [0:0.001:30]
%     fprintf('%f,',getArea(time, i)*100);
% end

figure(1)
    title('Duração das Emoções')
    xlabel('segundos [s]')
    ylabel('média')
    hold on
    plot(time, anger, 'LineWidth', 2)
    plot(time, fear, 'LineWidth', 2)
    plot(time, happy, 'LineWidth', 2) 
    plot(time, sad, 'LineWidth', 2)
    plot(time, disgust, 'LineWidth', 2) 
    plot(time, love, 'LineWidth', 2)
    plot(time, gratitude, 'LineWidth', 2)
    plot(time, sympathy, 'LineWidth', 2)
    hold off
    legend('Raiva','Medo','Felicidade', 'Tristeza', 'Nojo','Amor', 'Gratidão', 'Simpatia');
