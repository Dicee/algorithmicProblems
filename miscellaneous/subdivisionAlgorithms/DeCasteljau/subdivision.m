function Q = subdivision(P,depth,t)
N = size(P,2);
if (~depth)
	Q = [];
	return;
else
	[L, R] = casteljauStore(P,t);
    Q      = [subdivision(L, depth - 1, t), L(:,N), subdivision(R, depth - 1, t)];
end

