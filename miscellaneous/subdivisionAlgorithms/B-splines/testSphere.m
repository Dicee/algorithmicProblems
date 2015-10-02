function [] = testSphere(pts,depth,degree,showControl)
P = zeros(pts + 1,pts + 1,3);
[P(:,:,1),P(:,:,2),P(:,:,3)] = sphere(pts);
Q = splineFermeeSurface(P,depth,degree);

close all

hold on
axis off
if (showControl)
    surf(P(:,:,1),P(:,:,2),P(:,:,3))
    alpha 0.2
end
surf(Q(:,:,1),Q(:,:,2),Q(:,:,3))
colormap(autumn)
hold off
