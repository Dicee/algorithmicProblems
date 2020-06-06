function Q = casteljauSurface(P,depthSub,t)
[N, M, L] = size(P);
Qtemp     = zeros(N,2^depthSub-1,3);
Q         = zeros(2^depthSub-1,2^depthSub-1,3);
for i=1:N
    Pi = [];
    for k=1:L
        Pi = [ Pi ; P(i,:,k) ];
    end
    Qi = subdivision(Pi,depthSub,t);
    for k=1:L
        Qtemp(i,:,k) = Qi(k,:);
    end
end
for j=1:size(Q,2)
    Pj = [];
    for k=1:L
        Pj = [ Pj ; Qtemp(:,j,k)' ];
    end
    Qj = subdivision(Pj,depthSub,t);  
    for k=1:L
        Q(:,j,k) = Qj(k,:)';
    end
end

