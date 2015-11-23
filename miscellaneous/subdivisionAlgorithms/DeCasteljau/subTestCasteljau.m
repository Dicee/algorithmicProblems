P        = zeros(size(xx,1),size(xx,2),3);
P(:,:,1) = xx;
P(:,:,2) = yy;
P(:,:,3) = zz;

for i=2:6
    title = strcat('Depth=',num2str(i));
    figure('Name',title)    
    axis off
    
    Q = casteljauSurface(P,i,1/2);
    hold on     
    if (showControl)        
        surf(P(:,:,1),P(:,:,2),P(:,:,3))  
        alpha 0.5
    end
    surf(Q(:,:,1),Q(:,:,2),Q(:,:,3))       
    hold off
end

input('Tapez sur une touche pour passer au test suivant...')
close all