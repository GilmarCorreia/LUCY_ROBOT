%% Gráfico de Média de tempo de toque - Hertenstein

function porc = hertensteinTime(time, endTime)
           %raiva, medo, alegria, tristeza, nojo , amor, gratidão, simpatia
    mean = [ 4.5 , 7.6 ,   5.8  ,   6.4   , 5.8  , 5.3 ,   6.0   ,   6.4];
    sigma = [5.1 , 8.5 ,   3.7  ,   4.3   , 7.0  , 4.7 ,   4.6   ,   5.6];
    
    logic = (endTime >= mean);
    porc = zeros(1,8);
    
    for i=1:8
        initialTime =0;
        finalTime = 0;
        
        if logic(i)
            initialTime = endTime;
            finalTime = time(end);
        else
            initialTime = 0;
            finalTime = endTime;
        end
        
        porc(i) = integral(@(time) normpdf(time,mean(i),sigma(i)), initialTime, finalTime);
    end
end