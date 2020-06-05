clc
clear all
close all

%% Denavit-Hartenberg Bioloid ARM

syms L1 L2 L3 a1 T1 T2 T3

T1 = pi/2;
T2 = 0;
T3 = 0;

A10 = [cos(T1) 0 -sin(T1) (-a1*cos(T1)); sin(T1) 0 cos(T1) (-a1*sin(T1)); 0 -1 0 L1; 0 0 0 1];
A21 = [cos(T2) -sin(T2) 0 (L2*cos(T2)); sin(T2) cos(T3) 0 (L2*sin(T2)); 0 -1 0 0; 0 0 0 1];
A32 = [cos(T3) 0 -sin(T3) (L3*cos(T3)); sin(T3) 0 cos(T3) (L3*sin(T3)); 0 -1 0 0; 0 0 0 1];

A30 = A10*A21*A32