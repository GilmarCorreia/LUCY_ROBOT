clc
clear all
close all

%% Denavit-Hartenberg Bioloid ARM

syms L1 L2 L3 a1 T1 T2 T3

T1 = 0;
T2 = 0;
T3 = 0;

L1 = 6.790;
L2 = 6.855;
L3 = 10.650;
a1 = 1.320;

A10 = [cosd(T1) 0 -sind(T1) (-a1*cosd(T1)); sind(T1) 0 cosd(T1) (-a1*sind(T1)); 0 -1 0 L1; 0 0 0 1];
A21 = [cosd(-T2) -sind(-T2) 0 (L2*cosd(-T2)); sind(-T2) cosd(-T2) 0 (L2*sind(-T2)); 0 0 1 0; 0 0 0 1];
A32 = [cosd(-T3) 0 -sind(-T3) (L3*cosd(-T3)); sind(-T3) 0 cosd(-T3) (L3*sind(-T3)); 0 -1 0 0; 0 0 0 1];

A30 = A10*A21*A32

%% Inverse Kinematics Bioloid ARM

x = 13.5;
y = 0;
z = 0;

%x = A30(1,4);
%y = A30(2,4);
%z = A30(3,4);

T1 = atan2(y,x);
T3 = -real(acos((x^2+y^2 + (z-L1)^2 - L3^2 - (L2-a1)^2)/(2*(L2-a1)*L3)));

tangPhi = (z - L1)/(sqrt(x^2+y^2));
tangBeta = (sin(T3)*L3)/((L2-a1)+(cos(T3)*L3));

T2 = atan2((tangPhi - tangBeta),(1+(tangPhi*tangBeta)));

angles = [rad2deg(T1) rad2deg(T2) rad2deg(T3)];

disp("[T1 T2 T3]")
disp(angles);

%% Convert to dec


anglesToDeg = 1023-[(512+((511/150)*(90+angles(1)))) (512+((angles(2)-90)*(511/150))) (512+(511/150)*angles(3))]
