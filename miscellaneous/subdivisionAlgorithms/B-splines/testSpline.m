function [ ] = testSpline(inf,sup,degree,showControl)
x        = inf:sup;
[xx, yy] = meshgrid(x,x);

f        = @(x,y)(x.^2 + y.^2);
zz       = f(xx,yy);
subTestSpline

f        = @(x,y)(40*cos(y)*sin(x).^2);
zz       = f(xx,yy);
subTestSpline

f        = @(x,y)(sin(x.*y));
zz       = f(xx,yy);
subTestSpline

[ xx, yy, zz ] = peaks(15);
subTestSpline