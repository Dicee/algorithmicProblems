function [] = test2D(depth,degree)
[X, Y]  = saisie_points;
P       = [X ; Y];

tic
Bspline = splineFermee(P,depth,degree);
toc

hold on
plot(Bspline(1,:),Bspline(2,:),'r');
hold off