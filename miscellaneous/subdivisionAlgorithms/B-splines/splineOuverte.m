function Q = splineOuverte(P, depth, degree)
Q = P;
for k=1:depth 
    n = size(Q,2);
    R = zeros(size(Q,1),2*size(Q,2));
    for i=1:size(Q,1)
        R(i,:) = reshape([Q(i,:) ; Q(i,:)],1,2*n);
    end
    Q = R;  
    for i=1:degree
        for j=2:2*n-1
            if (i == 1)
                if (~mod(j,2))
                    Q(:,j) = Q(:,j-1)/2 + Q(:,j+1)/2;
                end
            else
                Q(:,j) = Q(:,j-1)/2 + Q(:,j)/2; 
            end
        end        
    end
end