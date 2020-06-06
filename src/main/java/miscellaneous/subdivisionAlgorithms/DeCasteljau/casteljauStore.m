function [L, R] = casteljauStore(P,t)
[N, M] = size(P);
R      = zeros(N,M);
L      = zeros(N,M);
rot    = [2:M 1];

L(:,1) = P(:,1);
R(:,M) = P(:,M);
for i=2:M
    P          = t*P + (1-t)*P(:,rot);
    L(:,i)     = P(:,1);
    R(:,M-i+1) = P(:,M-i+1);
end
