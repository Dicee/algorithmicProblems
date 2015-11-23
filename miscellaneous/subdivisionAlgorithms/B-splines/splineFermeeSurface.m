function Q = splineFermeeSurface(P,depth,degree)
[N, M, L] = size(P);
Qtemp     = [];
Q         = [];
for i=1:N
    Pi = [];
    for k=1:L
        Pi = [ Pi ; P(i,:,k) ];
    end
    Qi = splineFermee(Pi,depth,degree);
    if (size(Qtemp) == 0)
        Qtemp = zeros(N,size(Qi,2),3);
    end
    for k=1:L
        Qtemp(i,:,k) = Qi(k,:);
    end
end
for j=1:size(Qtemp,2)
    Pj = [];
    for k=1:L
        Pj = [ Pj ; Qtemp(:,j,k)' ];
    end
    Qj = splineFermee(Pj,depth,degree);  
    if (size(Q) == 0)
        Q = zeros(size(Qj,2),size(Qtemp,2),3);
    end
    for k=1:L
        Q(:,j,k) = Qj(k,:)';
    end
end